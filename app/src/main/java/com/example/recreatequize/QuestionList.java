package com.example.recreatequize;

import java.io.Serializable;

public class QuestionList implements Serializable {

    private final String question, option1, option2, option3, option4 ;
    private final int answer;

    private int userSelecedAnswer;


    public QuestionList(String question, String option1, String option2, String option3, String option4, int answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.userSelecedAnswer = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getAnswer() {
        return answer;
    }

    public int getUserSelecedAnswer() {
        return userSelecedAnswer;
    }

    public void setUserSelecedAnswer(int userSelecedAnswer) {
        this.userSelecedAnswer = userSelecedAnswer;
    }
}
