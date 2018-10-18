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
public class PayForServices extends InitialTxnSession {

    private String paymentType;
    private String account;
    private String walletNo;
    private String merMobileNo;

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

    public String getWalletNo() {
        return walletNo;
    }

    public void setWalletNo(String walletNo) {
        this.walletNo = walletNo;
    }

    public String getMerMobileNo() {
        return merMobileNo;
    }

    public void setMerMobileNo(String merMobileNo) {
        this.merMobileNo = merMobileNo;
    }

    @Override
    public String toString() {
        return "PayForServices{" + "paymentType=" + paymentType + ", account=" + account + ", walletNo=" + walletNo + ", merMobileNo=" + merMobileNo + '}';
    }

}
