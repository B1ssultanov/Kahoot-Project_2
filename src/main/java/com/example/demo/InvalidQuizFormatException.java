package com.example.demo;

public class InvalidQuizFormatException extends Throwable {
    InvalidQuizFormatException(){
        super("Such a file does not exists!");
    }
}
