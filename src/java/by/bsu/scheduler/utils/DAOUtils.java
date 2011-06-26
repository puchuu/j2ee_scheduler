package by.bsu.scheduler.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DAOUtils {

	private DAOUtils() {
	}

	public static String md5(String string) {
		byte[] hash = null;

		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(DAOUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(DAOUtils.class.getName()).log(Level.SEVERE, null, ex);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xff) < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(b & 0xff));
		}
		return hex.toString();
	}

	public static String salt(int gen) {
		StringBuilder resBuf = new StringBuilder();
		for (int i = 0; i < Math.random() % gen; i++) {
			resBuf.append(Math.random() % 255);
		}

		String res = DAOUtils.md5(resBuf.toString());
		return res;
	}

	public static boolean isEmailValid(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]{2,4}");
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
