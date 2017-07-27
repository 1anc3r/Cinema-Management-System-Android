package me.lancer.cinemaadmin.mvp.employee;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IEmployeePresenter {

    void loginSuccess(String result);

    void loginFailure(String result);

    void registerSuccess(String result);

    void registerFailure(String result);

    void fetchSuccess(List<EmployeeBean> list);

    void fetchFailure(String result);

    void modifySuccess(String result);

    void modifyFailure(String result);

    void deleteSuccess(String result);

    void deleteFailure(String result);
}