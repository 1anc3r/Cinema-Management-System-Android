package me.lancer.cinemaadmin.mvp.sale;

import java.util.List;

import me.lancer.cinemaadmin.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface ISaleView extends IBaseView {

    void showAdd(String result);

    void showFetch(List<SaleBean> list);

    void showModify(String result);

    void showDelete(String result);
}
