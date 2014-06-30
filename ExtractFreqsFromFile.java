import java.io.File;
import java.util.Scanner;

public class ExtractFreqsFromFile
{
	static short[] freqs;
	static int numUniqueWords = 0;
	static Scanner scanner;
	static String inStr;
	static String[] tokens;

	public static short[] returnFreqs(File wordsFile)
	{
		// on each line: (int)index (String)word (short)freq
		try
		{
			scanner = new Scanner(wordsFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				numUniqueWords += 1;
			} // end while scanner
			scanner.close();

			freqs = new short[numUniqueWords];
			scanner = new Scanner(wordsFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				tokens = inStr.split("([.,!?:;'\"-]|\\s)+");
				freqs[Short.parseShort(tokens[0])] = Short.parseShort(tokens[2]);
			} // end while scanner
			scanner.close();
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		return freqs;
	}
}
