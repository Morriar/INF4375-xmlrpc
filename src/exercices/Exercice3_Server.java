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

import http.HTTPRequest;
import http.HTTPUtils;
import catalog.Album;
import catalog.Catalog;
import catalog.XMLUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Simple server that returns a XML document over HTTP.
 *
 * In this version, the server reads the request from the client to select what
 * albums to display.
 */
public class Exercice3_Server {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        System.out.println("Loading catalog...");
        Catalog catalog = Catalog.load("catalog.xml");

        System.out.println("Listening on port 8080...");
        ServerSocket server = new ServerSocket(8080);
        while (true) {
            try (Socket socket = server.accept()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                try {
                    HTTPRequest request = new HTTPRequest(in);
                    switch (request.uri) {
                        case "/catalog": {
                            // Build XML response
                            Catalog resCatalog = new Catalog();
                            for (Album album : catalog.albums) {
                                if (album.inStock) {
                                    resCatalog.albums.add(album);
                                }
                            }
                            System.out.println(resCatalog.prettyPrint());
                            HTTPUtils.sendXmlResponse(out, 200, "OK", resCatalog.toXml());
                            break;
                        }
                        case "/search": {
                            if (!request.hasBody()) {
                                HTTPUtils.sendXmlError(out, 400, "No body provided in request");
                                break;
                            }
                            // Get research params
                            Document query = XMLUtils.newXmlDocument(request.body);
                            int from = Integer.parseInt(query.getElementsByTagName("from").item(0).getTextContent());
                            int to = Integer.parseInt(query.getElementsByTagName("to").item(0).getTextContent());
                            // Build XML response
                            Catalog resCatalog = new Catalog();
                            for (Album album : catalog.albums) {
                                if (album.year >= from || album.year <= to) {
                                    resCatalog.albums.add(album);
                                }
                            }
                            HTTPUtils.sendXmlResponse(out, 200, "OK", resCatalog.toXml());
                            break;
                        }
                        default:
                            HTTPUtils.sendXmlError(out, 400, "Unknown service " + request.uri);
                    }
                } catch (Exception ex) {
                    System.out.println("Warning: malformed request");
                    System.out.println(ex);
                    continue;
                }
            }
        }
    }
}
