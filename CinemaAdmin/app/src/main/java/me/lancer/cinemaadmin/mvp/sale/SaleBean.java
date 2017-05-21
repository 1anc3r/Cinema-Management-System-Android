package me.lancer.cinemaadmin.mvp.sale;

import java.util.List;

import me.lancer.cinemaadmin.mvp.employee.EmployeeBean;

/**
 * Created by HuangFangzhi on 2017/5/21.
 */

public class SaleBean {
    private int id;
    private int empid;
    private String saletime;
    private double payment;
    private double change;
    private int type;
    private int status;
    private EmployeeBean emp;
    private String result;
    private List<SaleItemBean> saleItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getSaletime() {
        return saletime;
    }

    public void setSaletime(String saletime) {
        this.saletime = saletime;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public EmployeeBean getEmp() {
        return emp;
    }

    public void setEmp(EmployeeBean emp) {
        this.emp = emp;
    }

    public List<SaleItemBean> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItemBean> saleItems) {
        this.saleItems = saleItems;
    }
}
