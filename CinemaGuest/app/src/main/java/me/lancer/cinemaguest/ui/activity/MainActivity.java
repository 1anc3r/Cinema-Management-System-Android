package me.lancer.cinemaguest.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import me.lancer.cinemaguest.R;
import me.lancer.cinemaguest.mvp.base.activity.BaseActivity;
import me.lancer.cinemaguest.mvp.employee.activity.LoginActivity;
import me.lancer.cinemaguest.mvp.employee.fragment.EmployeeListFragment;
import me.lancer.cinemaguest.mvp.play.fragment.PlayListFragment;
import me.lancer.cinemaguest.mvp.sale.fragment.SaleListFragment;
import me.lancer.cinemaguest.mvp.schedule.fragment.ScheduleListFragment;
import me.lancer.cinemaguest.mvp.studio.fragment.StudioListFragment;
import me.lancer.cinemaguest.ui.application.mApp;
import me.lancer.cinemaguest.ui.application.mParams;
import me.lancer.cinemaguest.ui.view.CircleImageView;

public class MainActivity extends BaseActivity {

    private mApp app = new mApp();
    private RequestQueue queue;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int currentIndex;
    private long exitTime;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (mApp) this.getApplication();
        queue = Volley.newRequestQueue(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initNavigationViewHeader();
        initNavigationViewBottom();
        initFragment(savedInstanceState);
    }

    private void initNavigationViewBottom() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initFragment(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        currentFragment = new PlayListFragment();
        bundle.putInt(getString(R.string.index), 0);
        currentFragment.setArguments(bundle);
        switchContent(currentFragment);
    }

    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            actionBarDrawerToggle.syncState();
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        }
    }

    private void initNavigationViewHeader() {
        navigationView = (NavigationView) findViewById(R.id.nv_main);
        View view = navigationView.inflateHeaderView(R.layout.drawer_header);
        final CircleImageView civHead = (CircleImageView) view.findViewById(R.id.civ_head);
        ImageRequest imageRequest = new ImageRequest(
                app.getEmployee().getImg(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        civHead.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                civHead.setImageResource(R.mipmap.ic_huh);
            }
        });
        queue.add(imageRequest);
        civHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.apply();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        TextView tvHead = (TextView) view.findViewById(R.id.tv_head);
        String title = app.getEmployee().getName();
        if (app.getEmployee().getAccess() == 1){
            title += "(专员)";
        }else if (app.getEmployee().getAccess() == 2){
            title += "(主管)";
        }else if (app.getEmployee().getAccess() == 3){
            title += "(经理)";
        }
        tvHead.setText(title);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelected());
    }

    class NavigationItemSelected implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            Bundle bundle = new Bundle();
            drawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.navigation_item_1:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_bottom_1);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new PlayListFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_2:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_bottom_2);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new StudioListFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_3:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_bottom_3);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new ScheduleListFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_4:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_bottom_4);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new SaleListFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_5:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_bottom_5);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new EmployeeListFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_setting:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent().setClass(MainActivity.this, SettingActivity.class));
                            finish();
                        }
                    }, 180);
                    return true;
                default:
                    return true;
            }
        }
    }

    public void switchContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
        invalidateOptionsMenu();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(mParams.CURRENT_INDEX, currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_bottom_sheet_dialog:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast(this, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME){
            Log.e("HOME", "hello,world!");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.navigation_bottom_1:
                    currentFragment = new PlayListFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_bottom_2:
                    currentFragment = new StudioListFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_bottom_3:
                    currentFragment = new ScheduleListFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_bottom_4:
                    currentFragment = new SaleListFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_bottom_5:
                    currentFragment = new EmployeeListFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
            }
            return false;
        }

    };
}
