package gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import gui.editHyperParameters.PanelEditHyperParam;
import gui.home.PanelHome;

public class FrameMain {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("SnakeAI");
		JPanel jpanelmain = new JPanel();

		
		CardLayout layout = new CardLayout();
		jpanelmain.setLayout(layout);
		
		PanelHome panelhome = new PanelHome(layout, jpanelmain);
		jpanelmain.add(panelhome, "home");

		PanelEditHyperParam panelEditHyperparam = new PanelEditHyperParam(layout, jpanelmain);
		jpanelmain.add(panelEditHyperparam);
		
		frame.add(jpanelmain);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();		
		frame.setSize(640,480);
		frame.setVisible(true);
		
	
	}

}
