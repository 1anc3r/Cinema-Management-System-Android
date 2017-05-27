package me.lancer.cinemaguest.mvp.studio;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IStudioPresenter {

    void addSuccess(String result);

    void addFailure(String result);

    void fetchSuccess(List<StudioBean> list);

    void fetchFailure(String result);

    void modifySuccess(String result);

    void modifyFailure(String result);

    void deleteSuccess(String result);

    void deleteFailure(String result);
}