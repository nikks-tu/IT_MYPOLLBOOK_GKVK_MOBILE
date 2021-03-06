package com.techuva.iot.ngt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.Shape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.techuva.iot.ngt.R;
import com.techuva.iot.ngt.adapter.DashboardTwoChannelRcvAdapter;
import com.techuva.iot.ngt.adapter.GridAdapterDashboard;
import com.techuva.iot.ngt.api_interface.CurrentDataInterface;
import com.techuva.iot.ngt.api_interface.LoginDataInterface;
import com.techuva.iot.ngt.api_interface.VersionInfoDataInterface;
import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.CurrentDataMainObject;
import com.techuva.iot.ngt.model.CurrentDataPostParameter;
import com.techuva.iot.ngt.model.CurrentDataResultObject;
import com.techuva.iot.ngt.model.CurrentDataValueObject;
import com.techuva.iot.ngt.model.LoginMainObject;
import com.techuva.iot.ngt.model.VersionInfoMainObject;
import com.techuva.iot.ngt.model.VersionInfoPostParameters;
import com.techuva.iot.ngt.views.MApplication;
import com.techuva.iot.ngt.views.MPreferences;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Dashboard_Two extends BaseActivity {

    LinearLayout ll_top_view, ll_bottom_view, ll_main;
    TextView tv_thermometer, tv_deviceName, tv_tempUnit;
    TextView tv_deviceName1, tv_deviceId, tv_last_sync, tv_last_sync_txt;
    Button btn_serverConnect, internetRetry;
    RecyclerView rcv_bottom_view;
    String deviceId, userId;
    Activity context;
    Intent intent;
    String inventoryId;
    String appVersion;
    String fontVersion;
    String FontUrl="";
    Toast exitToast;
    Boolean doubleBackToExitPressedOnce = true;

    private View decorView;
    RelativeLayout internetConnection, rl_serverError;
    ProgressDialog progressDialog;
    GridAdapterDashboard gridAdapterDashboard;
    DashboardTwoChannelRcvAdapter channelRcvAdapter;
    LinearLayout ll_refresh;
    public Dialog dialog;
    private AnimationDrawable animationDrawable;
    MPreferences mPreferences;
    int UserId;
    String authorityKey ="";
    String authorityKeyForRefresh ="";
    String grantType = "";
    String grantTypeRefresh = "";
    String refreshToken="";
    /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.chiller_dashboard_v_one);
        Init_Views();
     }*/
    private void setTypeface() {
        Typeface faceLight = Typeface.createFromAsset(getAssets(),
                "fonts/AvenirLTStd-Light.otf");
        Typeface roboto = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        Typeface faceMedium = Typeface.createFromAsset(getAssets(),
                "fonts/AvenirLTStd-Medium.otf");
        //tv_thermometer.setTypeface(faceBook);
        tv_deviceName1.setTypeface(roboto);
        tv_deviceId.setTypeface(roboto);
        tv_last_sync.setTypeface(roboto);
        tv_last_sync_txt.setTypeface(roboto);
       // tv_tempUnit.setTypeface(faceLight);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Init_Views() {
        context = Dashboard_Two.this;
        mPreferences = new MPreferences(context);
        llContent.addView(inflater.inflate(R.layout.chiller_dashboard_v_two, null), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        decorView = getWindow().getDecorView();
        rcv_bottom_view = findViewById(R.id.rcv_bottom_view);
        deviceId = MApplication.getString(context, Constants.DeviceID);
        userId = "1001";
        ll_refresh = findViewById(R.id.ll_refresh);
        ll_top_view = findViewById(R.id.ll_top_view);
        ll_bottom_view = findViewById(R.id.ll_bottom_view);
        ll_main = findViewById(R.id.ll_main);
        internetConnection =  findViewById(R.id.internetConnection);
        tv_thermometer = findViewById(R.id.tv_thermometer);
        tv_deviceName = findViewById(R.id.tv_deviceName);
        tv_tempUnit = findViewById(R.id.tv_tempUnit);
        tv_deviceName1 = findViewById(R.id.tv_deviceName1);
        tv_deviceId = findViewById(R.id.tv_deviceId);
        tv_last_sync = findViewById(R.id.tv_last_sync);
        tv_last_sync_txt = findViewById(R.id.tv_last_sync_txt);
        btn_serverConnect = findViewById(R.id.btn_serverConnect);
        internetRetry = findViewById(R.id.internetRetry);
        rl_serverError = findViewById(R.id.rl_serverError);
        inventoryId = MApplication.getString(context, Constants.InventoryTypeId);
        setTypeface();
        authorityKey = "Bearer "+ MApplication.getString(context, Constants.AccessToken);
        authorityKeyForRefresh = Constants.AuthorizationKey;
        grantType = Constants.GrantType;
        grantTypeRefresh = Constants.GrantTypeRefresh;
        refreshToken = MApplication.getString(context, Constants.RefreshToken);
        UserId = Integer.parseInt(MApplication.getString(context, Constants.UserID));
        exitToast = Toast.makeText(getApplicationContext(), R.string.exit_toast, Toast.LENGTH_SHORT); if(MApplication.isNetConnected(context))
        {
            internetConnection.setVisibility(View.GONE);
            ll_main.setVisibility(View.VISIBLE);
            appVersion = mPreferences.getStringFromPreference(Constants.AppVersion, "1");
            fontVersion = mPreferences.getStringFromPreference(Constants.FontVersion, "0");
            showLoaderNew();
            serviceCallforVersionInfo();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    serviceCall();
                }
            }, 1000);
        }
        else {
            internetConnection.setVisibility(View.VISIBLE);
            ll_main.setVisibility(View.GONE);
        }
        /*  grid_bottom.setOnItemClickListener((parent, view, position, id) -> {
            int selectedGridChannelNum = gridAdapterDashboard.getChannelNumber(position);
            Intent intent = new Intent(context, History_View_Activity.class);
            intent.putExtra("TabId", selectedGridChannelNum);
            startActivity(intent);});
        */

        ll_refresh.setOnClickListener(v -> {
            showLoaderNew();
            serviceCall();
        });

        btn_serverConnect.setOnClickListener(v -> serviceCall());

        internetRetry.setOnClickListener(v -> serviceCall());

        MApplication.setBoolean(context, Constants.IsHomeSelected, true);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Device ID", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        //String msg = getResources().getString(R.string.app_name, token);
                        Log.d("Device ID", token);
                       // Toast.makeText(Dashboard.this, "IOT" +token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void serviceCall(){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.LIVE_BASE_URL)
                .baseUrl(Constants.BASE_URL_TOKEN)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrentDataInterface service = retrofit.create(CurrentDataInterface .class);

        //Call<CurrentDataMainObject> call = service.getStringScalar(new CurrentDataPostParameter(deviceId,userId));
        Call<CurrentDataMainObject> call = service.getStringScalarWithSession(UserId, authorityKey, new CurrentDataPostParameter(deviceId,userId));
        call.enqueue(new Callback<CurrentDataMainObject>() {
            @Override
            public void onResponse(Call<CurrentDataMainObject> call, Response<CurrentDataMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideloader();
                if(response.body()!=null){
                   // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getResult()!=null)
                    {
                        try {
                            ll_main.setVisibility(View.VISIBLE);
                            ll_bottom_view.setVisibility(View.VISIBLE);
                            rcv_bottom_view.setVisibility(View.VISIBLE);
                            rl_serverError.setVisibility(View.GONE);
                            dataResponse(response.body().getResult());
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        //Toast.makeText(getBaseContext(), "Data not found",Toast.LENGTH_SHORT).show();
                    }


                }else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                  //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CurrentDataMainObject> call, Throwable t) {
            hideloader();
              //  Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                ll_main.setVisibility(View.GONE);
                rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }


    private void serviceCallforVersionInfo(){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.LIVE_BASE_URL)
                .baseUrl(Constants.BASE_URL_TOKEN)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VersionInfoDataInterface service = retrofit.create(VersionInfoDataInterface.class);

       // Call<VersionInfoMainObject> call = service.getStringScalar(new VersionInfoPostParameters(inventoryId, appVersion, fontVersion));
        Call<VersionInfoMainObject> call = service.getStringScalarWithSession(UserId, authorityKey, new VersionInfoPostParameters(inventoryId, appVersion, fontVersion));
        call.enqueue(new Callback<VersionInfoMainObject>() {
            @Override
            public void onResponse(Call<VersionInfoMainObject> call, Response<VersionInfoMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideloader();

                if(response.body()!=null){

                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    if(response.body().getInfo().getErrorCode()==0)
                    {
                       if(!response.body().getResult().getFontVersionStatus())
                        {
                            FontUrl = response.body().getResult().getFontLink();
                            mPreferences.saveString(Constants.AppVersion, String.valueOf(response.body().getResult().getCurrentAppVersion()));
                            mPreferences.saveString(Constants.FontVersion, String.valueOf(response.body().getResult().getVersionNumber()));
                            Toast.makeText(context, "Font Downloading.." + "DeviceId" +deviceId, Toast.LENGTH_SHORT).show();
                            download();
                        }
                        else
                       {
                           if(isFontFileExist())
                           {
                           //    Toast.makeText(context, "Font Already Updated" +mPreferences.getStringFromPreference(Constants.FontVersion, Constants.FontVersion), Toast.LENGTH_SHORT).show();
                           }
                           else {
                             // Toast.makeText(context, "Font File Deleted" +mPreferences.getStringFromPreference(Constants.FontVersion, Constants.FontVersion), Toast.LENGTH_SHORT).show();
                               /*finish();
                               startActivity(getIntent());*/
                               recreate();
                               fontVersion ="0";
                               serviceCallforVersionInfo();
                           }
                       }
                    }
                    else
                    {
                       // Toast.makeText(context, "Font Already Updated" +mPreferences.getStringFromPreference(Constants.FontVersion, Constants.FontVersion), Toast.LENGTH_SHORT).show();

                        //Toast.makeText(getBaseContext(), "Data not found",Toast.LENGTH_SHORT).show();
                    }


                }else {
                    hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<VersionInfoMainObject> call, Throwable t) {
                hideloader();
                //  Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                ll_main.setVisibility(View.GONE);
                rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        mPreferences = new MPreferences(context);
        // Web request fro the personal info
     /*   appVersion = MPreferences.getAppVersion();
        fontVersion = MPreferences.getFontVersion();*/
        appVersion = mPreferences.getStringFromPreference(Constants.AppVersion, "1");
        fontVersion = mPreferences.getStringFromPreference(Constants.FontVersion, "0");
        serviceCallforVersionInfo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                serviceCall();
            }
        }, 1000);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initialize()
    {
        Init_Views();
        //serviceCallforRefreshToken();
    }

    @Override
    public void goto_home() {
        if(MApplication.getBoolean(context, Constants.IsHomeSelected))
        {

        }
        else
        {
            intent = new Intent(context, Dashboard_Two.class);
            startActivity(intent);
        }

    }


    @Override
    public void goto_devices() {
        intent = new Intent(context, UserDevicesListActivity.class);
        startActivity(intent);
    }

    @Override
    public void goto_devicesHisory() {
        intent = new Intent(context, HistoryDataTableActivity.class);
        startActivity(intent);
    }

    @Override
    public void goto_forewarningDetails() {

    }

    @Override
    public void goto_aboutApp() {

    }

    @Override
    public void goto_TermsandCondition() {

    }

    @Override
    public void goto_logout_method() {
        MApplication.setString(context, Constants.UserID, "");
        MApplication.setString(context, Constants.DeviceID, "");
        MApplication.setString(context, Constants.DEVICE_IN_USE, "");
        MApplication.setBoolean(context, Constants.IsLoggedIn, false);
        MApplication.setBoolean(context, Constants.SingleAccount, false);
       // MApplication.clearAllPreference();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }


    private void dataResponse(CurrentDataResultObject currentDataResultObject) throws java.text.ParseException {
        //Success message
         tv_deviceId.setText(currentDataResultObject.getInventoryID());
         //Constants.DEVICE_IN_USE= currentDataResultObject.getDeviceName();
        MApplication.setString(context, Constants.DEVICE_IN_USE, currentDataResultObject.getDeviceName());
        tv_deviceName1.setText(MApplication.getString(context, Constants.DEVICE_IN_USE));
        if(currentDataResultObject.getValues().size()>0)
        {
            String dateToParse = currentDataResultObject.getValues().get(0).getDate();
            //dateToParse.replaceAll("AMP", "");
            DateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
            DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            //:String inputDateStr = dateToParse;
            Date date = inputFormat.parse(dateToParse);
            String outputDateStr = outputFormat.format(date);
            tv_last_sync.setText(outputDateStr);
        }


        //tv_last_sync.setText(currentDataResultObject.getValues().get(0).getDate());
        List<CurrentDataValueObject> list = currentDataResultObject.getValues();
        generateDataList(list);
    }

    private void generateDataList(List<CurrentDataValueObject> values)
    {

         ArrayList<CurrentDataValueObject> arrayList = new ArrayList<>();
        if(values.size()>0)
        {

            for (int i =0; i<values.size() ; i++)

            {
                //tv_thermometer.setText(values.get(0).getValue());
                String s2 = values.get(0).getIcon();

                String s1 = s2.replaceAll("&#x", "");

                s1 = s1.replaceAll(";", "");

              /*  String icon = new String(Character.toChars(Integer.parseInt(
                        s1, 16)));*/
               // tv_thermometer_icon.setText(icon);

                arrayList.add(values.get(i));

            }

        }
        if(arrayList.size()>0)
        {
            @SuppressLint("WrongConstant") LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
            rcv_bottom_view.setLayoutManager(mLayoutManager);
            channelRcvAdapter = new DashboardTwoChannelRcvAdapter(context, arrayList);
            rcv_bottom_view.setAdapter(channelRcvAdapter);
        }


    }

    public void showLoaderNew() {
        runOnUiThread(new Dashboard_Two.Runloader(getResources().getString(R.string.loading)));
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

                ImageView imgeView = dialog
                        .findViewById(R.id.imgeView);
                TextView tvLoading = dialog
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



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){
            // Do what ever you want
            exitToast.show();
            doubleBackToExitPressedOnce = false;
        } else{
            finishAffinity();
            finish();
            // Do exit app or back press here
            super.onBackPressed();
        }
    }
    private void download () {

       try {
           File Dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
           //  File file = new File(Dir,"nahk.txt");
           File file = new File(Dir,  "/icomoon.ttf");
           if(file.exists()){
               file.delete();
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        new DownloadFileFromURL().execute(FontUrl); // Downlod LINK !
    }


    // File download process from URL
    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("/storage/emulated/0/Download/icomoon.ttf");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {
            // Display the custom font after the File was downloaded !
          //  loadfont();
        }
    }


    public boolean isFontFileExist()
    {
        File Dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
        //  File file = new File(Dir,"nahk.txt");

        File file = new File(Dir,  "/icomoon.ttf");

        if(file.exists()){
            return true;
        }
        else {
            return false;
        }
    }


    public ShapeDrawable myFunction(){
        Path path = new Path();
        float stdW = 100;
        float stdH = 100;
        float w3 = stdW / 3;
        float h2 = stdH / 2;
        path.moveTo(0, h2);
        h2 -= 6 / 2;
        path.rLineTo(w3, -h2);         path.rLineTo(w3, 0); path.rLineTo(w3, h2);
        path.rLineTo(-w3, h2); path.rLineTo(-w3, 0); path.rLineTo(-w3, -h2);
        Shape s = new PathShape(path, stdW, stdH);
        ShapeDrawable d = new ShapeDrawable(s);
        Paint p = d.getPaint();
        p.setColor(0xffeeeeee);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(6);

        return d;

    }

    private void serviceCallforRefreshToken() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_TOKEN)
                //.baseUrl(Constants.USER_MGMT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginDataInterface service = retrofit.create(LoginDataInterface.class);

        Call<LoginMainObject> call = service.refreshSession(authorityKeyForRefresh, grantTypeRefresh, refreshToken);
        call.enqueue(new Callback<LoginMainObject>() {
            @Override
            public void onResponse(Call<LoginMainObject> call, Response<LoginMainObject> response) {
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        hideloader();
                        Toast.makeText(getBaseContext(), "Refreshed",Toast.LENGTH_SHORT).show();
                        MApplication.setString(context, Constants.AccessToken, response.body().getResult().getAccessToken());
                        MApplication.setString(context, Constants.RefreshToken, response.body().getResult().getRefreshToken());
                        MApplication.setBoolean(context, Constants.IsLoggedIn, true);
                        MApplication.setString(context, Constants.UserID, String.valueOf(response.body().getResult().getUserId()));
                        UserId = Integer.parseInt(MApplication.getString(context, Constants.UserID));
                        MApplication.setString(context, Constants.UserName, String.valueOf(response.body().getResult().getUserName()));
                    }
                    else if(response.body().getInfo().getErrorCode().equals(1))
                    {
                        hideloader();
                        Toast toast = Toast.makeText(context, response.body().getInfo().getErrorMessage(), Toast.LENGTH_LONG);
                        View view = toast.getView();
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                        toast.show();
                        // Toast.makeText(loginContext, response.body().getInfo().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    hideloader();
                    Toast toast = Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<LoginMainObject> call, Throwable t) {
                hideloader();
                Toast toast = Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_back_red));
                toast.show();

            }
        });

    }




}
