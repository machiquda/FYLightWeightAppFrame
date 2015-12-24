package fengyu.cn.library.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5String {

	public static String MD5Encoding(String s) {
		String result = "";

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			md.update(s.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return result;
	}
}
