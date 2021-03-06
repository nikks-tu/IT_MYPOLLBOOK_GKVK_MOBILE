package com.techuva.iot.ngt.api_interface;


import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.ForwarningPostParameters;
import com.techuva.iot.ngt.response_model.ForwarningMainObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ForwarningDataInterface {

    @POST(Constants.ForwarningDataCall)
    Call<ForwarningMainObject>  getStringScalar(@Body ForwarningPostParameters postParameter);

    @POST(Constants.ForwarningDataCall)
    Call<ForwarningMainObject>  getStringScalarWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body ForwarningPostParameters postParameter);

}
