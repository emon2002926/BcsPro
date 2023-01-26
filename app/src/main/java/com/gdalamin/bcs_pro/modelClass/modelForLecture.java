package com.gdalamin.bcs_pro.modelClass;

public class modelForLecture {

    String lectureDetails,pdfLink,pdfDate;

    public modelForLecture(){

    }
    public modelForLecture(String lectureDetails,String pdfLink,String pdfDate){

        this.lectureDetails = lectureDetails;
        this.pdfLink = pdfLink;
        this.pdfDate = pdfDate;
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
