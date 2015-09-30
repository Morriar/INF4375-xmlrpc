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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Album representation.
 */
public class Album {

    public String title;
    public String artist;
    public Integer year;
    public Double price;
    public Boolean inStock;

    // Build a new Album instance from values.
    public Album(String title, String artist, Integer year, Double price, Boolean inStock) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.price = price;
        this.inStock = inStock;
    }

    // Build a new Album instance from a xml Element.
    public Album(Element xmlElement) {
        this.title = xmlElement.getElementsByTagName("title").item(0).getTextContent();
        this.artist = xmlElement.getElementsByTagName("artist").item(0).getTextContent();
        this.year = Integer.parseInt(xmlElement.getElementsByTagName("year").item(0).getTextContent());
        this.price = Double.parseDouble(xmlElement.getElementsByTagName("price").item(0).getTextContent());
        this.inStock = Boolean.parseBoolean(xmlElement.getElementsByTagName("instock").item(0).getTextContent());
    }

    @Override
    public String toString() {
        return title + " - " + artist + "(" + year + ") " + price + "$";
    }

    // Add this as a xml element into document.
    public Element toXML(Document document) {
        Element title = document.createElement("title");
        title.setTextContent(this.title);
        Element artist = document.createElement("artist");
        artist.setTextContent(this.artist);
        Element year = document.createElement("year");
        year.setTextContent(this.year.toString());
        Element price = document.createElement("price");
        price.setTextContent(this.price.toString());
        Element inStock = document.createElement("instock");
        inStock.setTextContent(this.inStock.toString());
        Element album = document.createElement("album");
        album.appendChild(title);
        album.appendChild(artist);
        album.appendChild(year);
        album.appendChild(price);
        album.appendChild(inStock);
        return album;
    }
}
