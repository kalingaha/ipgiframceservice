/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.mobitel.model;

import java.sql.Timestamp;

/**
 *
 * @author Dhanushkan
 */
public class InitialTxnSession {

    private String key;
    private String otp;
    private Timestamp requestedTime;
    private Timestamp expiredTime;
    private String status;
    private double amount;
    private String media;
    private String requestedBy;
    private String url;
    private String partnerTxnId;
    private String cusContactNo;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Timestamp getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Timestamp requestedTime) {
        this.requestedTime = requestedTime;
    }

    public Timestamp getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Timestamp expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPartnerTxnId() {
        return partnerTxnId;
    }

    public void setPartnerTxnId(String partnerTxnId) {
        this.partnerTxnId = partnerTxnId;
    }

    public String getCusContactNo() {
        return cusContactNo;
    }

    public void setCusContactNo(String cusContactNo) {
        this.cusContactNo = cusContactNo;
    }

    @Override
    public String toString() {
        return "Session1{" + "key=" + key + ", otp=" + otp + ", requestedTime=" + requestedTime + ", expiredTime=" + expiredTime + ", status=" + status + ", amount=" + amount + ", media=" + media + ", requestedBy=" + requestedBy + ", url=" + url + ", partnerTxnId=" + partnerTxnId + ", cusContactNo=" + cusContactNo + '}';
    }

}
