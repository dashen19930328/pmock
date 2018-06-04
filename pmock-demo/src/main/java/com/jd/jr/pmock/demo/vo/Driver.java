package com.jd.jr.pmock.demo.vo;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-30
 * Time: 上午10:38
 */
public class Driver extends PersonVo {
    private String carName;//车的名字
    private Integer oil;//油耗

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Integer getOil() {
        return oil;
    }

    public void setOil(Integer oil) {
        this.oil = oil;
    }
}
