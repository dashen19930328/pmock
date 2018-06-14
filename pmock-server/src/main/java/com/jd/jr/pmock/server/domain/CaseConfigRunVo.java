package com.jd.jr.pmock.server.domain;

public class CaseConfigRunVo  {

    private String method;//执行的方法
    private String inputPara;//方法的入参

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInputPara() {
        return inputPara;
    }

    public void setInputPara(String inputPara) {
        this.inputPara = inputPara;
    }
}
