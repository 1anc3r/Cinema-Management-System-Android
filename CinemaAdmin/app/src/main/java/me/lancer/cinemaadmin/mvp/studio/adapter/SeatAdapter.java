package me.lancer.cinemaadmin.mvp.studio.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.base.activity.BaseActivity;
import me.lancer.cinemaadmin.mvp.studio.SeatBean;
import me.lancer.cinemaadmin.mvp.studio.StudioBean;
import me.lancer.cinemaadmin.ui.view.ZoomLayout;

//import me.lancer.cinemaadmin.mvp.play.activity.PlayDetailActivity;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    private List<SeatBean> list;
    private Context context;

    public SeatAdapter(Context context, List<SeatBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seat_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            Drawable drawableZero = ContextCompat.getDrawable(context, R.mipmap.ic_event_seat_black_24dp);
            Drawable drawableOne;
            if (list.get(position).getStatus() == -1) {
                drawableOne = tintDrawable(drawableZero,ContextCompat.getColor(context, R.color.orange));
                viewHolder.ivSeat.setImageDrawable(drawableOne);
            } else if (list.get(position).getStatus() == 0) {
                viewHolder.ivSeat.setVisibility(View.INVISIBLE);
            } else if (list.get(position).getStatus() == 1) {
                drawableOne = tintDrawable(drawableZero,ContextCompat.getColor(context, R.color.blue));
                viewHolder.ivSeat.setImageDrawable(drawableOne);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static Drawable tintDrawable(Drawable drawable, int colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivSeat;

        public ViewHolder(View rootView) {
            super(rootView);
            ivSeat = (ImageView) rootView.findViewById(R.id.iv_seat);
        }
    }
}
