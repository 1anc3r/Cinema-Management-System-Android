package me.lancer.cinemaadmin.mvp.employee.adapter;

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
import me.lancer.cinemaadmin.mvp.employee.EmployeeBean;
import me.lancer.cinemaadmin.util.LruImageCache;

//import me.lancer.cinemaadmin.mvp.play.activity.PlayDetailActivity;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<EmployeeBean> list;
    private RequestQueue mQueue;
    private Context context;

    public EmployeeAdapter(Context context, List<EmployeeBean> list) {
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
        if (list.get(position) != null) {
            String title = list.get(position).getName();
            if (list.get(position).getAccess() == 1){
                title += "(专员)";
            }else if (list.get(position).getAccess() == 2){
                title += "(主管)";
            }else if (list.get(position).getAccess() == 3){
                title += "(经理)";
            }
            viewHolder.tvTitle.setText(title);
            String content = "";
            content += "工号 : "+list.get(position).getNumber()+"\n";
            content += "电话 : "+list.get(position).getTel()+"\n";
            content += "邮箱 : "+list.get(position).getEmail();
            viewHolder.tvContent.setText(content);
            LruImageCache cache = LruImageCache.instance();
            ImageLoader loader = new ImageLoader(mQueue,cache);
            viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
            viewHolder.cvMedimu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
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
