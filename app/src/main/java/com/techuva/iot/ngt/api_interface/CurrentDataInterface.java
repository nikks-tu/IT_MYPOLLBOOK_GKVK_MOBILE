package com.techuva.iot.ngt.api_interface;

import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.CurrentDataMainObject;
import com.techuva.iot.ngt.model.CurrentDataPostParameter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface CurrentDataInterface {

    @POST(Constants.CurrentData)
    Call<CurrentDataMainObject>  getStringScalar(@Body CurrentDataPostParameter postParameter);

    @POST(Constants.CurrentData)
    Call<CurrentDataMainObject>  getStringScalarWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body CurrentDataPostParameter postParameter);

}
