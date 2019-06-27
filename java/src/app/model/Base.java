package app.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Base {

	private List<String[]> atributos;
	
	private List<double[]> valores;
	
	public boolean carregar(File dir) {
		try(InputStreamReader isr = new InputStreamReader(new FileInputStream(dir))){
			try(Scanner arq = new Scanner(isr)){
				atributos = new ArrayList<>();
				// lendo arquivo linha a linha e adicionando a lista vetor com nome do atributo e tipo
				while(arq.hasNextLine()) {
					String linhaArq = arq.nextLine().toLowerCase().trim();
					if(linhaArq.contains("@attribute")) {
						String[] campos = linhaArq.replace("@attribute","").trim().split(" +");
						atributos.add(campos);
					}else if(linhaArq.equals("@data"))
						break;
				}
				
				valores = new ArrayList<double[]>();
				// Continuando a ler arquivo linha a linha a fim de armazenar valores em lista
				while(arq.hasNextLine()) {
					String linhaArq = arq.nextLine().trim();
					if(!linhaArq.equals("")) {
						valores.add(passarArrayEmDouble(linhaArq.split(",")));
					
					}
				}
				if(!atributos.isEmpty() || !valores.isEmpty())
					return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public List<List<double[]>> treinamentoValidacaoTeste(double taxaTreinamento){
		// embaralhando cópia
		List<double[]> valoresEmbaralhados = new ArrayList<>();
		for(double[] regristro : valores)
			valoresEmbaralhados.add(regristro);
		Collections.shuffle(valoresEmbaralhados);
		
		// dividindo em partes
		List<List<double[]>> tvt = new ArrayList<>();
		int tamTreinamento = (int) (valores.size() * taxaTreinamento / 100);
		tvt.add(valoresEmbaralhados.subList(0, tamTreinamento));
		tvt.add(valoresEmbaralhados.subList(tamTreinamento+1, valoresEmbaralhados.size()-1));
		return tvt;
	}
	
	
	public List<String[]> getAtributos() {
		return atributos;
	}

	public List<double[]> getValores() {
		return valores;
	}
	
	private double[] passarArrayEmDouble(String[] strings) {
		double[] doubles = new double[strings.length];
		for(int i = 0 ; i < strings.length ; i ++) {
			try {
				doubles[i] = Double.parseDouble(strings[i].trim());
			}catch (Exception e) {
				doubles[i] = Double.parseDouble("0."+Math.abs(strings[i].trim().hashCode()));
			}
		}
		return doubles;
	}
	
	@Override
	public String toString() {
		if(atributos != null && !atributos.isEmpty() && valores != null  && !valores.isEmpty()) {
			StringBuffer sb = new StringBuffer("\n\natributos\n");
			for(String[] a :atributos) {
				sb.append(Arrays.toString(a)+"\n");
			}
			sb.append("\n\nvalores\n");
			for(double[] a :valores) {
				sb.append(Arrays.toString(a)+"\n");
			}
			return sb.toString();
		}else {
			return "Base não foi devidamente carregada";
		}
	}
}
