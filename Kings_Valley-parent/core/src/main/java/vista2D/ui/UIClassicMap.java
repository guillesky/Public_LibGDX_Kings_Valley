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
     * @param matrixDimension         Dimension de la matriz sobre la que se
     *                                dibujaran las piramides en el mapa.
     */
    public UIClassicMap(Texture mapTexture, Texture currentPyramidTexture, Texture completedPyramidTexture,
	    int matrixDimension)
    {
	super(mapTexture, currentPyramidTexture, completedPyramidTexture, matrixDimension);

    }

    @Override
    protected void generatePyramidsCellsInMaps()
    {
	int p = 1;
	int w = 1;
	int h = 1;
	int direction = 1;
	int lastRow = this.getMatrixDimension() - 1;
	while (p <= 16)
	{
	    this.pyramidsInMap.put(p, new CellInMap(w, lastRow - h));
	    p++;
	    w += direction;
	    if (w == 0 || w == lastRow)
	    {
		h++;
		direction *= -1;
		w += direction;
	    }
	}
    }

}
