package wipeout_3d;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.shape.Box;
import javafx.geometry.Point3D;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.util.Duration;


public class Player {
	
	double x,y,z;
	
	Box body;
	
	public Player(double x, double y, double z, double w, double l, double h, PhongMaterial color) {
		this.x = x;
		this.y = y;
		this.z = z;

		body = new Box(w,l,h);
		body.setMaterial(color);
		body.setTranslateY(y);
		body.setTranslateX(x);
		body.setTranslateZ(z);
	}
	
	public void movePlayer(double dx, double dy, double dz) {
		this.x += dx;
		this.y += dy;
		this.z += dz;
		
		body.setTranslateX(this.x);
		body.setTranslateY(this.y);
		body.setTranslateZ(this.z);
	}
	
	public Box getObjectRef() {
		return body;
	}
	
}
