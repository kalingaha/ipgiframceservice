package lk.mobitel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.mobitel.model.TxnSessionDetails;
import lk.mobitel.repository.SessionRepository;
import java.net.URL;
import java.sql.Timestamp;
import lk.mobitel.model.CallBackDetails;
import lk.mobitel.model.CallBackRequest;
import lk.mobitel.model.PayForGoods;
import lk.mobitel.model.PayForServices;
import lk.mobitel.soapclient.UtilValidationService;
import lk.mobitel.soapclient.UtilValidationServiceService;
import lk.mobitel.soapclient.ValidateUtilityRequest;
import org.apache.log4j.Logger;

@Service
public class SessionService {

    private final static Logger LOG = Logger.getLogger(SessionService.class);

    @Autowired
    private SessionRepository sessionRepository;

    public boolean addSession(PayForServices session) {
        return sessionRepository.addSessionForServicesPayment(session);
    }

    public boolean addSession(PayForGoods session) {
        return sessionRepository.addSessionForGoodsPayment(session);
    }

    public List<TxnSessionDetails> getAllSession() {
        return sessionRepository.findAllSessions();
    }

    public TxnSessionDetails getSessionByKey(String key) {
        TxnSessionDetails session = null;
        if (!key.equals("null")) {
            session = sessionRepository.findSessionByKey(key);
            session.setCheckRecaptcha(sessionRepository.checkRecaptcha(session.getRequestedBy()));
        } else {
            session = new TxnSessionDetails("null", null, null, null, null, null, null, null, null, 0, null, null, null, null, null, null, null, null, false);
        }
        return session;
    }

    public String getPaymentTypeByCode(String code) {
        String merchantShortCode = code.substring(0, 6);
        String mmc = code.substring(6);
        return sessionRepository.findPaymentType(merchantShortCode, mmc);
    }

    public int getSequence() {
        return sessionRepository.getSequence();
    }

    public String getMerchantNameByMobileNo(String merMobile) {
        return sessionRepository.findMerchantNameByMobileNo(merMobile);
    }

    public void updateSessionDetails(String key, String status, String txnRef, Timestamp executedTime) {
        sessionRepository.updateSession(key, status, txnRef, executedTime);
    }

    public boolean walletAvailability(String walletNumber) {
        return sessionRepository.findWallet(walletNumber);
    }

    public boolean validatePartner(String partnerCode, String partnerPasswordEncoded) {
        return sessionRepository.findpartnerByCodeAndPassword(partnerCode, partnerPasswordEncoded);
    }

    public boolean accountValidation(PayForServices session, URL validationServiceUrl) {

        LOG.info("Account Validation");
        UtilValidationServiceService locator = new UtilValidationServiceService(validationServiceUrl);
        UtilValidationService utilValidationService = locator.getUtilValidationServiceSoap11();
        ValidateUtilityRequest vcr = new ValidateUtilityRequest();
        vcr.setMobileNumber(session.getWalletNo());
        vcr.setUtilityAccountNumber(session.getAccount());
        vcr.setUtilityAmount(session.getAmount());
        vcr.setUtilityCode(session.getPaymentType());

        boolean isValidAccount = false;
        try {
            isValidAccount = utilValidationService.validateUtility(vcr).isUtilityValid();
        } catch (Exception ex) {
            LOG.info("Account validation service :- " + ex.getMessage());
        }
        return isValidAccount;
    }

    public CallBackDetails getCallBackUrl(String partnerCode) {
        return sessionRepository.findCallBackUrl(partnerCode);
    }

    public String callBackRequest(CallBackRequest request) {

        String response = "Status Code = " + request.getTansactionStatus() + " \nDescription = " + request.getDescription() + ".";
        return response;
    }

    public String getWalletTypeByMobileNo(String mobileNumber) {
        return sessionRepository.findWalletTypeByMobileNo(mobileNumber);
    }

}
