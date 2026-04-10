package vista2D.ui;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa el mapa en la version Extendida del juego (60 niveles en 4 episodios + Tesoro)
 * @author Guillermo Lazzurri
 */
public class UIExtendedMap extends UIAbstractMap
{
    /**
     * Constructor de clase, llama a super(mapTexture, currentPyramidTexture,
     * completedPyramidTexture, matrixDimension);
     * 
     * @param mapTexture              Texture del mapa en blanco
     * @param currentPyramidTexture   Textura del resalatado para piramide actual
     * @param completedPyramidTexture Textura para marcar las piramides completadas
     *                              
     */
    public UIExtendedMap(Texture mapTexture, Texture currentPyramidTexture, Texture completedPyramidTexture)
    {
	super(mapTexture, currentPyramidTexture, completedPyramidTexture, 10);

    }

    @Override
    protected void generateLevelCellsInMaps()
    {
	int levelId = 0;
	int col = 1;
	int row = 0;
	int direction = 1;
	int lastRow = this.getMatrixDimension() - 1;

	for (int count = 0; count < 4; count++)
	{
	    col = 1;
	    direction = 1;
	    row++;
	    for (int i = 0; i < 15; i++)
	    {
		levelId++;
		this.levelsInMap.put(levelId, new CellInMap(col, lastRow - row));

		col += direction;
		if (col == lastRow)
		{
		    row++;
		    direction *= -1;
		    col += direction;
		}
	    }
	   
	}
	
	this.levelsInMap.put(61, new CellInMap(col, lastRow - row));

    }

}
