package tmxEditor;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;

public class TMXObjcetScaler
{
    public static void main(String[] args)
    {
	String[] fileNames =
	{ "level_01.tmx", "level_02.tmx", "level_03.tmx", "level_04.tmx", "level_05.tmx", "level_06.tmx",
		"level_07.tmx", "level_08.tmx", "level_09.tmx", "level_10.tmx", "level_11.tmx", "level_12.tmx",
		"level_13.tmx", "level_14.tmx", "level_15.tmx" };
	for (int i = 0; i < fileNames.length; i++)
	    convertFile(fileNames[i]);

    }

    public static void convertFile(String fileName)
    {
	try
	{
	    File inputFile = new File(fileName);
	    // Parsear el XML
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(inputFile);
	    doc.getDocumentElement().normalize();

	    // Modificar objetos:
	    /*
	     * 
	     * 
	     */
	    NodeList objectGroupList = doc.getElementsByTagName("objectgroup");

	    for (int i = 0; i < objectGroupList.getLength(); i++)
	    {
		Element objectGroup = (Element) objectGroupList.item(i);
		NodeList objectList = objectGroup.getElementsByTagName("object");

		for (int j = 0; j < objectList.getLength(); j++)
		{
		    Element object = (Element) objectList.item(j);
		    int x = Integer.parseInt(object.getAttribute("x"));
		    int y = Integer.parseInt(object.getAttribute("y"));

		    x *= 6.4;
		    y *= 6.4;

		    object.setAttribute("x", String.valueOf(x));
		    object.setAttribute("y", String.valueOf(y));
		    object.setAttribute("width", "64");
		    object.setAttribute("height", "64");

		}
	    }

	    // Guardar el archivo
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File("new_" + fileName));
	    transformer.transform(source, result);

	    System.out.println("Archivo modificado guardado como mapa_modificado.tmx");
	} catch (Exception e)
	{
	    System.out.println(e.getMessage());
	}
    }

    private static String replaceTileValues(String csv)
    {
	String[] tiles = csv.split(",");
	StringBuilder modified = new StringBuilder();
	for (int i = 0; i < tiles.length; i++)
	{
	    String tileStr = tiles[i].trim();
	    if (!tileStr.isEmpty())
	    {
		int tile = Integer.parseInt(tileStr);
		if (20 < tile && tile < 40)
		{
		    tile -= 2;
		} else if (40 < tile && tile < 60)
		{
		    tile -= 4;
		} else if (60 < tile && tile < 79)
		{
		    tile -= 6;
		} else if (tile == 79 || tile == 80)
		    tile += 23;
		else if (80 < tile && tile < 99)
		{
		    tile -= 8;
		} else if (tile == 99 || tile == 100)
		    tile += 21;
		else if (100 < tile && tile < 112)
		{
		    tile -= 10;
		} else if (tile == 112 || tile == 113)
		    tile -= 43;
		else if (120 < tile && tile < 132)
		{
		    tile -= 12;
		} else if (tile == 132 || tile == 133)
		    tile -= 45;
		else if (206 < tile && tile < 210)
		{
		    tile -= 103;
		} else if (tile == 210 || tile == 211)
		    tile -= 175;
		else if (tile == 212 || tile == 213)
		    tile -= 159;
		else if (213 < tile && tile < 218)
		{
		    tile -= 75;
		} else if (220 < tile)
		{
		    tile -= 220;
		}

		modified.append(tile);
		if (i < tiles.length - 1)
		{
		    modified.append(",");
		}
	    }
	}
	return modified.toString();
    }

    public static void changeP0(Element object, String value)
    {
	NodeList properties = object.getElementsByTagName("properties");
	for (int k = 0; k < properties.getLength(); k++)
	{
	    Element props = (Element) properties.item(k);
	    NodeList propList = props.getElementsByTagName("property");
	    for (int p = 0; p < propList.getLength(); p++)
	    {
		Element prop = (Element) propList.item(p);
		if ("p0".equals(prop.getAttribute("name")))
		{
		    prop.setAttribute("value", value);
		}
	    }
	}
    }

}
