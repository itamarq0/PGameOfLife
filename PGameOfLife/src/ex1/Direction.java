package ex1;

public enum Direction {
	LEFT,
	RIGHT,
	UPRIGHT,
	UPLEFT,
	UP,
	DOWN,
	DOWNLEFT,
	DOWNRIGHT;
	
	public static Direction getAntiDir(Direction dir){
		switch(dir){
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case RIGHT:
			return LEFT;
		case LEFT:
			return RIGHT;
		case DOWNLEFT:
			return UPRIGHT;
		case DOWNRIGHT:
			return UPLEFT;
		case UPLEFT:
			return DOWNRIGHT;
		case UPRIGHT:
			return DOWNLEFT;
		}
		return null;
	}
	
	public static Direction VectorToDirection(int x, int y){
		if(x == 0 && y < 0)
			return DOWN;
		if(x == 0 && y > 0)
			return UP;
		if(x < 0 && y == 0)
			return LEFT;
		if(x > 0 && y == 0)
			return RIGHT;
		if(x > 0 && y > 0)
			return UPRIGHT;
		if(x < 0 && y > 0)
			return UPLEFT;
		if(x > 0 && y < 0)
			return DOWNRIGHT;
		if(x < 0 && y < 0)
			return DOWNLEFT;
		return null;
	}
	
}
