package vista2D.ui;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa el Mapa en la version clasica del juego. (15 niveles + Tesoro)
 * 
 * @author Guillermo Lazzurri
 */
public class UIClassicMap extends UIAbstractMap
{

    /**
     * Constructor de clase, llama a super(mapTexture, currentPyramidTexture,
     * completedPyramidTexture, matrixDimension);
     * 
     * @param mapTexture              Texture del mapa en blanco
     * @param currentPyramidTexture   Textura del resalatado para piramide actual
     * @param completedPyramidTexture Textura para marcar las piramides completadas
     */
    public UIClassicMap(Texture mapTexture, Texture currentPyramidTexture, Texture completedPyramidTexture)
    {
	super(mapTexture, currentPyramidTexture, completedPyramidTexture, 6);

    }

    @Override
    protected void generateLevelCellsInMaps()
    {
	int levelId = 1;
	int col = 1;
	int row = 1;
	int direction = 1;
	int lastRow = this.getMatrixDimension() - 1;
	while (levelId <= 16)
	{
	    this.levelsInMap.put(levelId, new CellInMap(col, lastRow - row));
	    levelId++;
	    col += direction;
	    if (col == 0 || col == lastRow)
	    {
		row++;
		direction *= -1;
		col += direction;
	    }
	}
    }

}
