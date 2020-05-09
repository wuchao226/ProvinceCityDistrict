package com.wuc.location.location;

/**
 * @author : wuchao5
 * @date : 2020/5/3 14:00
 * @desciption : 存储选择时省市区
 */
public class SelectResultBean {
    /**
     * 省 名称
     */
    private String province;
    /**
     * 市 名称
     */
    private String city;
    /**
     * 区域 名称
     */
    private String district;
    /**
     * 区域 code
     */
    private String code;
    /**
     * 当前选择的 区域 所在的 省 的索引
     */
    private int provinceIndex = 0;
    /**
     * 当前选择的 区域 所在的 市 的索引
     */
    private int cityIndex = 0;

    private int districtIndex = 0;

    public SelectResultBean() {
    }

    public SelectResultBean(String province, String city, String district, String code, int provinceIndex, int cityIndex, int districtIndex) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.code = code;
        this.provinceIndex = provinceIndex;
        this.cityIndex = cityIndex;
        this.districtIndex = districtIndex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getProvinceIndex() {
        return provinceIndex;
    }

    public void setProvinceIndex(int provinceIndex) {
        this.provinceIndex = provinceIndex;
    }

    public int getCityIndex() {
        return cityIndex;
    }

    public void setCityIndex(int cityIndex) {
        this.cityIndex = cityIndex;
    }

    public int getDistrictIndex() {
        return districtIndex;
    }

    public void setDistrictIndex(int districtIndex) {
        this.districtIndex = districtIndex;
    }
}
