package wipeout_3d;

import java.io.IOException;

import javafx.scene.Group;
//import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
//import javafx.scene.Group;
//import javafx.scene.PointLight;
import javafx.scene.shape.Box;
//import javafx.geometry.Point3D;
//import javafx.animation.RotateTransition;
//import javafx.animation.TranslateTransition;
//import javafx.animation.Animation;
//import javafx.animation.Interpolator;
//import javafx.util.Duration;


public class Player {
	
	double x,y,z;
	double w,l,h;
	
	Box body;
	Group model;
	
	public Player(double x, double y, double z, double w, double l, double h, String modelPath) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.w = w;
		this.l = l;
		this.h = h;

		body = new Box(w,l,h);
		body.setTranslateY(y);
		body.setTranslateX(x);
		body.setTranslateZ(z);
		
		ObjView drvr = new ObjView();
		try {
			drvr.load(ClassLoader.getSystemResource(modelPath).toString());
		} catch(IOException e) {
			System.out.println("Trouble loading model");
			e.printStackTrace();
		}
		
		model = drvr.getRoot();
		model.setScaleX(12);
		model.setScaleY(-12);
		model.setScaleZ(-12);
		model.setTranslateX(x);
		model.setTranslateY(y);
		model.setTranslateZ(z);
	}
	
	public void movePlayer(double dx, double dy, double dz) {
		this.x += dx;
		this.y += dy;
		this.z += dz;
		
		body.setTranslateX(this.x);
		body.setTranslateY(this.y);
		body.setTranslateZ(this.z);
		
		model.setTranslateX(this.x);
		model.setTranslateY(this.y);
		model.setTranslateZ(this.z);
	}
	
	public void resetPlayer() {
		double x = -1*(500/2);
		double y = -25;
		double z = -1;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		body.setTranslateX(x);
		body.setTranslateY(y);
		body.setTranslateZ(z);
		
		model.setTranslateX(this.x);
		model.setTranslateY(this.y);
		model.setTranslateZ(this.z);
	}
	
	public Group getObjectRef() {
		return model;
	}
	
	public double getX() {
		return x;
	}
	
	public double getZ() {
		return z;
	}
	
	public double getWidth() {
		return w;
	}
	
	public void update() {
		if(z > 31) {
			//movePlayer(0,1,0);
		}
	}
	
}
