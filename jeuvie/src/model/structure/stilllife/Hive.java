package model.structure.stilllife;

import model.structure.Structure;

public class Hive implements Structure{

	@Override
	public int[][] getTable() {
		int [][] toRet = {
			{0,1,1,0},
			{1,0,0,1},
			{0,1,1,0}
		};
		return toRet;
	}
}
