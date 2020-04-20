package com.techuva.iot.ngt.app;

/**
 * Created by user on 6/29/2015.
 */
public interface Constants {
    //Test URL
    String BASE_URL_TOKEN = "http://182.18.177.27:8586/";
    //Production URL
    //String BASE_URL_TOKEN = "https://service.datapollz.com";
    String CurrentData = "currentdata";
    String CurrentDataAgri = "currentdataAgri";
    String LoginData = "login";
    String ForgetPassword = "forgotpassword";
    String HistoryData = "inventoryHistoricalData";
    // .Net APIinventoryHistoricalData
    //String HistoryDataAllChannels = "GetPivotRAW_SENSOR_DATA";
    // JAVA API
    String HistoryDataAllChannels = "GetPivotRawSensorData";
    String HistoryDataAllChannelsForAgri = "GetPivotRawSensorDataAgri";
    String ListofDevices = "productinventorydashboard";
    String CompanyList = "companydashboard/";
    String VersionCheck = "VersionCheckInfo";
    String GetGraphData = "getconsumeddetailsGraphData";
    int SelectedChannel = 1;
    //Test URL
    String USER_MGMT_URL = "http://182.18.177.27/TUUserManagement/api/user/";
    //Production URL
    //String USER_MGMT_URL = "https://winservices.datapollz.com//TUIoTAdminAPI/api/user/";
    //String Table_Data_URL = "https://winservices.datapollz.com//TUIoTHomeAPI/api//IOT_API/";
    String Ref_Type = "MOBILE";
    String DeviceID = "DeviceID";
    String InventoryName = "InventoryName";
    String InventoryTypeName = "InventoryTypeName";
    String CompanyID ="CompanyID";
    String UserID ="UserID";
    String UserIDSelected ="UserIDSelected";
    String UserName ="UserName";
    String UserMailId ="UserMailId";
    String AppVersion = "1";
    String FontVersion = "0";
    String InventoryTypeId = "InventoryTypeId";
    String ForwarningDataCall = "getDeviceReportConvertData";
    String LeafWetnessCall = "getLeafWetnessInfo";

    String SingleAccount="SingleAccount";

    String IsLoggedIn = "IsLoggedIn";
    String IsHomeSelected = "IsHomeSelected";
    String IsDefaultDeviceSaved = "IsDefaultDeviceSaved";
    String Template = "Template";
    String ChannelNumGraph = "ChannelNumGraph";
    //Token 90 Day
    String AuthorizationKey = "Basic dGVjaHV2YS1jbGllbnQtbW9iaWxlOnNlY3JldA==";
    //Token 1 Hr
    //String AuthorizationKey = "Basic dGVjaHV2YS1jbGllbnQ6c2VjcmV0";
    String GrantType= "password";
    String GrantTypeRefresh= "refresh_token";
    String AccessToken ="AccessToken";
    String RefreshToken ="RefreshToken";
    String SecondsToExpireToken ="SecondsToExpireToken";
    String DateToExpireToken ="DateToExpireToken";
    String TokenExpireMsg = "Token Expired/Invalid";
    String NoUserRole = "No Role Assigned to User";
    String IsSessionExpired = "IsSessionExpired";

    public static final String NULL_STRING = "null";
    public static String DEVICE_IN_USE = "";
    public static String COMPANY_ID = "COMPANY_ID";

}
