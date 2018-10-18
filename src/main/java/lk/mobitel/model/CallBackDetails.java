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
public class CallBackDetails {

    private String callBackUrl;
    private String headerToken;

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getHeaderToken() {
        return headerToken;
    }

    public void setHeaderToken(String headerToken) {
        this.headerToken = headerToken;
    }

    @Override
    public String toString() {
        return "CallBackDetails{" + "callBackUrl=" + callBackUrl + ", headerToken=" + headerToken + '}';
    }

}
