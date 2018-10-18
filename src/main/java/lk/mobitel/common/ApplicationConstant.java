package lk.mobitel.common;

public class ApplicationConstant {

    public static final String URL_PATHPARAM_KEY = "/?key=";
    public static final String URL_PATHPARAM_PARTNERCODE = "&partnerCode=";

    public static final String GETSESSIONBYKEYURL = "/mCashIntTxn/{key}";
    public static final String GETALLSESSIONURL = "/mCashIntTxn";
    public static final String GETPAYMENTTYPEURL = "/mCashIntTxn/payment/{code}";
    public static final String GETMERCHANTNAMEURL = "/mCashIntTxn/merchant/{merMobile}";
    public static final String GETCALLBACKURL = "/mCashIntTxn/callback/{partnerCode}";
    public static final String UPDATESESSIONURL = "/mCashIntTxn/update/{key}/{status}/{txnRef}";
//    public static final String ADDSESSIONFORSERVICESURL = "/mCashIntTxn/payForServices/{accNo}/{amount}/{cusMobile}/{txnType}/{partnerCode}/{partnerPassword}/{partnerTxnId}";
    public static final String ADDSESSIONFORSERVICESURL = "/mCashIntTxn/payForServices/{accNo}/{amount}/{walletNumber}/{txnType}/{partnerCode}/{partnerPassword}/{partnerTxnId}/{customerContactNo}";
    public static final String ADDSESSIONFORGOODSURL = "/mCashIntTxn/payForGoods/{amount}/{cusMobile}/{merMobile}/{partnerCode}/{partnerPassword}/{partnerTxnId}/{paymentFor}";

    public static final String CLIENTURLWITHHOSTNAME = "clienturl";
    public static final String TIMEOUT_TIME = "timeoutTime";
    public static final String STATUS = "PENDING";
    public static final String ACCOUNT_STAT = "ACTIVE";
    public static final int SERVICE_SUCCESS_CODE = 1000;
    public static final int ERROR_WALLET_CODE = 1010;
    public static final int ERROR_CUSTOMER_WALLET_CODE = 1011;
    public static final int ERROR_MERCHANT_WALLET_CODE = 1012;
    public static final int ERROR_ACCOUNTTYPE_CODE = 1013;
    public static final int ERROR_CUSTOMER_CONTACT_CODE = 1014;
    public static final int SERVICE_UNIQUE_ERROR_CODE = 1020;
    public static final int INVALID_AMOUNT_CODE = 1030;
    public static final int INCORRECT_PARTNERDETAILS_CODE = 1040;
    public static final String SUCCESS_DESCRIPTION = "SUCCESS";
    public static final String ERROR_CUSTOMER_CONTACT_DESCRIPTION = "FAILED : Invalid Customer Contact Number.";
    public static final String ERROR_CUSTOMER_WALLET_DESCRIPTION = "FAILED : Invalid Customer Wallet Number.";
    public static final String ERROR_MERCHANT_WALLET_DESCRIPTION = "FAILED : Invalid Merchant Wallet Number.";
    public static final String ERROR_ACCOUNTTYPE_DESCRIPTION = "FAILED : Invalid Account Type.";
    public static final String INVALID_AMOUNT_DESCRIPTION = "FAILED : Minimum amount should be 20.00";
    public static final String UNIQUE_ERROR_DESCRIPTION = "FAILED : Partner transaction ID should be unique.";
    public static final String INCORRECT_PARTNERDETAILS_DESCRIPTION = "FAILED : Incorrect Partner Details.";
    public static final String ERROR_WALLET_DESCRIPTION = "FAILED : Invalid Wallet Number.";

    public static final String GETALLSESSIONQUERY = "SELECT * FROM MWT_TXN_REQ_SESSION_INFO";
    public static final String GETONESESSIONBYKEY = "SELECT * FROM MWT_TXN_REQ_SESSION_INFO where key=? AND STATUS=?";
    public static final String GETPAYMENTTYPEQUERY = "select wd.short_name from mwt_merchant_details m left join mwt_user_wallet_details wd on m.acc_id = wd.acc_id where m.mer_short_code =? and m.mcc = ?";
    public static final String GETMERCHANTNAMEQUERY = "select wd.NAME from MWT_USER_WALLET w left join mwt_user_wallet_details wd on w.acc_id = wd.acc_id where w.MOBILE_NO=? AND w.TERMINATED_DATE IS null";
//    public static final String INSERTSESSIONQUERY = "INSERT INTO MWT_TXN_REQ_SESSION_INFO (ACCOUNT,AMOUNT,EXPIREDTIME,KEY,MOBILE,MERCHANTMOBILE,PAYMENTTYPE,URL,REQUESTBY,REQUESTEDTIME,STATUS,PARTNER_TXN_ID,PAYMENTFOR) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERTSESSIONQUERYFORSERVICES = "INSERT INTO MWT_TXN_REQ_SESSION_INFO (ACCOUNT,AMOUNT,EXPIREDTIME,KEY,MOBILE,MERCHANTMOBILE,PAYMENTTYPE,URL,REQUESTBY,REQUESTEDTIME,STATUS,PARTNER_TXN_ID,OTP,MEDIA,CUS_CONTACT_NO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String INSERTSESSIONQUERYFORGOODS = "INSERT INTO MWT_TXN_REQ_SESSION_INFO (AMOUNT,EXPIREDTIME,KEY,MOBILE,MERCHANTMOBILE,URL,REQUESTBY,REQUESTEDTIME,STATUS,PARTNER_TXN_ID,PAYMENTFOR,OTP,MEDIA,CUS_CONTACT_NO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String DBSEQUENCEQUERY = "SELECT SEQ_MWT_TXN_REQ_SESSION_INFO.NEXTVAL FROM dual";
    public static final String UPDATESESSIONQUERY = "UPDATE MWT_TXN_REQ_SESSION_INFO SET EXECUTEDTIME = ?, STATUS = ?, TXNREF = ? WHERE KEY= ?";
    public static final String GETWALLETQUERY = "SELECT MOBILE_NO FROM MWT_USER_WALLET WHERE MOBILE_NO =? AND TERMINATED_DATE IS NULL AND ACCOUNT_STAT=?";
    public static final String GETCALLBACKURLQUERY = "SELECT CALLBACK_URL, HEADER_TOKEN FROM MWT_IPG_MERCHANT WHERE MERCHANT_ID=?";

    public static final String GETPARTNERQUERY = "SELECT MERCHANT_ID FROM MWT_IPG_MERCHANT WHERE MERCHANT_ID=? AND TOKEN_PASSWORD=?";
    public static final String GETWALLETTYPEQUERY = "SELECT WALLET_TYPE FROM MWT_USER_WALLET WHERE MOBILE_NO=? AND ACCOUNT_STAT='ACTIVE' AND TERMINATED_DATE IS NULL AND TERMINATED_BY IS NULL";
}
