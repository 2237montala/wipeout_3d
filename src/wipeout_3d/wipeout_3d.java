package wipeout_3d;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.Animation;

import java.util.Random;


/**
 * Basic recreation of a 3D Wipeout game
 * by Jason Moens and Anthony Montalbano
 */


/*
 * TODO: Shouldn't be able to move back or forward if a bumper is out
 * TODO: Win detection
 * TODO: Death detection
 * TODO: Import player model
 * TODO: slightly slow down punches
 * TODO: space out the punchers more
 * 
 */
public class wipeout_3d extends Application {
	private final int FPS = 30;
	
	private PerspectiveCamera camera;
	private Group cameraDolly;
	private final double sceneWidth = 600;
	private final double sceneHeight = 600;
	
	final double groundLength = 500;
	final double groundHeight = 25;
	final double groundWidth = 25+1+15;
	final double playerDx = 5;
		
	final double cameraStartPosX = -400;
	final double cameraStartPosY = -50;
	final double cameraStartPosZ = -200;
	final double cameraStartAngleY = 45;
	final double cameraStartAngleX = 0;
	
	// This camera angle is look at the side of the game
//	final double cameraStartPosX = -250;
//	final double cameraStartPosY = -25;
//	final double cameraStartPosZ = -400;
//	final double cameraStartAngleY = 0;
//	final double cameraStartAngleX = 0;
	
	// This camera angle is a top down of the game
//	final double cameraStartPosX = -250;
//	final double cameraStartPosY = -400;
//	final double cameraStartPosZ = 0;
//	final double cameraStartAngleY = 0;
//	final double cameraStartAngleX = -90;
	
	Box xAxis;
	
	// Create references for the objects in the scene
	// The player is roddyRich cause he's a box
	boolean playerMoveForward = false, playerMoveBackward = false;
	
	boolean gameOver = false, gameWon = false;
	
	Player roddyRich;
	
	Random rand = new Random();
	
	Fist fistArray[] = new Fist[10];
	
	private void constructWorld(Group root) {
		AmbientLight light = new AmbientLight(Color.rgb(100, 100, 100));
		root.getChildren().add(light);
		
		PointLight pl = new PointLight();
		pl.setColor(Color.WHITE);
		pl.setTranslateX(-200);
		pl.setTranslateY(-400);
		pl.setTranslateZ(-50);
		root.getChildren().add(pl);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.FORESTGREEN);
		greenMaterial.setSpecularColor(Color.LIMEGREEN);
		xAxis = new Box(groundLength+50, groundHeight, groundWidth);
		xAxis.setMaterial(greenMaterial);
		root.getChildren().addAll(xAxis);
		
		final PhongMaterial wallMaterial = new PhongMaterial();
		wallMaterial.setDiffuseColor(Color.BLUE);
		wallMaterial.setSpecularColor(Color.LIMEGREEN);
		Box wall = new Box(groundLength,150,25);
		wall.setMaterial(wallMaterial);

		wall.setTranslateY(-62);
		wall.setTranslateZ(35);//25);
		
		root.getChildren().addAll(wall);
		
		final PhongMaterial playerMaterial = new PhongMaterial();
        playerMaterial.setDiffuseColor(Color.YELLOW);
        playerMaterial.setSpecularColor(Color.ORANGE);
        
        // Create a new player at x = -200, y = -25, z = 0
        // with a box size of LxWxH = 25,25,25
        roddyRich = new Player(-1*(groundLength/2),-25,-1,25,25,25,playerMaterial);
		
		root.getChildren().addAll(roddyRich.getObjectRef());
		
		for(int i = 0; i< fistArray.length; i++) {
			//fist created at x, y, z, delay
			fistArray[i] = new Fist(-220+(50*i), -25, 40, rand.nextInt(9)+1);
			fistArray[i].getObjectRef().setRotate(90);
			
			// Rotate on the x axis
			fistArray[i].getObjectRef().setRotationAxis(new Point3D(1,0,0));
			root.getChildren().add(fistArray[i].getObjectRef());
		}

	}
	
	public void setHandlers(Scene scene, Rotate xCameraRotate, Rotate yCameraRotate) {
		// Use keyboard to control camera position
		scene.setOnKeyPressed(event -> {
			// What key did the user press?
			KeyCode keycode = event.getCode();
			if (keycode == KeyCode.A) {
				playerMoveBackward = true;
			}
			if (keycode == KeyCode.D) {
				playerMoveForward = true;
			}
			
			//TODO: REMOVE THESE AFTER TESTING IS DONE
			if (keycode == KeyCode.Q) {
				// Move the player UP on the screen
				roddyRich.movePlayer(0, -1*playerDx, 0);
				
				// Move the camera to follow the player
				cameraDolly.setTranslateY(cameraDolly.getTranslateY() + -1*playerDx);
			}
			if (keycode == KeyCode.W) {
				// Move the player UP on the screen
				roddyRich.movePlayer(0, playerDx, 0);
				
				// Move the camera to follow the player
				cameraDolly.setTranslateY(cameraDolly.getTranslateY() + playerDx);
			}
			if (keycode == KeyCode.E) {
				// Move the player FORWARD on the screen
				roddyRich.movePlayer(0,0,-1*playerDx);
				
				// Move the camera to follow the player
				cameraDolly.setTranslateZ(cameraDolly.getTranslateZ() + -1*playerDx);
			}
			if (keycode == KeyCode.R) {
				// Move the player BACK on the screen
				roddyRich.movePlayer(0,0,playerDx);
				
				// Move the camera to follow the player
				cameraDolly.setTranslateZ(cameraDolly.getTranslateZ() + playerDx);
			}
			
		});
		
		scene.setOnKeyReleased(event -> {
			// What key did the user press?
			KeyCode keycode = event.getCode();
			
			if (keycode == KeyCode.A) {
				playerMoveBackward = false;
			}
			
			if (keycode == KeyCode.D) {
				playerMoveForward = false;
			}
		});
	}

	@Override
	public void start(Stage primaryStage) {

		// Build your Scene and Camera
		Group sceneRoot = new Group();
		constructWorld(sceneRoot);

		// Fourth parameter to indicate 3D world:
		Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight, true);
		scene.setFill(Color.GREY);
		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		scene.setCamera(camera);
		// translations through dolly
		cameraDolly = new Group();
		cameraDolly.setTranslateZ(cameraStartPosZ);
		cameraDolly.setTranslateX(cameraStartPosX);
		cameraDolly.setTranslateY(cameraStartPosY);
		cameraDolly.getChildren().add(camera);
		sceneRoot.getChildren().add(cameraDolly);
		// rotation transforms
		Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		camera.getTransforms().addAll(xRotate, yRotate);

		yRotate.setAngle(cameraStartAngleY);
		xRotate.setAngle(cameraStartAngleX);
		
		setHandlers(scene, xRotate, yRotate);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setTitle("Homework 8 - Wipeout 3D!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void update() {
		// Update the environment
		if(gameWon) {
			//win the game
		}
		else if(gameOver) {
			//lose the game
		}
		else if(!gameOver && !gameWon) {
			//updating the fist array
			for(int i = 0; i < fistArray.length; i++) {
				fistArray[i].update();
			}
			
			//checking each fist if the player is within the xbounds to push it further
			boolean playerTouchFist = false;
			for(int i = 0 ; i < fistArray.length; i++) {
				if(fistArray[i].hitting(roddyRich.getX(), roddyRich.getZ(), roddyRich.getWidth())) {
					// Move the player in the direction of the fist
					playerTouchFist = true;
				}
			}
			
			
			// Player asked to move forward so try to do that action
			if(playerMoveForward) {
				if(!playerTouchFist) {
					// Cant move forward because touching the a fist
					// Move the player right on the screen
					roddyRich.movePlayer(playerDx, 0, 0);
					
					// Move the camera to follow the player
					cameraDolly.setTranslateX(cameraDolly.getTranslateX() + playerDx);
				}
			}
			
			if(playerMoveBackward) {
				// Move the player left on the screen
				if(roddyRich.x >= -250){
					roddyRich.movePlayer(-1 * playerDx, 0, 0);
					
					// Move the camera to follow the player
					cameraDolly.setTranslateX(cameraDolly.getTranslateX() + -1 * playerDx);
				}
			}
			
			// Push the player if we are contacting the fist
			if(playerTouchFist) {
				roddyRich.movePlayer(0, 0, -10);
			}
			
			// If the player has been pushed outside the bounds then end the game
			if(roddyRich.z + roddyRich.getWidth()/2 <= xAxis.getTranslateZ()-groundWidth/2) {
				System.out.println("Game over");
			}
			
			
			roddyRich.valueUpdate();
		}
		
		
	}
}