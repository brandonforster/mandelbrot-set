/*
 * MandelbrotSet.java
 * Brandon Forster
 * COP 3330
 * 20 November 2011
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MandelbrotSet implements ActionListener, MouseListener {

	//these are global so that the listeners can access them
	private JFrame frame;
	private Canvas canvas;
	private JButton genButton;
	private JTextField realField;
	private JTextField imagField;
	private JTextField viewField;
	private JTextField lengthField;
	double square;

	// constructs a new MandelbrotSet object, which essentially everything
	public MandelbrotSet() {
		
		// Create the frame.
		frame = new JFrame("The Mandelbrot Set");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,600);

		// Create the canvas, add listener, and place it in the frame.
		canvas = new Canvas();
		canvas.setSize(500,500);
		canvas.setBackground(Color.BLACK);
		canvas.addMouseListener(this);
		frame.add(canvas, BorderLayout.PAGE_START);

		// Create the panel to store the input fields
		JPanel panel= new JPanel();
		panel.setPreferredSize(new Dimension(500,100));

		// Format the panel so it appears in the center of the window.
		frame.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2,2));


		// Add the appropriate label element to the panel
		JLabel realLabel = new JLabel("Real Center");
		panel.add(realLabel);
		// Add the appropriate text field to the frame.
		realField = new JTextField("-.4", 10);
		realField.addActionListener(this);
		panel.add(realField);


		// Add the appropriate label element to the panel
		JLabel imagLabel = new JLabel("Imaginary Center");
		panel.add(imagLabel);
		// Add the appropriate text field to the frame.
		imagField = new JTextField("0",10);
		imagField.addActionListener(this);
		panel.add(imagField);


		// Add the appropriate label element to the panel
		JLabel viewLabel= new JLabel("View Size");
		panel.add(viewLabel);
		// Add the appropriate text field to the frame.
		viewField = new JTextField("1.1",10);
		viewField.addActionListener(this);
		panel.add(viewField);


		// Add the appropriate label element to the panel
		JLabel lengthLabel = new JLabel("Maximum Length");
		panel.add(lengthLabel);
		// Add the appropriate text field to the frame.
		lengthField = new JTextField("50",10);
		lengthField.addActionListener(this);
		panel.add(lengthField);


		// Create the generate button.
		genButton = new JButton("Generate");
		genButton.addActionListener(this);
		frame.add(genButton, BorderLayout.PAGE_END);

		// make the frame all pretty
		frame.pack();

		frame.setVisible(true);
		panel.setVisible(true);

	}
	
	//This function used to be entirely in ActionPerformed, but then I realized I couldn't
	//call that from MouseClicked. So now it's here.
	public void draw()
	{
		double realCenter;
		double imagCenter;
		double view;
		double length;

		//if the user enters any invalid arguments, it sets invalid arguments
		//to the "standard" Mandelbrot view
		try{
			realCenter= Double.parseDouble(realField.getText());
		}
		catch (InputMismatchException ime)
		{
			realCenter=-.4;
			realField.setText("-.4");
		}
		catch (NumberFormatException nfe)
		{
			realCenter=-.4;
			realField.setText("-.4");
		}

		try{
			imagCenter= Double.parseDouble(imagField.getText());
		}
		catch (InputMismatchException ime)
		{
			imagCenter=0;
			imagField.setText("0");
		}
		catch (NumberFormatException nfe)
		{
			imagCenter=0;
			imagField.setText("0");
		}

		try{
			view= Double.parseDouble(viewField.getText());
		}
		catch (InputMismatchException ime)
		{
			view=1.1;
			viewField.setText("1.1");
		}
		catch (NumberFormatException nfe)
		{
			view=1.1;
			viewField.setText("1.1");
		}

		try
		{
			length= Double.parseDouble(lengthField.getText());
		}
		catch (InputMismatchException ime)
		{
			length=50;
			lengthField.setText("50");
		}
		catch (NumberFormatException nfe)
		{
			length=50;
			lengthField.setText("50");
		}

		//create our graphics object and set its default value
		Graphics g = canvas.getGraphics();
		g.setColor(Color.BLACK);
		canvas.paint(g);
		
		//this logic formats the drawing so that it draws based on the shortest side of
		//the canvas
		double width= canvas.getWidth();
		double height= canvas.getHeight();
		
		if (width<height)
			square= width;
		else
			square= height;

		//these loops process each pixel
		for (int x=0; x<square; x++)
		{
			for (int y=0; y<square; y++)
			{
				//create z, our ComplexNumber that will change
				ComplexNumber z= new ComplexNumber(realCenter,imagCenter);
				z= z.map(view, square, x, y);

				//create our ComplexNumber that will not change
				ComplexNumber c= z;

				//initialize and sanitize our per pixel variables
				int numStepsTaken=0;
				boolean draw= false;

				do
				{	
					//here we see our base Mandelbrot logic
					z= z.square().add(c);

					//if the number is outside the set
					if(z.magnitude()>2)
					{
						//draw a Rorschach test on fire
						g.setColor(new Color((int) ((16 << 16) * numStepsTaken / length)));
						g.drawLine(x, y, x, y);
						//indicate that we have colored this pixel and it does not need
						//default coloring
						draw=true;
					}
					//since this is a while loop, this is the only way to know how many
					//steps we have taken. it gets reset per pixel.
					numStepsTaken++;

				// if we max out steps, OR if we draw on the pixel, stop processing
				}while (numStepsTaken<length && draw==false);

				//this runs if we gave up trying to see if the pixel is outside the set.
				//draw the default color
				if (draw==false)
				{
					g.setColor(Color.BLACK);
					g.drawLine(x, y, x, y);
				}
			}
		}
	}

	//This function runs when the user clicks the button or presses enter.
	public void actionPerformed(ActionEvent e) {
		draw();
	}
	
	// implemented so that MouseListener is happy. doesn't actually do anything.
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {

		double realCenter;
		double imagCenter;
		double view;

		//if the user enters any invalid arguments, it sets arguments to the "standard"
		//Mandelbrot view and reflects this change in the text fields
		try{
			realCenter= Double.parseDouble(realField.getText());
		}
		catch (InputMismatchException ime)
		{
			realCenter=-.4;
			realField.setText("-.4");
		}
		catch (NumberFormatException nfe)
		{
			realCenter=-.4;
			realField.setText("-.4");
		}
		

		try{
			imagCenter= Double.parseDouble(imagField.getText());
		}
		catch (InputMismatchException ime)
		{
			imagCenter=0;
			imagField.setText("0");
		}
		catch (NumberFormatException nfe)
		{
			imagCenter=0;
			imagField.setText("0");
		}
		

		try{
			view= Double.parseDouble(viewField.getText());
		}
		catch (InputMismatchException ime)
		{
			view=1.1;
			viewField.setText("1.1");
		}
		catch (NumberFormatException nfe)
		{
			view=1.1;
			viewField.setText("1.1");
		}

		//makes a complex number to process based on read in values to output the complex
		//coordinates of the real coordinates of the click
		ComplexNumber cn= new ComplexNumber(realCenter, imagCenter);
		cn= cn.map(view,square,e.getX(),e.getY());


		//get the complex coordinates and set the text fields to these new values
		realField.setText(cn.getReal()+"");
		imagField.setText(cn.getImaginary()+"");
		
		//creates a new drawing based on the values we created.
		draw();
	}

	//implemented so that MouseListener is happy. doesn't actually do anything.
	@Override
	public void mouseEntered(MouseEvent e) {}

	//implemented so that MouseListener is happy. doesn't actually do anything.
	@Override
	public void mouseExited(MouseEvent e) {}

	//implemented so that MouseListener is happy. doesn't actually do anything.
	@Override
	public void mouseReleased(MouseEvent e) {}

	// Our main method only needs to construct the GUI.
	public static void main(String[] args) {
		new MandelbrotSet();
	}
}