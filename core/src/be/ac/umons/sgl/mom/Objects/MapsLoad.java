package be.ac.umons.sgl.mom.Objects;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;


public class MapsLoad
{
        public static void main(String[] args)
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
                    public void startElement(String uri, String localName, String name, Attributes attributes)
                    {
                        System.out.println("Element: " + name);
                    }
                    public void endElement(String uri, String localName, String name)
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

