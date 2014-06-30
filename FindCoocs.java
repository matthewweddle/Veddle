import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.BitSet;

public class FindCoocs
{
	public static int[] trainInts;
	public static PrintWriter printWriter;
	public static BitSet[] coocIsNonzeroArr;
	public static short[][] coocFreqArr;
	public static int contextLength; // use 50 for coocs and other for ngrams
	//static int freqWords; // number times that word observed
	//static int freqCooc; // nuumber times that word cooccurred
	public static int numWordsUnique;
	public static int contextCounter;
	public static int wordsCounter;
	public static int minNumCooc = 5;
	public static int numCoocRelats = 0;
	public static int numEmptyRelats = 0;

	// is already symmetric

	public static BitSet[] returnCoocNonzeros(int[] train, int wordsStrLength, int contextL)
	{
		trainInts = train; // should already be encoded
		numWordsUnique = wordsStrLength;
		contextLength = contextL;
		coocIsNonzeroArr = new BitSet[numWordsUnique];
		for (int i=0; i<coocIsNonzeroArr.length; i++)
		{
			coocIsNonzeroArr[i] = new BitSet(numWordsUnique);
		}

		int tempI;
		for (int i=0; i<trainInts.length; i+=2)
		{
			contextCounter = 0;
			contextCounter = trainInts[(i+1)];
			tempI = (i+2);
			while (contextCounter <= contextLength && (tempI+1)<trainInts.length)
			{
				if (coocIsNonzeroArr[trainInts[i]].get(trainInts[tempI]) == false)
				{
					coocIsNonzeroArr[trainInts[i]].flip(trainInts[tempI]);
					coocIsNonzeroArr[tempI].flip(trainInts[i]);
				}
				contextCounter += trainInts[(tempI+1)];
				tempI += 2;
			}
			if (i%100000 == 0)
				System.out.println(i+" / "+trainInts.length);
		} // end for trainInts (to create coocIsNonzeroArr)

		return coocIsNonzeroArr;
	}

	public static short[][] returnCoocFreqs(int[] train, int wordsStrLength, int contextL)
	{
		trainInts = train; // should already be encoded
		numWordsUnique = wordsStrLength;
		contextLength = contextL;
		coocFreqArr = new short[wordsStrLength][wordsStrLength];
		int tempI;
		for (int i=0; i<trainInts.length; i+=2)
		{
			contextCounter = 0;
			contextCounter = trainInts[(i+1)];
			tempI = (i+2);
			while (contextCounter <= contextLength && (tempI+1)<trainInts.length)
			{
				coocFreqArr[trainInts[i]][trainInts[tempI]] += 1;
				coocFreqArr[trainInts[tempI]][trainInts[i]] += 1;
				contextCounter += trainInts[(tempI+1)];
				tempI += 2;
			}
			if (i%100000 == 0)
				System.out.println(i+" / "+trainInts.length);
		}
		return coocFreqArr;
	}
}
