package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import facade.Facade;
import vista2D.GraphicsFileLoader;
import vista2D.ui.Controler2D;
import vista2D.ui.UI2D;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main2 implements ApplicationListener
{

    private AssetManager manager;
    private UI2D ui;
    private FreeTypeFontGenerator generator;
    private Controler2D controler;

    @Override
    public void create()
    {
	this.manager = Facade.getInstance().getManager();
	this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PAPYRUS.TTF"));
	this.ui = new UI2D(this.manager, generator);
	this.controler = new Controler2D(this.ui);
	GraphicsFileLoader graphicsFileLoader = new GraphicsFileLoader(this.manager);

	this.ui.create();

    }

    @Override
    public void render()
    {
	this.ui.render();
    }

    @Override
    public void resize(int width, int height)
    {
	this.ui.resize(width, height);
    }

    @Override
    public void pause()
    {
	this.ui.pause();
    }

    @Override
    public void resume()
    {
	this.ui.resume();
    }

    @Override
    public void dispose()
    {

	generator.dispose();
	this.manager.dispose();
	Facade.getInstance().saveGameOption();
    }
}
