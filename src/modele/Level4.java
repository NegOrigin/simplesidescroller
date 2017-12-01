package modele;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.TextArea;

public class Level4 extends Level {	
	
	public Level4(Hero hero, TextArea textArea) {
		intro = "C'est maintenant votre bras"+System.getProperty("line.separator")+
				"gauche qui réapparaît."+System.getProperty("line.separator")+
				"Vous gagnez en équilibre et"+System.getProperty("line.separator")+
				"pouvez faire un grand saut"+System.getProperty("line.separator")+
				"avec la touche E."+System.getProperty("line.separator")+System.getProperty("line.separator")+System.getProperty("line.separator")+
				"Appuyez sur espace pour"+System.getProperty("line.separator")+
				"commencer.";
		
		this.hero = hero;
		this.textArea = textArea;
		
		nextLevel = new Level5(hero, textArea);
		reward = new Arm();

		String line1Raw = "    _, _ .                _ .                _, _ .                _ .                _, _ .               ";
		String line2Raw = "   ( (  _ )_            (  _ )_             ( (  _ )_            (  _ )_             ( (  _ )_             ";
		String line3Raw = " (_(_  _(_ ,)         (_  _(_ ,)          (_(_  _(_ ,)         (_  _(_ ,)          (_(_  _(_ ,)            ";
		String line4Raw = "                                                                              |\\                           ";
		String line5Raw = "                                                                              |_\\                          ";
		String line6Raw = "___________ _________    ___________    ___________  ______________   ________|____________________________";
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
