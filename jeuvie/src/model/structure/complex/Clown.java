package model.structure.complex;

import model.structure.Structure;

public class Clown implements Structure {

	@Override
	public int[][] getTable() {
		int [][] toRet = {
				{1,1,1},
				{1,0,1},
				{1,0,1}
		};
		return toRet;
	}

}
