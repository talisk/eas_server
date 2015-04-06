package com.iot.security;

public interface Encrypt {

    byte[] encryptBytes(byte[] buffer);

    byte[] encryptBefaultBytes(byte[] buffer);
    
}