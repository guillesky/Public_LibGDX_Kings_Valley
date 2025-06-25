package modelo;

public interface KVEventListener
{

	int ENTERING_LEVEL = 0;
	int ENTER_LEVEL = 1;
	int EXITING_LEVEL = 2;
	int EXIT_LEVEL = 3;
	
	
	int THROW_DAGGER = 5;
	int ENTER_GIRATORY = 6;
	int ACTIVATE_TRAP = 7;
	int PLAYER_JUMP = 10;
	int PLAYER_LANDING = 11;
	int PLAYER_BEGIN_FALL = 12;
	int PLAYER_DIE = 13;
	int MUMMY_APPEAR = 20;
	int MUMMY_DIE = 21;
	int MUMMY_KILLED_BY_SWORD = 22;
	int FINISH_CURRENT_LEVEL = 30;
	int ADD_ONE_LIVE = 40;
	int PICKUP_JEWEL = 51;
	int PICKUP_DAGGER = 52;
	int PICKUP_PICKER = 53;
	int PICKUP_ALL_JEWEL = 54;
	int OPEN_DOOR=60;
	int CLOSE_DOOR=61;
	
	int FINISH_ALL_LEVELS=100;
	

	void eventFired(int eventCode, Object param);
}