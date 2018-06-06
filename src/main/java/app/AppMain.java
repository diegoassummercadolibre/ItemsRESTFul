package app;

import controllers.ItemsController;

import java.net.UnknownHostException;

public class AppMain {

    public static void main(String[] args) {

        try {
            ItemsController.init();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}