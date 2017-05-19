package me.lancer.cinemaadmin.mvp.play;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IPlayPresenter {

    void addSuccess(String result);

    void addFailure(String result);

    void fetchSuccess(List<PlayBean> list);

    void fetchFailure(String result);

    void modifySuccess(String result);

    void modifyFailure(String result);

    void deleteSuccess(String result);

    void deleteFailure(String result);
}