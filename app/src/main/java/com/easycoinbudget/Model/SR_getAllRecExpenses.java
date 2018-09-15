
package com.easycoinbudget.Model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getAllRecExpenses {

    @SerializedName("recamount")
    @Expose
    private String recamount;
    @SerializedName("rectype")
    @Expose
    private String rectype;
    @SerializedName("recfrequency")
    @Expose
    private String recfrequency;
    @SerializedName("recdescription")
    @Expose
    private String recdescription;
    @SerializedName("recdate")
    @Expose
    private String recdate;

    public String getRecamount() {
        return recamount;
    }

    public void setRecamount(String recamount) {
        this.recamount = recamount;
    }

    public String getRectype() {
        return rectype;
    }

    public void setRectype(String rectype) {
        this.rectype = rectype;
    }

    public String getRecfrequency() {
        return recfrequency;
    }

    public void setRecfrequency(String recfrequency) {
        this.recfrequency = recfrequency;
    }

    public String getRecdescription() {
        return recdescription;
    }

    public void setRecdescription(String recdescription) {
        this.recdescription = recdescription;
    }

    public String getRecdate() {
        return recdate;
    }

    public void setRecdate(String recdate) {
        this.recdate = recdate;
    }

}