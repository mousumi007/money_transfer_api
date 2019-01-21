package com.app.money.transfer.instapay;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.app.money.transfer.dao.TransactionDAO;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
	
	static String LOGPREFIX = "Main |";
	
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8089/v1/instaPay/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() throws IOException{
        // create a resource config that scans for JAX-RS resources and providers
        // in com.app.money.transfer.instapay package
        final ResourceConfig rc = new ResourceConfig().
        		packages("com.app.money.transfer.instapay","com.app.money.transfer.exception");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	
    	LOGPREFIX = LOGPREFIX + " main |";
    	
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        
        System.out.println(LOGPREFIX + TransactionDAO.initializeDAO());
        System.in.read();
        server.stop();
    }
}

