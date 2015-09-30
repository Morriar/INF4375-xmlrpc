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
package http;

import catalog.XMLUtils;
import java.io.PrintWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author morriar
 */
public class HTTPUtils {
    
    public static void sendXmlRequest(PrintWriter out, String uri, Document xml) {
        String body = XMLUtils.xmlToString(xml);
        HTTPRequest request = new HTTPRequest("POST", uri, "HTTP/1.1", body);
        request.send(out);
    }
    
    // Send an HTTP response with a XML body.
    public static void sendXmlResponse(PrintWriter out, Integer status, String reason, Document xml) {
        String body = XMLUtils.xmlToString(xml);
        HTTPResponse response = new HTTPResponse("HTTP/1.1", status, reason, body);
        response.headers.put("Content-Type", "text/xml");
        response.send(out);
    }

    // Send an HTTP response with a XML error.
    public static void sendXmlError(PrintWriter out, Integer status, String reason) {
        Document document = XMLUtils.newXmlDocument();
        Element rootElement = document.createElement("error");
        rootElement.setTextContent(reason);
        document.appendChild(rootElement);

        sendXmlResponse(out, status, reason, document);
    }
}
