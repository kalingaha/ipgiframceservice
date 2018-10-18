package lk.mobitel.model;

import java.sql.Timestamp;

public class TxnSessionDetails {

    private String key;
    private String otp;
    private Timestamp requestedTime;
    private Timestamp expiredTime;
    private Timestamp executedTime;
    private String status;
    private String txnRef;
    private String paymentType;
    private String account;
    private double amount;
    private String requestedBy;
    private String media;
    private String mobile;
    private String merMobile;
    private String url;
    private String partnerTxnId;
    private String paymentFor;
    private String cusContactNo;
    private boolean checkRecaptcha;

    public TxnSessionDetails() {
        super();
    }

    public TxnSessionDetails(String key, String account) {
        super();
        this.key = key;
        this.account = account;
    }

    public TxnSessionDetails(String key, String otp, Timestamp requestedTime, Timestamp expiredTime, Timestamp executedTime, String status, String txnRef, String paymentType, String account, double amount, String requestedBy, String media, String mobile, String merMobile, String url, String partnerTxnId, String paymentFor, String cusContactNo, boolean checkRecaptcha) {
        this.key = key;
        this.otp = otp;
        this.requestedTime = requestedTime;
        this.expiredTime = expiredTime;
        this.executedTime = executedTime;
        this.status = status;
        this.txnRef = txnRef;
        this.paymentType = paymentType;
        this.account = account;
        this.amount = amount;
        this.requestedBy = requestedBy;
        this.media = media;
        this.mobile = mobile;
        this.merMobile = merMobile;
        this.url = url;
        this.partnerTxnId = partnerTxnId;
        this.paymentFor = paymentFor;
        this.cusContactNo = cusContactNo;
        this.checkRecaptcha = checkRecaptcha;
    }

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

    public Timestamp getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(Timestamp executedTime) {
        this.executedTime = executedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxnRef() {
        return txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMerMobile() {
        return merMobile;
    }

    public void setMerMobile(String merMobile) {
        this.merMobile = merMobile;
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

    public boolean isCheckRecaptcha() {
        return checkRecaptcha;
    }

    public void setCheckRecaptcha(boolean checkRecaptcha) {
        this.checkRecaptcha = checkRecaptcha;
    }

    @Override
    public String toString() {
        return "TxnSessionDetails{" + "key=" + key + ", otp=" + otp + ", requestedTime=" + requestedTime + ", expiredTime=" + expiredTime + ", executedTime=" + executedTime + ", status=" + status + ", txnRef=" + txnRef + ", paymentType=" + paymentType + ", account=" + account + ", amount=" + amount + ", requestedBy=" + requestedBy + ", media=" + media + ", mobile=" + mobile + ", merMobile=" + merMobile + ", url=" + url + ", partnerTxnId=" + partnerTxnId + ", paymentFor=" + paymentFor + ", cusContactNo=" + cusContactNo + ", checkRecaptcha=" + checkRecaptcha + '}';
    }

}
