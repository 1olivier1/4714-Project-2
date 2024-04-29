/* Name: Olivier Ambroise
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/
package SyncThreads;

import java.util.Random;

public class Depositor implements Runnable{
	private static int maxD =500;
	private static Random numGen = new Random();
	private BAccount sharedLocation;
	
	String Tname;
	public Depositor(BAccount shared, String name) {
		Tname = name;
		sharedLocation=shared;
	}
	public void deposit() throws InterruptedException{
		int amount = numGen.nextInt(maxD)+1;
		sharedLocation.deposit(amount, Tname);
		int sleepTime = numGen.nextInt(100) + 7;
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
				deposit();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
