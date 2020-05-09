package com.wuc.location.location;

import java.util.List;

/**
 * @author : wuchao5
 * @date : 2020/4/28 14:06
 * @desciption : 市区
 */
public class City {
    private String name;
    /**
     * 选中的区的数量
     */
    private int selectCount;
    private boolean isCheck;

    private List<District> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<District> getValue() {
        return value;
    }

    public void setValue(List<District> value) {
        this.value = value;
    }

}
