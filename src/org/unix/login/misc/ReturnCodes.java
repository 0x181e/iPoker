package org.unix.login.misc;

/**
 * Created by unix on 15/06/14.
 */
public enum ReturnCodes {

    SUCCESS(1),
    INCORRECT_PASSWORD(2),
    INVALID_ACCOUNT(3),
    BANNED(4);

    int code;

    ReturnCodes(int type) {
        this.code = type;
    }

    public int getCode() {
        return code;
    }

}
