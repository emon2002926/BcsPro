package com.gdalamin.bcs_pro.modelClass;

public class resultModel
{
   String total,correct,wrong,mark ,date;

    public resultModel() {
    }

    public resultModel(String total,String correct,String wrong,String mark,String date) {

        this.total = total;
        this.correct = correct;
        this.wrong = wrong;
        this.mark = mark;
        this.date = date;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
