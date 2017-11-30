package application;

import javafx.application.Application;
import javafx.stage.Stage;

import modele.Hero;
import modele.Leg;
import modele.Level;
import modele.Level1;
import modele.Level2;
import modele.Limb;


public class Main extends Application {

	Hero hero = new Hero();
	Limb legLeft = new Leg();
	
	Level level1 = new Level2(hero);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			hero.addLimb(legLeft);
			level1.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
