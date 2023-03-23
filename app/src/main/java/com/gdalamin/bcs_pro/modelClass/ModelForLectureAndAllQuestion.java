package com.gdalamin.bcs_pro.modelClass;

import androidx.annotation.Keep;

@Keep public class ModelForLectureAndAllQuestion {

    String lectureDetails,pdfLink,pdfDate,course,text,subjects,subjectCode;

    public ModelForLectureAndAllQuestion(){

    }
    public ModelForLectureAndAllQuestion(String lectureDetails, String pdfLink, String pdfDate
            ,String course,String text,String subjects,String subjectCode){

        this.lectureDetails = lectureDetails;
        this.pdfLink = pdfLink;
        this.pdfDate = pdfDate;
        this.course = course;
        this.text = text;
        this.subjects= subjects;
        this.subjectCode = subjectCode;
    }

    public String getSubjects() {
        return subjects;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLectureDetails() {
        return lectureDetails;
    }

    public void setLectureDetails(String lectureDetails) {
        this.lectureDetails = lectureDetails;
    }

    public String getPdfDate() {
        return pdfDate;
    }

    public void setPdfDate(String pdfDate) {
        this.pdfDate = pdfDate;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }
}
