package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import modele.Hero;
import modele.Level;
import modele.Level1;


public class Main extends Application {

	Hero hero;
	Level level1;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Gestion du stage
			primaryStage.setTitle("The Trunk Man");
			primaryStage.setResizable(false);

			// Gestion de la zone de texte qui sert d'affichage
	        TextArea textArea = new TextArea();
	        textArea.setEditable(false);
	        textArea.setFont(Font.font("Monospaced", 30));
	        VBox vbox = new VBox(textArea);

	        // Création et association de la scène
	        Scene scene = new Scene(vbox, 580, 350);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        // Création du héros et du premier niveau
	        hero = new Hero();
	        level1 = new Level1(hero, textArea);
	        
	        // Lancement du premier niveau
			level1.start(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
