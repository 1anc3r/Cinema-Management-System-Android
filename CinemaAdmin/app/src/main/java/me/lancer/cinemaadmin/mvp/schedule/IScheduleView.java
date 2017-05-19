package me.lancer.cinemaadmin.mvp.schedule;

import java.util.List;

import me.lancer.cinemaadmin.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IScheduleView extends IBaseView {

    void showAdd(String result);

    void showFetch(List<ScheduleBean> list);

    void showModify(String result);

    void showDelete(String result);
}
