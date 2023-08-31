package com.gdalamin.bcs_pro.modelClass;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "models")

@Keep public class model
{
    @PrimaryKey
    @NonNull
    String id;

   String question, option1, option2,option3,option4,text,answer,explanation,image,
           option1Image,option2Image,option3Image,option4Image,explanationImage,batch;

    public model() {
    }

    public model(String id, String question, String option1, String option2, String option3,String option4
            , String text,String answer,String explanation,String image,String option1Image,String option2Image,
                 String option3Image,String option4Image,String explanationImage,String batch) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.text = text;
        this.answer = answer;
        this.explanation = explanation;
        this.image = image;
        this.option1Image = option1Image;
        this.option2Image = option2Image;
        this.option3Image = option3Image;
        this.option4Image = option4Image;
        this.explanationImage = explanationImage;
        this.batch = batch;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOption1Image(String option1Image) {
        this.option1Image = option1Image;
    }

    public void setOption2Image(String option2Image) {
        this.option2Image = option2Image;
    }

    public void setOption3Image(String option3Image) {
        this.option3Image = option3Image;
    }

    public void setOption4Image(String option4Image) {
        this.option4Image = option4Image;
    }

    public void setExplanationImage(String explanationImage) {
        this.explanationImage = explanationImage;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBatch() {
        return batch;
    }

    public String getExplanationImage() {
        return explanationImage;
    }

    public String getOption1Image() {
        return option1Image;
    }

    public String getOption2Image() {
        return option2Image;
    }

    public String getOption3Image() {
        return option3Image;
    }

    public String getOption4Image() {
        return option4Image;
    }

    public String getImage() {
        return image;
    }


    public String getOption4() {
        return option4;
    }




    public String getExplanation() {
        return explanation;
    }


    public String getAnswer() {
        return answer;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

}
