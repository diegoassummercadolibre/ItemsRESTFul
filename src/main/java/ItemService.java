import com.google.gson.Gson;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class ItemService {

    private TransportClient _client;
    private final String _index = "test";
    private final String _type = "item";

    public ItemService() throws UnknownHostException {
         _client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    public String add(Item item) {
        IndexResponse response = _client.prepareIndex(_index, _type)
                .setSource(new Gson().toJson(item), XContentType.JSON)
                .get();

        return response.getId();
    }

    public List<Item> get() {
        List<Item> results = new ArrayList<Item>();
        Item item;
        SearchResponse searchResponse = _client.prepareSearch().setQuery(matchAllQuery()).get();
        for (SearchHit hit : searchResponse.getHits()) {
            item = new Gson().fromJson(hit.getSourceAsString(), Item.class);
            item.setId(hit.getId());
            results.add(item);
        }

        return results;
    }

    public Item get(String id) {
        GetResponse response = _client.prepareGet(_index,_type, id).get();
        Item item = new Gson().fromJson(response.getSourceAsString(), Item.class);
        item.setId(response.getId());

        return item;
    }

    public Item edit(String id, Item item) {
        _client.prepareUpdate(_index, _type, id)
                .setDoc(new Gson().toJson(item), XContentType.JSON)
                .get();

        return get(id);
    }

    public boolean delete(String id) {
        _client.prepareDelete(_index, _type, id)
                .get();

        return true;
    }
}
