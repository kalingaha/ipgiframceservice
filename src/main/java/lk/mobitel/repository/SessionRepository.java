package lk.mobitel.repository;

import lk.mobitel.common.ApplicationConstant;
import lk.mobitel.model.TxnSessionDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import lk.mobitel.model.CallBackDetails;
import lk.mobitel.model.PayForGoods;
import lk.mobitel.model.PayForServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static Logger LOG = Logger.getLogger(SessionRepository.class);

    public List<TxnSessionDetails> findAllSessions() {
        String query = ApplicationConstant.GETALLSESSIONQUERY;
        List<TxnSessionDetails> sessionList = jdbcTemplate.query(query, new SessionMapper());
        return sessionList;
    }

    public TxnSessionDetails findSessionByKey(String key) {
        String query = ApplicationConstant.GETONESESSIONBYKEY;
        Object[] params = new Object[]{key, ApplicationConstant.STATUS};
        TxnSessionDetails session = new TxnSessionDetails();
        session.setKey("");
        try {
            session = jdbcTemplate.queryForObject(query, params, new SessionMapper());
            LOG.info("Find session by key :- " + session);
        } catch (EmptyResultDataAccessException ex) {
            LOG.info("Session cannot find by key :- " + ex.getMessage());
        }
        return session;

    }

    public String findPaymentType(String merchantShortCode, String mmc) {
        String query = ApplicationConstant.GETPAYMENTTYPEQUERY;
        Object[] params = new Object[]{merchantShortCode, mmc};
        String paymentType = null;
        try {
            paymentType = jdbcTemplate.queryForObject(query, params, String.class);
        } catch (EmptyResultDataAccessException ex) {
            LOG.info("Payment type cannot find :- " + ex.getMessage());
        }
        return paymentType;

    }

    public boolean addSessionForServicesPayment(PayForServices session) {
        LOG.info("********Starting Generate URL********");
        String query = ApplicationConstant.INSERTSESSIONQUERYFORSERVICES;

        Object[] params = new Object[]{session.getAccount(), session.getAmount(), session.getExpiredTime(),
            session.getKey(), session.getWalletNo(), session.getMerMobileNo(), session.getPaymentType(), session.getUrl(), session.getRequestedBy(),
            session.getRequestedTime(), session.getStatus(), session.getPartnerTxnId(), session.getOtp(), session.getMedia(), session.getCusContactNo()};

        boolean dataInserted = false;
        try {
            jdbcTemplate.update(query, params);
            dataInserted = true;
        } catch (DuplicateKeyException ex) {
            LOG.info("Data insertion failed, cannot generate URL :- " + ex.getMessage());
        }
        return dataInserted;
    }

    public boolean addSessionForGoodsPayment(PayForGoods session) {
        LOG.info("********Starting Generate URL********");
        String query = ApplicationConstant.INSERTSESSIONQUERYFORGOODS;
//INSERT INTO MWT_TXN_REQ_SESSION_INFO (AMOUNT,EXPIREDTIME,KEY,MOBILE,MERCHANTMOBILE,URL,REQUESTBY,REQUESTEDTIME,STATUS,
//PARTNER_TXN_ID,PAYMENTFOR,OTP,MEDIA) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)
        Object[] params = new Object[]{session.getAmount(), session.getExpiredTime(), 
            session.getKey(), session.getCusWalletNo(), session.getMerWalletNo(), session.getUrl(), 
            session.getRequestedBy(), session.getRequestedTime(), session.getStatus(), session.getPartnerTxnId(),
            session.getPaymentFor(),session.getOtp(),session.getMedia(),session.getCusContactNo()};

        boolean dataInserted = false;
        try {
            jdbcTemplate.update(query, params);
            dataInserted = true;
        } catch (DuplicateKeyException ex) {
            LOG.info("Data insertion failed, cannot generate URL :- " + ex.getMessage());
        }
        return dataInserted;
    }

    public int getSequence() {
        String query = ApplicationConstant.DBSEQUENCEQUERY;
        int sequence = 0;
        try {
            sequence = jdbcTemplate.queryForObject(query, int.class);
        } catch (Exception ex) {
            LOG.info("Sequence cannot generate :- " + ex.getMessage());
        }
        return sequence;
    }

    public String findMerchantNameByMobileNo(String merMobile) {
        String query = ApplicationConstant.GETMERCHANTNAMEQUERY;
        Object[] params = new Object[]{merMobile};
        String merchantName = null;

        try {
            merchantName = jdbcTemplate.queryForObject(query, params, String.class);
        } catch (EmptyResultDataAccessException ex) {
            LOG.info("Cannot find merchant name :- " + ex.getMessage());
        }
        return merchantName;
    }

    public void updateSession(String key, String status,String txnRef,Timestamp executedTime) {
        String query = ApplicationConstant.UPDATESESSIONQUERY;
        Object[] params = new Object[]{executedTime, status, txnRef, key};
        try {
            jdbcTemplate.update(query, params);
        } catch (Exception ex) {
            LOG.info("Session cannot update :- " + ex.getMessage());
        }
    }

    public boolean findWallet(String walletNumber) {
        String query = ApplicationConstant.GETWALLETQUERY;
        Object[] params = new Object[]{walletNumber, ApplicationConstant.ACCOUNT_STAT};

        boolean wallet = false;
        try {
            String verifiedWallet = jdbcTemplate.queryForObject(query, params, String.class);
            if (verifiedWallet != null) {
                wallet = true;
            }
        } catch (EmptyResultDataAccessException ex) {
            LOG.info("Cannot find this wallet number :- " + ex.getMessage());
        }
        return wallet;
    }

    public CallBackDetails findCallBackUrl(String partnerCode) {
        String query = ApplicationConstant.GETCALLBACKURLQUERY;
        Object[] param = new Object[]{partnerCode};
        CallBackDetails callBackUrl = null;
        try {
            callBackUrl = jdbcTemplate.queryForObject(query, param, new CallbackDetailsMapper());
        } catch (EmptyResultDataAccessException ex) {
            LOG.info("Cannot find callback url :-" + ex.getMessage());
        }

        return callBackUrl;
    }

    public boolean findpartnerByCodeAndPassword(String partnerCode, String partnerPasswordEncoded) {

        String query = ApplicationConstant.GETPARTNERQUERY;
        Object[] param = new Object[]{partnerCode, partnerPasswordEncoded};

        boolean partner = false;
        try {
            jdbcTemplate.queryForObject(query, param, String.class);
            partner = true;
        } catch (Exception e) {
            LOG.info("Cannot find partnet for this code and password : " + e.getMessage());
        }
        return partner;
    }

    public String findWalletTypeByMobileNo(String mobileNumber) {

        String query = ApplicationConstant.GETWALLETTYPEQUERY;
        Object[] params = new Object[]{mobileNumber};

        String walletType = null;
        try {
            walletType = jdbcTemplate.queryForObject(query, params, String.class);
        } catch (EmptyResultDataAccessException ex) {
            LOG.info("Cannot find wallet type :- " + ex.getMessage());
        }
        return walletType;
    }

    public boolean checkRecaptcha(String merchantId) {
        Object[] params=new Object[]{merchantId};
        String query="SELECT RECAPTCHA FROM MWT_IPG_MERCHANT WHERE MERCHANT_ID=?";
        
        boolean result=false;
        try{
            result=Boolean.valueOf(jdbcTemplate.queryForObject(query, params, String.class));
        }catch(Exception ex){
            LOG.info(ex.getMessage());
        }
        return result;
    }

    private static final class SessionMapper implements RowMapper<TxnSessionDetails> {

        @Override
        public TxnSessionDetails mapRow(ResultSet rs, int i) throws SQLException {
            TxnSessionDetails session = new TxnSessionDetails();
            session.setKey(rs.getString("KEY"));
            session.setOtp(rs.getString("OTP"));
            session.setRequestedTime(rs.getTimestamp("REQUESTEDTIME"));
            session.setExpiredTime(rs.getTimestamp("EXPIREDTIME"));
            session.setExecutedTime(rs.getTimestamp("EXECUTEDTIME"));
            session.setStatus(rs.getString("STATUS"));
            session.setTxnRef(rs.getString("TXNREF"));
            session.setPaymentType(rs.getString("PAYMENTTYPE"));
            session.setAccount(rs.getString("ACCOUNT"));
            session.setAmount(rs.getDouble("AMOUNT"));
            session.setRequestedBy(rs.getString("REQUESTBY"));
            session.setMedia(rs.getString("MEDIA"));
            session.setMobile(rs.getString("MOBILE"));
            session.setMerMobile(rs.getString("MERCHANTMOBILE"));
            session.setUrl(rs.getString("URL"));
            session.setPartnerTxnId(rs.getString("PARTNER_TXN_ID"));
            session.setPaymentFor(rs.getString("PAYMENTFOR"));
            session.setCusContactNo(rs.getString("CUS_CONTACT_NO"));
            return session;
        }

    }

    private static final class CallbackDetailsMapper implements RowMapper<CallBackDetails> {

        @Override
        public CallBackDetails mapRow(ResultSet rs, int i) throws SQLException {
            CallBackDetails callbackDetails = new CallBackDetails();
            callbackDetails.setCallBackUrl(rs.getString("CALLBACK_URL"));
            callbackDetails.setHeaderToken(rs.getString("HEADER_TOKEN"));
            return callbackDetails;
        }

    }
}
