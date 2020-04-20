package com.techuva.iot.ngt.holders;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.iot.ngt.R;
import com.techuva.iot.ngt.views.TextViewIcomoon;

public class HexagonChannelRCVHolder extends RecyclerView.ViewHolder {
// View holder for gridview recycler view as we used in listview

public TextViewIcomoon tv_channel_icon;
public LinearLayout ll_main;

public HexagonChannelRCVHolder(View view) {
        super(view);
        this.tv_channel_icon =  view.findViewById(R.id.tv_channel_icon);
        this.ll_main =  view.findViewById(R.id.ll_main);
     }
}