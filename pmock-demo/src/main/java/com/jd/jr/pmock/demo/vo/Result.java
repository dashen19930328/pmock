package com.jd.jr.pmock.demo.vo;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-28
 * Time: 上午11:39
 */
public class Result<T> {
    T value;
    Boolean flag;
    String desc;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
