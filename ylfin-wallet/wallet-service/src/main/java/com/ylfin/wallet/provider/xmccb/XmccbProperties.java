package com.ylfin.wallet.provider.xmccb;

import com.ylfin.core.tool.security.KeyPairUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.security.PrivateKey;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "wallet.xmccb")
public class XmccbProperties {

    private String platformNo;

    private URI gateway;

    private URI service;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public URI getGateway() {
        return gateway;
    }

    public void setGateway(URI gateway) {
        this.gateway = gateway;
    }

    public URI getService() {
        return service;
    }

    public void setService(URI service) {
        this.service = service;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        try {
            this.publicKey = KeyPairUtils.loadX509PublicKey(publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        try {
            this.privateKey = KeyPairUtils.loadPKCS8PrivateKey(privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
