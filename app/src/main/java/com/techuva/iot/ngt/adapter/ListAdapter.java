package com.techuva.iot.ngt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techuva.iot.ngt.R;
import com.techuva.iot.ngt.model.HistoryDataValueObject;

import java.util.List;

public class ListAdapter extends ArrayAdapter<HistoryDataValueObject> {

    private int resourceLayout;
    private Context mContext;

    public ListAdapter(Context context, int resource, List<HistoryDataValueObject> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.item_hr_channel_values, null);
        }


        HistoryDataValueObject p = getItem(position);

        if (p != null) {
            TextView channelValues = (TextView) v.findViewById(R.id.tv_history_channel_values);
            TextView tv_date_value = (TextView) v.findViewById(R.id.tv_date_value);
            TextView tv_time_value = (TextView) v.findViewById(R.id.tv_time_value);

            if (tv_time_value != null) {
                tv_time_value.setText(p.getTime());
            }

            if (tv_date_value != null) {
                tv_date_value.setText(p.getDate());
            }

            if (channelValues != null) {
                channelValues.setText(p.getValue());
            }
        }

        return v;
    }

}