import java.util.Random;

public class Matrix {
	
	public double[][] data;
	private int n;
	
	public Matrix(double[][] other)
	{
		data = other;
		n = other.length;
	}
	
	public Matrix(int n){
		data = new double[n][n];
	}
	
	public Matrix random() {
		int rows = data.length;
		int columns = data[0].length;
		Random rand = new Random();
		for (int i = 0; i < rows; i++) 
			for (int j = 0; j < columns; j++) 
				data[i][j] = rand.nextDouble();

		Matrix result = new Matrix(data);
		return result;
	}
	
	private Matrix add(Matrix other){
		double temp[][] = new double[data.length][data.length];
		for (int i = 0; i < data.length; i++){
			for (int j = 0; j < data.length; j++){
				temp[i][j] = data[i][j] + other.data[i][j];
			}
		}
		
		Matrix result = new Matrix(temp);
		return result;
	}
	
	private Matrix subtract(Matrix other){
		double[][] temp  = new double[data.length][data.length];
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data.length; j++)
				temp[i][j] = data[i][j] - other.data[i][j];
		
		Matrix result = new Matrix(temp);
		return result;
	}
	
	public Matrix productRegular(Matrix other)
	{
		double temp[][] = new double[n][n];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				for (int k = 0; k < n; k++)
					temp[i][j] += (data[i][k] * other.data[k][j]);
		
		Matrix result = new Matrix(temp);
		return result;
	}
	
	public Matrix productStrassen(Matrix other){
		int n = data.length;
		
		if (n < 2){
			return productRegular(other);
		}
		else{
			int newSize = n / 2;
			double[][] a11 = new double [newSize][newSize];
			double[][] a12 = new double [newSize][newSize];
			double[][] a21 = new double [newSize][newSize];
			double[][] a22 = new double [newSize][newSize];
			
			double[][] b11 = new double [newSize][newSize];
			double[][] b12 = new double [newSize][newSize];
			double[][] b21 = new double [newSize][newSize];
			double[][] b22 = new double [newSize][newSize];
			
			double[][] aResult = new double [newSize][newSize];
			double[][] bResult = new double [newSize][newSize];
			
			Matrix A = new Matrix(aResult);
			Matrix B = new Matrix(bResult);
			
			
			for (int i = 0; i < newSize; i++){
				for (int j = 0; j < newSize; j++){
					a11[i][j] = data[i][j];
					a12[i][j] = data[i][j + newSize];
					a21[i][j] = data[i + newSize][j];
					a22[i][j] = data[i + newSize][j + newSize];
					
					b11[i][j] = other.data[i][j];
					b12[i][j] = other.data[i][j + newSize];
					b21[i][j] = other.data[i + newSize][j];
					b22[i][j] = other.data[i + newSize][j + newSize];
				}
			}
			
			Matrix A11 = new Matrix(a11);
			Matrix A12 = new Matrix(a12);
			Matrix A21 = new Matrix(a21);
			Matrix A22 = new Matrix(a22);
			
			Matrix B11 = new Matrix(b11);
			Matrix B12 = new Matrix(b12);
			Matrix B21 = new Matrix(b21);
			Matrix B22 = new Matrix(b22);
			
			A = A11.add(A22);
			B = B11.add(B22);
			Matrix P1 = A.productStrassen(B);
			
			A = A21.add(A22);
			Matrix P2 = A.productStrassen(B11);
			
			B = B12.subtract(B22);
			Matrix P3 = A11.productStrassen(B);
			
			B = B21.subtract(B11);
			Matrix P4 = A22.productStrassen(B);
			
			A = A11.add(A12);
			Matrix P5 = A.productStrassen(B22);
			
			A = A21.subtract(A11);
			B = B11.add(B12);
			Matrix P6 = A.productStrassen(B);
			
			A = A12.subtract(A22);
			B = B21.add(B22);
			Matrix P7 = A.productStrassen(B);
			
			Matrix C12 = P3.add(P5);
			Matrix C21 = P2.add(P4);
			
			A = P1.add(P4);
			B = A.add(P7);
			Matrix C11 = B.subtract(P5);
			
			A = P1.add(P3);
			B = A.add(P6);
			Matrix C22 = B.subtract(P2);
			
			double[][]c = new double[n][n];
			Matrix C = new Matrix(c);
			for(int i = 0; i < newSize; i++){
				for(int j = 0; j < newSize; j++){
					C.data[i][j] = C11.data[i][j];
					C.data[i][j + newSize] = C12.data[i][j];
					C.data[i + newSize][j] = C21.data[i][j];
					C.data[i + newSize][j + newSize] = C22.data[i][j];
				}
			}
			return C;
		}
	}
}
