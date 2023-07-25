package com.tangyiming.utils;

import com.tangyiming.data.Consts;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PomFileOperatorUtil {
    private static final Logger LOGGER = Logger.getLogger(PomFileOperatorUtil.class.getName());

    /**
     * check if the node exist in pom.xml
     *
     * @param file
     * @param xpathExpression, should build with namespace, be like e.g. "//m:build/m:plugins"
     * @return
     */
    public static boolean checkNodeExits(File file, String xpathExpression) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            XPath xpath = document.createXPath(xpathExpression);
            // Define the namespace
            Namespace namespace = document.getRootElement().getNamespace();
            Map<String, String> m = new HashMap<>();
            m.put("m", namespace.getURI());
            xpath.setNamespaceURIs(m);
            List<Node> properties = xpath.selectNodes(document);
            if (properties.size() > 0) {
                return true;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> getProperties(File file) {
        try {
            List<String> res = new ArrayList<>();
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            // Create a new XPath instance with namespace support
            XPath xpath = document.createXPath("//m:properties/*");
            Namespace namespace = document.getRootElement().getNamespace();
            Map<String, String> m = new HashMap<>();
            m.put("m", namespace.getURI());
            xpath.setNamespaceURIs(m);
            List<Node> properties = xpath.selectNodes(document);
            for (Node property : properties) {
                Element propertyElement = (Element) property;
                res.add(propertyElement.getName() + ":" + propertyElement.getData() + "\n");
            }
            return res;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getBuildPlugins(File file) {
        List<String> res = new ArrayList<>();
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            XPath xpath = document.createXPath("//m:build/m:plugins/*");
            Namespace namespace = document.getRootElement().getNamespace();
            Map<String, String> m = new HashMap<>();
            m.put("m", namespace.getURI());
            xpath.setNamespaceURIs(m);
            List<Node> dependencies = xpath.selectNodes(document);
            for (Node dependency : dependencies) {
                Element dependencyElement = (Element) dependency;
                String groupId = dependencyElement.elementText("groupId").trim();
                String artifactId = dependencyElement.elementText("artifactId").trim();
                res.add(groupId + ":" + artifactId + "\n");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static File createPomFile(String filePath, String content) {
        try {
            File directory = new File(filePath);
            if (!directory.exists()) {
                boolean res = directory.mkdirs();
                if (!res) {
                    LOGGER.log(Level.WARNING, "Failed to create directory.");
                    return null;
                }
            }
            File pomFile = new File(directory, Consts.POM_FILE_NAME);
            FileWriter writer = new FileWriter(pomFile);
            LOGGER.log(Level.INFO, "File path: " + pomFile.getCanonicalPath());
            writer.write(content);
            writer.close();
            return pomFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add a new node to pom.xml
     *
     * @param filePath        e.g. "/home/src/main/resources/pom.xml"
     * @param xpathExpression e.g. should with namespace, convention: "m:properties"
     * @param elementName     e.g. "groupId"
     * @param content         e.g. "com.tangyiming"
     * @return true if success, false otherwise
     */
    public static Boolean addContentToPomNode(String filePath, String xpathExpression, String elementName, String content) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                LOGGER.log(Level.WARNING, "File does not exist.");
                return false;
            }
            if (!filePath.endsWith(".xml")) {
                LOGGER.log(Level.WARNING, "File name should end with .xml");
                return false;
            }
            // Read the existing pom.xml file
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);

            Namespace namespace = document.getRootElement().getNamespace();
            Map<String, String> m = new HashMap<>();
            m.put("m", namespace.getURI());

            XPath xpath = document.createXPath(xpathExpression);
            xpath.setNamespaceURIs(m);
            Node node = xpath.selectSingleNode(document);
            if (node != null) {
                Element targetElement = (Element) node;
                Element newContent = targetElement.addElement(elementName);
                newContent.setText(content);
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer = new XMLWriter(new FileWriter(file), format);
                writer.setEscapeText(false);
                writer.write(document);
                writer.close();
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Target node not found in the XML file.");
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * update value of node in pom.xml
     *
     * @param filePath        e.g. "/home/src/main/resources/pom.xml"
     * @param xpathExpression e.g. should with namespace, convention: "m:properties"
     * @param content         e.g. "com.tangyiming"
     * @return true if success, false otherwise
     */
    public static Boolean updateContentToPomNode(String filePath, String xpathExpression, String content) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                LOGGER.log(Level.WARNING, "File does not exist.");
                return false;
            }
            if (!filePath.endsWith(".xml")) {
                LOGGER.log(Level.WARNING, "File name should end with .xml");
                return false;
            }
            // Read the existing pom.xml file
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);

            Namespace namespace = document.getRootElement().getNamespace();
            Map<String, String> m = new HashMap<>();
            m.put("m", namespace.getURI());

            XPath xpath = document.createXPath(xpathExpression);
            xpath.setNamespaceURIs(m);
            Node node = xpath.selectSingleNode(document);
            if (node != null) {
                Element targetElement = (Element) node;
                targetElement.setText(content);
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer = new XMLWriter(new FileWriter(file), format);
                writer.setEscapeText(false);
                writer.write(document);
                writer.close();
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Target node not found in the XML file.");
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
