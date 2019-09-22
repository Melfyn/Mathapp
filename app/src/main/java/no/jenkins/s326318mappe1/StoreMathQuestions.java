package no.jenkins.s326318mappe1;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreMathQuestions implements Serializable {
    private ArrayList<MathQuestion> mathQuestions;

    public StoreMathQuestions(ArrayList<MathQuestion> mathQuestions) {
        this.mathQuestions = mathQuestions;
    }

    public ArrayList<MathQuestion> getMathQuestions() {
        return mathQuestions;
    }
}
