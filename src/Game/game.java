package Game;
/*
 * File name: game.java
 * Made by Michael Curry (worsewicked)
 * Started on: 2nd January 2012
 * Finished on: 7th January 2012
 * Made for Static's Challenge.
 * All images were made using photoshop by Michael Curry (worsewicked)
 * Libraries used are ACM Library and Standard Java Libs.
 * Game is called outerSpace();
 */

import acm.program.*;
import acm.graphics.*;

import java.awt.Color;
import java.awt.event.*;

public class game extends GraphicsProgram implements KeyListener{
	
	public static void main(String[] args) {
		new game().start(args);
	}
	@SuppressWarnings("deprecation")
	public void run() {
		
		setSize(1000, 600);
		add(bg);
		add(ship);
		add(asteroid);
		asteroid.setFillColor(Color.WHITE);
		asteroid.setFilled(true);
		
		Runnable r1 = new Runnable() {
		
			public void run() {
				
				statsUpdate();
			
			}
		
		};
		
		Runnable r2 = new Runnable() {
		
			public void run() {
				
				animateAsteroid();
				
			}
			
		};
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
			
	}
	
	public void statsUpdate() {
		
		add(pointslabel);
		pointslabel.setColor(Color.WHITE);
		GLabel timelabel = new GLabel("Time: " + time, 900, 200);
		add(timelabel);
		timelabel.setColor(Color.WHITE);
		
		while(time > 0) {
			
			time -= 1;
			timelabel.setLabel("Time: " + time);
			pointslabel.setLabel("Points: " + points);
			pause(1000);
			
			if(time == 0) {
				
				GLabel timeUp = new GLabel("Time Up, press L to quit and K to start over.", 100, 100);
				add(timeUp);
				timeUp.setColor(Color.WHITE);
				timeUp.sendToFront();
				
			}
			
		}
		
	}
	public void animateAsteroid() {
		
		while(asteroid.getY() + asteroid.getHeight() < 600) {
			
			if(asteroid.getBounds().intersects(ship.getBounds()) || ship.getBounds().intersects(asteroid.getBounds())) {
				
				asteroid.setLocation(Math.random() * 750, Math.random() * 250);
				asteroid.setSize(Math.random() * 100, Math.random() * 100);
				time += 1;
				points += 1;
				
			}
			
			asteroid.move(0, 10);
			pause(10);
			
			if(asteroid.getY() + asteroid.getHeight() > 600) {
				
				while(asteroid.getY() + asteroid.getHeight() > 0) {
				
					asteroid.move(0, -10);
					pause(10);
					
					if(asteroid.getY() + asteroid.getHeight() == 0) {
						
						break;
						
					}
				}
			
			}
	
		}
			
	}
	public void init() {
		
		addKeyListeners();

	}
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT) {
			
			ship.move(-speed, 0);
			
		}
		
		if(key == KeyEvent.VK_RIGHT) {
			
			ship.move(speed, 0);
			
		}
		if(key == KeyEvent.VK_UP) {
			
			ship.move(0, -speed);
			
		}
		if(key == KeyEvent. VK_DOWN) {
			
			ship.move(0, speed);
			
		}
		if(key == KeyEvent.VK_K) {
			
			time = 10;
			points = 0;
			
		}
		if(key == KeyEvent.VK_L) {
			
			System.exit(0);
			
		}
		if(key == KeyEvent.VK_K) {
			
			run();
			
		}
		if(key == KeyEvent.VK_C) {
			
			asteroid.setLocation(Math.random() * 800, Math.random() * 250);
			asteroid.setSize(Math.random() * 250, Math.random() * 250);
			
		}
		if(key == KeyEvent.VK_I) {
			
			time += 30;
			
		}
		
		
	}

	public int points = 0;
	public int time = 10;
	
	GImage ship = new GImage("ship.jpg", 100, 250);
	GImage bg = new GImage("bg.jpg", 0, 0);
	
	GLabel pointslabel = new GLabel("Points: " + points, 900, 20);
	
	GOval asteroid = new GOval(Math.random() * 1000, Math.random() * 200, Math.random() *  200, Math.random() * 100);
	
	public int speed = 20;
	
	GOval timePlus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
	GOval pointsPlus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
	GOval speedPlus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
	GOval timeMinus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
	GOval pointsMinus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
	GOval speedMinus = new GOval(100, 100, Math.random() * 200, Math.random() * 200);
}