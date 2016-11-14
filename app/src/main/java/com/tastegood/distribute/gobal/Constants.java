package com.tastegood.distribute.gobal;

/**
 * 常量
 *
 * Created by surandy on 2016/10/13.
 */

public interface Constants {

    int ORDER_STATUS_WAITHANDLE = 0;
    int ORDER_STATUS_SENDING = 1;
    int ORDER_STATUS_ARRIVED = 2;

    String CLIENT_TYPE = "Android";

    String API_CALLER = "PSAndroid";

    String APP_TYPE = "PS";

    String API_SECRET_KEY = "b0914551195352024d099ef62c681fa36a2909e0";

    String LOGGER_TAG = "TasteGoodDisreibute";

    int PAGE_SIZE = 20;

    /** share文件key值 */
    String DEVICE_NO_KEY = "device_no_key";
    String JPUSH_TOKEN_KEY = "jpush_token_key";
    String USER_INFO_KEY = "user_info_key";

    String JGPUSH_TEST = "PROD";

    /** 接口相关 */
    String BASE_URL = "http://www.meiyinheung.com/api/app/";

    String DEVICE_SIGNIN = "device/signIn";

    String UPLOAD_JPUSHTOKEN = "device/sumitJgToken";

    String DZ_LOGIN = "distribution/login";

    String DZ_LOGOUT = "distribution/logout";

    String GET_ORDERS = "distribution/orderList";

    String ORDER_ARRIVED = "distribution/order/ok";

}
