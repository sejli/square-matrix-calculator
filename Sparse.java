// Sean Li, sejli, pa3
import java.io.*;
import java.util.Scanner;

public class Sparse{
	public static void main(String[] args){
		if(args.length != 2){
			System.err.println("Usage: Sparse infile outfile");
			System.exit(1);
		}
		try{
			//System.out.println("grr");
			Scanner in = new Scanner(new File(args[0]));
			PrintWriter out = new PrintWriter(new FileWriter(args[1]));
			int n = in.nextInt();
			int a = in.nextInt();
			int b = in.nextInt();

			Matrix A = new Matrix(n);
			Matrix B = new Matrix(n);

			int x, y;
			double z;
			for(int i = 0; i < a; i++){
				x = in.nextInt();
				y = in.nextInt();
				z = in.nextDouble();
				//System.out.println(x + " " + y + " " + z);
				A.changeEntry(x, y, z);
			}

			for(int i = 0; i < b; i++){
				x = in.nextInt();
				y = in.nextInt();
				z = in.nextDouble();
				//System.out.println(x + " " + y + " " + z);
				B.changeEntry(x, y, z);
			}

			out.println("A has " + a + " non-zero entries:");
			out.println(A);

			out.println("B has " + b + " non-zero entries:");
			out.println(B);

			out.println("(1.5)*A = ");
			out.println(A.scalarMult(1.5));

			out.println("A+B =");
			out.println(A.add(B));

			out.println("A+A =");
			out.println(A.add(A));

			out.println("B-A =");
			out.println(B.sub(A));

			out.println("A-A =");
			out.println(A.sub(A));

			out.println("Transpose (A) =");
			out.println(A.transpose());

			out.println("A*B =");
			out.println(A.mult(B));

			out.println("B*B =");
			out.println(B.mult(B));

			in.close();
			out.close();
		}
		catch(Exception e){
			System.err.println("file not found");
			System.exit(1);
		}
	}
}