**"# RandomNumberMultithread"** 


The Runner class is the main class which will accept a user input as number of generators to create and it will create one processor.

The Number Generator call will share the blocking queue and it will generate the random numbers and push to the blocking queue. Once the blocking queue is full. It will wait until the processor class gdo the computations and clear the queue.

The Number processor will begin the execution once the blocking queue is full and it will compute the MIN, MAX, AVG, Most common number of the 30 number stored in the queue.

The Runner class will keep running until the user forcefully stops the program. 