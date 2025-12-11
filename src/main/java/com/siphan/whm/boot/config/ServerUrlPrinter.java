package com.siphan.whm.boot.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServerUrlPrinter implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") WebServerInitializedEvent event) {
        WebServer webServer = event.getWebServer();
        int port = webServer.getPort();
        String host = "localhost"; // Or use InetAddress.getLocalHost().getHostName() for actual hostname

        // Assuming default context path, adjust if a custom context path is set
        String contextPath = event.getApplicationContext().getServerNamespace();
        if (contextPath == null || contextPath.isEmpty()) {
            contextPath = "/";
        }

        String serverUrl = String.format("http://%s:%d%s", host, port, contextPath);
        System.out.println("Spring Boot server URL: " + serverUrl);
    }
}