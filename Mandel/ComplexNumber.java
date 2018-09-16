import static java.lang.Math.sqrt;

public class ComplexNumber {
  
  private double r, i;
  
  public ComplexNumber(double real, double imag)
  {
    r = real;
    i = imag;
  }// constructor

  // return a new ComplexNumber object whose value is (this + c)
  public ComplexNumber add(ComplexNumber c) 
  {
    ComplexNumber a = this;             // invoking object
    double real = a.r + c.r;
    double imag = a.i + c.i;
    return new ComplexNumber(real, imag);
  }// add
  
  
  // a static version of add
  public static ComplexNumber add(ComplexNumber c, ComplexNumber d) 
  {
    double real = c.r + d.r;
    double imag = c.i + d.i;
    //ComplexNumber sum = new ComplexNumber(real, imag);
    return new ComplexNumber(real, imag);
  }// static add
  
 
  // a static version of squared
  public ComplexNumber squared() 
  {
     double real = ((r*r)-(i*i));
     double imag = 2*r*i;
     //ComplexNumber square = new ComplexNumber(real, imag);
     return new ComplexNumber(real, imag);
  }// static squared
  
  
  // a static version of abs
  public double abs() 
  {
    double absolute = sqrt(r*r + i*i);
    return absolute;
  }// static abs  

}// ComplexNumber
