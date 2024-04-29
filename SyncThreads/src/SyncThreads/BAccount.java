/* Name: Olivier Ambroise
 Course: CNT 4714 Spring 2023
 Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: February 12, 2023
*/
package SyncThreads;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BAccount {
	public int balance =0;
	public Lock lock = new ReentrantLock();
	private Condition canWidthdraw = lock.newCondition();
	public boolean occupied = false;
	public int transactionCount = 0;
	public int prevCount = 0;
	public BAccount() {
		System.out.println("Deposit  Agents\t\tWithdrawl  Agents\t\tBalance\t\t\t\t\t\t\t\tTransaction Number\t");
		System.out.println("_______________\t\t_________________\t_______________________\t\t\t\t\t\t\t__________________\t");
	}
	public void deposit(int amount, String name) throws InterruptedException{
		lock.lock();
		String out = name + " deposits $" + amount;
		try {
			balance+=amount;
			out += "\t\t\t\t(+)  Balance is $" + balance + "\t\t\t\t\t\t\t\t" + ++transactionCount;
			System.out.println(out);
			canWidthdraw.signal();
			if(amount > 350) {
				System.out.println("* * * Flagged Transaction - Depositor " + name + " Made A Deposit In Excess Of $350.00 USD - See Flagged Transaction Log.");
				write("Depositor " + name + " issued a deposit of $" + amount + ".00 at: ");
			}
		}
		finally {
			lock.unlock();
		}
		
	}
	public void widthdrawl(int amount, String name) throws InterruptedException{
		lock.lock();
		String out = "\t\t\t" + name + " withdraws $" + amount + "\t";
		try {
			if(balance < amount) {
				out += "(******) WITHDRAWL BLOCKED - INSUFFICIENT FUNDS!!!";
				System.out.println(out);
				canWidthdraw.await();
			}
			else {
				balance-=amount;
				out += "(-)  Balance is $" + balance + "\t\t\t\t\t\t\t\t" + ++transactionCount;
				System.out.println(out);
				if(amount > 75) {
					System.out.println("* * * Flagged Transaction - Widthdrawl " + name + " Made A Withdrawl In Excess Of $75.00 USD - See Flagged Transaction Log.");
					write("\tWithdrawl " + name + " issued withdrawl of $" + amount + " at: ");
				}
			}
			
		}
		finally {
			lock.unlock();
		}
	}
	public void audit() throws InterruptedException{
		lock.lock();
		String out = "\t\tAUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE: $";
		try {
			out += balance + "\t\tNumber of transactions since last audit is: " + (transactionCount-prevCount);
			System.out.println("\n\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
			System.out.println(out);
			System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n\n");
			prevCount = transactionCount;
		}
		finally {
			lock.unlock();
		}
		
	}
	private void write(String s){
		try {
			 FileWriter txt = new FileWriter("transcations.txt", true);
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss z");  
			 Date date = new Date(); 
			 txt.write(s + formatter.format(date) + "\tTransaction Number: " +(transactionCount)+"\n\n");
			 txt.close();
		 }
		 catch(IOException ex){
			 ex.printStackTrace();
		 }
	}
}
