package com.pukkaspice.web.dao.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pukkaspice.web.common.model.user.PasswordResetWindow;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.model.user.VerificationRecord;
import com.pukkaspice.web.common.security.UserRole;

public interface UserMapper {
    
    UserSummary getUserSummaryByEmailAddress(@Param("emailAddress") String emailAddress, @Param("hashedPassword") String hashedPassword);
    
    UserSummary getUserById(@Param("userId") int userId);
    
    List<UserRole> getUserRoles(@Param("userId") int userId);
    
    void insertUserRole(@Param("userId") int userId, @Param("userRole") UserRole userRole);
    
    void insertNewUserSummary(@Param("userSummary") UserSummary userSummary, @Param("hashedPassword") String hashedPassword);
    
    VerificationRecord getEmailAddressVerificationRecord(@Param("verificationKey") String verificationKey);
    
    void insertEmailAddressVerificationRecord(@Param("userId") int userId, @Param("verificationKey") String verificationKey);
    
    void updateEmailAddressVerificationRecord(VerificationRecord verificationRecord);
    
    Boolean isUserExists(@Param("emailAddress") String emailAddress);

    void insertPasswordResetWindow(PasswordResetWindow passwordResetWindow);
    
    PasswordResetWindow getPasswordResetWindow(@Param("resetKey") String resetKey);

    void updateUserPassword(@Param("emailAddress") String emailAddress, @Param("hashedPassword") String hashedPassword);

    void updatePasswordResetWindow(PasswordResetWindow passwordResetWindow);

    void updateUserSummary(UserSummary userSummary);

}
