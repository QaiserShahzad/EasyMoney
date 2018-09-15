package com.easycoinbudget.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class rv_getIncome {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("incomedate")
    @Expose
    private String incomedate;

    @SerializedName("incomeid")
    @Expose
    private String incomeId;


    public String getIncomeId() {
        return incomeId;
    }
    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }




    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncomedate() {
        return incomedate;
    }

    public void setIncomedate(String incomedate) {
        this.incomedate = incomedate;
    }

}