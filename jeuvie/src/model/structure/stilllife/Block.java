package model.structure.stilllife;

import model.structure.Structure;

public class Block implements Structure {

	@Override
	public int[][] getTable() {
		int [][] toRet = {
			{1,1},
			{1,1}
		};
		return toRet;
	}
}
