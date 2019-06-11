// Implemente, em Java, um vetor que seja seguro para uso com threads. Os m�todos 
// get, set e swap devem ser implementados.

public class Vector {
	private int[] vector;

	public Vector(int size) {
		this.vector = new int[size];
	}

	public int get(int i) {
		synchronized (this.vector) {
			return this.vector[i];
		}
	}

	public void set(int i, int value) {
		synchronized (this.vector) {
			this.vector[i] = value;
			// System.out.println("On position " + i + " SET the value: " + value);
		}
	}

	public void swap(int from, int to) {
		synchronized (this.vector) {
			int temp = this.vector[from];
			this.vector[from] = this.vector[to];
			this.vector[to] = temp;
		}
	}

}