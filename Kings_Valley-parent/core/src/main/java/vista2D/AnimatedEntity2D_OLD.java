package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedEntity2D_OLD {
    protected Animation<TextureRegion> animation;
    protected float stateTime;
    protected Sprite sprite;

    public AnimatedEntity2D_OLD(Animation<TextureRegion> animation) {
        this.animation = animation;
        this.sprite = new Sprite(animation.getKeyFrame(0));
        this.stateTime = 0f;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        sprite.setRegion(animation.getKeyFrame(stateTime, true));
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
