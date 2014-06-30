public class ExpandQuery
{
	public static int[][] returnQueryResultInts(int[] queryInts, short[][] coocArrByInd)
	{
		// make an array to hold the value of the relevanceCounter for each unique index
		// need to determine how to separate results by relevanceCounter
		// do you need to check if the words in the same result are related enough?

		// separate mess into methods

		int numRelatIndexesTotal = 0; // non-unique for all not each of query ints
		int[] relatIndexesTotal; // arr of non-unique indexes of words related to query words
		for (int i=0; i<queryInts.length; i++)
		{
			numRelatIndexesTotal += coocArrByInd[queryInts[i]].length;
		}
		relatIndexesTotal = new int[numRelatIndexesTotal];
		int relatIndexesTotalCounter = 0;
		for (int i=0; i<queryInts.length; i++)
		{
			for (int j=0; j<coocArrByInd[queryInts[i]].length; j++)
			{
				relatIndexesTotal[relatIndexesTotalCounter] = coocArrByInd[queryInts[i]][j];
				relatIndexesTotalCounter += 1;
			}
		}
		java.util.Arrays.sort(relatIndexesTotal);
		System.out.println("relatIndexesTotalCounter: "+relatIndexesTotalCounter);
		/*for (int i=0; i<relatIndexesTotal.length; i++)
		{
			System.out.println("relatIndexesTotal["+i+"]: "+relatIndexesTotal[i]);
		}*/
		int[] relatIndexesUniqueBig = new int[500];
		int relatIndexesUniqueCounter = 0; // for adding indexes to arr
		int relevanceCounter = 1; // num of indexes related to any word
		int prevUniqueIndex = -1; // not automatically added to relatIndexes, count num related first
		for (int i=0; i<relatIndexesTotal.length; i++)
		{
			if (relatIndexesTotal[i] == prevUniqueIndex)
				relevanceCounter += 1;
			else
			{
				if (relevanceCounter >= 2)
				{
					relatIndexesUniqueBig[relatIndexesUniqueCounter] = prevUniqueIndex;
					relatIndexesUniqueCounter += 1;
				}
				prevUniqueIndex = relatIndexesTotal[i];
				relevanceCounter = 1;
			}
		}
		System.out.println("relatIndexesUniqueCounter: "+relatIndexesUniqueCounter);
		int[] relatIndexesUnique = new int[relatIndexesUniqueCounter];
		for (int i=0; i<relatIndexesUnique.length; i++)
		{
			relatIndexesUnique[i] = relatIndexesUniqueBig[i];
		}

		return relatIndexesUnique;
	}
}
