package sketchbook;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Sketchbook extends JFrame{

	private ButtonPanel buttonPanel;		// 버튼 패널
	private PaintPanel paintPanel;			// 그림판 영역
	private MyButtonHandler buttonHandler;	// 버튼 이벤트 처리 위한 ButtonHandler 
	
	public Sketchbook(String title) {
		super(title);
		display();
	}
	
	// 화면 구성 method
	private void display() {
		
		// 윈도우창 센터로 위치하기 위해 스크린/프레임 사이즈 저장
		Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
		Dimension frameSize = new Dimension ( 1024, 768 );

		// 윈도우창 기본 설정
		setBounds ( screenSize.width / 2 - frameSize.width / 2, 
					screenSize.height / 2 - frameSize.height / 2,
		            frameSize.width, frameSize.height );
		
		setResizable(false);
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());
		
		// 그림판 영역 (PaintPanel) 생성
		paintPanel = new PaintPanel();
		paintPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// 버튼 영역 (ButtonPane) 생성
		buttonHandler = new MyButtonHandler(paintPanel);	// ButtonHandler에 PaintPanel 넘겨주기
		buttonPanel = new ButtonPanel(buttonHandler);		// ButtonPanel에 해당 ButtonHandler 등록
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// Component 추가
		add(buttonPanel, BorderLayout.NORTH);
		add(paintPanel, BorderLayout.CENTER);
	
		System.out.println("display setting fin!");
	}
		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sketchbook sketchbook = new Sketchbook("인아그림판");
	}


}
