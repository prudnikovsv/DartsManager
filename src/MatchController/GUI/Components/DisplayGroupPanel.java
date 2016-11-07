package MatchController.GUI.Components;

import Constants.Constats;
import MatchController.Objects.PlayerObject;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vladislavs on 13.09.2016..
 */

// TODO set better style
// TODO set preferred size to mPanel
// TODO refactor

public class DisplayGroupPanel extends JPanel
{
	private JPanel mFirstPlayerPanel   = new JPanel ();
	private JPanel mSecondPlayerPanel  = new JPanel ();
	private JPanel mVersusPanel        = new JPanel ();
	private JPanel mPlayingTxtPanel    = new JPanel ();

	private JLabel mNameLabel          = new JLabel ();
	private JLabel mSNameLabel         = new JLabel ();

	private JLabel mPlayingLabel       = new JLabel ("Group to play!");
	private JLabel vsLabel             = new JLabel ("VS");
	private JLabel lArrow;
	private JLabel rArrow;

	private int mRow;
	private int mColumn;
	private double mWeightX;
	private double mWeightY;


	public DisplayGroupPanel (ArrayList<PlayerObject> mPlayerList)
	{
		initialization (mPlayerList.get (0).mName, mPlayerList.get (1).mName);
	}


	public DisplayGroupPanel (ArrayList<PlayerObject> mPlayerList, int row, int column, double weightX, double weightY)
	{
		this (mPlayerList);

		mRow        = row;
		mColumn     = column;
		mWeightX    = weightX;
		mWeightY    = weightY;
	}


	public DisplayGroupPanel ()
	{
		initialization ("?", "?");
	}


	public DisplayGroupPanel (int row, int column, double weightX, double weightY)
	{
		this ();

		mRow        = row;
		mColumn     = column;
		mWeightX    = weightX;
		mWeightY    = weightY;
	}


	private void initialization (String fPlayer, String sPlayer)
	{
		setMainPanelLayout ();
		setPanelsLayout ();
		panelStyling ();
		labelStyling ();
		setPlayersNames (fPlayer, sPlayer);
		buildPanel ();
	}


	private void setMainPanelLayout ()
	{
		GroupLayout groupLayout = new GroupLayout (this);
		this.setLayout (groupLayout);

		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		groupLayout.setHorizontalGroup
		(
				groupLayout.createSequentialGroup ()
						.addComponent (mFirstPlayerPanel)
						.addGroup
						(
								groupLayout.createParallelGroup (GroupLayout.Alignment.LEADING)
								           .addComponent (mPlayingTxtPanel)
								           .addComponent (mVersusPanel)
						)
						.addComponent (mSecondPlayerPanel)

		);
		groupLayout.setVerticalGroup
		(
				groupLayout.createSequentialGroup ()
						.addComponent (mPlayingTxtPanel)
						.addGroup
						(
								groupLayout.createParallelGroup (GroupLayout.Alignment.BASELINE)
								          .addComponent (mFirstPlayerPanel)
								          .addComponent (mVersusPanel)
								          .addComponent (mSecondPlayerPanel)
						)
		);
	}


	private void buildPanel ()
	{
		mVersusPanel        .add (lArrow);
		mPlayingTxtPanel    .add (mPlayingLabel);
		mVersusPanel        .add (vsLabel);
		mVersusPanel        .add (rArrow);
		mFirstPlayerPanel   .add (mNameLabel);
		mSecondPlayerPanel  .add (mSNameLabel);
	}


	private void setPanelsLayout ()
	{
		mVersusPanel        .setLayout (new GridLayout (1, 3, -1, -1));
		mFirstPlayerPanel   .setLayout (new GridLayout (1, 2, -1, -1));
		mSecondPlayerPanel  .setLayout (new GridLayout (1, 2, -1, -1));
		mPlayingTxtPanel    .setLayout (new GridLayout (1, 2, -1, -1));
	}


	private void setPlayersNames (String fPlayer, String sPlayer)
	{
		mNameLabel.setText (fPlayer);
		mSNameLabel.setText (sPlayer);
	}


	public void setPlayerName (String playerName)
	{
		if (mNameLabel.getText ().equals ("?"))
			mNameLabel.setText (playerName);
		else
			mSNameLabel.setText (playerName);
	}


	private void labelStyling ()
	{
		vsLabel         .setHorizontalAlignment (SwingConstants.CENTER);
		mPlayingLabel   .setHorizontalAlignment (SwingConstants.CENTER);

		mNameLabel  .setOpaque (true);
		mSNameLabel .setOpaque (false);

		mNameLabel  .setBackground (new Color(255, 255, 255, 0));
		mSNameLabel .setBackground (new Color(255, 255, 255, 0));

		setImages ();
	}


	private void setImages ()
	{
		Image leftImage = ImageLoader.getImage(Constats.LEFT_DART_PIC);
		Icon leftIcon = new ImageIcon(leftImage);
		lArrow = new JLabel(leftIcon);

		Image rightImage = ImageLoader.getImage(Constats.RIGHT_DART_PIC);
		Icon rightIcon = new ImageIcon(rightImage);
		rArrow = new JLabel(rightIcon);
	}


	private static Color hex2Rgb (String colorStr)
	{
		// TODO Check colorStr pattern.
		return new Color (
				Integer.valueOf (colorStr.substring (1, 3), 16),
				Integer.valueOf (colorStr.substring (3, 5), 16),
				Integer.valueOf (colorStr.substring (5, 7), 16)
		);
	}


	@Override
	protected void paintComponent (Graphics g)
	{
		super.paintComponent (g);
		Graphics2D g2d = (Graphics2D) g;

		int width = getWidth ();
		int height = getHeight ();

		Color lowerColor = hex2Rgb ("#e5ffd4");
		Color upperColor = hex2Rgb ("#d4ffda");

		GradientPaint gp = new GradientPaint (3, 4, lowerColor, 3, height, upperColor);

		g2d.setRenderingHint (RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setPaint (gp);
		g2d.fillRect (0, 0, width, height);
	}


	private void panelStyling ()
	{
		this.setBorder (new EtchedBorder (EtchedBorder.LOWERED));

		mFirstPlayerPanel   .setOpaque (false);
		mSecondPlayerPanel  .setOpaque (false);
		mVersusPanel        .setOpaque (false);
		mPlayingTxtPanel    .setOpaque (false);

		mFirstPlayerPanel   .setBackground (new Color(255, 255, 255, 0));
		mSecondPlayerPanel  .setBackground (new Color(255, 255, 255, 0));
		mVersusPanel        .setBackground (new Color(255, 255, 255, 0));
		mPlayingTxtPanel    .setBackground (new Color(255, 255, 255, 0));

		mPlayingTxtPanel.setVisible (false);
	}


	public int getRow ()
	{
		return mRow;
	}


	public int getColumn ()
	{
		return mColumn;
	}


	public double getWeightX ()
	{
		return mWeightX;
	}


	public double getWeightY ()
	{
		return mWeightY;
	}


	public void setCurrentPlayingGroup (boolean visibilityState)
	{
		mPlayingTxtPanel.setVisible (visibilityState);
	}
}
