package sketchbook;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Sketchbook extends JFrame{

	private ButtonPanel buttonPanel;		// ��ư �г�
	private PaintPanel paintPanel;			// �׸��� ����
	private MyButtonHandler buttonHandler;	// ��ư �̺�Ʈ ó�� ���� ButtonHandler 
	
	public Sketchbook(String title) {
		super(title);
		display();
	}
	
	// ȭ�� ���� method
	private void display() {
		
		// ������â ���ͷ� ��ġ�ϱ� ���� ��ũ��/������ ������ ����
		Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
		Dimension frameSize = new Dimension ( 1024, 768 );

		// ������â �⺻ ����
		setBounds ( screenSize.width / 2 - frameSize.width / 2, 
					screenSize.height / 2 - frameSize.height / 2,
		            frameSize.width, frameSize.height );
		
		setResizable(false);
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());
		
		// �׸��� ���� (PaintPanel) ����
		paintPanel = new PaintPanel();
		paintPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// ��ư ���� (ButtonPane) ����
		buttonHandler = new MyButtonHandler(paintPanel);	// ButtonHandler�� PaintPanel �Ѱ��ֱ�
		buttonPanel = new ButtonPanel(buttonHandler);		// ButtonPanel�� �ش� ButtonHandler ���
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// Component �߰�
		add(buttonPanel, BorderLayout.NORTH);
		add(paintPanel, BorderLayout.CENTER);
	
		System.out.println("display setting fin!");
	}
		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sketchbook sketchbook = new Sketchbook("�ξƱ׸���");
	}


}
