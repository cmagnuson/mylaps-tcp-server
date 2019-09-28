package com.mtecresults.mylapstcpserver.domain;

public class DataHandlingException extends Exception {

    public DataHandlingException(String message){
        super(message);
    }

    public DataHandlingException(String message, Throwable cause){
        super(message, cause);
    }
}
