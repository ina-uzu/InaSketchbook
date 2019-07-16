package sketchbook;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import sketchbook.ShapeComponent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.*;

public class PaintPanel extends JPanel{
	public final static int TYPE_UNDO=0;
	public final static int TYPE_REDO=1;
	public final static int TYPE_LINE=2;
	public final static int TYPE_CIRCLE=3;
	public final static int TYPE_RECT=4;
	public final static int TYPE_POLY=5;
	
	private int curId=0;
	
	private Stack <ShapeComponent> stack=new Stack<ShapeComponent>();		// ���� �׸��� ������ �ö�� �ִ� items
	private Stack <ShapeComponent> tmpStack =new Stack<ShapeComponent>();	// undo�� ������ items (redo ������ ����)
	private int lineThickness =1;
	
	private int x1,x2,y1,y2;
	private int type =-1;
	
	private boolean firstFlag=true;
	private boolean lineFinishedFlag=false;
	
	private List<Integer> xPoints=null;
	private List<Integer> yPoints=null;
	
	
	public PaintPanel() {
		super();
		setSize(1024,768);
		setBackground(Color.WHITE);
		
		addListener();	// ���콺 �̺�Ʈ ������ �߰�
	}
	
	
	public void addListener() {
		
		// ���콺 �̺�Ʈ ������ �߰�
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				
				// Left Click
				if( m.getButton() == MouseEvent.BUTTON1) {
					x1 = m.getX();
					y1 = m.getY();
					
					// Ÿ�� ���� �� ó������ Ŭ���ϴ� ��� x2,y2�� �ʱ�ȭ 
					if(firstFlag) {
						x2=x1;
						y2=y1;
						firstFlag =false;
					}
					
					if(type == TYPE_POLY) {
						 // �ٰ��� ó�� �׸��� ���
						 if(xPoints == null) {
							xPoints = new ArrayList<Integer>();
							yPoints = new ArrayList<Integer>();				
						 }
						 
						 xPoints.add(x1);
						 yPoints.add(y1);
					}
		               
				}		
				
				
				// Right Click
				else if(m.getButton()== MouseEvent.BUTTON3) {
					
					// Polygon �ڵ� �ϼ�
					if( type==TYPE_POLY) {
						int sx=-1, sy=-1;
						// stack���� �ٰ��� ù��°�� ��������
						for(int i=stack.size()-1; i>=0; i--) {
							if(stack.get(i).getId()!=curId)
								break;
							sx= stack.get(i).getX1();
							sy= stack.get(i).getY1();
						}

						// 2�� �̻��� ���� �ִ� ���, �ش� �ٰ��� �ϼ�
						if( sx >0 && sy >0 && xPoints.size()>0) {
							pushNewShape(sx, sy,xPoints.get(0), yPoints.get(0));
							curId++;
							xPoints = null;
							yPoints = null;
						}
					}	
				}
				
				repaint();
				
			}

			
			public void mouseReleased(MouseEvent m){
				
				// Left Click
				if( m.getButton()== MouseEvent.BUTTON1) {
				
	               x2 = m.getX();
	               y2 = m.getY();
		           
	               // ������ ���, ���콺 Release�ϸ� ���ο� ���� �׸� �غ� �ؾ� �ϹǷ� firstFlag set!
	               if(type == TYPE_LINE)
		        	   firstFlag =true;
		           
		           // ���ο� Component�� ����ϱ� ���� curId ����
	               if(type != TYPE_POLY)	// polygon�� right click������ �ϳ��� id�̹Ƿ� ���� 
	            	   curId++;
	               
	               // ���ο� Component �߰� �� panel refresh
	               if(type!=TYPE_LINE) {	// ������ ��� �ش� �۾��� drawComponent���� ó���ϹǷ� ���� 
	            	   pushNewShape();
	            	   repaint();					
	               }

	               // ���ο� component�� �߰��Ǹ� tmpStack �ʱ�ȭ
            	   tmpStack.clear();	
            	   
				}
	        }

		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent m) {
				x2 = m.getX();
				y2 = m.getY();
				repaint();
			}
		});
			
	}
	
	public void setType(int type) {
		if(type <0 || type > 5) return;
		
		System.out.println("Shanpe Type is " + String.valueOf(type));
		this.type = type;
		
		firstFlag = true;
		
		// ���� Ÿ���� ��� 
		if( type != TYPE_UNDO && type != TYPE_REDO) {
			// ���ο� Component�� ����ϱ� ���� Id ����
			curId++;
		}
	
		// undo, redo�� ��� stack update �� repaint
		if(type== TYPE_UNDO) {
			if(!stack.isEmpty()) {
				ShapeComponent item = stack.pop();
				tmpStack.push(item);
				int undoId = item.getId();
				
				// ���� ���̵�(���� Component) �� ���� ����
				while(!stack.isEmpty() && stack.lastElement().getId() == undoId) {
					tmpStack.push(stack.pop());
				}					
			
				System.out.println("Undo! curStackSize is " + String.valueOf(stack.size()));
				repaint();
			}
		}
		
		else if(type==TYPE_REDO) {
			if(!tmpStack.isEmpty()) {
				ShapeComponent item = tmpStack.pop();
				stack.push(item);
				int redoId = item.getId();
				
				// ���� ���̵�(���� Component) �� ���� ����
				while(!tmpStack.isEmpty() && tmpStack.lastElement().getId() == redoId) {
					stack.push(tmpStack.pop());
				}					
			
				System.out.println("Redo! curStackSize is " + String.valueOf(stack.size()));
				repaint();
			}
		}		
	}
	
	// �巡�װ� ����(�ϼ���) Shape�� stack�� �߰�
	private void pushNewShape() {		
		if(type == TYPE_LINE) {
			ShapeComponent item = new ShapeComponent(curId, x1,y1,x2,y2,TYPE_LINE);
			stack.push(item);
		}
		
		else if(type == TYPE_RECT) {
			ShapeComponent item = new ShapeComponent(curId, x1,y1,x2,y2,TYPE_RECT);
			stack.push(item);

		}
		
		else if(type == TYPE_CIRCLE) {
			ShapeComponent item = new ShapeComponent(curId, x1,y1,x2,y2,TYPE_CIRCLE);
			stack.push(item);
		}

		else if(type == TYPE_POLY) {
			ShapeComponent item = new ShapeComponent(curId, x1,y1,x2,y2,TYPE_POLY);
			stack.push(item);	
		}
	
	}

	// Ư�� ��ǥ�� �����Ͽ� Shape ���� & stack�� �߰�
	private void pushNewShape(int xx1, int yy1, int xx2, int yy2) {		
		if(type == TYPE_LINE) {
			ShapeComponent item = new ShapeComponent(curId, xx1,yy1,xx2,yy2,TYPE_LINE);
			stack.push(item);
		}
		
		else if(type == TYPE_RECT) {
			ShapeComponent item = new ShapeComponent(curId,xx1,yy1,xx2,yy2,TYPE_RECT);
			stack.push(item);

		}
		
		else if(type == TYPE_CIRCLE) {
			ShapeComponent item = new ShapeComponent(curId, xx1,yy1,xx2,yy2,TYPE_CIRCLE);
			stack.push(item);
		}

		else if(type == TYPE_POLY) {
			ShapeComponent item = new ShapeComponent(curId, xx1,yy1,xx2,yy2,TYPE_POLY);
			stack.push(item);	
		}
	
	}

	// stack�� �ִ� Shape Item���� �׷��ִ� method
	private void drawAllItems(Graphics g) {
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setStroke(new BasicStroke(lineThickness));
	
		for(int i=0; i<stack.size(); i++) {
			ShapeComponent item = stack.get(i);
			int width = item.getX2()- item.getX1();
			int height = item.getY2() - item.getY1();
			int itype = item.getType();
			
			if(itype == TYPE_LINE) {				
				 g2.drawLine(item.getX1(),item.getY1(),item.getX2(),item.getY2() );
				
			}
			
			else if(itype == TYPE_RECT) {
				g2.drawRect(item.getX1(),item.getY1(), width, height);
			}
			
			else if(itype == TYPE_CIRCLE) {
				g2.drawOval(item.getX1(),item.getY1(), width, height);
			}

			else if(itype == TYPE_POLY) {
				g2.drawLine(item.getX1(),item.getY1(),item.getX2(), item.getY2());				
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width  = (x2-x1);
		int height = (y2-y1);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(lineThickness));

		if(type == TYPE_LINE) {
			g2.drawLine(x1, y1,x2,y2);
			pushNewShape();	// stack�� ���� 
			
			x1= x2;
			y1= y2;
		}
		
		else if(type == TYPE_RECT) {
			g2.drawRect(x1, y1, width, height);
		}
		
		else if(type == TYPE_CIRCLE) {
			g2.drawOval(x1, y1, width, height);
		}

		else if(type == TYPE_POLY) {
			
			if(xPoints!=null && xPoints.size()>1) {
				if( xPoints.size()>1) {
					int sx = xPoints.get(0);
					int sy = yPoints.get(0);
					int dx = xPoints.get(1);
					int dy = yPoints.get(1);
					g2.drawLine(sx,  sy, dx, dy);
					xPoints.remove(0);
					yPoints.remove(0);	
				
					pushNewShape(sx, sy, dx, dy);
				}
				
			}
		}

		// Stack�� �ִ� ��� Item �׸���
		drawAllItems(g);

	}
	
}
