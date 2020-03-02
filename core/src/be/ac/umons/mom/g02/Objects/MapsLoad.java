package be.ac.umons.mom.g02.Objects;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;


public class MapsLoad
{
        public void readXML(String file)
        {
            try
            {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                parser.parse("XML_MapsGame", new DefaultHandler()
                {
                    public void startDocument()
                    {
                        System.out.println("Xml Maps Documents Start");
                    }
                    public void endDocument()
                    {
                        System.out.println("Xml Maps Documents End");
                    }
                    public void startElement(String ur, String localName, String name, Attributes attributes)
                    {
                        System.out.println("Element: " + name);
                    }
                    public void endElement(String ur, String localName, String name)
                    {
                        System.out.println("Quit element");
                    }
                });
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }
}

