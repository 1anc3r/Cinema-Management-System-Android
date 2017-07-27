package me.lancer.cinemaadmin.mvp.employee;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class EmployeeModel {

    IEmployeePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String loginUrl = contentGetterSetter.url + "login?";
    String registerUrl = contentGetterSetter.url + "register?method=add";
    String addUrl = contentGetterSetter.url + "employee?method=add";
    String fetchUrl = contentGetterSetter.url + "employee?method=fetch";
    String modifyUrl = contentGetterSetter.url + "employee?method=modify";
    String deleteUrl = contentGetterSetter.url + "employee?method=delete";

    public EmployeeModel(IEmployeePresenter presenter) {
        this.presenter = presenter;
    }

    public void login(String username, String password) {
        String content = contentGetterSetter.getContentFromHtml("login", loginUrl + "username=" + username + "&password=" + password);
        Log.e("url", loginUrl + "username=" + username + "&password=" + password);
        String result = null;
        if (!content.contains("获取失败!")) {
            result = getResultFromContent(content);
            if (result.contains("NULL")) {
                presenter.loginFailure("session为空!");
            } else {
                presenter.loginSuccess(result);
            }
        } else {
            presenter.loginFailure(content);
            Log.e("login", content);
        }
    }

    public void register(String username, String password, String access, String number, String address, String tel, String email, String img) {
        String content = contentGetterSetter.getContentFromHtml("register", registerUrl + "&username=" + username + "&password=" + password + "&access=" + access + "&number=" + number + "&address=" + address + "&tel=" + tel + "&email=" + email + "&img=" + img);
        String result = "";
        if (!content.contains("获取失败!")) {
            result = getResultFromContent(content);
            presenter.registerSuccess(result);
        } else {
            presenter.registerFailure(content);
            Log.e("register", content);
        }
    }

    public void fetch(String id, String access, String username, String number, String address, String tel, String email, String session) {
        String content = contentGetterSetter.getContentFromHtml("fetch", fetchUrl + "&id=" + id + "&access=" + access + "&username=" + username + "&number=" + number + "&address=" + address + "&tel=" + tel + "&email=" + email + "&session=" + session);
        List<EmployeeBean> list;
        Log.e("content", content);
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.fetchSuccess(list);
        } else {
            presenter.fetchFailure(content);
            Log.e("fetch", content);
        }
    }

    public void modify(String id, String access, String username, String number, String password, String address, String tel, String email, String img, String session) {
        String content = contentGetterSetter.getContentFromHtml("modify", modifyUrl + "&id=" + id + "&access=" + access + "&username=" + username + "&number=" + number + "&password=" + password + "&address=" + address + "&tel=" + tel + "&email=" + email + "&img=" + img + "&session=" + session);
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

    private List<EmployeeBean> getListFromContent(String content) {
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
}
