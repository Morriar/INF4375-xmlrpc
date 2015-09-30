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
package exercices;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML parsing example using DOMXML.
 */
public class Exercice1_Solution1 {

    public static void main(String[] args) {
        try {

            // Load XML file into a DOM document
            File xmlFile = new File("catalog.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // Get `cd` node list
            NodeList cds = doc.getElementsByTagName("album");
            int length = cds.getLength();

            // Display each node
            System.out.println("This catalog contains " + length + " albums");
            for (int i = 0; i < length; i++) {
                Node cd = cds.item(i);
                if (cd.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) cd;
                    String title = elem.getElementsByTagName("title").item(0).getTextContent();
                    String artist = elem.getElementsByTagName("artist").item(0).getTextContent();
                    Integer year = Integer.parseInt(elem.getElementsByTagName("year").item(0).getTextContent());
                    Double price = Double.parseDouble(elem.getElementsByTagName("price").item(0).getTextContent());
                    System.out.println(" * " + title + " - " + artist + " (" + year + ") " + price + "$");
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println(ex);
        }
    }
}
