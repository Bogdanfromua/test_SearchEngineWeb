package com.plb.test.searchengine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DocumentAddingException extends Throwable {
    public DocumentAddingException(String message) {
        super(message);
    }
}
