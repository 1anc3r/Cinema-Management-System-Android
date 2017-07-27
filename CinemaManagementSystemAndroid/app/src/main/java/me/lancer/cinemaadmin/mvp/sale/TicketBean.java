package me.lancer.cinemaadmin.mvp.sale;

import me.lancer.cinemaadmin.mvp.schedule.ScheduleBean;
import me.lancer.cinemaadmin.mvp.seat.SeatBean;

/**
 * Created by HuangFangzhi on 2017/5/21.
 */

public class TicketBean {

    private int id;
    private int seatid;
    private int schedid;
    private double price;
    private int status;
    private String locktime;
    private SeatBean seat;
    private ScheduleBean sched;
    private String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeatid() {
        return seatid;
    }

    public void setSeatid(int seatid) {
        this.seatid = seatid;
    }

    public int getSchedid() {
        return schedid;
    }

    public void setSchedid(int schedid) {
        this.schedid = schedid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocktime() {
        return locktime;
    }

    public void setLocktime(String locktime) {
        this.locktime = locktime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SeatBean getSeat() {
        return seat;
    }

    public void setSeat(SeatBean seat) {
        this.seat = seat;
    }

    public ScheduleBean getSched() {
        return sched;
    }

    public void setSched(ScheduleBean sched) {
        this.sched = sched;
    }
}
