package com.jersey.rest.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.jersey.rest.api.config.ApplicationConfig;
import com.jersey.rest.api.utils.PropertyFileReader;



public class Application {

    public static void main(String[] args) {

        // Set the hostname and port for Jetty server
        String host = PropertyFileReader.getPropertyValue("server.host");
        int port = Integer.parseInt(PropertyFileReader.getPropertyValue("server.port"));

        // Create a Jetty server instance
        Server server = new Server();

        // Create a ServerConnector with a specific host and port
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(host);
        connector.setPort(port);
        connector.setReuseAddress(true);

        // Set the ServerConnector to the server
        server.addConnector(connector);

        // Create a servlet context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new ServletContainer(new ApplicationConfig())), "/*");

        // Set the context for the Jetty server
        server.setHandler(context);

        try {
            // Start the Jetty server
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Stop the Jetty server on exit
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
