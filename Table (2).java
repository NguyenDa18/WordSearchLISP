import java.util.Random;
import java.io.*;
import java.net.URL;

/**
 Ryan Dehart, Teresa Condon, Danh Nguyen
 CS 352
 Program 1
 Word Search Puzzle Generator/Solver
/*

/** Resources Used */
// dictionary used created by infoChimps: http://www.infochimps.com/datasets/word-list-350000-simple-english-words-excel-readable
// .txt format found at https://github.com/dwyl/english-words
// help on how to read .txt files: https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html

/**
Program generates a word search puzzle with random search words inserted from a dictionary txt file that the user has to find. The user can choose to print the puzzle solution. Rsserved Java words are used.
*/

public class Table {
	//variable
	int myInt = 1;
	String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //file path for dictionary txt file containing hidden words
	String dictionaryFileName = "./src/dictionary/WSdictionary.txt";
	String wordLine = "";

    //board size
	int numRows, numCols = 16;

	char blankChar = ' ';

	char[][] grid = new char[numRows][numCols];
	String [] hiddenwrds = new String[(numRows + numCols)/4];
    int [][] hiddenwrdPos = new int[(numRows + numCols)/4][2];
    int [] hiddenwrdDir = new int[(numRows + numCols)/4];

	final int LEFT = 0; //the word direction right to left
	final int RIGHT = 1;
	final int UP = 2;
	final int DOWN = 3;



	public void printTable() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++){
				System.out.print(" " + grid[j][i]);
			}
			if (i == 0){
				System.out.println("    Words to Search:");
			}
			else if (i > 1 && i < hiddenwrds.length + 2){
				System.out.println("    " + hiddenwrds[i - 2]);
			}
			else {
				System.out.println();
			}
		}
	}

	public Table()
	{
        //Random value generator to place words in table
		Random rand = new Random();
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++){
				grid[i][j] = ' ';
			}
		}
		setHiddenWords();

        //places words on table, preferring horizontal placement.
        //Sometimes, there is a mix of vertical and horizontal placement.
        //multiple words can share a letter if it is the same letter (words can cross-over)
		for (int j = 0; j < hiddenwrds.length; j++) {
			int[] firstLetterPos = new int[2];
			int numTries = 0;
			boolean rightOpen = false;
			boolean leftOpen = false;
			boolean upOpen = false;
			boolean downOpen = false;

      //search for open space
			while (rightOpen == false && leftOpen == false &&
					upOpen == false && downOpen == false && numTries < 500)
			{
				numTries += 1;
				while (numRows != 0 && numCols != 0) {
				firstLetterPos[0] = rand.nextInt(numRows);
				firstLetterPos[1] = rand.nextInt(numCols);
				rightOpen = (numCols - firstLetterPos[1] >= hiddenwrds[j].length());
				leftOpen = (firstLetterPos[1] >= hiddenwrds[j].length());
				upOpen = (firstLetterPos[0] >= hiddenwrds[j].length());
				downOpen = (numRows - firstLetterPos[0] >= hiddenwrds[j].length());

        //check if word can go right, left, up, or down
				for (int i = 0; i < hiddenwrds[j].length(); i++){
					if (rightOpen){
						if (grid[firstLetterPos[1] + i][firstLetterPos[0]] != hiddenwrds[j].charAt(i)
							&& grid[firstLetterPos[1] + i][firstLetterPos[0]] != blankChar){
							rightOpen = false;
						}
					}
					if (leftOpen){
						if (grid[firstLetterPos[1] - i][firstLetterPos[0]] != hiddenwrds[j].charAt(i)
							&& grid[firstLetterPos[1] - i][firstLetterPos[0]] != blankChar){
							leftOpen = false;
						}
					}
					if (upOpen){
						if (grid[firstLetterPos[1]][firstLetterPos[0] - i] != hiddenwrds[j].charAt(i)
							&& grid[firstLetterPos[1]][firstLetterPos[0] - i] != blankChar){
							upOpen = false;
						}
					}
					if (downOpen){
						if (grid[firstLetterPos[1]][firstLetterPos[0] + i] != hiddenwrds[j].charAt(i)
							&& grid[firstLetterPos[1]][firstLetterPos[0] + i] != blankChar){
							downOpen = false;
						}
					}
				}
			}
			}

      //set word's direction based on what is available
			if (rightOpen && leftOpen){
				if (rand.nextInt(1) == 0){
					placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], LEFT);
                    hiddenwrdDir[j] = LEFT;
					//go left
				}
				else {
                    placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], RIGHT);
                    hiddenwrdDir[j] = RIGHT;
					//go right
				}
			}
			else if (upOpen && downOpen){
				if (rand.nextInt(1) == 0){
                    placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], UP);
                    hiddenwrdDir[j] = UP;
					//go up
				}
				else {
                    placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], DOWN);
                    hiddenwrdDir[j] = DOWN;
					//go down
				}
			}
			else if (rightOpen){
                placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], RIGHT);
                hiddenwrdDir[j] = RIGHT;
				//go right
			}
			else if (leftOpen){
                placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], LEFT);
                hiddenwrdDir[j] = LEFT;
				//go left
			}
			else if (upOpen){
                placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], UP);
                hiddenwrdDir[j] = UP;
                //go up
			}
			else if (downOpen){
                placeWord(hiddenwrds[j], firstLetterPos[1], firstLetterPos[0], DOWN);
                hiddenwrdDir[j] = DOWN;
                //go down
			}

            //record positions for solution
            hiddenwrdPos[j][0] = firstLetterPos[0];
            hiddenwrdPos[j][1] = firstLetterPos[1];
		}

		this.writePuzzle("solution.txt");

    //fill the rest of the table with random letters
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++){
				if (grid[i][j] == blankChar){
					grid[i][j] = alphabet.charAt(rand.nextInt(26));
				}
			}
		}

		this.writePuzzle("puzzle.txt");
	}

	public void placeWord(String word, int startPosX, int startPosY, int direction) {
		grid[startPosX][startPosY] = word.charAt(0);
		for(int i = 1; i < word.length(); i++) {
			if (direction == LEFT){
				grid[startPosX - i][startPosY] = word.charAt(i);
			}
			else if (direction == RIGHT){
				grid[startPosX + i][startPosY] = word.charAt(i);
			}
			else if (direction == UP){
				grid[startPosX][startPosY - i] = word.charAt(i);
			}
			else if (direction == DOWN){
				grid[startPosX][startPosY + i] = word.charAt(i);
			}
		}
	}

	public void setHiddenWords(){
		for (int i = 0; i < hiddenwrds.length; i++){
			try {
				while(hiddenwrds[i] == null || hiddenwrds[i].length() < 3 || hiddenwrds[i].length() > Math.min(numRows, numCols)){
					FileReader reader = new FileReader(dictionaryFileName);

					BufferedReader buff = new BufferedReader(reader);

					Random rand = new Random();
					int lineNum = rand.nextInt(235886);
					for (int j = 0; j < lineNum - 1; j++){
						buff.readLine();
					}
					hiddenwrds[i] = buff.readLine().toLowerCase();

					buff.close();
				}
			}
			catch(FileNotFoundException fe) {
				System.out.println("Error opening file.");
			}
			catch(IOException ioe){
				System.out.println("System error.");
			}
		}
	}

	// reads the random words to search from dictionary file and prints them to the side of the puzzle
	public void writePuzzle(String filePath){
		try {
      //import filewriter to write to a txt file
			FileWriter puzzleWriter = new FileWriter(new File(filePath));

			for (int i = 0; i < numRows; i++){
				for (int j = 0; j < numCols; j++){
					puzzleWriter.write(" " + grid[j][i]);
				}
				if (i == 0){
					puzzleWriter.write("  Words to Search:");
				}
				else if (i > 1 && i < hiddenwrds.length + 2){
					puzzleWriter.write("    " + hiddenwrds[i - 2]);
				}
				puzzleWriter.write(System.lineSeparator());
			}

            //print solution's additional information
            if(filePath.equals("solution.txt")){
                puzzleWriter.write(System.lineSeparator());
                for (int i = 0; i < hiddenwrds.length; i++){
                    puzzleWriter.write(hiddenwrds[i] + ": Row: " + hiddenwrdPos[i][0] + " Column: " + hiddenwrdPos[i][1] + " Direction: " + hiddenwrdDir[i]);
                    puzzleWriter.write(System.lineSeparator());
                }
            }

			puzzleWriter.close();
		}
		catch(IOException e){
			System.out.println("Error writing file");
		}
	}

    // prints version of solved puzzle in a separate txt file
	public void printSolution(){
		try {
			FileReader fr = new FileReader("solution.txt");
			BufferedReader buff = new BufferedReader(fr);

			String row = null;
			while((row = buff.readLine()) != null){
				System.out.println(row);
			}

			fr.close();
		}
		catch(FileNotFoundException fe) {
			System.out.println("Error opening file.");
		}
		catch(IOException ioe){
			System.out.println("System error.");
		}
	}

	public static void main(String [] args) {
		Table myTable = new Table();
		myTable.printTable();
		myTable.printSolution();
	}
}
