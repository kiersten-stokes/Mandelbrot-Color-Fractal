import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;


public class Mandelbrot{

  private int xRes, yRes;
  private double cxMin, cxMax, cyMin, cyMax;
  private int[][] xtable;
  ColorList colors;
  static Graphics g;

  public Mandelbrot(int xResolution, int yResolution, double xMin, 
					double xMax, double yMin, double yMax)
  {
    xRes=xResolution;
    yRes=yResolution;
    cxMin=xMin;
    cxMax=xMax;
    cyMin=yMin;
    cyMax=yMax;
    colors = new ColorList();

    xtable= new int[yRes][xRes];

    for(int x=0; x < yRes; x++)
    {
      for(int y=0; y < xRes; y++)
      {
        ComplexNumber c = new ComplexNumber(cxMin + y/(double)(xRes-1)*(cxMax-cxMin), 
											cyMin + x/(double)(yRes-1)*(cyMax-cyMin));
        ComplexNumber z = new ComplexNumber(0.0,0.0);

        int iter=0;
        int maxIter = 255;

        while (iter < maxIter && z.abs()< 2.0)
        {
          z=z.squared().add(c);
          iter++;
        }

        if (z.abs()>=2.0)
        {
          xtable[x][y] = iter;
        }
        else
          xtable[x][y] = 0;

      }
    }
  }// constructor


  public void setPixel(Graphics g, int x, int y, int red, int grn, int blu)
  {
    Color c = new Color(red,grn,blu);
    g.setColor(c);
    g.drawLine(y,x,y,x);
  }// setPixel


  public void displayFractal(Graphics g)
  {
    for(int x=0; x < yRes; x++)
    {
      for(int y=0; y < xRes; y++)
      {
        Color c = colors.colList.get(xtable[x][y]);
        
        int red = c.getRed();
        int grn = c.getGreen();
        int blu = c.getBlue();

        setPixel(g,x,y,red,grn,blu);
        
      }
    }
  }// setPixel


  public void saveFractal(String fileName) throws FileNotFoundException
  {
    File f = new File("C:\\temp\\" + fileName + ".ppm");
    FileOutputStream fout = new FileOutputStream(f);
    PrintStream out = new PrintStream(fout);

    // header
    out.println("P3\r\n" + xRes + " " + yRes + "\r\n255\r\n"); // P2 xres yres maxval

    // pixel data: RGB triplets
    for(int x=0; x < yRes; x++)
    {
      for(int y=0; y < xRes; y++)
      {
        Color c = colors.colList.get(xtable[x][y]);
        
        int red = c.getRed();
        int grn = c.getGreen();
        int blu = c.getBlue();

        setPixel(g,x,y,red,grn,blu);

        out.println(red + " " + grn + " " + blu);

      }// next x
    }// next y   
    out.close();
  }// saveFractal


  public static void main(String[] args) throws FileNotFoundException
  {

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GridLayout gl = new GridLayout(1,1);
    gl.setVgap(0);gl.setHgap(0);
    frame.setLayout(gl);

    JPanel p = new JPanel();
    p.setBackground(Color.GRAY); 
    p.setPreferredSize(new Dimension(640,480)); 
    frame.add(p); 

    BufferedImage pb1 = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB);
    
    JLabel pi1 = new JLabel(new ImageIcon(pb1));
    pi1.setPreferredSize(new Dimension(640,480));
    p.add(pi1);    

    g = pb1.createGraphics();

    Mandelbrot fractal = new Mandelbrot(640,480,-2,1,-1,1);
    
    fractal.displayFractal(g);
    fractal.saveFractal("fractal");

    frame.setVisible(true); 
    frame.setResizable(false);
    frame.pack(); 

  }// main()
}// Mandelbrot
