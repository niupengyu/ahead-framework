package com.github.niupengyu.core.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



//************************************************************************
//【类　名】　　　　字符串工具集合类
/**
 * 字符串操作方法实现.
 * 
 * @author　　　 yuegy
 * @version　　 2011-02-10
 */
// ************************************************************************
// 【作　成】 　　　　 www.sh-db.com.cn　　　　2011-02-10（R1.00）
// ************************************************************************
public class StrUtil {

    private static final String PASSWORD_CRYPT_KEY = "__fudan_";
    private final static String DES = "DES";
    private final static String MD5 = "MD5";
    public final static String EMPTY = "";
    public final static String PADLEFT = "LEFT";
    public final static String PADRIGHT = "RIGHT";

    /**
     * MD5加密.
     * 
     * @param str
     * @return
     */
    public static String encryptByMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            return "";
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    /**
     * 
     * 加密.
     * 
     * @param src
     *            数据源
     * 
     * @param key
     *            密钥，长度必须是8的倍数
     * 
     * @return 返回加密后的数据
     * 
     * @throws Exception
     * 
     */
    private static byte[] encrypt(byte[] src, byte[] key) throws Exception {

        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        // 现在，获取数据并加密

        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 
     * 解密.
     * 
     * @param src
     *            数据源
     * 
     * @param key
     *            密钥，长度必须是8的倍数
     * 
     * @return 返回解密后的原始数据
     * 
     * @throws Exception
     * 
     */
    private static byte[] decrypt(byte[] src, byte[] key) throws Exception {

        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);

    }

    /**
     * 
     * 密码解密.
     * 
     * @param data
     * 
     * @return
     * 
     * @throws Exception
     * 
     */
    public final static String decryptByDES(String data) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()),
                    PASSWORD_CRYPT_KEY.getBytes()));
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 
     * 密码加密.
     * 
     * @param password
     * 
     * @return
     * 
     * @throws Exception
     * 
     */
    public final static String encryptByDES(String password) {
        try {
            return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY
                    .getBytes()));
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 
     * 二行制转字符串.
     * 
     * @param b
     * 
     * @return
     * 
     */
    public static String byte2hex(byte[] b) {
        String hs = EMPTY;
        String stmp = EMPTY;
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 去掉字符串中的指定字符.
     * 
     * @param src
     *            输入字符串
     * @param chars
     *            要去除的字符
     * 
     * 例：trim("  abcdxxxx", ' ', 'x') ==> "abcd"
     * 
     * @return 输出字符串
     * 
     */
    public static String trim(String src, char... chars) {
        if (src == null) {
            return EMPTY;
        }
        int count = src.length();
        int len = src.length();
        int st = 0;

        while (st < len) {
            if (src.charAt(st) < ' ') {//非打印字符
                st++;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(st) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    st++;
                } else {
                    break;
                }
            }
        }
        while (st < len) {
            if (src.charAt(len - 1) < ' ') {
                len--;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(len - 1) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    len--;
                } else {
                    break;
                }
            }
        }
        return ((st > 0) || (len < count)) ? src.substring(st, len) : src;
    }

    /**
     * 去掉字符串左边的指定字符.
     * 
     * @param src
     *            输入字符串
     * @param chars
     *            要去除的字符
     * 
     * 例：trimLeft("  abcdxxxx", ' ', 'x') ==> "abcdxxxx"
     * 
     * @return 输出字符串
     * 
     */
    public static String trimLeft(String src, char... chars) {
        if (src == null) {
            return EMPTY;
        }
        int count = src.length();
        int len = src.length();
        int st = 0;

        while (st < len) {
            if (src.charAt(st) < ' ') {//非打印字符
                st++;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(st) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    st++;
                } else {
                    break;
                }
            }
        }
        return ((st > 0) || (len < count)) ? src.substring(st, len) : src;
    }

    /**
     * 去掉字符串右边的指定字符.
     * 
     * @param src
     *            输入字符串
     * @param chars
     *            要去除的字符
     * 
     * 例：trimRight("  abcdxxxx", ' ', 'x') ==> "  abcd"
     * 
     * @return 输出字符串
     * 
     */
    public static String trimRight(String src, char... chars) {
        if (src == null) {
            return EMPTY;
        }
        int count = src.length();
        int len = src.length();
        int st = 0;

        while (st < len) {
            if (src.charAt(len - 1) < ' ') {
                len--;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(len - 1) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    len--;
                } else {
                    break;
                }
            }
        }
        return ((st > 0) || (len < count)) ? src.substring(st, len) : src;
    }
    
    /**
     * 判断字符串是空.
     * 
     * @param inputStr
     *            输入字符串
     * 
     * @return boolean
     * 
     */
    public static boolean isEmpty(String inputStr) {
        return null == inputStr || EMPTY.equals(inputStr);
    }

    /**
     * 判断字符串是空.
     * 
     * @param inputStr
     *            输入字符串
     * 
     * @return boolean
     * 
     */
    public static boolean isNotEmpty(String inputStr) {
        return !isEmpty(inputStr);
    }

    /**
     * 字符串左填充.
     * 
     * @param src
     * @param padchar
     * @param len
     * @return
     */
    public static String padLeft(String src, String padchar, int len) {
        if (isEmpty(padchar)) {
            return src;
        }
        String out = src;
        if (isEmpty(out)) {
            out = EMPTY;
        }
        if (len <= 0 || out.length() >= len) {
            return out;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(padchar);
        }
        sb.append(out);
        out = sb.substring(sb.length() - len);
        return out;
    }

    /**
     * 字符串右填充.
     * 
     * @param src
     * @param padchar
     * @param len
     * @return
     */
    public static String padRight(String src, String padchar, int len) {
        if (isEmpty(padchar)) {
            return src;
        }
        String out = src;
        if (isEmpty(out)) {
            out = EMPTY;
        }
        if (len <= 0 || out.length() >= len) {
            return out;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(out);
        for (int i = 0; i < len; i++) {
            sb.append(padchar);
        }
        out = sb.substring(0, len - 1);
        return out;
    }

    /**
     * 
     * 截取字符串指定开始位置的指定长度的子串，null作为""处理.
     * "abcde", 2, 1 ==〉"c"
     * "abcde", 2, 4 ==〉"cde"
     * "abcde", 20, 1 ==〉""
     * 
     * @param src	原字符串
     * @param startindex	开始位置，第一个字符为0
     * @param length	截取的长度
     * @return
     */
    public static String mid(String src, int startindex, int length) {
        if (isEmpty(src))
            return EMPTY;

        if (startindex >= src.length())
            return EMPTY;
        int len = src.length() - startindex;
        if (length < len)
            len = length;

        return src.substring(startindex, len);
    }

    /**
     * 截取左起指定长度的子串，null作为""处理.
     * @param src 	原字符串
     * @param length	截取的长度
     * @return
     */
    public static String left(String src, int length) {
        return mid(src, 0, length);
    }

    /**
     * 截取右起指定长度的子串，null作为""处理.    
     * @param src	原字符串
     * @param length	截取的长度
     * @return
     */
    public static String right(String src, int length) {
        if (isEmpty(src))
            return EMPTY;

        int start = src.length() - length;
        if (start < 0){
            start = 0;
        }
        return mid(src, start, length);
    }

    /**
     * 取得字符串字节长度.
     * 
     * @param text
     * @return
     */
    public static int getLengthAsByte(String text) {
        if (null == text) {
            return 0;
        } else {
            return text.getBytes().length;
        }
    }
    
    /**
     * validateField Method.
     * Method Description:
     * @param field
     * @param length
     * @return
     * @author yuegy
     * @date 2011-3-28
     */
    public static boolean checkField(String field, int length) {
        return length <= getLengthAsByte(field);
    }
    
    /**
     * 检查字符串小数点前不超过intBit位，小数点后不超过decimalBit位.
     * 
     * @param str
     *            检查对象
     * @param IntegerBit
     *            整数部分位数
     * @param decimalBit
     *            小数部分位数
     * @return boolean
     */
    public static boolean checkDataAccuracy(String str, int intBit, int decimalBit) {
        boolean b = false;
        if (str == null || "".equals(str)) {
            return b;
        }
        String s = "^(\\d{0," + intBit + "})(\\.\\d{0," + decimalBit + "})?$";
        Pattern p = Pattern.compile(s);
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    public static String[] subByBit(int inputData) {
        String zhi[] = new String[2];
        switch (inputData) {
            case 0: {
                zhi[0] = "0";
                zhi[1] = "1";
                break;
            }
            case 1: {
                zhi[0] = "1";
                zhi[1] = "0";
                break;
            }
            case 2: {
                zhi[0] = "0";
                zhi[1] = "1";
                break;
            }
            case 3: {
                zhi[0] = "1";
                zhi[1] = "1";
                break;
            }
            default : 
                break;
        }
        return zhi;
    }
    

    public static void main(String[] args) {
    	System.out.println("46f94c8de14fb36680850768ff1b7f2a");
    	System.out.println(encryptByMD5("123456"));
		System.out.println(encryptByMD5("n123456"));
		System.out.println(encryptByMD5("34331234"));
	}
}

