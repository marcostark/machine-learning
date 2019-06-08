package app.model;

public class Perceptron{
	
	private float[] wk;
	
	private float bias;
	
	public static FuncaoAtivacao funcaoAtivacao;
	
	private double yk;
	
	public Perceptron(float[] wk, float bias) {
		this.wk = wk;
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
}
