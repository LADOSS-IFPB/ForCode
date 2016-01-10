package br.edu.web.forcode.bean.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class BeanUtil {
	
	public static void setSessionValue(String key, Object value) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);

		session.setAttribute(key, value);
	}

	public static Object getSessionValue(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

		return session.getAttribute(key);
	}
	
	public static void removeSessionValue(String key){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		
		session.removeAttribute(key);
	}
}
