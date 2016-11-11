package dominio;

import javax.imageio.ImageIO;





import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.awt.*;

/**
 * @author Alvaro Angel-Moreno Pinilla, Carlos C�rdoba Ruiz & Roberto Plaza Romero
 */
/*
 * 
 */
public class TestPQueue{

	public static void main(String[] args) throws IOException{
		init();

	}
	public static void init() throws IOException {
		int rows =4;
		int cols = 4;	  
		String img1="pics/AlhambraInicialPuzzle4x4.png";
		String img2="pics/IntermedioAlhambra41.png";
		ImgProcessor img= new ImgProcessor(rows,cols,img1,img2);
		int cero[]=img.getZero();
		int pos [][]=img.getPos();
		int ngen=0;
		int nite=0;
		long  time,time_start, time_end;
		int value=0;

		State initialstate= new State(rows,cols,cero[0],cero[1],pos);
		nodeTree firstnode = new nodeTree(initialstate);
		PriorityQueue<nodeTree> frontier = createFrontier();
		insertFrontier(firstnode,frontier);

		ngen++;
		time=System.currentTimeMillis();
		while(true){
			time_start = System.currentTimeMillis();
			nodeTree node=removeFirstFrontier(frontier);
			Stack<State> states=node.state.succesor();
			while(!states.isEmpty()){
				Random rndGenerator=new Random();		
				State st=states.pop();
				value=rndGenerator.nextInt(900)+100;
				nodeTree tnode = new nodeTree(node,st,st.action,value);
				insertFrontier(tnode, frontier);
				ngen++;
			}
			nite++;

			time_end = System.currentTimeMillis();
			System.out.println(" PQueue Iteration nº: " +nite + ". Nodes that have been created: " + ngen + ""
					+ " , time consumed in the iteration " + (time_end-time_start) + ", time since we started"
					+ "creating the nodes " + (time_end-time)/1000);


		}
	}

	public static PriorityQueue<nodeTree> createFrontier(){
		return new PriorityQueue<nodeTree>(new Comparator<nodeTree>() {
			public int compare(nodeTree e1, nodeTree e2) {
				if(e1.getValue() > e2.getValue())
					return 1;
				
				else if(e1.getValue() < e2.getValue())
					return -1;
				
				else
					return 0;
			}

		});
	}

	public static void insertFrontier(nodeTree t, Queue<nodeTree> frontier){
		frontier.add(t);
		}

	public static nodeTree removeFirstFrontier(Queue<nodeTree> frontier){
		return frontier.poll();
		}

	public static boolean frontierIsEmpty(Queue<nodeTree> frontier){
		return frontier.isEmpty();
		}
}