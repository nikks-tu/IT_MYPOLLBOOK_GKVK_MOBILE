package com.techuva.iot.ngt.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.iot.ngt.R;


public class ChannelDataRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
// View holder for gridview recycler view as we used in listview
public TextView time;
public TextView channel1;
public TextView channel3;
public TextView channel4;
public TextView channel5;
public TextView channel6;
public TextView channel7;
public TextView channel8;




public ChannelDataRCVHolder(View view) {
        super(view);
        // Find all views ids

        this.time =  view.findViewById(R.id.tv_history_channel_data);
        this.channel1 =  view.findViewById(R.id.tv_channel_one);
        this.channel3 =  view.findViewById(R.id.tv_channel_three);
        this.channel4 =  view.findViewById(R.id.tv_channel_four);
        this.channel5 =  view.findViewById(R.id.tv_channel_five);
        this.channel6 =  view.findViewById(R.id.tv_channel_six);
        this.channel7 =  view.findViewById(R.id.tv_channel_seven);
        this.channel8 =  view.findViewById(R.id.tv_channel_eight);
        }

        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();
        }
}