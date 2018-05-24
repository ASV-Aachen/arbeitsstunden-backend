package de.asvaachen.workinghours.backend.members.model;

public class ErrorMessageDto {

    private Integer status;
    private String error;
    private String field;
    private String message;


    public ErrorMessageDto(Integer status, String error, String field, String message) {
        this.status = status;
        this.error = error;
        this.field = field;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
