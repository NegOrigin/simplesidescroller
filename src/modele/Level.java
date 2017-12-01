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
	
	// Point de d�part pour l'affichage partiel du niveau (position horizontale actuelle du h�ros)
	protected int startPoint;
	
	// Texte d'introduction du niveau
	protected String intro;
	
	protected Hero hero;
	protected TextArea textArea;
	
	// Prochain niveau et r�compense obtenue pour l'accomplissement du niveau actuel
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
	
	// Retourne la portion du niveau devant �tre affich�e
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
	
	// Triple chaque �l�ment de chaque ligne du niveau
	public void letsTriple() {
		line1 = letsTriple(line1);
		line2 = letsTriple(line2);
		line3 = letsTriple(line3);
		line4 = letsTriple(line4);
		line5 = letsTriple(line5);
		line6 = letsTriple(line6);
		line7 = letsTriple(line7);
	}
	
	// Triple chaque �l�ment d'une ligne du niveau
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
			textArea.setText("Vous �tes tomb� dans un trou !"+System.getProperty("line.separator")+
					"Vous �tes confus, mais"+System.getProperty("line.separator")+
					"une force myst�rieuse vous"+System.getProperty("line.separator")+
					"attrape et vous ram�ne"+System.getProperty("line.separator")+
					"quelques m�tres plus t�t,"+System.getProperty("line.separator")+
					"sur le chemin..."+System.getProperty("line.separator")+System.getProperty("line.separator")+
					"R�essayez encore une fois !"+System.getProperty("line.separator")
					);
		
		// Remise � 0 du point de d�part
		startPoint = 0;

		// D�finition des contr�les
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
		// Apr�s 1/8�me de seconde
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
            	// Rafra�chissement de la partie actuelle du niveau
            	String levelPart = getActualPart();
            	// Ajout � cette partie du h�ros
            	String tmp = placeHero(levelPart);
            			
            	// Si le niveau n'est pas termin�
    			if(tmp.length() >= 224) {
    				// Mise � jour de l'affichage
        			textArea.setText(tmp);
        			
        			// Si le h�ros chute, il recommence le niveau
        			if(levelPart.charAt(5*32) == ' ' && hero.getPosY() == 0) {
        				start(false);
        			// Sinon, lancement de la boucle suivante
        			} else {
        				levelloop();
        			}
        		// Sinon si le niveau est termin� et qu'il y a un niveau suivant, le h�ros re�oit sa r�compense et passe au niveau suivant
    			} else if(nextLevel != null) {
    				hero.addLimb(reward);
    				nextLevel.start(true);
    			// Sinon, il a termin� le jeu et un texte de conclusion s'affiche
    			} else {
    				textArea.setText("Vous reconnaissez la vilaine"+System.getProperty("line.separator")+
    						"sorci�re, mais elle est"+System.getProperty("line.separator")+
    						"� terre..."+System.getProperty("line.separator")+System.getProperty("line.separator")+
    						"Il semblerait que quelqu'un"+System.getProperty("line.separator")+
    						"se soit d�j� charg� d'elle..."+System.getProperty("line.separator")+
    						"Tant pis ! Vous finissez au"+System.getProperty("line.separator")+
    						"moins entier. :)");
    			}
            }
        });
        new Thread(sleeper).start();
	}
	
	// Place le h�ros dans la cha�ne de caract�res repr�sentant la partie du niveau � afficher
	public String placeHero(String levelPart) {
		StringBuilder levelPartWithHero = new StringBuilder(levelPart);
		levelPartWithHero.setCharAt((5-hero.getPosY())*32, hero.toString().charAt(0));
		
		return levelPartWithHero.toString();
	}
	
}
