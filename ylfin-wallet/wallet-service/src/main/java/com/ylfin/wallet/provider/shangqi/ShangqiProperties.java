package com.ylfin.wallet.provider.shangqi;

import lombok.Data;

import java.net.URI;

@Data
public class ShangqiProperties {

    private String agentCode;

    private String intMhtCode;

    private String secret;

    private URI apiGateway;

    private String backEndUrl;

    private String frontEndUrl;
}
