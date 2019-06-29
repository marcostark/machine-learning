import csv
import random
import math
import operator
import pandas as pd

def load_dataset(filename):	
    try:		
        return pd.read_csv(filename)
    except FileNotFoundError as e:
        raise e
 

def load_dataset_to_list(filename, split, trainingSet=[] , testSet=[]):
    trainingSet = []
    testSet = []

	# Transformando dataframe em numpy array e convertendo para listas
    lines = pd.read_csv(filename)
    dataset = lines.to_numpy().tolist()    
    for x in range(len(dataset)-1):
        if random.random() < split:
            trainingSet.append(dataset[x])
        else:
            testSet.append(dataset[x]) 
    
    return (trainingSet, testSet)

def distancia_euclidiana(instancia1, instancia2, length):
    '''
    Calculo da distancia euclidiana que é definida como a raiz quadrada da soma
    das diferenças quadrada entra duas matrizes de números
    return: K vizinhos mais semelhantes do conjunto de treinamento em comparação com uma determinada
    intancia de teste, usando o calculo da função euclidiana para isso.
    '''
    distancia_entre_pontos = 0
    for x in range(length):
        distancia_entre_pontos += pow((instancia1[x] - instancia2[x]), 2)
    return math.sqrt(distancia_entre_pontos)

def obter_vizinhos(trainingSet, testInstance, k):
    # Calculo da similiridade entre duas instancias de dados.     
	distances = []
	length = len(testInstance)-1
	for x in range(len(trainingSet)):
		dist = distancia_euclidiana(testInstance, trainingSet[x], length)
		distances.append((trainingSet[x], dist))
	distances.sort(key=operator.itemgetter(1))
	neighbors = []
	for x in range(k):
		neighbors.append(distances[x][0])
	return neighbors

# Função que obterm a maioria dos votos de um numero de vizinho proximos.
# O ultimo atributo corresponde a classe
def obtem_resposta(vizinhos):
	votos ={}
	for x in range(len(vizinhos)):
		response = vizinhos[x][-1]
		if response in votos:
			votos[response] += 1
		else:
			votos[response] = 1
	ordenaVotos = sorted(votos.items(), key=operator.itemgetter(1), reverse=True)
	return ordenaVotos[0][0]

# CAlcula o total das predições corretas, e retorna 
# a taxa de acerto em porcetagem
def acuracia(testSet, predicoes):
	corretos = 0
	tamanhoTeste = len(testSet)
	for i in range(tamanhoTeste):
		if testSet[i][-1] is predicoes[i]:
			corretos += 1
	return (corretos/float(tamanhoTeste)) * 100.0

train = []
test = []

train, test = load_dataset_to_list('iris-dataset.csv', 0.67, train, test)  
print('Train: ' + repr(len(train)))
print('Test: ' + repr(len(test)))

predicoes = []
k = 3
tamanhoDataset = len(test)

for i in range(tamanhoDataset):
  vizinhos = obter_vizinhos(train, test[i], k)
  result = obtem_resposta(vizinhos)
  predicoes.append(result)
  print('> Predição = {} - Real = {}'.format(result,test[i][-1]))
acuracia = acuracia(test, predicoes)
print('Acurácia: {:.2f} % '.format(acuracia))