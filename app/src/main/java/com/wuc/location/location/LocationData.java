package com.wuc.location.location;

/**
 * @author : wuchao5
 * @date : 2020/4/27 18:58
 * @desciption : 地址 数据 实体类
 */
public class LocationData {
    private int code;
    private LocationResult result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocationResult getResult() {
        return result;
    }

    public void setResult(LocationResult result) {
        this.result = result;
    }
}
