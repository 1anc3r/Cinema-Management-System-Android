package me.lancer.cinemaguest.mvp.studio;

import java.util.List;

import me.lancer.cinemaguest.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IStudioView extends IBaseView {

    void showAdd(String result);

    void showFetch(List<StudioBean> list);

    void showModify(String result);

    void showDelete(String result);
}
