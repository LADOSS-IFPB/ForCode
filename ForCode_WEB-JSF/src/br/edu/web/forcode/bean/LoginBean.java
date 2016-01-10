package br.edu.web.forcode.bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.util.EncodingUtil;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "loginBean")
public class LoginBean {

	private String username;
	private String password;
	private static final Logger logger = LogManager
			.getLogger(ContestantBean.class.getName());
	private static ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);

	public String login() throws IOException {
		String username = EncodingUtil.encode(this.username);
		String password = EncodingUtil.encode(this.password);

		Response response = service.login(username, password);

		if (response.getStatusInfo() != Response.Status.OK) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.xhtml");
			return "";
		} else {
			User user = response.readEntity(User.class);
			BeanUtil.setSessionValue("user", user);

			logger.info("Session opened for " + this.username);
			return user.getTypeUser().getTypeName().toLowerCase()
					+ "/home.xhtml?faces-redirect=true";
		}
	}

	public void logout() throws IOException {
		User user = (User) BeanUtil.getSessionValue("user");
		logger.info("Logging " + user.getUsername() + " out");

		service.logout(user);
		BeanUtil.removeSessionValue("user");

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../index.xhtml");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
