package com.baidu.face.demo;

import com.baidu.aip.face.AipFace;
import com.sun.jnlp.ApiDialog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by xutianhan on 17/5/21.
 */
public class FaceUtil {

    public static final String APP_ID = "9657457";
    public static final String API_KEY = "Qo4L7hFqpGrWLYwTQbn3KGrT";
    public static final String SECRET_KEY = "ZlvkBtgYu9zi5q23NeKY5t8kTceyHKnT";


    /**
     * 初始化
     * @return
     */
    public static AipFace initFaceClient() {
        // 初始化一个FaceClient
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(5000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }



    /**
     * 注册
     * @param client
     * @param path
     * @param uid
     * @param userInfo
     * @return true/false
     */
    public static boolean facesetAddUser(AipFace client, String path, long uid, String userInfo) {
        // 参数为本地图片路径
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.addUser(String.valueOf(uid), userInfo, Arrays.asList("group1"), path, options);
        System.out.println(res.toString(2));
        if (res.has("error_code")) {
            return false;
        }
        // 解析json
        return true;
    }


    /**
     * 验证 1：N
     * @param client
     * @param path
     * @return uid -1
     */
    public static long identifyUser(AipFace client,String path) {
        HashMap<String, Object> options = new HashMap<String, Object>(1);
        options.put("user_top_num", 1);
        JSONObject res = client.identifyUser(Arrays.asList("group1"), path, options);
        System.out.println(res.toString(2));

        JSONArray resultList = (JSONArray)res.get("result");
        if (resultList.length() >=1) {
            JSONObject result = resultList.getJSONObject(0);
            JSONArray scoreArray = result.getJSONArray("scores");
            double score = scoreArray.getDouble(0);
            System.out.println(score);
            if (score >= Constants.oneToNScore) {
                long uid = result.getLong("uid");
                return uid;
            } else {
                return -1;
            }
        }
        return -1;
    }


    /**
     * 1:1 人脸比对
     * @param client
     * @param path1
     * @param path2
     */
    public static boolean faceRecognize(AipFace client, String path1, String path2) {
        ArrayList<String> pathArray = new ArrayList<String>();
        pathArray.add(path1);
        pathArray.add(path2);
        JSONObject res = client.match(pathArray, new HashMap<String, String>());
        JSONArray resultList = (JSONArray)res.get("result");
        if (resultList.length() >= 1) {
            JSONObject result = resultList.getJSONObject(0);
            double score = result.getDouble("score");
            System.out.println(score);
            if (score >= Constants.oneToOneScore) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static void deleteUserFromGroup() {

    }


    /**
     * 从人脸库中彻底删除用户
     * @param client
     * @param uid
     */
    public static void deleteUser(AipFace client, long uid) {
        JSONObject resDB = client.deleteUser(String.valueOf(uid));
        System.out.println(resDB.toString(2));
    }


    /**
     * 只从指定组中删除用户
     * @param client
     * @param group
     * @param uid
     */
    public void facesetDeleteUser(AipFace client, String group, long uid) {
        JSONObject resGroup = client.deleteUser(String.valueOf(uid), Arrays.asList(group));
        System.out.println(resGroup.toString(2));
    }



}
