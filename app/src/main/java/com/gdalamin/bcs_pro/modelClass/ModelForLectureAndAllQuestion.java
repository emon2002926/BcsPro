package com.gdalamin.bcs_pro.modelClass;

public class ModelForLectureAndAllQuestion {

    String lectureDetails,pdfLink,pdfDate,course;

    public ModelForLectureAndAllQuestion(){

    }
    public ModelForLectureAndAllQuestion(String lectureDetails, String pdfLink, String pdfDate,String course){

        this.lectureDetails = lectureDetails;
        this.pdfLink = pdfLink;
        this.pdfDate = pdfDate;
        this.course = course;
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
