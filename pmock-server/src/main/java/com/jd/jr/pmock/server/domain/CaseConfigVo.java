package com.jd.jr.pmock.server.domain;

import java.util.Date;

public class CaseConfigVo {

    /**
     *
     */

    private Long id;




    /**
     * 通过类名作为case名字
     */

    private String className;




    /**
     * case的内容
     */

    private String caseText;




    /**
     * case脚本语言类型
     */

    private String scriptType;




    /**
     * 版本
     */

    private String version;




    /**
     * 配置人id
     */

    private String userId;




    /**
     * 配置人名字
     */

    private String userName;




    /**
     * 系统编码
     */

    private String systemCode;




    /**
     * 系统名字
     */

    private String systemName;




    /**
     * 状态
     */

    private Integer status;




    /**
     * 扩展字段1
     */

    private String ext1;




    /**
     * 扩展字段2
     */

    private String ext2;




    /**
     * 更新时间
     */

    private Date updateDate;




    /**
     * 创建时间
     */

    private Date createdDate;


    private String method;
    private String inputPara;

    public String getInputPara() {
        return inputPara;
    }

    public void setInputPara(String inputPara) {
        this.inputPara = inputPara;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getCaseText() {
        return caseText;
    }

    public void setCaseText(String caseText) {
        this.caseText = caseText;
    }


    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }


    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }


    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
