package model;

import java.util.ArrayList;
import java.util.List;

public class TempWall {
	private static final int RANGE = 10;
	private List<Publication> wall;
	private int startBound, endBound;
	
	public TempWall(List<Publication> wall) {
		this.wall = new ArrayList<Publication>(wall);
		this.startBound = 0;
		this.endBound = wall.size();
		this.checkEndBound();
	}
	
	public List<Publication> getTempWall() {
		List<Publication> tmp = new ArrayList<Publication>();
		for(int i = startBound ; i < endBound+1 ; i++) {
			tmp.add(this.wall.get(i));
		}
		return tmp;
	}
	
	public void plus() {
		if(this.startBound + 10>= this.wall.size()) {
			this.startBound = this.wall.size() - RANGE;
		} else {
			this.startBound += 10;
		}
	}
	public void minus() {
		if(this.startBound - 10 < 0) {
			this.startBound = 0;
		} else {
			this.startBound -= 10;
		}
	}
	private void checkEndBound() {
		this.endBound = this.startBound + RANGE;
		if(this.endBound >= this.wall.size()) {
			this.endBound = this.wall.size()-1;
		}
	}
}
