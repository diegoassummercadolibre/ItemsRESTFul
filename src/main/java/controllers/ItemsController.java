package controllers;

import com.google.gson.Gson;
import common.ItemException;
import common.StandardResponse;
import common.StatusResponse;
import domains.Item;
import org.apache.http.HttpStatus;
import services.ItemService;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.delete;

public class ItemsController {

    public static void init() throws UnknownHostException {

        port(8080);

        final ItemService itemService = new ItemService();
        final String resource = "/items";

        get(resource, (req, res) -> {
            try {
                res.type("application/json");
                Collection<Item> items = itemService.getAll();
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(items)));
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            }
        });

        get(resource + "/:id", (req, res) -> {
            try {
                res.type("application/json");
                Item item = itemService.get(req.params(":id"));

                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(item)));

            } catch (ItemException e) {
                res.status(HttpStatus.SC_NOT_FOUND);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            }
        });

        post(resource, (req, res) -> {
            try {
                res.type("application/json");
                Item item = new Gson().fromJson(req.body(), Item.class);
                String id = itemService.add(item);

                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(id)));

            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            }
        });

        put(resource + "/:id", (req, res) -> {
            try {
                res.type("application/json");
                Item item = itemService.edit(req.params(":id"), new Gson().fromJson(req.body(), Item.class));

                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(item)));

            } catch (ItemException e) {
                res.status(HttpStatus.SC_NOT_FOUND);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            }
        });

        delete(resource + "/:id", (req, res) -> {
            try {
                res.type("application/json");
                itemService.delete(req.params(":id"));
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS));

            } catch (ItemException e) {
                res.status(HttpStatus.SC_NOT_FOUND);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, new Gson()
                                .toJsonTree(e.getMessage())));
            }
        });


        //VISTAS
        get(resource + "/view/list", (req, res) -> {
            Collection<Item> items = itemService.getAll();
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Lista de Items");
            model.put("items", items);

            // The vm files are located under the resources directory
            return new ModelAndView(model, "itemList.vm");
        }, new VelocityTemplateEngine());
    }
}
