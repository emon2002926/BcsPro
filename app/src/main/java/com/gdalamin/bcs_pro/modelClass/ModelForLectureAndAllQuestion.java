package com.gdalamin.bcs_pro.modelClass;

import androidx.annotation.Keep;

@Keep public class ModelForLectureAndAllQuestion {

    String bcsYearName,dailyExam,subjects,subjectCode,totalQuestion;

    public ModelForLectureAndAllQuestion(){

    }
    public ModelForLectureAndAllQuestion(String bcsYearName,String dailyExam,String subjects,String subjectCode,String totalQuestion){

        this.bcsYearName = bcsYearName;
        this.dailyExam = dailyExam;
        this.subjects= subjects;
        this.subjectCode = subjectCode;
        this.totalQuestion = totalQuestion;
    }

    public String getBcsYearName() {
        return bcsYearName;
    }

    public void setBcsYearName(String bcsYearName) {
        this.bcsYearName = bcsYearName;
    }

    public String getDailyExam() {
        return dailyExam;
    }

    public void setDailyExam(String dailyExam) {
        this.dailyExam = dailyExam;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
}
