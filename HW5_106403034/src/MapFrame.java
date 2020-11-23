import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MapFrame extends JFrame{
	List<String> list = new ArrayList<String>();	//�ɮ�Ū�i�Ӫ�0,1,2
	private BloodPanel bloodJPanel;				//��q�ϰ�
	private JPanel mapJPanel;					//�a�ϰ�
	private static int bloodValue=100;			//��q
	private Icon brick, heart, diamond;			//�Ϥ�
	
	public MapFrame() {	//constructor
		super("�q�y����");
		setLayout(new BorderLayout());
		SecureRandom secureRandom = new SecureRandom();
		
		// ��q��JPanel //
		bloodJPanel = new BloodPanel();
		bloodJPanel.setPreferredSize(new Dimension(550, 50));
		add(bloodJPanel, BorderLayout.NORTH);
		
		// �פJmap.txt�ɡA�o�@��List<>String //
		list = ReadMapFile.readFromFile();
		
		//�a�ϰ�JPanel //
		mapJPanel = new JPanel();
		mapJPanel.setLayout(new GridLayout(10,10));
		add(mapJPanel, BorderLayout.CENTER);
		
		// �Ϥ� //
		brick = changeSize("brickwall.png");
		heart = changeSize("heart.png");
		diamond = changeSize("diamond.png");
		
		//�Ʀr��a��(functional programming)
		list.stream()
			.forEach(element->{
				switch(Integer.parseInt(element)) {
				case 0:
					addRoad();
					break;
				case 1:	//�H���Nbrick�নheart
					//�]�w���G�j���C5�ӡA�|��1�������R��
					if(secureRandom.ints(1, 6).findFirst().getAsInt()==1)
						addHeart();
					else
						addBrick();
					break;
				case 2:
					addDiamond();
					break;
				}
			});
	}
	
	public Icon changeSize(String str){	//���Ϥ��j�p
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(str));
		Image img = imgIcon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(img);
		return icon;
	}
	
	private void addRoad() {
		JLabel roadLabel = new JLabel();
		roadLabel.addMouseListener(new RoadHandler());
		mapJPanel.add(roadLabel);
	}
	
	private void addBrick() {
		JLabel brickLabel = new JLabel(brick);
		brickLabel.addMouseListener(new BrickHandler());
		mapJPanel.add(brickLabel);
	}
	
	private void addHeart() {
		JLabel heartLabel = new JLabel(heart);
		heartLabel.addMouseListener(new HeartHandler());
		mapJPanel.add(heartLabel);
	}
	
	private void addDiamond() {
		JLabel diamondLabel = new JLabel(diamond);
		diamondLabel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent event){
				JOptionPane.showMessageDialog(null, "���\�����p��", "����", JOptionPane.PLAIN_MESSAGE);
				dispose();
			}
		});
		mapJPanel.add(diamondLabel);
	}
	
	private void draw(){		//���e��q��
		bloodJPanel.setValue(bloodValue);
		Graphics g = bloodJPanel.getGraphics();
		g.setColor(Color.BLUE);
		g.drawRect(15, 20, 500, 10);
		g.fillRect(15, 20, bloodValue*5, 10);
		repaint();
	}
	
	private class RoadHandler extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent event){
			bloodValue -= 5;
			draw();
			noBlood();
		}
	}
	
	private class HeartHandler extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent event){
			bloodValue += 5;
			if(bloodValue>100)	//�Y��q�[�W�L100�ҵ���100
				bloodValue = 100;
			draw();
		}
	}
	
	private class BrickHandler extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent event){
			bloodValue -= 20;
			//System.out.printf("brick %d\n",bloodValue);
			draw();
			noBlood();
		}
	}
	
	private void noBlood(){	//�T�{�٦��S����(�O�_�p��0)
		if(bloodValue<0) {
			JOptionPane.showMessageDialog(null, "�S��F�աA�U���A�[�oQQ", "����", JOptionPane.PLAIN_MESSAGE);
			dispose();	//��������
		}
	}
}
