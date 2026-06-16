package model;

import java.sql.Timestamp;

public class Invoice {
    public int invoice_id;
    public int customer_id;
    private Timestamp created_at ;
    private double total_amount;

    public Invoice() {
    }

    public Invoice(int invoice_id, int customer_id, Timestamp created_at, double total_amount) {
        this.invoice_id = invoice_id;
        this.customer_id = customer_id;
        this.created_at = created_at;
        this.total_amount = total_amount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }
}
