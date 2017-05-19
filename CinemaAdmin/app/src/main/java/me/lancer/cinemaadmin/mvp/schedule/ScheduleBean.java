package me.lancer.cinemaadmin.mvp.schedule;

import java.util.List;

import me.lancer.cinemaadmin.mvp.play.PlayBean;
import me.lancer.cinemaadmin.mvp.studio.StudioBean;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class ScheduleBean {

    private int id;
    private int studid;
    private int playid;
    private String time;
    private double price;
    private StudioBean stud;
    private PlayBean play;

    public ScheduleBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudid() {
        return studid;
    }

    public void setStudid(int studid) {
        this.studid = studid;
    }

    public int getPlayid() {
        return playid;
    }

    public void setPlayid(int playid) {
        this.playid = playid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public StudioBean getStud() {
        return stud;
    }

    public void setStud(StudioBean stud) {
        this.stud = stud;
    }

    public PlayBean getPlay() {
        return play;
    }

    public void setPlay(PlayBean play) {
        this.play = play;
    }
}
