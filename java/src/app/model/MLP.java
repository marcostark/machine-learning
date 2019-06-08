package app.model;

import java.util.ArrayList;
import java.util.List;

public class MLP {

	List<List<Perceptron>> camadas;
	
	/**
	 * 
	 * Costruindo toda a estrutura da RN
	 * @param bias
	 * @param momentium
	 * @param tamW tamanho de vetor de caracteristicas para primeira camada
	 * @param qtdPerceptronCamada matriz contendo quantidade de camadas e quantidade de perceptrons para cada uma
	 */
	public void inicialiar(float bias, float momentium, int tamW, int... qtdPerceptronCamada) {
		this.camadas  = new ArrayList<>();
		System.out.println("Iniciando MLP com "+ qtdPerceptronCamada.length +" camadas");
		for(int i = 0 ; i < qtdPerceptronCamada.length ; i++) { // para cada camada desejada
			List<Perceptron> camada  = new ArrayList<>();
			for(int j = 0 ; j < qtdPerceptronCamada[i] ; j++) { // para cada Perceptron desejado
				System.out.println("\t"+(j+1)+" - novo perceptron camada "+i);
				camada.add(new Perceptron(new float[tamW], bias)); 
			}
			tamW = qtdPerceptronCamada[i]; // tamanho de vetor de pessos da proxima camada depende do tamanh da quantidade de Nos da camada anterior
			System.out.println("Todos os perceptrons da camada foram adicionados\n.Novo tamanho vetor pesos para proxíma camada "+tamW);
			camadas.add(camada);
		}
	}
	
	/**
	 * Propagar sinal de forma positiva por todo MLP
	 * @param x vetor de caracteristicas
	 */
	public void programacaoPositiva(double x[]) {
		for(List<Perceptron> camada : camadas) { // passar por todas as camadas
			double attX[] = new double[x.length];
			for(int j = 0 ; j < camada.size() ; j++) { // chamar função de ativação passando vetor de caracteristicas
				Perceptron perceptron = camada.get(j);
				attX[j] = perceptron.ativacao(x);
			}
			x = attX; // atualizando vetor com as saidas da camada anterior (Assim a saída de uma camada é entrada para próxima)
		}
	}


	public void retropropagacaoErro() {
		
	}
}


