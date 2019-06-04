package p1;

public class MatUtils
{
	public final static float PI = 3.1415926536f;	
	
	/*********************************************************************/
	/* Vector functions                                                  */
	/*********************************************************************/
	
	public static Vect3D addVec(Vect3D v0, Vect3D v1)
	{
		return new Vect3D(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z);
	}
	
	public static Vect3D subVec(Vect3D v0, Vect3D v1)
	{
		return new Vect3D(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z);
	}
	
	public static Vect3D mulVec(Vect3D v, float k)
	{
		return new Vect3D(v.x * k, v.y * k, v.z * k);
	}
	
	public static Vect3D divVec(Vect3D v, float k)
	{
		//if(k == 0)
			//throw new ArithmeticException();
		return new Vect3D(v.x / k, v.y / k, v.z / k);
	}
	
	public static float dotProd(Vect3D v0, Vect3D v1)
	{
		return v0.x * v1.x + v0.y * v1.y + v0.z * v1.z;
	}
	
	public static float vecLen(Vect3D v)
	{
		return (float) Math.sqrt(dotProd(v, v));
	}

	public static Vect3D normVec(Vect3D v)
	{
		float l = vecLen(v);
		return divVec(v, l);
	}
	
	public static Vect3D crossProd(Vect3D v0, Vect3D v1)
	{
		return new Vect3D
		(
			v0.y * v1.z - v0.z * v1.y,
			v0.z * v1.x - v0.x * v1.z,
			v0.x * v1.y - v0.y * v1.x
		);
	}
	
	public static Vect3D mulVecMat(Vect3D v, float[][] m)
	{
		return new Vect3D
		(
			v.x * m[0][0] + v.y * m[1][0] + v.z * m[2][0] + v.w * m[3][0],
			v.x * m[0][1] + v.y * m[1][1] + v.z * m[2][1] + v.w * m[3][1],
			v.x * m[0][2] + v.y * m[1][2] + v.z * m[2][2] + v.w * m[3][2],
			v.x * m[0][3] + v.y * m[1][3] + v.z * m[2][3] + v.w * m[3][3]
		);
	}
	
	public static Vect3D interPlane(Vect3D planeP, Vect3D planeN, Vect3D lineStart, Vect3D lineEnd, float[] t)
	{
		planeN = normVec(planeN); // original NO NEW PLANEN
		float planeD = - dotProd(planeN, planeP);
		float ad = dotProd(lineStart, planeN);
		float bd = dotProd(lineEnd, planeN);
		t[0] = (- planeD - ad) / (bd - ad);
		Vect3D lineStartToEnd = subVec(lineEnd, lineStart);
		Vect3D lineToIntersect = mulVec(lineStartToEnd, t[0]);
		
		return addVec(lineStart, lineToIntersect);
	}
	
	/*********************************************************************/
	/* Matrix functions                                                  */
	/*********************************************************************/
	
	public static float[][] identityMat()
	{
		float[][] m = new float[4][4];
		
		m[0][0] = 1.0f;
		m[1][1] = 1.0f;
		m[2][2] = 1.0f;
		m[3][3] = 1.0f;
		
		return m;
	}
	
	public static float[][] xRotMat(float a)
	{
		float[][] m = new float[4][4];
		
		m[0][0] = 1.0f;
		m[1][1] = (float) Math.cos(a);
		m[1][2] = (float) Math.sin(a);
		m[2][1] = (float) - Math.sin(a);
		m[2][2] = (float) Math.cos(a);
		m[3][3] = 1.0f;
		
		return m;
	}
	
	public static float[][] yRotMat(float a)
	{
		float[][] m = new float[4][4];
		
		m[0][0] = (float) Math.cos(a);
		m[1][1] = 1.0f;
		m[0][2] = (float) - Math.sin(a);
		m[2][0] = (float) Math.sin(a);
		m[2][2] = (float) Math.cos(a);
		m[3][3] = 1.0f;
		
		return m;
	}
	
	public static float[][] zRotMat(float a)
	{
		float[][] m = new float[4][4];
		
		m[0][0] = (float) Math.cos(a);
		m[0][1] = (float) Math.sin(a);
		m[1][0] = (float) - Math.sin(a);
		m[1][1] = (float) Math.cos(a);
		m[2][2] = 1.0f;
		m[3][3] = 1.0f;
		
		return m;
	}
	
	public static float[][] transMat(float x, float y, float z)
	{
		float[][] m = new float[4][4];
		
		m[0][0] = 1.0f;
		m[1][1] = 1.0f;
		m[2][2] = 1.0f;
		m[3][3] = 1.0f;
		m[3][0] = x;
		m[3][1] = y;
		m[3][2] = z;
		
		return m;
	}
	
	public static float[][] projMat(float fovDegrees, float aspectRatio, float near, float far)
	{
		float fovRad = (float) (1.0f / Math.tan(fovDegrees * 0.5f / 180.0f * PI));
		float[][] m = new float[4][4];
		
		m[0][0] = aspectRatio * fovRad;
		m[1][1] = fovRad;
		m[2][2] = far / (far - near);
		m[3][2] = (- far * near) / (far - near);
		m[2][3] = 1.0f;
		m[3][3] = 0.0f;
		
		return m;
	}
	
	public static float[][] mulMat(float[][] m0, float[][] m1)
	{
		float[][] m = new float[4][4];
		
		for(byte c = 0; c < 4; c++)
			for(byte r = 0; r < 4; r++)
				m[r][c] = m0[r][0] * m1[0][c] + m0[r][1] * m1[1][c] + m0[r][2] * m1[2][c] + m0[r][3] * m1[3][c];
		
		return m;
	}
	
	public static float[][] pointAtMat(Vect3D pos, Vect3D target, Vect3D up)
	{
		Vect3D newForward = subVec(target, pos);
		newForward = normVec(newForward);

		Vect3D a = mulVec(newForward, dotProd(up, newForward));
		Vect3D newUp = subVec(up, a);
		newUp = normVec(newUp);

		Vect3D newRight = crossProd(newUp, newForward);

		float[][] m = new float[4][4];
		
		m[0][0] = newRight.x;	m[0][1] = newRight.y;	m[0][2] = newRight.z;	m[0][3] = 0f;
		m[1][0] = newUp.x;		m[1][1] = newUp.y;		m[1][2] = newUp.z;		m[1][3] = 0f;
		m[2][0] = newForward.x;	m[2][1] = newForward.y;	m[2][2] = newForward.z;	m[2][3] = 0f;
		m[3][0] = pos.x;		m[3][1] = pos.y;		m[3][2] = pos.z;		m[3][3] = 1f;
		
		return m;
	}
	
	public static float[][] lookAtMat(float[][] m0) // Only for Rotation/Translation Matrices (can only use PointAt matrix)
	{
		float[][] m = new float[4][4];
		
		m[0][0] = m0[0][0]; m[0][1] = m0[1][0]; m[0][2] = m0[2][0]; m[0][3] = 0.0f;
		m[1][0] = m0[0][1]; m[1][1] = m0[1][1]; m[1][2] = m0[2][1]; m[1][3] = 0.0f;
		m[2][0] = m0[0][2]; m[2][1] = m0[1][2]; m[2][2] = m0[2][2]; m[2][3] = 0.0f;
		m[3][0] = -(m0[3][0] * m[0][0] + m0[3][1] * m[1][0] + m0[3][2] * m[2][0]);
		m[3][1] = -(m0[3][0] * m[0][1] + m0[3][1] * m[1][1] + m0[3][2] * m[2][1]);
		m[3][2] = -(m0[3][0] * m[0][2] + m0[3][1] * m[1][2] + m0[3][2] * m[2][2]);
		m[3][3] = 1.0f;
		
		return m;
	}
	
	
	/*********************************************************************/
	/* Other functions                                                   */
	/*********************************************************************/
	
	public static byte clipAgainstPlane(Vect3D planeP, Vect3D planeN, Triangle inTri, Triangle outTri0, Triangle outTri1)
	{
		Vect3D planeNn = normVec(planeN);
		
		Vect3D[] insidePoints = new Vect3D[3];
		Vect3D[] outsidePoints = new Vect3D[3];
		byte nInsidePointCount  = 0;
		byte nOutsidePointCount = 0;
		
		Vect2D[] insideTex = new Vect2D[3];
		Vect2D[] outsideTex = new Vect2D[3];		
		byte nInsideTexCount  = 0;
		byte nOutsideTexCount = 0;
		
		float d0 = distance(inTri.p[0], planeNn, planeP);
		float d1 = distance(inTri.p[1], planeNn, planeP);
		float d2 = distance(inTri.p[2], planeNn, planeP);
		
		if(d0 >= 0)
		{
			insidePoints[nInsidePointCount++] = inTri.p[0];
			insideTex[nInsideTexCount++] = inTri.t[0];
		}
		else
		{
			outsidePoints[nOutsidePointCount++] = inTri.p[0];
			outsideTex[nOutsideTexCount++] = inTri.t[0];
		}
		if(d1 >= 0)
		{
			insidePoints[nInsidePointCount++] = inTri.p[1];
			insideTex[nInsideTexCount++] = inTri.t[1];
		}
		else
		{
			outsidePoints[nOutsidePointCount++] = inTri.p[1];
			outsideTex[nOutsideTexCount++] = inTri.t[1];
		}
		if(d2 >= 0)
		{
			insidePoints[nInsidePointCount++] = inTri.p[2];
			insideTex[nInsideTexCount++] = inTri.t[2];
		}
		else
		{ 
			outsidePoints[nOutsidePointCount++] = inTri.p[2];
			outsideTex[nOutsideTexCount++] = inTri.t[2];
		}
		
		if(nInsidePointCount == 0)
			return 0;
		
		if(nInsidePointCount == 3)
		{
			outTri0 = inTri; // ****************** original NO CLONE
			
			return 1;
		}
		
		if(nInsidePointCount == 1 && nOutsidePointCount == 2)
		{
			outTri0.lum = inTri.lum;
			
			outTri0.p[0] = insidePoints[0]; // ****************** original NO CLONE
			outTri1.t[0] = insideTex[0]; // ****************** original NO CLONE
			
			float[] t = new float[1];
			outTri0.p[1] = interPlane(planeP, planeNn, insidePoints[0], outsidePoints[0], t);
			outTri0.t[1].u = t[0] * (outsideTex[0].u - insideTex[0].u) + insideTex[0].u;
			outTri0.t[1].v = t[0] * (outsideTex[0].v - insideTex[0].v) + insideTex[0].v;
			outTri0.t[1].w = t[0] * (outsideTex[0].w - insideTex[0].w) + insideTex[0].w;
		
			outTri0.p[2] = interPlane(planeP, planeNn, insidePoints[0], outsidePoints[1], t);
			outTri0.t[2].u = t[0] * (outsideTex[1].u - insideTex[0].u) + insideTex[0].u;
			outTri0.t[2].v = t[0] * (outsideTex[1].v - insideTex[0].v) + insideTex[0].v;
			outTri0.t[2].w = t[0] * (outsideTex[1].w - insideTex[0].w) + insideTex[0].w;
		
			return 1;
		}
		
		if(nInsidePointCount == 2 && nOutsidePointCount == 1)
		{
			outTri0.lum = inTri.lum;
			outTri1.lum = inTri.lum;
			
			outTri0.p[0] = insidePoints[0]; // ****************** original NO CLONE
			outTri0.p[1] = insidePoints[1]; // ****************** original NO CLONE
			outTri0.t[0] = insideTex[0]; // ****************** original NO CLONE
			outTri0.t[1] = insideTex[1]; // ****************** original NO CLONE
		
			float[] t = new float[1];
			outTri0.p[2] = interPlane(planeP, planeNn, insidePoints[0], outsidePoints[0], t);
			outTri0.t[2].u = t[0] * (outsideTex[0].u - insideTex[0].u) + insideTex[0].u;
			outTri0.t[2].v = t[0] * (outsideTex[0].v - insideTex[0].v) + insideTex[0].v;
			outTri0.t[2].w = t[0] * (outsideTex[0].w - insideTex[0].w) + insideTex[0].w;
	
			outTri1.p[0] = insidePoints[1]; // ****************** original NO CLONE
			outTri1.t[0] = insideTex[1]; // ****************** original NO CLONE
			outTri1.p[1] = outTri0.p[2]; // CLONE????
			outTri1.t[1] = outTri0.t[2]; // CLONE????
			outTri1.p[2] = interPlane(planeP, planeNn, insidePoints[1], outsidePoints[0], t);
			outTri1.t[2].u = t[0] * (outsideTex[0].u - insideTex[1].u) + insideTex[1].u;
			outTri1.t[2].v = t[0] * (outsideTex[0].v - insideTex[1].v) + insideTex[1].v;
			outTri1.t[2].w = t[0] * (outsideTex[0].w - insideTex[1].w) + insideTex[1].w;
			
			return 2;
		}
		
		return -1;
	}
	
	private static float distance(Vect3D p, Vect3D planeN, Vect3D planeP)
	{ 
		Vect3D n = normVec(p);
		return planeN.x * n.x + planeN.y * n.y + planeN.z * n.z - dotProd(planeN, planeP);
	} 
}
