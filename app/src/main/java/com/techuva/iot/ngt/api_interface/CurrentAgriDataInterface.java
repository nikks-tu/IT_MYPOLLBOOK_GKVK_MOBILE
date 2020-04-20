package com.techuva.iot.ngt.api_interface;


import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.CurrentDataAgriPostParameter;
import com.techuva.iot.ngt.model.CurrentDataMainObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface CurrentAgriDataInterface {

    @POST(Constants.CurrentData)
    Call<CurrentDataMainObject>  getStringScalarWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body CurrentDataAgriPostParameter postParameter);

}
