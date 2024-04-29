/* Name: Olivier Ambroise
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/
package SyncThreads;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sync {
	public static final int M_agents = 16;
	
	
	public static void main (String[]args) {
		ExecutorService application = Executors.newFixedThreadPool(M_agents);
		BAccount sharedLocation = new BAccount();
		try {
			
			application.execute(new Withdrawl(sharedLocation,"Agent WT0"));
			application.execute(new Depositor(sharedLocation,"Agent DT0"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT1"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT2"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT3"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT4"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT5"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT6"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT7"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT8"));
			application.execute(new Withdrawl(sharedLocation,"Agent WT9"));
			application.execute(new Depositor(sharedLocation,"Agent DT1"));
			application.execute(new Depositor(sharedLocation,"Agent DT2"));
			application.execute(new Depositor(sharedLocation,"Agent DT3"));
			application.execute(new Depositor(sharedLocation,"Agent DT4"));
			application.execute(new Auditor(sharedLocation));
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		application.shutdown();
		
	}
}
