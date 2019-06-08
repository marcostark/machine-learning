package app;

import app.model.FAFactory;
import app.model.MLP;
import app.model.Perceptron;

public class app {

	public static void main(String[] args) {
		Perceptron.funcaoAtivacao = FAFactory.sgmoideLogistica(0.2);
		MLP mlp = new MLP();
		mlp.inicialiar(0.7f ,0.2f, 4, 8,4,2);
	}
	
}
