package com.nium.interview.transfers;

import java.util.Date;

public class Transfer {

    private int source;
    private int destination;
    private float amount;
    private Date date;
    private int transferId;

    public Transfer(int source, int destination, float amount, Date date, int transferId) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.date = date;
        this.transferId = transferId;
    }
    public float getAmount() {
        return amount;
    }
    public Date getDate() {
        return date;
    }
    public int getDestination() {
        return destination;
    }
    public int getSource() {
        return source;
    }
    public int getTransferId() {
        return transferId;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setDestination(int destination) {
        this.destination = destination;
    }
    public void setSource(int source) {
        this.source = source;
    }
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return source + " " + destination + " " + amount + " " + date + " " + transferId;
    }
}

