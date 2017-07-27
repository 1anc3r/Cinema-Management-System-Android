package me.lancer.cinemaadmin.mvp.sale;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaadmin.mvp.employee.EmployeeBean;
import me.lancer.cinemaadmin.mvp.play.PlayBean;
import me.lancer.cinemaadmin.mvp.schedule.ScheduleBean;
import me.lancer.cinemaadmin.mvp.seat.SeatBean;
import me.lancer.cinemaadmin.mvp.studio.StudioBean;
import me.lancer.cinemaadmin.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class SaleModel {

    ISalePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String saleAddUrl = contentGetterSetter.url+"sale?method=add";
    String saleFetchUrl = contentGetterSetter.url+"sale?method=fetch";
    String saleModifyUrl = contentGetterSetter.url+"sale?method=modify";
    String saleDeleteUrl = contentGetterSetter.url+"sale?method=delete";
    String tickAddUrl = contentGetterSetter.url+"ticket?method=add";
    String saleItemAddUrl = contentGetterSetter.url+"saleItem?method=add";
    String saleItemFetchUrl = contentGetterSetter.url+"saleItem?method=fetch";
    String saleItemModifyUrl = contentGetterSetter.url+"saleItem?method=modify";
    String saleItemDeleteUrl = contentGetterSetter.url+"saleItem?method=delete";
    String tickFetchUrl = contentGetterSetter.url+"ticket?method=fetch";
    String tickModifyUrl = contentGetterSetter.url+"ticket?method=modify";
    String tickDeleteUrl = contentGetterSetter.url+"ticket?method=delete";
    String empFetchUrl = contentGetterSetter.url + "employee?method=fetch";
    String seatFetchUrl = contentGetterSetter.url+"seat?method=fetch";
    String schedFetchUrl = contentGetterSetter.url+"schedule?method=fetch";
    String studioFetchUrl = contentGetterSetter.url+"studio?method=fetch";
    String playFetchUrl = contentGetterSetter.url+"play?method=fetch";

    public SaleModel(ISalePresenter presenter) {
        this.presenter = presenter;
    }

    public void add(String tickid, String saleid, String price, String session){

    }

    public void fetch(String id, String empid, String time, String payment, String change, String type, String status, String session){
        String content = contentGetterSetter.getContentFromHtml("fetch", saleFetchUrl + "&id=" + id + "&empid=" + empid + "&time=" + time + "&payment=" + payment + "&change=" + change + "&type=" + type + "&status=" + status + "&session=" + session);
        List<SaleBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content, session);
            presenter.fetchSuccess(list);
        } else {
            presenter.fetchFailure(content);
            Log.e("fetch", content);
        }
    }

    public void modify(String id, String tickid, String saleid, String price, String session){

    }

    public void delete(String id, String session) {

    }

    public EmployeeBean emp(String empid, String session) {
        String content = contentGetterSetter.getContentFromHtml("emp", empFetchUrl + "&id=" + empid + "&session=" + session);
        List<EmployeeBean> list;
        if (!content.contains("获取失败!")) {
            list = getEmployeeListFromContent(content);
            if (list.size()>0) {
                return list.get(0);
            }else{
                return null;
            }
        } else {
            Log.e("emp", content);
            return null;
        }
    }

    public List<SaleItemBean> saleitem(String saleid, String session) {
        String content = contentGetterSetter.getContentFromHtml("saleitem", saleItemFetchUrl + "&saleid=" + saleid + "&session=" + session);
        List<SaleItemBean> list;
        if (!content.contains("获取失败!")) {
            list = getSaleItemListFromContent(content, session);
            if (list.size()>0) {
                return list;
            }else{
                return null;
            }
        } else {
            Log.e("saleitem", content);
            return null;
        }
    }

    public TicketBean tick(String tickid, String session) {
        String content = contentGetterSetter.getContentFromHtml("ticket", tickFetchUrl + "&id=" + tickid + "&session=" + session);
        List<TicketBean> list;
        if (!content.contains("获取失败!")) {
            list = getTicketListFromContent(content, session);
            if (list.size()>0) {
                return list.get(0);
            }else{
                return null;
            }
        } else {
            Log.e("ticket", content);
            return null;
        }
    }

    public List<SeatBean> seat(String seatid, String session) {
        String content = contentGetterSetter.getContentFromHtml("seat", seatFetchUrl + "&id=" + seatid + "&session=" + session);
        List<SeatBean> list;
        if (!content.contains("获取失败!")) {
            list = getSeatListFromContent(content);
            return list;
        } else {
            Log.e("seat", content);
            return null;
        }
    }

    public ScheduleBean sched(String schedid, String session) {
        String content = contentGetterSetter.getContentFromHtml("sched", schedFetchUrl + "&id=" + schedid + "&session=" + session);
        Log.e("url", schedFetchUrl + "&id=" + schedid + "&session=" + session);
        List<ScheduleBean> list;
        if (!content.contains("获取失败!")) {
            list = getScheduleListFromContent(content, session);
            for (ScheduleBean item : list){
                if (Integer.parseInt(schedid) == item.getId()){
                    return item;
                }
            }
            return null;
        } else {
            Log.e("sched", content);
            return null;
        }
    }

    public StudioBean studio(String stud, String session) {
        String content = contentGetterSetter.getContentFromHtml("studio", studioFetchUrl + "&id=" + stud + "&session=" + session);
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
        String content = contentGetterSetter.getContentFromHtml("play", playFetchUrl + "&id=" + stud + "&session=" + session);
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

    private String getResultFromContent(String content) {
        try {
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            String result = jsonObj.getString("data");
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<SaleBean> getListFromContent(String content, String session) {
        try {
            List<SaleBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    SaleBean bean = new SaleBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setEmpid(jbItem.getInt("empId"));
                    bean.setEmp(emp(String.valueOf(bean.getEmpid()), session));
                    bean.setSaletime(jbItem.getString("time"));
                    bean.setPayment(jbItem.getDouble("payment"));
                    bean.setChange(jbItem.getDouble("change"));
                    bean.setType(jbItem.getInt("type"));
                    bean.setStatus(jbItem.getInt("status"));
                    bean.setSaleItems(saleitem(String.valueOf(bean.getId()),session));
                    list.add(bean);
                }
            } else {
                SaleBean bean = new SaleBean();
                bean.setResult(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<SaleItemBean> getSaleItemListFromContent(String content, String session) {
        try {
            List<SaleItemBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    SaleItemBean bean = new SaleItemBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setTickid(jbItem.getInt("ticketId"));
                    bean.setSaleid(jbItem.getInt("saleId"));
                    bean.setPrice(jbItem.getDouble("price"));
                    bean.setTick(tick(String.valueOf(bean.getTickid()), session));
                    list.add(bean);
                }
            } else {
                SaleItemBean bean = new SaleItemBean();
                bean.setResult(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<EmployeeBean> getEmployeeListFromContent(String content) {
        try {
            List<EmployeeBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            int code = jsonObj.getInt("code");
            if (code == 0) {
                JSONArray jsonArr = jsonObj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    EmployeeBean bean = new EmployeeBean();
                    JSONObject jbItem = jsonArr.getJSONObject(i);
                    bean.setId(jbItem.getInt("id"));
                    bean.setAccess(jbItem.getInt("access"));
                    bean.setNumber(jbItem.getInt("no"));
                    bean.setName(jbItem.getString("name"));
                    bean.setPassword(jbItem.getString("password"));
                    bean.setAddress(jbItem.getString("addr"));
                    bean.setTel(jbItem.getString("tel"));
                    bean.setEmail(jbItem.getString("email"));
                    if (jbItem.has("image")) {
                        bean.setImg(jbItem.getString("image"));
                    }else{
                        bean.setImg("");
                    }
                    list.add(bean);
                }
            } else {
                EmployeeBean bean = new EmployeeBean();
                bean.setName(jsonObj.getString("data"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<TicketBean> getTicketListFromContent(String content, String session) {
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
                    bean.setSeat(seat(String.valueOf(bean.getSeatid()), session).get(0));
                    bean.setSchedid(jbItem.getInt("scheduleId"));
                    bean.setSched(sched(String.valueOf(bean.getSchedid()), session));
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

    private List<ScheduleBean> getScheduleListFromContent(String content, String session) {
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
                    bean.setSeats(seat(String.valueOf(bean.getId()), session));
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

    private List<PlayBean> getPlayListFromContent(String content) {
        try {
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
