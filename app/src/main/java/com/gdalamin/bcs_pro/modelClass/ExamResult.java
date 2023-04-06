package com.gdalamin.bcs_pro.modelClass;

public class ExamResult {
    private String total,totalIA,totalBA;
    private String correct,correctIA,correctBA;
    private String wrong,wrongIA,wrongBA;
    private String mark,marksIA,markBA;
    private String userId;
    private String date;

    public ExamResult() {
        // Default constructor
        this.total = "";
        this.correct = "";
        this.wrong = "";
        this.mark = "";
        this.userId = "";
        this.date = "";

        this.totalIA = "";
        this.correctIA = "";
        this.wrongIA = "";
        this.marksIA= "";

        this.totalBA = "";
        this.correctBA = "";
        this.wrongBA = "";
        this.markBA = "";

    }

    public ExamResult(String total, String correct, String wrong, String mark
            , String userId, String date,String totalIA,String correctIA,String wrongIA,String marksIA, String totalBA,String correctBA
    ,String wrongBA, String markBA) {
        this.total = total;
        this.correct = correct;
        this.wrong = wrong;
        this.mark = mark;
        this.userId = userId;
        this.date = date;

        this.totalIA = totalIA;
        this.correctIA = correctIA;
        this.wrongIA = wrongIA;
        this.marksIA = marksIA;

        this.totalBA = totalBA;
        this.correctBA = correctBA;
        this.wrongBA = wrongBA;
        this.markBA = markBA;
    }

    public String getTotalBA() {
        return totalBA;
    }

    public void setTotalBA(String totalBA) {
        this.totalBA = totalBA;
    }

    public String getCorrectBA() {
        return correctBA;
    }

    public void setCorrectBA(String correctBA) {
        this.correctBA = correctBA;
    }

    public String getWrongBA() {
        return wrongBA;
    }

    public void setWrongBA(String wrongBA) {
        this.wrongBA = wrongBA;
    }

    public String getMarkBA() {
        return markBA;
    }

    public void setMarkBA(String markBA) {
        this.markBA = markBA;
    }

    public String getMarksIA() {
        return marksIA;
    }

    public void setMarksIA(String marksIA) {
        this.marksIA = marksIA;
    }

    public String getWrongIA() {
        return wrongIA;
    }

    public void setWrongIA(String wrongIA) {
        this.wrongIA = wrongIA;
    }

    public String getCorrectIA() {
        return correctIA;
    }

    public void setCorrectIA(String correctIA) {
        this.correctIA = correctIA;
    }

    public String getTotalIA() {
        return totalIA;
    }

    public void setTotalIA(String totalIA) {
        this.totalIA = totalIA;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
