/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyhakanto.jee.serveur;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

/**
 *
 * @author nyhakanto
 */
public class WebServer {

    private Server server;

    public WebServer(int port) {
        this.server = new Server(port);
        server.setHandler(new WebServerHandler());
    }

    public void start() throws Exception {
        this.server.start();
    }

    public void startAndWait() throws Exception {
        start();
        this.server.join();
    }

    public void stop() throws Exception {
        this.server.stop();
    }

    private static class WebServerHandler extends AbstractHandler {

        private static final String QUERY_PARAMETER = "q";

        @Override
        public void handle(
            String target, 
            HttpServletRequest request, 
            HttpServletResponse response, 
            int dispatch
        ) throws IOException, ServletException {
        
            String question = request.getParameter(QUERY_PARAMETER);
            String answer = "Bad question ...";
            if ("Quelle est ton adresse email".equals(question)) {
                answer = "nyhakanto@hotmail.fr";
            }
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter writer = response.getWriter();
            writer.println(answer);
            writer.close();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv("PORT"));
        WebServer webServer = new WebServer(port);
        webServer.startAndWait();
    }
}