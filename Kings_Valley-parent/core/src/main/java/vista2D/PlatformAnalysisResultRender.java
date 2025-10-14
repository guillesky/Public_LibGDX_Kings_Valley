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

	private ArrayList<EndPlatform> endPlatforms = new ArrayList<EndPlatform>();

	/**
	 * Constructor de clase
	 * @param platformAnalysisResult Resultado del analisis que debe renderizarse
	 */
	public PlatformAnalysisResultRender(PlatformAnalysisResult platformAnalysisResult)
	{
		this.endPlatforms.add(platformAnalysisResult.getEndEndPlatformLeft());
		this.endPlatforms.add(platformAnalysisResult.getEndEndPlatformRight());
		this.nearestUpStair = platformAnalysisResult.getNearestUpStair();
		this.nearestDownStair = platformAnalysisResult.getNearestDownStair();
	}

	/**
	 * Llamado al momento de renderizar
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
		if (this.nearestUpStair != null)
		{
			shapeRenderer.setColor(Color.MAGENTA);
			shapeRenderer.rect(this.nearestUpStair.getDownStair().x, this.nearestUpStair.getDownStair().y,
					this.nearestUpStair.getDownStair().width, this.nearestUpStair.getDownStair().height);
		}

		if (this.nearestDownStair != null)
		{
			shapeRenderer.setColor(Color.CYAN);
			shapeRenderer.rect(this.nearestDownStair.getUpStair().x, this.nearestDownStair.getUpStair().y,
					this.nearestDownStair.getUpStair().width, this.nearestDownStair.getUpStair().height);
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

}
