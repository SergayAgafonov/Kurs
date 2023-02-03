package com.example.kyrsach;

public class DBtableZ {
    private Integer CodeID;
    private String DateZayv;
    private String CodeFIO;
    private String CodeContDat;
    private String CodeAdr;
    private String CodeNeispr;
    private String CodeStatus;

    public DBtableZ(Integer CodeID, String DateZayv, String CodeFIO, String CodeContDat, String CodeAdr, String CodeNeispr,String CodeStatus) {
        this.CodeID = CodeID;
        this.DateZayv = DateZayv;
        this.CodeFIO = CodeFIO;
        this.CodeContDat = CodeContDat;
        this.CodeAdr = CodeAdr;
        this.CodeNeispr = CodeNeispr;
        this.CodeStatus = CodeStatus;
    }
     public DBtableZ() {

     }

    public Integer getIDCode() {
        return CodeID;
    }

    public Integer getCodeID() {
        return CodeID;
    }

    public void setCodeID(Integer CodeID) {
        this.CodeID = CodeID;
    }

    public String getDateZayv() {
        return DateZayv;
    }

    public void setDateZayv(String DateZayv) {
        this.DateZayv = DateZayv;
    }

    public String getCodeFIO() {
        return CodeFIO;
    }

    public void setCodeFIO(String CodeFIO) {
        this.CodeFIO = CodeFIO;
    }

    public String getCodeContDat() {
        return CodeContDat;
    }

    public void setCodeContDat(String CodeContDat) {
        this.CodeContDat = CodeContDat;
    }

    public String getCodeAdr() {
        return CodeAdr;
    }

    public void setCodeAdr(String CodeAdr) {
        this.CodeAdr = CodeAdr;
    }

    public String getCodeNeispr() {
        return CodeNeispr;
    }

    public void setCodeNeispr(String CodeNeispr) {
        this.CodeNeispr = CodeNeispr;
    }

    public String getCodeStatus() {
        return CodeStatus;
    }

    public void setCodeStatus(String CodeStatus) {
        this.CodeStatus = CodeStatus;
    }
}
