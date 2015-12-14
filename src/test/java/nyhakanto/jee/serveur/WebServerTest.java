/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyhakanto.jee.serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import junit.framework.Assert;
import nyhakanto.jee.serveur.WebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nyhakanto
 */
public class WebServerTest {

    private WebServer webServer;
    private int port;

    @Before
    public void setUp() throws Exception {
        this.port = new Random().nextInt(65535);
        this.webServer = new WebServer(port);
        this.webServer.start();
    }

    @After
    public void tearDown() throws Exception {
        this.webServer.stop();
    }

    @Test
    public void question1_should_return_email_address() throws Exception {
        URL url = new URL("http://localhost:" + port + "/?q=Quelle+est+ton+adresse+email");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(200, httpConn.getResponseCode());

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(httpConn.getInputStream())
        );
        Assert.assertEquals("nyhakanto@hotmail.fr", reader.readLine());
        reader.close();
    }
}

