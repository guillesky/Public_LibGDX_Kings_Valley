package engine.gameCharacters.mummys;

import engine.level.Stair;

/**
 * Representa el resultado del analisis de una plataforma desde la perspectiva
 * de un caracter
 * 
 * @author Guillermo Lazzurri
 */
public class PlatformAnalysisResult
{
	EndPlatform endEndPlatformLeft;
	EndPlatform endEndPlatformRight;
	Stair nearestUpStair;
	Stair nearestDownStair;

	/**
	 * Constructor de clase
	 * 
	 * @param endEndPlatformLeft  Indica el final izquierdo de la plataforma
	 * @param endEndPlatformRight Indica el final derecho de la plataforma
	 * @param nearestUpStair      Indica La escalera que sube mas cercana al
	 *                            caracter. Podria ser null.
	 * @param nearestDownStair    Indica La escalera que baja mas cercana al
	 *                            caracter. Podria ser null.
	 */
	public PlatformAnalysisResult(EndPlatform endEndPlatformLeft, EndPlatform endEndPlatformRight, Stair nearestUpStair,
			Stair nearestDownStair)
	{
		super();
		this.endEndPlatformLeft = endEndPlatformLeft;
		this.endEndPlatformRight = endEndPlatformRight;
		this.nearestUpStair = nearestUpStair;
		this.nearestDownStair = nearestDownStair;
	}

	/**
	 * Retorna el final izquierdo de la plataforma
	 * @return El final izquierdo de la plataforma
	 */
	public EndPlatform getEndEndPlatformLeft()
	{
		return endEndPlatformLeft;
	}

	/**
	 * Retorna el final derecho de la plataforma
	 * @return El final derecho de la plataforma
	 */
	public EndPlatform getEndEndPlatformRight()
	{
		return endEndPlatformRight;
	}

	/**
	 * Retorna la escalera que sube mas cercana. Si no hay escaleras en la plataforma retorna null.
	 * @return La escalera que sube mas cercana. Si no hay escaleras en la plataforma retorna null.
	 */
	public Stair getNearestUpStair()
	{
		return nearestUpStair;
	}

	/**
	 * Retorna la escalera que baja mas cercana. Si no hay escaleras en la plataforma retorna null.
	 * @return La escalera que baja mas cercana. Si no hay escaleras en la plataforma retorna null.
	 */
	
	public Stair getNearestDownStair()
	{
		return nearestDownStair;
	}

	@Override
	public String toString()
	{
		return "PlatFormAnalisys [endEndPlatformLeft=" + endEndPlatformLeft + ", endEndPlatformRight="
				+ endEndPlatformRight + ", nearestUpStair=" + nearestUpStair + ", nearestDownStair=" + nearestDownStair
				+ "]";
	}

}
