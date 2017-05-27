package me.lancer.cinemaguest.mvp.studio;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaguest.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class StudioModel {

    IStudioPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String addUrl = contentGetterSetter.url+"studio?method=add";
    String fetchUrl = contentGetterSetter.url+"studio?method=fetch";
    String modifyUrl = contentGetterSetter.url+"studio?method=modify";
    String deleteUrl = contentGetterSetter.url+"studio?method=delete";
    String seatsUrl = contentGetterSetter.url+"seat?method=fetch";

    public StudioModel(IStudioPresenter presenter) {
        this.presenter = presenter;
    }

    public void add(String name, String introduction, String status, String rows, String cols, String session){
        String content = contentGetterSetter.getContentFromHtml("add", addUrl + "&name=" + name + "&introduction=" + introduction + "&status=" + status + "&rows=" + rows + "&cols=" + cols + "&session=" + session);
        String result = "";
        if (!content.contains("获取失败!")) {
            result = getResultFromContent(content);
            presenter.addSuccess(result);
        } else {
            presenter.addFailure(content);
            Log.e("add", content);
        }
    }

    public void fetch(String id, String name, String introduction, String status, String rows, String cols, String session){
        String content = contentGetterSetter.getContentFromHtml("fetch", fetchUrl + "&id=" + id + "&name=" + name + "&introduction=" + introduction + "&status=" + status + "&rows=" + rows + "&cols=" + cols + "&session=" + session);
        List<StudioBean> list;
        Log.e("content", content);
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content, session);
            presenter.fetchSuccess(list);
        } else {
            presenter.fetchFailure(content);
            Log.e("fetch", content);
        }
    }

    public void modify(String id, String name, String introduction, String status, String rows, String cols, String session){
        String content = contentGetterSetter.getContentFromHtml("modify", modifyUrl + "&id=" + id + "&name=" + name + "&introduction=" + introduction + "&status=" + status + "&rows=" + rows + "&cols=" + cols + "&session=" + session);
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

    public List<SeatBean> seatlist(String stud, String session) {
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

    private List<StudioBean> getListFromContent(String content, String session) {
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
                    bean.setSeats(seatlist(String.valueOf(bean.getId()), session));
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
}
