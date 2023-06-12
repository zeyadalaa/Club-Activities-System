package org.bibalex.Configuration;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jakarta.servlet.ServletContext;

public class Configuration {
    private static Properties properties;

    
    public static void loadConfig(ServletContext servletContext) {
        properties = new Properties();
        try (InputStream inputStream = servletContext.getResourceAsStream("/WEB-INF/Config.xml")) {
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            // Access the admin configuration properties
            NodeList nodeList = doc.getElementsByTagName("admin");
            Node node = nodeList.item(0);
            Element element = (Element) node;
            properties.setProperty("admin.username", element.getElementsByTagName("username").item(0).getTextContent());
            properties.setProperty("admin.password", element.getElementsByTagName("password").item(0).getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
    
    public static String getAdminUsername() {
        return properties.getProperty("admin.username");
    }

    public static String getAdminPassword() {
        return properties.getProperty("admin.password");
    }
}
