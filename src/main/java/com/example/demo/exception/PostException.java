package com.example.demo.exception;

public class PostException extends Exception{
    private static final long serialVersionUID = 2806285144114914760L;
    public PostException(String message) {
        super(message);
    }
}