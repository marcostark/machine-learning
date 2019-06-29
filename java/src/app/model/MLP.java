package app.model;

import java.util.ArrayList;
import java.util.List;

public class MLP {

	List<List<Perceptron>> camadas;
	/**
	 * 
	 * Costruindo toda a estrutura da RN
	 * @param bias
	 * @param tamW tamanho de vetor de caracteristicas para primeira camada
	 * @param qtdPerceptronCamada matriz contendo quantidade de camadas e quantidade de perceptrons para cada uma
	 */
	public String inicialiar(float bias, int tamW, int... qtdPerceptronCamada) {
		this.camadas  = new ArrayList<>();
		StringBuffer s = new StringBuffer("\n------------Iniciando MLP com "+ qtdPerceptronCamada.length +" camadas");
		for(int i = 0 ; i < qtdPerceptronCamada.length ; i++) { // para cada camada desejada
			List<Perceptron> camada  = new ArrayList<>();
			for(int j = 0 ; j < qtdPerceptronCamada[i] ; j++) { // para cada Perceptron desejado
				s.append("\n\t"+(j+1)+" - novo perceptron camada "+i);
				camada.add(new Perceptron(new double[tamW], bias)); 
			}
			tamW = qtdPerceptronCamada[i]; // tamanho de vetor de pessos da proxima camada depende do tamanh da quantidade de Nos da camada anterior
			if(i!= qtdPerceptronCamada.length -1)
				s.append("\nTodos os perceptrons da camada foram adicionados"
						+ "\nNovo tamanho vetor pesos para proxíma camada "+tamW);
			camadas.add(camada);
		}
		return s.toString();
	}
	
	/**
	 * Propagar sinal de forma positiva por todo MLP
	 * @param x vetor de caracteristicas
	 */
	public void programacaoPositiva(double... x) {
		for(List<Perceptron> camada : camadas) { // passar por todas as camadas
			double attX[] = new double[camada.size()];
			for(int j = 0 ; j < camada.size() ; j++) { // chamar função de ativação passando vetor de caracteristicas
				Perceptron perceptron = camada.get(j);
				attX[j] = perceptron.ativacao(x);
			}
			x = attX; // atualizando vetor com as saidas da camada anterior (Assim a saída de uma camada é entrada para próxima)
		}
		
		
	}

	/**
	 * Retropropagar o erro por toda rede, a fim de corrigir pesos dos Nó
	 * @param y valor real
	 */
	public void retropropagacaoErro(double y, double n, double constanteMomento) {
		
		// caso 1 : o no está na camada de saída
		List<Perceptron> camadaSaida = camadas.get(camadas.size()-1);
		
		for(Perceptron p : camadaSaida) 
		{
			p.gradienteLocal = p.erro(y) * p.derivada();
			ajustarPesos(p, n, constanteMomento);
			
		}
		// caso 2 : o no está em uma camada oculta;
		if(camadaSaida.size() > 1)
			for(int i = camadas.size()-2 ; i > 0 ; i--) { // inicia na camada anterior a de saída e itera todas em ordem reversa
				// atualizando gradiente local de cada nó da camada em questão
				for(int j = 0 ; j < camadas.get(i).size() ; j++) { // para cada perceptron da camada em questão
					double sumGlp = 0; // soma gradiente local ponderado
					for(Perceptron p : camadas.get(i+1)) // pega a soma ponderada do gradiente local pelo peso
						sumGlp += p.gradienteLocal * p.wk[j]; // j é também o identificador do peso do perceptron.
					Perceptron perceptron = camadas.get(i).get(j);
					perceptron.gradienteLocal = perceptron.derivada() * sumGlp;
					ajustarPesos(perceptron, n, constanteMomento);
				}
			}
	
	}
	
	private void  ajustarPesos(Perceptron p,double n, double constanteMomento) {
		for(int i =0 ; i < p.wk.length ; i++) { // coreção dos pesos da camada de saída
			double variacaoWk =  n * p.gradienteLocal * p.x[i];
			p.wk[i] = p.wk[i] + constanteMomento * p.variacaoAnteriorWk[i] + variacaoWk;
			p.variacaoAnteriorWk[i] = variacaoWk;
		}
	}
	
	public double sumEnergiaErroIteracaoN(double y) 
	{
		List<Perceptron> camadaSaida = camadas.get(camadas.size()-1);
		double energiaMediaErro = 0;
		for(Perceptron p : camadaSaida)
			energiaMediaErro += p.energiaErro(y);
		return energiaMediaErro;
	}
	
	public double saidadaRede() 
	{
		List<Perceptron> camadaSaida = camadas.get(camadas.size()-1);
		double y = 0;
		for(Perceptron p : camadaSaida)
			y += p.yk;
		y /= camadaSaida.size();
		return y;
	}
}




