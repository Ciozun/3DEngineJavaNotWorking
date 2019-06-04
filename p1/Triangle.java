package p1;

public class Triangle implements Cloneable
{
	Vect3D[] p;
	Vect2D[] t;
	float lum = 1;
	
	public Triangle()
	{
		p = new Vect3D[3];
		t = new Vect2D[3];
		p[0] = new Vect3D();
		p[1] = new Vect3D();
		p[2] = new Vect3D();
		t[0] = new Vect2D();
		t[1] = new Vect2D();
		t[2] = new Vect2D();
	}
	
	public Triangle(Vect3D[] p)
	{
		this.p = p;
		t = new Vect2D[3];
		t[0] = new Vect2D();
		t[1] = new Vect2D();
		t[2] = new Vect2D();
	}
	
	public Triangle(Vect3D[] p, Vect2D[] t)
	{
		this.p = p;
		this.t = t;
 	}
	
	public Triangle clone()
	{
		try
		{
			return (Triangle) super.clone();
		}
		catch(Exception e){return null;}
	}
}
