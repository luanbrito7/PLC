class Counter extends Thread {
	int counter;
	long max;
	
	Counter(int min, int max) {
		this.max = max;
		this.counter = min; //De onde começo a contar
	}
	
	synchronized public void increment() {
		if (this.counter < this.max) {
			this.counter ++;
			System.out.println("Adicionei para: " + this.counter);
		}
			
	}
}

/**
 * Não é eficiente porque as threads não podem trabalhar realmente em paralelo
 **/

public class JavaT extends Thread {
	Counter contador;
	int index;
	
	JavaT(Counter contador, int index) {
		this.contador = contador;
		this.index = index;
	}
	
	public void run() {
		while(this.contador.counter < this.contador.max){ //Cada thread vai tentar incrementar até os fins dos tempos
			System.out.println("Thread de Index(" + this.index + ") || contador em (" + this.contador.counter + ") " );
			this.contador.increment();
		}
	}
	
	public static void main(String[] args) {
		Counter c = new Counter(0, 10000);
		JavaT[] threads = new JavaT[10];
		for (int i = 0; i < 10; i++) { //Vamos criar 10 threads para contar
			threads[i] = new JavaT(c, i);
		}
		for (int i = 0; i < 10; i++) {
			threads[i].start();
		}
		try {
			for (int i = 0; i < 10; i++) {
				threads[i].join();
			}
		} catch(InterruptedException ie) {
			System.out.println(ie);
		}
	}
}
