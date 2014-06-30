public class FindQueryInts
{
	public static int[] returnQueryInts(String[] query, String[] wordsStr)
	{
		int[] queryInts = new int[query.length];
		for (int i=0; i<query.length; i++)
		{
			queryInts[i] = java.util.Arrays.binarySearch(wordsStr, query[i]);
			if (queryInts[i] < 0)
				queryInts[i] = 0;
		}
		return queryInts;
	}
}
