package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import modelo.Controles;
import modelo.Juego;
import modelo.Pyramid;
import util.Constantes;
import vista2D.TileMapGrafica2D;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main implements IMyApplicationnListener
{
    private AssetManager manager;
    private IMyApplicationnListener grafica;

    @Override
    public void create()
    {

	manager = new AssetManager();
	manager.setLoader(TiledMap.class, new TmxMapLoader());
	for (int i = 1; i <= 15; i++)
	    manager.load(Constantes.levelFileName.get(i), TiledMap.class);
	grafica = new TileMapGrafica2D(manager);

	manager.finishLoading();

	for (int i = 1; i <= 15; i++)
	{
	    TiledMap map = manager.get(Constantes.levelFileName.get(i), TiledMap.class);
	    Juego.getInstance().addPyramid(new Pyramid(map, grafica));
	}
	
	this.grafica.create();

    }

    @Override
    public void render()
    {
	this.updateInput();
	this.grafica.render();
    }

    @Override
    public void dispose()
    {
	manager.dispose();
	this.grafica.dispose();
	Juego.getInstance().dispose();

    }

    private void updateInput()
    {
	Controles controles = Juego.getInstance().getControles();

	float x = 0, y = 0;

	Vector2 aux;
	if (Gdx.input.isKeyPressed(Input.Keys.UP))
	    y += 1;
	if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
	    y -= 1;
	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	    x += 1;
	if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
	    x -= 1;
	aux = new Vector2(x, y);

	controles.setNuevoRumbo(aux);
	if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && controles.isShotEnabled())
	    controles.shot();
	if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) &&!controles.isShotEnabled())
	    controles.enableShotEnabled();

	Juego.getInstance().actualizaframe(Gdx.graphics.getDeltaTime());
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
    public void resize(int width, int height)
    {
	this.grafica.resize(width, height);

    }

    @Override
    public void pause()
    {
	this.grafica.pause();

    }

    @Override
    public void resume()
    {
	this.grafica.resume();

    }

}
