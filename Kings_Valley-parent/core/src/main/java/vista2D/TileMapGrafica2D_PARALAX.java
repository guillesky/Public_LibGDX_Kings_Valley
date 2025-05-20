package vista2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import io.github.some_example_name.IMyApplicationListener;
import modelo.game.Game;
import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.player.PairInt;
import modelo.level.DrawableElement;
import modelo.level.GiratoryMechanism;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import modelo.level.TrapMechanism;
import modelo.level.dagger.Dagger;
import modelo.level.door.Door;
import util.Constantes;
import util.Messages;

public class TileMapGrafica2D_PARALAX extends TileMapGrafica2D
{
	private OrthographicCamera cameraBack;
	private float factor;

	public TileMapGrafica2D_PARALAX(AssetManager manager, float factor)
	{
		super(manager);
		this.factor = factor;
	}

	@Override
	public void create()
	{
		super.create();
		Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
		cameraBack = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// cameraBack.position.x = pyramid.getMapWidthInPixels() * .5f;

	}

	@Override
	public void render()
	{

		Gdx.gl.glClearColor(.0f, .0f, .0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		this.calculateCamera();
		this.calculateCameraBack();
		;

		this.spriteBatch.begin();
		this.drawUI();
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		/*
		 * float factor = 0.5f; camera.position.x = originalCamPos.x * factor;
		 * camera.position.y = originalCamPos.y * factor; camera.update();
		 * 
		 * renderer.setView(camera);
		 */

		this.cameraBack.update();
		renderer.setView(cameraBack);

		renderer.render(new int[]
		{ 1 });

//		renderer.renderTileLayer((TiledMapTileLayer) renderer.getMap().getLayers().get("back"));

		// camera.position.set(originalCamPos);

		// camera.update();

		renderer.setView(camera);
		renderer.render(new int[]
		{ 2, 3 });
		ArrayIterator<AnimatedTrapKV2> it3 = this.animatedTraps.iterator();
		while (it3.hasNext())
		{
			AnimatedTrapKV2 animatedTrapKV2 = it3.next();
			animatedTrapKV2.updateElement(Game.getInstance().getDelta());
			animatedTrapKV2.getSprite().draw(spriteBatch);
		}
		if (Game.getInstance().getState() == Game.ST_GAME_PLAYING)
		{
			Iterator<AbstractAnimatedDoor2D> itDoors = this.animatedDoor2D.iterator();
			while (itDoors.hasNext())
			{
				AbstractAnimatedDoor2D animatedDoor = itDoors.next();
				animatedDoor.updateElement(null);
				animatedDoor.draw(spriteBatch);
			}
		}
		ArrayIterator<MySpriteKV> it = this.instances.iterator();
		while (it.hasNext())
		{
			MySpriteKV mySpriteKV = it.next();
			mySpriteKV.updateElement(null);
			mySpriteKV.draw(spriteBatch);
		}

		ArrayIterator<AnimatedEntity2D> it2 = this.animatedEntities.iterator();
		while (it2.hasNext())
		{
			AnimatedEntity2D animatedEntity2D = it2.next();
			animatedEntity2D.updateElement(Game.getInstance().getDelta());
			animatedEntity2D.render(spriteBatch);
		}

		if (Game.getInstance().getState() == Game.ST_GAME_PLAYING
				|| Game.getInstance().getState() == Game.ST_GAME_DYING)
		{

			this.playerAnimated2D.updateElement(Game.getInstance().getDelta());
			this.playerAnimated2D.render(spriteBatch);
		} else
		{
			this.animatedEnteringDoor2D.updateElement(null);
			this.animatedEnteringDoor2D.drawBack(spriteBatch);
			this.playerAnimated2D.updateElement(Game.getInstance().getDelta());
			if ((Game.getInstance().getState() == Game.ST_GAME_ENTERING
					&& Game.getInstance().getDelta() <= this.getTimeToExitLevel())
					|| Game.getInstance().getState() == Game.ST_GAME_EXITING)
			{
				this.playerAnimated2D.render(spriteBatch);
				this.animatedEnteringDoor2D.drawFront(spriteBatch);
			} else
			{
				this.animatedEnteringDoor2D.drawFront(spriteBatch);
				this.playerAnimated2D.render(spriteBatch);

			}

		}

		if (Game.getInstance().isPaused())
		{
			this.cameraUI.update();
			spriteBatch.setProjectionMatrix(cameraUI.combined);
			GlyphLayout layout = new GlyphLayout(font48, Messages.GAME_PAUSED.getValue());
			float x = (Gdx.graphics.getWidth() - layout.width) / 2;
			float y = (Gdx.graphics.getHeight() + layout.height) / 2;
			spriteBatch.setColor(0, 0, 0, 0.5f); // negro con 50% opacidad
			spriteBatch.draw(pixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font48.draw(spriteBatch, layout, x, y);

		}
		this.drawUI();

		spriteBatch.end();
		// this.rectaglesRenderDebug.render(camera.combined);
	}

	private void calculateCameraBack()
	{
		Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
		float playerX = Game.getInstance().getCurrentLevel().getPlayer().getX();
		float medioX = pyramid.getMapWidthInPixels() / 2;
		float dif = (medioX - playerX) * factor;
		float aux_X = playerX + dif;

		if (playerX >= (cameraBack.viewportWidth / 2)
				&& playerX + (cameraBack.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
			cameraBack.position.x = aux_X;

		cameraBack.position.y = pyramid.getMapHeightInPixels() * .55f;
	}

	@Override
	public void resize(int width, int height)
	{

		super.resize(width, height);

		if (height != 0 && this.cameraBack != null)
		{
			Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
			float ancho = pyramid.getMapHeightInPixels() * width / (height + 2);
			float alto = pyramid.getMapHeightInPixels() + 20;
			cameraBack.setToOrtho(false, ancho, alto);
			this.calculateCameraBackFull();
		}

	}

	private void calculateCameraBackFull()
	{
		float posCameraX = 0;
		Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();

		float playerX = Game.getInstance().getCurrentLevel().getPlayer().getX();
		float medioX = pyramid.getMapWidthInPixels() / 2;
		float dif = (medioX - playerX) * this.factor;
		float aux_X = playerX + dif;

		if (playerX >= (cameraBack.viewportWidth / 2)
				&& playerX + (cameraBack.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
			posCameraX = aux_X;

		else
		{
			if (cameraBack.viewportWidth >= pyramid.getMapWidthInPixels())
				posCameraX = pyramid.getMapWidthInPixels() * .5f;
			else
			{
				if (playerX < pyramid.getMapWidthInPixels() / 2)
				{

					playerX = cameraBack.viewportWidth / 2;
					medioX = pyramid.getMapWidthInPixels() / 2;
					dif = (medioX - playerX) * this.factor;
					posCameraX = playerX + dif;

				} else
				{
					playerX = pyramid.getMapWidthInPixels() - cameraBack.viewportWidth / 2;
					medioX = pyramid.getMapWidthInPixels() / 2;
					dif = (medioX - playerX) * this.factor;

					posCameraX = playerX + dif;
				}
			}
		}

		cameraBack.position.x = posCameraX;
		cameraBack.position.y = pyramid.getMapHeightInPixels() * .55f;
	}

}
