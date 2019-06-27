package app.model;

import java.security.SecureRandom;

public class Perceptron{
	
	double[] wk;
	
	float bias;
	
	public static FuncaoAtivacao funcaoAtivacao;
	
	double yk;
	
	double gradienteLocal;
	
	public Perceptron(double[] wk, float bias) {
		this.wk = wk;
		SecureRandom sr = new SecureRandom();
		for(int i = 0 ; i < wk.length ; i++)
			wk[i] = sr.nextDouble(); // valores iniciais aleatórios entre 0 e 1 para pesos
		this.bias = bias;
	}

	public double ativacao(double x[]) {
		float vk = combinadorLinar(x);
		this.yk = funcaoAtivacao.executar(vk);
		return yk;
	}
	
	private float combinadorLinar(double x[]) {
		float uk = 0f;
		for(int j = 0 ; j < x.length ; j++)
			uk+= wk[j] * x[j];		
		return uk + bias;
	}
	
	public double energiaErro(double y) {
		return 0.5 * Math.pow(erro(y), 2); 
	}
	
	public double derivada(){
		return yk * ( 1 -  yk );
	}
	
	public double erro(double y) {
		return y - yk;
	}
	
}
