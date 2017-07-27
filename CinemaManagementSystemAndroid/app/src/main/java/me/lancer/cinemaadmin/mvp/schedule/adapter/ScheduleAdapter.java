package me.lancer.cinemaadmin.mvp.schedule.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.base.activity.BaseActivity;
import me.lancer.cinemaadmin.mvp.schedule.ScheduleBean;
import me.lancer.cinemaadmin.mvp.seat.SeatBean;
import me.lancer.cinemaadmin.mvp.seat.adapter.SeatAdapter;
import me.lancer.cinemaadmin.ui.view.htmltextview.HtmlHttpImageGetter;
import me.lancer.cinemaadmin.ui.view.htmltextview.HtmlTextView;
import me.lancer.cinemaadmin.util.LruImageCache;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<ScheduleBean> list;
    private RequestQueue mQueue;
    private Context context;
    final int duration = 400;

    public ScheduleAdapter(Context context, List<ScheduleBean> list) {
        this.context = context;
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_item, viewGroup, false);
        DisplayMetrics dm = new DisplayMetrics();
        ((BaseActivity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.height = height - dp2px(128);
        v.setLayoutParams(lp);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final ScheduleBean item = list.get(position);
        final List<SeatBean> select = new ArrayList<>();
        if (item != null) {
            viewHolder.Name = item.getPlay().getName();
            LruImageCache cache = LruImageCache.instance();
            ImageLoader loader = new ImageLoader(mQueue, cache);
            viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setImageUrl(list.get(position).getPlay().getPost(), loader);
            viewHolder.cvShow0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.ishow) {
                        Hide(viewHolder);
                    } else {
                        Show(viewHolder);
                    }
                    viewHolder.ishow = !viewHolder.ishow;
                }
            });
            viewHolder.tvDone.setText(item.getPlay().getName());
            viewHolder.tvDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.mAdapter = new SeatAdapter(context, list.get(position).getStud().getSeats(), list.get(position).getTicks(), select);
                    viewHolder.rvSeats.setAdapter(viewHolder.mAdapter);
                }
            });
            viewHolder.tvDate.setText(item.getTime());
            viewHolder.tvPrice.setText("$" + item.getPrice());
            viewHolder.htvContent.setHtml(item.getPlay().getIntroduction(), new HtmlHttpImageGetter(viewHolder.htvContent));
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, list.get(position).getStud().getCols());
            viewHolder.rvSeats.setLayoutManager(mGridLayoutManager);
            viewHolder.rvSeats.setItemAnimator(new DefaultItemAnimator());
            viewHolder.rvSeats.setHasFixedSize(false);
            viewHolder.mAdapter = new SeatAdapter(this.context, list.get(position).getStud().getSeats(), list.get(position).getTicks(), select);
            viewHolder.mAdapter.setHasStableIds(true);
            viewHolder.rvSeats.setAdapter(viewHolder.mAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private void Hide(final ViewHolder viewHolder) {
        viewHolder.rvSeats.setVisibility(View.VISIBLE);
        viewHolder.tvDone.setText(viewHolder.Done);
        viewHolder.tvDate.setVisibility(View.GONE);
        viewHolder.tvPrice.setVisibility(View.GONE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(viewHolder.ivImg, "translationY", 0, dp2px(-73)),
                ObjectAnimator.ofFloat(viewHolder.ivImg, "rotationX", 0, -40),
                ObjectAnimator.ofFloat(viewHolder.ivImg, "scaleX", 1, 0.9f),
                ObjectAnimator.ofFloat(viewHolder.ivImg, "scaleY", 1, 0.0f),
                ObjectAnimator.ofFloat(viewHolder.rlShow0, "translationY", 0, viewHolder.rlShow0.getHeight() - dp2px(64)),
                ObjectAnimator.ofFloat(viewHolder.rlShow0, "translationX", 0, -(viewHolder.rlShow0.getWidth() * 0.55f)),
                ObjectAnimator.ofFloat(viewHolder.rlShow1, "translationY", 0, viewHolder.rlShow0.getHeight() - dp2px(64)),
                ObjectAnimator.ofFloat(viewHolder.rlShow1, "translationX", 0, viewHolder.rlShow0.getWidth() * 0.55f),
                ObjectAnimator.ofFloat(viewHolder.cvShow0, "scaleX", 1, 0.65f),
                ObjectAnimator.ofFloat(viewHolder.cvShow1, "scaleX", 1, 0.65f),
                ObjectAnimator.ofFloat(viewHolder.htvContent, "translationX", 0, dp2px(50)),
                ObjectAnimator.ofFloat(viewHolder.htvContent, "alpha", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(viewHolder.tvDone, "translationX", 0, dp2px(50)),
                ObjectAnimator.ofFloat(viewHolder.tvBack, "translationX", 0, -dp2px(50)),
                ObjectAnimator.ofFloat(viewHolder.tvDate, "translationX", 0, dp2px(200)),
                ObjectAnimator.ofFloat(viewHolder.tvPrice, "translationX", 0, dp2px(200)),
                ObjectAnimator.ofFloat(viewHolder.rvSeats, "scaleX", 1.3f, 1),
                ObjectAnimator.ofFloat(viewHolder.rvSeats, "scaleY", 1.3f, 1),
                ObjectAnimator.ofFloat(viewHolder.rvSeats, "alpha", 0.0f, 1.0f)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewHolder.vScreen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.setDuration(duration).start();
    }

    private void Show(final ViewHolder viewHolder) {
        viewHolder.vScreen.setVisibility(View.GONE);
        viewHolder.tvDone.setText(viewHolder.Name);
        viewHolder.tvDate.setVisibility(View.VISIBLE);
        viewHolder.tvPrice.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(viewHolder.ivImg, "translationY", dp2px(-73), 0),
                ObjectAnimator.ofFloat(viewHolder.ivImg, "rotationX", -40, 0),
                ObjectAnimator.ofFloat(viewHolder.ivImg, "scaleX", 0.9f, 1),
                ObjectAnimator.ofFloat(viewHolder.ivImg, "scaleY", 0.0f, 1),
                ObjectAnimator.ofFloat(viewHolder.rlShow0, "translationY", viewHolder.rlShow0.getHeight() - dp2px(64), 0),
                ObjectAnimator.ofFloat(viewHolder.rlShow0, "translationX", -(viewHolder.rlShow0.getWidth() * 0.55f), 0),
                ObjectAnimator.ofFloat(viewHolder.rlShow1, "translationY", viewHolder.rlShow0.getHeight() - dp2px(64), 0),
                ObjectAnimator.ofFloat(viewHolder.rlShow1, "translationX", viewHolder.rlShow0.getWidth() * 0.55f, 0),
                ObjectAnimator.ofFloat(viewHolder.cvShow0, "scaleX", 0.65f, 1),
                ObjectAnimator.ofFloat(viewHolder.cvShow1, "scaleX", 0.65f, 1),
                ObjectAnimator.ofFloat(viewHolder.htvContent, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(viewHolder.htvContent, "translationX", dp2px(50), 0),
                ObjectAnimator.ofFloat(viewHolder.tvDone, "translationX", dp2px(50), 0),
                ObjectAnimator.ofFloat(viewHolder.tvBack, "translationX", -dp2px(50), 0),
                ObjectAnimator.ofFloat(viewHolder.tvDate, "translationX", dp2px(200), 0),
                ObjectAnimator.ofFloat(viewHolder.tvPrice, "translationX", dp2px(200), 0),
                ObjectAnimator.ofFloat(viewHolder.rvSeats, "scaleX", 1, 1.3f),
                ObjectAnimator.ofFloat(viewHolder.rvSeats, "scaleY", 1, 1.3f),
                ObjectAnimator.ofFloat(viewHolder.rvSeats, "alpha", 1.0f, 0)
        );
        set.setDuration(duration).start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewHolder.rvSeats.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView ivImg;
        private View vScreen;
        private CardView cvShow0, cvShow1;
        private RelativeLayout rlShow0, rlShow1;
        private HtmlTextView htvContent;
        private TextView tvBack, tvDone, tvDate, tvPrice;
        private RecyclerView rvSeats;
        private SeatAdapter mAdapter;
        private String Name, Done = "成交";
        private boolean ishow = true;

        public ViewHolder(View rootView) {
            super(rootView);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            vScreen = rootView.findViewById(R.id.v_screen);
            cvShow0 = (CardView) rootView.findViewById(R.id.cv_show0);
            cvShow1 = (CardView) rootView.findViewById(R.id.cv_show1);
            rlShow0 = (RelativeLayout) rootView.findViewById(R.id.rl_show0);
            rlShow1 = (RelativeLayout) rootView.findViewById(R.id.rl_show1);
            tvBack = (TextView) rootView.findViewById(R.id.tv_back);
            tvDone = (TextView) rootView.findViewById(R.id.tv_done);
            htvContent = (HtmlTextView) rootView.findViewById(R.id.htv_content);
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvPrice = (TextView) rootView.findViewById(R.id.tv_price);
            rvSeats = (RecyclerView) rootView.findViewById(R.id.rv_seats);
        }
    }
}
