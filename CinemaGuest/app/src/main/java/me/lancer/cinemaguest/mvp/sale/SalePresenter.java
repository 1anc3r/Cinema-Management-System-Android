package me.lancer.cinemaguest.mvp.sale;

import java.util.List;

import me.lancer.cinemaguest.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class SalePresenter implements IBasePresenter<ISaleView>, ISalePresenter {

    private ISaleView view;
    private SaleModel model;

    public SalePresenter(ISaleView view) {
        attachView(view);
        model = new SaleModel(this);
    }

    @Override
    public void attachView(ISaleView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void add(String tickid, String saleid, String price, String session) {
        if (view != null) {
            view.showLoad();
            model.add(tickid, saleid, price, session);
        }
    }

    @Override
    public void addSuccess(String result) {
        if (view != null) {
            view.showAdd(result);
            view.hideLoad();
        }
    }

    @Override
    public void addFailure(String result) {
        if (result != null && result.length() > 0 && view != null) {
            view.showMsg(result);
            view.hideLoad();
        }
    }

    public void fetch(String id, String empid, String time, String payment, String change, String type, String status, String session) {
        if (view != null) {
            view.showLoad();
            model.fetch(id, empid, time, payment, change, type, status, session);
        }
    }

    @Override
    public void fetchSuccess(List<SaleBean> list) {
        if (view != null) {
            view.showFetch(list);
            view.hideLoad();
        }
    }

    @Override
    public void fetchFailure(String result) {
        if (result != null && result.length() > 0 && view != null) {
            view.showMsg(result);
            view.hideLoad();
        }
    }

    public void modify(String id, String tickid, String saleid, String price, String session) {
        if (view != null) {
            view.showLoad();
            model.modify(id, tickid, saleid, price, session);
        }
    }

    @Override
    public void modifySuccess(String result) {
        if (view != null) {
            view.showModify(result);
            view.hideLoad();
        }
    }

    @Override
    public void modifyFailure(String result) {
        if (result != null && result.length() > 0 && view != null) {
            view.showMsg(result);
            view.hideLoad();
        }
    }

    public void delete(String id, String session) {
        if (view != null) {
            view.showLoad();
            model.delete(id, session);
        }
    }

    @Override
    public void deleteSuccess(String result) {
        if (view != null) {
            view.showDelete(result);
            view.hideLoad();
        }
    }

    @Override
    public void deleteFailure(String result) {
        if (result != null && result.length() > 0 && view != null) {
            view.showMsg(result);
            view.hideLoad();
        }
    }
}
