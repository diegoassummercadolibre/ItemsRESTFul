import com.google.gson.Gson;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class ItemService {

    TransportClient _client;


    public ItemService() throws UnknownHostException {
/*
        Node node = NodeBuilder.nodeBuilder().settings(Settings.builder()
                        .put("path.home", "/Users/dassum/Downloads/elasticsearch-6.2.4/bin/"))
                        .node();*/

/*         Node node = nodeBuilder()
                .clusterName("elasticsearch").client(true).node();

           _client = node.client();*/

         _client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));


    }

    public void add(Item item) {

        item.setId ("MLA" + new Date().getTime());
        IndexResponse response = _client.prepareIndex("dev", "item")
                .setSource(new Gson().toJson(item), XContentType.JSON)
                .get();


        /*String index = "list";
        _client.admin().indices()
                .create(new CreateIndexRequest(index))
                .actionGet();*/
    }

    public List<Item> get() {

        SearchResponse response = _client.prepareSearch().execute().actionGet();
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        List<Item> results = new ArrayList<Item>();
        searchHits.forEach(hit -> results.add( new Gson().fromJson(hit.getSourceAsString(), Item.class)));

        /*SearchResponse searchResponse =  _client.prepareSearch().setQuery(matchAllQuery()).get();
        String yourId  =new String();
        for (SearchHit hit : searchResponse.getHits()) {
             yourId += hit.getSourceAsString();
        }*/

        return results;
    }

    public Item get(String id) {
       /* GetResponse response = _client.prepareGet("dev","item", id).get();
        return new Gson().fromJson(response.getSourceAsString(), Item.class);*/

        SearchResponse response = _client.prepareSearch("dev")
                .setTypes("item")
                .setQuery(QueryBuilders.matchQuery("id", id))
                .get();

        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        List<Item> results = new ArrayList<Item>();
        searchHits.forEach(hit -> results.add( new Gson().fromJson(hit.getSourceAsString(), Item.class)));

        return results.get(0);
    }

    public Item edit(String id, Item item) throws IOException {
       /*_client.prepareUpdate("dev", "item", item.getId())
                .setDoc(new Gson().toJsonTree(item, Item.class))
                .get();*/

        UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(_client);
        Script scr = new Script("ctx._source.title = \"" + item.getTitle() + "\""
                + "; ctx._source.category_id = \"" + item.getCategory_id() + "\""
                + "; ctx._source.price = \"" + item.getPrice() + "\""
                + "; ctx._source.currency_id = \"" + item.getCurrency_id() + "\""
                + "; ctx._source.available_quantity = \"" + item.getAvailable_quantity() + "\""
                + "; ctx._source.buying_mode = \"" + item.getBuying_mode() + "\""
                + "; ctx._source.listing_type_id = \"" + item.getListing_type_id() + "\""
                + "; ctx._source.condition = \"" + item.getCondition() + "\""
                + "; ctx._source.description = \"" + item.getDescription() + "\""
                + "; ctx._source.video_id = \"" + item.getVideo_id() + "\""
                + "; ctx._source.warranty = \"" + item.getWarranty() + "\"");
                //+ "; ctx._source.pictures = [\"http://123.jpg\", \"http://en.wikipedia.org/wiki/File:Teashades.gif\"]");
                //+ "; ctx._source.pictures = " + item.getPictures() + "\"");

        /*Script scr = new Script("ctx._source.title = \"Titulo Actualizado 22\""
         + "; ctx._source.description = \"description Actualizado 22\"");*/
/*

        String pic = new String();
        pic += item.getPictures();*/

        updateByQuery.source("dev")
                .filter(QueryBuilders.matchQuery("id", id))
                .script(scr);
        BulkByScrollResponse response = updateByQuery.get();

        return get(id);
    }

    public long delete(String id) {
       /* DeleteResponse response = _client.prepareDelete("dev", "item", id)
                .get();*/


       /* DeleteIndexResponse deleteResponse = _client.admin().indices().delete(new DeleteIndexRequest("dev")).actionGet();
        _client.admin().indices().prepareCreate("dev").get();*/


        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(_client)
                .filter(QueryBuilders.matchQuery("id", id))
                .source("dev")
                .get();

        return response.getDeleted();
    }

    public boolean exist(String id) {

        return true;
    }
}
