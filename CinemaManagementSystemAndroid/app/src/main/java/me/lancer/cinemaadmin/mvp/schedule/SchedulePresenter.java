package me.lancer.cinemaadmin.mvp.schedule;

import java.util.List;

import me.lancer.cinemaadmin.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class SchedulePresenter implements IBasePresenter<IScheduleView>, ISchedulePresenter {

    private IScheduleView view;
    private ScheduleModel model;

    public SchedulePresenter(IScheduleView view) {
        attachView(view);
        model = new ScheduleModel(this);
    }

    @Override
    public void attachView(IScheduleView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void add(String studid, String playid, String time, String price, String session) {
        if (view != null) {
            view.showLoad();
            model.add(studid, playid, time, price, session);
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

    public void fetch(String id, String studid, String playid, String time, String price, String session) {
        if (view != null) {
            view.showLoad();
            model.fetch(id, studid, playid, time, price, session);
        }
    }

    @Override
    public void fetchSuccess(List<ScheduleBean> list) {
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

    public void modify(String id, String studid, String playid, String time, String price, String session) {
        if (view != null) {
            view.showLoad();
            model.modify(id, studid, playid, time, price, session);
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
