public class Contador extends Thread {
	int contador;
	
	
	Contador () {
		this.contador = 0;
	}
	
	synchronized public void increment() {
		this.contador += 1;
	}
	
	synchronized public void decrement(){
		this.contador -= 1;
	}
	
	public static void main(String []args) {
		Contador contador = new Contador();
		Thread[] threads = new Thread[4];
		
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				threads[i] = new Produtor(contador);
				System.out.println("Criei um produtor.");
			} else {
				threads[i] = new Consumidor(contador);
				System.out.println("Criei um consumidor.");
			}
		}
		
		for (int i = 0; i < 4; i++) {
			threads[i].start();
		}
		
		for (int i = 0; i < 4; i++) {
			try {
				threads[i].join();
				System.out.println("Thread " + i + " morreu.");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Valor final do contador: " + contador.contador);
	}
	
}

class Produtor extends Thread {
	Contador contador;
	
	Produtor(Contador contador) {
		this.contador = contador;
	}
	
	public void run() {
		for (int i = 0; i < 1000; i++) {
			this.contador.increment();
		}
	}
}

class Consumidor extends Thread {
	Contador contador;
	
	Consumidor(Contador contador) {
		this.contador = contador;
	}
	
	public void run() {
		for (int i = 0; i < 1000; i++) {
			this.contador.decrement();
		}
	}
}