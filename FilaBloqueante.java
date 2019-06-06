import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

class Fila extends Thread {
	ArrayList<Integer> fila;
	
	Fila () {
		this.fila = new ArrayList<Integer>(0);
	}
	
	synchronized public int rmv() {
		int value = -1;
		while (this.fila.isEmpty()) {
			try {
				System.out.println("Não posso remover de uma fila vazia, durma.");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		value = this.fila.get(0);
		ArrayList<Integer> updated = new ArrayList<Integer>(0);
		for(int c = 1; c < this.fila.size(); c++) {
			updated.add(this.fila.get(c));
		}
		this.fila = updated; // Atualizo sem o primeiro elemento
		return value;
	}
	
	synchronized public void add(int value) {
		System.out.println("Adicionando o elemento: " + value + " a fila.");
		this.fila.add(value);
		notify();
	}
}

public class FilaBloqueante extends Thread {
	Fila filaBloqueante;
	boolean producer; // Caso seja false é um consumidor
	
	FilaBloqueante(Fila fila ,boolean status) {
		this.filaBloqueante = fila;
		this.producer = status;
	}
	
	public void run() {
		for(int i = 0; i < 10; i++) {
			if (this.producer) {
				this.filaBloqueante.add(i);
			} else { 	
				System.out.println("Removi o elemento: " + this.filaBloqueante.rmv() + " da fila");
			}			
		}
	}
	
	public static void main(String[] args) {
		Fila fila = new Fila();
		FilaBloqueante consumidor = new FilaBloqueante(fila, false);
		FilaBloqueante produtor = new FilaBloqueante(fila, true);
		consumidor.start();
		produtor.start();
		try {
			produtor.join();
			consumidor.join();
		} catch(InterruptedException ie) {
			System.out.println(ie);
		}
	}
}
