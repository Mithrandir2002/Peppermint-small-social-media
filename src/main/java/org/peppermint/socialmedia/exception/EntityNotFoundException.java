package org.peppermint.socialmedia.exception;

import java.util.Locale;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Integer id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id " + id + " does not exist in our records");
    }
}
