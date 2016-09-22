package MatchController;

import GameController.GameController;
import MatchController.Objects.PlayerObject;
import MatchController.GUI.GameManagerGuiForm;
import MatchController.GUI.PlayerGeneratedGroupsGuiForm;
import MatchController.GUI.WinnerGuiForm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislavs on 06.09.2016..
 */

// TODO style players turn
// TODO Create functionality to display next group after each players game

// TODO Create menus, back button

// TODO Check and put try-catch blocs where is needed
// TODO Create documentation

// TODO more than 2 people in one group -> little bit changes in rules check this! (for this need huge refactor and logic changes) AND Create more game types

// Additional features
// ==================================
// TODO Create logging system
// TODO Game Save for unexpected exit
// TODO all player result?
// TODO allPlayer places?
// TODO style configuration
//===================================

// TODO Global Refactor: split logic
// TODO Global End Refactor


public class MatchController
{
	private GameManagerGuiForm                            gameManagerGuiForm;
	private PlayerGeneratedGroupsGuiForm                  playerGeneratedGroupsGuiForm;
	private WinnerGuiForm                                 winnerGuiForm;
	private GameController                                mGameController;

	private ArrayList <PlayerObject>                      mPlayerList;
	private HashMap <Integer, ArrayList <Integer>>        mPlayerGroupsMap;
	private HashMap <Integer, PlayerObject>               mStageWinnerHashMap;

	private Integer                                       mCurrentPlayingGroupNumber;
	private Integer                                       mPlayersNumberInGroup;


	public MatchController ()
	{
		initializeNewMatch ();
	}


	private void initializeNewMatch ()
	{
		mPlayersNumberInGroup       = 2;
		mCurrentPlayingGroupNumber = 0;
		mStageWinnerHashMap         = new HashMap <> ();
		gameManagerGuiForm          = new GameManagerGuiForm (this);
	}


	private void setPlayerList (ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayerList =  new ArrayList <> (tablePlayerList);
	}


	public void runActionsAfterPlayerRegistration (Integer playersNumberInGroup, ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayersNumberInGroup = playersNumberInGroup;

		setPlayerList (tablePlayerList);
		matchManagerGuiFormClose ();
		initializePlayersGroups ();

		try
		{
			ifOnePlayerInGroupPromoteToNextStage ();
			displayGameGroups ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private void ifOnePlayerInGroupPromoteToNextStage () throws Exception
	{
		for (Object o : mPlayerGroupsMap.entrySet ())
		{
			Map.Entry <Integer, ArrayList <Integer>> pair = (Map.Entry <Integer, ArrayList <Integer>>) o;   // Could by cast exception at runtime!
			ArrayList <Integer> value =  pair.getValue ();

			if (value.size () < mPlayersNumberInGroup)
			{
				Integer playerId = pair.getKey ();

				mStageWinnerHashMap.put (playerId, getPlayerObjectById (playerId));
				mPlayerGroupsMap.remove (pair.getKey ());
			}
		}
	}


	private void matchManagerGuiFormClose ()
	{
		// TODO Create correct handle of form to close form and reuse of form (if need to go back)
		gameManagerGuiForm.setVisibility (false);
	}


	private void playerGeneratedGroupsGuiFormClose ()
	{
		// TODO Create correct handle of form to close form and reuse of form (if need to go back)
		playerGeneratedGroupsGuiForm.setVisibility (false);
	}


	private void initializePlayersGroups ()
	{
		mPlayerGroupsMap = GroupGenerator.generateRandomGroups (mPlayersNumberInGroup, mPlayerList);
	}


	public void runActionsAfterGroupDisplay ()
	{
		try
		{
			playerGeneratedGroupsGuiFormClose ();
			startGame ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private void displayGameGroups()
	{
		playerGeneratedGroupsGuiForm = new PlayerGeneratedGroupsGuiForm (this, mPlayerList, mPlayerGroupsMap);
	}


	private ArrayList <PlayerObject> getGameOpponents () throws Exception
	{
		ArrayList <PlayerObject> playerObjectsList = new ArrayList <> ();

		ArrayList <Integer> playersIds = mPlayerGroupsMap.get (getNextPlayersGroupNumber ());
		for (Integer playerId : playersIds)
			playerObjectsList.add (getPlayerObjectById (playerId));

		return playerObjectsList;
	}


	private int getNextPlayersGroupNumber ()
	{
		return ++ mCurrentPlayingGroupNumber;
	}


	private PlayerObject getPlayerObjectById (Integer playerId) throws Exception
	{
		for (PlayerObject player : mPlayerList)
			if (player.mId.equals (playerId))
				return player;

		throw new Exception ("Can`t find player object by id! Bug spotted");
	}


	public void runActionsAfterGameController (ArrayList <PlayerObject> playerObjectArrayListResult)
	{
		try
		{
			PlayerObject winner = getWinnersPlayerObject (playerObjectArrayListResult);
			mStageWinnerHashMap.put (mCurrentPlayingGroupNumber, winner);

			if (ifLastGroupPlayed ())
				handleLastGroupPlayedState (winner);
			else
				displayWinner (winner.mName);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private void handleLastGroupPlayedState (PlayerObject winner) throws Exception
	{
		if (ifFinalStage ())
		{
			showMatchWinner (winner);
		}
		else
		{
			groupsStageRotation ();
			displayWinner (winner.mName, true);
		}
	}


	private void showMatchWinner (PlayerObject winner)
	{
		winnerGuiForm = new WinnerGuiForm (this, winner);
	}


	private boolean ifLastGroupPlayed ()
	{
		return getGroupCount ().equals (mCurrentPlayingGroupNumber);
	}


	private Integer getGroupCount ()
	{
		return mPlayerGroupsMap.size ();
	}


	private void displayWinner (String winnerName)
	{
		playerGeneratedGroupsGuiForm.displayWinnerPanelInGroup (mCurrentPlayingGroupNumber, winnerName);
		playerGeneratedGroupsGuiForm.setVisibility (true);
	}


	private void  displayWinner (String winnerName, boolean isNextStage)
	{
		playerGeneratedGroupsGuiForm.displayWinner (mCurrentPlayingGroupNumber, winnerName, isNextStage);
		playerGeneratedGroupsGuiForm.setVisibility (true);
	}


	public void nextStageTrigger ()
	{
		playerGeneratedGroupsGuiForm.destroy ();
		mCurrentPlayingGroupNumber = 0;
		displayGameGroups();
	}


	private void groupsStageRotation () throws Exception
	{
		mPlayerGroupsMap.clear ();

		int winnersHashMapSize = mStageWinnerHashMap.size ();

		for (int i = 1; i < winnersHashMapSize; i = i + 1)
		{
			PlayerObject currentPlayer  = mStageWinnerHashMap.get (i);
			PlayerObject nextPlayer     = mStageWinnerHashMap.get (i + 1);

			if (currentPlayer == null || nextPlayer == null)
				throw new Exception ("Can`t retrieve id from current player object");


			if (i + 1 > winnersHashMapSize)
				mPlayerGroupsMap.put (i, new ArrayList <Integer> ()
				{
					{
						add (currentPlayer.mId);
					}
				});
			else
				mPlayerGroupsMap.put (i, new ArrayList <Integer> ()
				{
					{
						add (currentPlayer.mId);
						add (nextPlayer.mId);
					}
				});
		}

		mStageWinnerHashMap.clear ();
	}


	private boolean ifFinalStage ()
	{
		return mStageWinnerHashMap.size () == 1;
	}


	private PlayerObject getWinnersPlayerObject (ArrayList<PlayerObject> playerObjectArrayListResult)
	{
		return (playerObjectArrayListResult.get (0).mLeg > playerObjectArrayListResult.get (1).mLeg) ?
				playerObjectArrayListResult.get (0) :
				playerObjectArrayListResult.get (1);    // TODO Refactor bad code
	}


	public void newMatch ()
	{
		if (winnerGuiForm != null)
			winnerGuiForm = null;

		initializeNewMatch ();
	}


	private void startGame () throws Exception
	{
		if (mGameController != null)
			mGameController = null;

		mGameController = new GameController (this, getGameOpponents ());
	}
}
