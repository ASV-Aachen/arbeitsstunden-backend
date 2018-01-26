package de.asvaachen.workinghours.backend.member.service;

public class EmailExistsException extends Throwable {
    public EmailExistsException(String email) {
        super("email :" + email + " already exists.");
    }
}
