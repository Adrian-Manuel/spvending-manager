package com.smart_padel.spvending_management_api.shared.utils;

public class RegexUtils {
    private RegexUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static final String EMAIL_REGEX = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
}
