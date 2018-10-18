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
public class PayForGoods extends InitialTxnSession {

    private String cusWalletNo;
    private String merWalletNo;
    private String paymentFor;

    public String getCusWalletNo() {
        return cusWalletNo;
    }

    public void setCusWalletNo(String cusWalletNo) {
        this.cusWalletNo = cusWalletNo;
    }

    public String getMerWalletNo() {
        return merWalletNo;
    }

    public void setMerWalletNo(String merWalletNo) {
        this.merWalletNo = merWalletNo;
    }

    public String getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    @Override
    public String toString() {
        return "PayForGoods{" + "cusWalletNo=" + cusWalletNo + ", merWalletNo=" + merWalletNo + ", paymentFor=" + paymentFor + '}';
    }

}
