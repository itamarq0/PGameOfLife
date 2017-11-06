package ex1;

import
import java.util.ArrayList;

public class ParallelGameOfLife implements GameOfLife {

	public ArrayList<Boolean> current;
	public ArrayList<Boolean> prev;
	
	public boolean[][][] invoke(boolean[][] initalField, int hSplit, int vSplit,
			int generations) {
		int length = initalField.length;
		int width = initalField[0].length;
		current = new ArrayList<Boolean>();
		prev = new ArrayList<Boolean>();
		for (int i = 0; i < initalField.length; i++) {
			for (int j = 0; j < initalField[0].length; j++) {
				prev.add(initalField[i][j]);
			}
		}
		int hSplitNum = length/vSplit;
		int vSplitNum = width/hSplit;
		int threadNum = hSplit * ve
		
		
		return null;
	}

}


