/*
 * Copyright 2015 Alexandre Terrasa <alexandre@moz-code.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Catalog representation that contains Albums.
 */
public class Catalog {

    // Load a Catalog from a path or abort if error.
    public static Catalog load(String xmlPath) {
        Catalog catalog;
        try {
            catalog = new Catalog(xmlPath);
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            System.err.println("Error: Cannot load xml file " + xmlPath);
            System.err.println("Cause: " + ex.toString());
            System.exit(1);
            return null;
        }
        return catalog;
    }

    public List<Album> albums = new ArrayList<>();

    // Initialize Catalog from a xml file.
    public Catalog(String filePath) throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        buildCatalogFromXml(document);
    }

    // Initialize a new empty Catalog.
    public Catalog() {
    }

    // Initialize Catalog from a xml Document.
    public Catalog(Document document) {
        buildCatalogFromXml(document);
    }

    // Build album list from a xml document.
    private void buildCatalogFromXml(Document document) {
        NodeList nodes = document.getElementsByTagName("album");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                albums.add(new Album(element));
            }
        }
    }

    // Pretty print Catalog size and the Albums it contains.
    public String prettyPrint() {
        StringBuilder str = new StringBuilder();
        System.out.println();
        str.append("This catalog contains ");
        str.append(albums.size());
        str.append(" albums\n");
        for (Album album : albums) {
            str.append(" * ");
            str.append(album.toString());
            str.append("\n");
        }
        return str.toString();
    }

    // Return this Catalog as a new XMlDocument.
    public Document toXml() {
        Document document = XMLUtils.newXmlDocument();
        Element root = document.createElement("catalog");
        for (Album album : albums) {
            root.appendChild(album.toXML(document));
        }
        document.appendChild(root);
        return document;
    }
}
