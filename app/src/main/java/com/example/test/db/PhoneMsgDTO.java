package com.example.test.db;

public class PhoneMsgDTO {
    private String phone;
    private String msg;

    public PhoneMsgDTO(String phone, String msg) {
        this.phone = phone;
        this.msg = msg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
