package ex1;

import java.util.ArrayList;
import java.util.EnumMap;

import ex1.Direction;

public class GameThreadV2 extends Thread {
	private boolean [][] prevPart;
	private boolean [][] currentPart;
	private int rowStart;
	private int colStart;
	private int partLength;
	private int partWidth;
	private int totLength;
	private int totWidth;
	private int generations;
	
	private EnumMap<Direction,GameThreadV2> neighbours;

	public GameThreadV2(boolean[][] board,int rowStart,int colStart, int partLength,
			int partWidth, int generations){
		neighbours = new EnumMap<Direction,GameThreadV2>(Direction.class); 

		this.rowStart = rowStart;
		this.colStart = colStart;
		this.partLength =partLength;
		this.partWidth = partWidth;
		this.totLength = board.length;
		this.totWidth = board[0].length;
		this.generations = generations;
		prevPart = new boolean[partLength][partWidth];
		currentPart = new boolean[partLength][partWidth];
		for (int i = rowStart; i < rowStart + partLength; i++) {
			for (int j = colStart; j < colStart + partWidth; j++) {
				prevPart[i-rowStart][j-colStart] = board[i][j];
			}
		}
	}
	
	public boolean insideBoard(int row, int col){
		if(row >= rowStart + partLength)	return false;
		if(row < rowStart)	return false;
		if(col >= colStart + partWidth)	return false;
		if(col < colStart)	return false;
		return true;
	}
	
	public int limitAccessIndex(int row, int col,Direction dir){
		if(dir == Direction.UPLEFT || dir == Direction.UPRIGHT || dir == Direction.DOWNLEFT || dir == Direction.DOWNRIGHT ){
			return 0;
		}
		if(dir == Direction.UP || dir == Direction.DOWN){
			return col -colStart;
		}
		if(dir == Direction.LEFT || dir == Direction.RIGHT){
			return row - rowStart;
		}
		return -1;
	}
	
	public boolean compute(int row, int col, EnumMap<Direction,ArrayList<Boolean>> limits){
		int sum = 0;
		for (int i = row-1; i <= row+1; i++) {
			if (i < 0 || i >= totLength)	continue;
			for (int j = col-1; j <= col+1; j++) {
				if (j < 0 || j >= totWidth)	continue;
				if(insideBoard(i,j)){
					if(prevPart[i - rowStart][j - colStart]){
						sum++;
					}
				}
				else{
					int x = col < colStart ? -1 : col;
					int y = row < rowStart ? -1 : row;
					Direction dir = Direction.VectorToDirection(x, y);
					int index = limitAccessIndex(row, col, dir);
					if(limits.get(Direction.getAntiDir(dir)).get(index)){
						sum++;
					}
				}
			}
		}
		if(prevPart[row-rowStart][col - colStart]){
			if(sum>=2 && sum<=3){
				return true;
			}
			return false;
		}
		if(sum == 3){
			return true;
		}
		return false;
	}

	public void step(){
		EnumMap<Direction,ArrayList<Boolean>> limits = new EnumMap<Direction,ArrayList<Boolean>>(Direction.class); 
		for(Direction dir : Direction.values()){
			limits.put(dir, neighbours.get(dir).getLimit(Direction.getAntiDir(dir)));
		}
		for (int i = rowStart; i < rowStart + partLength; i++){
			for(int j = colStart; j< colStart + partWidth; j++){
				currentPart[i - rowStart][j - colStart] = compute(i,j,limits);
			}
		}
	}

	public void setNeighbour(Direction dir, GameThreadV2 thread){
		neighbours.put(dir, thread);
	}
	
	public ArrayList<Boolean> getLimit(Direction dir){
		ArrayList<Boolean> toReturn = new ArrayList<Boolean>();
		switch(dir){
			case UP:
				for (int i = 0; i < partWidth; i++) {
					toReturn.add(prevPart[0][i]);
				}
				break;
			case DOWN:
				for (int i = 0; i < partWidth; i++) {
					toReturn.add(prevPart[partLength-1][i]);
				}
				break;
			case RIGHT:
				for (int i = 0; i < partLength; i++) {
					toReturn.add(prevPart[i][partWidth-1]);
				}
				break;
			case LEFT:
				for (int i = 0; i < partLength; i++) {
					toReturn.add(prevPart[i][0]);
				}
				break;
			case DOWNLEFT:
				toReturn.add(prevPart[partLength-1][0]);
				break;
			case DOWNRIGHT:
				toReturn.add(prevPart[partLength-1][partWidth-1]);
				break;
			case UPLEFT:
				toReturn.add(prevPart[0][0]);
				break;
			case UPRIGHT:
				toReturn.add(prevPart[0][partWidth-1]);
				break;
		}
		return toReturn;
		
	}
	
	
	
}
