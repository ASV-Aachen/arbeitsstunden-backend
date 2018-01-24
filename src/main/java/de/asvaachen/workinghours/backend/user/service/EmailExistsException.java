package de.asvaachen.workinghours.backend.user.service;

public class EmailExistsException extends Throwable {
    public EmailExistsException(String email) {
        super("email :" + email + " already exists.");
    }
}
