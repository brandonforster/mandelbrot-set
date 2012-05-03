/*
 * ComplexNumber.java
 * Brandon Forster
 * COP 3330
 * 20 November 2011
 */

/**
 * Creates objects that represent a complex number, allowing you to keep the real (a)
 * part of the number distinct from the imaginary (b) part. Provides functions to
 * perform basic operations on these numbers, especially ones relevant to calculating
 * the Mandelbrot Set.
 * 
 * @author Brandon Forster
 *
 */
public class ComplexNumber
{
	/**
	 * The ComplexNumber constructor. Creates a new ComplexNumber with the arguments
	 * as the real and imaginary coefficients.
	 * 
	 * @param real The real part of a complex number
	 * @param comp The imaginary number coefficient of a complex number
	 */
	public ComplexNumber(double real, double comp)
	{
		this.real= real;
		this.comp= comp;
	}
	
	
	/**
	 * This method returns a new ComplexNumber that is the square of the
	 * object it is called on.
	 * 
	 * @return ComplexNumber that is the square of the input.
	 */
	public ComplexNumber square()
	{	
		double real= Math.pow(this.real,2.0) - Math.pow(this.comp,2.0);
		double complex= (2*this.real*this.comp);
		
		return new ComplexNumber(real, complex);
	}
	
	/**
	 * This method returns a double that represents the largeness of a ComplexNumber
	 * point on a complex plane. This calculation is relevant when determining inclusion
	 * for the Mandelbrot Set, and the calculation itself is based on the
	 * Pythagorean Theorem.
	 * 
	 * @return The value of the magnitude (largeness) of this complex number
	 */
	public double magnitude()
	{	
		return Math.abs(Math.sqrt(this.real*this.real + this.comp*this.comp));
	}
	
	/**
	 * Adds two ComplexNumber objects together and outputs a new ComplexNumber object
	 * that is the sum of this and c.
	 * 
	 * @param c A ComplexNumber to add to the ComplexNumber this method is called on.
	 * 
	 * @return A new ComplexNumber object with a center at points derived from
	 * calculating the addition of this and c.
	 */
	public ComplexNumber add(ComplexNumber c)
	{
		double real= this.real + c.real;
		double comp= this.comp+ c.comp;
		
		return new ComplexNumber(real, comp);
	}
	
	/**
	 * Maps a point on the Cartesian plane to its equivalent on the complex plane based on
	 * parameters defined by the user in the MandelbrotSet GUI.
	 * 
	 * @param viewSize The zoom coefficient defined by the user in the MandelbrotSet GUI.
	 * 
	 * @param canvasSize The size of the complex plane defined by the user in the
	 * MandelbrotSet GUI.
	 * 
	 * @param x The Cartesian x coordinate of the point we are to map to
	 * the complex plane.
	 * 
	 * @param y The Cartesian y coordinate of the point we are to map to
	 * the complex plane.
	 * 
	 * @return A new ComplexNumber with coefficients based on the input Cartesian
	 * coordinate and the input parameters of the complex plane.
	 */
	public ComplexNumber map(double viewSize, double canvasSize, double x, double y)
	{
		double real= (this.real - viewSize)+ (2* ((viewSize*x)/canvasSize));
		double comp= (this.comp + viewSize)- (2* ((viewSize*y)/canvasSize));
		
		return new ComplexNumber(real, comp);
	}
	
	/**
	 * Gets the value of the real coefficient of the ComplexNumber.
	 * 
	 * @return The real coefficient of the ComplexNumber the method is called on.
	 */
	public double getReal()
		{return this.real;}
	
	/**
	 * Gets the value of the imaginary coefficient of the ComplexNumber.
	 * 
	 * @return The imaginary coefficient of the ComplexNumber the method is called on.
	 */
	public double getImaginary()
	{return this.comp;}
	
	/**
	 * Returns a String representation of a ComplexNumber object. Includes logic for
	 * properly displaying the sign of the complex coefficient.
	 * 
	 * @return A String representing the ComplexNumber the method is called on.
	 */
	public String toString()
	{
		if (this.real==0 && this.comp==0)
			return "0";
		else if (this.real==0)
			return this.comp+"i";
		else if (this.comp==0)
			return this.real+"";
		else if (this.comp<0)
			return this.real +" "+this.comp+"i";
		else
			return this.real +" +"+this.comp+"i";
	}
	
	/*
	 * These are declared down here so that they can be final and immutable and still
	 * edited by the constructor.
	 */
	private final double real;
	private final double comp;
}
