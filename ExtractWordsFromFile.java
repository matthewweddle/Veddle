import java.io.File;
import java.util.Scanner;

public class ExtractWordsFromFile
{
	static String[] words;
	static int numUniqueWords = 0;
	static Scanner scanner;
	static String inStr;
	static String[] tokens;

	public static String[] returnWords(File wordsFile)
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

			words = new String[numUniqueWords];
			scanner = new Scanner(wordsFile);
			while (scanner.hasNext())
			{
				inStr = scanner.nextLine();
				tokens = inStr.split("([.,!?:;'\"-]|\\s)+");
				words[Integer.parseInt(tokens[0])] = ""+tokens[1];
			} // end while scanner
			scanner.close();
		}
		catch(Exception exc)
		{
			System.out.println("couldn't read file");
			exc.printStackTrace();
			System.out.println("");
		}
		return words;
	}
}
