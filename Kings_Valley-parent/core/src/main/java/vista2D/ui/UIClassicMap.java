package vista2D.ui;

import com.badlogic.gdx.graphics.Texture;

public class UIClassicMap extends UIAbstractMap
{

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
