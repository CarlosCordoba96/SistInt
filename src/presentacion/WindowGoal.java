package presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Rectangle;
import java.util.Queue;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextPane;

public class WindowGoal {

	private JFrame frame;
	private final JLabel lblNMoves = new JLabel("Number of moves:");
	private final JLabel lblNodesCreated = new JLabel("Nodes Created:");
	private final JLabel lblVisitedNodes = new JLabel("Visited Nodes:");
	private final JLabel lblTimeSpent = new JLabel("Time Spent:");
	private final JLabel lblNMov = new JLabel("");
	private final JLabel lblNCreated = new JLabel("");
	private final JLabel lblVNodes = new JLabel("");
	private final JLabel lblTime = new JLabel("");
	private final JTextPane txtPath = new JTextPane();
	private final JLabel lblPathTaken = new JLabel("Path Taken:");


	/**
	 * Create the application.
	 */
	public WindowGoal(int moves, int nodes, int vnodes, int time, Queue q) {
		initialize();
		information(moves,nodes,vnodes,time,q);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(new Rectangle(500, 700, 0, 0));
		frame.setBounds(100, 100, 469, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		{
			GridBagConstraints gbc_lblNMoves = new GridBagConstraints();
			gbc_lblNMoves.insets = new Insets(0, 0, 5, 5);
			gbc_lblNMoves.gridx = 2;
			gbc_lblNMoves.gridy = 2;
			frame.getContentPane().add(lblNMoves, gbc_lblNMoves);
		}
		{
			GridBagConstraints gbc_lblNMov = new GridBagConstraints();
			gbc_lblNMov.insets = new Insets(0, 0, 5, 0);
			gbc_lblNMov.gridx = 5;
			gbc_lblNMov.gridy = 2;
			frame.getContentPane().add(lblNMov, gbc_lblNMov);
		}
		{
			GridBagConstraints gbc_lblNodesCreated = new GridBagConstraints();
			gbc_lblNodesCreated.insets = new Insets(0, 0, 5, 5);
			gbc_lblNodesCreated.gridx = 2;
			gbc_lblNodesCreated.gridy = 4;
			frame.getContentPane().add(lblNodesCreated, gbc_lblNodesCreated);
		}
		{
			GridBagConstraints gbc_lblNCreated = new GridBagConstraints();
			gbc_lblNCreated.insets = new Insets(0, 0, 5, 0);
			gbc_lblNCreated.gridx = 5;
			gbc_lblNCreated.gridy = 4;
			frame.getContentPane().add(lblNCreated, gbc_lblNCreated);
		}
		{
			GridBagConstraints gbc_lblVisitedNodes = new GridBagConstraints();
			gbc_lblVisitedNodes.insets = new Insets(0, 0, 5, 5);
			gbc_lblVisitedNodes.gridx = 2;
			gbc_lblVisitedNodes.gridy = 6;
			frame.getContentPane().add(lblVisitedNodes, gbc_lblVisitedNodes);
		}
		{
			GridBagConstraints gbc_lblVNodes = new GridBagConstraints();
			gbc_lblVNodes.insets = new Insets(0, 0, 5, 0);
			gbc_lblVNodes.gridx = 5;
			gbc_lblVNodes.gridy = 6;
			frame.getContentPane().add(lblVNodes, gbc_lblVNodes);
		}
		{
			GridBagConstraints gbc_lblTimeSpent = new GridBagConstraints();
			gbc_lblTimeSpent.insets = new Insets(0, 0, 5, 5);
			gbc_lblTimeSpent.gridx = 2;
			gbc_lblTimeSpent.gridy = 8;
			frame.getContentPane().add(lblTimeSpent, gbc_lblTimeSpent);
		}
		{
			GridBagConstraints gbc_lblTime = new GridBagConstraints();
			gbc_lblTime.insets = new Insets(0, 0, 5, 0);
			gbc_lblTime.gridx = 5;
			gbc_lblTime.gridy = 8;
			frame.getContentPane().add(lblTime, gbc_lblTime);
		}
		{
			GridBagConstraints gbc_lblPathTaken = new GridBagConstraints();
			gbc_lblPathTaken.insets = new Insets(0, 0, 5, 5);
			gbc_lblPathTaken.gridx = 2;
			gbc_lblPathTaken.gridy = 9;
			frame.getContentPane().add(lblPathTaken, gbc_lblPathTaken);
		}
		{
			GridBagConstraints gbc_txtPath = new GridBagConstraints();
			gbc_txtPath.gridwidth = 2;
			gbc_txtPath.insets = new Insets(0, 0, 5, 0);
			gbc_txtPath.fill = GridBagConstraints.BOTH;
			gbc_txtPath.gridx = 4;
			gbc_txtPath.gridy = 9;
			frame.getContentPane().add(txtPath, gbc_txtPath);
		}
		
		
		
	}
	
	public void information (int moves, int nodes, int vnodes, int time, Queue q) {
		lblNMov.setText("" + moves);
		lblNCreated.setText("" + nodes);
		lblVNodes.setText("" + vnodes);
		lblTime.setText("" + time);
		txtPath.setText(q.toString());
	}

}
