package br.edu.web.forcode.bean.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.commons.forcode.entities.Institution;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@FacesConverter(value = "institutionConverter", forClass = Institution.class)
public class InstitutionConverter implements Converter {

	private static ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		if (value != null) {
			Institution institution = (Institution) service.searchInstitution(
					value).readEntity(Institution.class);
			System.out.println(institution.toString());
			return institution;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object object) {
		return ((Institution) object).getName();
	}
}
