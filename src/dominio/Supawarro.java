package dominio;

import java.util.Queue;

public class Supawarro {

	public static void main(String[] args) {
		System.out.println("Empieza el programa");
		int[][] originalpuzzle = {{0,1,2,3},{10,11,12,13},{20,21,22,23},{30,31,32,33}};
		int[][] madeuppuzzle = {{10,1,2,3},{11,30,12,13},{20,21,22,23},{0,31,32,33}};
		StateSpace goal = new StateSpace(4,4,0,0,originalpuzzle);
		StateSpace origin = new StateSpace(4,4,3,0,madeuppuzzle);
		Problem problem = new Problem(goal,origin);
		Queue<Character> s = problem.search("BFS", 1000000, 2);
		System.out.println(s.toString());
		System.out.println();
	}

}
