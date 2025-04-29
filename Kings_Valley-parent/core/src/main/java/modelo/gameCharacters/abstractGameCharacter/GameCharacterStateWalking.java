package modelo.gameCharacters.abstractGameCharacter;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import modelo.level.LevelObject;
import modelo.level.Stair;

public class GameCharacterStateWalking extends GameCharacterState
{

	public GameCharacterStateWalking(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_WALKING);
		this.gameCharacter.resetAnimationDelta();
	}

	@Override
	protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
	{

		this.gameCharacter.motionVector.x = v.x * this.gameCharacter.speedWalk;
		if (v.x != 0)
			this.gameCharacter.lookRight = v.x > 0;
	
		if (b)
			this.gameCharacter.doAction();
		else
		{

			if (v.x == 0)
				this.gameCharacter.gameCharacterState = new GameCharacterStateIddle(this.gameCharacter);
		}
		if (v.y != 0 && v.x != 0)
			this.checkEnterStair(v);
		if (!this.isFloorDown())
			this.gameCharacter.gameCharacterState = new GameCharacterStateFalling(this.gameCharacter);
	}

	protected void checkEnterStair(Vector2 v)
	{
		Stair stair = null;
		if (v.y > 0)
		{
			if (v.x > 0)
			{
				stair = this.checkStairsFeetColision(this.gameCharacter.pyramid.getPositiveStairs(), true);

			} else
			{
				stair = this.checkStairsFeetColision(this.gameCharacter.pyramid.getNegativeStairs(), true);

			}
		} else
		{
			if (v.x > 0)
			{
				stair = this.checkStairsFeetColision(this.gameCharacter.pyramid.getNegativeStairs(), false);

			} else
			{
				stair = this.checkStairsFeetColision(this.gameCharacter.pyramid.getPositiveStairs(), false);

			}
		}
		if (stair != null)
			this.gameCharacter.gameCharacterState = new GameCharacterStateOnStair(this.gameCharacter, stair);

	}

	private Stair checkStairsFeetColision(ArrayList<Stair> stairs, boolean isUpping)
	{

		Iterator<Stair> it = stairs.iterator();
		Stair respuesta = null;
		Stair stair = null;
		LevelObject item = null;
		if (it.hasNext())
			do
			{
				stair = it.next();
				if (isUpping)
					item = stair.getDownStair();
				else
					item = stair.getUpStair();
			} while (it.hasNext() && !this.gameCharacter.isFeetColision(item));

		if (this.gameCharacter.isFeetColision(item))
		{
			respuesta = stair;
		}

		return respuesta;
	}

	@Override
	protected void moveSecondStep(Vector2 escalado)
	{
		this.colision(escalado);
	}

}
