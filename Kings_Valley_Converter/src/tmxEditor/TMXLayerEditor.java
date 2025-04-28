package tmxEditor;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;

public class TMXLayerEditor
{
    public static void main(String[] args) throws Exception
    {
	File inputFile = new File("level_01.tmx");

	// Parsear el XML
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(inputFile);
	doc.getDocumentElement().normalize();

	// Buscar todos los nodos <layer>
	NodeList layerList = doc.getElementsByTagName("layer");

	for (int i = 0; i < layerList.getLength(); i++)
	{
	    Element layer = (Element) layerList.item(i);
	    NodeList dataList = layer.getElementsByTagName("data");
	    if (dataList.getLength() > 0)
	    {
		Element data = (Element) dataList.item(0);
		String encoding = data.getAttribute("encoding");

		if ("csv".equals(encoding))
		{
		    String csv = data.getTextContent().trim();
		    // Reemplazar todos los 145 por 78
		    String modifiedCsv = replaceTileValues(csv, 75, 375);
		    data.setTextContent(modifiedCsv);
		} else
		{
		    System.out.println("La capa usa otro tipo de codificación: " + encoding);
		}
	    }
	}

	// Guardar el archivo
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new File("mapa_modificado.tmx"));
	transformer.transform(source, result);

	System.out.println("Archivo modificado guardado como mapa_modificado.tmx");
    }

    private static String replaceTileValues(String csv, int oldValue, int newValue)
    {
	String[] tiles = csv.split(",");
	StringBuilder modified = new StringBuilder();
	for (int i = 0; i < tiles.length; i++)
	{
	    String tileStr = tiles[i].trim();
	    if (!tileStr.isEmpty())
	    {
		int tile = Integer.parseInt(tileStr);
		if (tile == oldValue)
		{
		    tile = newValue;
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
}
