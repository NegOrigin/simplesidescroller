package modele;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public abstract class Level {

	// Niveau complet
	protected ArrayList<ArrayList<String>> level = new ArrayList<ArrayList<String>>();
	
	// Lignes du niveau
	protected ArrayList<String> line1;
	protected ArrayList<String> line2;
	protected ArrayList<String> line3;
	protected ArrayList<String> line4;
	protected ArrayList<String> line5;
	protected ArrayList<String> line6;
	protected ArrayList<String> line7;
	
	// Point de départ pour l'affichage partiel du niveau (position horizontale actuelle du héros)
	protected int startPoint;
	
	// Texte d'introduction du niveau
	protected String intro;
	
	protected Hero hero;
	protected TextArea textArea;
	
	// Prochain niveau et récompense obtenue pour l'accomplissement du niveau actuel
	protected Level nextLevel;
	protected Limb reward;
	
	public String toString() {
		String result = "";
		
		for(int i = 0; i < level.size(); i++) {
			for(int j = 0; j < line1.size(); j++) {
				result += level.get(i).get(j);
			}
			result += System.getProperty("line.separator");
		}
		
		return result;
	}
	
	// Retourne la portion du niveau devant être affichée
	public String getActualPart() {
		String result = "";
		
		for(int i = 0; i < level.size(); i++) {
			for(int j = startPoint; j < (line1.size() < startPoint+30 ? line1.size() : startPoint+30); j++) {
				result += level.get(i).get(j);
			}
			result += System.getProperty("line.separator");
		}
		startPoint++;
		
		return result;
	}
	
	// Triple chaque élément de chaque ligne du niveau
	public void letsTriple() {
		line1 = letsTriple(line1);
		line2 = letsTriple(line2);
		line3 = letsTriple(line3);
		line4 = letsTriple(line4);
		line5 = letsTriple(line5);
		line6 = letsTriple(line6);
		line7 = letsTriple(line7);
	}
	
	// Triple chaque élément d'une ligne du niveau
	public ArrayList<String> letsTriple(ArrayList<String> line) {
		ArrayList<String> lineTmp = new ArrayList<String>();
		
		for(int i = 0; i < line.size(); i++) {
			lineTmp.add(line.get(i));
			lineTmp.add(line.get(i));
			lineTmp.add(line.get(i));
		}
		
		return lineTmp;
	}
	
	// Retourne le texte d'introduction du niveau
	public String getIntro() {
		return intro;
	}
	
	// Lancement du niveau
	public void start(boolean firstTry) {
		// Affichage de l'intro s'il s'agit du premier essai, d'un autre texte sinon
		if(firstTry)
			textArea.setText(getIntro());
		else
			textArea.setText("Vous êtes tombé dans un trou !"+System.getProperty("line.separator")+
					"Vous êtes confus, mais"+System.getProperty("line.separator")+
					"une force mystérieuse vous"+System.getProperty("line.separator")+
					"attrape et vous ramène"+System.getProperty("line.separator")+
					"quelques mètres plus tôt,"+System.getProperty("line.separator")+
					"sur le chemin..."+System.getProperty("line.separator")+System.getProperty("line.separator")+
					"Réessayez encore une fois !"+System.getProperty("line.separator")
					);
		
		// Remise à 0 du point de départ
		startPoint = 0;

		// Définition des contrôles
        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A: case Q:
                    	if(hero.getLimbNumber() >= 1)
                    		hero.jump(1);
                    	break;
                    case Z: case W:
                    	if(hero.getLimbNumber() >= 2)
                    		hero.jump(2);
                		else if(hero.getLimbNumber() >= 1)
                			hero.jump(1);
                    	break;
                    case E:
                    	if(hero.getLimbNumber() >= 3)
                    		hero.jump(3);
                    	else if(hero.getLimbNumber() >= 2)
                    		hero.jump(2);
                		else if(hero.getLimbNumber() >= 1)
                			hero.jump(1);
                    	break;
                    case R:
                    	if(hero.getLimbNumber() >= 4)
                    		hero.jump(4);
                    	else if(hero.getLimbNumber() >= 3)
                    		hero.jump(3);
                    	else if(hero.getLimbNumber() >= 2)
                    		hero.jump(2);
                		else if(hero.getLimbNumber() >= 1)
                			hero.jump(1);
                    	break;
                    case SPACE:
                    	if(startPoint == 0)
                    		levelloop();
                    	break;
                    default:
                    	break;
                }
            }
        });
	}
	
	// Boucle de jeu du niveau
	public void levelloop() {
		// Après 1/8ème de seconde
		Task<Void> sleeper = new Task<Void>() {
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1000/8);
                } catch (InterruptedException e) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
            	// Rafraîchissement de la partie actuelle du niveau
            	String levelPart = getActualPart();
            	// Ajout à cette partie du héros
            	String tmp = placeHero(levelPart);
            			
            	// Si le niveau n'est pas terminé
    			if(tmp.length() >= 224) {
    				// Mise à jour de l'affichage
        			textArea.setText(tmp);
        			
        			// Si le héros chute, il recommence le niveau
        			if(levelPart.charAt(5*32) == ' ' && hero.getPosY() == 0) {
        				start(false);
        			// Sinon, lancement de la boucle suivante
        			} else {
        				levelloop();
        			}
        		// Sinon si le niveau est terminé et qu'il y a un niveau suivant, le héros reçoit sa récompense et passe au niveau suivant
    			} else if(nextLevel != null) {
    				hero.addLimb(reward);
    				nextLevel.start(true);
    			// Sinon, il a terminé le jeu et un texte de conclusion s'affiche
    			} else {
    				textArea.setText("Vous reconnaissez la vilaine"+System.getProperty("line.separator")+
    						"sorcière, mais elle est"+System.getProperty("line.separator")+
    						"à terre..."+System.getProperty("line.separator")+System.getProperty("line.separator")+
    						"Il semblerait que quelqu'un"+System.getProperty("line.separator")+
    						"se soit déjà chargé d'elle..."+System.getProperty("line.separator")+
    						"Tant pis ! Vous finissez au"+System.getProperty("line.separator")+
    						"moins entier. :)");
    			}
            }
        });
        new Thread(sleeper).start();
	}
	
	// Place le héros dans la chaîne de caractères représentant la partie du niveau à afficher
	public String placeHero(String levelPart) {
		StringBuilder levelPartWithHero = new StringBuilder(levelPart);
		levelPartWithHero.setCharAt((5-hero.getPosY())*32, hero.toString().charAt(0));
		
		return levelPartWithHero.toString();
	}
	
}
