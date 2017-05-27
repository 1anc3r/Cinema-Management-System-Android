package me.lancer.cinemaguest.mvp.play;

import java.util.List;

import me.lancer.cinemaguest.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class PlayPresenter implements IBasePresenter<IPlayView>, IPlayPresenter {

    private IPlayView view;
    private PlayModel model;

    public PlayPresenter(IPlayView view) {
        attachView(view);
        model = new PlayModel(this);
    }

    @Override
    public void attachView(IPlayView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void add(String type, String lang, String name, String introduction, String img, String price, String status, String session) {
        if (view != null) {
            view.showLoad();
            model.add(type, lang, name, introduction, img, price, status, session);
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

    public void fetch(String id, String type, String lang, String name, String introduction, String img, String price, String status, String session) {
        if (view != null) {
            view.showLoad();
            model.fetch(id, type, lang, name, introduction, img, price, status, session);
        }
    }

    @Override
    public void fetchSuccess(List<PlayBean> list) {
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

    public void modify(String id, String type, String lang, String name, String introduction, String img, String price, String status, String session) {
        if (view != null) {
            view.showLoad();
            model.modify(id, type, lang, name, introduction, img, price, status, session);
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
