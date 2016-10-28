package org.easyArch.converter.util.json;/**
 * Description : 
 * Created by YangZH on 16-5-29
 *  下午5:50
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by YangZH on 16-5-29
 * 下午5:50
 */

public class JSONUtil {

    public static List<String> strToArray(String json) {
        return JSONArray.parseArray(json, String.class);
    }

    public static Map<String, Object> strToMap(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map<String, Object> params = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        return params;
    }

    public static boolean isJson(String string) {
        try {
            JSON.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String listToJson(List list) {
        return JSON.toJSONString(list,true);
    }

    public static String pojoToJson(Object object) {
        return JSON.toJSONString(object, true);
    }

    public static String mapToJson(Map<String,Object> jsonMap){
        JSONObject jsonObject = new JSONObject(jsonMap);
        return jsonObject.toJSONString();
    }

    public static JSONObject str2Json(String str) {
        return JSON.parseObject(str);
    }

    /**
     * 从一段json中按key获取array
     *
     * @param key
     * @param json
     * @return
     */
    public static JSONArray getArrayFromStr(String key, String json) {
        JSONObject object = JSON.parseObject(json);
        return (JSONArray) object.get(key);
    }

    public static JSONArray getArrayFromJSON(String key, JSONObject json) {
        return (JSONArray) json.get(key);
    }
}
//                bill.put("tradeTime", data.get(0));
//                bill.put("officialAccountId", data.get(1));
//                bill.put("businessId", data.get(2));
//                bill.put("subBusinessid", data.get(3));
//                bill.put("deviceId", data.get(4));
//                bill.put("businessOrderid", data.get(5));
//                bill.put("wechatOrderid", data.get(6));
//                bill.put("userId", data.get(7));
//                bill.put("tradeType", data.get(8));
//                bill.put("tradeStatus", data.get(9));
//                bill.put("payBank", data.get(10));
//                bill.put("currency", data.get(11));
//                bill.put("totalCost", data.get(12));
//                bill.put("redPackets", data.get(13));
//                bill.put("wechatRefundsOrderId", data.get(14));
//                bill.put("busRefundsOrderId", data.get(15));
//                bill.put("refund", data.get(16));
//                bill.put("redPacketsRefund", data.get(17));
//                bill.put("refundType", data.get(18));
//                bill.put("refundStatus", data.get(19));
//                bill.put("itemName", data.get(20));
//                bill.put("busDataPacket", data.get(21));
//                bill.put("fee", data.get(22));
//                bill.put("feePercent", data.get(23));