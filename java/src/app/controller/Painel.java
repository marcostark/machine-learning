package app.controller;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import app.App;
import app.model.Base;
import app.model.FAFactory;
import app.model.MLP;
import app.model.Perceptron;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

public class Painel {

    @FXML
    private TextField dirFld;

    @FXML
    private FlowPane tblPane;

    @FXML
    private TextField aFld;

    @FXML
    private TextField nFld;

    @FXML
    private TextField camadasFld;

    @FXML
    private TextField tvtFld;
    
    @FXML
    private TextField bFld;

    @FXML
    private TextArea logArea;
    
    @FXML
    private TextArea baseLogArea;
    
    private Base base;

    @FXML
    void rodar(ActionEvent event) {
    	if(base == null || (base != null && base.toString().contains("Base não foi devidamente carregada"))) {
    		Alert a = new Alert(AlertType.WARNING);
			a.setContentText("É necessário carregar a base de dados primeiro");
			a.show();
    		return;
    	}
    	try {
    		// pegar dados da tela
    		float b = (!bFld.getText().trim().equals(""))? Float.parseFloat(bFld.getText().trim()): 1f;
    		double a = Double.parseDouble(aFld.getText().trim());
    		double n = Double.parseDouble(nFld.getText().trim());
    		
    		int[] tamCamadasDefault = {3,3,1};
    		int[] tamCamadas = (!camadasFld.getText().trim().equals(""))? passarArrayEmInt(camadasFld.getText().trim().split(",")) : tamCamadasDefault;
    	
    		double taxaTVT = (!tvtFld.getText().trim().equals(""))? Double.parseDouble(tvtFld.getText().trim()) : 80.0;
    	
    		logArea.clear();
    		
    		//configurar perceptron
	    	Perceptron.funcaoAtivacao = FAFactory.sgmoideLogistica(a);
	    	
	    	//configurar base
	    	List<List<double[]>> tvt = base.treinamentoValidacaoTeste(taxaTVT);
	    	List<double[]> valoresTreinamento = tvt.get(0);
	    	List<double[]> valoresTeste = tvt.get(1) ;
	    	
	    	//configurar MLP
	    	MLP mlp = new MLP();
	    	logArea.setText(mlp.inicialiar(b, base.getAtributos().size() -1 , tamCamadas));
	    	
	    	// para cada elemento do treinamento fazer propagação positiva e negativa
	    	logArea.appendText("\n\n------------Iniciando treinamento------------");
	    	for(int i = 0 ; i < valoresTreinamento.size() ; i++) {
	    		
	    		double[] caracteristicas = Arrays.copyOf(valoresTreinamento.get(i),valoresTreinamento.get(i).length-1);
	    		mlp.programacaoPositiva(caracteristicas);// excluindo a classe
	    		logArea.appendText("\nPropagando "+ Arrays.toString(caracteristicas)+" na rede");
	    		
	    		double yDesejado = valoresTreinamento.get(i)[valoresTreinamento.get(i).length-1];
	    		logArea.appendText("\n\tEnergia Média do erro = "+mlp.energiaMediaErro(yDesejado));
	    		mlp.retropropagacaoErro(yDesejado, n);
	    		logArea.appendText("\nRetropropagando "+ yDesejado +" na rede\n");
	    		
	    	}
	    	logArea.appendText("\n------------Fim treinamento------------");
	    	
	    	logArea.appendText("\n\n------------Iniciando teste------------");
	    	//int acerto = 0;
	    	for(double[] valor : valoresTeste) {
	    		double[] caracteristicas = Arrays.copyOf(valor,valor.length-1);
	    		double yDesejado = valor[valor.length-1];
	    		mlp.programacaoPositiva(caracteristicas);
	    		double saidaReal = mlp.saidadaRede();
	    		logArea.appendText("\nPropagando "+ Arrays.toString(caracteristicas)+" na rede");
    			logArea.appendText("\n\tSaída real " + saidaReal + " Saída Desejada "+yDesejado+"\n");
    			
    			/*double taxa = base.getClasses().get(1);
    			double limiteSuperior = taxa;
    			for(double classe :base.getClasses()) {
    				if(yDesejado == classe && saidaReal >= classe && saidaReal < limiteSuperior)
    					acerto++;
					limiteSuperior += taxa;
    			}*/
	    	}
	    	//logArea.appendText("\n\nAccuracy  = "+ acerto / valoresTeste.size()+" %");
	    	
	    	logArea.appendText("\n\n------------Fim teste------------");
	    	
    	}catch (Exception e) {
    		e.printStackTrace();
			Alert a = new Alert(AlertType.WARNING);
			a.setContentText("Parametros de configuração invalidos");
			a.show();
		}
    }
    

    @FXML
    void selecionarBase(ActionEvent event) {
    	 FileChooser fileChooser = new FileChooser();
    	 fileChooser.setTitle("Selecionar base de dados");
    	 File selectedFile = fileChooser.showOpenDialog(App.tela);
    	 dirFld.setText(selectedFile.toString());
    	 base = new Base();
    	 base.carregar(selectedFile);
    	 baseLogArea.setText(base.toString());
    }
    
    public int[] passarArrayEmInt(String[] strings) throws Exception {
    	int[] ints = new int[strings.length];
		for(int i = 0 ; i < strings.length ; i ++)
			ints[i] = Integer.parseInt(strings[i].trim());
		return ints;
    }

}
