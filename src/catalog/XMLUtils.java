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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Useful XML related methods.
 */
public class XMLUtils {

    // Transform a XML `Document` as a `String`.
    public static String xmlToString(Document doc) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (TransformerException ex) {
            System.err.println("Error: Cannot load xml transformer");
            System.err.println("Cause: " + ex.toString());
            System.exit(1);
            return null;
        }
    }

    // Return a new empty document or abort if call to newDocumentBuilder() failed.
    public static Document newXmlDocument() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.newDocument();
        } catch (ParserConfigurationException ex) {
            System.err.println("Error: Canot create new XML document");
            System.err.println("Cause: " + ex.getMessage());
            System.exit(1);
            return null;
        }
    }

    // Return a new document from a string or abort if call to newDocumentBuilder() failed.
    public static Document newXmlDocument(String xml) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.parse(new InputSource(new StringReader(xml.toString())));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println("Error: Canot create new XML document");
            System.err.println("Cause: " + ex.getMessage());
            System.exit(1);
            return null;
        }
    }
}
