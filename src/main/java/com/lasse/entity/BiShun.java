package com.lasse.entity;

import java.io.Serializable;

public class BiShun implements Serializable {

    private static final long serialVersionUID = -4340616318955043042L;
    private Integer uniCode;

    private String bishun;

    public BiShun() {
    }

    public BiShun(Integer uniCode, String bishun) {
        this.uniCode = uniCode;
        this.bishun = bishun;
    }

    public Integer getUniCode() {
        return uniCode;
    }

    public void setUniCode(Integer uniCode) {
        this.uniCode = uniCode;
    }

    public String getBishun() {
        return bishun;
    }

    public void setBishun(String bishun) {
        this.bishun = bishun;
    }

    @Override
    public String toString() {
        return "BiShun{" +
                "uniCode=" + uniCode +
                ", bishun='" + bishun + '\'' +
                '}';
    }
}
