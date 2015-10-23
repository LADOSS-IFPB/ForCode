package br.edu.web.forcode.service;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import br.edu.commons.forcode.entities.User;
import br.edu.web.forcode.bean.util.BeanUtil;

public class ProviderServiceFactory {
	
	private static final String END_POINT = ProviderServiceFactory.getEndPoints().get(0);
	
	private static final String URL_SERVICE = END_POINT 
			+ "/ForCode_SERVICE/";

	static {
		/* Precisa ser chamado uma única vez para registrar providers RESTEasy,
		 * scanear classes, etc
		 */
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	}

	public static <T> T createServiceClient(Class<T> serviceType) {
		return createServiceClient(URL_SERVICE, serviceType);
	}

	/**
	 * A factory of clients to our REST services.
	 * 
	 * @author Rhavy Maia Guedes
	 * @author Eri Jonhson
	 */
	public static <T> T createServiceClient(String serviceUrl,
			Class<T> serviceType) {
		
		User user = (User) BeanUtil.getSessionValue("user");
		ResteasyClient client;
		
		if (user != null && user.getUserKey() != null){
			client = new ResteasyClientBuilder().register(new Authenticator(user.getUserKey())).build();
		}else{
			client = new ResteasyClientBuilder().build();
		}
		
		ResteasyWebTarget target = client.target(serviceUrl);

		return target.proxy(serviceType);
	}
	
	
	private static List<String> getEndPoints() {

		ArrayList<String> endPoints = new ArrayList<String>();

		try {

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			Set<ObjectName> objs = mbs.queryNames(new ObjectName(
					"*:type=Connector,*"), Query.match(Query.attr("protocol"),
					Query.value("HTTP/1.1")));

			String hostname = InetAddress.getLocalHost().getHostName();

			InetAddress[] addresses = InetAddress.getAllByName(hostname);

			
			for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
				ObjectName obj = i.next();
				
				String scheme = mbs.getAttribute(obj, "scheme").toString();
				
				String port = obj.getKeyProperty("port");
				
				for (InetAddress addr : addresses) {
					String host = addr.getHostAddress();
					String ep = scheme + "://" + host + ":" + port;
					endPoints.add(ep);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return endPoints;
	}
}
