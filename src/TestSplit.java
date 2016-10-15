	import javax.imageio.ImageIO;
	
	import javax.swing.ImageIcon;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import java.awt.image.PixelGrabber;
	
	import java.awt.image.BufferedImage;
	import java.io.*;
	import java.awt.*;


public class TestSplit {

	    public static void main(String[] args) throws IOException {
	    	init();
	        
	}
	    public static void init() throws IOException{
	    	Puzzle p = new Puzzle();
	    	JFrame frame = new JFrame();	        
	        BufferedImage image = chargeimage("C:/Users/Carlos/workspace/Game/pics/Alhambra4x4/AlhambraInicialPuzzle4x4.png"); //reading the image file
	        BufferedImage image2 = chargeimage("C:/Users/Carlos/workspace/Game/pics/Alhambra4x4/IntermedioAlhambra41.png");
	        
	        int rows =calcrows(image); //You should decide the values for rows and cols variables
	        int cols = calcols(image);	        
	        int ImgWidth = image.getWidth();
	        int ImgHeight = image.getHeight();
	        int splitWidth = ImgWidth / cols; // determines the chunk width and height       
	        int splitHeight = ImgHeight / rows;	       
	        BufferedImage imgs[][] = new BufferedImage[rows][cols]; //Image array to hold image chunks
	        BufferedImage imgs2[][] = new BufferedImage[rows][cols];	        
	        int pos[][] = new int [rows][cols]; 
	        int cero[]=new int[2];
	        
	        spliting(rows, cols, splitWidth, splitHeight, image, imgs);
	        spliting(rows, cols, splitWidth, splitHeight, image2, imgs2);
	        compareimgs(imgs, imgs2);
	        //createfirstblackimg(imgs);
	        //showimage(imgs2, ImgWidth, ImgHeight);
	        
	        p.printimg(mergeimg(imgs2, ImgWidth, ImgHeight,rows,cols));
	        findpos(imgs, imgs2, pos);
	        printarray(pos);
	        whereiszero(pos,cero);
	        
	        moveleft(imgs2, pos,cero);
	        p.printimg(mergeimg(imgs2, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	        
	        moveright(imgs2, pos,cero);
	        p.printimg(mergeimg(imgs2, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	        
	        movedown(imgs2,pos,cero);
	        p.printimg(mergeimg(imgs2, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	        
	        moveup(imgs2,pos,cero);
	        p.printimg(mergeimg(imgs2, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	    }
	    /*
	     * 
	     * Method to read the images from files
	     */
	    public static BufferedImage chargeimage(String trace) throws IOException{
	    	File file = new File(trace); // I have cat.jpg in my working directory (1080x820) [empieza en el p 30]
	        FileInputStream img = new FileInputStream(file);
	        BufferedImage image = ImageIO.read(img); //reading the image file
	        return image;
	    }
	    /*
	     * 
	     * Calculate the nº of rows of the image
	     */
	    
	    public static int calcrows(BufferedImage img){
	    	int n=0;
	    	int i;
	    	BufferedImage imag = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	    	for(i=0;i<img.getHeight();i++){
	    		 if (img.getRGB(0,i) != imag.getRGB(0, 0)) {
	    			 break;
	    		 }
	    		 n++;
	    	}
	    	return img.getHeight()/n;
	    }
	    /*
	     * Calculate the nº of cols of the image
	     */
	    public static int calcols(BufferedImage img){
	    	int n=0;
	    	int i;
	    	BufferedImage imag = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	    	for(i=0;i<img.getWidth();i++){	    		
	    		 if (img.getRGB(i,0) != imag.getRGB(0, 0)) {
	    			 break;
	    		 }
	    		 n++;
	    	}
	    	return img.getWidth()/n;
	    }
	    
	    public static void printarray(int[][] array){
	    	
	    	for(int i = 0; i<array.length;i++){
	    		for(int j = 0; j<array[0].length;j++){
	    			
	    			System.out.print(array[i][j]+" ");
	    		}
	    		System.out.print("\n");
	    	}
	    }
	    
	    /*
	     * Split the image and store each sub-image in each position of the array
	     */
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
	        System.out.println("Splitting done");
	        
	        //writing mini images into image files
	        for (int i = 0; i < imgs.length; i++) {
	        	for (int j = 0; j < imgs[i].length; j++) {
	        		ImageIO.write(imgs[i][j], "png", new File("img" + i +""+ j + ".png"));
	        		//En este bucle se pueden imprimir las imágenes una a una
	        	}
	        }
	        System.out.println("Mini images created");
	    }
	    /*
	     * Create from the original image a black image in the first position of the array created
	     */
	    public static void createfirstblackimg (BufferedImage[][] imgs) {//crea el primer recuadro negro
		        	    int width;
		       	        int height;
		       	        width=imgs[0][0].getWidth();
		       	        height=imgs[0][0].getHeight();
		       	        BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		       	        imgs[0][0] = imag;					//Posiblemente hacer todo método para meter la primera entrada en negro.
		       	        
	    }
	    /*
	     * We compare 2 images
	     */
	    public static boolean compareminimg (BufferedImage img1, BufferedImage img2){//COMPARAR DOS IMAGENES
	    	
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
	       return (((npixels-nep)/npixels)>0.95);
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
	    	
			for (int k = 0; k < pos.length; k++) {
				for (int z = 0; z < pos[k].length; z++) {
					System.out.println(pos[k][z]);
				}
			}
	    }   
	    /*
	     * We create the full image from the BufferedImage array
	     */
	    public static BufferedImage mergeimg (BufferedImage[][] img, int width, int height, int rows, int cols) throws IOException {//JUNTA LA MATRIZ EN UNA IMAGEN 
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
	    	    
	    	 ImageIO.write(resimg,"png",new File("result.png"));
	    	 System.out.println("FINAL IMAGE CREATED");
		    	return resimg;
	    }
	    
	    /*
	     * Move to the left the black image
	     */
public static void moveleft(BufferedImage [][] array, int[][] intarray, int[] poscero){
	    	
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
public static void moveright(BufferedImage [][] array, int[][] intarray, int[] poscero){
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
public static void moveup(BufferedImage [][] array, int[][] intarray, int[] poscero){
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
public static void movedown(BufferedImage [][] array, int[][] intarray, int[] poscero){
	
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
	
}