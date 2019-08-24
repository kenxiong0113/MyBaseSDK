package com.ken.mybasesdk.bean;

/**
 * Copyright (C) 2019-2020, by 中集智能, All rights reserved.
 * -----------------------------------------------------------------
 * File: MenuBean.java
 *
 * @author by ken
 * Create: 2019/8/24 8:55
 * @description： -----------------------------------------------------------------
 */
public class MenuBean {
    private String buttonText;
    private int act;

    public int getAct() {
        return act;
    }

    public void setAct(int act) {
        this.act = act;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public MenuBean(String buttonText, int act) {
        this.buttonText = buttonText;
        this.act = act;
    }
}
