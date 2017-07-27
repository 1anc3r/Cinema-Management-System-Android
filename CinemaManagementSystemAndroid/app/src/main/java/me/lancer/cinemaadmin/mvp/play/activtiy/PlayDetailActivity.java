package me.lancer.cinemaadmin.mvp.play.activtiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.base.activity.PresenterActivity;
import me.lancer.cinemaadmin.mvp.play.IPlayView;
import me.lancer.cinemaadmin.mvp.play.PlayBean;
import me.lancer.cinemaadmin.mvp.play.PlayPresenter;
import me.lancer.cinemaadmin.ui.application.mApp;
import me.lancer.cinemaadmin.ui.application.mParams;
import me.lancer.cinemaadmin.ui.view.htmltextview.HtmlHttpImageGetter;
import me.lancer.cinemaadmin.ui.view.htmltextview.HtmlTextView;
import me.lancer.cinemaadmin.util.LruImageCache;

public class PlayDetailActivity extends PresenterActivity<PlayPresenter> implements IPlayView {

    mApp app;

    private CollapsingToolbarLayout layout;
    private NetworkImageView ivImg;
    private HtmlTextView htvContent;
    private LoadToast loadToast;

    private RequestQueue mQueue;
    private int id;
    private String type = "", lang = "", name = "", introduction = "", img = "", price = "", status = "", session;


    private  Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    loadToast.error();
                    break;
                case 3:
                    if (msg.obj != null) {
                        loadToast.success();
                        PlayBean bean = (PlayBean) msg.obj;
                        layout.setTitle(bean.getName());
                        ViewCompat.setTransitionName(ivImg, mParams.TRANSITION_PIC);
                        LruImageCache cache = LruImageCache.instance();
                        ImageLoader loader = new ImageLoader(mQueue, cache);
                        ivImg.setImageUrl(bean.getPost(), loader);
                        if (bean.getIntroduction()!=null) {
                            htvContent.setHtml(bean.getIntroduction(), new HtmlHttpImageGetter(htvContent));
                        }
                    }
                    break;
            }
        }
    };

    private Runnable loadDetail = new Runnable() {
        @Override
        public void run() {
            presenter.fetch(String.valueOf(id), type, lang, name, introduction, img, price, status, session);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large);
        initData();
        initView();
    }

    private void initData() {
        app = (mApp) getApplication();
        session = app.getSession();
        mQueue = Volley.newRequestQueue(this);
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        img = getIntent().getStringExtra("img");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.t_large);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        layout = (CollapsingToolbarLayout) findViewById(R.id.ctl_large);
        layout.setTitle(name);
        ivImg = (NetworkImageView) findViewById(R.id.iv_img);
        ViewCompat.setTransitionName(ivImg, mParams.TRANSITION_PIC);
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
        ivImg.setImageUrl(img, loader);
        htvContent = (HtmlTextView) findViewById(R.id.htv_content);
        loadToast = new LoadToast(this);
        loadToast.setTranslationY(160);
        loadToast.setText("玩命加载中...");
        loadToast.show();
        new Thread(loadDetail).start();
    }

    public static void startActivity(Activity activity, int id, String name, String img, NetworkImageView networkImageView) {
        Intent intent = new Intent();
        intent.setClass(activity, PlayDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("img", img);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, networkImageView, mParams.TRANSITION_PIC);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        ivImg.destroyDrawingCache();
        htvContent.destroyDrawingCache();
        super.onDestroy();
    }

    @Override
    protected PlayPresenter onCreatePresenter() {
        return new PlayPresenter(this);
    }

    @Override
    public void showMsg(String log) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = log;
        handler.sendMessage(msg);
    }

    @Override
    public void showLoad() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    public void hideLoad() {
        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);
    }

    @Override
    public void showAdd(String result) {

    }

    @Override
    public void showFetch(List<PlayBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list.get(0);
        handler.sendMessage(msg);
    }

    @Override
    public void showModify(String result) {

    }

    @Override
    public void showDelete(String result) {

    }


    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }
}
