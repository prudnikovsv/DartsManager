package Tools;

import MatchController.Objects.PlayerObject;

import java.util.*;

/**
 * Created by vladislavs on 06.09.2016..
 */

// TODO May be need Refactor - Check!

public class GroupGenerator
{
	public static HashMap <Integer, ArrayList <PlayerObject>> generateRandomGroups (Integer playersNumberInGroup, ArrayList <PlayerObject> playerList)
	{
		int groupCount = playerList.size () / playersNumberInGroup;

		if (isOdd (playerList.size ()))
			groupCount++;

		HashMap <Integer, ArrayList <PlayerObject>> generatedGroupMap = new HashMap <> ();
		for (int i = 0; i < groupCount; i++)
			generatedGroupMap.put (i, getOneCreatedGroup (playersNumberInGroup, playerList, getShuffledListOfPlayerObject (playerList)));

		return generatedGroupMap;
	}


	private static ArrayList <PlayerObject> getOneCreatedGroup (Integer maxPlayerInGroup, ArrayList <PlayerObject> playerList, List <PlayerObject> shuffledList)
	{
		ArrayList <PlayerObject> playersGroupValue = new ArrayList <> ();

		for (int i = 0; i < maxPlayerInGroup; i++)
			if (shuffledList.size () != 0)
				playersGroupValue.add (getPlayerFromShuffledListAndRemove (shuffledList));

		return playersGroupValue;
	}


	private static PlayerObject getPlayerFromShuffledListAndRemove (List <PlayerObject> shuffledList)
	{
		PlayerObject playerObjectFromShuffledList = shuffledList.get (0);
		shuffledList.remove (0);

		return playerObjectFromShuffledList;
	}


	private static boolean isOdd (int maxPlayerInGroup)
	{
		return ((maxPlayerInGroup % 2) != 0);
	}


	private static List <PlayerObject> getShuffledListOfPlayerObject (ArrayList <PlayerObject> playerList)
	{
		List <Integer> returnList = new LinkedList <> ();

		for (int i = 0; i < playerList.size (); i++)
			returnList.add (i);

		Collections.shuffle(returnList);

		return createListOfPlayerObject (playerList, returnList);
	}


	private static List <PlayerObject> createListOfPlayerObject (ArrayList <PlayerObject> playerList, List <Integer> playersIdList)
	{
		List <PlayerObject> returnList = new LinkedList <> ();

		for (Integer id : playersIdList)
			returnList.add (findPlayerObject (playerList, id));

		return returnList;
	}


	private static PlayerObject findPlayerObject (ArrayList<PlayerObject> playerList, Integer searchedId)   // TODO Handle null exception!!!
	{
		for (PlayerObject player : playerList)
			if (player.mId.equals (searchedId))
				return player;

		return null;
	}
}