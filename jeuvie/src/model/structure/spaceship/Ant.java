package model.structure.spaceship;

import model.structure.Structure;

public class Ant implements Structure {

	@Override
	public int[][] getTable() {
		int [][] toRet = {
				{0,1,1},
				{1,1,0},
				{0,0,1}
		};
		return toRet;
	}

}
