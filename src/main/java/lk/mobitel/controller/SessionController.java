package lk.mobitel.controller;

import java.io.UnsupportedEncodingException;
import lk.mobitel.common.ApplicationConstant;
import lk.mobitel.model.InitialResponse;
import lk.mobitel.model.TxnSessionDetails;
import lk.mobitel.service.SessionService;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Base64;

import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lk.mobitel.model.CallBackDetails;
import lk.mobitel.model.CallBackRequest;
import lk.mobitel.model.PayForGoods;
import lk.mobitel.model.PayForServices;
import lk.mobitel.model.InitialTxnSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:apiconfig.properties")
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Value("${clienturl}")
    private String url;
    @Value("${timeoutTime}")
    private String timeoutTime;
    @Value("${accountvalidation.service.url}")
    private String accountValidationServiceUrl;

    private static final Logger LOG = Logger.getLogger(SessionController.class);

    @RequestMapping(method = RequestMethod.GET, value = ApplicationConstant.GETALLSESSIONURL)
    public List<TxnSessionDetails> getAllSession() {
        return sessionService.getAllSession();
    }

    @RequestMapping(method = RequestMethod.GET, value = ApplicationConstant.GETSESSIONBYKEYURL)
    public TxnSessionDetails getSessionByKey(@PathVariable String key) {
        TxnSessionDetails session = sessionService.getSessionByKey(key);
        return session;
    }

    @RequestMapping(method = RequestMethod.GET, value = ApplicationConstant.GETPAYMENTTYPEURL)
    public String getPaymentTypeByCode(@PathVariable String code) {
        return sessionService.getPaymentTypeByCode(code);
    }

    @RequestMapping(method = RequestMethod.GET, value = ApplicationConstant.GETMERCHANTNAMEURL)
    public String getMerchantNameByMobileNo(@PathVariable String merMobile) {
        return sessionService.getMerchantNameByMobileNo(merMobile);
    }

    @RequestMapping(method = RequestMethod.PUT, value = ApplicationConstant.UPDATESESSIONURL)
    public void updateSessionDetails(@PathVariable String key, @PathVariable String status, @PathVariable String txnRef) {

        Timestamp executedTime = new Timestamp(new Date().getTime());

        sessionService.updateSessionDetails(key, status, txnRef, executedTime);
    }

    @RequestMapping(method = RequestMethod.GET, value = ApplicationConstant.GETCALLBACKURL)
    public CallBackDetails getCallBackUrl(@PathVariable String partnerCode) {
        return sessionService.getCallBackUrl(partnerCode);
    }

    @RequestMapping(method = RequestMethod.POST, value = ApplicationConstant.ADDSESSIONFORSERVICESURL)
    public ResponseEntity<InitialResponse> addSessionPayForServices(@PathVariable String walletNumber, @PathVariable String txnType, @PathVariable String accNo, @PathVariable double amount, @PathVariable String partnerCode, @PathVariable String partnerPassword, @PathVariable String partnerTxnId, @PathVariable String customerContactNo) {

        LOG.info("Pay For Services");

        PayForServices session = new PayForServices();
        session.setAccount(accNo);
        session.setPaymentType(txnType);
        session.setAmount(amount);
        session.setWalletNo(walletNumber);

        URL validationServiceUrl = null;
        try {
            validationServiceUrl = new URL(accountValidationServiceUrl);
        } catch (MalformedURLException ex) {
            LOG.info("Cannot find account validation WS URL :- " + ex.getMessage());
        }

        InitialResponse response = new InitialResponse();

        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = pattern.matcher(customerContactNo);

        if (matcher.matches()) {

            if (sessionService.validatePartner(partnerCode, getSHA256Hash(partnerPassword, partnerCode))) {

                if (amountValidation(amount)) {

                    if (sessionService.walletAvailability(walletNumber)) {

                        if (sessionService.accountValidation(session, validationServiceUrl)) {
                            session.setKey(keyGenerator());
                            if (sessionService.getWalletTypeByMobileNo(walletNumber).equalsIgnoreCase("CUS")) {
                                session.setMerMobileNo(null);
                            } else {
                                session.setMerMobileNo(walletNumber);
                            }
                            session.setCusContactNo(customerContactNo);
                            session.setRequestedBy(partnerCode);
                            session.setRequestedTime(new Timestamp(new Date().getTime()));
                            session.setStatus(ApplicationConstant.STATUS);
                            session.setUrl(url + ApplicationConstant.URL_PATHPARAM_KEY + session.getKey() + ApplicationConstant.URL_PATHPARAM_PARTNERCODE + partnerCode);
                            session.setExpiredTime(generateExpiredTime(timeoutTime));
                            session.setPartnerTxnId(partnerTxnId);

                            if (sessionService.addSession(session)) {
                                response.setKey(session.getKey());
                                response.setUrl(session.getUrl());
                                response.setExpireTime(session.getExpiredTime().toString());
                                response.setStatusCode(ApplicationConstant.SERVICE_SUCCESS_CODE);
                                response.setDescription(ApplicationConstant.SUCCESS_DESCRIPTION);
                            } else {
                                response.setStatusCode(ApplicationConstant.SERVICE_UNIQUE_ERROR_CODE);
                                response.setDescription(ApplicationConstant.UNIQUE_ERROR_DESCRIPTION);
                            }
                        } else {
                            response.setStatusCode(ApplicationConstant.ERROR_ACCOUNTTYPE_CODE);
                            response.setDescription(ApplicationConstant.ERROR_ACCOUNTTYPE_DESCRIPTION);
                        }
                    } else {
                        response.setStatusCode(ApplicationConstant.ERROR_WALLET_CODE);
                        response.setDescription(ApplicationConstant.ERROR_WALLET_DESCRIPTION);
                    }
                } else {
                    response.setStatusCode(ApplicationConstant.INVALID_AMOUNT_CODE);
                    response.setDescription(ApplicationConstant.INVALID_AMOUNT_DESCRIPTION);
                }
            } else {
                response.setStatusCode(ApplicationConstant.INCORRECT_PARTNERDETAILS_CODE);
                response.setDescription(ApplicationConstant.INCORRECT_PARTNERDETAILS_DESCRIPTION);
            }
        } else {
            response.setStatusCode(ApplicationConstant.ERROR_CUSTOMER_CONTACT_CODE);
            response.setDescription(ApplicationConstant.ERROR_CUSTOMER_CONTACT_DESCRIPTION);
        }

        return new ResponseEntity<InitialResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = ApplicationConstant.ADDSESSIONFORGOODSURL)
    public ResponseEntity<InitialResponse> addSessionPayForGoods(@PathVariable String cusMobile, @PathVariable String merMobile, @PathVariable double amount, @PathVariable String partnerCode, @PathVariable String partnerPassword, @PathVariable String partnerTxnId, @PathVariable String paymentFor) {

        LOG.info("Pay For Goods");

        InitialResponse response = new InitialResponse();

        if (sessionService.validatePartner(partnerCode, getSHA256Hash(partnerPassword, partnerCode))) {

            if (amountValidation(amount)) {

                if (sessionService.walletAvailability(cusMobile)) {

                    if (sessionService.walletAvailability(merMobile)) {
                        PayForGoods session = new PayForGoods();
                        session.setAmount(amount);
                        session.setKey(keyGenerator());
                        session.setCusWalletNo(cusMobile);
                        session.setMerWalletNo(merMobile);
                        session.setRequestedBy(partnerCode);
                        session.setRequestedTime(new Timestamp(new Date().getTime()));
                        session.setStatus(ApplicationConstant.STATUS);
                        session.setUrl(url + ApplicationConstant.URL_PATHPARAM_KEY + session.getKey() + ApplicationConstant.URL_PATHPARAM_PARTNERCODE + partnerCode);
                        session.setExpiredTime(generateExpiredTime(timeoutTime));
                        session.setPartnerTxnId(partnerTxnId);
                        session.setPaymentFor(paymentFor);
                        session.setCusContactNo(cusMobile);

                        if (sessionService.addSession(session)) {
                            response.setKey(session.getKey());
                            response.setUrl(session.getUrl());
                            response.setExpireTime(session.getExpiredTime().toString());
                            response.setStatusCode(ApplicationConstant.SERVICE_SUCCESS_CODE);
                            response.setDescription(ApplicationConstant.SUCCESS_DESCRIPTION);

                        } else {
                            response.setStatusCode(ApplicationConstant.SERVICE_UNIQUE_ERROR_CODE);
                            response.setDescription(ApplicationConstant.UNIQUE_ERROR_DESCRIPTION);
                        }
                    } else {
                        response.setStatusCode(ApplicationConstant.ERROR_MERCHANT_WALLET_CODE);
                        response.setDescription(ApplicationConstant.ERROR_MERCHANT_WALLET_DESCRIPTION);
                    }
                } else {
                    response.setStatusCode(ApplicationConstant.ERROR_CUSTOMER_WALLET_CODE);
                    response.setDescription(ApplicationConstant.ERROR_CUSTOMER_WALLET_DESCRIPTION);
                }

            } else {
                response.setStatusCode(ApplicationConstant.INVALID_AMOUNT_CODE);
                response.setDescription(ApplicationConstant.INVALID_AMOUNT_DESCRIPTION);
            }

        } else {
            response.setStatusCode(ApplicationConstant.INCORRECT_PARTNERDETAILS_CODE);
            response.setDescription(ApplicationConstant.INCORRECT_PARTNERDETAILS_DESCRIPTION);
        }

        return new ResponseEntity<InitialResponse>(response, HttpStatus.OK);
    }

    private String getSHA256Hash(String partnerPassword, String partnerCode) {

        String hash = null;
        MessageDigest md = null;

        String str = partnerPassword + partnerCode;

        try {

            md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes("UTF-8"));

            byte[] shaDig = md.digest();

            byte[] encoded = Base64.getEncoder().encode(shaDig);
            hash = new String(encoded);

        } catch (NoSuchAlgorithmException ex) {
            LOG.info(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            LOG.info(ex.getMessage());
        } catch (Exception ex) {
            LOG.info("Exception :- "+ex.getMessage());
        }

        return hash;
    }

    public boolean amountValidation(double amount) {
        if (amount >= 20) {
            LOG.info("****** Valid Amount ******");
            return true;
        } else {
            LOG.info("****** Invalid Amount ******");
            return false;
        }
    }

    public Timestamp generateExpiredTime(String timeoutTime) {
        int timeoutMinPeriod = Integer.parseInt(timeoutTime);
        Long timeoutMillSecPeriod = new Long(timeoutMinPeriod * 60 * 1000);
        Long expireTime = new Date().getTime() + timeoutMillSecPeriod;
        return new Timestamp(expireTime);
    }

    public String keyGenerator() {

        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String month = String.format("%02d", date.get(Calendar.MONTH) + 1);
        String day = String.format("%02d", date.get(Calendar.DATE));
        String hour = String.format("%02d", date.get(Calendar.HOUR));
        String minute = String.format("%02d", date.get(Calendar.MINUTE));
        String seconds = String.format("%02d", date.get(Calendar.SECOND));
        String sequence = String.valueOf(sessionService.getSequence());

        String key = year + month + day + hour + minute + seconds + sequence;

        return key;
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/testDeal/{mobileNo}/{paymentType}/{accountNo}/{merchantName}/{amount}/{refNo}")
//    public String callBackRequest(@PathVariable String mobileNo, @PathVariable String paymentType, @PathVariable String accountNo, @PathVariable String merchantName, @PathVariable String amount, @PathVariable String refNo) {
//        return sessionService.callBackRequest(mobileNo, paymentType, accountNo, merchantName, amount, refNo);
//    }
    @RequestMapping(method = RequestMethod.POST, value = "/testDeal")
    public String callBackRequest(@RequestBody CallBackRequest request) {
        LOG.info(request.toString());
        return sessionService.callBackRequest(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getWalletType/{mobileNumber}")
    public String getWalletTypeByMobileNo(@PathVariable String mobileNumber) {
        return sessionService.getWalletTypeByMobileNo(mobileNumber);
    }
}
