package me.lancer.cinemaguest.mvp.employee.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import me.lancer.cinemaguest.R;
import me.lancer.cinemaguest.mvp.base.activity.PresenterActivity;
import me.lancer.cinemaguest.mvp.employee.EmployeeBean;
import me.lancer.cinemaguest.mvp.employee.EmployeePresenter;
import me.lancer.cinemaguest.mvp.employee.IEmployeeView;
import me.lancer.cinemaguest.ui.activity.MainActivity;
import me.lancer.cinemaguest.ui.application.mApp;
import me.lancer.cinemaguest.ui.view.ClearEditText;
import me.lancer.cinemaguest.util.NetworkDiagnosis;

public class LoginActivity extends PresenterActivity<EmployeePresenter> implements IEmployeeView {

    mApp app;

    private LinearLayout llLogin;
    private ClearEditText cetUsername;
    private EditText etPassword;
    private Button btnLogin;
    private ProgressDialog pdLogin;
    private String id = "", access = "", username = "", password = "", number = "", address = "", tel = "", email = "", session;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pdLogin.dismiss();
                    break;
                case 1:
                    pdLogin.show();
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    showSnackbar(llLogin, (String) msg.obj);
                    break;
                case 3:
                    app.setSession((String) msg.obj);
                    session = (String) msg.obj;
                    new Thread(fetch).start();
                    break;
                case 4:
                    app.setEmployee(((List<EmployeeBean>) msg.obj).get(0));
                    sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    Runnable login = new Runnable() {
        @Override
        public void run() {
            presenter.login(username, password);
        }
    };

    Runnable fetch = new Runnable() {
        @Override
        public void run() {
            presenter.fetch(id, access, username, number, address, tel, email, session);
        }
    };

    @Override
    protected EmployeePresenter onCreatePresenter() {
        return new EmployeePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    public void initData() {
        app = (mApp) this.getApplication();
        sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
        if (username.length()>0&&password.length()>0){
            new Thread(login).start();
        }
    }

    public void initView() {
        llLogin = (LinearLayout) findViewById(R.id.ll_login);
        cetUsername = (ClearEditText) findViewById(R.id.cet_username);
        cetUsername.setText(username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPassword.setText(password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(vOnClickListener);
        pdLogin = new ProgressDialog(this);
        pdLogin.setMessage("正在登录...");
        pdLogin.setCancelable(false);
    }

    private View.OnClickListener vOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnLogin) {
                NetworkDiagnosis networkDiagnosis = new NetworkDiagnosis();
                if (networkDiagnosis.checkNetwork(getApplication())) {
                    if (cetUsername.getText().toString().equals("")) {
                        showSnackbar(llLogin, "账号昵称/号码不能为空!");
                    } else if (etPassword.getText().toString().equals("")) {
                        showSnackbar(llLogin, "账号密码不能为空!");
                    } else {
                        username = cetUsername.getText().toString();
                        password = etPassword.getText().toString();
                        if (!networkDiagnosis.checkNetwork(LoginActivity.this)) {
                            showSnackbar(llLogin, "网络连接错误!");
                        } else {
                            new Thread(login).start();
                        }
                    }
                }
            }
        }
    };

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
    public void showLogin(String result) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = result;
        handler.sendMessage(msg);
    }

    @Override
    public void showRegister(String result) {

    }

    @Override
    public void showFetch(List<EmployeeBean> list) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showModify(String result) {

    }

    @Override
    public void showDelete(String result) {

    }
}
