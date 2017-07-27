package me.lancer.cinemaadmin.mvp.employee;

import java.util.List;

import me.lancer.cinemaadmin.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IEmployeeView extends IBaseView {

    void showLogin(String result);

    void showRegister(String result);

    void showFetch(List<EmployeeBean> list);

    void showModify(String result);

    void showDelete(String result);
}
