package modelo;

import util.Constantes;

public class Player extends Character
{

    public Player(LevelItem door)
    {
	super(Constantes.PLAYER, door.getX(), door.getY(), 0, 0,6,18);
	
    }

}
