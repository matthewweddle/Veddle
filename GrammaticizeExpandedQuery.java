public class GrammaticizeExpandedQuery
{
	public static int[][] returnGEQuery(int[][] queryResultInts, short[][] twogramFreqArr, short[][] threegramFreqArr, short[][] fourgramFreqArr, short[][] fivegramFreqArr)
	{
		for (int i=0; i<queryResultInts.length; i++)
		{
			for (int j=0; j<queryResultInts[i].length; j++)
			{
				// basically like relevanceCounter algorithm for grammar
				// use ngrams to maximize grammaticalCounter
			}
		}
		return queryResultInts;
	}
}
// do you need to grammaticalize?
