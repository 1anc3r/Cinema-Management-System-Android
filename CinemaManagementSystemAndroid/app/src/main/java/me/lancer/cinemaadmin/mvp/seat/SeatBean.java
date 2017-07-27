package me.lancer.cinemaadmin.mvp.seat;

/**
 * Created by HuangFangzhi on 2017/5/16.
 */

public class SeatBean {
    private int id;
    private int stud;
    private int row;
    private int col;
    private int status;
    private String name;

    public SeatBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStud() {
        return stud;
    }

    public void setStud(int stud) {
        this.stud = stud;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
