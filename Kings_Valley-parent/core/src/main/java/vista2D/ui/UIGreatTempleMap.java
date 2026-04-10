package vista2D.ui;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa el mapa en la version Extendida del juego (60 niveles en 4
 * episodios + Tesoro)
 * 
 * @author Guillermo Lazzurri
 */
public class UIGreatTempleMap extends UIAbstractMap
{
	/**
	 * Constructor de clase, llama a super(mapTexture, currentPyramidTexture,
	 * completedPyramidTexture, matrixDimension);
	 * 
	 * @param mapTexture              Texture del mapa en blanco
	 * @param currentPyramidTexture   Textura del resalatado para piramide actual
	 * @param completedPyramidTexture Textura para marcar las piramides completadas
	 */
	public UIGreatTempleMap(Texture mapTexture, Texture currentPyramidTexture, Texture completedPyramidTexture)
	{
		super(mapTexture, currentPyramidTexture, completedPyramidTexture, 7);

	}

	@Override
	protected void generateLevelCellsInMaps()
	{
		int levelId = 0;
		
		int row = 0;
		
		int lastRow = this.getMatrixDimension() - 1;

		for (int count = 0; count < 2; count++)
		{
		
			row++;
			for (int i = 1; i <= 4; i++)
			{
				levelId++;
				this.levelsInMap.put(levelId, new CellInMap(i, lastRow - row));
			}
			row++;
			for (int i = 5; i >= 2; i--)
			{
				levelId++;
				this.levelsInMap.put(levelId, new CellInMap(i, lastRow - row));
			}

		}

		this.levelsInMap.put(17, new CellInMap(2, 1));
		this.levelsInMap.put(18, new CellInMap(4, 1));
		

	}

}
