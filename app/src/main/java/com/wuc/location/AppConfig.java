package com.wuc.location;

import android.content.res.AssetManager;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wuc.location.global.AppGlobals;
import com.wuc.location.location.LocationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author : wuchao5
 * @date : 2020/4/27 18:52
 * @desciption :
 */
public class AppConfig {

    /**
     * 解析地址数据
     */
    public static LocationData getLocationConfig() {
        String content = parseFile("location.json");
        return JSON.parseObject(content, new TypeReference<LocationData>() {
        });
    }

    private static String parseFile(String fileName) {
        AssetManager assets = AppGlobals.getApplication().getAssets();
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            is = assets.open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {

            }
        }

        return builder.toString();
    }
}
