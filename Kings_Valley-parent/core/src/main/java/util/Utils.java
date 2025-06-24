package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

import i18n.Language;
import i18n.Messages;

public class Utils
{
    public static ArrayList<Rectangle> generateRectangles(int mapWidthInTiles, int mapHeightInTiles,
	    TiledMapTileLayer layer)
    {
	float width = Config.getInstance().getLevelTileWidthUnits();
	float height = Config.getInstance().getLevelTileHeightUnits();
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	Cell cell = null;
	ArrayList<Integer> bricks = new ArrayList<Integer>();
	for (int i = 0; i < 18; i++)
	{
	    bricks.add(i + 1);
	    bricks.add(i + 221);
	}
	for (int i = 210; i < 219; i++)
	{
	    bricks.add(i);

	}

	for (int i = mapHeightInTiles - 1; i >= 0; i--)
	{
	    int j = 0;
	    while (j < mapWidthInTiles)
	    {
		cell = layer.getCell(j, i);
		if (cell != null)
		{
		    int value = cell.getTile().getId();
		    int count = 0;
		    while (cell != null && bricks.contains(value) && j < mapWidthInTiles)
		    {
			count++;
			j++;
			cell = layer.getCell(j, i);
			if (cell != null)
			    value = cell.getTile().getId();

		    }
		    if (count > 0)
		    {
			rectangles.add(
				new Rectangle2((j - count) * width, i * height, count * width, height, Color.WHITE));

		    }

		}
		j++;
	    }
	}
	optimize(rectangles, mapWidthInTiles, mapHeightInTiles);

	return rectangles;
    }

    private static boolean optimize(ArrayList<Rectangle> rectangles, int mapWidthInTiles, int mapHeightInTiles)
    {

	boolean r = false;
	Rectangle a = null;
	Rectangle b = null;
	float height = Config.getInstance().getLevelTileHeightUnits();

	Iterator<Rectangle> it = rectangles.iterator();
	while (it.hasNext() && !r)
	{
	    a = it.next();
	    Iterator<Rectangle> it2 = rectangles.iterator();
	    while (it2.hasNext() && !r)
	    {
		b = it2.next();
		r = (a != b && a.x == b.x && a.width == b.width && a.y == b.y + b.height);

	    }

	}
	if (r)
	{
	    Rectangle c = new Rectangle2(a.x, a.y - b.height, a.width, a.height + b.height, Color.RED);
	    rectangles.remove(a);
	    rectangles.remove(b);
	    rectangles.add(0, c);

	    optimize(rectangles, mapWidthInTiles, mapHeightInTiles);
	}
	return r;
    }

    public static void i18n(String languageCode)
    {
	String path = "i18n/" + languageCode + ".json";
	FileHandle file = Gdx.files.internal(path);
	if (!file.exists())
	{
	    throw new RuntimeException("Language file not found: " + path);
	}

	Json json = new Json();
	ObjectMap<String, String> loaded = json.fromJson(ObjectMap.class, file);

	for (Messages key : Messages.values())
	{
	    String msg = loaded.get(key.name());
	    if (msg != null)
	    {
		key.setValue(msg);
	    } else
	    {
		key.setValue("???" + key.name());
	    }
	}
    }

    public static Language i18nToLanguage(String languageCode)
    {
	HashMap<String, String> words = new HashMap<String, String>();
	String path = "i18n/" + languageCode + ".json";
	FileHandle file = Gdx.files.internal(path);
	if (!file.exists())
	{
	    throw new RuntimeException("Language file not found: " + path);
	}

	Json json = new Json();
	ObjectMap<String, String> loaded = json.fromJson(ObjectMap.class, file);

	Iterator<String> keys = loaded.keys().iterator();
	while (keys.hasNext())
	{
	    String key = keys.next();
	    words.put(key, loaded.get(key));
	}
	return new Language(languageCode,words);
    }
    
    
    public static void i18n(Language language)
    {
	
	for (Messages key : Messages.values())
	{
	    String msg = language.getWords().get(key.name());
	    if (msg != null)
	    {
		key.setValue(msg);
	    } else
	    {
		key.setValue("???" + key.name());
	    }
	}
    }
    
}
