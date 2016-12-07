package io.swagger.utility;

/**
 * Created by IBM on 12/7/2016.
 */
public class Utility {
	public static boolean isNullOrEmpty(String a) {
		if (a == null) {
			return true;
		}
		if (a.trim().equals("")) {
			return true;
		}

		return false;
	}
}
