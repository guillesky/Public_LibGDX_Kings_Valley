package vista2D;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

import engine.gameCharacters.mummys.EndPlatform;
import engine.gameCharacters.mummys.PlatformAnalysisResult;
import engine.level.Stair;

/**
 * Representa graicamente un PlatformAnalysisResult, es decir, el resultado del
 * analisis de una plataforma por un caracter. Dibuja los rectangulos en los
 * bordes de la plataorma y en los comienzos de las escaleras mas cercanas que
 * suben y bajan.<br>
 * Dependiendo del tipo de borde se utiliza el siguiente condigo de colores<br>
 * 
 * 
 * EndPlatform2.END_BLOCK: color = Color.RED; (Si el borde es un bloqueo)<br>
 * 
 * EndPlatform2.END_CLIFF: color = Color.YELLOW; (Si el borde es una
 * cornisa)<br>
 * 
 * EndPlatform2.END_STEP: color = Color.GREEN; (Si el borde es un escalon) <br>
 * 
 * Dependiendo del tipo de escalera:<br>
 * Escalera que sube: Color.MAGENTA<br>
 * Escalera que baja: Color.CYAN<br>
 * <b>Solo usado para debug</b>
 * 
 * @author Guillermo Lazzurri
 */
public class PlatformAnalysisResultRender
{

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Stair nearestUpStair;
    private Stair nearestDownStair;
    private ArrayList<Stair> upStairsInPlatform;
    private ArrayList<Stair> downStairsInPlatform;

    private ArrayList<EndPlatform> endPlatforms = new ArrayList<EndPlatform>();

    /**
     * Constructor de clase
     * 
     * @param platformAnalysisResult Resultado del analisis que debe renderizarse
     */
    public PlatformAnalysisResultRender(PlatformAnalysisResult platformAnalysisResult)
    {
	this.endPlatforms.add(platformAnalysisResult.getEndPlatformLeft());
	this.endPlatforms.add(platformAnalysisResult.getEndPlatformRight());
	this.nearestUpStair = platformAnalysisResult.getNearestUpStair();
	this.nearestDownStair = platformAnalysisResult.getNearestDownStair();
	this.downStairsInPlatform = platformAnalysisResult.getDownStairsInPlatform();
	this.upStairsInPlatform = platformAnalysisResult.getUpStairsInPlatform();
    }

    /**
     * Llamado al momento de renderizar
     * 
     * @param combined Matriz de proyeccion, Usualmente camera.combined
     */
    public void render(Matrix4 combined)
    {
	shapeRenderer.setProjectionMatrix(combined);
	shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	shapeRenderer.setColor(Color.RED);
	for (EndPlatform e : endPlatforms)
	{

	    Color color = Color.WHITE;
	    switch (e.getType())
	    {
	    case EndPlatform.END_BLOCK:
		color = Color.RED;
		break;
	    case EndPlatform.END_CLIFF:
		color = Color.YELLOW;
		break;
	    case EndPlatform.END_STEP:
		color = Color.GREEN;
		break;

	    }
	    Rectangle rect = e.getRectangle();
	    shapeRenderer.setColor(color);
	    shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);

	}
	for(Stair stair: this.downStairsInPlatform)
	    this.drawBeginStair(stair, false, false);
	for(Stair stair: this.upStairsInPlatform)
	    this.drawBeginStair(stair, true, false);
	if (this.nearestUpStair != null)
	{
	    this.drawBeginStair(nearestUpStair, true, true);
	}

	if (this.nearestDownStair != null)
	{
	    this.drawBeginStair(nearestDownStair, false, true);
	}

	shapeRenderer.end();
    }

    /**
     * libera los recursos
     */
    public void dispose()
    {
	shapeRenderer.dispose();
    }

    private void drawBeginStair(Stair stair, boolean isUpStair, boolean isNearStair)
    {
	Color color;
	Rectangle r;
	if (isUpStair)
	{
	    color = Color.MAGENTA;
	    r = stair.getDownStair();
	} else
	{
	    color = Color.CYAN;
	    r = stair.getUpStair();
	}

	shapeRenderer.setColor(color);
	shapeRenderer.rect(r.x, r.y, r.width, r.height);
	if (isNearStair)
	{
	    shapeRenderer.line(r.x, r.y, r.x+r.width, r.y+r.height);
	    shapeRenderer.line(r.x+r.width, r.y, r.x, r.y+r.height);
	}
    }
}
