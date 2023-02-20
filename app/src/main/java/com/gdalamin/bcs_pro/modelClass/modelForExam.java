package com.gdalamin.bcs_pro.modelClass;

public class modelForExam {

    String dailyExam,details;
    int totalQc;

    public modelForExam(){

    }
    public modelForExam(String dailyExam,String details,int totalQc){
        this.dailyExam = dailyExam;
        this.details = details;
        this.totalQc = totalQc;


    }

    public int getTotalQc() {
        return totalQc;
    }

    public void setTotalQc(int totalQc) {
        this.totalQc = totalQc;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDailyExam() {
        return dailyExam;
    }

    public void setDailyExam(String dailyExam) {
        this.dailyExam = dailyExam;
    }
}
