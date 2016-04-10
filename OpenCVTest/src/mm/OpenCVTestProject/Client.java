package mm.OpenCVTestProject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Client {

	static{System.load(new File("C:\\OpenCV\\opencv\\build\\java\\x64\\opencv_java300.dll").getAbsolutePath());}

	private JFrame frame;
	private JLabel imageLabel;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	// changed
	int width = (int)screenSize.getWidth();	// changed
	int height = (int)screenSize.getHeight();	// changed

	Socket server ;
	InputStream in ;
	static Image image = null;


	public static void main (String args[]) 
	{

		try {
			Client c = new Client();
			c.initGUI();
			Thread.sleep(1000);
			c.runMainLoop();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	private void initGUI() {

		frame = new JFrame("Kiko Web Camera");
		frame.setSize(width, height);	// changed
		imageLabel = new JLabel();
		frame.add(imageLabel);
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void runMainLoop() {
		try
		{
			server = new Socket("localhost",8085);
			in = server.getInputStream();

			while(true)
			{
				image = ImageIO.read(in);			
				ImageIcon imageIcon = new ImageIcon(image, "Captured video");
				imageLabel.setIcon(imageIcon);
				frame.pack();
			}
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


}
