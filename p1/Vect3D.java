package p1;

public class Vect3D implements Cloneable
{
	float x;
	float y;
	float z;
	float w;
	
	public Vect3D()
	{
		x = 0;
		y = 0;
		z = 0;
		w = 1;
	}
	
	public Vect3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		w = 1;
	}
	
	public Vect3D(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vect3D clone()
	{
		try
		{
			return (Vect3D) super.clone();
		}
		catch(Exception e){return null;}
	}
}
