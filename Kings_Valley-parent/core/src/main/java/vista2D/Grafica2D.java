package vista2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import io.github.some_example_name.IMyApplicationnListener;
import modelo.Juego;
import modelo.Pyramid;

public class Grafica2D implements IMyApplicationnListener
{
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private AssetManager manager;

	public Grafica2D(AssetManager manager)
	{
		this.manager = manager;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void create()
	{
		TiledMap map=Juego.getInstance().getCurrentPyramid().getMap();
		Pyramid pyramid=Juego.getInstance().getCurrentPyramid();
		camera = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());
		camera.position.x = pyramid.getMapWidthInPixels() * .5f;
		camera.position.y = pyramid.getMapHeightInPixels() * .5f;
		renderer = new OrthogonalTiledMapRenderer(map);

	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

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
		Juego.getInstance().dispose();
	}

}
