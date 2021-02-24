package br.com.zup.propostas.shared.config.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.ZeroSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomEncryptor {

    @Value("${proposal.encryptors.document.secretKey}")
    private String secretKey;
    @Value("${proposal.encryptors.document.salt}")
    private String salt;
    private StandardPBEStringEncryptor encryptor;
    private static CustomEncryptor customEncryptor;

    public String encrypt(String value){
        return this.encryptor.encrypt(value);
    }

    public String decrypt(String value) {
        return this.encryptor.decrypt(value);
    }

    public static CustomEncryptor getInstance() {
        return customEncryptor;
    }

    @PostConstruct
    public void init() {
        customEncryptor = new CustomEncryptor(secretKey, salt);
    }

    public static void testCustomEncryptor(String secretKey, String salt) {
        customEncryptor = new CustomEncryptor(secretKey, salt);
    }

    public CustomEncryptor(String secretKey, String salt) {
        this.secretKey = secretKey;
        this.salt = salt;
        this.encryptor = new StandardPBEStringEncryptor();
        this.encryptor.setAlgorithm("PBEWithMD5AndDES");
        this.encryptor.setSaltGenerator(new ZeroSaltGenerator());
        this.encryptor.setPassword(this.secretKey);
    }

    public CustomEncryptor() {
    }

    public String getSalt() {
        return salt;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
