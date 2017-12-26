package com.diandian.pdd.diandiancha.request;

import android.util.Log;

import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.util.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyRequest {
    /*
     * "upload/userw9qCgHPG6779CtWN3XsWlD9PRTAt1z.png", "path":"upload /user"
	 */
    private static OkHttpClient client;
    public static String HOST = "http://120.203.64.131:8080/";

    static {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
//                .addInterceptor(new LogInterceptor())
                .build();
        client.cookieJar();
    }


    public static Call postForm(String api, HashMap<String, String> map, MyCallback callback) {
        Request request = getRequest(api, map);

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
//    private static Request getRequest(String api, HashMap<String, String> map) {
//        FormBody.Builder bu = new FormBody.Builder();
//        for (String key : map.keySet()) {
//            bu.add(key, map.get(key));
//        }
//        return new Request.Builder().url(api).post(bu.build()).build();
//    }

    private static Request getRequest(String api, HashMap<String, String> map) {
       api=attachHttpGetParams(api,map);
       MyLog.log("netLog",api);
        return new Request.Builder().url(api).build();
    }
    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, HashMap<String,String> params){

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i=0;i<params.size();i++ ) {
            String value=null;
            try {
                value= URLEncoder.encode(values.next(),"utf-8");
            }catch (Exception e){
                e.printStackTrace();
            }

            stringBuffer.append(keys.next()+"="+value);
            if (i!=params.size()-1) {
                stringBuffer.append("&");
            }
        }

        return url + stringBuffer.toString();
    }


    public static Call login(String userName, String passWord, MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_Login";
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("passWord", passWord);
        return postForm(api, map, callback);
    }

    public static Call registr(String userName, String passWord, String userType, String userExplain, String phoneNum, MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_InsterUser";
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("passWord", passWord);
        map.put("userType", userType);
        map.put("userExplain", userExplain);
        map.put("phoneNum", phoneNum);
        return postForm(api, map, callback);
    }

    public static Call queryAll(MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_QueryAllUser";
        HashMap<String, String> map = new HashMap<>();
        return postForm(api, map, callback);
    }

    public static Call deleteUser(int userId, MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_DeleteUser";
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", String.valueOf(userId));
        return postForm(api, map, callback);
    }

    public static Call insterWares(String waresName, String waresPrice, String waresImgName, String sellerName, MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_InsterWares";
        HashMap<String, String> map = new HashMap<>();
        map.put("waresName", waresName);
        map.put("waresPrice", waresPrice);
        map.put("waresImgName", waresImgName);
        map.put("sellerName", sellerName);
        return postForm(api, map, callback);
    }

    public static Call deleteWares(String waresId, MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_DeleteWares";
        HashMap<String, String> map = new HashMap<>();
        map.put("waresId", waresId);
        return postForm(api, map, callback);
    }

    public static Call queryAllWares(MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_QueryAllWares";
        HashMap<String, String> map = new HashMap<>();
        return postForm(api, map, callback);
    }

    public static Call insterOrder(String waresName, String buyersName, String sellerName, String waresNum, String total, String orderState, String orderTime, MyCallback callback) {
        String api = HOST + "Diandiancha/Servlet_InterOrder";
        HashMap<String, String> map = new HashMap<>();
        map.put("waresName", waresName);
        map.put("buyersName", buyersName);
        map.put("sellerName", sellerName);
        map.put("waresNum", waresNum);
        map.put("total", total);
        map.put("orderState", orderState);
        map.put("orderTime", orderTime);
        return postForm(api, map, callback);
    }


    public abstract static class MyCallback implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {
            MyLog.log("netLog", "网络错误");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.i("netLog", "get netdata=" + body);
            try {
                JSONObject json = new JSONObject(body);
                if ("0000".equals(json.getString("error"))) {

                    sucess(json.getString("message"));
                } else {
                    failed(json.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public abstract void sucess(String body);

        public void failed(String msg) {
            MyLog.log("netLog", msg);
        }

    }


//    /**
//     * 获取版本号
//     */
//    public static void getVersition2(Context context, Callback callback) {
//        if (context == null) {
//            return;
//        }
//        String api = "http://120.203.64.131:8081/AdroidUpload/UploadFile";
//
//
//        PostForm(api, mapStr, callback);
//    }


}
