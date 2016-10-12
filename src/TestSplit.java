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
	    	Puzzle p = new Puzzle();
	    	JFrame frame = new JFrame();
	        File file = new File("C:/Users/Carlos/workspace/Game/pics/alhambra.png"); // I have cat.jpg in my working directory (1080x820) [empieza en el p 30]
	        File file2 = new File ("C:/Users/Carlos/workspace/Game/pics/alhambra.png");
	        FileInputStream img = new FileInputStream(file);
	        FileInputStream img2 = new FileInputStream(file2);
	        BufferedImage image = ImageIO.read(img); //reading the image file
	        BufferedImage image2 = ImageIO.read(img2);

	        int rows = 4; //You should decide the values for rows and cols variables
	        int cols = 4;
	        
	        int ImgWidth = image.getWidth();
	        int ImgHeight = image.getHeight();
	        int splitWidth = ImgWidth / cols; // determines the chunk width and height
	        int splitHeight = ImgHeight / rows;
	        BufferedImage imgs[][] = new BufferedImage[rows][cols]; //Image array to hold image chunks
	        BufferedImage imgs2[][] = new BufferedImage[rows][cols];
	        
	        int pos[][] = new int [rows][cols];
	        
	  
	        
	        spliting(rows, cols, splitWidth, splitHeight, image, imgs);
	        spliting(rows, cols, splitWidth, splitHeight, image2, imgs2);
	        compareimgs(imgs, imgs2);
	        createfirstblackimg(imgs);
	        //showimage(imgs2, ImgWidth, ImgHeight);
	        
	        p.printimg(mergeimg(imgs, ImgWidth, ImgHeight,rows,cols));
	        findpos(imgs, imgs2, pos);
	        printarray(pos);
	        
	        moveright(imgs, pos);
	        p.printimg(mergeimg(imgs, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	        
	        moveleft(imgs, pos);
	        p.printimg(mergeimg(imgs, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	        
	        movedown(imgs,pos);
	        p.printimg(mergeimg(imgs, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	        
	        moveup(imgs,pos);
	        p.printimg(mergeimg(imgs, ImgWidth, ImgHeight,rows,cols));
	        printarray(pos);
	}
	    
	    
	    public static void printarray(int[][] array){
	    	
	    	for(int i = 0; i<array.length;i++){
	    		for(int j = 0; j<array[0].length;j++){
	    			
	    			System.out.print(array[i][j]+" ");
	    		}
	    		System.out.print("\n");
	    	}
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
	        System.out.println("Splitting done");
	        
	        //writing mini images into image files
	        for (int i = 0; i < imgs.length; i++) {
	        	for (int j = 0; j < imgs[i].length; j++) {
	        		ImageIO.write(imgs[i][j], "png", new File("img" + i +""+ j + ".png"));
	        		//En este bucle se pueden imprimir las im�genes una a una
	        	}
	        }
	        System.out.println("Mini images created");
	    }
	    
	    public static void createfirstblackimg (BufferedImage[][] imgs) {//crea el primer recuadro negro
		        	    int width;
		       	        int height;
		       	        width=imgs[0][0].getWidth();
		       	        height=imgs[0][0].getHeight();
		       	        BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		       	        imgs[0][0] = imag;					//Posiblemente hacer todo m�todo para meter la primera entrada en negro.
		       	        
	    }
	    
	    public static boolean compareminimg (BufferedImage img1, BufferedImage img2){//COMPARAR DOS IMAGENES
	    	
	    	if (img1.getWidth() != img2.getWidth() || img1.getHeight()!= img2.getHeight()) {
	            return false;
	       }
	    	int npixels= img1.getWidth() * img1.getHeight();
	       for (int x = 1; x < img2.getWidth(); x++) {
	           for (int y = 1; y < img2.getHeight(); y++) {
	                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
	                    return false;
	                }
	           }
	       }
	       return true;
	    }
	    
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
	    
	    public static BufferedImage mergeimg (BufferedImage[][] img, int width, int height, int rows, int cols) throws IOException {//JUNTA LA MATRIZ EN UNA IMAGEN 
	    	 BufferedImage resimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    	 Graphics g = resimg.getGraphics();
	    	 int imgwidth;
	    	 int imgheigth;
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
	    
	    
	    public static void moveleft(BufferedImage [][] array, int[][] intarray){
	    	int width=array[0][0].getWidth();
   	        int height=array[0][0].getHeight();
	    	BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    	int ibuff = 0, jbuff = 0;
	    	 System.out.println("Array length: "+array[1].length);
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; j<array[i].length-1; j++){	 
	    			if(compareminimg(array[i][j],imag)){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff][jbuff-1];
	    	array[ibuff][jbuff-1] = imag;
	    	intarray[ibuff][jbuff] = intarray[ibuff][jbuff-1];
	    	intarray[ibuff][jbuff-1] = 0;
	    }
	    
	    public static void moveright(BufferedImage [][] array, int[][] intarray){
	    	int width=array[0][0].getWidth();
   	        int height=array[0][0].getHeight();
	    	BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    	int ibuff = 0, jbuff = 0;
	    	 System.out.println("Array length: "+array[1].length);
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; j<array[i].length-1; j++){	 
	    			if(compareminimg(array[i][j],imag)){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff][jbuff+1];
	    	array[ibuff][jbuff+1] = imag;
	    	intarray[ibuff][jbuff] = intarray[ibuff][jbuff+1];
	    	intarray[ibuff][jbuff+1] = 0;
	    }
	    
	    public static void moveup(BufferedImage [][] array, int[][] intarray){
	    	int width=array[0][0].getWidth();
   	        int height=array[0][0].getHeight();
	    	BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    	int ibuff = 0, jbuff = 0;
	    	 System.out.println("Array length: "+array[1].length);
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; j<array[i].length-1; j++){	 
	    			if(compareminimg(array[i][j],imag)){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff-1][jbuff];
	    	array[ibuff-1][jbuff] = imag;
	    	intarray[ibuff][jbuff] = intarray[ibuff-1][jbuff];
	    	intarray[ibuff-1][jbuff] = 0;
	    }
	    
	    
	    public static void movedown(BufferedImage [][] array, int[][] intarray){
	    	
	    	int width=array[0][0].getWidth();
   	        int height=array[0][0].getHeight();
	    	BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    	int ibuff = 0, jbuff = 0;
	    	 System.out.println("Array length: "+array[1].length);
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; j<array[i].length-1; j++){	 
	    			if(compareminimg(array[i][j],imag)){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff+1][jbuff];
	    	array[ibuff+1][jbuff] = imag;
	    	intarray[ibuff][jbuff] = intarray[ibuff+1][jbuff];
	    	intarray[ibuff+1][jbuff] = 0;
	    }
	    
}