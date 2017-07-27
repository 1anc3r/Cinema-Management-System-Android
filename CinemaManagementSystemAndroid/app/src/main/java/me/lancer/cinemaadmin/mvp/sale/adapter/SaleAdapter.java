package me.lancer.cinemaadmin.mvp.sale.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.cinemaadmin.R;
import me.lancer.cinemaadmin.mvp.sale.SaleBean;
import me.lancer.cinemaadmin.mvp.sale.SaleItemBean;
import me.lancer.cinemaadmin.util.LruImageCache;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {

    private List<SaleBean> list;
    private RequestQueue mQueue;
    private Context context;

    public SaleAdapter(Context context, List<SaleBean> list) {
        this.context = context;
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.employee_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list != null && list.get(position) != null) {
            viewHolder.tvTitle.setText(list.get(position).getSaleItems().get(0).getTick().getSched().getPlay().getName());
            String content = "放映 : " + list.get(position).getSaleItems().get(0).getTick().getSched().getTime() + "\n影厅 : " + list.get(position).getSaleItems().get(0).getTick().getSched().getStud().getName() + " / 座位 : ";
            double price = 0;
            for (SaleItemBean item : list.get(position).getSaleItems()) {
                price += item.getPrice();
                content += item.getTick().getSeat().getRow() + "行" + item.getTick().getSeat().getCol() + "列 ";
            }
            if (list.get(position).getStatus() == 1) {
                content += "\n应付 : " + price + " / 实付 : " + list.get(position).getPayment() + " / 找零 : " + list.get(position).getChange();
            } else {
                content += "\n应退 : " + list.get(position).getPayment();
            }
            viewHolder.tvContent.setText(content);
            LruImageCache cache = LruImageCache.instance();
            ImageLoader loader = new ImageLoader(mQueue, cache);
            viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setImageUrl(list.get(position).getSaleItems().get(0).getTick().getSched().getPlay().getImg(), loader);
            viewHolder.cvMedimu.setOnClickListener(new View.OnClickListener() {
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

        public CardView cvMedimu;
        public NetworkImageView ivImg;
        public TextView tvTitle, tvContent;

        public ViewHolder(View rootView) {
            super(rootView);
            cvMedimu = (CardView) rootView.findViewById(R.id.cv_medimu);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        }
    }
}
