package com.techuva.iot.ngt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.iot.ngt.R;
import com.techuva.iot.ngt.holders.ChannelValuesRCVHolder;
import com.techuva.iot.ngt.response_model.forwarningResultObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ChannelValuesRcvAdapter extends RecyclerView.Adapter<ChannelValuesRCVHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private List<forwarningResultObject> arrayList;
    private Context context;
    private ChannelValuesRCVHolder listHolder;

    public ChannelValuesRcvAdapter(Context context, List<forwarningResultObject> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
       // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ChannelValuesRCVHolder holder, int position) {
        forwarningResultObject model = arrayList.get(position);
        String value = String.valueOf(round(Double.parseDouble(model.getValue()), 2));
        holder.tv_channel_data.setText(value);
        holder.tv_channel_name.setText(model.getCHANNELLABEL());
       // holder.setIsRecyclable(false);

    }

    @Override
    public ChannelValuesRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_forwarning_channel_data, null, false);
        listHolder = new ChannelValuesRCVHolder(mainGroup);
        return listHolder;

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}