package sketchbook;

import java.awt.Color;

import javax.swing.*;
public class ButtonPanel extends JPanel{

	private JButton[] buttons;	
	private int buttonCnt=6;
	
	public ButtonPanel(MyButtonHandler handler) {
		super();
		setBackground(Color.LIGHT_GRAY);
		
		// ��ư ���� �� �гο� �߰�
		buttons = new JButton[buttonCnt];
		for(int i=0; i<buttonCnt; i++) {
			buttons[i] = new JButton();
			buttons[i].addActionListener(handler);
			add(buttons[i]);
		}
		
		// ��ư �ؽ�Ʈ ����
		buttons[0].setText("��");
		buttons[1].setText("��");
		buttons[2].setText("/");
		buttons[3].setText("��");
		buttons[4].setText("��");
		buttons[5].setText("�ٰ�");	
	}
	
}
