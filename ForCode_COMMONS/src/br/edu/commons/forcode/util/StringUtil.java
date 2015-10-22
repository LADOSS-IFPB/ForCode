package br.edu.commons.forcode.util;

public class StringUtil {
	public static boolean isNum(String s){
		 for (char letter : s.toCharArray())  
			 if(letter < '0' || letter > '9')  
				 return false;
		 return true;
	}
}
