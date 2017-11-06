package ex1;

import ex1.GameThreadV2;
import java.util.ArrayList;

public class ParallelGameOfLifeV2 implements GameOfLife {

	private boolean[][] board;
	//private ArrayList<GameThreadV2> threads; 
	private GameThreadV2[][] threads;
	
	public boolean[][][] invoke(boolean[][] initalField, int hSplit, int vSplit,
			int generations) {
		threads = new GameThreadV2[hSplit][vSplit];
		int length = initalField.length;
		int width = initalField[0].length;
		board = initalField;
		int hSplitNum = length/vSplit;
		int vSplitNum = width/hSplit;
		for (int i = 0; i < hSplit; i++) {
			for (int j = 0; j < vSplit; j++) {
				int upperLeftRow = i * hSplitNum;
				int upperLeftCol = j * vSplitNum;
				int partLength = hSplitNum;
				int partWidth = vSplitNum;
				if(i == hSplit-1) partLength = length - ((i-1) * hSplitNum);
				if(j == vSplit-1) partWidth = width - ((j-1) * vSplitNum);
				GameThreadV2 thread = new GameThreadV2(board,upperLeftRow,upperLeftCol,partLength,partWidth,generations);
				threads[i][j] = thread;
			}
		}
		threads[0][0].
		return null;
	}

}


