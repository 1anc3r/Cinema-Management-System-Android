package me.lancer.cinemaguest.mvp.schedule;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaguest.mvp.play.PlayBean;
import me.lancer.cinemaguest.mvp.sale.TicketBean;
import me.lancer.cinemaguest.mvp.studio.SeatBean;
import me.lancer.cinemaguest.mvp.studio.StudioBean;
import me.lancer.cinemaguest.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class ScheduleModel {

    ISchedulePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String addUrl = contentGetterSetter.url+"schedule?method=add";
    String fetchUrl = contentGetterSetter.url+"schedule?method=fetch";
    String modifyUrl = contentGetterSetter.url+"schedule?method=modify";
    String deleteUrl = contentGetterSetter.url+"schedule?method=delete";
    String studioUrl = contentGetterSetter.url+"studio?method=fetch";
    String playUrl = contentGetterSetter.url+"play?method=fetch";
    String seatsUrl = contentGetterSetter.url+"seat?method=fetch";
    String ticksUrl = contentGetterSetter.url+"ticket?method=fetch";

    public ScheduleModel(ISchedulePresenter presenter) {
        this.presenter = presenter;
    }

    public void add(String studid, String playid, String time, String price, String session){
        String content = contentGetterSetter.getContentFromHtml("add", addUrl + "&studid=" + studid + "&playid=" + playid + "&time=" + time + "&price=" + price + "&session=" + session);
        String result = "";
        if (!content.contains("获取失败!")) {
            result = getResultFromContent(content);
            presenter.addSuccess(result);
        } else {
            presenter.addFailure(content);
            Log.e("add", content);
        }
    }

    public void fetch(String id, String studid, String playid, String time, String price, String session){
        String content = contentGetterSetter.getContentFromHtml("fetch", fetchUrl + "&id=" + id + "&studid=" + studid + "&playid=" + playid + "&time=" + time + "&price=" + price + "&session=" + session);
        List<ScheduleBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content, session);
            presenter.fetchSuccess(list);
        } else {
            presenter.fetchFailure(content);
            Log.e("fetch", content);
        }
    }

    public void modify(String id, String studid, String playid, String time, String price, String session){
        String content = contentGetterSetter.getContentFromHtml("modify", modifyUrl + "&id=" + id + "&studid=" + studid + "&playid=" + playid + "&time=" + time + "&price=" + price + "&session=" + session);
        String result = "";
        if (!content.contains("获取失败!")) {
            result = getResultFromContent(content);
            presenter.modifySuccess(result);
        } else {
            presenter.modifyFailure(content);
            Log.e("modify", content);
        }
    }

    public void delete(String id, String session) {
        String content = contentGetterSetter.getContentFromHtml("delete", deleteUrl + "&id=" + id + "&session=" + session);
        String result = "";
        if (!content.contains("获取失败!")) {
            result = getResultFromContent(content);
            presenter.deleteSuccess(result);
        } else {
            presenter.deleteFailure(content);
            Log.e("delete", content);
        }
    }

    public StudioBean studio(String stud, String session) {
        String content = contentGetterSetter.getContentFromHtml("studio", studioUrl + "&id=" + stud + "&session=" + session);
        List<StudioBean> list;
        if (!content.contains("获取失败!")) {
            list = getStudioListFromContent(content, session);
            if (list.size()>0) {
                return list.get(0);
            }else{
                return null;
            }
        } else {
            Log.e("studio", content);
            return null;
        }
    }

    public PlayBean play(String stud, String session) {
        String content = contentGetterSetter.getContentFromHtml("play", playUrl + "&id=" + stud + "&session=" + session);
        List<PlayBean> list;
        if (!content.contains("获取失败!")) {
            list = getPlayListFromContent(content);
            if (list.size()>0) {
                return list.get(0);
            }else{
                return null;
            }
        } else {
            Log.e("play", content);
            return null;
        }
    }

    public List<SeatBean> seats(String stud, String session) {
        String content = contentGetterSetter.getContentFromHtml("seatlist", seatsUrl + "&studid=" + stud + "&session=" + session);
        List<SeatBean> list;
        if (!content.contains("获取失败!")) {
            list = getSeatListFromContent(content);
            return list;
        } else {
            Log.e("seatlist", content);
            return null;
        }
    }

    public List<TicketBean> ticks(String schedid, String session, ScheduleBean sched) {
        String content = contentGetterSetter.getContentFromHtml("ticket", ticksUrl + "&schedid=" + schedid + "&session=" + session);
        List<TicketBean> list;
        if (!content.contains("获取失败!")) {
            list = getTicketListFromContent(content, session, sched);
            if (list.size()>0) {
                return list;
            }else{
                return null;
            }
        } else {
            Log.e("ticket", content);
            return null;
        }
    }

    private String getResultFromContent(String content) {
        try {
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            Log.e("code", String.valueOf(code));
            String result = jsonObj.getString("data");
            Log.e("result", result);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ScheduleBean> getListFromContent(String content, String session) {
        try {
            List<ScheduleBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    ScheduleBean bean = new ScheduleBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setStudid(jbItem.getInt("studioId"));
                    bean.setPlayid(jbItem.getInt("playId"));
                    bean.setTime(jbItem.getString("time"));
                    bean.setPrice(jbItem.getDouble("price"));
                    bean.setStud(studio(String.valueOf(bean.getStudid()), session));
                    bean.setPlay(play(String.valueOf(bean.getPlayid()), session));
                    bean.setTicks(ticks(String.valueOf(bean.getId()), session, bean));
                    list.add(bean);
                }
            } else {
                ScheduleBean bean = new ScheduleBean();
                bean.setTime(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<StudioBean> getStudioListFromContent(String content, String session) {
        try {
            List<StudioBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    StudioBean bean = new StudioBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setName(jbItem.getString("name"));
                    bean.setIntroduction(jbItem.getString("introduction"));
                    bean.setStatus(jbItem.getInt("studioFlag"));
                    bean.setRows(jbItem.getInt("rowCount"));
                    bean.setCols(jbItem.getInt("colCount"));
                    bean.setSeats(seats(String.valueOf(bean.getId()), session));
                    list.add(bean);
                }
            } else {
                StudioBean bean = new StudioBean();
                bean.setName(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<SeatBean> getSeatListFromContent(String content) {
        try {
            List<SeatBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    SeatBean bean = new SeatBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setStud(jbItem.getInt("studioId"));
                    bean.setStatus(jbItem.getInt("seatStatus"));
                    bean.setRow(jbItem.getInt("row"));
                    bean.setCol(jbItem.getInt("column"));
                    list.add(bean);
                }
            } else {
                SeatBean bean = new SeatBean();
                bean.setName(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<TicketBean> getTicketListFromContent(String content, String session, ScheduleBean sched) {
        try {
            List<TicketBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    TicketBean bean = new TicketBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setSeatid(jbItem.getInt("seatId"));
                    bean.setSeat(seats(String.valueOf(bean.getSeatid()), session).get(0));
                    bean.setSchedid(jbItem.getInt("scheduleId"));
                    bean.setSched(sched);
                    bean.setPrice(jbItem.getDouble("price"));
                    bean.setStatus(jbItem.getInt("status"));
                    bean.setLocktime(jbItem.getString("locked_time"));
                    list.add(bean);
                }
            } else {
                TicketBean bean = new TicketBean();
                bean.setResult(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<PlayBean> getPlayListFromContent(String content) {
        try {
            Log.e("play", content);
            List<PlayBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    PlayBean bean = new PlayBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setType(jbItem.getInt("typeId"));
                    bean.setLang(jbItem.getInt("langId"));
                    bean.setName(jbItem.getString("name"));
                    bean.setIntroduction(jbItem.getString("introduction").replace("\\n", "<br><strong>").replace(" : ", "</strong> : "));
                    if (jbItem.has("image")) {
                        bean.setImg(jbItem.getString("image"));
                    }else{
                        bean.setImg(jbItem.getString(""));
                    }
                    if (jbItem.has("post")) {
                        bean.setPost(jbItem.getString("post"));
                    }else{
                        bean.setPost(bean.getImg());
                    }
                    bean.setPrice(jbItem.getDouble("price"));
                    bean.setLength(jbItem.getInt("length"));
                    bean.setStatus(jbItem.getInt("status"));
                    list.add(bean);
                }
            } else {
                PlayBean bean = new PlayBean();
                bean.setName(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
