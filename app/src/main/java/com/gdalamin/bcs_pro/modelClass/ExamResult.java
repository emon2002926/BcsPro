package com.gdalamin.bcs_pro.modelClass;

public class ExamResult {
    private String total;
    private String correct;
    private String wrong;
    private String mark;
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
    }

    public ExamResult(String total, String correct, String wrong, String mark, String userId, String date) {
        this.total = total;
        this.correct = correct;
        this.wrong = wrong;
        this.mark = mark;
        this.userId = userId;
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
