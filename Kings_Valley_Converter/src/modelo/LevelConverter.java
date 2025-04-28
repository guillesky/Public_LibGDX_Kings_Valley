package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.Constantes;

public class LevelConverter
{
    private int width;
    private int height;
    private byte[][] matrizBack;
    private byte[][] matrizFront;
    private byte[][] matrizStairs;

    private LevelItem[] levelitems;
    private static String header1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
	    + "<map version=\"1.10\" tiledversion=\"1.11.0\" orientation=\"orthogonal\" renderorder=\"right-down\" width=\"";

    private static String header2 = "\" height=\"";
    private static String header3 = "\" tilewidth=\"10\" tileheight=\"10\" infinite=\"0\" nextlayerid=\"4\" nextobjectid=\"";
    private static String header4 = "\">\r\n" + " <tileset firstgid=\"1\" source=\"tiles.tsx\"/>\r\n";
    private static String layer1 = " <layer id=\"";
    private static String layer2 = "\" name=\"";
    private static String layer3 = "\" width=\"";
    private static String layer4 = "\" height=\"";
    private static String layer5 = "\">\r\n" + "  <data encoding=\"csv\">";
    private static String layer6 = "</data>\r\n" + "</layer>\n";
    private static String objectsHeader = "<objectgroup id=\"3\" name=\"items\">\n";

    private static String eof = "\n</map>";

    public void setFromArray(byte[] buffer)
    {
	Byte[] escaleras =
	{ 23, 24, 27, 28, 31, 32, 35, 36, 43, 44, 47, 48, 51, 52, 55, 56 };
	List<Byte> lista = Arrays.asList(escaleras);
	this.width = buffer[0];
	this.height = buffer[1];
	this.matrizBack = new byte[height][width];
	this.matrizFront = new byte[height][width];
	this.matrizStairs = new byte[height][width];
	int index = 2;

	for (int i = 0; i < this.height; i++)
	    for (int j = 0; j < this.width; j++)
	    {
		this.matrizBack[i][j] = buffer[index];
		index++;
	    }
	for (int i = 0; i < this.height; i++)
	    for (int j = 0; j < this.width; j++)
	    {
		byte dato = buffer[index];
		if (lista.contains(dato))
		{
		    this.matrizStairs[i][j] = dato;
		    this.matrizFront[i][j] = 0;
		} else
		{
		    this.matrizStairs[i][j] = 0;
		    this.matrizFront[i][j] = dato;
		}

		index++;
	    }
	int nItems = buffer[index];
	index++;
	ArrayList<LevelItem> itemsTemp = new ArrayList<LevelItem>();

	for (int i = 0; i < nItems; i++)
	{
	    int itemType = buffer[index];
	    index++;
	    int itemX = buffer[index];
	    index++;
	    int itemY = buffer[index];
	    index++;
	    int itemP0 = buffer[index] & 0xff;
	    index++;
	    int itemP1 = 0;
	    if (itemType == Constantes.It_wall)
	    {
		itemP1 = buffer[index];
		index++;
	    }
	    else 
	    	 if (itemType == Constantes.It_giratory)
	 	    {
	 		itemP0=0;
	 		index++;
	 	    }
	    itemsTemp.add(new LevelItem(itemType, itemX, itemY, itemP0, itemP1));

	}

	this.generaActivators(itemsTemp);
	this.levelitems=itemsTemp.toArray(new LevelItem[0]);
    }

    private void generaActivators(ArrayList<LevelItem> itemsTemp)
    {
	int nextActivator = 0;
	ArrayList<LevelItem> activators = new ArrayList<LevelItem>();
	for (int i = 0; i < itemsTemp.size(); i++)
	{
	    LevelItem item=itemsTemp.get(i);
	    if (item.getType() == Constantes.It_wall)
	    {
		int x = item.getP1();
		int y = item.getP0();
		activators.add(new LevelItem(Constantes.It_activator, x, y, nextActivator, 0));
		item.setP0(nextActivator);
		item.setP1(0);
		nextActivator++;
	    }

	}
	itemsTemp.addAll(activators);
    }

    public String frontMatrix()
    {
	return this.genericMatrix(matrizFront);
    }

    private String genericMatrix(byte[][] matrix)
    {
	StringBuilder r = new StringBuilder();
	for (int i = 0; i < this.height; i++)
	{
	    for (int j = 0; j < this.width; j++)
	    {
		int valor = matrix[i][j] & 0xff;
		r.append(valor + ",");

	    }
	    r.append("\n");
	}
	r.deleteCharAt(r.lastIndexOf(","));
	return r.toString();
    }

    public String backMatrix()
    {
	return this.genericMatrix(matrizBack);
    }

    @Override
    public String toString()
    {
	return "Level [width=" + width + ", height=" + height + ", matrizBack=" + Arrays.toString(matrizBack)
		+ ", matrizFront=" + Arrays.toString(matrizFront) + ", levelitems=" + Arrays.toString(levelitems) + "]";
    }

    public int getWidth()
    {
	return width;
    }

    public int getHeight()
    {
	return height;
    }

    public byte[][] getMatrizBack()
    {
	return matrizBack;
    }

    public byte[][] getMatrizFront()
    {
	return matrizFront;
    }

    public LevelItem[] getLevelitems()
    {
	return levelitems;
    }

    public String getTMX()
    {
	StringBuilder sb = new StringBuilder();
	sb.append(header1);
	sb.append(this.width);
	sb.append(header2);
	sb.append(this.height);
	sb.append(header3);
	sb.append(this.levelitems.length);
	sb.append(header4);
	sb.append(this.getGenericLayer(matrizBack, 1, "back"));
	sb.append(this.getGenericLayer(matrizFront, 2, "front"));
	sb.append(this.getGenericLayer(matrizStairs, 3, "stairs"));
	sb.append(this.getObjectLayer());
	sb.append(eof);

	return sb.toString();
    }

    private String getGenericLayer(byte[][] matrix, int id, String name)
    {
	StringBuilder sb = new StringBuilder();
	sb.append(layer1);
	sb.append(id);
	sb.append(layer2);

	sb.append(name);
	sb.append(layer3);
	sb.append(this.width);
	sb.append(layer4);
	sb.append(this.height);
	sb.append(layer5);
	sb.append(this.genericMatrix(matrix));
	sb.append(layer6);

	return sb.toString();
    }

    public String getObjectLayer()
    {
	StringBuilder sb = new StringBuilder();
	sb.append(LevelConverter.objectsHeader);
	int cont = 1;
	for (LevelItem item : this.levelitems)
	{
	    sb.append("<object id=\"");
	    sb.append(cont);
	    sb.append("\" type=\"");
	    sb.append(Constantes.identificacion.get(item.getType()));
	    sb.append("\" gid=\"");
	    int width = 10;
	    int height = 10;

	    switch (item.getType())
	    {
	    case Constantes.It_dagger:
	    case Constantes.It_picker:
	    case Constantes.It_jewel:
		sb.append(item.getP0());
		break;
	    case Constantes.It_door:
		sb.append(248);
		break;
	    case Constantes.It_giratory:
		sb.append(246);
		break;
	    case Constantes.It_mummy:
		sb.append(item.getP0() + 241);
		break;
	    case Constantes.It_wall:
		sb.append(254);
		break;
	    case Constantes.It_activator:
		sb.append(255);
		break;
	    case Constantes.It_stairs:
		if (item.getP0() == 1)
		{
		    item.setX(item.getX() + 1);
		    item.setY(item.getY() - 1);

		}
		if (item.getP0() == 3)
		{
		    item.setX(item.getX() - 1);
		    item.setY(item.getY() - 1);

		}
		sb.append(250 + item.getP0());

	    }
	    sb.append("\" x=\"");
	    sb.append(item.getX() * 10);
	    sb.append("\" y=\"");
	    sb.append((item.getY() + 1) * 10);
	    sb.append("\" width=\"" + width + "\" height=\"" + height + "\">\n");

	    sb.append(" <properties>\r\n" + "    <property name=\"p0\" value=\"" + item.getP0() + "\"/>\r\n"
		    +  " </properties>\r\n"
		    + "</object>\r\n");

	    cont++;
	}
	sb.append("</objectgroup>");
	return sb.toString();

    }
}
