package com.example;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class WifiAPITest {

    /**
     * @throws IOException
     */
    @Test
    public void testWifiAPI() throws IOException {
        // Örnek bir URL oluşturma
        URL url = new URL("http://localhost:8080/wifi");

        // Bağlantı oluşturma
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Yanıtı alma
        int responseCode = connection.getResponseCode();

        // Yanıtı kontrol etme
     // Yanıtı kontrol etme
// Yanıtı kontrol etme
assertEquals(200,  "Bağlantı hatası! Sunucu yanıt kodu: " + responseCode);


        // Yanıtı okuma
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Yanıtı kontrol etme
        assertEquals("Wifi ağları başarıyla alındı.", response.toString(), "Beklenen yanıt alınamadı!");

        // Bağlantıyı kapatma
        connection.disconnect();
    }
}
