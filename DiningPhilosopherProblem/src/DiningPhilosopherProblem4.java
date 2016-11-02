import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// The purpose of the first algorithm is to  prevent a situation where two philosophers hold the same chopstick at the same time
public class DiningPhilosopherProblem4 implements Runnable {

	
	private Lock left;
	private Lock right;
	private String name;
    // To randomize eat/Think time
    private Random randomGenerator = new Random();
	public static Integer NUMBER = 5;

	
	public static void main(String[] args){
		// get from the user the number of Philosophers
		if(args.length > 0){
			try{
				NUMBER = Integer.valueOf(args[0]);
				
			
			} catch( NumberFormatException e){
				e.printStackTrace();
				System.out.println("Please insert the number of Philosophers:");
				System.exit(0);
			}
			
		}		
		// Make sure only one philosopher can take this chopsticks at any time.
		Lock[] chopsticks = new ReentrantLock[NUMBER];
		
		for (int i = 0; i < NUMBER; i++) {
			chopsticks[i] = new ReentrantLock();
		}
		
		// Create the philosophers 
		DiningPhilosopherProblem4[] philosopher = new DiningPhilosopherProblem4[NUMBER];
		
		// start each running in its own thread
		for (int i = 0; i < NUMBER; i++) {
			philosopher[i] = new DiningPhilosopherProblem4(String.valueOf(i), chopsticks[i], chopsticks[(i+1)%NUMBER]);
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
	public DiningPhilosopherProblem4 (String name, Lock left, Lock right) {
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
				if (name.equals(Integer.valueOf(NUMBER)-1)){
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

			}
		} catch (InterruptedException e) {
			System.out.println("Philosopher " + name + ", throws an exception.");			
		}
		
	}
	
	private void think() throws InterruptedException {
		System.out.println("Philosopher " + name + " is now thinking.");
			Thread.sleep(randomGenerator.nextInt(1000));
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
			Thread.sleep(randomGenerator.nextInt(1000));
			Thread.sleep(randomGenerator.nextInt(1000));
	}
	
	private void putDown() {
		left.unlock();
		right.unlock();
	}
	
}

