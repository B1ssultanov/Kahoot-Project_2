package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    String name;
    public static final List<Question> questions = new ArrayList<>();
    public static String[][] options = new String[1000][1000];
    public static int opind = 0;
    public static String[] testAnswerQuiz = new String[10000];
    public static String[] FillInAnswerQuiz = new String[10000];

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
    public  List<Question> loadFromFile(String name) throws FileNotFoundException {
        Quiz quiz = new Quiz();
        quiz.setName(name);

        File file = new File(name);//1
        Scanner sc = new Scanner(file);
        Scanner UI = new Scanner(System.in);
        double score = 0, fails = 0;

        while(sc.hasNextLine()) {
            String description = sc.nextLine();
            if (description.contains("________")) {
                FillIn fillIn = new FillIn();
                fillIn.setDescription(description);
                String answer = sc.nextLine();
                fillIn.setAnswer(answer);
                FillInAnswerQuiz[opind] = answer;

                questions.add(fillIn);
            } else {
                Test test = new Test();
                test.setDescription(description);
                questions.add(test);

                for (int i = 0; i < 4; ++i) {
                    options[opind][i] = sc.nextLine();
                }
                test.setAnswer(options[opind][0]);
                testAnswerQuiz[opind] = options[opind][0];

                ArrayList<String> arr = new ArrayList<>();
                for ( int i = 0 ; i < 4 ; ++i ){
                    arr.add(options[opind][i]);
                }
                Collections.shuffle(arr);
                for ( int i = 0 ; i < 4 ; ++i ){
                    options[opind][i] = arr.get(i);
                }

            }
            opind++;
            sc.nextLine();
        }
        return questions;
    }

    @Override
    public String toString() {
        return "Quiz{name='" + getName() + '\'' + ", questions=" + questions + '}';
    }

    public void start() throws FileNotFoundException {

        File file = new File(name);//2
//        Scanner sc = new Scanner(file);
//        Scanner UI = new Scanner(System.in);
//        double score = 0, fails = 0;
//
//        System.out.println("WELCOME TO \"" + file.getName() + "\" QUIZ!");
//        System.out.println("_______________________________________________");
//
//        while(sc.hasNextLine()) {
//            String description = sc.nextLine();
//            System.out.println(description);
//            if (description.contains("________")) {
//                FillIn fillIn = new FillIn();
//                fillIn.setDescription(description);
//                fillIn.setAnswer(sc.nextLine());
//
//                System.out.print("----------------------\n" + "Type your answer: ");
//                String UA = UI.nextLine();
//
//                if (UA.equals(fillIn.getAnswer())) {
//                    System.out.println("Correct!");
//                    System.out.println("_______________________________________________");
//                    score++;
//                } else {
//                    System.out.println("Incorrect!");
//                    fails++;
//                }
//            } else {
//                Test test = new Test();
//                test.setDescription(description);
//
//                String[] options = new String[1000];
//                for (int i = 0; i < 4; ++i) {
//                    options[i] = sc.nextLine();
//                }
//                test.setAnswer(options[0]);
//                test.setOptions(options);
//
//                for ( int i = 0 ; i < 4 ; ++i ){
//                    System.out.println((char)('A' + i) + ") " + test.getOptionAt(i));
//                }
//
//                System.out.print("----------------------\n" + "Enter the correct choice: ");
//                char UA = UI.nextLine().charAt(0);
//                int cind = UA - 'A';
//
//                while ( cind < 0 || cind > 3 ){
//                    System.out.print("Invalid choice! Try again (Ex: A, B, C, D): ");
//                    UA = UI.nextLine().charAt(0);
//                    cind = UA - 'A';
//                }
//
//                if (test.getOptionAt(cind).equals(test.getAnswer())) {
//                    System.out.println("Correct!");
//                    System.out.println("_______________________________________________");
//                    score++;
//                } else {
//                    System.out.println("Incorrect!");
//                    fails++;
//                }
//            }
//            sc.nextLine();
//        }
//        double ans = (100*score)/(fails+score);
//        System.out.println("Correct Answers: " + (int)score + "/" + (int)(fails+score) + "(" + ans + "%)");
    }
}
