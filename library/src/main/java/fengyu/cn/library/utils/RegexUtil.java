package fengyu.cn.library.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	static final String REGEX_EMAIL = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
	static final String REGEX_PHONE = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";

	public static boolean isEmail(String str) {
		Pattern pattern = Pattern.compile(REGEX_EMAIL);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static boolean isPhone(String str) {
		if (str.length() == 11)
			return true;
		return false;
	}
}
