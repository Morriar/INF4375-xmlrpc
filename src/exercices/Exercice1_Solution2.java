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

import catalog.Catalog;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * XML parsing example using DOMXML and object oriented representation.
 */
public class Exercice1_Solution2 {

    public static void main(String[] args) throws ParserConfigurationException {
        String xmlPath = "catalog.xml";
        try {
            Catalog catalog = new Catalog(xmlPath);
            System.out.println(catalog.prettyPrint());
        } catch (IOException | SAXException ex) {
            System.err.println("Error: Cannot load xml file " + xmlPath);
            System.err.println("Cause: " + ex.toString());
        }
    }
}
