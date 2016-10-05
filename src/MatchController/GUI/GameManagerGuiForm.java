package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Constats;
import MatchController.Objects.PlayerObject;
import MenuGui.ImagedPanel;
import Tools.ImageLoader;
import Tools.ImageViewport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

// TODO Set BackGround of mPanel as dartBoard with two side windows
// TODO set Board at the middle
// TODO set font + underline
// TODO change font of table
// TODO change button style
// TODO thing on input style
// TODO Borders of table hide
// TODO Create check on name twin (name should be UNIQUE)

/**
 * Created by vladislavs on 06.09.2016..
 */
public class GameManagerGuiForm
{
	private final String            COLUMN_ID      = "Id";
	private final String            COLUMN_NAME    = "Name";

	private final MatchController mMatchController;

	private JFrame mJFrame;

	private ImagedPanel             mJPanel;
	private JPanel                  mTableJPanel;
	private JPanel                  mControlJPanel;

	private JTable                  mPlayerTable;
	private JTextField              mPlayerNameTxtField;
	private JButton                 mPlayerAddBtn;
	private JButton                 mMatchStartBtn;


	private JScrollPane             mPlayerTableJScrollPane;
	private JTextField              mPlayersInGroupTxtField;
	private JButton                 mBackButton;
	private Image                   mBackGroundImage;

	private JLabel                  mPlayerInGroupCntLabel;
	private JLabel                  mPlayerNameLabel;

	private String                  mPlayerNameTxtFieldDefaultValue;
	private String []               mPlayerTableHeaders;
	private Object [][]             mPlayerTableData;
	private DefaultTableModel       mDefaultTableModel;


	public GameManagerGuiForm (MatchController matchController)
	{
		mBackGroundImage = ImageLoader.getImage (Constats.OPEN_BOARD_PIC);
		mMatchController = matchController;
		initializeVariables ();
		initializeComponents ();
		addComponentsListeners ();
		buildMainFrame ();
	}


	private void mainPanelBuilder ()
	{
		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		mJPanel.setLayout (new GridBagLayout ());
		mJPanel.setPreferredSize (new Dimension (Constats.MAIN_WIDTH, Constats.MAIN_HEIGHT));
		mJPanel.setBackground (Color.BLUE);

		controlPanelBuilder ();
		tablePanelBuilder ();

		addComponentToPanel (mJPanel, mControlJPanel,   0, 0, new Insets (67, 35, 0, 0), 0, 0, 0, 1, GridBagConstraints.NORTHWEST, mPanelGbc);
		addComponentToPanel (mJPanel, mTableJPanel,     1, 0, new Insets (144, 245, 0, 10), 0, 1, 1, 1, GridBagConstraints.NORTHWEST, mPanelGbc);
	}


	private void tablePanelBuilder ()
	{
		GridBagConstraints tablePanelGbc = new GridBagConstraints ();
		mTableJPanel.setLayout (new GridBagLayout ());
		mPlayerTableJScrollPane = new JScrollPane (mPlayerTable);
		mTableJPanel.setBackground (new Color (255, 255, 255, 0));
		mTableJPanel.setOpaque (false);

		newPlayerTableInitialization ();
		setTableStyle ();

		addComponentToPanel (mTableJPanel, mPlayerTableJScrollPane, 0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 0, GridBagConstraints.CENTER, tablePanelGbc);
	}


	private void controlPanelBuilder ()
	{
		GridBagConstraints ctrPanelGbc = new GridBagConstraints ();
		mControlJPanel.setLayout (new GridBagLayout ());
		mControlJPanel.setBackground (new Color (255, 255, 255 , 0));
		mControlJPanel.setOpaque (false);

		controlPanelComponentStyling ();

		addComponentToPanel (mControlJPanel, mPlayerInGroupCntLabel,  0, 1, new Insets (0,   5, 5, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc);
		addComponentToPanel (mControlJPanel, mPlayersInGroupTxtField, 0, 2, new Insets (0,   5, 0, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc);
		addComponentToPanel (mControlJPanel, mPlayerNameLabel,        0, 3, new Insets (15,   5, 5, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc);
		addComponentToPanel (mControlJPanel, mPlayerNameTxtField,     0, 4, new Insets (0,   5, 5, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc);
		addComponentToPanel (mControlJPanel, mPlayerAddBtn,           1, 5, new Insets (2,   5, 0, 5), 0, 0, 0, 1, GridBagConstraints.CENTER, ctrPanelGbc);
		addComponentToPanel (mControlJPanel, mBackButton,             0, 7, new Insets (5,   5, 5, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc);
		addComponentToPanel (mControlJPanel, mMatchStartBtn,          0, 6, new Insets (110, 5, 0, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc);
	}


	private void controlPanelComponentStyling ()
	{
		mPlayersInGroupTxtField.setEditable (false);
		mPlayersInGroupTxtField.setText ("2");

		mPlayerNameTxtField.setText (mPlayerNameTxtFieldDefaultValue);

		mPlayerAddBtn.setText ("Add player");
		mPlayerAddBtn.setPreferredSize (new Dimension (32, 25));

		mBackButton.setText ("Back");
		mBackButton.setPreferredSize (new Dimension (25, 25));

		mMatchStartBtn.setText ("Start Match");
		mMatchStartBtn.setPreferredSize (new Dimension (25, 25));
	}


	private void buildMainFrame ()
	{
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.setContentPane (mJPanel);
		//mJFrame.setResizable (false);
		setMJFrameLocation ();

		mainPanelBuilder ();

		mJFrame.pack ();
		mJFrame.setVisible (true);
		setEntryComponentFocus ();
	}


	private void initializeComponents ()
	{
		mJFrame                 = new JFrame ();

		mJPanel                 = new ImagedPanel (mBackGroundImage);
		mControlJPanel          = new JPanel ();
		mTableJPanel            = new JPanel ();

		mPlayerTable            = new JTable ();
		mPlayerTableJScrollPane = new JScrollPane (mPlayerTable);

		mPlayerAddBtn           = new JButton ();
		mMatchStartBtn          = new JButton ();
		mBackButton             = new JButton ();

		mPlayerInGroupCntLabel  = new JLabel ("Number of players in group: ");
		mPlayerNameLabel        = new JLabel ("Name");

		mPlayersInGroupTxtField = new JTextField ();
		mPlayerNameTxtField     = new JTextField ();
	}


	private void addComponentToPanel (JPanel parent, Component child, int xPos, int yPos, Insets insets, int ipady,
	                                  double weightx, double weighty, int gridwidth, int anchor, GridBagConstraints gbc)
	{
		gbc.fill        = GridBagConstraints.HORIZONTAL;
		gbc.gridx       = xPos;
		gbc.gridy       = yPos;
		gbc.insets      = insets;
		gbc.ipady       = ipady;
		gbc.weightx     = weightx;
		gbc.weighty     = weighty;
		gbc.gridwidth   = gridwidth;
		gbc.anchor      = anchor;

		parent.add (child, gbc);
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void setEntryComponentFocus ()
	{
		mJPanel.requestFocus ();
	}


	private void initializeVariables ()
	{
		mPlayerTableHeaders = new String[] {COLUMN_ID, COLUMN_NAME, Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID};
		mPlayerNameTxtFieldDefaultValue = "Enter New Player Name ...";
	}


	private ArrayList<PlayerObject> getPlayersFromTable ()
	{
		ArrayList <PlayerObject> returnPlayerList = new ArrayList <> ();
		Vector tableData = mDefaultTableModel.getDataVector ();

		try
		{
			for (Object rowData : tableData)
				returnPlayerList.add (getPlayerObjectNewInstance ((Vector) rowData));
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		return returnPlayerList;
	}


	private PlayerObject getPlayerObjectNewInstance (Vector rowData)
	{
		try
		{
			String playerName = (String) rowData.get (getColumnNumberByName (COLUMN_NAME));
			String playerIdString = (String) rowData.get (getColumnNumberByName (COLUMN_ID));
			Integer playerId = Integer.parseInt (playerIdString);

			return new PlayerObject (playerName, playerId);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		return null;
	}


	private int getColumnNumberByName (String columnName)
	{
		int columnNumber = 0;
		for (String cName : mPlayerTableHeaders)
		{
			if (cName.equals (columnName))
				return columnNumber;

			columnNumber++;
		}

		return -1;
	}


	private void setTableStyle ()
	{
		mPlayerTable.setForeground(Color.BLACK);
		mPlayerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mPlayerTable.setOpaque(false);
		mPlayerTable.setBackground (new Color (255, 255, 255, 0));
		mPlayerTable.setOpaque (false);

		mPlayerTableJScrollPane.setPreferredSize (new Dimension (161, 275));
		mPlayerTableJScrollPane.setBackground (new Color (255, 255, 255, 0));
		mPlayerTableJScrollPane.getViewport ().setOpaque (false);
	}


	private boolean isTableInnerButton (int column, boolean hackFlag)
	{
		int hackInt = 0;
		if (hackFlag)
			hackInt = 1;

		//TODO resolve issue based on hidden column
		return mPlayerTable.getColumnName (column - hackInt).equals (Constats.DELETE_BTN_ID) ||
				mPlayerTable.getColumnName (column - hackInt).equals (Constats.EDIT_BTN_ID);
	}


	private String getEditedText (String oldTxt)
	{
		JFrame frame = new JFrame();
		String result = JOptionPane.showInputDialog (frame, "Enter new name:");

		return  (result.isEmpty ()) ? oldTxt : result;
	}


	private void addNewPlayer (String Name)
	{
		// Players Id`s are connected with data model size (to get last id we need to get data model size)
		int lastInsertedId = mDefaultTableModel.getRowCount ();

		mDefaultTableModel.addRow (new String [] {String.valueOf (lastInsertedId), Name,
				Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID});
	}


	private void addNewPlayer ()
	{
		String playerName = mPlayerNameTxtField.getText ();
		if (mPlayerNameTxtFieldDefaultValue.equals (playerName) || playerName.isEmpty ())
			return;

		addNewPlayer (playerName);
		mPlayerNameTxtField.setText (mPlayerNameTxtFieldDefaultValue);
	}


	protected void editNewPlayerInTable ()
	{
		int selectedRow     = mPlayerTable.getSelectedRow ();
		int selectedColumn  = mPlayerTable.getColumn (COLUMN_NAME).getModelIndex ();

		if (mDefaultTableModel.getRowCount () == 0)
			return;

		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog (null, "Please select player in the table to Edit.");
			return;
		}

		String currentCellValue = (String) mDefaultTableModel.getValueAt (selectedRow, selectedColumn);
		mDefaultTableModel.setValueAt (getEditedText (currentCellValue), selectedRow, selectedColumn);
	}


	protected void deleteNewPlayerFromTable ()
	{
		int selectedRow = mPlayerTable.getSelectedRow ();

		if (mDefaultTableModel.getRowCount () == 0)
			return;

		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog (null, "Please select player in the table to delete.");
			return;
		}

		if (isDeleteConfirmed ())
			mDefaultTableModel.removeRow (selectedRow);
	}


	private boolean isDeleteConfirmed ()
	{
		int result = JOptionPane.showConfirmDialog (null, "Are you sure, you want to delete player?",
		                                            "alert", JOptionPane.OK_CANCEL_OPTION);
		return result == 0;
	}


	public void setVisibility (boolean visibilityFlag)
	{
		mJFrame.setVisible (visibilityFlag);
	}


	//TODO create separate model class
	//TODO same to table
	private void newPlayerTableInitialization ()
	{
		mDefaultTableModel = new DefaultTableModel ()
		{
			public boolean isCellEditable (int row, int column)
			{
				return isTableInnerButton (column, true);
			}
		};

		mDefaultTableModel.setDataVector (mPlayerTableData, mPlayerTableHeaders);

		if (mMatchController.getPlayerList () != null)
			populateTableModelWithOldPlayers (mMatchController.getPlayerList ());

		mPlayerTable.setModel (mDefaultTableModel);

		mPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellRenderer (new ButtonRenderer ());
		mPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellRenderer (new ButtonRenderer ());

		mPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellEditor (new ButtonEditor (this, new JCheckBox ()));
		mPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellEditor (new ButtonEditor (this, new JCheckBox ()));

		mPlayerTable.removeColumn (mPlayerTable.getColumn ("Id"));    // To hide Id Column

		mPlayerTable.getColumnModel ().getColumn (0).setPreferredWidth (118);
		mPlayerTable.getColumnModel ().getColumn (1).setPreferredWidth (20);
		mPlayerTable.getColumnModel ().getColumn (2).setPreferredWidth (20);

		//mPlayerTable.getTableHeader ().setReorderingAllowed (false);
		//mPlayerTable.getTableHeader ().setResizingAllowed (false);
	//	mPlayerTable.getTableHeader ().setBackground(new Color(0,0,0,0.6f));
		mPlayerTable.getTableHeader ().setVisible (false);
		mPlayerTable.getTableHeader ().setOpaque (false);
		mPlayerTable.getTableHeader ().setBackground(new Color(255,255,255,0));
	}


	private void populateTableModelWithOldPlayers (ArrayList <PlayerObject> playerObjectArrayList)
	{
		for (PlayerObject playerObject : playerObjectArrayList)
			mDefaultTableModel.addRow (new String [] {String.valueOf (playerObject.mId), playerObject.mName, Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID});
	}


	private boolean isPlayersInGroupNumberCorrect ()
	{
		String digitRegEx = "[0-9]+";
		String playersInGroup = mPlayersInGroupTxtField.getText ();

		return playersInGroup.matches (digitRegEx);
	}


	private void tryToRegisterPlayers ()
	{
		ArrayList <PlayerObject> createdPlayers = getPlayersFromTable ();

		if (! isPlayersInGroupNumberCorrect ())
		{
			JOptionPane.showMessageDialog (null, "Number of players in group is incorrect.");
			return;
		}

		Integer playersNumberInGroup = Integer.parseInt (mPlayersInGroupTxtField.getText ());

		if (createdPlayers.size () < playersNumberInGroup)
		{
			JOptionPane.showMessageDialog (null, "Number of players have to be at least " + playersNumberInGroup + ".");
			return;
		}

		mMatchController.runActionsAfterPlayerRegistration (playersNumberInGroup, createdPlayers);
	}


	private void addComponentsListeners ()
	{
		mPlayerNameTxtField.addFocusListener (new FocusAdapter ()
		{
			@Override
			public void focusGained (FocusEvent e)
			{
				String currentTxtFieldValue = mPlayerNameTxtField.getText ();
				if(currentTxtFieldValue.equals (mPlayerNameTxtFieldDefaultValue))
					mPlayerNameTxtField.setText ("");
			}


			@Override
			public void focusLost (FocusEvent e)
			{
				if (mPlayerNameTxtField.getText ().length () == 0)
					mPlayerNameTxtField.setText (mPlayerNameTxtFieldDefaultValue);
			}
		});

		mBackButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				mMatchController.openMenuGuiForm ();
			}
		});

		mMatchStartBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				tryToRegisterPlayers ();
			}
		});

		mPlayerAddBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				addNewPlayer ();
			}
		});

		mPlayerNameTxtField.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_ENTER)
				{
					addNewPlayer ();
					mPlayerNameTxtField.setText ("");
				}
			}
		});

		mPlayerTable.addMouseMotionListener (new MouseMotionAdapter ()
		{
			@Override
			public void mouseMoved (MouseEvent e)
			{
				int columnIndex = mPlayerTable.columnAtPoint (e.getPoint());
				if (isTableInnerButton (columnIndex, false))
					mPlayerTable.setCursor (new Cursor (Cursor.HAND_CURSOR));
				else
					mPlayerTable.setCursor (new Cursor (Cursor.DEFAULT_CURSOR));
			}
		});


		mPlayerTable.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_DELETE)
					deleteNewPlayerFromTable ();
			}
		});
	}

	public void destroy ()
	{
		mJFrame.dispose ();
	}
}
