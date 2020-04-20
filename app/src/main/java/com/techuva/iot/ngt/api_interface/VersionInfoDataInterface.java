package com.techuva.iot.ngt.api_interface;


import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.VersionInfoMainObject;
import com.techuva.iot.ngt.model.VersionInfoPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface VersionInfoDataInterface {

    @POST(Constants.VersionCheck)
    Call<VersionInfoMainObject>  getStringScalar(@Body VersionInfoPostParameters postParameter);

    @POST(Constants.VersionCheck)
    Call<VersionInfoMainObject>  getStringScalarWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body VersionInfoPostParameters postParameter);

}
