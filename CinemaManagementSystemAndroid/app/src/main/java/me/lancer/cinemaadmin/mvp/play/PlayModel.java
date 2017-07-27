package me.lancer.cinemaadmin.mvp.play;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaadmin.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class PlayModel {

    IPlayPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String addUrl = contentGetterSetter.url+"play?method=add";
    String fetchUrl = contentGetterSetter.url+"play?method=fetch";
    String modifyUrl = contentGetterSetter.url+"play?method=modify";
    String deleteUrl = contentGetterSetter.url+"play?method=delete";

    public PlayModel(IPlayPresenter presenter) {
        this.presenter = presenter;
    }

    public void add(String type, String lang, String name, String introduction, String img, String price, String status, String session){
        String content = contentGetterSetter.getContentFromHtml("add", addUrl + "&type=" + type + "&lang=" + lang + "&name=" + name + "&introduction=" + introduction + "&img=" + img + "&price=" + price + "&status=" + status + "&session=" + session);
        String result = "";
        if (!content.contains("获取失败!")) {
            result = getResultFromContent(content);
            presenter.addSuccess(result);
        } else {
            presenter.addFailure(content);
            Log.e("add", content);
        }
    }

    public void fetch(String id, String type, String lang, String name, String introduction, String img, String price, String status, String session){
        String content = contentGetterSetter.getContentFromHtml("fetch", fetchUrl + "&id=" + id + "&type=" + type + "&lang=" + lang + "&name=" + name + "&introduction=" + introduction + "&img=" + img + "&price=" + price + "&status=" + status + "&session=" + session);
        List<PlayBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.fetchSuccess(list);
        } else {
            presenter.fetchFailure(content);
            Log.e("fetch", content);
        }
    }

    public void modify(String id, String type, String lang, String name, String introduction, String img, String price, String status, String session){
        String content = contentGetterSetter.getContentFromHtml("modify", modifyUrl + "&id=" + id + "&type=" + type + "&lang=" + lang + "&name=" + name + "&introduction=" + introduction + "&img=" + img + "&price=" + price + "&status=" + status + "&session=" + session);
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

    private List<PlayBean> getListFromContent(String content) {
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
                    bean.setImg(jbItem.getString("image"));
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
