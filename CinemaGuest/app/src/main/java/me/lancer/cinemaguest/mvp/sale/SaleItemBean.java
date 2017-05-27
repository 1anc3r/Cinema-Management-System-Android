package me.lancer.cinemaguest.mvp.sale;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class SaleItemBean {

    private int id;
    private int tickid;
    private int saleid;
    private double price;
    private String result;
    private TicketBean tick;

    public SaleItemBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTickid() {
        return tickid;
    }

    public void setTickid(int tickid) {
        this.tickid = tickid;
    }

    public int getSaleid() {
        return saleid;
    }

    public void setSaleid(int saleid) {
        this.saleid = saleid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TicketBean getTick() {
        return tick;
    }

    public void setTick(TicketBean tick) {
        this.tick = tick;
    }
}
