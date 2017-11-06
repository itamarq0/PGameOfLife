package ex1;

import java.util.ArrayList;

public class GameThread extends Thread {
	
	private static Integer globalCounter = 0;
	private static int globalThreadCounter = 0;
	private ArrayList<Boolean> current;
	private ArrayList<Boolean> prev;
	private int rowStart;
	private int rowEnd;
	private int colStart;
	private int colEnd;
	private int length;
	private int width;
	private int generations;
	
	public GameThread(ArrayList<Boolean> current,ArrayList<Boolean> prev,int rowStart, int rowEnd,
			int colStart, int colEnd, int length,int width, int generations){
		this.generations = generations;
		this.current = current;
		this.prev = prev;
		this.rowStart = rowStart;
		this.rowEnd = rowEnd;
		this.colStart = colStart;
		this.colEnd = colEnd;
		this.length = length;
		this.width = width;
		globalThreadCounter++;
	}
	public void step(){
		for (int i = rowStart;i < rowEnd; i++){
			for(int j = colStart; j<colEnd; j++){
				current.set(i*width+j, compute(prev,i,j));
			}
		}
	}
	public boolean compute(ArrayList<Boolean> table, int row, int col){
		int sum = 0;
		for (int i = row-1; i < row+2; i++) {
			if (i <0 || i >= length){
				continue;
			}
			for (int j = col-1; j < col+2; j++) {
				if (j <0 || j >= width){
					continue;
				}
				if(table.get(i*width+j)){
					sum++;
				}
			}
		}
		if(table.get(row*width+col)){
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
	
	@Override
	public void run(){
		for (int i = 0; i < generations; i++) {
			step();
			synchronized(globalCounter){
				globalCounter++;
				if(globalCounter == globalThreadCounter){
					globalCounter = 0;
					notifyAll();
				}
				else{
					while(globalCounter < globalThreadCounter){
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			ArrayList<Boolean> temp = current;
			current = prev;
			prev = temp;
		}
	}
	
	
	
}
