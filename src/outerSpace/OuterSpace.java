package outerSpace;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class OuterSpace extends GraphicsProgram implements KeyListener{

	public static void main(String[] args) {
		
			OuterSpace outerSpace = new OuterSpace();
			outerSpace.start(args);
			
	}	
	@SuppressWarnings("static-access")
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
		
		//-- Fourth runnable for powerUps --//
		Runnable r4 = new Runnable() {
		
			public void run() {
				
				powerUps();
				
			}
		
		};
		Thread t4 = new Thread(r4);
		t4.start();
		//---------------------------------//
		
		//-- Fifth runnable for health --//
		Runnable r5 = new Runnable() {
			
			public void run() {
				
				updateHealth();
				
			}
		
		};
		Thread t5 = new Thread(r5);
		t5.start();
		//---------------------------------//
		
		//-- Sixth runnable for resetStats --//
		Runnable r6 = new Runnable() {
			
			public void run() {
				
				resetStats();
				
			}
		
		};
		Thread t6 = new Thread(r6);
		t6.start();
		//---------------------------------//
		
	}
	public void init() {
		
		addKeyListeners();
		
	}

	public void animateAsteroid(Asteroid asteroid) {

		while(true) {
			asteroid.move(deltaX, deltaY);

			if(asteroid.getBounds().intersects(ship.getBounds())) {

				
				asteroid.setLocation(Math.random() * Math.random() * 600, Math.random() * 400);
				time += 1;
				points += 1;
				health -= 5;

			}
			if(bullet.getBounds().intersects(asteroid.getBounds())) {
				
				asteroid.setLocation(Math.random() * Math.random() * 600, Math.random() * 400);
				points += 2;
				time += 2;
				
			}

			pause(5);

			if(asteroid.getY() < 0)
				deltaY = 10;

			else if(asteroid.getY() + asteroid.getHeight() > getSize().getHeight())
				deltaY = -10;

			if(asteroid.getX() < 0)
				deltaX = 1;

			else if(asteroid.getX() + asteroid.getWidth() > getSize().getWidth())
				deltaX = -1;
			
			if(ship.getHeight() + ship.getY() < 50) {
				
				ship.setLocation(ship.getX(), ship.getY() + 5);
				
			}
			if(ship.getWidth() + ship.getX() < 50) {
				
				ship.setLocation(ship.getX() + 5, ship.getY());
				
			}
			if(ship.getHeight() + ship.getY() > 750) {
				
				ship.setLocation(ship.getX(), ship.getY() - 5);
				
			}
			if(ship.getHeight() + ship.getX() > 950) {
				
				ship.setLocation(ship.getX() - 5, ship.getY());
				
			}
		}
	}
	
	public void updateHealth() {
		
		while(health > -1) {
			if(health <= 0) {
				
				finalScore.setLabel("Final score: " + points);
				finalScore.setColor(Color.WHITE);
				sOQ.setColor(Color.WHITE);
				add(finalScore);
				add(sOQ);
				
			}
		}
		pause(1);
		
		
	}
	
	public void updateTime() {
		
		while(time > 0) {
			time -= 1;
			timeLbl.setLabel("Time: " + time);
			pause(1000);
			healthLbl.setLabel("Health: " + health);
			
			if(time <= 0 || health <= 0) {
				
				sOQ.setColor(Color.WHITE);
				add(sOQ);
				
			}
			
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
	
	
	public void powerUps() {
		
		GOval timePlus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
		GOval pointsPlus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
		GOval speedPlus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
		GOval timeMinus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
		GOval pointsMinus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
		GOval speedMinus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
		while(time > 0) {
			long rand = Math.round(Math.random() * 100);
			pause(1000);
			if(rand == 25) {
				
				timePlus.setFillColor(Color.GREEN);
				timePlus.setFilled(true);
				add(timePlus);
				
			}
			if(rand == 35) {
				
				pointsPlus.setFillColor(Color.GREEN);
				pointsPlus.setFilled(true);
				add(pointsPlus);
				
			}
			if(rand == 45) {
				
				speedPlus.setFillColor(Color.GREEN);
				speedPlus.setFilled(true);
				add(speedPlus);
				
			}
			if(rand == 55) {
				
				timeMinus.setFillColor(Color.RED);
				timeMinus.setFilled(true);
				add(timeMinus);
				
			}
			if(rand == 65) {
				
				pointsMinus.setFillColor(Color.RED);
				pointsMinus.setFilled(true);
				add(pointsMinus);
				
			}
			if(rand == 75) {
				
				speedMinus.setFillColor(Color.RED);
				speedMinus.setFilled(true);
				add(speedMinus);
				
			}
			if(ship.getBounds().intersects(timePlus.getBounds())) {
				
				time += 5;
				remove(timePlus);
				
			}
			if(ship.getBounds().intersects(pointsPlus.getBounds())) {
				
				points += 3;
				remove(pointsPlus);
				
			}
			if(ship.getBounds().intersects(speedPlus.getBounds())) {
				
				speed += 5;
				remove(speedPlus);
				
			}
			if(ship.getBounds().intersects(timeMinus.getBounds())) {
				
				time -= 5;
				remove(timeMinus);
				
			}
			if(ship.getBounds().intersects(pointsMinus.getBounds())) {
				
				points -= 3;
				remove(pointsMinus);
				
			}
			if(ship.getBounds().intersects(speedMinus.getBounds())) {
				
				speed -= 5;
				remove(speedMinus);
				
			}
	
		}
		
	}
	
	public void resetStats() {
		
		remove(sOQ);
		remove(finalScore);
		time = 10;
		points = 0;
		health = 100;
		speed = 10;
		
	}
	
	public int speed = 10;
	public void keyPressed(final KeyEvent e) {
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_K) {
			
			resetStats();
			
		}
		if(key == KeyEvent.VK_UP) {
			
			ship.move(0, -speed);
			ship.setImage("shipUp.jpg");
			ship.setSize(63, 183);
			pause(20);
			
		}
		if(key == KeyEvent.VK_DOWN) {
			
			ship.move(0, speed);
			ship.setImage("shipDown.jpg");
			ship.setSize(64, 184);
			pause(20);
			
		}
		if(key == KeyEvent.VK_LEFT) {
			
			ship.move(-speed, 0);
			ship.setImage("ship.jpg");
			ship.setSize(185, 65);
			
		}
		if(key == KeyEvent.VK_RIGHT) {
			
			ship.move(speed, 0);
			ship.setImage("shipRight.jpg");
			ship.setSize(186, 66);
			
		}
		if(key == KeyEvent.VK_L) {
			
			System.exit(0);
			
		}
		if(key == KeyEvent.VK_C) {
			
			add(Cat);
			
		}

	}

	//-- Creating the Ship --//
	GImage ship = new GImage("ship.jpg", 500, 375);
	//----------------------//
		
	//-- Creating the background --//
	GImage bg = new GImage("bg.jpg", 0, 0);
	//----------------------------//
	
	//-- Declaring stats variables --//
	public int points = 0;
	public int time = 10;
	public int health = 100;
	//------------------------------//
	
	//-- Creating an instance of a bullet --//
	GImage bullet = new GImage("bullet.jpg", ship.getX() + 50, ship.getY() + 25);
	//--------------------------------------//
	
	//-- Asteroid motion --//
	int deltaX = 1;
	int deltaY = 10;
	//---------------------//
	
	//-- Declaring stats Labels --//
	GLabel pointsLbl = new GLabel("Points: " + points, 0, 15);
	GLabel timeLbl = new GLabel("Time: " + time, 0, 30);
	GLabel healthLbl = new GLabel("Health: " + health, 0, 45);
	GLabel finalScore = new GLabel(" ", 300, 300);
	GLabel sOQ = new GLabel("Would you like to start over? Press L To Quit or K to Start Over", 300, 320);
	GLabel Cat = new GLabel("Cat", 300, 400);
	//---------------------------//
}