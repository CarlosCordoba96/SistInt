package dominio;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.image.PixelGrabber;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.awt.*;
import presentacion.Puzzle;

/**
 * @author Alvaro Angel-Moreno Pinilla, Carlos C�rdoba Ruiz & Roberto Plaza Romero
 */
/*
 * 
 */
public class TestLinkedlist{

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
		LinkedList<nodeTree> frontier = createeFrontier();
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
			System.out.println(" Llist Iteration nº: " +nite + ". Nodes that have been created: " + ngen + ""
					+ " , time consumed in the iteration " + (time_end-time_start) + ", time since we started"
					+ "creating the nodes " + (time_end-time)/1000);


		}
	}
	public static LinkedList<nodeTree> createeFrontier(){
		return new LinkedList<nodeTree>();
	}

	public static void insertFrontier(nodeTree t, LinkedList<nodeTree> frontier){
		class Mycomparator implements Comparator<nodeTree>{

			public int compare(nodeTree e1, nodeTree e2) {
				if(((dominio.nodeTree) e1).getValue() > ((dominio.nodeTree) e2).getValue()){
					return 1;
				}
				else if(((dominio.nodeTree) e1).getValue() < ((dominio.nodeTree) e2).getValue()){
					return -1;
				}
				else {
					return 0;
				}
			}
		}
		frontier.add(t);
		Collections.sort(frontier,new Mycomparator());
	}

	public static nodeTree removeFirstFrontier(LinkedList<nodeTree> frontier){
		return frontier.poll();
	}

	public static boolean frontierIsEmpty(LinkedList <nodeTree> frontier){
		return frontier.isEmpty();
	}

}