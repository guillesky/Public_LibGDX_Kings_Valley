package vista2D.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.game.Game;

/**
 * Clase que representa la UI del mapa
 * 
 * @author Guillermo Lazzurri
 */
public class UIMap
{
	private Texture mapTexture;
	private Texture currentPyramidTexture;
	private Texture completedPyramidTexture;
	private Sprite mapSprite;
	private SpriteBatch batch;
	private OrthographicCamera cameraMap;
	private HashMap<Integer, CellInMap> pyramidsInMap = new HashMap<Integer, CellInMap>();

	private SpriteWithId currentPyramidSprite;

	private ArrayList<SpriteWithId> completedPyramidSprites = new ArrayList<SpriteWithId>();

	private class CellInMap
	{
		int col;
		int row;

		public int getCol()
		{
			return col;
		}

		public int getRow()
		{
			return row;
		}

		public CellInMap(int col, int row)
		{
			super();
			this.col = col;
			this.row = row;
		}
	}

	private class SpriteWithId extends Sprite
	{
		private int id;

		public SpriteWithId(Texture texture, int id)
		{
			super(texture);
			this.id = id;
		}
	}

	/**
	 * Constructor de clase
	 * 
	 * @param mapTexture              Texture del mapa en blanco
	 * @param currentPyramidTexture   Textura del resalatado para piramide actual
	 * @param completedPyramidTexture Textura para marcar las piramides completadas
	 */
	public UIMap(Texture mapTexture, Texture currentPyramidTexture, Texture completedPyramidTexture)
	{
		super();
		this.mapTexture = mapTexture;
		this.currentPyramidTexture = currentPyramidTexture;
		this.completedPyramidTexture = completedPyramidTexture;
		this.batch = new SpriteBatch();
		this.cameraMap = new OrthographicCamera();
		this.cameraMap.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.mapSprite = new Sprite(mapTexture);
		this.generatePyramidsCellsInMaps();
	}

	/**
	 * Libera los recursos creados
	 */
	public void dispose()
	{
		this.batch.dispose();
	}

	/**
	 * Recalcular el mapa de acuerdo a los nuevos tamanos
	 * 
	 * @param width  ancho nuevos de la ventana
	 * @param height alto nuevo de la ventana
	 */
	public void resize(int width, int height)
	{
		this.cameraMap.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (this.currentPyramidSprite != null)
			this.calulateMapSprites(width, height);

	}

	/**
	 * Recalcula los tamanos de los sprites del mapa
	 * 
	 * @param width  ancho nuevos de la ventana
	 * @param height alto nuevo de la ventana
	 */
	private void calulateMapSprites(float width, float height)
	{
		float imageRatio = (float) this.mapTexture.getWidth() / (float) mapTexture.getHeight();
		float stageRatio = width / height;
		float cellSize;
		float offsetX;
		float offsetY;

		if (imageRatio > stageRatio)
		{
			this.mapSprite.setSize(width, width / imageRatio);
			cellSize = width / 6;
		} else
		{
			this.mapSprite.setSize(height * imageRatio, height);
			cellSize = height / 6;
		}

		offsetX = (width - this.mapSprite.getWidth()) / 2f;
		offsetY = (height - this.mapSprite.getHeight()) / 2f;

		this.mapSprite.setPosition(offsetX, offsetY);

		for (SpriteWithId completed : this.completedPyramidSprites)
		{
			this.recalculateSpriteWithId(completed, cellSize, offsetX, offsetY);
		}
		this.recalculateSpriteWithId(currentPyramidSprite, cellSize, offsetX, offsetY);
	}

	/**
	 * Recalcula las posiciones de los sprites dentro del mapa
	 * 
	 * @param spriteWithId Sprite a recalcular
	 * @param cellSize     tamano de la celda
	 * @param offsetX      desplazamiento en x de la celda
	 * @param offsetY      desplazamiento en y de la celda
	 */
	private void recalculateSpriteWithId(SpriteWithId spriteWithId, float cellSize, float offsetX, float offsetY)
	{

		spriteWithId.setSize(cellSize, cellSize);
		float cellOffsetX = this.pyramidsInMap.get(spriteWithId.id).getCol() * cellSize + offsetX;
		float cellOffsetY = this.pyramidsInMap.get(spriteWithId.id).getRow() * cellSize + offsetY;
		spriteWithId.setPosition(cellOffsetX, cellOffsetY);
	}

	/**
	 * Genera las celdas dondes estan las piramides
	 */
	private void generatePyramidsCellsInMaps()
	{
		int p = 1;
		int w = 1;
		int h = 1;
		int direction = 1;

		while (p <= 16)
		{
			this.pyramidsInMap.put(p, new CellInMap(w, 5 - h));
			p++;
			w += direction;
			if (w == 0 || w == 5)
			{
				h++;
				direction *= -1;
				w += direction;
			}
		}
	}

	/**
	 * Dibuja todo el conjunto del mapa
	 */
	public void render()
	{
		this.batch.begin();
		batch.setProjectionMatrix(cameraMap.combined);
		this.mapSprite.draw(batch);
		this.currentPyramidSprite.draw(batch);
		for (SpriteWithId completed : this.completedPyramidSprites)
			completed.draw(batch);
		this.batch.end();

	}

	/**
	 * Genera las piramides que ya estan completadas
	 */
	public void generateCompletedPyramids()
	{
		this.completedPyramidSprites.clear();

		HashMap<Integer, Boolean> completedLevels = Game.getInstance().getCompletedLevels();

		this.currentPyramidSprite = new SpriteWithId(currentPyramidTexture,
				Game.getInstance().getCurrentLevel().getId());

		for (Integer p : completedLevels.keySet())
		{
			if (completedLevels.get(p))
			{

				SpriteWithId spriteWithId = new SpriteWithId(completedPyramidTexture, p);
				this.completedPyramidSprites.add(spriteWithId);

			}

		}
		this.calulateMapSprites(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	}

}
