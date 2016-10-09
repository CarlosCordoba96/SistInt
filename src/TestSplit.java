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

	        File file = new File("D:/Programas/workspace/Game/pics/alhambra.png"); // I have cat.jpg in my working directory (1080x820) [empieza en el p 30]
	        File file2 = new File ("D:/Programas/workspace/Game/pics/alhambraf.png");
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
	        //showimage(imgs, ImgWidth, ImgHeight);
	        //showimage(imgs2, ImgWidth, ImgHeight);
	        
	        compareimgs(imgs, imgs2);
	        findpos(imgs, imgs2, pos);
	        
	        //mergeimg(imgs, ImgWidth, ImgHeight);
	        
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
	        		//En este bucle se pueden imprimir las imágenes una a una
	        	}
	        }
	        System.out.println("Mini images created");
	    }
	    
	    public static void showimage (BufferedImage[][] imgs, int ImgWidth, int ImgHeight) {
	    	JFrame frame = new JFrame();
	    	
	    	frame.getContentPane().setLayout(new FlowLayout());
	        for (int i = 0; i < imgs.length; i++) {
	        	for (int j = 0; j < imgs[i].length; j++) {
	        		if (i == 0 && j == 0){
		        	    int width;
		       	        int height;
		       	        width=imgs[0][0].getWidth();
		       	        height=imgs[0][0].getHeight();
		       	        BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		       	        imgs[0][0] = imag;					//Posiblemente hacer todo método para meter la primera entrada en negro.
		       	        frame.getContentPane().add(new JLabel(new ImageIcon(imag)));
		        	} else {
		        		frame.getContentPane().add(new JLabel(new ImageIcon(imgs[i][j])));
		        	}
	        	}
	        }
	    	frame.pack();
	    	frame.setSize(ImgWidth + 50, ImgHeight + 75);
	    	frame.setVisible(true);
	    }
	    
	    public static boolean compareminimg (BufferedImage img1, BufferedImage img2){
	    	if (img1.getWidth() != img2.getWidth() || img1.getHeight()!= img2.getHeight()) {
	            return false;
	       }
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
	    
	    public static void mergeimg (BufferedImage[][] img, int width, int height, int rows, int cols) throws IOException {
	    	 BufferedImage resimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    	 Graphics g = resimg.getGraphics();
	    	 int x=0; int y=0;
	    	 int i=0;
	    	 int j=0;
	    	    for (int k = 0; k < img.length; k++) {
	    	    	for(int l=0;l<img[k].length ;l++){
					BufferedImage bi =img[k][l];
					System.out.println("img"+i+""+j+".png");
	    	        j++;
	    	        if(j==cols){
	    	        	i++;
	    	        	j=0;
	    	        }
	    	        g.drawImage(bi, x, y, null);
	    	        System.out.println(" "+i + " "+j);
	    	        x += bi.getWidth();
	    	        if(x == resimg.getWidth()){
	    	            x = 0;
	    	            y += bi.getHeight();
	    	        }
	    	    	}
				}
	    	    
	    	 ImageIO.write(resimg,"png",new File("result.png"));
	    	 System.out.println("FINAL IMAGE CREATED");
	    }
	    
	    public static void moveleft(int[][] array){
	    	
	    	int ibuff = 0, jbuff = 0;
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; i<array[0].length; j++){
	    			if(array[i][j] == 0){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff-1][jbuff];
	    	array[ibuff-1][jbuff] = 0;
	    	//Aqui se cambia la imagen displayeada
	    }
	    
	    public static void moveright(int[][] array){
	    	
	    	int ibuff = 0, jbuff = 0;
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; i<array[0].length; j++){
	    			if(array[i][j] == 0){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff+1][jbuff];
	    	array[ibuff+1][jbuff] = 0;
	    	//Aqui se cambia la imagen displayeada
	    }
	    
	    public static void moveup(int[][] array){
	    	
	    	int ibuff = 0, jbuff = 0;
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; i<array[0].length; j++){
	    			if(array[i][j] == 0){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff][jbuff+1];
	    	array[ibuff][jbuff+1] = 0;
	    	//Aqui se cambia la imagen displayeada
	    }
	    
	    public static void movedown(int[][] array){
	    	
	    	int ibuff = 0, jbuff = 0;
	    	for(int i = 0; i<array.length; i ++){
	    		for(int j = 0; i<array[0].length; j++){
	    			if(array[i][j] == 0){
	    				ibuff = i;
	    				jbuff = j;
	    			}
	    		}
	    	}
	    	array[ibuff][jbuff] = array[ibuff][jbuff-1];
	    	array[ibuff][jbuff-1] = 0;
	    	//Aqui se cambia la imagen displayeada
	    }
	    
}