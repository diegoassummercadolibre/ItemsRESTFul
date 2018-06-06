package services;

import com.google.gson.Gson;
import common.NotFoundException;
import domains.Item;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class ItemService {

    private TransportClient _client;
    private final String INDEX = "test";
    private final String TYPE = "item";

    public ItemService() throws UnknownHostException {
        _client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    public String add(Item item) {

         IndexResponse response = _client.prepareIndex(INDEX, TYPE)
                .setSource(new Gson().toJson(item), XContentType.JSON)
                .get();

        item.setId(response.getId());
        return response.getId();
    }

    public List<Item> getAll() {
        List<Item> items = new ArrayList<Item>();

        Item item;
        SearchResponse searchResponse = _client.prepareSearch().setQuery(matchAllQuery()).get();
        for (SearchHit hit : searchResponse.getHits()) {
            item = new Gson().fromJson(hit.getSourceAsString(), Item.class);
            item.setId(hit.getId());
            items.add(item);
        }

        return items;
    }

    public Item get(String id) throws NotFoundException {
        Item item;
        GetResponse response = _client.prepareGet(INDEX, TYPE, id).get();
        if (!response.isExists())
            throw new NotFoundException();

        item = new Gson().fromJson(response.getSourceAsString(), Item.class);
        if (item != null)
            item.setId(response.getId());

        return item;
    }

    public Item edit(String id, Item item) throws NotFoundException {
        Item itemEdited;
        try {
            UpdateResponse response = _client.prepareUpdate(INDEX, TYPE, id)
                    .setDoc(new Gson().toJson(item), XContentType.JSON)
                    .get();

            itemEdited = get(id);

        } catch (DocumentMissingException e) {
            throw new NotFoundException();
        }

        return itemEdited;
    }

    public boolean delete(String id) throws NotFoundException {
        DeleteResponse response = _client.prepareDelete(INDEX, TYPE, id)
                .get();

        if (!response.getResult().getLowercase().equals("deleted"))
            throw new NotFoundException();

        return true;
    }
}
