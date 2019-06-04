package p1;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class SimpleGameEngine implements KeyListener
{
	/*********************************************************************/
	/* Setup variables                                                   */
	/*********************************************************************/
	
	private final int WIDTH;
	private final int HEIGHT;
	private final String TITLE;
	
	/*********************************************************************/
	/* Graphics                                                          */
	/*********************************************************************/
	
	private final JFrame frame;
	private final Canvas canvas;
	
	/*********************************************************************/
	/* Game Loop variables                                               */
	/*********************************************************************/
	
	private boolean running = true;
	private short frameCounter = 0;
	private boolean FPSLock = true;
	private boolean showFPS = true;
	private final static float PREFERRED_FPS = 60;
	
	private short FPS = 0;
	private long elapsedTime;	
	
	/*********************************************************************/
	/* Boolean Keys                                                      */
	/*********************************************************************/
	
	private boolean escapeKey = false;	
	
	private boolean upKey = false;
	private boolean downKey = false;
	private boolean rightKey = false;
	private boolean leftKey = false;
	
	private boolean wKey = false;
	private boolean eKey = false;
	private boolean rKey = false;
	private boolean sKey = false;
	private boolean aKey = false;
	private boolean dKey = false;
	private boolean xKey = false;
	
	private boolean np2Key = false;
	private boolean np4Key = false;
	private boolean np6Key = false;
	private boolean np7Key = false;
	private boolean np8Key = false;
	private boolean np9Key = false;
	private boolean npplusKey = false;
	private boolean npminusKey = false;
	
	private boolean f1Key = false;
	private boolean f3Key = false;
	private boolean f11Key = false;
	
	/*********************************************************************/
	/* Constructor                                                       */
	/*********************************************************************/
	
	public SimpleGameEngine(int width, int height, String title) throws Exception
	{
		if(width < 10 || height < 10)
			throw new IllegalArgumentException("Resolution " + width + "x" + height + " not supported.");
		
		if(title == null)
			throw new NullPointerException("The title cannot be null.");
		
		WIDTH = width;
		HEIGHT = height;
		TITLE = title;	
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		frame.setResizable(false);
		
		canvas = new Canvas();
		canvas.setSize(width, height);
		
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);		
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		
		frame.addKeyListener(this);
		canvas.addKeyListener(this);
	
		long t0 = System.nanoTime();
		long t1 = t0;
		long start;
		
		while(running)
		{
			start = System.nanoTime();
			
			update();
			render();
			
			if(t1 >= t0 + 1000000000)
			{			
				t0 = t1;
				FPS = frameCounter;
				frameCounter = 0;
				
				if(showFPS)
					frame.setTitle(TITLE + " - " + FPS + " FPS");
			}
		
			frameCounter++;
			
			if(FPSLock)
				while(System.nanoTime() < t1 + (1 / PREFERRED_FPS) * 1000000000);
			t1 = System.nanoTime();
			elapsedTime = t1 - start;
		}
		
		frame.dispose();
	}
	
	public void update() throws Exception {}
	public void render() throws Exception {}
	
	public final Graphics getGraphics()
	{
		Graphics g = canvas.getBufferStrategy().getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		return g;
	}
	
	public final void drawGraphics()
	{
		canvas.getBufferStrategy().show();
		canvas.getBufferStrategy().getDrawGraphics().dispose();
	}
	
	public final void stop()
	{
		running = false;
	}
	
	public final void setFPSLock(boolean FPSLock)
	{
		this.FPSLock = FPSLock;
	}
	
	public final void setTitle(String title)
	{
		frame.setTitle(title);
	}
	
	/*********************************************************************/
	/* Getters                                                           */
	/*********************************************************************/
	
	public final int getWidth()
	{
		return WIDTH;
	}
	
	public final int getHeight()
	{
		return HEIGHT;
	}
	
	public final String getTitle()
	{
		return TITLE;
	}
	
	public final short getFPS()
	{
		return FPS;
	}
	
	public final boolean getFPSLock()
	{
		return FPSLock;
	}
	
	public final long getElapsedTime()
	{
		return elapsedTime;
	}
	
	public final boolean escapeKey()	{return escapeKey;}
	public final boolean upKey() 	 	{return upKey;}
	public final boolean downKey() 	 	{return downKey;}
	public final boolean rightKey()  	{return rightKey;}
	public final boolean leftKey() 	 	{return leftKey;}
	public final boolean wKey()		 	{return wKey;}
	public final boolean eKey()		 	{return eKey;}
	public final boolean rKey()		 	{return rKey;}
	public final boolean sKey()		 	{return sKey;}
	public final boolean aKey()		 	{return aKey;}
	public final boolean dKey()		 	{return dKey;}
	public final boolean xKey()		 	{return xKey;}
	public final boolean np2Key()	 	{return np2Key;}
	public final boolean np4Key()	 	{return np4Key;}
	public final boolean np6Key()	 	{return np6Key;}
	public final boolean np7Key()	 	{return np7Key;}
	public final boolean np8Key()	 	{return np8Key;}
	public final boolean np9Key()	 	{return np9Key;}
	public final boolean npplusKey() 	{return npplusKey;}
	public final boolean npminusKey()	{return npminusKey;}
	public final boolean f1Key() 		{return f1Key;}
	public final boolean f3Key() 		{return f3Key;}
	public final boolean f11Key() 		{return f11Key;}
	
	/*********************************************************************/
	/* Events                                                            */
	/*********************************************************************/

	public final void keyPressed(KeyEvent e)
	{	
		//System.out.println(e.getKeyCode());
		
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_ESCAPE:  escapeKey = true; return;
			case KeyEvent.VK_UP: 	  upKey = true; return;
			case KeyEvent.VK_DOWN:    downKey = true; return;
			case KeyEvent.VK_RIGHT:   rightKey = true; return;
			case KeyEvent.VK_LEFT: 	  leftKey = true; return;
			case KeyEvent.VK_W:       wKey = true; return;
			case KeyEvent.VK_E:       eKey = true; return;
			case KeyEvent.VK_R:       rKey = true; return;
			case KeyEvent.VK_S:  	  sKey = true; return;
			case KeyEvent.VK_A:       aKey = true; return;
			case KeyEvent.VK_D:       dKey = true; return;
			case KeyEvent.VK_X:       xKey = true; return;
			case KeyEvent.VK_NUMPAD2: np2Key = true; return;
			case KeyEvent.VK_NUMPAD4: np4Key = true; return;
			case KeyEvent.VK_NUMPAD6: np6Key = true; return;
			case KeyEvent.VK_NUMPAD7: np7Key = true; return;
			case KeyEvent.VK_NUMPAD8: np8Key = true; return;
			case KeyEvent.VK_NUMPAD9: np9Key = true; return;
			case 107:                 npplusKey = true; return; // NUMPAD_PLUS
			case 109:                 npminusKey = true; return; // NUMPAD_MINUS
			case KeyEvent.VK_F1:      f1Key = true; return;
			case KeyEvent.VK_F3:      f3Key = true; return;
			case KeyEvent.VK_F11:     f11Key = true; return;
		}
	}

	public final void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_ESCAPE:  escapeKey = false; return;
			case KeyEvent.VK_UP: 	  upKey = false; return;
			case KeyEvent.VK_DOWN:    downKey = false; return;
			case KeyEvent.VK_RIGHT:   rightKey = false; return;
			case KeyEvent.VK_LEFT: 	  leftKey = false; return;
			case KeyEvent.VK_W:       wKey = false; return;
			case KeyEvent.VK_E:       eKey = false; return;
			case KeyEvent.VK_R:       rKey = false; return;
			case KeyEvent.VK_S:  	  sKey = false; return;
			case KeyEvent.VK_A:       aKey = false; return;
			case KeyEvent.VK_D:       dKey = false; return;
			case KeyEvent.VK_X:       xKey = false; return;
			case KeyEvent.VK_NUMPAD2: np2Key = false; return;
			case KeyEvent.VK_NUMPAD4: np4Key = false; return;
			case KeyEvent.VK_NUMPAD6: np6Key = false; return;
			case KeyEvent.VK_NUMPAD7: np7Key = false; return;
			case KeyEvent.VK_NUMPAD8: np8Key = false; return;
			case KeyEvent.VK_NUMPAD9: np9Key = false; return;
			case 107:                 npplusKey = false; return; // NUMPAD_PLUS
			case 109:                 npminusKey = false; return; // NUMPAD_MINUS
			case KeyEvent.VK_F1:      f1Key = false; return;
			case KeyEvent.VK_F3:      f3Key = false; return;
			case KeyEvent.VK_F11:     f11Key = false; return;
		}
	}
	
	public final void keyTyped(KeyEvent e){}	
}
