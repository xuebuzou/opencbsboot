package cn.com.bocd.opencbsboot.tool;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrKit {

    public static String toBinaryString(int x, int minLength) {
        String org = Integer.toBinaryString(x);
        StringBuilder sb = new StringBuilder();
        int paddingCount = minLength - org.length();
        while(paddingCount-- > 0) sb.append("0");
        sb.append(org);
        return sb.toString();
    }

    public static boolean isBlank(String str) {
        return null == str || "".equals(str.trim());
    }

    public static String join(Object[] array, String delim){
        return StringUtils.arrayToDelimitedString(array, delim);
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static String capitalize(String str) {
        if(isBlank(str)) return null;
        return String.valueOf(str.charAt(0)).toUpperCase().concat(str.substring(1));
    }

    private static final Pattern CAMEL_PATTERN = Pattern.compile("[A-Z]([a-z\\d]+)?");

    public static String camel2Underline(String camelStr, boolean upperCase) {

        String capStr = capitalize(camelStr);
        if(isBlank(capStr)) return null;
        StringBuilder sb = new StringBuilder();
        Matcher matcher = CAMEL_PATTERN.matcher(capStr);
        while(matcher.find()){
            sb.append(upperCase ? matcher.group().toUpperCase() : matcher.group());
            sb.append(matcher.end() == capStr.length() ? "" : "_");
        }
        return sb.toString();
    }
}
