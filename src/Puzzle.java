import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Puzzle {

	protected static JFrame frame;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Puzzle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void printimg(BufferedImage img){
		frame.getContentPane().removeAll();
    	int imgwidth,imgheigth;
    	imgwidth=img.getWidth();
    	imgheigth=img.getHeight();
    	frame.getContentPane().setLayout(new FlowLayout());
    	frame.getContentPane().add(new JLabel(new ImageIcon(img)));
    	
    	frame.setSize(imgwidth+30, imgheigth+50);
    	frame.setVisible(true);
    	
    	
    }
	

}
