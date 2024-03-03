package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class WifiAPI {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/wifi", new WifiHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server " + port + " portunda başlatıldı.");
    }

    static class WifiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Bağlı olduğunuz ağdaki diğer ağların isimlerini alın
            List<String> wifiNetworks = getConnectedWifiNetworks();
            
            // JSON formatında wifi ağları oluştur
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("[");
            for (int i = 0; i < wifiNetworks.size(); i++) {
                if (i > 0) {
                    responseBuilder.append(",");
                }
                responseBuilder.append("\"").append(wifiNetworks.get(i)).append("\"");
            }
            responseBuilder.append("]");
            String response = responseBuilder.toString();

            // Yanıtı gönder
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }

        // Bağlı olduğunuz ağdaki diğer ağların isimlerini döndürür
        private List<String> getConnectedWifiNetworks() throws IOException {
            List<String> wifiNetworks = new ArrayList<>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback() && !networkInterface.isVirtual()) {
                    Enumeration<java.net.InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        java.net.InetAddress address = addresses.nextElement();
                        if (address.isSiteLocalAddress()) {
                            wifiNetworks.add(networkInterface.getDisplayName());
                            break;
                        }
                    }
                }
            }
            return wifiNetworks;
        }
    }
}
