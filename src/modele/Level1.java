package modele;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.TextArea;

public class Level1 extends Level {	
	
	public Level1(Hero hero, TextArea textArea) {
		intro = "Oh non !"+System.getProperty("line.separator")+
				"Une sorci�re a fait"+System.getProperty("line.separator")+
				"dispara�tre vos bras et"+System.getProperty("line.separator")+
				"vos jambes !"+System.getProperty("line.separator")+
				"Vite ! Rattrapons la !"+System.getProperty("line.separator")+System.getProperty("line.separator")+System.getProperty("line.separator")+
				"Appuyez sur espace pour"+System.getProperty("line.separator")+
				"commencer.";
		
		this.hero = hero;
		this.textArea = textArea;
		
		nextLevel = new Level2(hero, textArea);
		reward = new Leg();

		String line1Raw = "    _, _ .                _ .                _, _ .                _ .                _, _ .               ";
		String line2Raw = "   ( (  _ )_            (  _ )_             ( (  _ )_            (  _ )_             ( (  _ )_             ";
		String line3Raw = " (_(_  _(_ ,)         (_  _(_ ,)          (_(_  _(_ ,)         (_  _(_ ,)          (_(_  _(_ ,)            ";
		String line4Raw = "                                                                              |\\                           ";
		String line5Raw = "                                                                              |_\\                          ";
		String line6Raw = "______________________________________________________________________________|____________________________";
		String line7Raw = "                                                                                                           ";
		
		line1 = new ArrayList<String>(Arrays.asList(line1Raw.split("")));
		line2 = new ArrayList<String>(Arrays.asList(line2Raw.split("")));
		line3 = new ArrayList<String>(Arrays.asList(line3Raw.split("")));
		line4 = new ArrayList<String>(Arrays.asList(line4Raw.split("")));
		line5 = new ArrayList<String>(Arrays.asList(line5Raw.split("")));
		line6 = new ArrayList<String>(Arrays.asList(line6Raw.split("")));
		line7 = new ArrayList<String>(Arrays.asList(line7Raw.split("")));
		
		level.add(line1);
		level.add(line2);
		level.add(line3);
		level.add(line4);
		level.add(line5);
		level.add(line6);
		level.add(line7);
	}

}
