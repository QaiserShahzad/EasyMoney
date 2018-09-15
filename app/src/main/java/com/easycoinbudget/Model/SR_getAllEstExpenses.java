


package com.easycoinbudget.Model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


public class SR_getAllEstExpenses {


    @SerializedName("estamount")
    @Expose
    private String estamount;
    @SerializedName("esttype")
    @Expose
    private String esttype;
    @SerializedName("estfrequency")
    @Expose
    private String estfrequency;
    @SerializedName("estdescription")
    @Expose
    private String estdescription;
    @SerializedName("estdate")
    @Expose
    private String estdate;

    public String getEstamount() {
        return estamount;
    }

    public void setEstamount(String estamount) {
        this.estamount = estamount;
    }

    public String getEsttype() {
        return esttype;
    }

    public void setEsttype(String esttype) {
        this.esttype = esttype;
    }

    public String getEstfrequency() {
        return estfrequency;
    }

    public void setEstfrequency(String estfrequency) {
        this.estfrequency = estfrequency;
    }

    public String getEstdescription() {
        return estdescription;
    }

    public void setEstdescription(String estdescription) {
        this.estdescription = estdescription;
    }

    public String getEstdate() {
        return estdate;
    }

    public void setEstdate(String estdate) {
        this.estdate = estdate;
    }

}
