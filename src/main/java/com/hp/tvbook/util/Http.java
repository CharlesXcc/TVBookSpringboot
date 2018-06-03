/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.tvbook.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

/**
 *
 * @author jcaradec
 */
public class Http {

    private static final Logger log_ = Logger.getLogger(Http.class.getName());

    /**
     * Send a http put with proper header for EVENT interactions
     *
     * @param req
     * @param body
     * @throws java.lang.Exception
     *
     *
     */
    public static String sendAPostRequest(
            String path,
            String body) throws Exception {
        System.out.print("\nPOST Request sent to: " + path + "\n");
        System.out.print("\nPOST Request body : " + body + "\n");

        URL url = new URL(path);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        SSLContext sc = SSLContext.getInstance("TLSv1.2");

        KeyManagerFactory kmf = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance("JKS");

        try {
            String filepath = "file:/opt/OC/tester/cert/keystore.jks";
            URL myUrl = new URL(filepath);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ks.load(input, "secret".toCharArray());
            kmf.init(ks, "secret".toCharArray());
            sc.init(kmf.getKeyManagers(),
                    null, new SecureRandom());
        } catch (Exception e) {

            System.out.print("\nException on SSL: " + e.getMessage());
        }
        final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory
                .getDefaultAlgorithm());
        KeyStore ts = KeyStore.getInstance("JKS");
        try {
            String filepath = "file:/opt/OC/tester/cert/truststore.jks";
            URL myUrl = new URL(filepath);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ts.load(input, "secret".toCharArray());
            tmf.init(ts);
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        } catch (Exception e) {

            System.out.print("\nException on SSL: " + e.getMessage());
        }

        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output, response = "";
        //System.out.println("Output from Server .... \n");
        System.out.print("\nResponse received: ");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            response += output;
        }
        System.out.print("\n");
        conn.disconnect();
        return response;

    }

    

}
