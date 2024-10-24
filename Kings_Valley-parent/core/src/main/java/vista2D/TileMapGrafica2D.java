package vista2D;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import io.github.some_example_name.IMyApplicationnListener;
import modelo.Juego;
import modelo.LevelItem;
import modelo.Pyramid;
import util.Constantes;

public class TileMapGrafica2D implements IMyApplicationnListener
{
	private final String archiPlayer = "pics/player.png";
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private AssetManager manager;
	protected Sprite player = null;
	private SpriteBatch spriteBatch = new SpriteBatch();;
	private Array<MySpriteKV> instances = new Array<MySpriteKV>();
	private HashMap<Integer, Texture> textures = new HashMap<Integer, Texture>();

	public TileMapGrafica2D(AssetManager manager)
	{
		this.manager = manager;
		this.manager.load(this.archiPlayer, Texture.class);
		// TODO Auto-generated constructor stub
// agergar codigo de carga en el manager

	}

	@Override
	public void addGraphicElement(Object element)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeGraphicElement(Object element)
	{
		ArrayIterator<MySpriteKV> it = this.instances.iterator();
		while (it.hasNext() && it.next().getLevelItem() != element)
		{

		}
		it.remove();

	}

	@Override
	public void create()
	{
		TiledMap map = Juego.getInstance().getCurrentPyramid().getMap();
		Pyramid pyramid = Juego.getInstance().getCurrentPyramid();
		camera = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());
		camera.position.x = pyramid.getMapWidthInPixels() * .5f;
		camera.position.y = pyramid.getMapHeightInPixels() * .5f;
		Texture texturePlayer = manager.get(this.archiPlayer, Texture.class);
		this.textures.put(Constantes.PLAYER, texturePlayer);
		this.instances.add(new MySpriteKV(texturePlayer, pyramid.getPlayer()));
		renderer = new OrthogonalTiledMapRenderer(map);

		TiledMapTileSet tileSet = pyramid.getMap().getTileSets().getTileSet(0);

		Iterator<LevelItem> levelItems = Juego.getInstance().getCurrentPyramid().getLevelItems();
		while (levelItems.hasNext())

		{
			LevelItem item = levelItems.next();
			if (item.getType() == Constantes.It_dagger || item.getType() == Constantes.It_picker
					|| item.getType() == Constantes.It_jewel)
			{
				MySpriteKV sprite = new MySpriteKV(tileSet.getTile(item.getP0()).getTextureRegion(), item);
				this.instances.add(sprite);

			}

			

		}

	}

	@Override
	public void resize(int width, int height)
	{
		Pyramid pyramid = Juego.getInstance().getCurrentPyramid();
		if (height != 0)
		{
			camera.setToOrtho(false, pyramid.getMapHeightInPixels() * width / height, pyramid.getMapHeightInPixels());
			camera.position.x = pyramid.getMapWidthInPixels() * .5f;
			camera.position.y = pyramid.getMapHeightInPixels() * .5f;
		}
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(.0f, .0f, .0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		renderer.setView(camera);
		renderer.render();

		this.spriteBatch.begin();
		ArrayIterator<MySpriteKV> it = this.instances.iterator();
		while (it.hasNext())
		{
			MySpriteKV mskv = it.next();
			mskv.updateElement(null);
			mskv.draw(spriteBatch);
		}
		spriteBatch.end();
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
		this.renderer.dispose();
	}

}
