package vista2D.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class SliderDificult extends SliderWithLabel
{
    private String easy;
    private String normal;
    private String hard;

    public SliderDificult(String texto, float min, float max, float step, float valorInicial, Skin skin,
	    int usserObject, ChangeListener changeListener, String easy, String normal, String hard)
    {
	super(texto, min, max, step, valorInicial, skin, usserObject, changeListener);
	this.easy = easy;
	this.normal = normal;
	this.hard = hard;
	this.labelValue.setText(this.setValueLabel());
	this.slider.removeListener(this.changeListener);
	this.changeListener = new ChangeListener()
	{
	    @Override
	    public void changed(ChangeEvent event, Actor actor)
	    {
		labelValue.setText(setValueLabel());
	    }
	};
	this.slider.addListener(this.changeListener);
	this.labelValue.setText(this.setValueLabel());
    }

    private String setValueLabel()
    {
	String r = null;
	int v = (int) this.slider.getValue();
	if (v == 0)
	    r = this.normal;
	else if (v > 0)
	    r = this.hard + " " + v;
	else
	    r = this.easy + " " + -v;
	return r;
    }

    public void setText(String text, String easy, String normal, String hard)
    {

	this.setText(text);
	this.easy = easy;
	this.normal = normal;
	this.hard = hard;

    }

    
    

}
