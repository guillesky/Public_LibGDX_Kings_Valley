package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.LevelObject;
import util.Config;

public class AnimatedEntity2D
{
    protected LevelObject levelObject;
    protected Animation<TextureRegion> animation;

    protected Sprite sprite;
    private float spriteWidth;
    private float spriteHeight;

    public AnimatedEntity2D(LevelObject levelObject, Animation<TextureRegion> animation,float spriteWidth,float spriteHeight)
    {
	this.animation = animation;
	
	this.levelObject = levelObject;
	this.spriteWidth=spriteWidth;
	this.spriteHeight=spriteHeight;
	this.sprite = new Sprite(animation.getKeyFrame(0));
	//this.sprite.setSize(this.spriteWidth,this.spriteHeight);
    }

    public void updateElement(float deltaTime)
    {

	sprite.setRegion(animation.getKeyFrame(deltaTime, true));
	float x = this.levelObject.getX() + (this.levelObject.getWidth() - this.sprite.getWidth()) / 2;
	float y = this.levelObject.getY();
	this.sprite.setPosition(x, y);
	

    }

    public void render(SpriteBatch batch)
    {

	sprite.draw(batch);
    }

    public LevelObject getLevelObject()
    {
	return levelObject;
    }

}
