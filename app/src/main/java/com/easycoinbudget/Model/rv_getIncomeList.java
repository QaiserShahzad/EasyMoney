package com.easycoinbudget.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Dell ins on 5/2/2018.
 */

public class rv_getIncomeList
{

    @SerializedName("userIncome")
    @Expose
    private ArrayList<rv_getIncome> userIncome = null;

    public ArrayList<rv_getIncome> getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(ArrayList<rv_getIncome> userIncome) {
        this.userIncome = userIncome;
    }

}