package wipeout_3d;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.geometry.Point3D;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.util.Duration;

public class Fist {
	
	double x,y,z;
	
	Cylinder body;
	
	public Fist(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.PINK);
        
        body = new Cylinder(10,30);
		body.setMaterial(redMaterial);
		body.setTranslateY(y);
		body.setTranslateX(x);
		body.setTranslateZ(z);
		
		// Set up the sun to move left and right
		TranslateTransition fistMove = new TranslateTransition(Duration.millis(100),body);
		fistMove.setByZ(-30);
		fistMove.setCycleCount(Animation.INDEFINITE);
		fistMove.setAutoReverse(true);
		fistMove.play();
        
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
	
}
