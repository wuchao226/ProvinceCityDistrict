package com.wuc.location.location;

import java.util.List;

/**
 * @author : wuchao5
 * @date : 2020/4/28 14:04
 * @desciption : 省
 */
public class Province {
    private String name;
    /**
     * 选中的所有市中区的总数量
     */
    private int selectCount;
    private boolean isCheck;
    private List<City> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getValue() {
        return value;
    }

    public void setValue(List<City> value) {
        this.value = value;
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
}
