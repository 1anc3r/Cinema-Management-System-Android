package me.lancer.cinemaguest.mvp.schedule;

import java.util.List;

import me.lancer.cinemaguest.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IScheduleView extends IBaseView {

    void showAdd(String result);

    void showFetch(List<ScheduleBean> list);

    void showModify(String result);

    void showDelete(String result);
}
