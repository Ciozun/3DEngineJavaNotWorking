package p1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Mesh
{
	Triangle[] tris;
	
	public Mesh() {}
	
	public Mesh(String filePath, boolean hasTexture) throws FileNotFoundException
	{		
		FileReader fr = new FileReader(filePath);
		Scanner sca = new Scanner(fr);
		
		ArrayList<Vect3D> vectors = new ArrayList<Vect3D>();
		ArrayList<Vect2D> texs = new ArrayList<Vect2D>();
		ArrayList<Triangle> tris = new ArrayList<Triangle>();

		while(sca.hasNextLine())
		{	
			String line = sca.nextLine();
			
			if(!line.startsWith("#"))
			{		
				String[] s = line.split(" ");

				switch(s[0])
				{
					case "v": vectors.add(new Vect3D(Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]))); break;
					case "vt": texs.add(new Vect2D(Float.parseFloat(s[1]), Float.parseFloat(s[2]))); break;
					case "f":
						if(!hasTexture)
						{
							Vect3D[] p = new Vect3D[3];
							p[0] = vectors.get(Integer.parseInt(s[1]) - 1);
							p[1] = vectors.get(Integer.parseInt(s[2]) - 1);
							p[2] = vectors.get(Integer.parseInt(s[3]) - 1);
							
							tris.add(new Triangle(p));							
						}
						else
						{
							Vect3D[] p = new Vect3D[3];
							Vect2D[] t = new Vect2D[3];
							
							String[] sf0 = s[1].split("/"); 
							String[] sf1 = s[2].split("/"); 
							String[] sf2 = s[3].split("/");
							
							p[0] = vectors.get(Integer.parseInt(sf0[0]) - 1);
							p[1] = vectors.get(Integer.parseInt(sf1[0]) - 1);
							p[2] = vectors.get(Integer.parseInt(sf2[0]) - 1);
							t[0] = texs.get(Integer.parseInt(sf0[1]) - 1);
							t[1] = texs.get(Integer.parseInt(sf1[1]) - 1);
							t[2] = texs.get(Integer.parseInt(sf2[1]) - 1);
							
							tris.add(new Triangle(p, t));
						}
				}
			}
		}
		
		sca.close();
		
		this.tris = new Triangle[tris.size()];
		
		for(int i = 0; i < tris.size(); i++)
			this.tris[i] = tris.get(i);
	}
}
