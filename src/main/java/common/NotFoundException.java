package common;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("No se encontró el recurso");
    }
}