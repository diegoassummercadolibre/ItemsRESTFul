import com.google.gson.Gson;

import java.net.UnknownHostException;

import static spark.Spark.*;
import static spark.Spark.delete;

public class ItemsController {

    public static void main(String[] args) throws UnknownHostException {

        port(8080);

        final ItemService itemService = new ItemService();
        final String resource = "/items";

        get(resource, (req, res) -> {
            res.type("application/json");
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, new Gson()
                            .toJsonTree(itemService.get())));
        });

        get(resource + "/:id", (req, res) -> {
            res.type("application/json");
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, new Gson()
                            .toJsonTree(itemService.get(req.params(":id")))));
        });

        post(resource, (req, res) -> {
            res.type("application/json");
            Item item = new Gson().fromJson(req.body(), Item.class);
            itemService.add(item);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        put(resource + "/:id", (req, res) -> {
            res.type("application/json");
            Item toEdit =  new Gson().fromJson(req.body(), Item.class);
            Item editItem = itemService.edit(req.params(":id"), toEdit);

            if(editItem != null){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(editItem)));
            }
            else{
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree("Item no encontrado o error al editar")));
            }
        });

        options(resource + "/:id", (req, res) -> {
            res.type("application/json");
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS
                            , (itemService.exist(req.params(":id"))) ? "El Item existe" : "El Item no existe"));
        });

        delete(resource + "/:id", (req, res) -> {
            res.type("application/json");
            String msg = new String();

            msg = itemService.delete(req.params(":id")) == 1 ? "Item Borrado" : "No se puedo borrar el Item porque no existe";

            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, msg));
        });
    }
}
