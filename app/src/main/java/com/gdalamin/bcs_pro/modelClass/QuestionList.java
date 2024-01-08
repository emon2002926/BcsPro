package com.gdalamin.bcs_pro.modelClass;

import androidx.annotation.Keep;

import java.io.Serializable;

 @Keep public class QuestionList implements Serializable {

    private final String question;
     private final String option1;
     private final String option2;
     private final String option3;
     private final String option4 ;
    private final String questionImageString;
     private final String option1ImageString;
     private final String option2ImageString;
     private final String option3ImageString;
     private final String option4ImageString;


    private final int answer;

    private int userSelecedAnswer;



    public QuestionList(String question, String option1, String option2, String option3
            , String option4,String questionImageString,String option1ImageString,String option2ImageString
            ,String option3ImageString,String option4ImageString, int answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.questionImageString = questionImageString;
        this.option1ImageString = option1ImageString;
        this.option2ImageString = option2ImageString;
        this.option3ImageString = option3ImageString;
        this.option4ImageString = option4ImageString;
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

     public String getQuestionImageString() {
         return questionImageString;
     }

     public String getOption1ImageString() {
         return option1ImageString;
     }

     public String getOption2ImageString() {
         return option2ImageString;
     }

     public String getOption3ImageString() {
         return option3ImageString;
     }

     public String getOption4ImageString() {
         return option4ImageString;
     }
 }
