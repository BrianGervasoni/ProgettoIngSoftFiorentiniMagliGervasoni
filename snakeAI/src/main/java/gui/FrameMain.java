package gui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import gui.home.PanelHome;

public class FrameMain {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("SnakeAI");
		JPanel jpanelmain = new JPanel();
		
		CardLayout layout = new CardLayout();
		
		
		PanelHome panelhome = new PanelHome();
		jpanelmain.setLayout(layout);
		jpanelmain.add(panelhome);
		
		frame.add(jpanelmain);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
				
		frame.setSize(640,480);
		frame.setVisible(true);
		
	}

}
