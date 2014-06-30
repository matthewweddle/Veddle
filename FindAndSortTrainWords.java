import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class FindAndSortTrainWords
{
	public static File trainFile;
	static Scanner scanner;
	static PrintWriter printWriter;
	static String inStr; // used for parsing from scanner
	static String[] tokens; // used for parsing words from inStr
	static String[] words; // the unique words
	static int numWordsTotal; // the initial length of words[]
	static int numWordsUnique; // the final length of words[]
	static short[] freqWords; // number times that word observed
	static int totalLines = 0;
	static short minFreqWord = 50;
	static short maxFreqWord = 2000;

	public static void printWords(String train)
	{
		trainFile = new File(train);
		findTotalWords();
		System.out.println("numWordsTotal: "+numWordsTotal);
		sortTotalWords();
		trimTotalWords(); // removes copied words
		findUniqueWords(); // puts nontrimmed words in new words[]
		System.out.println("numWordsUnique: "+numWordsUnique);
		//trimFreqWords();
		printWordsToFile();
		System.out.println("words printed");
	}

	public static void printWords(String train, String[] wordsStrTotal, short[] freqArrTotal)
	{
		trainFile = new File(train);
		findTotalWords();
		System.out.println("numWordsTotal: "+numWordsTotal);
		sortTotalWords();
		trimTotalWords(); // removes copied words
		findUniqueWords(); // puts nontrimmed words in new words[]
		System.out.println("numWordsUnique: "+numWordsUnique);
		//trimFreqWords();
		printWordsToFile(wordsStrTotal, freqArrTotal);
		System.out.println("words printed");
	}

	public static void findTotalWords()
	{
		try
		{
			scanner = new Scanner(trainFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				//System.out.println("inStr: "+inStr);
				tokens = inStr.split("([.,!?:;'\"-]|\\s)+");
				numWordsTotal += tokens.length;
				totalLines += 1;
			}
			System.out.println("totalLines: "+totalLines);
			scanner.close();
			words = new String[numWordsTotal];
			int wordCounter = 0; // used to insert tokens into words[]
			scanner = new Scanner(trainFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				//System.out.println("inStr: "+inStr);
				tokens = inStr.split("([.,!?:;'\"-]|\\s)+");
				for (int i=0; i<tokens.length; i++)
				{
					tokens[i].toLowerCase();
					words[wordCounter] = ""+tokens[i];
					wordCounter += 1;
				}
			}
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		scanner.close();
	}

	public static void sortTotalWords()
	{
		java.util.Arrays.sort(words);
	}

	public static void trimTotalWords()
	{
		freqWords = new short[numWordsTotal];
		freqWords[0] = 1;
		for (int i=1; i<words.length; i++)
		{
			if (words[i].equals(words[i-1]))
			{
				words[i-1] = "";
				freqWords[i] = (short)(freqWords[i-1]+1);
				freqWords[i-1] = 0;
			}
		}
	}

	public static void findUniqueWords()
	{
		// numWordsUnique, freqWord
		int wordCounter = 0; // used to insert unique words into words[]
		int freqCounter = 0;
		String[] wordsTemp = new String[words.length];
		short[] freqWordsTemp = new short[freqWords.length];
		for (int i=0; i<words.length; i++)
		{
			if (words[i].equals("") == false && freqWords[i] >= minFreqWord && freqWords[i] <= maxFreqWord)
			{
				wordsTemp[wordCounter] = ""+words[i];
				wordCounter += 1;
				freqWordsTemp[freqCounter] = freqWords[i];
				freqCounter += 1;
			}
		}
		numWordsUnique = wordCounter;
		words = new String[numWordsUnique];
		for (int i=0; i<words.length; i++)
		{
			words[i] = wordsTemp[i];
		}
		freqWords = new short[numWordsUnique];
		for (int i=0; i<freqWords.length; i++)
		{
			freqWords[i] = freqWordsTemp[i];
		}
	} // end findUniqueWords

	/*public static void trimFreqWords() // done in findUniqueWords
	{
		short[] freqWordsTemp = new short[numWordsUnique];
		int freqCounter = 0;
		for (int i=0; i<freqWords.length; i++)
		{
			if (freqWords[i] != 0)
			{
				freqWordsTemp[freqCounter] = freqWords[i];
				freqCounter += 1;
			}
		}
		freqWords = new short[numWordsUnique];
		for (int i=0; i<freqWords.length; i++)
		{
			freqWords[i] = freqWordsTemp[i];
		}
	}*/

	public static void printWordsToFile()
	{
		try
		{
			printWriter = new PrintWriter(new BufferedWriter(new FileWriter("words.txt",false)));
			for (int i=0; i<words.length; i++)
			{
				printWriter.println(i+" "+words[i]+" "+freqWords[i]);
				printWriter.flush();
			}
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		printWriter.close();
	}

	public static void printWordsToFile(String[] wordsStrTotal, short[] freqArrTotal)
	{
		try
		{
			BitSet wordIsNew = new BitSet(words.length);
			int wordsStrTotalCounter = 0;
			int j;
			boolean searchIsDone;
			for (int i=0; i<words.length; i++)
			{
				j = wordsStrTotalCounter;
				searchIsDone = false;
				while (searchIsDone == false)
				{
					if (words[i].equals(wordsStrTotal[j]))
					{
						freqArrTotal[j] += freqWords[i];
						searchIsDone = true;
						wordsStrTotalCounter = j;
					}
					else if (words[i].compareTo(wordsStrTotal[j]) < 0)
					{
						wordIsNew.flip(i);
						searchIsDone = true;
					}
					j += 1;
				}
			}

			printWriter = new PrintWriter(new BufferedWriter(new FileWriter("words.txt",false)));
			int wordIsNewCounter = 0;
			int newWordsAlreadyPrinted = 0;
			for (int i=0; i<wordsStrTotal.length; i++)
			{
				// use wordIsNew to compare wordsStrTotal before printing
				while (wordsStrTotal[i].compareTo(words[wordIsNewCounter]) < 0 && wordIsNewCounter < wordIsNew.length())
				{
					// need to determine or add while loop to find where wordIsNewCounter should be
					printWriter.println((i+newWordsAlreadyPrinted)+" "+words[wordIsNewCounter]+" "+freqWords[wordIsNewCounter]);
					newWordsAlreadyPrinted += 1;
					wordIsNewCounter += 1;
					while (wordIsNew.get(wordIsNewCounter) == false && wordIsNewCounter < wordIsNew.length())
					{
						wordIsNewCounter += 1;
					}
				}
				printWriter.println((i+newWordsAlreadyPrinted)+" "+wordsStrTotal[i]+" "+freqArrTotal[i]);
			}
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		printWriter.close();
	}
}
