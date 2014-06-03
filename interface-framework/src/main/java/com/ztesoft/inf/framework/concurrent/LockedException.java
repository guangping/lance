package com.ztesoft.inf.framework.concurrent;

import java.util.regex.Pattern;

public class LockedException extends RuntimeException {

	private Object key;

	public LockedException(Object key) {
		super(key.toString());
		this.key = key;
	}

	public Object getKey() {
		return key;
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		String a = "(^[0-9]{3,4}-[0-9]{3,8}$)|(^([0-9]{3,4})[0-9]{3,8}$)|(^1[358][0-9]{9}$)";
		byte[] aa = a.getBytes();
		for (int i = 0; i < aa.length; i++) {
			System.out.print(aa[i]);
		}
		System.out.println("");
		System.out.println(Pattern.matches(a, "111111110791123"));
	}
}

