package com.arthuryasak.exceptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DAOException extends RuntimeException {
    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
