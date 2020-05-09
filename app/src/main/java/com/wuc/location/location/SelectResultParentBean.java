package com.wuc.location.location;

import java.util.List;

/**
 * @author : wuchao5
 * @date : 2020/5/3 15:47
 * @desciption : 用于存储选择的区域
 */
public class SelectResultParentBean {
    private String name;
    private List<SelectResultBean> result;

    public SelectResultParentBean(String name, List<SelectResultBean> result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectResultBean> getResult() {
        return result;
    }

    public void setResult(List<SelectResultBean> result) {
        this.result = result;
    }
}
