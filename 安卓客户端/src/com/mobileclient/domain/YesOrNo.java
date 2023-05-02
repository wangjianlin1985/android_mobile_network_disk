package com.mobileclient.domain;

import java.io.Serializable;

public class YesOrNo implements Serializable {
    /*记录id*/
    private int yesNoId;
    public int getYesNoId() {
        return yesNoId;
    }
    public void setYesNoId(int yesNoId) {
        this.yesNoId = yesNoId;
    }

    /*记录名称*/
    private String yesNoName;
    public String getYesNoName() {
        return yesNoName;
    }
    public void setYesNoName(String yesNoName) {
        this.yesNoName = yesNoName;
    }

}