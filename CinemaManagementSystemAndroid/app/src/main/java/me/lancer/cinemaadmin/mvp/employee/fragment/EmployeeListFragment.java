package me.lancer.cinemaadmin.mvp.employee.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.base.activity.PresenterFragment;
import me.lancer.cinemaadmin.mvp.employee.IEmployeeView;
import me.lancer.cinemaadmin.mvp.employee.EmployeeBean;
import me.lancer.cinemaadmin.mvp.employee.EmployeePresenter;
import me.lancer.cinemaadmin.mvp.employee.adapter.EmployeeAdapter;
import me.lancer.cinemaadmin.ui.activity.MainActivity;
import me.lancer.cinemaadmin.ui.application.mApp;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class EmployeeListFragment extends PresenterFragment<EmployeePresenter> implements IEmployeeView {

    mApp app;

    private Toolbar toolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private EmployeeAdapter mAdapter;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<EmployeeBean> mList = new ArrayList<>();

    private int last = 0;
    private String id = "", access = "", username = "", number = "", address = "", tel = "", email = "", session;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    break;
                case 3:
                    if (msg.obj != null) {
                        mList.clear();
                        mList.addAll((List<EmployeeBean>) msg.obj);
                        mAdapter = new EmployeeAdapter(getActivity(), mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable fetch = new Runnable() {
        @Override
        public void run() {
            presenter.fetch(id, access, username, number, address, tel, email, session);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (mApp) getActivity().getApplication();
        toolbar = (Toolbar) view.findViewById(R.id.t_large);
        toolbar.setTitle("人事");
        ((MainActivity) getActivity()).initDrawer(toolbar);
        initView(view);
        initData();
        inflateMenu();
        initSearchView();
    }

    private void initData() {
        session = app.getSession();
        new Thread(fetch).start();
    }

    private void initView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.teal, R.color.green, R.color.yellow, R.color.orange, R.color.red, R.color.pink, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessageDelayed(msg, 800);
//                flag = 0;
//                new Thread(loadLatest).start();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new EmployeeAdapter(getActivity(), mList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void inflateMenu() {
        toolbar.inflateMenu(R.menu.menu_search);
    }

    private void initSearchView() {
        final SearchView searchView = (SearchView) toolbar.getMenu()
                .findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint("搜索...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Fragment newfragment = new DiseaseFragment();
//                Bundle data = new Bundle();
//                data.putInt("what", 3);
//                data.putInt("obj", 0);
//                data.putString("name", query);
//                newfragment.setArguments(data);
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fl_main, newfragment).commit();
//                getActivity().invalidateOptionsMenu();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    protected EmployeePresenter onCreatePresenter() {
        return new EmployeePresenter(this);
    }

    @Override
    public void showLogin(String result) {

    }

    @Override
    public void showRegister(String result) {

    }

    @Override
    public void showFetch(List<EmployeeBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showModify(String result) {

    }

    @Override
    public void showDelete(String result) {

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
}

