package com.example.e_market;

public class TransactionHistory {
    private String usr_email;
    private String item_name;
    private String sold_qyt;
    private String added_date;

    public String getUsr_email() {
        return usr_email;
    }

    public void setUsr_email(String usr_email) {
        this.usr_email = usr_email;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getSold_qyt() {
        return sold_qyt;
    }

    public void setSold_qyt(String stock_qyt) {
        this.sold_qyt = stock_qyt;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }
}
