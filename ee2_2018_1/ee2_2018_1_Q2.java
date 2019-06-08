// Implemente uma fila bloqueante em Java. N�o usar fun��es da biblioteca de Java.

public class MyBQueue {
	private int[] queue;
	private int size;

	public MyBQueue(int size) {
		this.queue = new int[size];
		this.size = 0;
	}

	public synchronized void push(int value) throws InterruptedException {
		if (this.queue.length == this.size) {
			wait();
		}
		this.queue[this.size++] = value;
		System.out.println("Queue size: " + this.size+  " | Push: " + value + " | Position: " + this.size );
		
		notifyAll();
	}

	public synchronized int pop() throws InterruptedException {
		if (this.size == 0) {
			wait();
		}

		int num = this.queue[0];
		for (int i = 0; i < this.size - 1; i++) {
			this.queue[i] = this.queue[i + 1];
		}
		this.size--;
		System.out.println("Queue size: " + this.size + " | Pop: " + num);
		
		notifyAll();

		return num;
	}

	public synchronized int getSize() {
		return this.size;
	}

}