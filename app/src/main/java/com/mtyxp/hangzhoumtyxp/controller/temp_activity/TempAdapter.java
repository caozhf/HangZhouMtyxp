package com.mtyxp.hangzhoumtyxp.controller.temp_activity;

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
 * Created by CaoZhF on 2018-05-17.
 */

public class TempAdapter extends BaseAdapter {

    private List<Bashinai> mlist;
    private Context mContext;
    private LayoutInflater mInflate;

    public TempAdapter(Context context, List<Bashinai> mlist) {
        this.mContext = context;
        this.mlist = mlist;
        mInflate = LayoutInflater.from(context);
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
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflate.inflate(R.layout.express_fg_lv_item,viewGroup,false);
            viewHolder.express_fg_lv_item_pass = convertView.findViewById(R.id.express_fg_lv_item_pass);
            viewHolder.express_fg_lv_item_title =convertView.findViewById(R.id.express_fg_lv_item_title);
            viewHolder.express_fg_lv_item_number = convertView.findViewById(R.id.express_fg_lv_item_number);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Bashinai bashinai = mlist.get(position);

        viewHolder.express_fg_lv_item_pass.setText("第"+bashinai.getPass_num()+"货道");
        viewHolder.express_fg_lv_item_title.setText(bashinai.getBa_title()+"");
        viewHolder.express_fg_lv_item_number.setText(bashinai.getBa_num()+"");

        return convertView;
    }
    class ViewHolder{
        TextView express_fg_lv_item_pass;
        TextView express_fg_lv_item_title;
        TextView express_fg_lv_item_number;
    }

}
