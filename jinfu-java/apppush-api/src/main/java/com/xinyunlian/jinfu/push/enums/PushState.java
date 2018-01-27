package com.xinyunlian.jinfu.push.enums;

/**
 * @author Sephy
 * @since: 2017-01-16
 */
public abstract class PushState {

    public static final int WAIT_SEND = 0;

    public static final int SENDING = 1;

    public static final int SEND_SUCCESSED = 2;

    public static final int SEND_FAILED = 3;
}
