package sketchbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonHandler implements ActionListener{
	private PaintPanel panel;
	public MyButtonHandler(PaintPanel panel) {
		this.panel=panel;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("��")) {
			panel.setType(PaintPanel.TYPE_UNDO);
		}
		
		else if(e.getActionCommand().equals("��")) {
			panel.setType(PaintPanel.TYPE_REDO);
		}
		
		else if(e.getActionCommand().equals("/")) {
			panel.setType(PaintPanel.TYPE_LINE);
		}
		else if(e.getActionCommand().equals("��")) {
			panel.setType(PaintPanel.TYPE_CIRCLE);
		}
		
		else if(e.getActionCommand().equals("��")) {
			panel.setType(PaintPanel.TYPE_RECT);
		}
		
		else if(e.getActionCommand().equals("�ٰ�")) {
			panel.setType(PaintPanel.TYPE_POLY);
		}
	}
	
}
