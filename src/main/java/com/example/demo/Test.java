package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;

public class Test extends Question {
    private int NumOfOptions;
    private String[] options = new String[1000];
    ArrayList labels = new ArrayList();

    public void setOptions(String[] options) {
        ArrayList<String> arr = new ArrayList<>();
        for ( int i = 0 ; i < 4 ; ++i ){
            arr.add(options[i]);
        }
        Collections.shuffle(arr);
        for ( int i = 0 ; i < 4 ; ++i ){
            this.options[i] = arr.get(i);
        }
    }

    public String getOptionAt(int index) {
        return options[index];
    }

    @Override
    public String toString() {
        String desc = getDescription() + "\n";
        for ( int i = 1 ; i <= 4 ; ++i ){
            desc += getOptionAt(i) + "\n";
        }
        return desc;
    }

}
