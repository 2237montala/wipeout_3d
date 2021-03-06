package wipeout_3d;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
//import javafx.scene.Group;
//import javafx.scene.PointLight;
//import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
//import javafx.geometry.Point3D;
//import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
//import javafx.animation.Animation;
//import javafx.animation.Interpolator;
import javafx.util.Duration;

public class Fist {
	
	double x,y,z;
	int delay;
	final int RADIUS = 10;
	final int LENGTH = 50;
	
	Cylinder body;
	
	boolean inAnimation = false;
	
	TranslateTransition punch;
	
	long startTime, endTime;
	
	final int ANIMATIONTIME = 250;
	
	public Fist(double x, double y, double z, int delay) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.delay = delay;
		
		final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.PINK);
        
        body = new Cylinder(RADIUS,LENGTH);
		body.setMaterial(redMaterial);
		body.setTranslateY(y);
		body.setTranslateX(x);
		body.setTranslateZ(z);
		
		// Set up the sun to move left and right
		this.punch = new TranslateTransition(Duration.millis(ANIMATIONTIME),body);
		punch.setByZ(-40);
		punch.setCycleCount(2);
		punch.setAutoReverse(true);
        
	}
	
	public void movePlayer(double dx, double dy, double dz) {
		this.x += dx;
		this.y += dy;
		this.z += dz;
		
		body.setTranslateX(this.x);
		body.setTranslateY(this.y);
		body.setTranslateZ(this.z);
	}
	
	public Cylinder getObjectRef() {
		return body;
	}
	
	public void update() {
		// Run the animation loop
		if(inAnimation == false) {
			if(System.currentTimeMillis()%(delay) == 0) {
				punch.play();
				startTime = System.currentTimeMillis();
				inAnimation = true;
			}
		}
		else {
			endTime = System.currentTimeMillis();
			if(endTime - startTime > 2*(2*ANIMATIONTIME)+100*delay) {
				inAnimation = false;
			}
		}
	}
	
	public boolean hitting(double playerX, double playerZ, double playerWidth) {
		playerWidth /= 2;
		//System.out.println("Player X: " + (playerZ+playerWidth)+ ", " + (playerZ-playerWidth));
		//System.out.println("Fist Z: " + body.getTranslateZ());

		// Check if the player is being touched by the front face of the cylinder
		if(playerZ-playerWidth > body.getTranslateZ()-LENGTH) {
			if(playerX+playerWidth > body.getTranslateX()-RADIUS && playerX-playerWidth <= body.getTranslateX()+RADIUS) {
					return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
