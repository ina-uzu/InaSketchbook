package sketchbook;

import java.awt.Color;

import javax.swing.*;
public class ButtonPanel extends JPanel{

	private JButton[] buttons;	
	private int buttonCnt=6;
	
	public ButtonPanel(MyButtonHandler handler) {
		super();
		setBackground(Color.LIGHT_GRAY);
		
		// 버튼 생성 및 패널에 추가
		buttons = new JButton[buttonCnt];
		for(int i=0; i<buttonCnt; i++) {
			buttons[i] = new JButton();
			buttons[i].addActionListener(handler);
			add(buttons[i]);
		}
		
		// 버튼 텍스트 설정
		buttons[0].setText("←");
		buttons[1].setText("→");
		buttons[2].setText("/");
		buttons[3].setText("●");
		buttons[4].setText("□");
		buttons[5].setText("다각");	
	}
	
}
