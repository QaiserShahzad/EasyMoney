

package com.easycoinbudget.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SR_getAllExtraIncome {

    @SerializedName("extAmount")
    @Expose
    private String extAmount;
    @SerializedName("extType")
    @Expose
    private String extType;
    @SerializedName("extDescrp")
    @Expose
    private String extDescrp;
    @SerializedName("extDate")
    @Expose
    private String extDate;

    public String getExtAmount() {
        return extAmount;
    }

    public void setExtAmount(String extAmount) {
        this.extAmount = extAmount;
    }

    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getExtDescrp() {
        return extDescrp;
    }

    public void setExtDescrp(String extDescrp) {
        this.extDescrp = extDescrp;
    }

    public String getExtDate() {
        return extDate;
    }

    public void setExtDate(String extDate) {
        this.extDate = extDate;
    }

}
