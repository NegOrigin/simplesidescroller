package modele;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public abstract class Level {
	
	Scanner sc = new Scanner(System.in);

	protected ArrayList<ArrayList<String>> level = new ArrayList<ArrayList<String>>();
	
	protected ArrayList<String> line1;
	protected ArrayList<String> line2;
	protected ArrayList<String> line3;
	protected ArrayList<String> line4;
	protected ArrayList<String> line5;
	protected ArrayList<String> line6;
	protected ArrayList<String> line7;
	
	protected int startPoint;
	
	protected String intro;
	
	protected Hero hero;
	
	protected Level nextLevel;
	
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
	
	public void letsTriple() {
		line1 = letsTriple(line1);
		line2 = letsTriple(line2);
		line3 = letsTriple(line3);
		line4 = letsTriple(line4);
		line5 = letsTriple(line5);
		line6 = letsTriple(line6);
		line7 = letsTriple(line7);
	}
	
	public ArrayList<String> letsTriple(ArrayList<String> line) {
		ArrayList<String> lineTmp = new ArrayList<String>();
		
		for(int i = 0; i < line.size(); i++) {
			lineTmp.add(line.get(i));
			lineTmp.add(line.get(i));
			lineTmp.add(line.get(i));
		}
		
		return lineTmp;
	}
	
	public String getIntro() {
		return intro;
	}
	
	public void start() {
		clearScreen();
		System.out.println(getIntro());
		System.out.println(this);
		sc.nextLine();
		
		startPoint = 0;
		levelloop();
	}
	
	public void rip() {
		clearScreen();
		System.out.println("Vous êtes tombé, mais vous pouvez réessayer");
		System.out.println(this);
		sc.nextLine();
		
		startPoint = 0;
		levelloop();
	}
	
	public void levelloop() {
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
            	String levelPart = getActualPart();
            	String tmp = placeHero(levelPart);
            			
    			if(tmp.length() >= 224) {
                	clearScreen();
        			System.out.println(tmp);
        			
        			System.out.println(levelPart.charAt(5*32));
        			if(levelPart.charAt(5*32) == ' ' && hero.getPosY() == 0) {
        				rip();
        			} else {
        				levelloop();
        			}
    			} else if(nextLevel != null) {
    				nextLevel.start();
    			} else {
    				clearScreen();
    				System.out.println("Félicitation !"+System.getProperty("line.separator")+"Vous avez terminé le jeu.");
    			}
            }
        });
        new Thread(sleeper).start();
	}
	
	public String placeHero(String levelPart) {
		StringBuilder levelPartWithHero = new StringBuilder(levelPart);
		levelPartWithHero.setCharAt((5-hero.getPosY())*32, hero.toString().charAt(0));
		
		return levelPartWithHero.toString();
	}
	
	public static void clearScreen() {
	    for(int i = 0; i < 100; i++) {
	        System.out.println();
	    }
	}
	
}
