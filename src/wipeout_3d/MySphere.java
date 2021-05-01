package wipeout_3d;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class MySphere {
	
	Sphere sphere;
	
	double centerX,centerY,centerZ;
	double radius;
	
	
	MySphere(double x, double y,double z,double radius, Color diffuseColor, Color specularColor) {
		// Set the global variables
		centerX = x;
		centerY = y;
		centerZ = z;
		this.radius = radius;
		
		final PhongMaterial yellowMaterial = new PhongMaterial();
		//yellowMaterial.setDiffuseColor(Color.rgb(200, 200, 0, 0.5));
		 yellowMaterial.setDiffuseColor(diffuseColor);
		 yellowMaterial.setSpecularColor(specularColor);
		 
		sphere = new Sphere(radius);
		sphere.setMaterial(yellowMaterial);

		sphere.setTranslateX(x);
		sphere.setTranslateY(y);
		sphere.setTranslateZ(z);
	}
	
	public void setPathBoundries(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
		
	}
	
	public void update() {
		
	}
	
	public Sphere getSelfRef() {
		return sphere;
	}
}
