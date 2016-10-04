package MatchController;

import MatchController.Objects.PlayerObject;

import java.util.*;

/**
 * Created by vladislavs on 06.09.2016..
 */
public class GroupGenerator
{

	public static HashMap <Integer, ArrayList <Integer>> generateRandomGroups (Integer playersNumberInGroup, ArrayList <PlayerObject> playerList)
	{
		HashMap <Integer, ArrayList <Integer>> generatedGroupMap = new HashMap <> ();
		List <Integer> shuffledList = getShuffledList (playerList);

		int maxPlayerInGroup = playersNumberInGroup;
		int groupCount = playerList.size () / maxPlayerInGroup;

		if (isOdd (playerList.size ()))
			groupCount++;

		for (int i = 0; i < groupCount; i++)
			generatedGroupMap.put (i, getOneCreatedGroup (maxPlayerInGroup, playerList, shuffledList));

		return generatedGroupMap;
	}


	private static ArrayList <Integer> getOneCreatedGroup (Integer maxPlayerInGroup, ArrayList <PlayerObject> playerList, List <Integer> shuffledList)
	{
		ArrayList <Integer> playersGroupValue = new ArrayList <> ();

		for (int i = 0; i < maxPlayerInGroup; i++)
			if (shuffledList.size () != 0)
				playersGroupValue.add (getPlayerFromShuffledListAndRemove (playerList, shuffledList));

		return playersGroupValue;
	}


	private static Integer getPlayerFromShuffledListAndRemove (ArrayList <PlayerObject> playerList, List <Integer> shuffledList)
	{
		Integer playerIdFromShuffledList = shuffledList.get (0);
		Integer pId = playerList.get (playerIdFromShuffledList).mId;
		shuffledList.remove (0);

		return pId;
	}


	private static boolean isOdd (int maxPlayerInGroup)
	{
		return ((maxPlayerInGroup % 2) != 0);
	}


	private static List <Integer> getShuffledList (ArrayList <PlayerObject> playerList)
	{
		List <Integer> returnList = new LinkedList <> ();

		for (int i = 0; i < playerList.size (); i++)
			returnList.add (i);

		Collections.shuffle(returnList);

		return returnList;
	}
}
