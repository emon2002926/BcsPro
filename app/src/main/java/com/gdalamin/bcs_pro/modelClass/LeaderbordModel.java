package com.gdalamin.bcs_pro.modelClass;

import androidx.annotation.Keep;

@Keep public class LeaderbordModel {
    private String userId;
    private double averageMark;
    private int totalCorrect;
    private int totalWrong;
    private int totalNotAnswered;
    private int totalExamsTaken;
    private int totalQuestions;
    private String userName;

    private String userRank;

    private String base64ImageString;

    public LeaderbordModel(){

    }
    public LeaderbordModel(String userId, double averageMark, int totalCorrect, int totalWrong, int totalNotAnswered, int totalExamsTaken
            , String userName,String base64ImageString,int totalQuestions,String userRank) {
        this.userId = userId;
        this.averageMark = averageMark;
        this.totalCorrect = totalCorrect;
        this.totalWrong = totalWrong;
        this.totalNotAnswered = totalNotAnswered;
        this.totalExamsTaken = totalExamsTaken;
        this.userName = userName;
        this.base64ImageString = base64ImageString;
        this.totalQuestions = totalQuestions;
        this.userRank = userRank;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public int getTotalWrong() {
        return totalWrong;
    }

    public void setTotalWrong(int totalWrong) {
        this.totalWrong = totalWrong;
    }

    public int getTotalNotAnswered() {
        return totalNotAnswered;
    }

    public void setTotalNotAnswered(int totalNotAnswered) {
        this.totalNotAnswered = totalNotAnswered;
    }

    public int getTotalExamsTaken() {
        return totalExamsTaken;
    }

    public void setTotalExamsTaken(int totalExamsTaken) {
        this.totalExamsTaken = totalExamsTaken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBase64ImageString() {
        return base64ImageString;
    }

    public void setBase64ImageString(String base64ImageString) {
        this.base64ImageString = base64ImageString;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }
}
