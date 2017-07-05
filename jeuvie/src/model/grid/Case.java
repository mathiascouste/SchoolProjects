package model.grid;

public class Case implements Runnable {
	private static String names[] = {"Boris","Jean","Jacques","Pierre","Feuille","Ciseaux"};
	private static String lastnames[] = {"Arbre","Dupont","Dupond","Culeuh","Martin","Elsine"};
	private boolean state,lastState,go;
	private int date;
	private String name,rule;
	private Case[][] neighborhood;
	boolean willDie, willBorn;
	
	public void born() {
		this.willBorn = true;
	}

	public Case(String rule) {
		this.state = false;
		this.lastState = false;
		this.date = 0;
		this.neighborhood = null;
		this.name = Case.randomName();
		this.rule = rule;
		this.go = true;
		this.willDie = false;
		this.willBorn = false;
	}
	
	private void checkup() {
		this.lastState = this.state;
		if(this.willDie) {
			this.state = false;
			this.willDie = false;
		}
		if(this.willBorn) {
			this.state = true;
			this.willBorn = false;
		}
	}

	public void die() {
		this.willDie = true;
	}
	
	public void doTheThing() {
		if(isNeighborhoodReady()) {
			checkup();
			if(!this.rule.equals("")) {
			
			} else {
				int aliveNeighbor = getAliveNeighbor();
				if(aliveNeighbor == 3) {
					this.born();
				}
				if(aliveNeighbor == 2) {
					this.stay();
				}
				if(aliveNeighbor < 2 || aliveNeighbor > 3) {
					die();
				}
			}
			this.date++;
		}
	}

	private void stay() {
		// TODO Auto-generated method stub
		
	}

	public int getAliveNeighbor() {
		int cpt = 0;
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				if(this.neighborhood[i][j].isAlive(this.date)) cpt++;
			}
		}
		if(this.state) cpt--;
		return cpt;
	}

	private boolean isNeighborhoodReady() {
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				int dateDiff = this.neighborhood[i][j].getDate() - this.date;
				if(dateDiff < 0) {
					return false;
				}
			}
		}
		return true;
	}

	private static String randomName() {
		return names[(int) (Math.random()*names.length)]+" "+lastnames[(int) (Math.random()*lastnames.length)];
	}

	@Override
	public void run() {
		while(this.go) {
			try {
				Thread.sleep((long) (100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.doTheThing();
		}
	}

	public static String[] getNames() {
		return names;
	}

	public static void setNames(String[] names) {
		Case.names = names;
	}

	public static String[] getLastnames() {
		return lastnames;
	}

	public static void setLastnames(String[] lastnames) {
		Case.lastnames = lastnames;
	}

	public boolean isAlive() {
		return state;
	}
	
	public boolean isAlive(int date) {
		if(this.date == date) return this.state;
		if(this.date < date) {
			if(this.willBorn) return true;
			if(this.willDie) return false;
			return this.state;
		} else {
			return this.lastState;
		}
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Case[][] getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(Case[][] neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	public void stop() {
		this.go = false;
	}
	
	public String toString() {
		return "Hello ! I am " + this.name;
	}

	public String getSign() {
		String toRet;
		/*if(this.state) toRet = "X";
		else if (this.getAliveNeighbor()==0)  toRet = " ";
		else toRet = ""+this.getAliveNeighbor();*/
		if(this.state) toRet = "☮";
		else toRet = " ";
		if(this.state && this.willDie) toRet = "☮";
		if(!this.state && this.willBorn) toRet = " ";
		return toRet;
	}
}
