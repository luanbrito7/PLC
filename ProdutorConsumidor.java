import java.util.concurrent.locks.ReentrantLock;

class Drop {
	String msg;
	Boolean empty;
	ReentrantLock lock;
	/**
	 * lock: atributo responsável por obter o
	 * monitor do objeto
	 **/
	Drop(ReentrantLock lock) {
		this.empty = true;
		this.lock = lock;
		this.msg = "";
	}
	
	void put(String msg) {
		boolean monitor = this.lock.tryLock();  //monitor = consegui a tranca?
		try {
			while (!(this.empty && monitor)) { //não entro se Drop está vazia e tenho a trava
				if (monitor) {
					this.lock.unlock();					
				}
				monitor = this.lock.tryLock();
				// Tento conseguir a trava novamente e vejo se a Drop já está vazia
			}
			System.out.println("Colocando a palavra: " + msg);
			this.msg = msg;
			this.empty = false;			
			
		} finally {
			if (monitor) {
				this.lock.unlock(); //Podem usar a trava
			}
		}
	}
	
	String rmv() {
		boolean monitor = this.lock.tryLock();
		try {
			while (!(monitor && !this.empty)) { //Dessa vez preciso que já tenha algo no Drop
				if (monitor) {
					this.lock.unlock();
				}					
				monitor = this.lock.tryLock();
			}
			this.empty = true;
			String l = this.msg;
			this.msg = "";
			System.out.println("Dropando a palavra: " + l);
			return l;
		} finally {
			if (monitor) {
				this.lock.unlock();
			}
		}
	}
}

class Cons extends Thread {
	Drop c;
	String action;
	
	Cons(Drop c, String action) {
		this.c = c;
		this.action = action; //Minha Drop vai ser produtor ou consumidor
	/**
	 * Uso essa variável para usar a mesma classe para Produtor ou Consumidor
	 * Essa provavelmente não é a melhor forma de fazer isso, mas pra nossos fins tá suave
	 **/
	}
	
	public void run() { //Tenta colocar ou remover 25 palavras da Drop
		for(int i = 0; i < 25; i++) {
			if (this.action == "put") {
				this.c.put(i + "");				
			} else {
				String l = this.c.rmv();
			}
		}
	}
}

public class JavaT extends Thread {
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Drop drop = new Drop(lock);
		Cons tr1 = new Cons(drop, "put"); //Produtor
		Cons tr2 = new Cons(drop, "rmv"); //Consumidor
		tr1.start();
		tr2.start();
		try {
			//Aguardo apenas todos serem consumidos
			//sei que terminam primeiro.
			tr2.join(); 
		} catch(InterruptedException ie) {
			System.out.println(ie);
		}
	}
}
