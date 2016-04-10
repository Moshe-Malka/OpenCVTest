package mm.OpenCVTestProject;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.videoio.Videoio;
import org.opencv.videoio.VideoCapture;
import mm.OpenCVTestProject.ImageProcessor;

public class App
{
	/*
	Socket socket = null;
	private final int PORT = 8085;
	*/
	
	//static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    static{System.load(new File("C:\\OpenCV\\opencv\\build\\java\\x64\\opencv_java300.dll").getAbsolutePath());}
	
    private JFrame frame;
	private JLabel imageLabel;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	// changed
	int width = (int)screenSize.getWidth();	// changed
	int height = (int)screenSize.getHeight();	// changed
	
	public static void main(String[] args) {
		try{
			App app = new App();
			app.initGUI();
			Thread.sleep(1000);
			app.runMainLoop(args);
			//app.readAndSend();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
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
	private void runMainLoop(String[] args) {		
		Mat webcamMatImage = new Mat();
		Image tempImage;
		VideoCapture capture = new VideoCapture(0);
		capture.open(0);
		capture.set(Videoio.CAP_PROP_FRAME_WIDTH,(double)width);	// changed
		capture.set(Videoio.CAP_PROP_FRAME_HEIGHT,(double)height);	// changed
		if( capture.isOpened()){
			while (true){
				capture.read(webcamMatImage);
				if( !webcamMatImage.empty() ){
					tempImage= ImageProcessor.toBufferedImage(webcamMatImage);					
					ImageIcon imageIcon = new ImageIcon(tempImage, "Captured video");
					imageLabel.setIcon(imageIcon);
					frame.pack(); //this will resize the window to fit the image					
				}
				else{
					System.out.println(" -- Frame not captured -- Break!");
					break;
				}
			}
		}
		else{
			System.out.println("Couldn't open capture.");
		}
	}

	/*
	private void readAndSend() 
	{
		VideoCapture capture2 = new VideoCapture(0);
		capture2.open(0);
		Mat tempMat = new Mat();
		RenderedImage tempImage;
		boolean exitCode=true;
		
		if (capture2.isOpened())
		{
			while (exitCode)
			{
				capture2.read(tempMat);
				if( !tempMat.empty() )
				{
					try {
					tempImage= ImageProcessor.toBufferedImage(tempMat);		
					ByteArrayOutputStream baos = new ByteArrayOutputStream();					
					ImageIO.write( tempImage, "jpg", baos );					
					baos.flush();
					byte[] imageInByteArray = baos.toByteArray();
					baos.close();
					//String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
					sendToWeb(imageInByteArray);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				else{
					System.out.println(" -- Frame not captured -- Break!");
					exitCode=false;
				}		
			}	
		}
		else
		{
			System.out.println("Couldn't open capture.");
		}	
	}

	private void sendToWeb(byte[] imageInByteArray) 
	{
		try {
			ServerSocket server = new ServerSocket(PORT);

			while(true)
			{
				System.out.println("Waiting for client...");
	            Socket client = server.accept();
	            
	            System.out.println("Client connected.");
	            OutputStream out = client.getOutputStream();

	            out.write(imageInByteArray);
			}
			//server.close();			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}