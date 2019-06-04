package p1;

public class Vect2D implements Cloneable
{
	float u;
	float v;
	float w;
	
	public Vect2D()
	{
		u = 0;
		v = 0;
		w = 1;
	}
	
	public Vect2D(float u, float v)
	{
		this.u = u;
		this.v = v;
		w = 1;
	}
	
	public Vect2D(float u, float v, float w)
	{
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	public Vect2D clone()
	{
		try
		{
			return (Vect2D) super.clone();
		}
		catch(Exception e){return null;}
	}
}
