package com.gdalamin.bcs_pro.modelClass;

import androidx.annotation.Keep;

@Keep public class ExamResult {
    private String total,totalIA,totalBA,totalB,totalMAV,totalML,totalEL,totalG,totalMS,totalGS,totalICT;
    private String correct,correctIA,correctBA,correctB,correctMAV,correctML,correctEL,correctG,correctMS,correctGS,correctICT;
    private String wrong,wrongIA,wrongBA,wrongB,wrongMAV,wrongML,wrongEL,wrongG,wrongMS,wrongGS,wrongICT;
    private String mark,marksIA,marksBA,marksB,marksMAV,marksML,marksEL,marksG,marksMS,marksGS,marksICT;
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
        this.marksBA = "";

        this.totalB = "";
        this.correctB = "";
        this.wrongB = "";
        this.marksB = "";

        this.totalMAV = "";
        this.correctMAV = "";
        this.wrongMAV = "";
        this.marksMAV = "";

        this.totalML = "";
        this.correctML = "";
        this.wrongML = "";
        this.marksML = "";

        this.totalEL = "";
        this.correctEL = "";
        this.wrongEL = "";
        this.marksEL = "";

        this.totalMS = "";
        this.correctMS = "";
        this.wrongMS = "";
        this.marksMS = "";


        this.totalGS = "";
        this.correctGS = "";
        this.wrongGS = "";
        this.marksGS = "";

        this.totalICT = "";
        this.correctICT = "";
        this.wrongICT = "";
        this.marksICT = "";


    }

    public ExamResult(String total, String correct, String wrong, String mark
            , String userId, String date,String totalIA,String correctIA,String wrongIA,String marksIA, String totalBA,String correctBA
    ,String wrongBA, String markBA,String totalB,String correctB,String wrongB,String marksB,String totalMAV,String correctMAV
    ,String wrongMAV,String marksMAV,String totalML,String correctML,String wrongML,String marksML,String totalEL
    ,String correctEL,String wrongEL,String marksEL,String totalG,String correctG,String wrongG,String marksG,String totalMS
    ,String correctMS,String wrongMS,String marksMS,String totalGS,String correctGS,String wrongGS,String marksGS
    ,String totalICT,String correctICT, String wrongICT,String marksICT) {

        this.totalICT = totalICT;
        this.correctICT = correctICT;
        this.wrongICT = wrongICT;
        this.marksICT = marksICT;

        this.totalGS = totalGS;
        this.correctGS = correctGS;
        this.wrongGS = wrongGS;
        this.marksGS = marksGS;

        this.totalMS = totalMS;
        this.correctMS = correctMS;
        this.wrongMS = wrongMS;
        this.marksMS = marksMS;

        this.totalG = totalG;
        this.correctG = correctG;
        this.wrongG = wrongG;
        this.marksG = marksG;

        this.totalEL = totalEL;
        this.correctEL = correctEL;
        this.wrongEL = wrongEL;
        this.marksEL = marksEL;

        this.totalML = totalML;
        this.correctML = correctML;
        this.wrongML = wrongML;
        this.marksML = marksML;

        this.totalMAV = totalMAV;
        this.correctMAV = correctMAV;
        this.wrongMAV = wrongMAV;
        this.marksMAV = marksMAV;

        this.totalB = totalB;
        this.correctB = correctB;
        this.wrongB = wrongB;
        this.marksB = marksB;

        this.totalBA = totalBA;
        this.correctBA = correctBA;
        this.wrongBA = wrongBA;
        this.marksBA = markBA;

        this.totalIA = totalIA;
        this.correctIA = correctIA;
        this.wrongIA = wrongIA;
        this.marksIA = marksIA;

        this.total = total;
        this.correct = correct;
        this.wrong = wrong;
        this.mark = mark;
        this.userId = userId;
        this.date = date;

    }

    public String getMarksBA() {
        return marksBA;
    }

    public void setMarksBA(String marksBA) {
        this.marksBA = marksBA;
    }

    public String getTotalICT() {
        return totalICT;
    }

    public void setTotalICT(String totalICT) {
        this.totalICT = totalICT;
    }

    public String getCorrectICT() {
        return correctICT;
    }

    public void setCorrectICT(String correctICT) {
        this.correctICT = correctICT;
    }

    public String getWrongICT() {
        return wrongICT;
    }

    public void setWrongICT(String wrongICT) {
        this.wrongICT = wrongICT;
    }

    public String getMarksICT() {
        return marksICT;
    }

    public void setMarksICT(String marksICT) {
        this.marksICT = marksICT;
    }

    public String getTotalGS() {
        return totalGS;
    }

    public void setTotalGS(String totalGS) {
        this.totalGS = totalGS;
    }

    public String getCorrectGS() {
        return correctGS;
    }

    public void setCorrectGS(String correctGS) {
        this.correctGS = correctGS;
    }

    public String getWrongGS() {
        return wrongGS;
    }

    public void setWrongGS(String wrongGS) {
        this.wrongGS = wrongGS;
    }

    public String getMarksGS() {
        return marksGS;
    }

    public void setMarksGS(String marksGS) {
        this.marksGS = marksGS;
    }

    public String getTotalMS() {
        return totalMS;
    }

    public void setTotalMS(String totalMS) {
        this.totalMS = totalMS;
    }

    public String getCorrectMS() {
        return correctMS;
    }

    public void setCorrectMS(String correctMS) {
        this.correctMS = correctMS;
    }

    public String getWrongMS() {
        return wrongMS;
    }

    public void setWrongMS(String wrongMS) {
        this.wrongMS = wrongMS;
    }

    public String getMarksMS() {
        return marksMS;
    }

    public void setMarksMS(String marksMS) {
        this.marksMS = marksMS;
    }

    public String getTotalG() {
        return totalG;
    }

    public void setTotalG(String totalG) {
        this.totalG = totalG;
    }

    public String getCorrectG() {
        return correctG;
    }

    public void setCorrectG(String correctG) {
        this.correctG = correctG;
    }

    public String getWrongG() {
        return wrongG;
    }

    public void setWrongG(String wrongG) {
        this.wrongG = wrongG;
    }

    public String getMarksG() {
        return marksG;
    }

    public void setMarksG(String marksG) {
        this.marksG = marksG;
    }

    public String getTotalEL() {
        return totalEL;
    }

    public void setTotalEL(String totalEL) {
        this.totalEL = totalEL;
    }

    public String getCorrectEL() {
        return correctEL;
    }

    public void setCorrectEL(String correctEL) {
        this.correctEL = correctEL;
    }

    public String getWrongEL() {
        return wrongEL;
    }

    public void setWrongEL(String wrongEL) {
        this.wrongEL = wrongEL;
    }

    public String getMarksEL() {
        return marksEL;
    }

    public void setMarksEL(String marksEL) {
        this.marksEL = marksEL;
    }

    public String getTotalMAV() {
        return totalMAV;
    }

    public void setTotalMAV(String totalMAV) {
        this.totalMAV = totalMAV;
    }

    public String getTotalML() {
        return totalML;
    }

    public void setTotalML(String totalML) {
        this.totalML = totalML;
    }

    public String getCorrectMAV() {
        return correctMAV;
    }

    public void setCorrectMAV(String correctMAV) {
        this.correctMAV = correctMAV;
    }

    public String getCorrectML() {
        return correctML;
    }

    public void setCorrectML(String correctML) {
        this.correctML = correctML;
    }

    public String getWrongMAV() {
        return wrongMAV;
    }

    public void setWrongMAV(String wrongMAV) {
        this.wrongMAV = wrongMAV;
    }

    public String getWrongML() {
        return wrongML;
    }

    public void setWrongML(String wrongML) {
        this.wrongML = wrongML;
    }

    public String getMarksMAV() {
        return marksMAV;
    }

    public void setMarksMAV(String marksMAV) {
        this.marksMAV = marksMAV;
    }

    public String getMarksML() {
        return marksML;
    }

    public void setMarksML(String marksML) {
        this.marksML = marksML;
    }

    public String getTotalB() {
        return totalB;
    }

    public void setTotalB(String totalB) {
        this.totalB = totalB;
    }

    public String getCorrectB() {
        return correctB;
    }

    public void setCorrectB(String correctB) {
        this.correctB = correctB;
    }

    public String getWrongB() {
        return wrongB;
    }

    public void setWrongB(String wrongB) {
        this.wrongB = wrongB;
    }

    public String getMarksB() {
        return marksB;
    }

    public void setMarksB(String marksB) {
        this.marksB = marksB;
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
