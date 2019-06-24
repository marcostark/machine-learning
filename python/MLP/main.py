from MLP import *
import load_mnist

EPOCHS = 2
LEARNING_RATE = 3.0
MINI_BATCH = 10


training_data, validation_data, test_data = load_mnist.load_data_wrapper()
training_data = list(training_data)


net = Network([784, 30, 10])
net.SGD(training_data, EPOCHS, MINI_BATCH, LEARNING_RATE, test_data=test_data)