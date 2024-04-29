/* Name: Olivier Ambroise
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/
package SyncThreads;
import java.util.Random;

public class Withdrawl implements Runnable {
	String Tname;
	private static int maxW =99;
	private static Random numGen = new Random();
	private BAccount sharedLocation;
	public Withdrawl(BAccount shared, String name) {
		// TODO Auto-generated constructor stub
		Tname = name;
		sharedLocation=shared;
	}

	public void widthdraw() throws InterruptedException {
		int amount = numGen.nextInt(maxW)+1;
		sharedLocation.widthdrawl(amount, Tname);
		int sleepTime = numGen.nextInt(10);
		try {
			Thread.sleep(sleepTime*1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				widthdraw();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
