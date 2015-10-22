package br.edu.commons.forcode.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.jboss.resteasy.util.Base64;

public class EncodingUtil {
	
	public static String encode(String s){
		
		try {
            s = DatatypeConverter.printBase64Binary(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode with UTF-8", ex);
        }
		
		return s;
	}
	
	public static String decode(String s){
		
		try {
            s = new String(Base64.decode(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return s;
	}
	
}