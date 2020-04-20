package com.techuva.iot.ngt.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techuva.iot.ngt.R;

public class DevicesListViewHolder{
    public TextView tv_device_headers;
    public ImageView iv_device_icon;
    public ImageView iv_expand_list;
    public DevicesListViewHolder(View v) {
        this.tv_device_headers = v.findViewById(R.id.tv_device_headers);
        this.iv_device_icon = v.findViewById(R.id.iv_device_icon);
        this.iv_expand_list = v.findViewById(R.id.iv_expand_list);
    }

}