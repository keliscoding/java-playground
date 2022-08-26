package io.github.zam0k.api.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException() {
        super("Objeto não encontrado.");
    }
}
