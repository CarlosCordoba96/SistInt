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
	        FileInputStream img = new FileInputStream(file);
	        BufferedImage image = ImageIO.read(img); //reading the image file

	        int rows = 4; //You should decide the values for rows and cols variables
	        int cols = 4;
	        
	        int ImgWidth = image.getWidth();
	        int ImgHeight = image.getHeight();
	        int splitWidth = ImgWidth / cols; // determines the chunk width and height
	        int splitHeight = ImgHeight / rows;
	        BufferedImage imgs[][] = new BufferedImage[rows][cols]; //Image array to hold image chunks
	        
	        spliting(rows, cols, splitWidth, splitHeight, image, imgs);
	        showimage(imgs, ImgWidth, ImgHeight);
	        //compareimg();
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
	    	//
	    	frame.getContentPane().setLayout(new FlowLayout());
	        for (int i = 0; i < imgs.length; i++) {
	        	for (int j = 0; j < imgs[i].length; j++) {
	        		if (i == 0 && j == 0){
		        	    int width;
		       	        int height;
		       	        width=imgs[0][0].getWidth();
		       	        height=imgs[0][0].getHeight();
		       	        BufferedImage imag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		       	        imgs[0][0] = imag;
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
	    
	    public static boolean compareimg (BufferedImage img1, BufferedImage img2){
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
}