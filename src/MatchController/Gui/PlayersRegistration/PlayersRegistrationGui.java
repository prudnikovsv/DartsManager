package MatchController.Gui.PlayersRegistration;

import BaseAbstractClasses.DartsGuiFormBase;
import GuiComponents.MenuButton;
import MatchController.Gui.Components.ButtonEditor;
import MatchController.Gui.Components.ButtonRenderer;
import MatchController.Gui.Components.TableScrollBar;
import MatchController.MatchController;
import Constants.Constats;
import MatchController.Objects.PlayerObject;
import GuiComponents.ImagedPanel;
import Tools.ImageLoader;
import Tools.ImageViewport;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

abstract class PlayersRegistrationGui extends DartsGuiFormBase
{
	protected abstract void                     initializePlayerTableDataModel  ();
	protected abstract ArrayList<PlayerObject>  getPlayersFromTable             ();
	protected abstract PlayerObject             getPlayerObjectNewInstance      (Vector rowData);
	protected abstract boolean                  isTableInnerButton              (int column, boolean hackFlag);
	protected abstract void                     tryToRegisterPlayers            ();
	protected abstract void                     addNewPlayer                    ();
	protected abstract void                     deleteNewPlayerFromTable        ();
	protected abstract PlayersRegistration      getPlayerRegistrationObject     ();


	private final String            COLUMN_ID                       = "Id";
	private final String            COLUMN_NAME                     = "Name";
	private final String            mPlayerNameTxtFieldDefaultValue = "Enter New Player Name ...";

	private JPanel                  mTableJPanel;
	private JPanel                  mControlJPanel;

	private JTable                  mPlayerTable;

	private JScrollPane             mPlayerTableJScrollPane;

	private JTextField              mPlayersInGroupTxtField;
	private JTextField              mPlayerNameTxtField;

	private JButton                 mPlayerAddBtn;
	private JButton                 mMatchStartBtn;
	private JButton                 mBackButton;

	private JLabel                  mPlayerLooseCntLabel;
	private JLabel                  mPlayerNameLabel;


	PlayersRegistrationGui (MatchController matchController)
	{
		super (matchController);
		setEntryComponentFocus ();
	}


	@Override
	protected void initializeComponents ()
	{
		setMainJPanel (new ImagedPanel (ImageLoader.getImage (Constats.PL_OPEN_BOARD_PIC)));

		mControlJPanel          = new JPanel ();
		mTableJPanel            = new JPanel ();

		mPlayerTable            = new JTable ();
		mPlayerTableJScrollPane = new JScrollPane (mPlayerTable);

		mPlayerAddBtn           = new MenuButton ("Add player");
		mMatchStartBtn          = new MenuButton ("Start Match");
		mBackButton             = new MenuButton ("Back");

		mPlayerLooseCntLabel    = new JLabel ("Loose Count");
		mPlayerNameLabel        = new JLabel ("Name");

		mPlayersInGroupTxtField = new JTextField ();
		mPlayerNameTxtField     = new JTextField ();
	}


	@Override
	protected void addComponentsListener ()
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

		mBackButton.addActionListener (e -> getMatchController ().openMenuAndDestroyPlayerRegistration ());

		mMatchStartBtn.addActionListener (e -> tryToRegisterPlayers ());

		mPlayerAddBtn.addActionListener (e -> addNewPlayer ());

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


	@Override
	protected void buildMainPanel ()
	{
		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		getMainJPanel ().setLayout (new GridBagLayout ());
		getMainJPanel ().setPreferredSize (new Dimension (Constats.MAIN_WIDTH, Constats.MAIN_HEIGHT));

		buildControlPanel ();
		buildTablePanel ();

		addComponentToPanel (getMainJPanel (), mControlJPanel,   0, 0, new Insets (210,  35, 0,  0), 0, 0, 0, 1, GridBagConstraints.NORTHWEST, mPanelGbc, null);
		addComponentToPanel (getMainJPanel (), mTableJPanel,     1, 0, new Insets (282, 245, 0, 10), 0, 1, 1, 1, GridBagConstraints.NORTHWEST, mPanelGbc, null);
	}


	private void buildControlPanel ()
	{
		GridBagConstraints ctrPanelGbc = new GridBagConstraints ();
		mControlJPanel.setLayout (new GridBagLayout ());
		mControlJPanel.setBackground (new Color (255, 255, 255 , 0));
		mControlJPanel.setOpaque (false);

		controlPanelComponentStyling ();

		addComponentToPanel (mControlJPanel, mPlayerLooseCntLabel, 0, 1, new Insets (0, 5, 5, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc, null);
		addComponentToPanel (mControlJPanel, mPlayersInGroupTxtField, 0, 2, new Insets (0,   5, 10, 5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc, null);
		addComponentToPanel (mControlJPanel, mPlayerNameLabel,        0, 3, new Insets (15,  5, 5,  5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc, null);
		addComponentToPanel (mControlJPanel, mPlayerNameTxtField,     0, 4, new Insets (0,   5, 5,  5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc, null);
		addComponentToPanel (mControlJPanel, mPlayerAddBtn,           1, 5, new Insets (2,   5, 0,  5), 0, 0, 0, 1, GridBagConstraints.CENTER, ctrPanelGbc, null);
		addComponentToPanel (mControlJPanel, mBackButton,             0, 7, new Insets (5,   5, 5,  5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc, null);
		addComponentToPanel (mControlJPanel, mMatchStartBtn,          0, 6, new Insets (100, 5, 0,  5), 0, 0, 0, 2, GridBagConstraints.CENTER, ctrPanelGbc, null);
	}


	private void controlPanelComponentStyling ()
	{
		Font scratchFont = new Font ("Nail Scratch", Font.TRUETYPE_FONT, 12);

		mPlayerLooseCntLabel.setForeground (Color.white);
		mPlayerLooseCntLabel.setFont (scratchFont);

		mPlayersInGroupTxtField.setEditable (false);
		mPlayersInGroupTxtField.setText ("2");
		mPlayersInGroupTxtField.setFont (scratchFont);
		mPlayersInGroupTxtField.setBorder (new EmptyBorder (0, 0, 0, 0));
		mPlayersInGroupTxtField.setBackground (new Color (255, 255, 255, 0));
		mPlayersInGroupTxtField.setForeground (Color.WHITE);
		mPlayersInGroupTxtField.setOpaque (false);

		mPlayerNameLabel.setForeground (Color.white);
		mPlayerNameLabel.setFont (scratchFont);

		mPlayerNameTxtField.setText (mPlayerNameTxtFieldDefaultValue);
		mPlayerNameTxtField.setFont (scratchFont);
		mPlayerNameTxtField.setBorder (new EmptyBorder (0, 0, 0, 0));
		mPlayerNameTxtField.setBackground (new Color (255, 255, 255, 0));
		mPlayerNameTxtField.setForeground (Color.WHITE);
		mPlayerNameTxtField.setOpaque (false);

		mPlayerAddBtn.setPreferredSize (new Dimension (150, 25));
		mBackButton.setPreferredSize (new Dimension (150, 25));
		mMatchStartBtn.setPreferredSize (new Dimension (150, 25));

		mPlayerAddBtn.setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC_150x25)));
		mPlayerAddBtn.setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC_150x25)));

		mBackButton.setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC_150x25)));
		mBackButton.setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC_150x25)));

		mMatchStartBtn.setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC_150x25)));
		mMatchStartBtn.setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC_150x25)));
	}


	private void buildTablePanel ()
	{
		GridBagConstraints tablePanelGbc = new GridBagConstraints ();
		mTableJPanel.setLayout (new GridBagLayout ());
		mPlayerTableJScrollPane = new JScrollPane (mPlayerTable);
		mTableJPanel.setBackground (new Color (255, 255, 255, 0));
		mTableJPanel.setOpaque (false);

		initializePlayerTableDataModel ();
		setTableStyle ();

		addComponentToPanel (mTableJPanel, mPlayerTableJScrollPane, 0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 0, GridBagConstraints.CENTER, tablePanelGbc, null);
	}


	private void setPlayersTableDefaultRenderer ()
	{
		mPlayerTable.setDefaultRenderer (Object.class, new DefaultTableCellRenderer ()
		{
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
				return this;
			}
		});
	}


	private void setTableStyle ()
	{
		Color transparentColor = new Color (255, 255, 255 ,0);

		setPlayersTableDefaultRenderer ();

		mPlayerTableJScrollPane.setViewport                             (new ImageViewport (ImageLoader.getImage (Constats.TABLE_BOARD_PIC)));
		mPlayerTableJScrollPane.setViewportView                         (mPlayerTable);
		mPlayerTableJScrollPane.setBorder                               (new EmptyBorder (0, 0, 0, 0));
		mPlayerTableJScrollPane.getVerticalScrollBar().setUI            (new TableScrollBar ());
		mPlayerTableJScrollPane.getVerticalScrollBar().setBackground    (Color.GRAY);
		mPlayerTableJScrollPane.getVerticalScrollBar().setPreferredSize (new Dimension(10, 0));

		mPlayerTableJScrollPane.setHorizontalScrollBarPolicy    (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mPlayerTableJScrollPane.setPreferredSize                (new Dimension (161, 275));

		mPlayerTable.setFont                    (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 9));
		mPlayerTable.setForeground              (Color.WHITE);
		mPlayerTable.setAutoResizeMode          (JTable.AUTO_RESIZE_OFF);
		mPlayerTable.setOpaque                  (false);
		mPlayerTable.setBackground              (transparentColor);
		mPlayerTable.setShowGrid                (false);
		mPlayerTable.setSelectionBackground     (transparentColor);
		mPlayerTable.setSelectionForeground     (Color.WHITE);
		mPlayerTable.setBorder                  (new EmptyBorder (0, 0, 0, 0));
		mPlayerTable.setTableHeader             (null);
		mPlayerTable.getColumn                  (Constats.DELETE_BTN_ID).setCellRenderer    (new ButtonRenderer ());
		mPlayerTable.getColumn                  (Constats.EDIT_BTN_ID  ).setCellRenderer    (new ButtonRenderer ());
		mPlayerTable.getColumn                  (Constats.DELETE_BTN_ID).setCellEditor      (new ButtonEditor (getPlayerRegistrationObject (), new JCheckBox ()));
		mPlayerTable.getColumn                  (Constats.EDIT_BTN_ID  ).setCellEditor      (new ButtonEditor (getPlayerRegistrationObject (), new JCheckBox ()));
		mPlayerTable.removeColumn               (mPlayerTable.getColumn (COLUMN_ID));    // To hide Id Column
		mPlayerTable.setRowHeight               (20);
		mPlayerTable.setIntercellSpacing        (new Dimension (5, 0));

		mPlayerTable.getColumnModel ().getColumn (0).setPreferredWidth (100);
		mPlayerTable.getColumnModel ().getColumn (1).setPreferredWidth (25);
		mPlayerTable.getColumnModel ().getColumn (2).setPreferredWidth (25);
	}


	private void setEntryComponentFocus ()
	{
		getMainJPanel ().requestFocus ();
	}


	String getCOLUMN_ID ()
	{
		return COLUMN_ID;
	}


	String getCOLUMN_NAME ()
	{
		return COLUMN_NAME;
	}


	String getPlayerNameTxtFieldDefaultValue ()
	{
		return mPlayerNameTxtFieldDefaultValue;
	}


	JTable getPlayersTable ()
	{
		return mPlayerTable;
	}


	JTextField getPlayersInGroupTxtField ()
	{
		return mPlayersInGroupTxtField;
	}


	JTextField getPlayerNameTxtField ()
	{
		return mPlayerNameTxtField;
	}
}
