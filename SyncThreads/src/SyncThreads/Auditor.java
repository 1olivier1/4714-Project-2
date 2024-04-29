/* Name: Olivier Ambroise
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/
package SyncThreads;

import java.util.Random;

public class Auditor implements Runnable{
	private BAccount sharedLocation;
	private static Random numGen = new Random();
	public Auditor(BAccount shared) {
		sharedLocation=shared;
	}
	
	public void audit() throws InterruptedException{
		int sleepTime = numGen.nextInt(30)+5;
		sharedLocation.audit();
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
				audit();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
