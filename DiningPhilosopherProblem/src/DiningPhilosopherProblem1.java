import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// The purpose of the first algorithm is to  prevent a situation where two philosophers hold the same chopstick at the same time
public class DiningPhilosopherProblem1 implements Runnable {

	
	private Lock left;
	private Lock right;
	private String name;
    // To randomize eat/Think time
    private Random randomGenerator = new Random();
	
	public static void main(String[] args){
		// Make sure only one philosopher can take this chopsticks at any time.
		Lock[] chopsticks = new ReentrantLock[5];
		
		for (int i = 0; i < 5; i++) {
			chopsticks[i] = new ReentrantLock();
		}
		
		// Create the philosophers 
		DiningPhilosopherProblem1[] philosopher = new DiningPhilosopherProblem1[5];
		
		// start each running in its own thread
		for (int i = 0; i < 5; i++) {
			philosopher[i] = new DiningPhilosopherProblem1(String.valueOf(i), chopsticks[i], chopsticks[(i+1)%5]);
			new Thread(philosopher[i]).start();
		}
		// Terminate the program if user press 'n'
		Scanner sc= new Scanner(System.in);
		char exitCondition = sc.findInLine(".").charAt(0);
		if(exitCondition=='n'){
			System.out.println("Program termination by user");
			sc.close();
			System.exit(0);
		}
		sc.close();
	}
	
     /**
     * **
     *
     * @param name of Philosopher 
     *
     * @param the left chopstick
     * @param the right chopstick
     */
	// DiningPhilosopherProblem1
	public DiningPhilosopherProblem1 (String name, Lock left, Lock right) {
		this.name = name;
		this.left = left;
		this.right = right;
	}
	
	public void run() {
		try {
			while (true) {
				// Think for a bit.
				think();
				System.out.println("Philosopher " + name + ", is now hungry.");
				// Try to get left chopstick
				pickLeftChopStick();
				// Try to get right chopstick
				pickRightChopStick();
				// eat some
				eat();
				// Release both left and right chopsticks
				putDown();
			}
		} catch (InterruptedException e) {
			System.out.println("Philosopher " + name + ", throws an exception.");			
		}
		
	}
	
	private void think() throws InterruptedException {
		System.out.println("Philosopher " + name + " is now thinking.");
		Thread.sleep (randomGenerator.nextInt(1000));
	}
	

	private void pickLeftChopStick() {
		left.lock();
		System.out.println("Philosopher " + name + ", is holding left chopstick.\n");

	}

	private void pickRightChopStick() {
		right.lock();
		System.out.println("Philosopher " + name + ", is holding right chopstick.\n");

	}

	private void eat() throws InterruptedException {
		System.out.println("Philosopher " + name + ", is now eating.");
		Thread.sleep (randomGenerator.nextInt(1000));
	}
	

	private void putDown() {
		left.unlock();
		right.unlock();
	}
	
}

