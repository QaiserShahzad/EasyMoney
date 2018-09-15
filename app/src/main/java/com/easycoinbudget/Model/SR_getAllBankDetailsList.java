
package com.easycoinbudget.Model;

        import java.util.ArrayList;
        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getAllBankDetailsList {

    @SerializedName("bankinfo")
    @Expose
    private ArrayList<SR_getAllBankDetails> bankinfo = null;

    public List<SR_getAllBankDetails> getBankinfo() {
        return bankinfo;
    }

    public void setBankinfo(ArrayList<SR_getAllBankDetails> bankinfo) {
        this.bankinfo = bankinfo;
    }

}