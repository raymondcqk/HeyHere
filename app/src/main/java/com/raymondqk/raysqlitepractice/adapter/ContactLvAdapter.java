package com.raymondqk.raysqlitepractice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.model.ContactDetail;

import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/17 0017.
 */
public class ContactLvAdapter extends BaseAdapter {

    private List<ContactDetail> mContactDetailList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ContactLvAdapter(Context context, List<ContactDetail> contactDetailList) {
        mContactDetailList = contactDetailList;
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mContactDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.lv_item_contact, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.lv_item_contact_name);
            viewHolder.identity = (TextView) convertView.findViewById(R.id.lv_item_contact_identity);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.lv_item_contact_phone);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.lv_item_contact_avatar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mContactDetailList.get(position).getUsername());
        if (TextUtils.isEmpty(mContactDetailList.get(position).getIdentity())){
            viewHolder.identity.setText("神秘秘黑衣人");
        }else {
            viewHolder.identity.setText(mContactDetailList.get(position).getIdentity());
        }

        viewHolder.phone.setText(mContactDetailList.get(position).getPhone());
        // TODO: 2016/8/17 0017 这里日后修改头像资源
        viewHolder.avatar.setImageResource(R.drawable.exit);
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView identity;
        TextView phone;
        ImageView avatar;
    }

    public void reflesh() {
        notifyDataSetChanged();
    }
}
