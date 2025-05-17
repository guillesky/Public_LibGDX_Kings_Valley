package io.github.some_example_name;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.Stage;

import modelo.level.Level;
import modelo.level.LevelReader;
import util.Constantes;
import vista2D.TileMapGrafica2D;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main_ContadorTiles implements ApplicationListener
{
    
   
    private AssetManager manager;
    private IMyApplicationListener grafica;

    @Override
    public void create()
    {
	manager = new AssetManager();
	grafica = new TileMapGrafica2D(manager);

	manager.finishLoading();
	LevelReader levelReader = new LevelReader();
	ArrayList<TiledMap> maps = new ArrayList<TiledMap>();
	for (int i = 1; i <= 15; i++)
	{
	    Level level = levelReader.getLevel(i, Constantes.levelFileName.get(i), 0, false, null, grafica);
	    maps.add(level.getPyramid().getMap());
	}
	this.countTiles(maps);
    }

    private void countTiles(ArrayList<TiledMap> maps)
    {
	TreeMap<Integer, Integer> tileCounter = new TreeMap<Integer, Integer>();

	Iterator<TiledMap> it = maps.iterator();
	int i = 0;
	while (it.hasNext())

	{
	    i++;
	    TiledMap map = it.next();
	    TiledMapTileLayer layer1 = (TiledMapTileLayer) map.getLayers().get("back");
	    TiledMapTileLayer layer2 = (TiledMapTileLayer) map.getLayers().get("front");
	    TiledMapTileLayer layer3 = (TiledMapTileLayer) map.getLayers().get("stairs");

	    int mapWidthInTiles = map.getProperties().get("width", Integer.class);
	    int mapHeightInTiles = map.getProperties().get("height", Integer.class);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer1, tileCounter);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer2, tileCounter);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer3, tileCounter);
	    if (tileCounter.get(33) != tileCounter.get(34))
		System.out
			.println("ERROR en MAPA: " + i + "     " + tileCounter.get(33) + "    " + tileCounter.get(34));
	}
	System.out.println(tileCounter);
	System.out.println("Total de tiles: " + tileCounter.size());
    }

    private void searchInLayer(int mapWidthInTiles, int mapHeightInTiles, TiledMapTileLayer layer,
	    TreeMap<Integer, Integer> tileCounter)
    {
	Cell cell;
	for (int i = 0; i < mapHeightInTiles; i++)
	    for (int j = 0; j < mapWidthInTiles; j++)
	    {
		cell = layer.getCell(j, i);
		if (cell != null)
		{
		    int value = cell.getTile().getId();
		    if (tileCounter.get(value) == null)
		    {
			tileCounter.put(value, 1);
		    } else
		    {
			int count = tileCounter.get(value) + 1;
			tileCounter.put(value, count);
		    }
		}
	    }
    }

    @Override
    public void render()
    {

    }

    @Override
    public void resize(int width, int height)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void pause()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void resume()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void dispose()
    {
	// TODO Auto-generated method stub

    }
}
