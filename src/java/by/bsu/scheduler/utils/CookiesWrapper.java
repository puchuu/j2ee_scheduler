package by.bsu.scheduler.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookiesWrapper {

	public Cookie[] cookies;
	public HashSet<Integer> modified;

	public CookiesWrapper(Cookie[] cookies) {
		this.cookies = cookies;
		this.modified = new HashSet<Integer>();
	}

	public String get(String key) {
		if (this.cookies != null) {
			for (int i = 0; i < this.cookies.length; i++) {
				if (this.cookies[i].getName().equals(key)) {
					return this.cookies[i].getValue();
				}
			}
		}
		return null;
	}

	public void delete(String key, String path) {
		if (this.cookies != null) {
			for (int i = 0; i < this.cookies.length; i++) {
				if (this.cookies[i].getName().equals(key)) {
					this.cookies[i].setPath(path);
					this.cookies[i].setMaxAge(0);
					this.modified.add(i);
				}
			}
		}
	}

	public void save(HttpServletResponse response) {
		for (int i : this.modified) {
			response.addCookie(this.cookies[i]);
		}
	}
}
