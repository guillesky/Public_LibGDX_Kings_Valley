package io.github.some_example_name;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import modelo.LevelItem;
import util.Constantes;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter
{
	private TiledMap map;
	private AssetManager manager;
	private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles, mapWidthInPixels, mapHeightInPixels;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private LevelItem[] levelItems;

	@Override
	public void create()
	{
		String fileName = "maps/level_01.tmx";
		manager = new AssetManager();
		manager.setLoader(TiledMap.class, new TmxMapLoader());
		manager.load(fileName, TiledMap.class);
		manager.finishLoading();

		map = manager.get(fileName, TiledMap.class);
		MapProperties properties = map.getProperties();
		tileWidth = properties.get("tilewidth", Integer.class);
		tileHeight = properties.get("tileheight", Integer.class);
		mapWidthInTiles = properties.get("width", Integer.class);
		mapHeightInTiles = properties.get("height", Integer.class);
		mapWidthInPixels = mapWidthInTiles * tileWidth;
		mapHeightInPixels = mapHeightInTiles * tileHeight;
		camera = new OrthographicCamera(mapHeightInPixels * 4 / 3, mapHeightInPixels);

		camera.position.x = mapWidthInPixels * .5f;
		camera.position.y = mapHeightInPixels * .5f;
		renderer = new OrthogonalTiledMapRenderer(map);
		TiledMapTileLayer layerFront = (TiledMapTileLayer) map.getLayers().get(1);
		System.out.println(layerFront.toString()+ "   Width: "+layerFront.getWidth()+"  Height: "+layerFront.getHeight());
		for(int i=0;i<layerFront.getHeight();i++)
			for(int j=0;j<layerFront.getWidth();j++) 
			{
				
	System.out.println("i: "+i+" j:"+j+"       "+layerFront.getCell(j, i));
					
			}
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(.5f, .0f, .0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void dispose()
	{
		manager.dispose();
		this.map.dispose();
		this.renderer.dispose();
	}

	private void readLevelItem()
	{
		MapLayers mapLayers = map.getLayers();
		MapLayer layerBack = mapLayers.get(0);
		MapLayer layerFront = mapLayers.get(1);
		MapLayer layerObject = mapLayers.get(2);
		this.levelItems = new LevelItem[layerObject.getObjects().getCount()];
		for (int i = 0; i < layerObject.getObjects().getCount(); i++)
		{
			MapObject objeto = layerObject.getObjects().get(i);

			MapProperties mp = objeto.getProperties();
			String stype = (String) mp.get("type");
			float fx = (float) mp.get("x");
			float fy = (float) mp.get("y");
			String sp0 = (String) mp.get("p0");
			String sp1 = (String) mp.get("p1");
			int type = Constantes.stringToInteger.get(stype);
			int x = (int) (fx / 10);
			int y = (int) (fy / 10);
			int p0 = Integer.parseInt(sp0);
			int p1 = Integer.parseInt(sp1);
			LevelItem levelItem = new LevelItem(type, x, y, p0, p1);
			this.levelItems[i] = levelItem;
			System.out.println(levelItem);
			System.out.println("******************************");
		}
	}
}
