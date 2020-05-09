package com.wuc.location.location;

/**
 * @author : wuchao5
 * @date : 2020/4/28 15:17
 * @desciption : 区域数据
 */
public class District {

    private String name;

    private String value;
    private boolean isCheck;

    public District() {
    }

    public District(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
