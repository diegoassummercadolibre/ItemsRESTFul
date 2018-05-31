import com.google.gson.Gson;

import java.net.UnknownHostException;
import java.util.Collection;

import static spark.Spark.*;
import static spark.Spark.delete;

public class ItemsController {

    public static void main(String[] args) throws UnknownHostException {

        port(8080);

        final ItemService itemService = new ItemService();
        final String resource = "/items";

        get(resource, (req, res) -> {
            res.type("application/json");
            Collection<Item> items = itemService.get();

            if(items.toArray().length != 0){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(items)));
            }
            else{
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree("No hay Items en la base de datos")));
            }
        });

        get(resource + "/:id", (req, res) -> {
            res.type("application/json");
            Item item = itemService.get(req.params(":id"));

            if(item != null){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(item)));
            }
            else{
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree("El Item no existe")));
            }
        });

        post(resource, (req, res) -> {
            res.type("application/json");
            Item item = new Gson().fromJson(req.body(), Item.class);
            String id = itemService.add(item);

            if(item != null){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(id)));
            }
            else{
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree("Hubo un error. El Item no fue creado")));
            }
        });

        put(resource + "/:id", (req, res) -> {
            res.type("application/json");
            Item item = itemService.edit(req.params(":id"), new Gson().fromJson(req.body(), Item.class));

            if(item != null){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(item)));
            }
            else{
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree("El Item no existe o error al editar")));
            }
        });

        delete(resource + "/:id", (req, res) -> {
            res.type("application/json");
            String msg = new String();

            if(itemService.delete(req.params(":id"))){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree("El Item fue eliminado")));
            }
            else{
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree("El Item no existe")));
            }
        });
    }
}
