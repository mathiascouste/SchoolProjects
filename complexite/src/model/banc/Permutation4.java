package model.banc;

import java.util.ArrayList;

public class Permutation4 {
	private int factorielle(int k) {
		int f = 1;
		for (int i = 1; i <= k; i++)
			f = f * i;
		return f;
	}

	private void Permutation(int k, int[] r) {
		int fact = 1;
		for (int i = 2; i < r.length + 1; i++) {
			fact = fact * (i - 1);
			int pos = i - ((k / fact) % i) - 1;
			swap(pos, i - 1, r);
		}
	}

	private void swap(int i, int j, int[] A) {
		int oi = A[i];
		int oj = A[j];
		A[j] = oi;
		A[i] = oj;
	}

	public ArrayList<String> afficherpermutations(int[] v) {
		ArrayList<String> strs = new ArrayList<String>();
		for (int i = 0; i < factorielle(v.length); i++) {

			Permutation(i, v);
			String str ="";
			for (int j = 0; j < v.length; j++) {
				str += v[j];
				if(j != v.length-1) str += ",";
			}
			strs.add(str);
		}
		return strs;
	}

	public static ArrayList<String> recupererCombinaisons(int n) {
		int[] v = new int[n];
		for(int i = 0 ; i < n ; i++) v[i] = i;
		Permutation4 p4 = new Permutation4();
		return p4.afficherpermutations(v);
	}
	
	public static void main(String[] args) {
		ArrayList<String> l = recupererCombinaisons(10);
		for(String s : l) System.out.println(s);
	}
}