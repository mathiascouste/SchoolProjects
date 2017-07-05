package model.structure.stilllife;

import model.structure.Structure;

public class Biblock implements Structure {

	@Override
	public int[][] getTable() {
		int [][] toRet = {
			{1,1,0,1,1},
			{1,1,0,1,1}
		};
		return toRet;
	}
}
