package MenuGui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 26.09.2016..
 */
public class test
{
	private JPanel mJPanel;
	private JFrame mJFrame;

	public static void main (String[] args)
	{
		new test ();
	}


	public test ()
	{
		mJFrame = new JFrame ("test");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);

		mJPanel.setLayout (new FlowLayout ());

		JPanel p = new JPanel ();
		p.setLayout (new FlowLayout ());
		p.setPreferredSize (new Dimension (200, 100));
		p.setBackground (Color.BLUE);

		JLayeredPane lp = new JLayeredPane ();
		lp.setOpaque (true);
		lp.setBackground (Color.RED);
		lp.setPreferredSize(new Dimension(80, 50));
		lp.setBorder(BorderFactory.createLineBorder(Color.green));

		JPanel bg = new JPanel ();
		bg.setBackground (Color.darkGray);
		bg.setBounds (0, 0, 20, 20);

		lp.add (bg, 0);

		JLayeredPane c = new JLayeredPane ();
		c.setBackground (Color.CYAN);
		c.setOpaque (true);
		//c.setPreferredSize(new Dimension(80, 50));
		c.setBounds (10, 10, 60, 30);
		//c.setBorder(BorderFactory.createLineBorder(Color.green));

		JPanel btnBg = new JPanel ();
		btnBg.setBackground (Color.green);
		btnBg.setBounds (0, 0, 50, 20);
		c.add (btnBg, 1);

		lp.add (c, 1);

		//lp.add(new JButton (), new Integer(50));
		//lp.add(new JButton (), new Integer(100));

		p.add (lp);
		lp.setBorder(BorderFactory.createLineBorder(Color.green));

		mJPanel.add (p);

		mJFrame.pack ();
		mJFrame.setVisible (true);

		//JPanel f = new JPanel ();
	//	f.setLayout (new BorderLayout ());
		//f.add (new MenuOptionComponent ());


	}
}
