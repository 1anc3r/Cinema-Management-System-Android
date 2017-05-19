package me.lancer.cinemaadmin.mvp.play.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.base.activity.BaseActivity;
import me.lancer.cinemaadmin.mvp.play.PlayBean;
//import me.lancer.cinemaadmin.mvp.play.activity.PlayDetailActivity;
import me.lancer.cinemaadmin.util.LruImageCache;

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.ViewHolder> {

    private List<PlayBean> list;
    private RequestQueue mQueue;
    private Context context;

    public PlayAdapter(Context context, List<PlayBean> list) {
        this.context = context;
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.large_item, viewGroup, false);
//        DisplayMetrics dm = new DisplayMetrics();
//        ((BaseActivity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int height = dm.heightPixels;
//        ViewGroup.LayoutParams lp = v.getLayoutParams();
//        lp.height = (height - dp2px(128))/2;
//        v.setLayoutParams(lp);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            viewHolder.tvTitle.setText(list.get(position).getName());
            LruImageCache cache = LruImageCache.instance();
            ImageLoader loader = new ImageLoader(mQueue,cache);
            viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
            viewHolder.cvLarge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvLarge;
        public NetworkImageView ivImg;
        public TextView tvTitle;

        public ViewHolder(View rootView) {
            super(rootView);
            cvLarge = (CardView) rootView.findViewById(R.id.cv_large);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        }
    }
}
