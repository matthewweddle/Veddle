public class FindAnswerWords
{
	public static void returnAnswerWords(int[] queryInts, String[] wordsStr)
	{
		String[] answerWords = new String[queryInts.length];
		for (int i=0; i<queryInts.length; i++)
		{
			answerWords[i] = wordsStr[queryInts[i]];
		}
		for (int i=0; i<answerWords.length; i++)
		{
			if (i%10 == 0)
				System.out.println("");
			System.out.print(answerWords[i]+" ");
		}
	}
}
