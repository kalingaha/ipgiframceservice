/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.mobitel.model;

/**
 *
 * @author Dhanushkan
 */
public class CallBackRequest {

    private String mobileNo;
    private String paymentType;
    private String accountNo;
    private String merchantName;
    private double amount;
    private String refNo;
    private int tansactionStatus;
    private String description;
    private String partnerCode;
    private String partnerTxnId;
    private String paymentFor;
    private String cusContactNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public int getTansactionStatus() {
        return tansactionStatus;
    }

    public void setTansactionStatus(int tansactionStatus) {
        this.tansactionStatus = tansactionStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerTxnId() {
        return partnerTxnId;
    }

    public void setPartnerTxnId(String partnerTxnId) {
        this.partnerTxnId = partnerTxnId;
    }

    public String getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    public String getCusContactNo() {
        return cusContactNo;
    }

    public void setCusContactNo(String cusContactNo) {
        this.cusContactNo = cusContactNo;
    }

    @Override
    public String toString() {
        return "CallBackRequest{" + "mobileNo=" + mobileNo + ", paymentType=" + paymentType + ", accountNo=" + accountNo + ", merchantName=" + merchantName + ", amount=" + amount + ", refNo=" + refNo + ", tansactionStatus=" + tansactionStatus + ", description=" + description + ", partnerCode=" + partnerCode + ", partnerTxnId=" + partnerTxnId + ", paymentFor=" + paymentFor + ", cusContactNo=" + cusContactNo + '}';
    }

}
