package vista2D.ui;

import com.badlogic.gdx.graphics.Texture;

public class UIExtendedMap extends UIAbstractMap
{

    public UIExtendedMap(Texture mapTexture, Texture currentPyramidTexture, Texture completedPyramidTexture,
	    int matrixDimension)
    {
	super(mapTexture, currentPyramidTexture, completedPyramidTexture, matrixDimension);

    }

    @Override
    protected void generatePyramidsCellsInMaps()
    {
	int p = 0;
	int w = 1;
	int h = 0;
	int direction = 1;
	int lastRow = this.getMatrixDimension() - 1;

	for (int count = 0; count < 4; count++)
	{
	    w = 1;
	    direction = 1;
	    h++;
	    for (int i = 0; i < 15; i++)
	    {
		p++;
		this.pyramidsInMap.put(p, new CellInMap(w, lastRow - h));

		w += direction;
		if (w == lastRow)
		{
		    h++;
		    direction *= -1;
		    w += direction;
		}
	    }
	   
	}
	
	this.pyramidsInMap.put(61, new CellInMap(w, lastRow - h));

    }

}
