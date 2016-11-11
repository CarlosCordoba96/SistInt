package dominio;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.image.PixelGrabber;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
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
public class seconderiv {

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		init();

	}
	public static void init() throws IOException, CloneNotSupportedException{
		BufferedImage image = chargeimage("pics/AlhambraInicialPuzzle4x4.png"); //reading the image file
		BufferedImage image2 = chargeimage("pics/IntermedioAlhambra41.png");
		int rows =4; //You should decide the values for rows and cols variables
		int cols = 4;	        
		int ImgWidth = image.getWidth();
		int ImgHeight = image.getHeight();
		int splitWidth = ImgWidth / cols; // determines the chunk width and height       
		int splitHeight = ImgHeight / rows;
		BufferedImage imgs[][] = new BufferedImage[rows][cols]; //Image array to hold image chunks
		BufferedImage imgs2[][] = new BufferedImage[rows][cols];	        
		int pos[][] = new int [rows][cols]; 
		int orgpos[][] = new int [rows][cols]; 
		int cero[]=new int[2];
		spliting(rows, cols, splitWidth, splitHeight, image, imgs);
		spliting(rows, cols, splitWidth, splitHeight, image2, imgs2);
		findpos(imgs, imgs2, pos);
		findpos(imgs, imgs, orgpos);
		whereiszero(pos, cero);
		printarray(orgpos);

		//acortar usando orientado a objetos
		int ngen=0;
		int nite=0;
		long  time,time_start, time_end;

		int value=0;
;

		State initialstate= new State(rows,cols,cero[0],cero[1],pos);
		nodeTree firstnode = new nodeTree(initialstate);


		//FRONTERA
		/*//////////////////////////////
		Stack <nodeTree> frontier = new Stack<nodeTree>();

		//////////////////////////////*/
		PriorityQueue<nodeTree> frontier = createFrontier();
		insertFrontier(firstnode,frontier);

		ngen++;
		time=System.currentTimeMillis();
		while(true){
			time_start = System.currentTimeMillis();

			///////////////////////////////
			nodeTree node=removeFirstFrontier(frontier);
			//////////////////////////////

			Stack<State> states=node.state.succesor();

			while(!states.isEmpty()){
				Random rndGenerator=new Random();
				//////////////////////////////
				State st=states.pop();
				//System.out.println("ACTION : "+st.action);
				//printarray(st.getPuzzle());
				value=rndGenerator.nextInt(900)+100;
				nodeTree tnode = new nodeTree(node,st,st.action,value);
				//System.out.println("The value of the node is: "+tnode.getValue());
				//System.out.println("########################################################");
				insertFrontier(tnode, frontier);
				//////////////////////////////	
				ngen++;
			}
			nite++;

			time_end = System.currentTimeMillis();
			System.out.println("Iteration nº: " +nite + ". Nodes that have been created: " + ngen + ""
					+ " , time consumed in the iteration " + (time_end-time_start) + ", time since we started"
					+ "creating the nodes " + (time_end-time)/1000);


		}

	}
	/*
	 * 
	 * Method to read the images from files
	 */

	public static BufferedImage chargeimage(String trace) throws IOException{
		File file = new File(trace); 
		FileInputStream img = new FileInputStream(file);
		BufferedImage image = ImageIO.read(img); //reading the image file
		return image;
	}

	public static void spliting (int rows, int cols, int splitWidth, int splitHeight, BufferedImage image, BufferedImage[][] imgs) throws IOException {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				//Initialize the image array with image chunks
				imgs[x][y] = new BufferedImage(splitWidth, splitHeight, image.getType());

				// draws the image chunk
				Graphics2D gr = imgs[x][y].createGraphics();
				gr.drawImage(image, 0, 0, splitWidth, splitHeight, splitWidth * y, splitHeight * x,
						splitWidth * y + splitWidth, splitHeight * x + splitHeight, null);
				gr.dispose();
			}
		}
	}
	/*
	 * Create from the original image a black image in the first position of the array created
	 */
	public static void printarray(int[][] array){


		for(int i = 0; i<array.length;i++){
			for(int j = 0; j<array[0].length;j++){

				System.out.print(array[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	//CAMBIAR ESTE METODOOOOOOOOOOOOOOOOOOO
	public static boolean compareminimg (BufferedImage img1, BufferedImage img2){//COMPARE 2 IMAGES

		if (img1.getWidth() != img2.getWidth() || img1.getHeight()!= img2.getHeight()) {
			return false;
		}
		double npixels= img1.getWidth() * img1.getHeight();
		double nep = 0.0;
		for (int x = 1; x < img2.getWidth(); x++) {
			for (int y = 1; y < img2.getHeight(); y++) {
				if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
					nep++;
				}
			}
		}
		return (((npixels-nep)/npixels)>0.95);//IF THE IMAGE IS UNDER 95% OF SIMILARITY IS FALSE

	}
	/*
	 * We compare 2 arrays of images
	 */
	public static void compareimgs (BufferedImage[][] img, BufferedImage[][] img2) {		//PARA RESULTADO FINAL!
		boolean result = true; 
		outerloop:
			for (int i = 0; i < img.length; i++){
				for (int j = 0; j < img[i].length; j++) {
					result = compareminimg(img[i][j], img2[i][j]);
					if (result == false){
						System.out.println("The images are different.");
						break outerloop;
					}
				}	        	
			}
		if (result) System.out.println("Both images are equal.");
	}
	public static void whereiszero(int [][] array, int[] cero){
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[i].length;j++){
				if(array[i][j]==0){
					cero[0]=i;
					cero[1]=j;
				}
			}
		}
	}
	/*
	 * We calculate the position of the image comparing the clear one
	 */
	public static void findpos (BufferedImage[][] img, BufferedImage[][] img2, int [][] pos) {
		boolean position;
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[i].length; j++) {
				for (int x = 0; x < img2.length; x++) {
					for (int y = 0; y < img2.length; y++) {
						position = compareminimg(img2[i][j], img[x][y]);
						if (position == true)
							pos[i][j] = (x *10) + y;
					}
				}
			}
		}

	}   
	/*
	 * We create the full image from the BufferedImage array
	 */
	public static BufferedImage mergeimg (BufferedImage[][] img, int width, int height, int rows, int cols) throws IOException {
		BufferedImage resimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = resimg.getGraphics();
		int x=0; int y=0;
		int i=0;
		int j=0;
		for (int k = 0; k < img.length; k++) {
			for(int l=0;l<img[k].length ;l++){
				BufferedImage bi =img[k][l];
				j++;
				if(j==cols){
					i++;
					j=0;
				}
				g.drawImage(bi, x, y, null);	    	      
				x += bi.getWidth();
				if(x == resimg.getWidth()){
					x = 0;
					y += bi.getHeight();
				}
			}
		}	    
		//ImageIO.write(resimg,"png",new File("result.png"));//MIX THE MATRIX IN JUST ONE IMAGE 
		return resimg;
	}

	/*
	 * Move to the left the black image
	 */
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