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

import http.HTTPResponse;
import http.HTTPUtils;
import catalog.XMLUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Client for `Exercice3_Server`.
 *
 * This client sends a XML request to the server and display the response.
 */
public class Exercice3_Client {

    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
        System.out.println("Connect to server localhost:8080...");

        Socket s = new Socket("localhost", 8080);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        Document request = newRequest(1990, 2000);
        HTTPUtils.sendXmlRequest(out, "/search", request);

        try {
            HTTPResponse response = new HTTPResponse(in);
            System.out.println(response.body);
        } catch (Exception ex) {
            System.out.println("Warning: malformed response");
            System.out.println(ex);
            return;
        }
    }

    public static Document newRequest(Integer from, Integer to) {
        Document document = XMLUtils.newXmlDocument();
        Element root = document.createElement("search");
        document.appendChild(root);
        Node fromNode = document.createElement("from");
        fromNode.setTextContent(from.toString());
        root.appendChild(fromNode);
        Node toNode = document.createElement("to");
        toNode.setTextContent(to.toString());
        root.appendChild(toNode);
        return document;
    }
}
