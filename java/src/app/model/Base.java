package app.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Base {

	private List<String[]> atributos;
	
	private List<String[]> valores;
	
	
	public float[] carregar(File dir) {
		float[] base = new float[3];
		
		try(InputStreamReader isr = new InputStreamReader(new FileInputStream(dir))){
			try(Scanner arq = new Scanner(isr)){
				atributos = new ArrayList<>();
				// lendo arquivo linha a linha e adicionando a lista vetor com nome do atributo e tipo
				while(arq.hasNextLine()) {
					String linhaArq = arq.nextLine().toLowerCase().trim();
					if(linhaArq.contains("@attribute")) {
						String[] campos = linhaArq.replace("@attribute","").trim().split(" +");
						atributos.add(campos);
					}else if(linhaArq.contains("@data"))
						break;
				}
				valores = new ArrayList<String[]>();
				// Continuando a ler arquivo linha a linha a fim de armazenar valores em lista
				while(arq.hasNextLine()) {
					String linhaArq = arq.nextLine().toLowerCase().trim();
					if(!linhaArq.equals("")) {
						valores.add(linhaArq.split(","));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base;
	}
	
	@Override
	public String toString() {
		if(atributos != null && !atributos.isEmpty() && valores != null  && !valores.isEmpty()) {
			StringBuffer sb = new StringBuffer("\n\natributos\n");
			for(String[] a :atributos) {
				sb.append(Arrays.toString(a)+"\n");
			}
			sb.append("\n\nvalores\n");
			for(String[] a :valores) {
				sb.append(Arrays.toString(a)+"\n");
			}
			return sb.toString();
		}else {
			return "Base não foi devidamente carregada";
		}
	}
}
