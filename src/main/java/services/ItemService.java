package services;

import com.google.gson.Gson;
import common.ItemException;
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
    private final String _index = "test";
    private final String _type = "item";

    public ItemService() throws UnknownHostException {
        _client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    public String add(Item item) throws ItemException {
        String id;
        try {
            IndexResponse response = _client.prepareIndex(_index, _type)
                    .setSource(new Gson().toJson(item), XContentType.JSON)
                    .get();

            id = response.getId();
            if (id != null)
                item.setId(response.getId());
            else
                throw new ItemException("El item no fue creado.");

        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    public List<Item> getAll() {
        List<Item> items = new ArrayList<Item>();
        try {

            Item item;
            SearchResponse searchResponse = _client.prepareSearch().setQuery(matchAllQuery()).get();
            for (SearchHit hit : searchResponse.getHits()) {
                item = new Gson().fromJson(hit.getSourceAsString(), Item.class);
                item.setId(hit.getId());
                items.add(item);
            }

        } catch (Exception e) {
            throw e;
        }

        return items;
    }

    public Item get(String id) throws ItemException {
        Item item;
        try {

            GetResponse response = _client.prepareGet(_index, _type, id).get();
            if (!response.isExists())
                throw new ItemException("No se encontró el Item");

            item = new Gson().fromJson(response.getSourceAsString(), Item.class);
            if (item != null)
                item.setId(response.getId());

        } catch (Exception e) {
            throw e;
        }

        return item;
    }

    public Item edit(String id, Item item) throws ItemException {
        Item itemEdited;
        try {
            UpdateResponse response = _client.prepareUpdate(_index, _type, id)
                    .setDoc(new Gson().toJson(item), XContentType.JSON)
                    .get();

            itemEdited = get(id);

        } catch (DocumentMissingException e) {
            throw new ItemException("No se encontró el Item");
        } catch (Exception e) {
            throw e;
        }

        return itemEdited;
    }

    public boolean delete(String id) throws ItemException {
        try {
            DeleteResponse response = _client.prepareDelete(_index, _type, id)
                    .get();

            if (!response.getResult().getLowercase().equals("deleted"))
                throw new ItemException("No se encontró el Item");

        } catch (Exception e) {
            throw e;
        }

        return true;
    }
}
