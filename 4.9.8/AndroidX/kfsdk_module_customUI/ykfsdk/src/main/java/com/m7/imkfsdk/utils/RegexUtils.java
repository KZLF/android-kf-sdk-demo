package com.m7.imkfsdk.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by longwei on 2016/3/18.
 */
public class RegexUtils {

    /**
     * 验证Email
     * @param email email地址，格式：zhang@gmail.com，zhang@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */

    public static boolean checkIdCard(String idCard)
    {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex,idCard);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *电信的号段：133、153、180（未启用）、189
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[3458]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }

    /**
     * 验证固定电话号码
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * 国家（地区） 代码 ：标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *  数字之后是空格分隔的国家（地区）代码。
     * 区号（城市代码）：这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。
     * 电话号码：这包含从 0 到 9 的一个或多个数字
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证整数（正整数和负整数）
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex,digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex,decimals);
    }

    /**
     * 验证空白字符
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex,blankSpace);
    }

    /**
     * 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex,chinese);
    }

    /**
     * 验证日期（年月日）
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex,birthday);
    }

    /**
     * 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {

//        String regex = "\"^(?i)(https?|ftp?|http?|www)://.*$\"";

        return url.startsWith("https")
                |url.startsWith("http")
                |url.startsWith("ftp")
                |url.startsWith("www");
//        return Pattern.matches(regex, url);
    }

    /**
     *      * 获取网址 URL 的一级域名
     * http://detail.tmall.com/item.htm?spm=a230r.1.10.44.1xpDSH&id=15453106243&_u=f4ve1uq1092 ->> tmall.com
     *
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        // 获取完整的域名
        // Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }
    /**
     * 匹配中国邮政编码
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

    //是否包含 . 号
    public static boolean checkContainsDot(String username) {
        return   username.contains(".");
    }

    //是否包含连词符
    public static boolean checkContainsHyphen(String username) {
        return   username.contains("-");
    }

    //密码长度 6-20
    public static boolean checkUserPasswordLength(String pwd) {
        return   pwd.length() > 5 && pwd.length() <21;
    }

    public static boolean isValidUserName(String un)
    {
        String regex = "([A-Z0-9a-z-]|[\\u4e00-\\u9fa5])+";
        return Pattern.matches(regex, un);
    }


    //过滤 只保留其中的数字
    public static  String  regexNumber(String a) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        return  m.replaceAll("").trim();
    }

    public static String getServicePhoneRegexp() {
        String servicePhoneRegexp = "(?:((400)+(\\-?[0-9]{3})+([0-9]{1})?+(\\-?[0-9]{3,4})))|" +
                "(?:((400)+([0-9]{1})?+(\\-?[0-9]{3})+(\\-?[0-9]{3,4})))";
        return servicePhoneRegexp;
    }
    /**
     * 固定电话正则表达式
     * 区号 + ‘-’ + 固定电话  + ‘-’ + 分机号
     * 区号 + ‘-’ + 固定电话
     * 区号 + 固定电话
     *
     * @return
     */
    public static String getLandlinePhoneRegexp() {
        String landlinePhoneRegexp = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)+([2-9][0-9]{6,8})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)+([2-9][0-9]{6,8})+(\\-[0-9]{1,4})?)";
        return landlinePhoneRegexp;
    }
    /**
     * 获得电话号码的正则表达式：包括固定电话和移动电话，和400电话
     *
     * @return 电话号码的正则表达式
     */
    public static String isPhoneRegexp() {
        String regexp = "";

        //能满足最长匹配，但无法完成国家区域号和电话号码之间有空格的情况
        String mobilePhoneRegexp = getMobilePhoneRegexp();

        //固定电话正则表达式
        String landlinePhoneRegexp = getLandlinePhoneRegexp();

        //400电话正则表达式
        String servicePhoneRegexp = getServicePhoneRegexp();
        regexp += "(?:" + mobilePhoneRegexp + "|" + landlinePhoneRegexp + "|" + servicePhoneRegexp +"|\\d{5,}" +")";

        return regexp;
    }

    /**
     * 移动电话正则表达式
     * 能满足最长匹配，但无法完成国家区域号和电话号码之间有空格的情况
     * 86+‘-’+11位电话号码(包括XXX-XXXX-XXXX)
     * 86+11位正常的电话号码(包括XXX-XXXX-XXXX)
     * 11位正常电话号码(包括XXX-XXXX-XXXX)
     * (+86) + 11位电话号码(包括XXX-XXXX-XXXX)
     * (86) + 11位电话号码(包括XXX-XXXX-XXXX)
     *
     * @return
     */
    public static String getMobilePhoneRegexp() {
        String mobilePhoneRegexp = "(?:(\\(\\+?86\\))((13[0-9]{1})|(15[0-9]{1})|(170)|(18[0,5-9]{1}))+(\\-?[0-9]{4})+(\\-?[0-9]{4}))|" +
                "(?:86-?((13[0-9]{1})|(15[0-9]{1})|(170)|(18[0,5-9]{1}))+(\\-?[0-9]{4})+(\\-?[0-9]{4}))|" +
                "(?:((13[0-9]{1})|(15[0-9]{1})|(170)|(18[0,5-9]{1}))+(\\-?[0-9]{4})+(\\-?[0-9]{4}))";
        return mobilePhoneRegexp;
    }
//    //匹配关键词
//    public static SpannableString matchSearchText(int color, String txt, String keyword) {
//        SpannableString spannableString = new SpannableString(txt);
//        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(spannableString);
//        while (matcher.find()) {
//            int start = matcher.start();
//            int end = matcher.end();
//            spannableString.setSpan(new ForegroundColorSpan(color),
//                    start,
//                    end,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        return spannableString;
//    }


    //匹配关键词
    public static SpannableString matchSearchText(int color, String txt, String keyword) {
        SpannableString spannableString = new SpannableString(txt);
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？\\\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keyword);
        String regex = m.replaceAll("");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new ForegroundColorSpan(color),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
}
