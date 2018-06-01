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
                return StandardResponse.getResponse(StatusResponse.SUCCESS, items);
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            }
        });

        get(resource + "/:id", (req, res) -> {
            try {
                res.type("application/json");
                Item item = itemService.get(req.params(":id"));

                return StandardResponse.getResponse(StatusResponse.SUCCESS, item);

            } catch (ItemException e) {
                res.status(HttpStatus.SC_NOT_FOUND);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            }
        });

        post(resource, (req, res) -> {
            try {
                res.type("application/json");
                Item item = new Gson().fromJson(req.body(), Item.class);
                String id = itemService.add(item);

                return StandardResponse.getResponse(StatusResponse.SUCCESS, id);

            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            }
        });

        put(resource + "/:id", (req, res) -> {
            try {
                res.type("application/json");
                Item item = itemService.edit(req.params(":id"), new Gson().fromJson(req.body(), Item.class));

                return StandardResponse.getResponse(StatusResponse.SUCCESS, item);

            } catch (ItemException e) {
                res.status(HttpStatus.SC_NOT_FOUND);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            }
        });

        delete(resource + "/:id", (req, res) -> {
            try {
                res.type("application/json");
                boolean response = itemService.delete(req.params(":id"));
                return StandardResponse.getResponse(StatusResponse.SUCCESS, response);

            } catch (ItemException e) {
                res.status(HttpStatus.SC_NOT_FOUND);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            } catch (Exception e) {
                res.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return StandardResponse.getResponse(StatusResponse.ERROR, e.getMessage());
            }
        });


        //VISTAS
        get(resource + "/view/list", (req, res) -> {
            Collection<Item> items = itemService.getAll();
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Lista de Items");
            model.put("items", items);

            return new ModelAndView(model, "itemList.vm");
        }, new VelocityTemplateEngine());
    }
}
