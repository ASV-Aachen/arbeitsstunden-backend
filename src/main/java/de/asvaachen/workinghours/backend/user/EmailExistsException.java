package de.asvaachen.workinghours.backend.user;

public class EmailExistsException extends Throwable {
    public EmailExistsException(String email) {
        super("email :" +email + " already exists.");
    }
}
