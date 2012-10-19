package com.blee.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	private static Pattern DOMAIN_PATTERN = Pattern.compile("https?://([^/]+).*");

	/**
	 * 生成指定长度由小写字母和数字组成的随机字符串
	 * @param length
	 * @return
	 */
	public static String randomStr(int length){
		char[] chars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
		String result = "";
		for(int i=0; i<length; i++){
			// 生成下标的随机数
			Random random = new Random(); 
			int index = random.nextInt(chars.length); 
			char tempChar = chars[index];
			result += tempChar;  
		}
		return result;
	}
	
	public static String bytesToHex(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_CHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_CHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static byte[] hexToBytes(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		int k = 0;
		for (int i = 0; i < bytes.length; i++) {
			byte high = (byte) (Character.digit(hex.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hex.charAt(k + 1), 16) & 0xff);
			bytes[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return bytes;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		return false;
	}
	
	/**
	 * 参数列表里有任何一个String满足 str==null || "".equals(str.trim())即返回true
	 * @param strs
	 * @return
	 */
	public static boolean isAnyEmpty(String ... strs) {
	    if(strs.length == 0) {
	        return true;
	    }
	    for(int i = 0; i < strs.length; i++) {
	        if(isEmpty(strs[i])) {
	            return true;
	        }
	    }
	    return false;
	}

	public static String joinPath(String path, String file) {
		boolean a = path.charAt(path.length() - 1) == '/';
		boolean b = file.charAt(0) == '/';
		if (a && b) {
			return path + file.substring(1);
		} else if (!a && !b) {
			return path + "/" + file;
		} else {
			return path + file;
		}
	}

	public static boolean equals(String str1, String str2) {
		return (str1 == str2) || (str1 != null && str1.equals(str2));
	}

	public static boolean in(Object thiz, Object... params) {
		for (Object p : params) {
			if (thiz == null && p == null)
				return true;
			if (thiz != null && thiz.equals(p))
				return true;
		}
		return false;
	}
	public static boolean inIgnoreCase(String thiz, String... params) {
		for (String p : params) {
			if (thiz == null && p == null)
				return true;
			if (thiz != null && thiz.equalsIgnoreCase(p))
				return true;
		}
		return false;
	}


	public static int countTrue(boolean... trues) {
		int i = 0;
		for (boolean t : trues) {
			if (t)
				i++;
		}
		return i;
	}
	
	/**
	 * 尝试从url里获取domain
	 * @param url
	 * @return
	 */
	public static String parseDomainFromHttp(String url) {
	    if(isEmpty(url)) {
	        return null;
	    }
	    Matcher matcher = DOMAIN_PATTERN.matcher(url);
	    if(matcher.find()) {
	        return matcher.group(1);
	    }
	    return null;
	}
	
}
