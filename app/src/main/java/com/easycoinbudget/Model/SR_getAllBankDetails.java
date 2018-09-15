


package com.easycoinbudget.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SR_getAllBankDetails {

    @SerializedName("bankname")
    @Expose
    private String bankname;
    @SerializedName("bankpin")
    @Expose
    private String bankpin;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankpin() {
        return bankpin;
    }

    public void setBankpin(String bankpin) {
        this.bankpin = bankpin;
    }

}