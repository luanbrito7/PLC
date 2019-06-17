public class Conta extends Thread {
	double saldo;
	
	
	Conta (double saldo) {
		this.saldo = saldo;
	}
	
	synchronized public void deposit(double valor){
		this.saldo += valor;
		System.out.println("Colocando o valor: " + valor + " ficando com: " + this.saldo);
		notify();
	}
	
	synchronized public void retirar(int valor){
		while (this.saldo < valor) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.saldo -= valor;
		System.out.println("Retirando o valor: " + valor + " ficando com: " + this.saldo);
	}
	
	public static void main(String []args) {
		Conta conta = new Conta(0);
		Thread[] threads = new Thread[4];
		
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				threads[i] = new Produtor(conta);
				System.out.println("Criei um produtor.");
			} else {
				threads[i] = new Consumidor(conta);
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
	}
	
}

class Produtor extends Thread {
	Conta conta;
	
	Produtor(Conta conta) {
		this.conta = conta;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			this.conta.deposit(50);
		}
	}
}

class Consumidor extends Thread {
	Conta conta;
	
	Consumidor(Conta conta) {
		this.conta = conta;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			this.conta.retirar(50);
		}
	}
}