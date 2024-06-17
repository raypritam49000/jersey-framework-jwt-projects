package com.jersey.rest.api.filters;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Properties props = new Properties();
		InputStream strm = ContextListener.class.getClassLoader().getResourceAsStream("log4j.properties");
		try {
			props.load(strm);
		} catch (IOException propsLoadIOE) {
			throw new Error("can't load logging config file", propsLoadIOE);
		} finally {
			try {
				assert strm != null;
				strm.close();
			} catch (IOException configCloseIOE) {
				throw new Error("error closing logging config file", configCloseIOE);
			}
		}
		props.put("webAppRoot", event.getServletContext().getRealPath("/"));
		PropertyConfigurator.configure(props);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}