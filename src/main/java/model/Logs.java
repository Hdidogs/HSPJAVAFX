package model;

import java.util.Date;

public class Logs {
    private int id;
    private int refUser;
    private int refAction;
    private Date date;

    public Logs(int id, int refUser, int refAction, Date date) {
        this.id = id;
        this.refUser = refUser;
        this.refAction = refAction;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRefUser() {
        return refUser;
    }

    public void setRefUser(int refUser) {
        this.refUser = refUser;
    }

    public int getRefAction() {
        return refAction;
    }

    public void setRefAction(int refAction) {
        this.refAction = refAction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
