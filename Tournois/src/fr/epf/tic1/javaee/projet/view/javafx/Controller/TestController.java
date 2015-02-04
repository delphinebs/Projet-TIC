package fr.epf.tic1.javaee.projet.view.javafx.Controller;

import javafx.scene.control.Label;

public class TestController {

	private Integer countVal = 0;
	public Label count;
	
	public void creerTournois() {
		countVal ++;
		count.setText(countVal.toString());
	}
	
	public void decrement () {
		countVal --;
		count.setText(countVal.toString());
	}
}
