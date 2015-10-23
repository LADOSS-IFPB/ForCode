package br.edu.service.forcode.database.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.edu.service.forcode.util.JPAUtil;


public class HibernateListener implements ServletContextListener {  
	  
    public void contextInitialized(ServletContextEvent event) {  
        JPAUtil.getSessionFactory(); 
    }  
  
    public void contextDestroyed(ServletContextEvent event) {  
    	JPAUtil.getSessionFactory().close();
    }  
} 
