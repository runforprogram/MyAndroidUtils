package com.utils.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private Utils() {

    }
    public static String formatLongToTimeStr(int millisecond) {
        long second = (millisecond / 1000) % 60;
        long minute = (millisecond / (1000 * 60)) % 60;
        long hour = (millisecond / (1000 * 60 * 60)) % 24;
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            return String.format("%02d:%02d", minute, second);
        }
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes("UTF-8"));
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                if ((b & 0xFF) < 0x10)
                    hexString.append("0");
                hexString.append(Integer.toHexString(b & 0xFF));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    public boolean isNumber(char c) {
        boolean isNumber = false;
        if (c >= '0' && c <= '9') {
            isNumber = true;
        }
        return isNumber;
    }
    public boolean isNumberString(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * is a mail string .
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isMail(String email) {
        String regexmail = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regexmail);
        Matcher m = p.matcher(email);
        return m.matches();

    }

    /**
     * Is mobile number .
     *
     * @param mobile the mobile
     * @return the boolean
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[^1,^4]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * string is is null.note:"null" and "" will return true
     *
     * @param str the string
     * @return the boolean
     */
    public boolean isNull(String str) // 字符串是否为空
    {
        if (null == str || "".equals(str.trim()) || "null".equals(str)) {
            return true;
        }
        return false;
    }
}
