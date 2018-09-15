package com.easycoinbudget.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SR_getSingleValue  {

    @SerializedName("User_Id")
    @Expose
    public String userId;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}