package view;

import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.ControllerWaitroom;

public class ViewWaitroom extends JFrame{
	
	private JLabel labelIntro;
	private ControllerWaitroom controller;
	
	public ViewWaitroom() {
		labelIntro = new JLabel("Conectando...");
		
		this.add(labelIntro);
		controller = new ControllerWaitroom(this);
		this.pack();
	}
	
	public void changeText(String text) {
		labelIntro.setText(text);
		this.pack();
		this.repaint();
	}

}
