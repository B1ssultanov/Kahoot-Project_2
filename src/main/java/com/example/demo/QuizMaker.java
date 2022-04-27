package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuizMaker {
    public static void main(String[] args) throws InvalidQuizFormatException {
        Quiz quiz = new Quiz();
        try{
            quiz.loadFromFile(args[0]);
        }
        catch (FileNotFoundException | ArrayIndexOutOfBoundsException exception){
            throw new InvalidQuizFormatException();
        }
    }
}
