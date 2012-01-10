package outerSpace;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class OuterSpace extends GraphicsProgram implements KeyListener{

	public static void main(String[] args) {
		
			OuterSpace outerSpace = new OuterSpace();
			outerSpace.start(args);
			
	}	
	public void run() {
		
		//-- Setting the size of the screen --//
		setSize(1000, 600);
		//-----------------------------------//
	
		//-- Adding the Background --//
		add(bg);
		//--------------------------//
		
		//-- Adding the Ship --//
		add(ship);
		//--------------------//
		
		//-- Adding statistics to the screen --//
		add(pointsLbl);
		add(timeLbl);
		add(healthLbl);
		pointsLbl.setColor(Color.WHITE);
		timeLbl.setColor(Color.WHITE);
		healthLbl.setColor(Color.WHITE);
		pointsLbl.sendToFront();
		timeLbl.sendToFront();
		healthLbl.sendToFront();
		//------------------------------------//
		
		//-- First runnable for asteroid --//
		Runnable r1 = new Runnable() {
			
			public void run() {
				//-- Creating and animating the asteroid --//
				double ar = Math.random() * 50;
				double ax = Math.random() * 250;
				double ay = Math.random() * 250;
				Asteroid asteroid = new Asteroid(ar, ax, ay);
				add(asteroid);
				animateAsteroid(asteroid);
				//--------------------------//
			}
		};
		final Thread t1 = new Thread(r1);
		t1.start();
		//-------------------------------//
		
		//-- Second runnable for time update --//
		Runnable r2 = new Runnable() {
			
			public void run() {
				
				updateTime();
				
			}
			
		};
		Thread t2 = new Thread(r2);
		t2.start();
		//-----------------------------------//
		
		//-- Third runnable for points update --//
		Runnable r3 = new Runnable() {
			
			public void run() {
				
				updatePoints();
				
			}
			
		};
		Thread t3 = new Thread(r3);
		t3.start();
		//-----------------------------------//
		
	}
	public void init() {
		
		addKeyListeners();
		
	}

	public void animateAsteroid(Asteroid asteroid) {
		
		while(asteroid.getY() + asteroid.getHeight() < 800) {
			
			if(asteroid.getBounds().intersects(ship.getBounds()) || ship.getBounds().intersects(asteroid.getBounds())) {
				
				asteroid.setLocation(Math.random() * 750, Math.random() * 250);
				time += 1;
				points += 1;
			
			}
			
			asteroid.move(0, 10);
			pause(5);
			
			if(asteroid.getY() + asteroid.getHeight() > 800) {
				
				while(asteroid.getY() + asteroid.getHeight() > 0) {
				
					asteroid.move(0, -10);
					pause(5);
					
					if(asteroid.getBounds().intersects(ship.getBounds()) || ship.getBounds().intersects(asteroid.getBounds())) {
						
						asteroid.setLocation(Math.random() * 750, Math.random() * 250);
						time += 1;
						points += 1;
					
					}
					
					if(asteroid.getY() + asteroid.getHeight() == 0) {
						
						break;
						
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void updateTime() {
		
		while(time > 0) {
			time -= 1;
			timeLbl.setLabel("Time: " + time);
			pause(1000);
		}
		if(time == 0) {
			
			GLabel sOQ = new GLabel("Would you like to start over? Press L To Quit or K to Start Over", 375, 400);
			sOQ.setColor(Color.WHITE);
			sOQ.setFont(new Font("Sans Serif", Font.BOLD, 18));
			add(sOQ);
			
		}
		
	}
	public void updatePoints() {
		
		while(time > 0) {
			
			pointsLbl.setLabel("Points: " + points);
			pause(1);
			
			if(time == 0) {
			
				finalScore.setColor(Color.WHITE);
				finalScore.setLabel("Final score: " + points);
				add(finalScore);
				
			}
			
		}
		
	}
	
	public void keyPressed(final KeyEvent e) {
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP) {
			
			ship.move(0, -10);
			pause(20);
			
		}
		if(key == KeyEvent.VK_DOWN) {
			
			ship.move(0, 10);
			pause(20);
			
		}
		if(key == KeyEvent.VK_LEFT) {
			
			ship.move(-10, 0);
			
		}
		if(key == KeyEvent.VK_RIGHT) {
			
			ship.move(10, 0);
			
		}
		if(key == KeyEvent.VK_L) {
			
			System.exit(0);
			
		}
		if(key == KeyEvent.VK_K) {
			
			time = 10;
			points = 0;
			remove(finalScore);
			
			
		}

	}

	//-- Creating the Ship --//
	GImage ship = new GImage("ship.jpg", 500, 375);
	//----------------------//
		
	//-- Creating the background --//
	GImage bg = new GImage("bg.jpg", 0, 0);
	//----------------------------//
	
	//-- Declaring stats variables --//
	int points = 0;
	int time = 10;
	int health = 100;
	//------------------------------//
	
	//-- Creating an instance of a bullet --//
	GImage bullet = new GImage("bullet.jpg", ship.getX() + 50, ship.getY() + 25);
	//--------------------------------------//
	
	//-- Declaring stats Labels --//
	GLabel pointsLbl = new GLabel("Points: " + points, 0, 15);
	GLabel timeLbl = new GLabel("Time: " + time, 0, 30);
	GLabel healthLbl = new GLabel("Health: " + health, 0, 45);
	GLabel finalScore = new GLabel(" ", 300, 300);
	//---------------------------//
}