package me.lancer.cinemaguest.mvp.play;

import java.util.List;

import me.lancer.cinemaguest.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IPlayView extends IBaseView {

    void showAdd(String result);

    void showFetch(List<PlayBean> list);

    void showModify(String result);

    void showDelete(String result);
}
