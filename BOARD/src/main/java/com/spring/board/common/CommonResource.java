package com.spring.board.common;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

public class CommonResource {
	public static boolean isEmptyString(String arg) {
		return arg == null || arg.trim().length() == 0; 
	}
	
	public static void closeAutoCloseableResource(AutoCloseable...args) {
		for (AutoCloseable arg : args)
			if (arg != null)
				try {arg.close();} catch (Exception e) {} 
	}
	
	public static String getStringFromRequestBody(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			while((line = reader.readLine()) != null)
				sb.append(line);
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
