package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.gameCharacters.GameCharacter;
import modelo.gameCharacters.mummys.Mummy;

public class MummyAnimated2D extends GameCharacterAnimated2D
{
	protected Animation<TextureRegion> mummyAnimationAppear;
	protected boolean visible = false;

	public MummyAnimated2D(GameCharacter character, Animation<TextureRegion>[] animations)
	{
		super(character, animations);
		this.mummyAnimationAppear = animations[TileMapGrafica2D.APPEAR];

	}

	@Override
	public void updateElement(float deltaTime)
	{
		Mummy mummy = (Mummy) this.getLevelObject();
	
		int state = mummy.getState();
		this.visible = (state != Mummy.ST_LIMBUS);
		if(state==Mummy.ST_APPEARING)
			this.animation=this.mummyAnimationAppear;
		super.updateElement(deltaTime);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		if (this.visible)
			super.render(batch);
		
	}

}
