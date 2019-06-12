import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



/*
 * O forno de uma padaria tem capacidade para assar 50 p ̃aes simultaneamente. 3. A`
medida que p ̃aes ficam prontos, s ̃ao retirados do forno. O abastecimento, que acontece
apenas ap ́os o forno ser completamente esvaziado,  ́e feito de maneira que 10 p ̃aes s ̃ao
colocados no forno por vez, at ́e a capacidade do forno. Assuma que o primeiro lote
de p ̃aes colocados no forno  ́e tamb ́em o primeiro a ser retirado. Utilizando uma fila
bloqueante, implemente, em Java, o comportamento dessa padaria. Considere que
retirar os paes do forno  ́e um processo mais lento que o abastecimento dele, devido
ao tempo necessario para assar os p ̃aes.
*/

//OBS: tá por alguma razão tá imprimindo a remoção de pães antes de imprimir que o forno tá cheio (50/50)
// mas a lógica tá certa, me baseei na resposta do prof

public class EE2_Q3_2018_1 {
	public static void main(String[] args) {
		BlockingQueue<Paes> forno = new LinkedBlockingQueue<Paes>();
		Consumidor cons = new Consumidor(forno);
		Produtor prod = new Produtor(forno);
		
		cons.start();
		prod.start();
	}
	
	public static class Produtor extends Thread{
		public BlockingQueue<Paes> forno;
		
		public Produtor (BlockingQueue<Paes> forno) {
			this.forno = forno;
		}
		
		public void run() {
			 while (true) {
                 while (true)
                     synchronized (this.forno) {
                         if (this.forno.isEmpty())
                             break;

                         try {
                             this.forno.wait();
                         } catch (InterruptedException ignored) {
                         }
                     }
                 System.out.println("Forno vazio");

                 for (int i = 0; i < 5; ++i)
                     try {
                         Thread.sleep(500);
                         this.forno.put(new Paes());
                         synchronized (this.forno) {
                             this.forno.notify();
                         }

                         System.out.printf("%d / 50 pães no forno%n", (i + 1) * 10);
                     } catch (InterruptedException ignored) {
                     }
             }		}
	}
	public static class Consumidor extends Thread{
		public BlockingQueue<Paes> forno;
		
		public Consumidor(BlockingQueue<Paes> forno) {
			this.forno = forno;
		}
		
		public void run() {
			while (true) {
                while (true)
                    synchronized (this.forno) {
                        if (!this.forno.isEmpty())
                            break;

                        try {
                            this.forno.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }

                try {
                    Thread.sleep(2000);
                    this.forno.take();
                    synchronized (this.forno) {
                        this.forno.notify();
                    }

                    System.out.println("10 pães prontos");
                } catch (InterruptedException ignored) {
                }
            }
		}
	}
	public static class Paes{
		
	}
	}

