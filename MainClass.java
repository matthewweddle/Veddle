import java.io.File;
import java.util.BitSet;
import java.util.Scanner;

public class MainClass
{
	public static String[] wordsStr;
	public static String[] wordsStrPart;
	public static String[] wordsStrTotal;
	public static int[] trainInts;
	public static String train;
	public static short[][] coocFreqArr;
	public static short[][] coocFreqArrPart;
	public static short[][] coocFreqArrTotal;
	public static short[][] twogramFreqArr;
	public static short[][] twogramFreqArrPart;
	public static short[][] twogramFreqArrTotal;
	public static short[][] threegramFreqArr;
	public static short[][] threegramFreqArrPart;
	public static short[][] threegramFreqArrTotal;
	public static short[][] fourgramFreqArr;
	public static short[][] fourgramFreqArrPart;
	public static short[][] fourgramFreqArrTotal;
	public static short[][] fivegramFreqArr;
	public static short[][] fivegramFreqArrPart;
	public static short[][] fivegramFreqArrTotal;
	public static short[] freqArr;
	public static short[] freqArrPart;
	public static short[] freqArrTotal;
	public static BitSet[] coocIsNonzeroArr;
	public static short[][] coocArrByInd;
	public static short[][] coocArrByRel;
	public static String[] query;
	public static int[] queryInts;
	public static int[][] queryResultInts;
	public static Scanner scanner;
	public static String inStr;

	// saving and checking for nonzero relats first
	// easier to have multiple lists of words using short rather than using int (can be slower for less common words)

	public static void main(String [] args)
	{
		scanner = new Scanner(System.in);
		System.out.print("Please enter arg : ");
		inStr = ""+scanner.next();
		// might combine words and coocs if not filtering out some words from freqs; don't know what to do yet
		if (inStr.equals("words"))
		{
			scanner.close();
			train = "trainFileLong.txt";
			File wordsFile = new File("words.txt");
			if (wordsFile.exists() == true)
			{
				wordsStrTotal = ExtractWordsFromFile.returnWords(wordsFile);
				freqArrTotal = ExtractFreqsFromFile.returnFreqs(wordsFile);
				FindAndSortTrainWords.printWords(train, wordsStrTotal, freqArrTotal);
			}
			else
				FindAndSortTrainWords.printWords(train);
			System.out.println("success");
		}
		else if (inStr.equals("coocs"))
		{
			// finish all words first, then use coocs (coocs doesn't re-sort like words does)
			scanner.close();
			train = "trainFileLong.txt";
			File wordsFile = new File("words.txt");
			wordsStr = ExtractWordsFromFile.returnWords(wordsFile);
			trainInts = EncodeTrainFile.returnEncodedTrainInts(train, wordsStr);
			coocFreqArrPart = FindCoocs.returnCoocFreqs(trainInts, wordsStr.length, 50);
			twogramFreqArrPart = FindCoocs.returnCoocFreqs(trainInts, wordsStr.length, 2);
			threegramFreqArrPart = FindCoocs.returnCoocFreqs(trainInts, wordsStr.length, 3);
			fourgramFreqArrPart = FindCoocs.returnCoocFreqs(trainInts, wordsStr.length, 4);
			fivegramFreqArrPart = FindCoocs.returnCoocFreqs(trainInts, wordsStr.length, 5);
			File coocFile = new File("coocFile.txt");
			File twogramCoocFile = new File("2gramFile.txt");
			File threegramCoocFile = new File("3gramFile.txt");
			File fourgramCoocFile = new File("4gramFile.txt");
			File fivegramCoocFile = new File("5gramFile.txt");
			if (coocFile.exists() == true)
			{
				coocFreqArrTotal = ExtractCoocsFromFile.returnCoocFreqShorts(coocFile);
				PrintCooc.printCoocFreqArr(coocFreqArrPart, coocFreqArrTotal, 50);
				twogramFreqArrTotal = ExtractCoocsFromFile.returnCoocFreqShorts(twogramFile);
				PrintCooc.printCoocFreqArr(twogramFreqArrPart, twogramFreqArrTotal, 2);
				threegramFreqArrTotal = ExtractCoocsFromFile.returnCoocFreqShorts(threegramFile);
				PrintCooc.printCoocFreqArr(threegramFreqArrPart, threegramFreqArrTotal, 3);
				fourgramFreqArrTotal = ExtractCoocsFromFile.returnCoocFreqShorts(fourgramFile);
				PrintCooc.printCoocFreqArr(fourgramFreqArrPart, fourgramFreqArrTotal, 4);
				fivegramFreqArrTotal = ExtractCoocsFromFile.returnCoocFreqShorts(fivegramFile);
				PrintCooc.printCoocFreqArr(fivegramFreqArrPart, fivegramFreqArrTotal, 5);
			}
			else
			{
				PrintCooc.printCoocFreqArr(coocFreqArrPart, 50);
				PrintCooc.printCoocFreqArr(twogramFreqArrPart, 2);
				PrintCooc.printCoocFreqArr(threegramFreqArrPart, 3);
				PrintCooc.printCoocFreqArr(fourgramFreqArrPart, 4);
				PrintCooc.printCoocFreqArr(fivegramFreqArrPart, 5);
			}
			System.out.println("success");
		}
		else if (inStr.equals("rank"))
		{
			File coocFile = new File("coocFile.txt");
			coocFreqArr = ExtractCoocsFromFile.returnCoocFreqShorts(coocFile);
			PrintCooc.printCoocByIndFile(coocFreqArr, 50); // fix to use coocRatio for both ways, also fix to count freq instead of extract
			File twogramFile = new File("2gramFile.txt");
			twogramFreqArr = ExtractCoocsFromFile.returnCoocFreqShorts(twogramFile);
			PrintCooc.printCoocByIndFile(twogramFreqArr, 2);
			File threegramFile = new File("3gramFile.txt");
			threegramFreqArr = ExtractCoocsFromFile.returnCoocFreqShorts(threegramFile);
			PrintCooc.printCoocByIndFile(threegramFreqArr, 3);
			File fourgramFile = new File("4gramFile.txt");
			fourgramFreqArr = ExtractCoocsFromFile.returnCoocFreqShorts(fourgramFile);
			PrintCooc.printCoocByIndFile(fourgramFreqArr, 4);
			File fivegramFile = new File("5gramFile.txt");
			fivegramFreqArr = ExtractCoocsFromFile.returnCoocFreqShorts(fivegramFile);
			PrintCooc.printCoocByIndFile(fivegramFreqArr, 5);
		}
		// protect against overflow of shorts
		//coocIsNonzeroArr = FindCooc.returnCoocNonzeros(trainInts, wordsStr.length, 50);
		else if (inStr.equals("query"))
		{
			System.out.print("Please enter query length : ");
			inStr = ""+scanner.nextInt();
			query = new String[Integer.parseInt(inStr)];
			for (int i=0; i<query.length; i++)
			{
				System.out.print("Please enter query["+i+"] : ");
				inStr = ""+scanner.next();
				query[i] = inStr;
				System.out.println("query word inputted");
			}
			scanner.close();
			System.out.println(query[0]+" "+query[1]);
			File wordsFile = new File("words.txt");
			File coocByIndFile = new File("coocByIndFile.txt");
			wordsStr = ExtractWordsFromFile.returnWords(wordsFile);
			queryInts = FindQueryInts.returnQueryInts(query, wordsStr);
			coocArrByInd = ExtractCoocsFromFile.returnCoocByIndShorts(coocByIndFile, wordsStr.length);
			// maybe change names to FindQueryResults and GrammaticizeQueryResults (though basically composing more than grammaticizing results)
			queryResultInts = ExpandQuery.returnQueryResultInts(queryInts, coocArrByInd); // fix to sort and separate into results
			File twogramFile = new File("2gramFile.txt");
			twogramFreqArr = ExtractCoocsFromFile.returnCoocs(twogramFile, wordsStr.length);
			File threegramFile = new File("3gramFile.txt");
			threegramFreqArr = ExtractCoocsFromFile.returnCoocs(threegramFile, wordsStr.length);
			File fourgramFile = new File("4gramFile.txt");
			fourgramFreqArr = ExtractCoocsFromFile.returnCoocs(fourgramFile, wordsStr.length);
			File fivegramFile = new File("5gramFile.txt");
			fivegramFreqArr = ExtractCoocsFromFile.returnCoocs(fivegramFile, wordsStr.length);
			queryResultInts = GrammaticizeExpandedQuery.returnGEQuery(queryResultInts, twogramFreqArr, threegramFreqArr, fourgramFreqArr, fivegramFreqArr);
			FindAnswerWords.returnAnswerWords(queryResultInts, wordsStr);
			System.out.println("");
			System.out.println("success");
		}
	}
}
// sort results by the relevanceCounter
// need a 2d array for results: [numResults][numIndexesInResult]
// add up values instead of just finding highest chain, helps prevent bag of words grammar
// consider function to automatically process train files in correct order, might have hundreds or more in storage, easier than waiting for download
// ngrams like markov optimization?
