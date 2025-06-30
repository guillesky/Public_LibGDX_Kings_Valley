package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

import modelo.control.Controls;
import modelo.game.Game;
import util.Utils;
import vista2D.GraphicsFileLoader;
import vista2D.TileMapGrafica2D;
import vista2D.TileMapGrafica2D_PARALAX;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main implements ApplicationListener
{
    private AssetManager manager;
    private IMyApplicationListener grafica;

    @Override
    public void create()
    {
	Utils.i18n("es");
	manager = new AssetManager();
	/*
	 * manager.setLoader(TiledMap.class, new TmxMapLoader());
	 * 
	 * for (int i = 1; i <= 15; i++) manager.load(Constantes.levelFileName.get(i),
	 * TiledMap.class);
	 */
	grafica = new TileMapGrafica2D_PARALAX(new GraphicsFileLoader(manager), .5f);
	// grafica = new TileMapGrafica2D(manager);

	/*
	 * Thread th = new Thread(new Runnable() {
	 * 
	 * @Override public void run() { float progress; do { progress =
	 * manager.getProgress(); System.out.println(progress);
	 * 
	 * } while (progress < 1.0);
	 * 
	 * } }); th.start();
	 */
	manager.finishLoading();

	Game.getInstance().setInterfaz(grafica);
	/*
	 * for (int i = 1; i <= 15; i++) { TiledMap map =
	 * manager.get(Constantes.levelFileName.get(i), TiledMap.class);
	 * Game.getInstance().addMap(i, map); }
	 */
	Game.getInstance().start(null);
	this.grafica.create();

	TileMapGrafica2D mytile2d = (TileMapGrafica2D) this.grafica;
	// mytile2d.changeTileSet();

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
	Game.getInstance().dispose();

    }

    private void updateInput()
    {
	Controls controles = Game.getInstance().getControles();

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
	controles.processKey(Input.Keys.SPACE);
	controles.processKey(Input.Keys.F);
	controles.processKey(Input.Keys.N);
	controles.processKey(Input.Keys.O);

	controles.processKey(Input.Keys.P);
	controles.processKey(Input.Keys.S);
	Game.getInstance().updateframe(Gdx.graphics.getDeltaTime());

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
