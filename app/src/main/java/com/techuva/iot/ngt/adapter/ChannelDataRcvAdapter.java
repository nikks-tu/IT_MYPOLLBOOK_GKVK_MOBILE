package com.techuva.iot.ngt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.iot.ngt.R;
import com.techuva.iot.ngt.holders.ChannelDataRCVHolder;
import com.techuva.iot.ngt.model.FullHistoryResultObject;

import java.util.List;

public class ChannelDataRcvAdapter extends RecyclerView.Adapter<ChannelDataRCVHolder>
{// Recyclerview will extend to
    // recyclerview adapter
    private List<FullHistoryResultObject> list;
    private Context context;
    private ChannelDataRCVHolder listHolder;

    public ChannelDataRcvAdapter(Context context, List<FullHistoryResultObject> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
       // return (null != arrayList ? arrayList.size() : 0);
        return list.size();

    }

    @Override
    public void onBindViewHolder(ChannelDataRCVHolder holder, int position) {
        FullHistoryResultObject model = list.get(position);

         holder.time.setText(model.getRECEIVEDTIME());
         holder.channel1.setText(model.get1());
         holder.channel3.setText(model.get3());
         holder.channel4.setText(model.get4());
         holder.channel5.setText(model.get5());
         holder.channel6.setText(model.get6());
         holder.channel7.setText(model.get7());
         holder.channel8.setText(model.get8());
       // mainHolder.imageview.setImageBitmap(image);
    }

    @Override
    public ChannelDataRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_hr_channel_data, viewGroup, false);

        listHolder = new ChannelDataRCVHolder(mainGroup);
        return listHolder;
    }


}