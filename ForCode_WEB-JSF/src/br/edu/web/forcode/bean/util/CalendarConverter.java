package br.edu.web.forcode.bean.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.web.forcode.bean.ManagerBean;

@FacesConverter(value = "calendarConverter", forClass = Calendar.class)
public class CalendarConverter implements Converter{

	private static final Logger logger = LogManager.getLogger(ManagerBean.class.getName());
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
			Calendar calendar = Calendar.getInstance();
			try{
				calendar.setTime(sdf.parse(value));
			}catch(ParseException ex){
				logger.warn("Error while trying to convert date: " + value);
			}
			return calendar;
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
