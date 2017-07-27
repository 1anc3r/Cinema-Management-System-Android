package me.lancer.cinemaadmin.util;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by HuangFangzhi on 2016/12/14.
 */

public class ContentGetterSetter {

    public String url = "http://111.231.1.112/CinemaManagementSystem/";

//    public String url = "http://192.168.1.103:8080/Cinema-Management-System-Web/";

    public ContentGetterSetter() {
    }

    public String getContentFromHtml(String log,String url) {
        StringBuilder content = new StringBuilder();
        OkHttpClient client = new OkHttpClient();
        client.setFollowRedirects(false);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                BufferedReader reader = new BufferedReader(response.body().charStream());
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();
                Log.e(log, "获取成功!");
                return content.toString();
            } else {
                Log.e(log, "获取失败!状态码:" + response.code());
                return "获取失败!状态码:" + response.code();
            }
        } catch (IOException e) {
            Log.e(log, "获取失败!捕获异常:" + e.toString());
            return "获取失败!捕获异常:" + e.toString();
        }
    }
}
