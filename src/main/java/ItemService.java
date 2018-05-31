import com.google.gson.Gson;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class ItemService {

    TransportClient _client;


    public ItemService() throws UnknownHostException {
         _client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    public String add(Item item) {
        //item.setId("MLA" + new Date().getTime());
        IndexResponse response = _client.prepareIndex("dev", "item")
                .setSource(new Gson().toJson(item), XContentType.JSON)
                .get();

        return response.getId();
    }

    public List<Item> get() {
        /*SearchResponse response = _client.prepareSearch().execute().actionGet();
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        List<Item> results = new ArrayList<Item>();
        searchHits.forEach(hit -> results.add( new Gson().fromJson(hit.getSourceAsString(), Item.class)));*/

        List<Item> results = new ArrayList<Item>();
        Item item;
        SearchResponse searchResponse =  _client.prepareSearch().setQuery(matchAllQuery()).get();
        for (SearchHit hit : searchResponse.getHits()) {
            item = new Gson().fromJson(hit.getSourceAsString(), Item.class);
            item.setId(hit.getId());
            results.add(item);
        }

        return results;
    }

    public Item get(String id) {
        //ESTE CODIGO SE UTILIZARIA EN EL CASO QUE ENVIEMOS EL ID DE DOCUMENTO DE ELASTIC SEARCH
        GetResponse response = _client.prepareGet("dev","item", id).get();
        Item item = new Gson().fromJson(response.getSourceAsString(), Item.class);
        item.setId(response.getId());

        return item;


        /*SearchResponse response = _client.prepareSearch("dev")
                .setTypes("item")
                .setQuery(QueryBuilders.matchQuery("id", id))
                .get();

        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        List<Item> results = new ArrayList<Item>();
        searchHits.forEach(hit -> results.add( new Gson().fromJson(hit.getSourceAsString(), Item.class)));

         SearchResponse searchResponse =  _client.prepareSearch().setQuery(matchAllQuery()).get();
        String yourId  =new String();
        for (SearchHit hit : searchResponse.getHits()) {
             yourId += hit.getId();
        }

        return results.toArray().length > 0 ? results.get(0) : null;*/
    }

    public Item edit(String id, Item item) throws IOException, InterruptedException, ExecutionException {
         //ESTE CODIGO SE UTILIZARIA EN EL CASO QUE ENVIEMOS EL ID DE DOCUMENTO DE ELASTIC SEARCH
        _client.prepareUpdate("dev", "item", id)
                .setDoc(new Gson().toJson(item), XContentType.JSON)
                .get();

        /*String json =new Gson().toJson(item.getPictures());

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
                //+ "; ctx._source.pictures = " + new Gson().toJson(item.getPictures()));
                //+ "; ctx._source.pictures = " + item.getPictures() + "\"");

        updateByQuery.source("dev")
                .filter(QueryBuilders.matchQuery("id", id))
                .script(scr);
        updateByQuery.get();*/

        return get(id);
    }

    public boolean delete(String id) {
        // ESTE CODIGO SE UTILIZARIA EN EL CASO QUE ENVIEMOS EL ID DE DOCUMENTO DE ELASTIC SEARCH
        DeleteResponse response = _client.prepareDelete("dev", "item", id)
                .get();

        return true;
       /* BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(_client)
                .filter(QueryBuilders.matchQuery("id", id))
                .source("dev")
                .get();

        return response.getDeleted();*/
    }
}
