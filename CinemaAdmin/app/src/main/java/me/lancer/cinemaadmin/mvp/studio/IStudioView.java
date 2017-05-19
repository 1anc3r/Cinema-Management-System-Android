package me.lancer.cinemaadmin.mvp.studio;

import java.util.List;

import me.lancer.cinemaadmin.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IStudioView extends IBaseView {

    void showAdd(String result);

    void showFetch(List<StudioBean> list);

    void showModify(String result);

    void showDelete(String result);
}
