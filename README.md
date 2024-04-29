# 4714-Project-2
develop an application which requires cooperating, synchronized multiple threads of execution.



Description: In this programming assignment you will simulate the deposits and
withdrawals made to a fictitious bank account (I’ll let you use my real bank account if
you promise to make only deposits! J). In this case the deposits and withdrawals will
be made by user agents (synchronized threads). Synchronization is required for two
reasons – (1) mutual exclusion (updates cannot be lost) and (2) because a withdrawal
cannot occur if the amount of the withdrawal request is greater than the current balance
in the account. This means that access to the account (the shared object) must be
synchronized. This application requires cooperation and communication amongst the
various agents (cooperating synchronized threads). (In other words, this problem is
similar to the producer/consumer problem where there is more than one producer and
more than one consumer process active simultaneously.) If a withdrawal agent
attempts to withdraw an amount greater than the current balance in the account – then
it must block itself and wait until a depositing agent has added money to the account
before it can try again. As we covered in the lecture notes, this will require that the
depositing agents signal all waiting withdrawing agents whenever a deposit is
completed.
1. You should have five depositor agents (threads) and ten withdrawal agents
(threads), and one auditor agent (thread) simultaneously executing. Use a
FixedThreadPool() and an Executor object to control the threads.
2. To keep things relatively simple, as well as to see immediate results from a series
of transactions (deposits and withdrawals), assume that deposits are made in
amounts ranging from $1 to $500 (whole dollars only) and withdrawals are made
in amounts ranging from $1 to $99 (again, whole dollars only). Since we have
more withdrawal threads than depositor threads, the account balance should
constantly decrease over time. This will lead to withdrawal agents repeatedly
blocking for insufficient funds. Start the simulation with a balance of $0.
CNT 4714 – Project – Spring 2023
Page 2
3. Once a depositor agent (thread) has deposited into the account, put it to sleep for
few milliseconds (randomly generate this number – don’t use a constant sleep
time) or so (depends a little bit on the speed of your system as to how long you
will want to sleep the depositing threads - basically we want to ensure a lot more
withdrawals than deposits) to allow other agents to execute. This is the only
situation in which a deposit agent will block.
4. For withdrawal agents, things will be a bit different depending on whether you
are working on a single or multi-core processor.
a. For single core processors, once a withdrawal agent has withdrawn funds
from the account, have it yield the processor unit. Since the agent is giving
up the processor voluntarily, it will be unlikely to run again (attempt a
second withdrawal in a row), before another agent runs. Note however,
that it does not prevent it from running again, if all other withdrawal
agents are blocked and all depositors are sleeping, it will run again. So
occasional back-to-back runs of withdrawal agents might occur (see
below).
b. For multi-core processors, once a withdrawal agent has executed, have it
sleep for some random period of time (again, a few milliseconds should
be fine). Depending on which core a thread is executing, yielding the CPU
won’t ensure that the same thread will not run again immediately. While,
sleeping the thread will also not ensure that it will not run two or more
times in succession, it is less likely to do so in the multi-core environment.
c. What we don’t want to happen is a single withdrawal agent gaining the
CPU and then executing a long sequence of withdrawal operations. Recall
though, that withdrawal agents block if they attempt to withdraw more
than the current balance in the account.
d. Similarly, we don’t want depositor agents monopolizing the CPU either
and causing the balance in the account to grow continuously. This would
most likely occur when the withdrawal agents are sleeping too long in
comparison to the average sleep time of the depositing agents. See page
9 for an illustration of this.
5. Assume all depositor and withdrawal agents have the same priority. Do not give
different priority to depositor and withdrawal agents (threads). The auditor agent
will also have normal priority and will simply run less frequently than the
depositor and withdrawal threads (i.e., the auditor thread will sleep longer
between runs than either depositors or withdrawals). See below.
6. The output from your program must look reasonably similar to the sample output
shown below. The simulation output should show the action of each agent along

with the account balance produced by the agent’s transaction and the transaction
number.
8. Do not put the threads into a counted loop for your simulation. In other
words, the run() method for all threads should be an infinite loop. Just stop
the simulation from your IDE after a few seconds.
9. Do not use the Java synchronized statement. I want you to handle the locking
and signaling yourself. No monitors!
10. You must utilize a reentrant lock from the java.util.concurrent.locks package for
implementing your locking protocols. We will specify no fairness policy for this
application. Do not create your own lock using a Boolean or any other type
of variable.
11. The Money Laundering Suppression Act, enacted by Congress in 1994, is a
policy regulation that requires banking institutions to file currency transaction
reports (CTRs) with the federal government (Department of the Treasury) for
any deposits of $10,000 or more into a bank account. You are going to simulate
this process by flagging any depositing transaction with a deposit value greater
than $350.00 and any withdrawal amount greater than $75.00. You will flag the
transaction in the normal output of the simulation as well as making an entry into
a transaction log file which will keep track of all flagged transactions
independently of the simulation. Each entry in the flagged transaction file will
contain the transaction details, a timestamp, and the transaction number. See
below for more details.
11.Every transaction made by a depositor or withdrawal agent will have a
transaction number (an integer initialized to 1). This transaction number is
printed out in the simulation run with each completed transaction. See output
examples below.
12.The auditor agent (there is only 1 of these), simply verifies the current balance
in the account at random intervals. The auditor agent does not make transactions
on the account and does not affect the transaction number sequence. The auditor
agent simply print the current account balance into the simulation run and keeps
track of the number of transactions that have executed since the last auditor
execution. Note that the auditor should run much less frequently than either
depositor or withdrawal agents.
