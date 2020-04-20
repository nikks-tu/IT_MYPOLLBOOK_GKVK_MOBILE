package com.techuva.iot.ngt.api_interface;

import com.techuva.iot.ngt.app.Constants;
import com.techuva.iot.ngt.model.ForgotPassPostParameters;
import com.techuva.iot.ngt.model.ForgotPasswordMainObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ForgotPasswordDataInterface {

    @POST(Constants.ForgetPassword)
    Call<ForgotPasswordMainObject> getStringScalar(@Body ForgotPassPostParameters postParameter);

}
