import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Banker's Algorithm");
		// Input Readers
		FileReader allocation = new FileReader("allocation.txt");
		FileReader maxResources = new FileReader("max.txt");

		int alloc[][] = new int[100][3];// allocated resources table
		int max[][] = new int[100][3];// maximum resources table
		int need[][] = new int[100][3];// needed resources table
		int numberOfProcesses = 0;
		int counter = 0;
		boolean executeComplete[];// true if a process is executed successfully
		boolean infiniteSeq = false;
		int a = 0, b = 0, c = 0;// Number of Resources A, B, C
		int sumAllocA = 0, sumAllocB = 0, sumAllocC = 0;// sum of Allocations of A, B, C
		int availableA = 0, availableB = 0, availableC = 0;// Available Resources of A, B, C
		int prevAvailableA = 0, prevAvailableB = 0, prevAvailableC = 0;

		BufferedReader br = new BufferedReader(allocation);
		String line;

		// Read Allocation File
		System.out.println("Allocation\nA B C");
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			String temp[] = line.split("[ ]", 3);

			alloc[counter][0] = Integer.parseInt(temp[0]);
			alloc[counter][1] = Integer.parseInt(temp[1]);
			alloc[counter][2] = Integer.parseInt(temp[2]);

			++counter;
		}

		numberOfProcesses = counter; // considering input Files Correct
		counter = 0;

		// Read Maximum Resources File
		br = new BufferedReader(maxResources);
		System.out.println("Maximum Resources\nA B C");
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			String temp[] = line.split("[ ]", 3);

			max[counter][0] = Integer.parseInt(temp[0]);
			need[counter][0] = max[counter][0] - alloc[counter][0];// Need Calculation

			max[counter][1] = Integer.parseInt(temp[1]);
			need[counter][1] = max[counter][1] - alloc[counter][1];// Need Calculation

			max[counter][2] = Integer.parseInt(temp[2]);
			need[counter][2] = max[counter][2] - alloc[counter][2];// Need Calculation

			++counter;
		}

		// Checking Number of Process Inputs Equal in both Files Provided
		if (counter == numberOfProcesses) {
			System.out.println(numberOfProcesses + " Processes in System");
		} else {
			System.out.println("Invalid Input");// Should Not Execute
		}

		// Print Need Table
		System.out.println("Needed\nA B C");
		for (int i = 0; i < numberOfProcesses; i++) {
			System.out.println(need[i][0] + " " + need[i][1] + " " + need[i][2]);
		}

		// Input Number of Resources
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Number of Resources for A, B and C");
		a = Integer.parseInt(br.readLine());
		b = Integer.parseInt(br.readLine());
		c = Integer.parseInt(br.readLine());

		executeComplete = new boolean[numberOfProcesses];

		// Bankers Algorithm

		for (int i = 0; i < numberOfProcesses; i++) {
			sumAllocA += alloc[i][0];
			sumAllocB += alloc[i][1];
			sumAllocC += alloc[i][2];
		}
		availableA = a - sumAllocA;
		availableB = b - sumAllocB;
		availableC = c - sumAllocC;

		System.out.println("Available\nA:" + availableA + " B:" + availableB + " C:" + availableC+"\n\n");

		while ((findBoolean(executeComplete, false)) && (infiniteSeq == false)) {
			for (int i = 0; i < numberOfProcesses; i++) {

				if (executeComplete[i] == false) {
					if ((need[i][0] <= availableA) && (need[i][1] <= availableB) && (need[i][2] <= availableC)) {
						System.out.println("Executing Process " + (i + 1));
						executeComplete[i] = true;
						availableA += alloc[i][0];
						availableB += alloc[i][1];
						availableC += alloc[i][2];
						
						System.out.println("Available\nA:" + availableA + " B:" + availableB + " C:" + availableC);
						
					} else {
						//System.out.println("Process " + (i + 1) + " Skipped");
					}
				} else {
					//System.out.println("Process " + (i + 1) + " Already Completed");
				}
				
			} // end of for loop
			if((prevAvailableA == availableA) && (prevAvailableB == availableB) && (prevAvailableC == availableC)) {
				infiniteSeq = true;
				System.out.println("Terminating Loop");
			}
			prevAvailableA = availableA;
			prevAvailableB = availableB;
			prevAvailableC = availableC;
		} // end of while loop
		
		if(infiniteSeq == true) {
			System.out.println("Cannot Continue. Sequence resulted in unsafe state");
			System.exit(-1);
		}
		
		System.out.println("\n\nAll Executions Complete");
		System.out.println("Available\nA:" + availableA + " B:" + availableB + " C:" + availableC);

		if ((availableA == a) && (availableB == b) && (availableC == c)) {
			System.out.println("Available Values Matched Initial Values");
			System.out.println("Given Execution Sequence returns Safe State");
		}
		System.out.println("Exiting Program");
		System.exit(0);
	}// end of main

	// Checks Array haystack for boolean value needle returns true if found
	static boolean findBoolean(boolean haystack[], boolean needle) {
		for (boolean x : haystack) {
			if (x == needle) {
				return true;
			}
		}
		return false;
	}
}
