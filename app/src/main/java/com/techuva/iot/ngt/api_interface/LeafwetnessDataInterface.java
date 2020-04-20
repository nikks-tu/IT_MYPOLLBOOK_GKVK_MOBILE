package com.techuva.iot.ngt.api_interface;


import com.google.gson.JsonElement;
import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.LeafWetnessPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface LeafwetnessDataInterface {

    @POST(Constants.LeafWetnessCall)
    Call<JsonElement>  getStringScalarWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body LeafWetnessPostParameters postParameter);

}
