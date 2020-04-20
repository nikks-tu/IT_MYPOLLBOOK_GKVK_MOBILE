package com.techuva.iot.ngt.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.iot.ngt.R;

public class DayDatesRCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
// View holder for gridview recycler view as we used in listview
public TextView tv_day;
public TextView tv_date;
public View view_selected;




public DayDatesRCVHolder(View view) {
        super(view);
        // Find all views ids
        view.setClickable(true);

        this.tv_day =  view.findViewById(R.id.tv_day);
        this.tv_date =  view.findViewById(R.id.tv_date);
        this.view_selected =  view.findViewById(R.id.view_selected);
        }
        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();
        }


}