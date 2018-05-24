package com.mtyxp.hangzhoumtyxp.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.model.bean.Bashinai;

import java.util.List;

/**
 * Created by CaoZhF on 2018-05-19.
 */

public class RemainCheckAdapter extends BaseAdapter {

    private List<Bashinai> mlist;
    private Context mContext;
    private LayoutInflater mInflater;

    public RemainCheckAdapter(Context context, List<Bashinai> mlist) {
        this.mContext = context;
        this.mlist = mlist;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Bashinai bashinai = mlist.get(position);
        convertView = mInflater.inflate(R.layout.remain_check_item, viewGroup, false);
        TextView textView = convertView.findViewById(R.id.remain_item);
        textView.setText(bashinai.getBa_title());

//        ViewHolder viewHolder;
//        if (convertView == null){
//            viewHolder = new ViewHolder();
//            convertView = mInflater.inflate(R.layout.remain_check_item,null);
//            viewHolder.remain_item = convertView.findViewById(R.id.remain_item);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        Bashinai bashinai = mlist.get(position);
//        viewHolder.remain_item.setText(bashinai.getBa_title());

        return convertView;
    }

//    class ViewHolder{
//        TextView remain_item;
//    }

}
