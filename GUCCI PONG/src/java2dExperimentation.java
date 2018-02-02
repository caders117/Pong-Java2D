import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class java2dExperimentation extends JPanel{
	
	static int paddle1x = 10, paddle1y = 1;
	static int paddle2x = 10, paddle2y = 1;
	static int ballX, ballY, ballSpeed, ballYInt;
	static float ballSlope;
	static int mouseX, mouseY, frameMouseX, frameMouseY;
	static Timer timer;
	static int paddleH = 100, paddleW;
	static boolean running;
	static int score, compScore;
	static int fontSize = 25;
	static JFrame frame = new JFrame();
	static int timerWait = 0;
	
	static java2dExperimentation exp = new java2dExperimentation();


	
	public static void main(String[] args){
		exp.initExp();
		frame.add(exp);
		frame.addMouseListener(new MouseAdapter(){
			public void MouseEntered(){
				running = true;
			}
			public void MouseExited(){
				running = false;
			}
		});
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500, 1500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
		
		ballSlope = -0.95f;
		ballX = exp.getSize().width / 2;
		ballY = exp.getSize().height / 2;
		System.out.println(ballY);
		ballSpeed = exp.getSize().width / 250;
		ballYInt = 0;
	}
	
	private void reset(){
		ballSlope = -0.95f;
		ballX = exp.getSize().width / 2;
		ballY = exp.getSize().height / 2;
		System.out.println(ballY);
		ballSpeed = exp.getSize().width / 250;
		ballYInt = 0;
	}
	
	private void initExp(){
		setBackground(Color.RED);
		setPreferredSize(new Dimension(500, 500));
		
		addMouseMotionListener(new mouseListen());
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 100, 25);
	}
	
	public class mouseListen implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseX = e.getX();
			mouseY = e.getY();
		}
		
	}
	
	private class ScheduleTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			/*System.out.println("Mouse X: " + mouseX);
			System.out.println("Mouse Y: " + mouseY);*/
			if(timerWait == 0){
				
				System.out.println("Mouse X: " + (mouseX = MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x - 15));
				System.out.println("Mouse Y: " + (mouseY = MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 65));
	
				
				
				if(exp.getSize().height - ballY < 20 || ballY < 10){
				
					bounce(false);
					
				}
				
				if ((ballX + 20 > paddle2x && ballX + 20 < paddle2x + 10 && paddle2y - paddleH / 2 < ballY && ballY < paddle2y + paddleH / 2) || 
					(Math.abs(ballX - paddle1x - paddleW + 10) < 10 && ballX > paddle1x + paddleW -10 && paddle1y - paddleH / 2 < ballY && ballY < paddle1y + paddleH / 2)){
						bounce(true);
				} else if(Math.abs(ballX - exp.getSize().width) < 10){
					
					score += 1;
					reset();
					timerWait = 1;
					
				} else if (ballX < 10){
					
					compScore += 1;
					reset();
					timerWait = 1;
					
				}
				
				ballX += ballSpeed;
				ballY = (int) (Math.abs(ballSlope * ballX + ballYInt));
				
				System.out.println(ballX);
				System.out.println(ballY);
				System.out.println(ballSlope);
				
				
			} else {
				timerWait++;
				if (timerWait == 40){
					timerWait = 0;
				}
			}
			paddle1y += (mouseY - paddle1y) / 10;
			paddle2y += (paddle1y - paddle2y) / 10;
			repaint();
		}
		
	}
	
	public void bounce(boolean x){
		Random rand = new Random();
		float randomSlope = (float)(rand.nextInt(4) - 2) / 10;
		System.out.println(randomSlope);
		ballSlope *= -1;
		if(x){
			///ballYInt += (int) (ballX * (ballSlope * 2));
			ballSlope += randomSlope;

			ballSpeed *= -1;
			ballYInt = (int)(ballY - (ballSlope * ballX));
			
		} else {
			/*ballYInt += (int) (ballX * (ballSlope * 2));
			if(ballY > 50){
				ballYInt += 1;
			}*/
			//ballSlope *= -1;
			ballYInt = (int)(ballY - (ballSlope * ballX));

		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("default", Font.BOLD, fontSize));
		g2d.drawString("Your Score: " + score, 5, 30);
		g2d.drawString("Computer's Score: " + compScore, exp.getWidth() - 250, 30);

		g2d.fillRoundRect(paddle1x = exp.getSize().width / 50, paddle1y - (paddleH / 2), paddleW = exp.getSize().width / 25, paddleH = exp.getSize().height / 5, 10, 10);
		g2d.fillRoundRect(paddle2x = exp.getSize().width - 30 - paddleW - exp.getSize().width / 50, paddle2y - (paddleH / 2), paddleW, paddleH = exp.getSize().height / 5, 10, 10);
		g2d.fillOval(ballX, ballY, exp.getSize().width / 50, exp.getSize().height / 50);

		
		repaint();
	}
	
	
}