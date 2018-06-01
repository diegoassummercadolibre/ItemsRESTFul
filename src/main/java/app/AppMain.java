package app;

import controllers.ItemsController;

import java.net.UnknownHostException;

public class AppMain {

    public static void main(String[] args) throws UnknownHostException {

        ItemsController.init();
    }
}