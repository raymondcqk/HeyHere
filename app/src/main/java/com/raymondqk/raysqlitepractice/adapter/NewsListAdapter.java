package com.raymondqk.raysqlitepractice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.model.newsbean.ImageurlsBean;
import com.raymondqk.raysqlitepractice.model.newsbean.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;

    private List<NewsBean> newsList;

    private Context context;

    private OnClickListener onClickListener;

    public NewsListAdapter(Context context) {
        this.context = context;
        newsList = new ArrayList<>();
    }

    public void setNewsList(List<NewsBean> newsList) {
        this.newsList = newsList;
    }
    public void addNewsList(List<NewsBean> newsList){
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new NewsItemViewHolder(LayoutInflater.
                    from(parent.getContext()).inflate(R.layout.item_news_list, parent, false));
        } else {
            return new FooterViewHolder(LayoutInflater.
                    from(parent.getContext()).inflate(R.layout.news_list_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //因为有多个holder，所以要进行类型判断
        if (holder instanceof NewsItemViewHolder && position < newsList.size()) {

            ((NewsItemViewHolder) holder).title.setText(""+newsList.get(position).getTitle());
            ((NewsItemViewHolder) holder).source.setText("来源："+newsList.get(position).getSource());
            ((NewsItemViewHolder) holder).date.setText("发布："+newsList.get(position).getPubDate());

            // TODO: 2016/10/29 0029 完成图片加载

            if (newsList.get(position).isHavePic()) {
                ImageurlsBean imageurlsBean = newsList.get(position).getImageurls().get(0);
                ImageRequest imageRequest = new ImageRequest(imageurlsBean.getUrl(), new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ((NewsItemViewHolder) holder).img.setImageBitmap(response);
                    }
                }, imageurlsBean.getWidth(), imageurlsBean.getHeight(), Bitmap.Config.ARGB_8888, null);
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(imageRequest);
            }

            ((NewsItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(newsList.get(position));

                    }
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        if (newsList != null) {
            return newsList.size() + 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == newsList.size()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    private class NewsItemViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView title;
        TextView source;
        TextView date;
        ImageView img;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = (TextView) itemView.findViewById(R.id.tv_news_list_tiem_title);
            img = (ImageView) itemView.findViewById(R.id.img_news_list_img);
            source = (TextView) itemView.findViewById(R.id.tv_news_list_tiem_source);
            date = (TextView) itemView.findViewById(R.id.tv_news_list_tiem_pubdate);

        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(NewsBean newsBean);
    }
}
