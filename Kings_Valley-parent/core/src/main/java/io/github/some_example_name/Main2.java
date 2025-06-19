package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import facade.Facade;
import vista2D.UI2D;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main2 implements ApplicationListener
{
	
	private AssetManager manager;
	private UI2D ui;
	private FreeTypeFontGenerator generator;

	@Override
	public void create()
	{
		this.manager = Facade.getInstance().getManager();
		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PAPYRUS.TTF"));
		this.ui = new UI2D(this.manager, generator);
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
	}
}
