package com.baidu.face.demo;

import com.baidu.aip.face.AipFace;

/**
 * Created by xutianhan on 17/5/21.
 */
public class MainClass {
    public static void main(String[] args) {
        AipFace client = FaceUtil.initFaceClient();

        // 注册
        String registerPath = "xutianhan.jpg";
        long uid = 1;
        String userInfo = "许天涵";
//        boolean registed = FaceUtil.facesetAddUser(client, registerPath, uid, userInfo);
//        System.out.println(registed);

        // 验证1：N
        String identifyPath = "test_Allen.jpg";
        long result_uid = FaceUtil.identifyUser(client, identifyPath);
        System.out.println(result_uid);

        // 验证1:1
        String path1 = "Allen.jpg";
        String path2 = "test_Allen.jpg";
//        boolean result = FaceUtil.faceRecognize(client, path1, path2);
//        System.out.println(result);

        // 删除
        long deleteUid = 3;
//        FaceUtil.deleteUser(client, deleteUid);
    }
}
