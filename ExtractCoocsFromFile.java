import java.io.File;
import java.util.Scanner;

public class ExtractCoocsFromFile
{
	static short[][] coocFreqArr;
	static short[][] coocArrByInd;
	static int numUniqueWords;
	static boolean moveToNextWord = true;
	static int currentWord; // used for first word in arr
	static int currentCooc; // used for second word in arr when looking at indexes instead of freqs
	static int tokensStart = 0;
	static Scanner scanner;
	static String inStr;
	static String[] tokens;

	public static short[][] returnCoocFreqShorts(File coocFile)
	{
		try
		{
			scanner = new Scanner(coocFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				tokens = inStr.split("([.,!?:;'\"]|\\s)+");
				//System.out.println(""+tokens[0]);
				if (moveToNextWord == true)
				{
					moveToNextWord = false;
					currentWord = Integer.parseInt(tokens[0]);
				}
				else if (tokens[0].equals("-1"))
					moveToNextWord = true;
			} // end while scanner
			scanner.close();
			numUniqueWords = (currentWord+1);
			currentWord = 0;
			moveToNextWord = true;
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}

		System.out.println("numUniqueWords "+numUniqueWords);
		coocFreqArr = new short[numUniqueWords][numUniqueWords];
		try
		{
			scanner = new Scanner(coocFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				tokens = inStr.split("([.,!?:;'\"]|\\s)+");
				//System.out.println(""+tokens[0]);
				if (moveToNextWord == true)
				{
					moveToNextWord = false;
					currentWord = Integer.parseInt(tokens[0]);
					if (currentWord%100 == 0)
						System.out.println(currentWord+" / "+numUniqueWords);
				}
				else if (tokens[0].equals("-1"))
					moveToNextWord = true;
				else
				{
					for (int i=0; i<tokens.length; i+=2)
					{
						coocFreqArr[currentWord][Integer.parseInt(tokens[i])] = Short.parseShort(tokens[(i+1)]);
					}
				}
			} // end while scanner
			scanner.close();
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		return coocFreqArr;
	}

	public static short[][] returnCoocByIndShorts(File coocFile, int numUniqueWords)
	{
		coocArrByInd = new short[numUniqueWords][1]; // using this to count num of coocRelats for creation later
		try
		{
			scanner = new Scanner(coocFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				tokens = inStr.split("([.,!?:;'\"]|\\s)+");
				//System.out.println(""+tokens[0]);
				if (moveToNextWord == true)
				{
					moveToNextWord = false;
					currentWord = Integer.parseInt(tokens[0]);
				}
				else if (tokens[0].equals("-1"))
					moveToNextWord = true;
				else
				{
					for (int i=0; i<tokens.length; i++)
					{
						coocArrByInd[currentWord][0] += 1;
					}
				}
			} // end while scanner
			scanner.close();

			scanner = new Scanner(coocFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				tokens = inStr.split("([.,!?:;'\"]|\\s)+");
				//System.out.println(""+tokens[0]);
				if (moveToNextWord == true)
				{
					moveToNextWord = false;
					currentWord = Integer.parseInt(tokens[0]);
					currentCooc = 0;
					coocArrByInd[currentWord] = new short[(coocArrByInd[currentWord][0])];
					if (currentWord%100 == 0)
						System.out.println(currentWord+" / "+numUniqueWords);
				}
				else if (tokens[0].equals("-1"))
					moveToNextWord = true;
				else if (tokens[0].equals("")==false)
				{
					for (int i=0; i<tokens.length; i++)
					{
						coocArrByInd[currentWord][currentCooc] = Short.parseShort(tokens[i]);
						currentCooc += 1;
					}
				}
			} // end while scanner
			scanner.close();
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		return coocArrByInd;
	}
}
