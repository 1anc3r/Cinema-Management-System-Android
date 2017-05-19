package me.lancer.cinemaadmin.mvp.play;

import java.util.List;

import me.lancer.cinemaadmin.mvp.base.IBaseView;
import me.lancer.cinemaadmin.mvp.schedule.ScheduleBean;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IPlayView extends IBaseView {

    void showAdd(String result);

    void showFetch(List<PlayBean> list);

    void showModify(String result);

    void showDelete(String result);
}
