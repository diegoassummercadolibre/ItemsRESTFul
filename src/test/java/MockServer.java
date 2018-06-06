import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import domains.Item;
import domains.Picture;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MockServer {

    static MockServerClient mockServer = startClientAndServer(8081);

    public static void main(String[] args) {

        ArrayList<Item> listItems = new ArrayList<Item>();
        ArrayList<Picture> pictures = new ArrayList<Picture>();
        pictures.add(new Picture("http://pictures.com/pictures01"));
        pictures.add(new Picture("http://pictures.com/pictures02"));
        Item item1 = new Item("sc12emdDScs_2sd1", "title 1", "category 1", (float) 500.00, "Dolar", 1000, "CC", "", "comdition 1", "description of item 1", "video 1",  "warranty 01", pictures);
        Item item2 = new Item("dasSFGEewDEdS21R", "title 2", "category 2", (float) 500.00, "Dolar", 1000, "CC", "", "comdition 2", "description of item 2", "video 2",  "warranty 02", pictures);

        listItems.add(item1);
        listItems.add(item2);
        Gson gson = new Gson();

        mockServer
                .when(request()
                                .withMethod("GET")
                                .withPath("/items")
                )
                .respond(response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withBody(gson.toJson(listItems))
                                .withDelay(new Delay(SECONDS, 1)));

        mockServer
                .when(request()
                                .withMethod("GET")
                                .withPath("/items/1")
                )
                .respond(response()
                        .withStatusCode(200)
                        .withHeaders(
                                new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(gson.toJson(listItems.get(0)))
                        .withDelay(new Delay(SECONDS, 1)));
        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/items/2")
                )
                .respond(response()
                        .withStatusCode(200)
                        .withHeaders(
                                new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(gson.toJson(listItems.get(0)))
                        .withDelay(new Delay(SECONDS, 1)));

    }

}