package com.iot.service.impl;

import com.iot.security.PasswordSHA1Utils;
import com.iot.service.UserPasswordService;
import com.iot.utils.MD5Utils;
import com.iot.utils.RandomUtils;
import org.springframework.stereotype.Service;

@Service("userPasswordService")
public class UserPasswordServiceImpl implements UserPasswordService {
    /**
     * 密码进行编码
     *
     * @param password
     * @return
     */
    @Override
    public String passwordEncode(String password, String salt) {
        String passwordEncoded = PasswordSHA1Utils.generatePassword(MD5Utils.encode(salt) + "_IYX_" + password);
        return passwordEncoded;
    }

    /**
     * 生成Salt
     *
     * @return
     */
    @Override
    public String createSalt() {
        try {
            return RandomUtils.getRandomString(6);
        } catch (Exception ex) {
            return "IYX987";
        }
    }
}
