package me.lancer.cinemaadmin.mvp.studio.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.studio.StudioBean;
import me.lancer.cinemaadmin.ui.view.cardstackview.CardStackView;
import me.lancer.cinemaadmin.ui.view.cardstackview.StackAdapter;

//import me.lancer.cinemaadmin.mvp.play.activity.PlayDetailActivity;

public class StudioAdapter extends StackAdapter<Integer> {

    private static List<StudioBean> list;

    public StudioAdapter(Context context, List<StudioBean> list) {
        super(context);
        this.list = list;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.studio_item) {
            view = getLayoutInflater().inflate(R.layout.studio_item, parent, false);
            return new ColorItemViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.studio_item;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView tvTitle, tvContent;
        RecyclerView rvSeats;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            tvTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            rvSeats = (RecyclerView) view.findViewById(R.id.rv_seats);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            tvTitle.setText(list.get(position).getName());
            tvContent.setText(list.get(position).getIntroduction());
            GridLayoutManager llm = new GridLayoutManager(getContext(), list.get(position).getCols());
            rvSeats.setLayoutManager(llm);
            rvSeats.setItemAnimator(new DefaultItemAnimator());
            rvSeats.setHasFixedSize(true);
            SeatAdapter adapter = new SeatAdapter(getContext(), list.get(position).getSeats());
            rvSeats.setAdapter(adapter);
        }
    }
}
