package com.raymondqk.raysqlitepractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.model.PubInfo;

import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/12 0012.
 */
public class PubInfoLvAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PubInfo> mPubInfos;

    public PubInfoLvAdapter(Context context, List<PubInfo> pubInfos) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPubInfos = pubInfos;
    }

    @Override
    public int getCount() {
        return mPubInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mPubInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateListView(List<PubInfo> pubInfos) {
        mPubInfos = pubInfos;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.lv_item_pubinfo, null);
            viewHolder.tv_project = (TextView) convertView.findViewById(R.id.item_project_title);
            viewHolder.tv_brief = (TextView) convertView.findViewById(R.id.item_project_brief);
            viewHolder.tv_orgnization = (TextView) convertView.findViewById(R.id.item_orgnization);
            viewHolder.tv_publisher = (TextView) convertView.findViewById(R.id.item_publisher);
            viewHolder.tv_pubdate = (TextView) convertView.findViewById(R.id.item_pubdate);
            viewHolder.tv_deadline = (TextView) convertView.findViewById(R.id.item_deadline);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_project.setText(mPubInfos.get(position).getProject());
        viewHolder.tv_brief.setText(mPubInfos.get(position).getBrief());
        viewHolder.tv_orgnization.setText(mPubInfos.get(position).getOrgnization());
        viewHolder.tv_pubdate.setText(mPubInfos.get(position).getPubdate());
        viewHolder.tv_deadline.setText(mPubInfos.get(position).getDeadline());
        viewHolder.tv_publisher.setText(mPubInfos.get(position).getPublisher());
        return convertView;
    }

    class ViewHolder {
        TextView tv_project;
        TextView tv_brief;
        TextView tv_orgnization;
        TextView tv_publisher;
        TextView tv_pubdate;
        TextView tv_deadline;
    }
}
