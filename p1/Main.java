package p1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Main
{
	private int WIDTH = 900;
	private int HEIGHT = 900;
	private String TITLE = "3D Engine Final";
	
	private float[][] cubeTris =
	{
		// SOUTH
		{ 0.0f, 0.0f, 0.0f, 1.0f,    0.0f, 1.0f, 0.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,}, 
		{ 0.0f, 0.0f, 0.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,    1.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
						  																			   
		// EAST           																			   
		{ 1.0f, 0.0f, 0.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
		{ 1.0f, 0.0f, 0.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 0.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
						   																			   
		// NORTH           																			   
		{ 1.0f, 0.0f, 1.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
		{ 1.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,    0.0f, 0.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
						   																			   
		// WEST            																			   
		{ 0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
		{ 0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f, 0.0f, 1.0f,    0.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
						   																			   
		// TOP             																			   
		{ 0.0f, 1.0f, 0.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
		{ 0.0f, 1.0f, 0.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
						   																			  
		// BOTTOM          																			  
		{ 1.0f, 0.0f, 1.0f, 1.0f,    0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
		{ 1.0f, 0.0f, 1.0f, 1.0f,    0.0f, 0.0f, 0.0f, 1.0f,    1.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
	};
	private Mesh mesh;
	private BufferedImage tex = toBufferedImage(new ImageIcon("meshes//player0.png").getImage());
	
	float[][] mProj = MatUtils.projMat(90, HEIGHT * 1f / WIDTH, 0.1f, 1000f);
	float[][] mWorld;
	Vect3D vCamera = new Vect3D();
	Vect3D vLookDir = new Vect3D();
	Vect3D vUp = new Vect3D(0f, 1f, 0f);
	Vect3D vLight = MatUtils.normVec(new Vect3D(0f, 1f, -1f));
	Vect3D planeP = new Vect3D(0f, 0f, 0.1f);
	Vect3D planeN = new Vect3D(0f, 0f, 1f);
	Vect3D vOffsetView = new Vect3D(1f, 1f, 0f);
	float yaw = 0f;
	float theta = 0f;
	
	float[] depthBuffer = new float[WIDTH * HEIGHT];
	
	private boolean bool0 = false;
	
	public Main() throws Exception
	{
		mesh = new Mesh("meshes//cube.obj", false);
	/*	mesh.tris = new Triangle[12];
		
		for(byte i = 0; i < 12; i++)
		{
			Vect3D[] p = new Vect3D[3];
			Vect2D[] t = new Vect2D[3];
			for(byte j = 0; j < 3; j++)
			{
				p[j] = new Vect3D(cubeTris[i][j * 4], cubeTris[i][j * 4 + 1], cubeTris[i][j * 4 + 2], cubeTris[i][j * 4 + 3]);
				t[j] = new Vect2D(cubeTris[i][12 + j * 3], cubeTris[i][12 + j * 3 + 1], cubeTris[i][12 + j * 3 + 2]);
			}
			
			mesh.tris[i] = new Triangle(p, t);
		}*/
		
		new SimpleGameEngine(WIDTH, HEIGHT, TITLE)
		{
			public void update()
			{
				if(!escapeKey())
				{
					if(f3Key())
					{
						if(!bool0)
						{
							setFPSLock(!getFPSLock());
							bool0 = true;
						}
					}else bool0 = false;
					
					float elapsedTime = getElapsedTime() / 1000000000f;
					
					if(upKey())
						vCamera.y += 8f * elapsedTime;
					if(downKey())
						vCamera.y -= 8f * elapsedTime;
					if(leftKey())
						vCamera.x -= 8f * elapsedTime;
					if(rightKey())
						vCamera.x += 8f * elapsedTime;
					
					Vect3D vForward = MatUtils.mulVec(vLookDir, 8f * elapsedTime);
					
					if(wKey())
						vCamera = MatUtils.addVec(vCamera, vForward);
					if(sKey())
						vCamera = MatUtils.subVec(vCamera, vForward);
					if(aKey())
						yaw += 2f * elapsedTime;
					if(dKey())
						yaw -= 2f * elapsedTime;
					
					theta += 1f * elapsedTime;
					
					float[][] mXRotMat = MatUtils.xRotMat(theta * 0.5f);
					float[][] mZRotMat = MatUtils.zRotMat(theta);
					float[][] mTrans = MatUtils.transMat(0, 0, 5);
					
					mWorld = MatUtils.mulMat(mZRotMat, mXRotMat);
					mWorld = MatUtils.mulMat(mWorld, mTrans);
				}
				else
					stop();
			}
			
			public void render()
			{
				Graphics g = getGraphics();
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, WIDTH, HEIGHT);
				
				Vect3D vTarget = new Vect3D(0f, 0f, 1f);
				float[][] mCameraRot = MatUtils.yRotMat(yaw);
				vLookDir = MatUtils.mulVecMat(vTarget, mCameraRot);
				vTarget = MatUtils.addVec(vCamera, vLookDir);
				float[][] mCamera = MatUtils.pointAtMat(vCamera, vTarget, vUp);
				float[][] mView = MatUtils.lookAtMat(mCamera);
				
				ArrayList<Triangle> vecTriToRaster = new ArrayList<Triangle>();
				
				for(Triangle tri : mesh.tris)
				{
					Triangle triTransformed = new Triangle();					
					triTransformed.p[0] = MatUtils.mulVecMat(tri.p[0], mWorld);
					triTransformed.p[1] = MatUtils.mulVecMat(tri.p[1], mWorld);
					triTransformed.p[2] = MatUtils.mulVecMat(tri.p[2], mWorld);
					triTransformed.t[0] = tri.t[0];
					triTransformed.t[1] = tri.t[1];
					triTransformed.t[2] = tri.t[2];
					
					Vect3D line1 = MatUtils.subVec(triTransformed.p[1], triTransformed.p[0]);
					Vect3D line2 = MatUtils.subVec(triTransformed.p[2], triTransformed.p[0]);
					Vect3D normal = MatUtils.normVec(MatUtils.crossProd(line1, line2));
					
					Vect3D vCameraRay = MatUtils.subVec(triTransformed.p[0], vCamera);
					
					if(MatUtils.dotProd(normal, vCameraRay) < 0f)
					{
						float d = MatUtils.dotProd(vLight, normal);
						float dp = 0.1f > d ? 0.1f : d;
						
						Triangle triViewed = new Triangle();						
						triViewed.p[0] = MatUtils.mulVecMat(triTransformed.p[0], mView);
						triViewed.p[1] = MatUtils.mulVecMat(triTransformed.p[1], mView);
						triViewed.p[2] = MatUtils.mulVecMat(triTransformed.p[2], mView);
						triViewed.lum = dp;
						triViewed.t[0] = triTransformed.t[0];
						triViewed.t[1] = triTransformed.t[1];
						triViewed.t[2] = triTransformed.t[2];
						
						byte nClippedTris = 0;
						Triangle[] clipped = new Triangle[2];
						clipped[0] = new Triangle();
						clipped[1] = new Triangle();
						nClippedTris = MatUtils.clipAgainstPlane(planeP, planeN, triViewed, clipped[0], clipped[1]);
					
						for(byte n = 0; n < nClippedTris; n++)
						{
							Triangle triProjected = new Triangle();							
							triProjected.p[0] = MatUtils.mulVecMat(clipped[n].p[0], mProj);
							triProjected.p[1] = MatUtils.mulVecMat(clipped[n].p[1], mProj);
							triProjected.p[2] = MatUtils.mulVecMat(clipped[n].p[2], mProj);
							triProjected.lum = clipped[n].lum;
							triProjected.t[0] = clipped[n].t[0];
							triProjected.t[1] = clipped[n].t[1];
							triProjected.t[2] = clipped[n].t[2];
							
							triProjected.t[0].u = triProjected.t[0].u / triProjected.p[0].w;
							triProjected.t[1].u = triProjected.t[1].u / triProjected.p[1].w;
							triProjected.t[2].u = triProjected.t[2].u / triProjected.p[2].w;

							triProjected.t[0].v = triProjected.t[0].v / triProjected.p[0].w;
							triProjected.t[1].v = triProjected.t[1].v / triProjected.p[1].w;
							triProjected.t[2].v = triProjected.t[2].v / triProjected.p[2].w;

							triProjected.t[0].w = 1f / triProjected.p[0].w;
							triProjected.t[1].w = 1f / triProjected.p[1].w;
							triProjected.t[2].w = 1f / triProjected.p[2].w;

							triProjected.p[0] = MatUtils.divVec(triProjected.p[0], triProjected.p[0].w);
							triProjected.p[1] = MatUtils.divVec(triProjected.p[1], triProjected.p[1].w);
							triProjected.p[2] = MatUtils.divVec(triProjected.p[2], triProjected.p[2].w);
						
							triProjected.p[0].x *= -1f;
							triProjected.p[1].x *= -1f;
							triProjected.p[2].x *= -1f;
							triProjected.p[0].y *= -1f;
							triProjected.p[1].y *= -1f;
							triProjected.p[2].y *= -1f;
							
							triProjected.p[0] = MatUtils.addVec(triProjected.p[0], vOffsetView);
							triProjected.p[1] = MatUtils.addVec(triProjected.p[1], vOffsetView);
							triProjected.p[2] = MatUtils.addVec(triProjected.p[2], vOffsetView);
							triProjected.p[0].x *= 0.5f * WIDTH;
							triProjected.p[0].y *= 0.5f * HEIGHT;
							triProjected.p[1].x *= 0.5f * WIDTH;
							triProjected.p[1].y *= 0.5f * HEIGHT;
							triProjected.p[2].x *= 0.5f * WIDTH;
							triProjected.p[2].y *= 0.5f * HEIGHT;
							
							vecTriToRaster.add(triProjected);
						}
					}
				}
				
				for(int i = 0; i < WIDTH * HEIGHT; i++)
					depthBuffer[i] = 0f;
				
				
				for(Triangle triToRaster : vecTriToRaster)
				{
					Triangle[] clipped = new Triangle[2];
					clipped[0] = new Triangle();
					clipped[1] = new Triangle();
					ArrayList<Triangle> listTris = new ArrayList<Triangle>();
					
					listTris.add(triToRaster);
					int nNewTris = 1;
					
					for(byte p = 0; p < 4; p++)
					{
						byte nTrisToAdd = 0;
						while(nNewTris > 0)
						{
							Triangle test = listTris.get(0);
							listTris.remove(0);
							nNewTris--;
							
							switch(p)
							{
								case 0: nTrisToAdd = MatUtils.clipAgainstPlane(new Vect3D(0f, 0f, 0f), new Vect3D(0f, 1f, 0f), test, clipped[0], clipped[1]); break;
								case 1: nTrisToAdd = MatUtils.clipAgainstPlane(new Vect3D(0f, HEIGHT - 1f, 0f), new Vect3D(0f, -1f, 0f), test, clipped[0], clipped[1]); break;
								case 2: nTrisToAdd = MatUtils.clipAgainstPlane(new Vect3D(0f, 0f, 0f), new Vect3D(1f, 0f, 0f), test, clipped[0], clipped[1]); break;
								case 3: nTrisToAdd = MatUtils.clipAgainstPlane(new Vect3D(WIDTH - 1f, 0f, 0f), new Vect3D(-1f, 0f, 0f), test, clipped[0], clipped[1]);
							}
							
							for(byte w = 0; w < nTrisToAdd; w++)
								listTris.add(clipped[w]);
						}
						
						nNewTris = listTris.size();
					}
					
					for(Triangle t : listTris)
					{
						texturedTriangle(t.p[0].x, t.p[0].y, t.t[0].u, t.t[0].v, t.t[0].w,
								t.p[1].x, t.p[1].y, t.t[1].u, t.t[1].v, t.t[1].w,
								t.p[2].x, t.p[2].y, t.t[2].u, t.t[2].v, t.t[2].w, tex, g);
						
						g.setColor(new Color((int)(t.lum * 255), (int)(t.lum * 255), (int)(t.lum * 255)));
						
						int[] xs = new int[]{(int)t.p[0].x, (int)t.p[1].x, (int)t.p[2].x};
						int[] ys = new int[]{(int)t.p[0].y, (int)t.p[1].y, (int)t.p[2].y};
					
						g.drawPolygon(xs, ys, 3);
					}
				}
				
				drawGraphics();
			}
		};
	}
	
	private void texturedTriangle(float x, float y, float u1, float v1, float w1,
							      float x2, float y2, float u2, float v2, float w2,
								  float x3, float y3, float u3, float v3, float w3,
								  BufferedImage tex, Graphics g)
	{
		if (y2 < y)
		{
			y = getItself(y2, y2 = y);
			x = getItself(x2, x2 = x);
			u1 = getItself(u2, u2 = u1);
			v1 = getItself(v2, v2 = v1);
			w1 = getItself(w2, w2 = w1);
		}
		
		if (y3 < y)
		{
			y = getItself(y3, y3 = y);
			x = getItself(x3, x3 = x);
			u1 = getItself(u3, u3 = u1);
			v1 = getItself(v3, v3 = v1);
			w1 = getItself(w3, w3 = w1);
		}
		
		if (y3 < y2)
		{
			y2 = getItself(y3, y3 = y2);
			x2 = getItself(x3, x3 = x2);
			u2 = getItself(u3, u3 = u2);
			v2 = getItself(v3, v3 = v2);
			w2 = getItself(w3, w3 = w2);
		}
		
		int dy1 = (int) (y2 - y);
		int dx1 = (int) (x2 - x);
		float dv1 = v2 - v1;
		float du1 = u2 - u1;
		float dw1 = w2 - w1;
		
		int dy2 = (int) (y3 - y);
		int dx2 = (int) (x3 - x);
		float dv2 = v3 - v1;
		float du2 = u3 - u1;
		float dw2 = w3 - w1;
		
		float tex_u, tex_v, tex_w;
		
		float dax_step = 0, dbx_step = 0,
		du1_step = 0, dv1_step = 0,
		du2_step = 0, dv2_step = 0,
		dw1_step=0, dw2_step=0;
		
		if (dy1!=0) dax_step = dx1 / (float)Math.abs(dy1);
		if (dy2!=0) dbx_step = dx2 / (float)Math.abs(dy2);
		
		if (dy1!=0) du1_step = du1 / (float)Math.abs(dy1);
		if (dy1!=0) dv1_step = dv1 / (float)Math.abs(dy1);
		if (dy1!=0) dw1_step = dw1 / (float)Math.abs(dy1);
		
		if (dy2!=0) du2_step = du2 / (float)Math.abs(dy2);
		if (dy2!=0) dv2_step = dv2 / (float)Math.abs(dy2);
		if (dy2!=0) dw2_step = dw2 / (float)Math.abs(dy2);
		
		if (dy1!=0)
		{
			for (int i = (int) y; i <= y2; i++)
			{
				int ax = (int) (x + (float)(i - y) * dax_step);
				int bx = (int) (x + (float)(i - y) * dbx_step);
				
				float tex_su = u1 + (float)(i - y) * du1_step;
				float tex_sv = v1 + (float)(i - y) * dv1_step;
				float tex_sw = w1 + (float)(i - y) * dw1_step;
				
				float tex_eu = u1 + (float)(i - y) * du2_step;
				float tex_ev = v1 + (float)(i - y) * dv2_step;
				float tex_ew = w1 + (float)(i - y) * dw2_step;
				
				if (ax > bx)
				{
					ax = getItself(bx, bx = ax);
					tex_su = getItself(tex_eu, tex_eu = tex_su);
					tex_sv = getItself(tex_ev, tex_ev = tex_sv);
					tex_sw = getItself(tex_ew, tex_ew = tex_sw);
				}
				
				tex_u = tex_su;
				tex_v = tex_sv;
				tex_w = tex_sw;
				
				float tstep = 1.0f / ((float)(bx - ax));
				float t = 0.0f;
				
				for (int j = ax; j < bx; j++)
				{
					tex_u = (1.0f - t) * tex_su + t * tex_eu;
					tex_v = (1.0f - t) * tex_sv + t * tex_ev;
					tex_w = (1.0f - t) * tex_sw + t * tex_ew;
					if (tex_w > depthBuffer[i*WIDTH + j])
					{
						Color color = new Color(tex.getRGB((int)(tex_u / tex_w), (int)(tex_v / tex_w)));
						g.setColor(color);
						g.drawLine(j, i, j, i);
						depthBuffer[i*WIDTH + j] = tex_w;
					}
					t += tstep;
				}			
			}
		}
		
		dy1 = (int) (y3 - y2);
		dx1 = (int) (x3 - x2);
		dv1 = v3 - v2;
		du1 = u3 - u2;
		dw1 = w3 - w2;
		
		if (dy1!=0) dax_step = dx1 / (float)Math.abs(dy1);
		if (dy2!=0) dbx_step = dx2 / (float)Math.abs(dy2);
		
		du1_step = 0;
		dv1_step = 0;
		if (dy1!=0) du1_step = du1 / (float)Math.abs(dy1);
		if (dy1!=0) dv1_step = dv1 / (float)Math.abs(dy1);
		if (dy1!=0) dw1_step = dw1 / (float)Math.abs(dy1);
		
		if (dy1!=0)
		{
			for (int i = (int) y2; i <= y3; i++)
			{
				int ax = (int) (x2 + (float)(i - y2) * dax_step);
				int bx = (int) (x + (float)(i - y) * dbx_step);
				
				float tex_su = u2 + (float)(i - y2) * du1_step;
				float tex_sv = v2 + (float)(i - y2) * dv1_step;
				float tex_sw = w2 + (float)(i - y2) * dw1_step;
				
				float tex_eu = u1 + (float)(i - y) * du2_step;
				float tex_ev = v1 + (float)(i - y) * dv2_step;
				float tex_ew = w1 + (float)(i - y) * dw2_step;
				
				if (ax > bx)
				{
					ax = getItself(bx, bx = ax);
					tex_su = getItself(tex_eu, tex_eu = tex_su);
					tex_sv = getItself(tex_ev, tex_ev = tex_sv);
					tex_sw = getItself(tex_ew, tex_ew = tex_sw);
				}
				
				tex_u = tex_su;
				tex_v = tex_sv;
				tex_w = tex_sw;
				
				float tstep = 1.0f / ((float)(bx - ax));
				float t = 0.0f;
				
				for (int j = ax; j < bx; j++)
				{
					tex_u = (1.0f - t) * tex_su + t * tex_eu;
					tex_v = (1.0f - t) * tex_sv + t * tex_ev;
					tex_w = (1.0f - t) * tex_sw + t * tex_ew;
				
					if (tex_w > depthBuffer[i*WIDTH + j])
					{
						Color color = new Color(tex.getRGB((int)(tex_u / tex_w), (int)(tex_v / tex_w)));
						g.setColor(color);
						g.drawLine(j, i, j, i);
						depthBuffer[i*WIDTH + j] = tex_w;
					}
					t += tstep;
				}
			}	
		}		
	}
	
	public static int getItself(int itself, int dummy)
	{
	    return itself;
	}
	
	public static float getItself(float itself, float dummy)
	{
	    return itself;
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
