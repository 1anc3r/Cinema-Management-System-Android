package me.lancer.cinemaadmin.mvp.seat.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.sale.TicketBean;
import me.lancer.cinemaadmin.mvp.seat.SeatBean;

//import me.lancer.cinemaadmin.mvp.play.activity.PlayDetailActivity;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    private List<SeatBean> list;
    private List<TicketBean> ticks;
    private List<SeatBean> select;
    private Context context;

    public SeatAdapter(Context context, List<SeatBean> list) {
        this.context = context;
        this.list = list;
    }

    public SeatAdapter(Context context, List<SeatBean> list, List<TicketBean> ticks, List<SeatBean> select) {
        this.context = context;
        this.ticks = ticks;
        this.list = list;
        this.select = select;
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
                drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.orange));
                viewHolder.ivSeat.setImageDrawable(drawableOne);
            } else if (list.get(position).getStatus() == 0) {
                drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.card));
                viewHolder.ivSeat.setImageDrawable(drawableOne);
            } else if (list.get(position).getStatus() == 1) {
                if (ticks == null) {
                    drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.blue));
                } else {
                    drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.green));
                }
                viewHolder.ivSeat.setImageDrawable(drawableOne);
            }
            if (ticks != null) {
                for (int i = 0; i < ticks.size(); i++) {
                    if (ticks.get(i).getSeatid() == list.get(position).getId() && ticks.get(i).getStatus() == 9) {
                        drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.red));
                        viewHolder.ivSeat.setImageDrawable(drawableOne);
                    } else if (ticks.get(i).getSeatid() == list.get(position).getId() && ticks.get(i).getStatus() == 1) {
                        drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.yellow));
                        viewHolder.ivSeat.setImageDrawable(drawableOne);
                    }
                }
            }
            if (select != null){
                for (SeatBean item : select) {
                    if (item.getId() == list.get(position).getId()){
                        drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.red));
                        viewHolder.ivSeat.setImageDrawable(drawableOne);
                    }
                }
            }
            viewHolder.ivSeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ticks != null) {
                        Drawable drawableZero = ContextCompat.getDrawable(context, R.mipmap.ic_event_seat_black_24dp);
                        Drawable drawableOne = null;
                        for (int i = 0; i < ticks.size(); i++) {
                            if (ticks.get(i).getSeatid() == list.get(position).getId() && ticks.get(i).getStatus() == 1) {
                                select.remove(list.get(position));
                                drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.blue));
                                viewHolder.ivSeat.setImageDrawable(drawableOne);
                            } else {
                                select.add(list.get(position));
                                drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.yellow));
                                viewHolder.ivSeat.setImageDrawable(drawableOne);
                            }
                        }
                        viewHolder.ivSeat.setImageDrawable(drawableOne);
                    }
                }
            });
            viewHolder.ivSeat.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (ticks == null) {
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.seat_dialog, null);
                        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int pos, long id) {
                                Drawable drawableZero = ContextCompat.getDrawable(context, R.mipmap.ic_event_seat_black_24dp);
                                Drawable drawableOne = null;
                                if (pos == 0) {
                                    list.get(position).setStatus(1);
                                    drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.blue));
                                } else if (pos == 1) {
                                    list.get(position).setStatus(0);
                                    drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.card));
                                } else if (pos == 2) {
                                    drawableOne = tintDrawable(drawableZero, ContextCompat.getColor(context, R.color.orange));
                                    list.get(position).setStatus(-1);
                                }
                                viewHolder.ivSeat.setImageDrawable(drawableOne);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setView(dialogView);
                        Dialog dialog = builder.create();
                        dialog.show();
                    }
                    return false;
                }
            });
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
