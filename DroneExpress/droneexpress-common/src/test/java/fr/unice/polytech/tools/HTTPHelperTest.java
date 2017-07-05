package fr.unice.polytech.tools;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.tools.HTTPHelper.Method;


public class HTTPHelperTest {

    private HTTPHelper http;

    @Before
    public void setUp() throws Exception {
        http = new HTTPHelper();
    }

    @After
    public void tearDown() throws Exception {
        http = null;
    }

    @Test
    public void testCall() throws MalformedURLException, IOException {
        if (testConnexion()) {
            String res = http.setRequestMethod(Method.GET)
                    .setUrl("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=helloworld")
                    .call();
            assertTrue(!res.isEmpty());
            assertTrue(res.contains("google"));
            assertTrue(res.contains("hello"));
            assertTrue(res.contains("world"));
        } else {
            System.err.println(
                    "No internet connection detected ! Test HTTPHelperTest#testCall ignored");
        }
    }

    public boolean testConnexion() {
        try {
            URL url = new URL("http://www.google.fr");
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
