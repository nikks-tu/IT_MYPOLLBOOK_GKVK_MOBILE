package com.techuva.iot.ngt.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.techuva.iot.ngt.R;
import com.techuva.iot.ngt.adapter.ChannelDataAdapter;
import com.techuva.iot.ngt.adapter.ChannelNamesRcvAdapter;
import com.techuva.iot.ngt.api_interface.HistoryDataInterface;
import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.listener.EndlessScrollListener;
import com.techuva.iot.ngt.model.HistoryDataInfoObject;
import com.techuva.iot.ngt.model.HistoryDataMainObject;
import com.techuva.iot.ngt.model.HistoryDataObject;
import com.techuva.iot.ngt.model.HistoryDataPostParamter;
import com.techuva.iot.ngt.model.HistoryDataResultObject;
import com.techuva.iot.ngt.model.HistoryDataValueObject;
import com.techuva.iot.ngt.model.HistoryViewPaginationObject;
import com.techuva.iot.ngt.views.MApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab_view_history extends AppCompatActivity {

    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    Context context;
    LinearLayout ll_day_view, ll_select_date, ll_tab_data_not_found, ll_headings;
    RelativeLayout ll_root_day_view;
    static TextView tv_to_date;
    TextView tv_from_date;
    TextView tv_date_heading;
    TextView tv_time_heading;
    TextView tv_value_heading;
    TextView tv_data_not_found;
    ImageView iv_to_date, iv_from_date;
    ChannelNamesRcvAdapter channelNamesAdapter;
    Boolean channelNames = false;
    Boolean dateChanged = false;
    Button btn_retry;
    RecyclerView rcv_day_data;
    HorizontalScrollView hv_list_channels;
    ListView channel_list;
    ListView lv_data;
    static String fromDate;
    static String toDate;
    String deviceId, pagePerCount;
    int pageNumber = 1;
    int selectedTabId = 1;
    RecyclerView rcv_channel_names;
    List<HistoryDataObject> list;
    List<HistoryDataObject> listforValues;
    TabLayout tabLayout;
    ArrayList<HistoryDataObject> arrayList = new ArrayList<>();
    ArrayList<HistoryDataObject> arrayListforValues = new ArrayList<>();
    List<HistoryDataValueObject> valuelist = new ArrayList<>();
    ArrayList<HistoryDataValueObject> valueObjectArrayList = new ArrayList<>();
    List<HistoryViewPaginationObject> getDataPageVise = new ArrayList<>();
    ChannelDataAdapter adapter;
    int TabId = 0;
    int listCount = 0;
    int day, year, month;
    Calendar calendar;
    List<HistoryDataMainObject> arrayInfo = new ArrayList<>();
    private boolean loading = true;
    private boolean tabChanged = false;
    HistoryDataInfoObject info;
    HistoryViewPaginationObject pageViseData;
    private EndlessScrollListener scrollListener;
    HistoryDataInterface service;
    Call<HistoryDataMainObject> call1;
    HistoryViewPaginationObject historyViewPaginationObject = new HistoryViewPaginationObject();
    int UserId;
    String authorityKey ="";
    String grantType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);
        init();
        serviceCall();
        generateTabNames();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        ll_select_date.setOnClickListener(v -> {
            if(month<10)
            {
                showDate(year, month+1, day);
            }
            else
                showDate(year, month, day);
            //serviceCallforChannelNames();
        });
    }

    private DatePickerDialog.OnDateSetListener myDateListener = (arg0, arg1, arg2, arg3) -> {
        // TODO Auto-generated method stub
        // arg1 = year
        // arg2 = month
        // arg3 = day
        showDate(arg1, arg2+1, arg3);
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    private void showDate(int year, final int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                String date = (year + "-" +(monthOfYear+1)  + "-" + dayOfMonth);
                String date2 = (dayOfMonth+ "-" +(monthOfYear+1)+ "-" +year);
                tv_to_date.setText(date2);
                fromDate = date;
                toDate = date;
                dateChanged = true;
                lv_data.setSelectionAfterHeaderView();
                if(dateChanged){
                  serviceCall();
                }

            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void init() {

        context = Tab_view_history.this;
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = new GregorianCalendar();
        //calendar.getTime();
        // fromDate = sdf4.format(calendar.getTime());
        info = new HistoryDataInfoObject();
        pageViseData = new HistoryViewPaginationObject();
        list = new ArrayList<>();
        listforValues = new ArrayList<>();
        fromDate = sdf4.format(calendar.getTime());
        toDate = fromDate;
        ll_day_view = findViewById(R.id.ll_day_view);
        ll_select_date = findViewById(R.id.ll_select_date);
        ll_tab_data_not_found = findViewById(R.id.ll_tab_data_not_found);
        ll_headings = findViewById(R.id.ll_headings);
        ll_root_day_view = findViewById(R.id.ll_root_day_view);
        tv_to_date = findViewById(R.id.tv_to_date);
        tv_from_date = findViewById(R.id.tv_from_date);
        iv_to_date = findViewById(R.id.iv_to_date);
        iv_from_date = findViewById(R.id.iv_from_date);
        rcv_day_data = findViewById(R.id.rcv_day_data);
        hv_list_channels = findViewById(R.id.hv_list_channels);
        channel_list = findViewById(R.id.channel_list);
        lv_data = findViewById(R.id.lv_data);
        rcv_channel_names = findViewById(R.id.rcv_channel_names);
        tv_date_heading = findViewById(R.id.tv_date_heading);
        tv_time_heading = findViewById(R.id.tv_time_heading);
        tv_value_heading = findViewById(R.id.tv_value_heading);
        tv_data_not_found = findViewById(R.id.tv_data_not_found);
        btn_retry = findViewById(R.id.btn_retry);
        deviceId = MApplication.getString(context, Constants.DeviceID);
        pagePerCount = "10";
        String date = sdf3.format(calendar.getTime());
        tv_to_date.setText(date);
        tabLayout = findViewById(R.id.tabs);
        authorityKey = "Bearer "+ MApplication.getString(context, Constants.AccessToken);
        grantType = Constants.GrantType;
        UserId = Integer.parseInt(MApplication.getString(context, Constants.UserID));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showLoaderNew() {
        runOnUiThread(new Tab_view_history.Runloader(getResources().getString(R.string.loading)));
    }

    class Runloader implements Runnable {
        private String strrMsg;

        public Runloader(String strMsg) {
            this.strrMsg = strMsg;
        }

        @SuppressWarnings("ResourceType")
        @Override
        public void run() {
            try {
                if (dialog == null)
                {
                    dialog = new Dialog(context,R.style.Theme_AppCompat_Light_DarkActionBar);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                }
                dialog.setContentView(R.layout.loading);
                dialog.setCancelable(false);

                if (dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                    dialog=null;
                }
                dialog.show();

                ImageView imgeView = (ImageView) dialog
                        .findViewById(R.id.imgeView);
                TextView tvLoading = (TextView) dialog
                        .findViewById(R.id.tvLoading);
                if (!strrMsg.equalsIgnoreCase(""))
                    tvLoading.setText(strrMsg);

                imgeView.setBackgroundResource(R.drawable.frame);

                animationDrawable = (AnimationDrawable) imgeView
                        .getBackground();
                imgeView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (animationDrawable != null)
                            animationDrawable.start();
                    }
                });
            } catch (Exception e)
            {

            }
        }
    }


    private void generateTabNames() {

        if(getDataPageVise.size()>0)
        {
            for (int i =0; i<getDataPageVise.size() ; i++)

            {
                String s = getDataPageVise.get(i).getChannelName();
                s = s.replaceAll("_", " ");
                // tabLayout.addTab(tabLayout.newTab().setText(arrayList.get(i).getLabel()));
                tabLayout.addTab(tabLayout.newTab().setText(s));

            }
        }
        TabLayout.Tab tab = tabLayout.getTabAt(selectedTabId-1);
        if (tab != null) {
            tab.select();
        }
    /*    tabLayout.setSelected(true);
        tabLayout.setScrollX(tabLayout  .getWidth());
        tabLayout.getTabAt(selectedTabId-1).select();
    */    new Handler().postDelayed(
                new Runnable() {
                    @Override public void run() {
                        tabLayout.getTabAt(selectedTabId-1).select();
                    }
                }, 100);
        channelNamesAdapter = new ChannelNamesRcvAdapter(context, arrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(false);
        rcv_channel_names.setLayoutManager(mLayoutManager);
        rcv_channel_names.setAdapter(channelNamesAdapter);
    }

    private void serviceCall(){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.LIVE_BASE_URL)
                .baseUrl(Constants.BASE_URL_TOKEN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(HistoryDataInterface .class);

        //call1 = service.getStringScalar(new HistoryDataPostParamter(deviceId, fromDate, toDate, pagePerCount, String.valueOf(pageNumber)));
        call1 = service.getStringScalarWithSession(UserId, authorityKey, new HistoryDataPostParamter(deviceId, fromDate, toDate, pagePerCount, String.valueOf(pageNumber)));
            call1.enqueue(new Callback<HistoryDataMainObject>() {
                @Override
                public void onResponse(Call<HistoryDataMainObject> call, Response<HistoryDataMainObject> response) {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )


                    if (response.code()==401)
                    {
                        MApplication.setBoolean(context, Constants.IsSessionExpired, true);
                        Intent intent = new Intent(context, TokenExpireActivity.class);
                        startActivity(intent);
                    }

                    else if(response.body()!=null){
                        hideloader();
                        if(response.body().getINFO().getErrorCode().equals(0))
                        {
                            listCount = response.body().getINFO().getListCount();
                            if(response.body().getRESULT()!=null)
                            {
                                ll_tab_data_not_found.setVisibility(View.GONE);
                                lv_data.setVisibility(View.VISIBLE);

                                temp(response.body().getRESULT());
                            }
                        }


                    }else {
                        lv_data.setVisibility(View.GONE);
                        ll_tab_data_not_found.setVisibility(View.VISIBLE);

                    }

                }

                @Override
                public void onFailure(Call<HistoryDataMainObject> call1, Throwable t) {
                    hideloader();
                /*lv_data.setVisibility(View.GONE);
                ll_tab_data_not_found.setVisibility(View.VISIBLE);*/
                }

            });
        }


    private void temp(HistoryDataResultObject result){
        for (int i=0; i<result.getData().size(); i++)
        {
            historyViewPaginationObject.setChannelName(result.getData().get(i).getLabel());
            historyViewPaginationObject.setChannelNumber(Integer.parseInt(result.getData().get(i).getChannelNumber()));
            historyViewPaginationObject.setDisplayValue(result.getData().get(i).getDisplayOrder());
            historyViewPaginationObject.setData(result.getData().get(i).getValues());
            getDataPageVise.add(historyViewPaginationObject);
        }
    }



    public void hideloader() {
        runOnUiThread(() -> {
            try
            {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }); }


    @Override
    protected void onResume() {
        //serviceCall();
        //lv_data.setOnScrollListener(scrollListener);
        super.onResume();

    }
}
