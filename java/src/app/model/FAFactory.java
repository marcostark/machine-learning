package app.model;

public class FAFactory {
	
	
	public static FuncaoAtivacao sgmoideLogistica(double a) {
		return (v)->{
			return 1/(1+ Math.exp(-(a*v)));
		};
	}
	
	public static FuncaoAtivacao sgmoideTangenteHiperbolica() {
		// Não implementado
		return (v)->{
			return 0.0;
		};
	}
	
}
