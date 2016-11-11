package dominio;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.PixelGrabber;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.*;

public class ImgProcessor {
	private int rows;
	private int cols;
	private BufferedImage img;
	private BufferedImage img2;
	private BufferedImage spimg [][];
	private BufferedImage spimg2 [][];
	private int [][] pos;
	private int [] zero = new int [2];
	
	public ImgProcessor (int rows, int cols, String trace1, String trace2) throws IOException {
		this.rows = rows;
		this.cols = cols;
		this.img = chargeimage(trace1);
		this.img2 = chargeimage(trace2);
		spimg = createimgarray (rows, cols);
		spimg2 = createimgarray (rows, cols);
		pos = new int [rows][cols];
		
		spliting (rows, cols, img, spimg);
		spliting (rows, cols, img2, spimg2);
		findpos(spimg, spimg2, pos);
		whereiszero (pos, zero);
		printarray(pos);
	}
	
	public BufferedImage [][] createimgarray (int rows, int cols) {		
		BufferedImage image [][] = new BufferedImage[rows][cols];
		return image;
	}
	
	public int [][] createarray (int rows, int cols) {
		int [][] array = new int [rows][cols];
		return array;
	}
	
	/* 
	 * Method to read the images from files
	 */
	public BufferedImage chargeimage(String trace) throws IOException{
		File file = new File(trace); 
		FileInputStream img = new FileInputStream(file);
		BufferedImage image = ImageIO.read(img); //reading the image file
		return image;
	}
	
	/*
	 * Split the image and store each sub-image in each position of the array
	 */
	public  void spliting (int rows, int cols, BufferedImage image, BufferedImage[][] imgs) throws IOException {
		int splitWidth = image.getWidth()/rows;
		int splitHeight = image.getHeight()/cols;	
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
	 * We calculate the position of the image comparing the clear one
	 */
	public void findpos (BufferedImage[][] img, BufferedImage[][] img2, int [][] pos) {
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
	 * We compare 2 arrays of images
	 */
	public void compareimgs (BufferedImage[][] img, BufferedImage[][] img2) {		//PARA RESULTADO FINAL!
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
	
	public boolean compareminimg (BufferedImage img1, BufferedImage img2){//COMPARE 2 IMAGES

		if (img1.getWidth() != img2.getWidth() || img1.getHeight()!= img2.getHeight()) {
			return false;
		}
		double npixels= img1.getWidth() * img1.getHeight();
		double nep = 0.0;
		for (int x = 1; x < img2.getWidth(); x++) {
			for (int y = 1; y < img2.getHeight(); y++) {
				if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Create from the original image a black image in the first position of the array created
	 */
	public void createfirstblackimg (BufferedImage[][] imgs) {//crea el primer recuadro negro
		int width;
		int height;
		width=imgs[0][0].getWidth();
		height=imgs[0][0].getHeight();
		BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imgs[0][0] = imag;					

	}
	
	public void whereiszero(int [][] array, int[] zero){
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[i].length;j++){
				if(array[i][j]==0){
					zero[0]=i;
					zero[1]=j;
				}
			}
		}
	}
	
	public void printarray(int[][] array) {

		for(int i = 0; i<array.length;i++){
			for(int j = 0; j<array[0].length;j++){

				System.out.print(array[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	
	/*
	 * We create the full image from the BufferedImage array
	 */
	public BufferedImage mergeimg (BufferedImage[][] img, int width, int height, int rows, int cols) throws IOException {
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
	public void moveleft(BufferedImage [][] array, int[][] intarray, int[] poscero) {

		int width=array[0][0].getWidth();
		int height=array[0][0].getHeight();
		BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int ibuff = poscero[0], jbuff = poscero[1];
		if(jbuff != 0){
			array[ibuff][jbuff] = array[ibuff][jbuff-1];
			array[ibuff][jbuff-1] = imag;
			intarray[ibuff][jbuff] = intarray[ibuff][jbuff-1];
			intarray[ibuff][jbuff-1] = 0;
			poscero[1] = jbuff-1;
		}
	}
	
	/*
	 * Move to the right the black image
	 */
	public void moveright(BufferedImage [][] array, int[][] intarray, int[] poscero) {
		int width=array[0][0].getWidth();
		int height=array[0][0].getHeight();
		BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int ibuff = poscero[0], jbuff = poscero[1];

		if(jbuff < width-1){
			array[ibuff][jbuff] = array[ibuff][jbuff+1];
			array[ibuff][jbuff+1] = imag;
			intarray[ibuff][jbuff] = intarray[ibuff][jbuff+1];
			intarray[ibuff][jbuff+1] = 0;
			poscero[1] = jbuff+1;
		}
	}
	
	/*
	 * Move up the black image
	 */
	public void moveup(BufferedImage [][] array, int[][] intarray, int[] poscero) {
		int width=array[0][0].getWidth();
		int height=array[0][0].getHeight();
		BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int ibuff = poscero[0], jbuff = poscero[1];

		if(ibuff != 0){
			array[ibuff][jbuff] = array[ibuff-1][jbuff];
			array[ibuff-1][jbuff] = imag;
			intarray[ibuff][jbuff] = intarray[ibuff-1][jbuff];
			intarray[ibuff-1][jbuff] = 0;
			poscero[0] = ibuff-1;
		}
	}

	/*
	 * Move down the black image
	 */
	public void movedown(BufferedImage [][] array, int[][] intarray, int[] poscero) {
		int width=array[0][0].getWidth();
		int height=array[0][0].getHeight();
		BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int ibuff = poscero[0], jbuff = poscero[1];

		if(ibuff < height-1){
			array[ibuff][jbuff] = array[ibuff+1][jbuff];
			array[ibuff+1][jbuff] = imag;
			intarray[ibuff][jbuff] = intarray[ibuff+1][jbuff];
			intarray[ibuff+1][jbuff] = 0;
			poscero[0] = ibuff+1;
		}
	}
	public int[][] getPos() {
		return pos;
	}

	public void setPos(int[][] pos) {
		this.pos = pos;
	}

	public int[] getZero() {
		return zero;
	}

	public void setZero(int[] zero) {
		this.zero = zero;
	}

}