package me.lancer.cinemaguest.mvp.sale;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface ISalePresenter {

    void addSuccess(String result);

    void addFailure(String result);

    void fetchSuccess(List<SaleBean> list);

    void fetchFailure(String result);

    void modifySuccess(String result);

    void modifyFailure(String result);

    void deleteSuccess(String result);

    void deleteFailure(String result);
}