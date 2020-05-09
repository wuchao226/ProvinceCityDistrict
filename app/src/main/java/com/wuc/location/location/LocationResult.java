package com.wuc.location.location;

import java.util.List;

/**
 * @author : wuchao5
 * @date : 2020/4/27 19:14
 * @desciption :
 */
public class LocationResult {

    /**
     * 省市(大陆--北京/大陆--广东-广州/港澳台--香港/海外--美国)
     */
    private List<Province> geoLocations;
    /**
     * 区域（北京市--东城区/ 广东--广州---荔湾区）
     */
    private List<Province> district;
    /**
     * 商圈（北京市 -- 东城区--王府井 ）
     */
    private List<Province> rbd;

    public List<Province> getGeoLocations() {
        return geoLocations;
    }

    public void setGeoLocations(List<Province> geoLocations) {
        this.geoLocations = geoLocations;
    }

    public List<Province> getDistrict() {
        return district;
    }

    public void setDistrict(List<Province> district) {
        this.district = district;
    }

    public List<Province> getRbd() {
        return rbd;
    }

    public void setRbd(List<Province> rbd) {
        this.rbd = rbd;
    }
}
