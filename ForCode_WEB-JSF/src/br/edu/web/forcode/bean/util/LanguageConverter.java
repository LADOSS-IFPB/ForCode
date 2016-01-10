package br.edu.web.forcode.bean.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.commons.forcode.contests.Language;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@FacesConverter(value = "languageConverter", forClass = Language.class)
public class LanguageConverter implements Converter{


	private static ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		if (value != null) {
			Language language = (Language) service.searchLanguage(value);
			return language;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object object) {
		return ((Language) object).getName();
	}
	
}
