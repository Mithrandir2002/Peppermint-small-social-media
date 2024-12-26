package org.peppermint.socialmedia.exception;

public class UserExistedException extends RuntimeException{
    public UserExistedException(String email) {
        super("The email " + email + " is already registered in our records");
    }
}
