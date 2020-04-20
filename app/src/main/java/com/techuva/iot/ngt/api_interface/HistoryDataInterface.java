package com.techuva.iot.ngt.api_interface;


import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.HistoryDataMainObject;
import com.techuva.iot.ngt.model.HistoryDataPostParamter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface HistoryDataInterface {

    @POST(Constants.HistoryData)
    Call<HistoryDataMainObject>  getStringScalar(@Body HistoryDataPostParamter postParameter);

    @POST(Constants.HistoryData)
    Call<HistoryDataMainObject>  getStringScalarWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body HistoryDataPostParamter postParameter);

}
