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
	 * @param matrixDimension         Dimension de la matriz sobre la que se
	 *                                dibujaran las piramides en el mapa.
	 */
	public UIGreatTempleMap(Texture mapTexture, Texture currentPyramidTexture, Texture completedPyramidTexture)
	{
		super(mapTexture, currentPyramidTexture, completedPyramidTexture, 7);

	}

	@Override
	protected void generatePyramidsCellsInMaps()
	{
		int currentPyramid = 0;
		
		int h = 0;
		
		int lastRow = this.getMatrixDimension() - 1;

		for (int count = 0; count < 2; count++)
		{
		
			h++;
			for (int i = 1; i <= 4; i++)
			{
				currentPyramid++;
				this.pyramidsInMap.put(currentPyramid, new CellInMap(i, lastRow - h));
			}
			h++;
			for (int i = 5; i >= 2; i--)
			{
				currentPyramid++;
				this.pyramidsInMap.put(currentPyramid, new CellInMap(i, lastRow - h));
			}

		}

		this.pyramidsInMap.put(17, new CellInMap(2, 1));
		this.pyramidsInMap.put(18, new CellInMap(4, 1));
		

	}

}
