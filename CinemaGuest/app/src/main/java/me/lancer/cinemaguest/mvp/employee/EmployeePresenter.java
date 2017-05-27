package me.lancer.cinemaguest.mvp.employee;

import java.util.List;

import me.lancer.cinemaguest.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class EmployeePresenter implements IBasePresenter<IEmployeeView>, IEmployeePresenter {

    private IEmployeeView view;
    private EmployeeModel model;

    public EmployeePresenter(IEmployeeView view) {
        attachView(view);
        model = new EmployeeModel(this);
    }

    @Override
    public void attachView(IEmployeeView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void login(String username, String password) {
        if (view != null) {
            view.showLoad();
            model.login(username, password);
        }
    }

    @Override
    public void loginSuccess(String result) {
        if (view != null) {
            view.showLogin(result);
            view.hideLoad();
        }
    }

    @Override
    public void loginFailure(String result) {
        if (result != null && result.length() > 0 && view != null) {
            view.showMsg(result);
            view.hideLoad();
        }
    }

    public void register(String username, String password, String access, String number, String address, String tel, String email, String img) {
        if (view != null) {
            view.showLoad();
            model.register(username, password, access, number, address, tel, email, img);
        }
    }

    @Override
    public void registerSuccess(String result) {
        if (view != null) {
            view.showRegister(result);
            view.hideLoad();
        }
    }

    @Override
    public void registerFailure(String result) {
        if (result != null && result.length() > 0 && view != null) {
            view.showMsg(result);
            view.hideLoad();
        }
    }

    public void fetch(String id, String access, String username, String number, String address, String tel, String email, String session) {
        if (view != null) {
            view.showLoad();
            model.fetch(id, access, username, number, address, tel, email, session);
        }
    }

    @Override
    public void fetchSuccess(List<EmployeeBean> list) {
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

    public void modify(String id, String access, String username, String number, String password, String address, String tel, String email, String img, String session) {
        if (view != null) {
            view.showLoad();
            model.modify(id, access, username, number, password, address, tel, email, img, session);
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
