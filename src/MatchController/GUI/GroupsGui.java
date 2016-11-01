package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Objects.PlayerObject;
import MatchController.Constats;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislavs on 07.10.2016..
 */

// TODO Fix scroll to work
// TODO Change group design
// TODO Set some BG
// TODO Style buttons
// TODO Refactor

public class GroupsGui
{
	private final MatchController mMatchController;

	private JFrame                                              mJFrame;

	private JPanel                                              mJPanel;
	private JPanel                                              mControlJPanel;
	private JPanel                                              mGroupsPanel;
	private GroupPanelLines								        mGlassPanel;
	private JScrollPane                                         mGroupsScrollPane;

	private JButton                                             mGameStartBtn;
	private JButton                                             mNextStageBtn;
	private JButton                                             mBackBtn;

	private HashMap<Integer, ArrayList<Integer>>                mPlayerGroupsMap;
	private ArrayList <PlayerObject>                            mPlayerList;

	private HashMap <Integer, ArrayList <DisplayGroupPanel>>    mGroupsPanels;

	private Integer                                             mCurrentPlayingGroupNumber;


	public GroupsGui (MatchController matchController, ArrayList<PlayerObject> playerList,
					  HashMap <Integer, ArrayList <Integer>> playerGroupsMap, Integer currentPlayingGroupNumber)
	{
		mCurrentPlayingGroupNumber  = currentPlayingGroupNumber;
		mMatchController            = matchController;
		mPlayerList                 = playerList;
		mPlayerGroupsMap            = new HashMap <> (playerGroupsMap);
		mGroupsPanels 				= new HashMap <> ();

		initializeComponents ();
		addComponentsListener ();
		buildMainFrame ();
	}


	private void buildMainFrame ()
	{
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.setContentPane (mJPanel);
		setMJFrameLocation ();
		mainPanelBuilder ();
		mJFrame.pack ();
		mJFrame.setVisible (true);
	}


	private void mainPanelBuilder ()
	{
		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		mJPanel.setLayout (new GridBagLayout ());
		mJPanel.setPreferredSize (new Dimension (Constats.MAIN_WIDTH + 200, Constats.MAIN_HEIGHT + 100));
		mJPanel.setBackground (Color.BLUE);

		controlPanelBuilder ();
		groupsPanelBuilder ();

		createGroupLines ();
		addComponentToPanel (mJPanel, mGlassPanel,    0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 2, GridBagConstraints.NORTHWEST,       mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (mJPanel, mGroupsPanel,   0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 2, GridBagConstraints.NORTHWEST,       mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (mJPanel, mControlJPanel, 0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.LAST_LINE_START, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void groupsPanelBuilder ()
	{
		mGroupsPanel.setLayout (new GridBagLayout ());
		//mGroupsPanel.setPreferredSize (new Dimension (Constats.MAIN_WIDTH, Constats.MAIN_HEIGHT));
		addGroupsToPanelAndUpdateFrame ();
		setCurrentPlayingGroup ();
	}


	private void controlPanelBuilder ()
	{
		GridBagConstraints mControlPanelGbc = new GridBagConstraints ();
		mControlJPanel.setLayout (new GridBagLayout ());
		//mControlJPanel.setPreferredSize (new Dimension (Constats.MAIN_WIDTH, Constats.MAIN_HEIGHT / 4));

		controlPanelComponentModification ();

		addComponentToPanel (mJPanel, mBackBtn,      0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mJPanel, mGameStartBtn, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void controlPanelComponentModification ()
	{
		mGameStartBtn.setPreferredSize (new Dimension (100, 50));
		mBackBtn.setPreferredSize (new Dimension (100, 50));
	}


	private void initializeComponents ()
	{
		mJFrame = new JFrame ();

		mJPanel             = new JPanel ();
		mGroupsPanel        = new JPanel ();
		mControlJPanel      = new JPanel ();
		mGlassPanel 		= new GroupPanelLines (null);
		mGroupsScrollPane   = new JScrollPane (mGroupsPanel);

		mGameStartBtn   	= new JButton ("Start Game");
		mNextStageBtn   	= new JButton ("Next Stage");
		mBackBtn        	= new JButton ("Back");
	}

	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void addGroupsToPanelAndUpdateFrame ()
	{
		addGroupsToMainPanelAndLinkGroupWithPlayer ();
	}

	private void createGroupLines ()
	{
		mGlassPanel	= new GroupPanelLines (mGroupsPanels);
	}


	private void setCurrentPlayingGroup ()
	{
		mCurrentPlayingGroupNumber = mMatchController.getCurrentPlayingGroupNumber ();
		hideCurrentPlayingGroupPanelForAllGroups ();
		setCurrentPlayingGroup (mPlayerGroupsMap.get (mCurrentPlayingGroupNumber).get (0), true);
	}


	private void setCurrentPlayingGroup (Integer firstPlayerId, boolean state)
	{
		try
		{
			mMatchController.getPlayerObjectById (firstPlayerId).getDisplayGroupPanel ().setCurretPlayingGroup (state);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	private void hideCurrentPlayingGroupPanelForAllGroups ()
	{
		for (Object o : mPlayerGroupsMap.entrySet ())
		{
			Map.Entry pair = (Map.Entry) o;
			setCurrentPlayingGroup (((ArrayList <Integer>) pair.getValue ()).get (0), false);
		}
	}


	private void addComponentToPanel (JPanel parent, Component child, int xPos, int yPos, Insets insets, int ipady,
									  double weightx, double weighty, int gridwidth, Integer anchor, GridBagConstraints gbc, Integer fill)
	{
		if (fill != null)
			gbc.fill    = fill;

		gbc.gridx       = xPos;
		gbc.gridy       = yPos;
		gbc.insets      = insets;
		gbc.ipady       = ipady;
		gbc.weightx     = weightx;
		gbc.weighty     = weighty;
		gbc.gridwidth   = gridwidth;

		if (anchor != null)
			gbc.anchor  = anchor;

		parent.add (child, gbc);
	}


	private void addGroupsToMainPanelAndLinkGroupWithPlayer ()
	{
		ArrayList <DisplayGroupPanel>   groups    = new ArrayList <> ();
		Levels                          levels    = new Levels (mPlayerGroupsMap.size ());
		GridBagConstraints              gbc       = new GridBagConstraints ();

		int lvlAdd = 2;
		int prevLvlFirstElPos = 2;

		for (int i = 0; i < mPlayerGroupsMap.size (); i++)      // Fill 1st lvl groups
		{
			ArrayList <Integer> playersIds = mPlayerGroupsMap.get (i);
			DisplayGroupPanel dgp = new DisplayGroupPanel (playersIds, mPlayerList);

			int weightX = (i == 0) ? 1 : 0;
			int weightY = (i == mPlayerGroupsMap.size () - 1) ? 1 : 0;

			addComponentToPanel (mGroupsPanel, dgp,   0, i * lvlAdd, new Insets (0, 0, 0, 0), 0, weightX, weightY, 1, GridBagConstraints.NORTHWEST, gbc, GridBagConstraints.HORIZONTAL);

			groups.add(dgp);

			mMatchController.setPlayersGeneratedGroupLink (playersIds, dgp);
		}

		mGroupsPanels.put(1, new ArrayList <> (groups));

		for (int i = 2; i < levels.mGroupCntOnLvls.size () + 1; i++)  // Fill Future groups // start from second lvl
		{
			groups.clear();
			lvlAdd = (int) Math. pow ((double) 2, (double) i);

			if (i != 2)
				prevLvlFirstElPos *= 2;

			int position = prevLvlFirstElPos;

			for (int j = 0; j < levels.mGroupCntOnLvls.get(i); j++)
			{
				if (j > 0)
					position += lvlAdd;

				DisplayGroupPanel dgp = new DisplayGroupPanel ();

				addComponentToPanel (mGroupsPanel, dgp,   i - 1, position - 1, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.NORTHWEST, gbc, GridBagConstraints.HORIZONTAL);

				groups.add(dgp);
			}

			mGroupsPanels.put(i, new ArrayList <> (groups));
		}
	}


	public void setVisibility (boolean visibilityFlag)
	{
		mJFrame.setVisible (visibilityFlag);
	}


	public void displayWinnerPanelInGroup (PlayerObject winner)
	{
		setCurrentPlayingGroup ();
		showWinner (winner);
	}


	public void displayWinnerAndNextStage (PlayerObject winner)
	{
		hideCurrentPlayingGroupPanelForAllGroups ();
		showWinner (winner);
		mGameStartBtn.setVisible (false);
		mNextStageBtn.setVisible (true);
	}


	private void showWinner (PlayerObject winner)
	{
		try
		{
			mMatchController.getPlayerObjectById (winner.mId).getDisplayGroupPanel ().showWinner (winner.mName);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	public void destroy ()
	{
		mJFrame.dispose ();
	}


	private void addComponentsListener ()
	{
		mGameStartBtn.  addActionListener (e -> mMatchController.runActionsAfterGroupDisplay ());
		mBackBtn.       addActionListener (e -> mMatchController.openGameManagerGuiForm ());
//		mNextStageBtn.addActionListener (new ActionListener ()
//		{
//			@Override
//			public void actionPerformed (ActionEvent e)
//			{
//				mMatchController.nextStageTrigger ();
//			}
//		});
	}
}
