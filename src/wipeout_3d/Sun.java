package wipeout_3d;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.shape.Cylinder;
import javafx.geometry.Point3D;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.util.Duration;

public class Sun extends Group {
	
	MySphere body;
	
	private final int numRays = 8;
	Cylinder[] rayArr;
	
	// Variables to space the rays from the body
	private final double rayBodyDistX = 30;
	private final double rayBodyDistY = 30;
	private final double rayBodyDistZ = 0;
	
	public Sun(double x, double y, double z, double radius) {
		super();
		
		// Create center of sun
		body = new MySphere(x,y,z,radius,Color.YELLOW, Color.WHITE);
		getChildren().addAll(body.getSelfRef());
		
		// Create the rays of the sun
		// Set up color
		final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.YELLOW);
        redMaterial.setSpecularColor(Color.WHITE);
        
        rayArr = new Cylinder[numRays];
        double slice = 2*Math.PI/numRays;
		for(int i = 0; i < numRays; i++) {
			Cylinder ray = new Cylinder(5,50);
			
			// Move the ray to its location centered in the body
			//ray.setTranslateX(x); // Forward back
			//ray.setTranslateY(y); // Up down
			ray.setTranslateZ(z); // Left right
			
			ray.setTranslateX(x + (radius*2+rayBodyDistZ)*Math.sin(i*slice));
	        ray.setTranslateY(y + (radius*2+rayBodyDistZ)*Math.cos(i*slice));
			
			// Rotate the ray so it looks correct
	        ray.setRotate(-i*slice*180/Math.PI);
	        ray.setRotationAxis(new Point3D(0,0,1));
			
			ray.setMaterial(redMaterial);
			getChildren().addAll(ray);
			
			rayArr[i] = ray;
		}
		
		// Make all the rays rotate in a circle
		RotateTransition rotator = new RotateTransition(Duration.millis(30000), this);
		rotator.setByAngle(360);
		rotator.setAxis(new Point3D(0,0,1));
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(Animation.INDEFINITE);
		rotator.setAutoReverse(false);
		rotator.play();	
		
		// Since the sun emits light it needs a point light
		// The point light only illuminates the inside of the sun
		// and only things on the same plane as it
		// To fix this you need to set the illumination map
		// but you need an image for that
		PointLight pl = new PointLight();
		pl.setColor(Color.WHITE);
		pl.setTranslateX(x);
		pl.setTranslateY(y);
		pl.setTranslateZ(z);
		getChildren().add(pl);
		
		
		// Set up the sun to move left and right
		TranslateTransition sunMove = new TranslateTransition(Duration.millis(5000),this);
		sunMove.setByX(400);
		sunMove.setCycleCount(Animation.INDEFINITE);
		sunMove.setAutoReverse(true);
		sunMove.play();
	}
}
