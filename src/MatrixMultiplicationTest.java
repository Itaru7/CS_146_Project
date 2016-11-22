import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MatrixMultiplicationTest {
	public static void main(String[] args) {
		// create two double 2-D arrays
		double[][] a = new double[4][4];
		double[][] b = new double [4][4];
		Matrix m1 = new Matrix(a);
		Matrix m2 = new Matrix(b);
		m1.random();
		m2.random();
		System.out.println(m1);
		
		long startTimeStrassen = System.currentTimeMillis();
		Matrix productStrassen = m1.productStrassen(m2);
		long endTimeStrassen   = System.currentTimeMillis();
		long totalTimeStrassen = endTimeStrassen - startTimeStrassen;
		NumberFormat formatter = new DecimalFormat("#0.00000");
		System.out.println("Execution time for Strassen is " + formatter.format(totalTimeStrassen / 1000d) + " seconds");
		
		long startTimeRegular = System.currentTimeMillis();
		Matrix productRegular = m1.productRegular(m2);
		long endTimeRegular= System.currentTimeMillis();
		long totalTimeRegular = endTimeRegular - startTimeRegular;
		System.out.println("Execution time for Regular is " + formatter.format(totalTimeRegular / 1000d) + " seconds");
		
		System.out.println("Are matrices the same?"+productStrassen.equals(productRegular));
		System.out.println(productStrassen);
		
		/*
		for(int i = 0; i < productStrassen.data.length; i++){
			for(int j = 0; j < productStrassen.data[0].length; j++)
				System.out.print(productStrassen.data[i][j]);
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		
		
		for(int i = 0; i < productRegular.data.length; i++){
			for(int j = 0; j < productRegular.data[0].length; j++)
				System.out.print(productRegular.data[i][j]);
			System.out.println();
		}
		*/
	}
}
