import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class EncodeTrainFile
{
	public static File trainFile;
	public static File wordsFile;
	public static PrintWriter printWriter;
	static int finalWordIndex; // index of word in words file
	static Scanner scanner;
	static String inStr; // used for parsing from scanner
	static String[] tokens; // used for parsing words from inStr
	static String[] words; // the unique word
	//static int[] coocFreqArr; // the values for how many times a word cooccurs with another word (1st is target word, 2nd is cooccurring/context word, both from words[])
	static int contextLength = 50;
	//static int freqWords; // number times that word observed
	//static int freqCooc; // nuumber times that word cooccurred
	static int numWordsTotal = 1159219;
	static int numWordsUnique;
	static int[] allOccWordsIndexes;
	static int[] allOccTrainIndexes;
	static int maxNumAllOcc = (numWordsUnique * 200);
	static int occCounter;
	static int[] trainInts;

	public static int[] returnEncodedTrainInts(String train, String[] wordsStr)
	{
		trainFile = new File(train);
		words = wordsStr;
		numWordsUnique = words.length;
		encodeTrainFile();
		System.out.println(""+occCounter);
		trimAllOccWordsIndexes(); // also fills out allOccTrainIndexes
		System.out.println("allOccWordsIndexes trimmed");
		printEncodedTrainFile(); // currently just gets trainInts, doesn't print to file
		System.out.println("encodedTrainFile printed");
		return trainInts;

		// ENCODE WORDS AND TOKEN INDEXES AS INTS, a linked list
		// word_index num_of_unused_words next_word_index

		// FIND OCCURRENCES FIRST, THEN CONNECT AND FIND/INCREMENT COOCCURRENCES
		// INDEX OF OCCURRENCE AND NUM WORDS TO NEXT OCCURRENCE OF ANY WORD
		// CONTEXT FROM FRONT TO BACK INSTEAD OF WORD IN CENTER (requires an occurrence array]

		// fix the names on the variables to be more clear
	}

	public static void encodeTrainFile()
	{
		Scanner wordsScanner;
		allOccWordsIndexes = new int[numWordsTotal];
		try
		{
			scanner = new Scanner(trainFile);
			int trainCounter = 0;
			int wordsCounter = 0;
			occCounter = 0;
			boolean wordFound = false;
			int binSearchCounter = 0;
			int compVal;
			int lineCounter = 0;
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				tokens = inStr.split("([.,!?:;'\"-]|\\s)+");
				for (int i=0; i<tokens.length; i++)
				{
					compVal = java.util.Arrays.binarySearch(words, tokens[i]);
					if (compVal > 0) // change to >= 0 and fix bugs where zeroth word is not considered
					{
						allOccWordsIndexes[trainCounter] = compVal;
						occCounter += 1;
					}
					trainCounter += 1;
				}
				if (lineCounter%100 == 0)
					System.out.println(trainCounter+" / "+numWordsTotal);
				lineCounter += 1;
			} // end while scanner
			scanner.close();
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
	}

	public static void trimAllOccWordsIndexes()
	{
		allOccTrainIndexes = new int[occCounter];
		int[] tempAllOccWordsIndexes = new int[occCounter];
		int tempOccCounter = 0;
		int tempTrainIndex = 0;
		for (int i=0; i<allOccWordsIndexes.length; i++)
		{
			if (allOccWordsIndexes[i] != 0) // change to < 0 and find where to initialize unused indexes to -1
			{
				tempAllOccWordsIndexes[tempOccCounter] = allOccWordsIndexes[i];
				allOccTrainIndexes[tempOccCounter] = (i-tempTrainIndex);
				tempTrainIndex = i;
				tempOccCounter += 1;
			}
		}
		allOccWordsIndexes = new int[occCounter];
		for (int i=0; i<occCounter; i++)
		{
			allOccWordsIndexes[i] = tempAllOccWordsIndexes[i];
		}
	}

	public static void printEncodedTrainFile()
	{
		try
		{
			//printWriter = new PrintWriter(new BufferedWriter(new FileWriter("encodedTrainFile.txt",false)));
			trainInts = new int[allOccWordsIndexes.length+allOccTrainIndexes.length];
			for (int i=0; i<occCounter; i++)
			{
				if (allOccWordsIndexes[i] != 0)
				{
					//printWriter.print(allOccWordsIndexes[i]+" "+allOccTrainIndexes[i]+" ");
					//printWriter.flush();
					trainInts[(2*i)] = allOccWordsIndexes[i];
					trainInts[(2*i+1)] = allOccTrainIndexes[i];
				}
				if ((i%10) == 0 && i != 0)
				{
					//printWriter.println("");
				}
			}
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		//printWriter.close();
	}
}
