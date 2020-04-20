package com.techuva.iot.ngt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.iot.ngt.R;
import com.techuva.iot.ngt.holders.DayDatesRCVHolder;
import com.techuva.iot.ngt.model.DateTimeObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DayDatesRcvAdapter extends RecyclerView.Adapter<DayDatesRCVHolder>
{// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<DateTimeObject> arrayList;
    private Context context;
    private DayDatesRCVHolder listHolder;
    private int selectedPosition = -1;

    public DayDatesRcvAdapter(Context context, ArrayList<DateTimeObject> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
       // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(DayDatesRCVHolder holder, int position) {
        DateTimeObject model = arrayList.get(position);

        SimpleDateFormat sdf4 = new SimpleDateFormat("dd");

       /* if(selectedPosition==position)
            holder.tv_date.setBackgroundColor(Color.parseColor("#000000"));
        else
            holder.tv_date.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setBackgroundColor(Color.parseColor("#000000"));

        holder.tv_date.setOnClickListener(v -> {
            selectedPosition=position;
            notifyItemChanged(selectedPosition );

        });*/

        holder.tv_day.setText(model.getDay());
        //Date date = model.getDate();

        holder.tv_date.setText(model.getOnlyDate());
        // mainHolder.imageview.setImageBitmap(image);
    }

    @Override
    public DayDatesRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_date_time, viewGroup, false);


        listHolder = new DayDatesRCVHolder(mainGroup);
        return listHolder;

    }


}