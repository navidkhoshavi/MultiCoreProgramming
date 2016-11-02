import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// The purpose of the first algorithm is to  prevent a situation where two philosophers hold the same chopstick at the same time
public class DiningPhilosopherProblem3 implements Runnable {

	
	private Lock left;
	private Lock right;
	private String name;
    // To randomize eat/Think time
    private Random randomGenerator = new Random();
	public static int philosopher0 = 0;
	public static int philosopher1 = 0;
	public static int philosopher2 = 0;
	public static int philosopher3 = 0;
	public static int philosopher4 = 0;

	
	public static void main(String[] args){
		// Make sure only one philosopher can take this chopsticks at any time.
		Lock[] chopsticks = new ReentrantLock[5];
		
		for (int i = 0; i < 5; i++) {
			chopsticks[i] = new ReentrantLock();
		}
		
		// Create the philosophers 
		DiningPhilosopherProblem3[] philosopher = new DiningPhilosopherProblem3[5];
		
		// start each running in its own thread
		for (int i = 0; i < 5; i++) {
			philosopher[i] = new DiningPhilosopherProblem3(String.valueOf(i), chopsticks[i], chopsticks[(i+1)%5]);
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
	public DiningPhilosopherProblem3 (String name, Lock left, Lock right) {
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
				// However, to avoid deadlock, we use top to low order to allow each philosopher to take chopsticks
				if (name.equals("4")){
					pickRightChopStick();
					pickLeftChopStick();				
				}
				else {
					pickLeftChopStick();
					pickRightChopStick();				
				}
				
				// To guarantee no starvation for all philosophers
			    for (long i = System.currentTimeMillis()+10000; i > System.currentTimeMillis(); ) {
			        eat();
			    }
				// Release both left and right chopsticks
				putDown();
				if ("0".equals(name)) {
					DiningPhilosopherProblem3.philosopher0++;
				} else if ("1".equals(name)) {
					DiningPhilosopherProblem3.philosopher1++;
				} else if ("2".equals(name)) {
					DiningPhilosopherProblem3.philosopher2++;
				} else if ("3".equals(name)) {
					DiningPhilosopherProblem3.philosopher3++;
				} else if ("4".equals(name)) {
					DiningPhilosopherProblem3.philosopher4++;
				}

			}
		} catch (InterruptedException e) {
			System.out.println("Philosopher " + name + ", throws an exception.");			
		}
		
	}
	
	private void think() throws InterruptedException {
		System.out.println("Philosopher " + name + " is now thinking.");
		if (!"0".equals(name)&&!"3".equals(name)) {
			Thread.sleep(randomGenerator.nextInt(1000));
		}else {
			Thread.sleep(randomGenerator.nextInt(1000));
		}
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
		if (!"0".equals(name)&&!"3".equals(name)) {
			Thread.sleep(randomGenerator.nextInt(1000));
		}else {
			Thread.sleep(randomGenerator.nextInt(1000));
		}
	}
	
	private void putDown() {
		left.unlock();
		right.unlock();
	}
	
}

