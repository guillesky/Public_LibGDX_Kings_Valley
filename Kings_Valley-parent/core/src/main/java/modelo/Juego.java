package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Juego
{
    private static Juego instance = new Juego();
    private Controles controles = new Controles();
    private ArrayList<Pyramid> pyramids = new ArrayList<Pyramid>();
    private int currentPyramid = 0;

    private Juego()
    {
    }

    public static Juego getInstance()
    {
	return instance;
    }

    public void actualizaframe(float deltaTime)
    {
	Player player=this.getCurrentPyramid().getPlayer();
	player.move(this.controles.getNuevoRumbo().scl(deltaTime));
	if(this.isFloorDown(player))
	    player.setState(1000);
	else
	    player.setState(7);
	    

    }

    private boolean isFloorDown(Character character)
    {
	int x1 = (int) ((character.getX()+character.getWidth()/2) / 10);
	int x2 = (int) ((character.getX()-character.getWidth()/2) / 10);
	int y = (int) (character.getY()-1 / 10);
	TiledMapTileLayer layer = (TiledMapTileLayer) this.getCurrentPyramid().getMap().getLayers().get("front");

	return (layer.getCell(x1, y) != null || layer.getCell(x2, y) != null);
    }

    private void aplicaGravedad(Character character)
    {

    }

    public Controles getControles()
    {
	return controles;
    }

    public void setControles(Controles controles)
    {
	this.controles = controles;
    }

    public void addPyramid(Pyramid pyramid)
    {
	this.pyramids.add(pyramid);
    }

    public Pyramid getCurrentPyramid()
    {
	return this.pyramids.get(currentPyramid);
    }

    public void dispose()
    {
	Iterator<Pyramid> it = this.pyramids.iterator();
	while (it.hasNext())
	    it.next().getMap().dispose();
    }
}
