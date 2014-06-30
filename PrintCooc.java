import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.BitSet;

public class PrintCooc
{
	public static int[] trainInts;
	public static PrintWriter printWriter;
	public static BitSet[] coocIsNonzeroArr;
	public static short[][] coocArrByInd;
	public static short[][] coocArrByRel;
	public static int contextLength; // use 50 for coocs and other for ngrams
	//static int freqWords; // number times that word observed
	//static int freqCooc; // nuumber times that word cooccurred
	public static int numWordsUnique;
	public static int contextCounter;
	public static int wordsCounter;
	public static int minNumCooc = 15;
	public static int numCoocRelats = 0;
	public static int numEmptyRelats = 0;

	public static void printCoocFreqArr(short[][] coocFreqArrPart, int contextL)
	{
		try
		{
			if (contextL == 50)
				printWriter = new PrintWriter(new BufferedWriter(new FileWriter("coocFile.txt",false)));
			else
				printWriter = new PrintWriter(new BufferedWriter(new FileWriter(contextL+"gramFile.txt",false)));
			int printedCounter = 0;
			for (int i=1; i<coocFreqArrPart.length; i++)
			{
				printWriter.println(i+"");
				printedCounter = 0;
				for (int j=1; j<coocFreqArrPart[i].length; j++)
				{
					if (printedCounter == 10)
					{
						printWriter.println("");
						printedCounter = 0;
					}
					printWriter.print(j+" "+coocFreqArrPart[i][j]+" ");
					printedCounter += 1;
				}
				printWriter.println("");
				printWriter.println("-1 ");
				if (i%100 == 0)
					System.out.println(i+" / "+coocFreqArrPart[i].length);
			}
			printWriter.close();
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}

	}

	public static void printCoocFreqArr(short[][] coocFreqArrPart, short[][] coocFreqArrTotal, int contextL)
	{
		try
		{
			if (contextL == 50)
				printWriter = new PrintWriter(new BufferedWriter(new FileWriter("coocFile.txt",false)));
			else
				printWriter = new PrintWriter(new BufferedWriter(new FileWriter(contextL+"gramFile.txt",false)));
			int printedCounter = 0;
			for (int i=1; i<coocFreqArrPart.length; i++)
			{
				printWriter.println(i+"");
				printedCounter = 0;
				for (int j=1; j<coocFreqArrPart[i].length; j++)
				{
					if (printedCounter == 10)
					{
						printWriter.println("");
						printedCounter = 0;
					}
					printWriter.print(j+" "+(coocFreqArrPart[i][j]+coocFreqArrTotal[i][j])+" ");
					printedCounter += 1;
				}
				printWriter.println("");
				printWriter.println("-1 ");
				if (i%100 == 0)
					System.out.println(i+" / "+coocFreqArrPart[i].length);
			}
			printWriter.close();
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}

	}

	// is already symmetric

	public static void printCoocByIndFile(short[][] coocFreqArr, int contextL)
	{
		try
		{
			if (contextL == 50)
				printWriter = new PrintWriter(new BufferedWriter(new FileWriter("coocByIndFile.txt",false)));
			else
				printWriter = new PrintWriter(new BufferedWriter(new FileWriter(contextLength+"gramByIndFile.txt",false)));
			//printWriter.print(wordsCounter+" ");
			int printedCounter = 0;
			for (int i=0; i<coocFreqArr.length; i++)
			{
				printWriter.println(i+"");
				printedCounter = 0;
				for (int j=0; j<coocFreqArr.length; j++)
				{
					if (coocFreqArr[i][j] > minNumCooc)
					// want to use coocRatio, coocRatio = (coocFreqArr[i][j] / freqArr[i]);
					{
						printWriter.print(j+" ");
						printedCounter += 1;
						numCoocRelats += 1;
					}
					else
						numEmptyRelats += 1;

					if (printedCounter == 10)
					{
						printWriter.println("");
						printedCounter = 0;
					}
				}
				printWriter.println("");
				printWriter.println("-1 ");
				if (i%100 == 0)
					System.out.println(i+" / "+coocFreqArr[i].length);
			}
			printWriter.close();
			System.out.println("numCoocRelats: "+numCoocRelats);
			System.out.println("numEmptyRelats: "+numEmptyRelats);
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}

		/*coocArrByInd = new[numWordsUnique][1];
		short numNonzeroCoocs = 0;
		for (int i=0; i<coocArrByInd.length; i++)
		{
			numNonzeroCoocs = 0;
			for (int j=0; j<coocIsNonzeroArr[i].length(); j++)
			{
				if (coocIsNonzeroArr[i].get(j) == 1)
					numNonzeroCocs += 1;
			}
			coocArrByInd[i] = new short(numNonzeroCoocs);
		}
		short coocArrByIndCounter = 0;
		for (int i=0; i<coocArrByInd.length; i++)
		{
			coocArrByIndCounter = 0;
			for (int j=0; j<coocIsNonzeroArr[i].length(); j++)
			{
				if (coocIsNonzeroArr[i].get(j) == 1)
				{
					coocArrByInd[i][coocArrByIndCounter] = (short)(j);
					coocArrByIndCounter += 1;
				}
			}
		}*/
	}
}
