package me.lancer.cinemaadmin.mvp.studio.fragment;

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
import me.lancer.cinemaadmin.mvp.studio.IStudioView;
import me.lancer.cinemaadmin.mvp.studio.StudioBean;
import me.lancer.cinemaadmin.mvp.studio.StudioPresenter;
import me.lancer.cinemaadmin.mvp.studio.adapter.StudioAdapter;
import me.lancer.cinemaadmin.ui.activity.MainActivity;
import me.lancer.cinemaadmin.ui.application.mApp;
import me.lancer.cinemaadmin.ui.view.cardstackview.CardStackView;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class StudioListFragment extends PresenterFragment<StudioPresenter> implements IStudioView {

    mApp app;

    private Toolbar toolbar;

    private CardStackView mCardStackView;
    private StudioAdapter mAdapter;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<StudioBean> mList = new ArrayList<>();

    private int last = 0;
    private String id = "", name = "", introduction = "", status = "", rows = "", cols = "", session;

    List<Integer> colorList = new ArrayList<>();
    public static Integer[] palette = new Integer[]{
            R.color.red,
            R.color.pink,
            R.color.purple,
            R.color.deeppurple,
            R.color.indigo,
            R.color.blue,
            R.color.lightblue,
            R.color.cyan,
            R.color.teal,
            R.color.green,
            R.color.lightgreen,
            R.color.lime,
            R.color.yellow,
            R.color.amber,
            R.color.orange,
            R.color.deeporange,
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    break;
                case 3:
                    if (msg.obj != null) {
                        mList.clear();
                        mList.addAll((List<StudioBean>) msg.obj);
                        mAdapter = new StudioAdapter(getActivity(), mList);
                        mCardStackView.setAdapter(mAdapter);
                        new Handler().postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.updateData(colorList);
                                    }
                                }, 200
                        );
                    }
                    break;
            }
        }
    };

    private Runnable fetch = new Runnable() {
        @Override
        public void run() {
            presenter.fetch(id, name, introduction, status, rows, cols, session);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_in_card, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (mApp) getActivity().getApplication();
        toolbar = (Toolbar) view.findViewById(R.id.t_large);
        toolbar.setTitle("影厅");
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

        mCardStackView = (CardStackView) view.findViewById(R.id.csv_stack);
        mCardStackView.setItemExpendListener(new CardStackView.ItemExpendListener() {
            @Override
            public void onItemExpend(boolean expend) {

            }
        });
        mAdapter = new StudioAdapter(getActivity(), mList);
        mCardStackView.setAdapter(mAdapter);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateData(colorList);
                    }
                }, 200
        );
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
    protected StudioPresenter onCreatePresenter() {
        return new StudioPresenter(this);
    }


    @Override
    public void showAdd(String result) {

    }

    @Override
    public void showFetch(List<StudioBean> list) {
        for (int i = 0; i < list.size(); i++) {
            colorList.add(palette[i%16]);
        }
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

