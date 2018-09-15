package com.easycoinbudget.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SR_getAllRecord {


    @SerializedName("total_userIncome")
    @Expose
    private Integer totalUserIncome;
    @SerializedName("total_recExpense")
    @Expose
    private Integer totalRecExpense;
    @SerializedName("total_estExpense")
    @Expose
    private Integer totalEstExpense;
    @SerializedName("total_extraIncome")
    @Expose
    private Integer totalExtraIncome;

    public Integer getTotalUserIncome() {
        return totalUserIncome;
    }

    public void setTotalUserIncome(Integer totalUserIncome) {
        this.totalUserIncome = totalUserIncome;
    }

    public Integer getTotalRecExpense() {
        return totalRecExpense;
    }

    public void setTotalRecExpense(Integer totalRecExpense) {
        this.totalRecExpense = totalRecExpense;
    }

    public Integer getTotalEstExpense() {
        return totalEstExpense;
    }

    public void setTotalEstExpense(Integer totalEstExpense) {
        this.totalEstExpense = totalEstExpense;
    }

    public Integer getTotalExtraIncome() {
        return totalExtraIncome;
    }

    public void setTotalExtraIncome(Integer totalExtraIncome) {
        this.totalExtraIncome = totalExtraIncome;
    }

}