// Sean Li, sejli, pa3
public class Matrix{
	// fields
	private List[] entries;
	private int size;

	//private Entry class
	private class Entry{
		int column;
		double value;

		private Entry(int a, double b){
			column = a;
			value = b;
		}

		public String toString(){
			String s = "(" + column + ", " + value + ")";
			return s;
		}

		public boolean equals(Object x){
			boolean eq = false;
			if(x instanceof Entry){
				Entry that = (Entry) x;
				eq = (this.column == that.column);
				eq = (this.value == that.value);
			}
			return eq;
		}
	}

	// Constructor
	// Makes a new n x n zero Matrix. pre: n>=1
	public Matrix(int n){
		entries = new List[n + 1];
		size = n;
		for(int i = 0; i < entries.length; i++){
			entries[i] = new List();
		}
	}

	// Access functions

	// getSize(): Returns n, the number of rows and columns of this Matrix
	public int getSize(){
		return size;
	}

	// getNNZ(): Returns the number of non-zero entries in this Matrix
	int getNNZ(){
		int count = 0;
		for(int i = 0; i < entries.length; i++){
			count += entries[i].length();
		}
		return count;
	}

	// equals(): overrides Object's equals() method
	public boolean equals(Object x){
		Matrix M;
		if(x instanceof Matrix){
			M = (Matrix) x;
			if(this.getSize() != M.getSize()){
				return false;
			}
			for(int i = 0; i < this.entries.length; i++){
				if(!this.entries[i].equals(M.entries[i])){
					return false;
				}
			}
		}
		return true;
	}

	// Manipulation procedures

	// makeZero(): sets this Matrix to the zero state 
	void makeZero(){
		for(int i = 0; i < entries.length; i++){
			entries[i].clear();
		}
	}

	// changeEntry(): changes ith row, jth column of this Matrix to x
	// pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x){
		if(i < 1 || i > this.getSize() || j < 1 || j > this.getSize()){
			throw new RuntimeException("error: calling changeEntry() on out of bounds indices");
		}
		Entry E = new Entry(j, x);
		if(entries[i].length() == 0){
			if(x == 0){
				return;
			}
			entries[i].append(E);
			return;
		}
		else{
			entries[i].moveFront();			
		}
		while(entries[i].index() >= 0){
			Entry N = (Entry) entries[i].get();
			if(j < N.column){
				if(x == 0){
					return;
				}
				else{
					entries[i].insertBefore(E);
					return;
				}
			}
			else if(j == N.column){
				if(x == 0){
					entries[i].delete();
					return;
				}
				else{
					entries[i].insertAfter(E);
					entries[i].delete();
					return;
				}
			}
			else{
				entries[i].moveNext();
			}
		}
		if(x == 0){
			return;
		}
		else
			entries[i].append(E);
	}

	// copy(): returns a new Matrix having the same entries as this Matrix
	Matrix copy(){
		Matrix M = new Matrix(this.getSize());
		int a;
		double b;
		Entry E;
		for(int i = 0; i < this.entries.length; i++){
			if(this.entries[i].length() > 0){
				this.entries[i].moveFront();
				for(int j = 0; j < this.entries[i].length(); j++){
					E = (Entry) this.entries[i].get();
					a = E.column;
					b = E.value;
					M.changeEntry(i, a, b);
					this.entries[i].moveNext();
				}
			}
		}
		return M;
	}

	// toString(): overrides Object's toString() method
	public String toString(){
		String s = "";
		for(int i = 0; i < entries.length; i++){
			if(entries[i].length() != 0){
				s += String.valueOf(i);
				s += ": ";
				entries[i].moveFront();
				for(int j = 0; j < entries[i].length(); j++){
					s += entries[i].get().toString();
					s += " ";
					entries[i].moveNext();
				}
				s += "\n";
			}
		}
		return s;
	}

	// scalarMult():  returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x){
		Matrix M = new Matrix(this.getSize());
		int a;
		double b;
		Entry E;
		for(int i = 0; i < this.entries.length; i++){
			if(this.entries[i].length() > 0){
				this.entries[i].moveFront();
				for(int j = 0; j < this.entries[i].length(); j++){
					E = (Entry) this.entries[i].get();
					a = E.column;
					b = E.value * x;
					M.changeEntry(i, a, b);
					this.entries[i].moveNext();
				}
			}
		}
		return M;
	}

	// add(): returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix add(Matrix M){
		if(M.getSize() != this.getSize()){
			throw new RuntimeException("error: calling add() with incompatible matrices");
		}
		Matrix A = new Matrix(this.getSize());
		Matrix copy = M.copy();
		Entry E, F;
		for(int i = 1; i < this.entries.length; i++){
				if(this.entries[i].length() == 0 && copy.entries[i].length() > 0){
					copy.entries[i].moveFront();
					for(int k = 0; k < copy.entries[i].length(); k++){
						E = (Entry) copy.entries[i].get();
						A.changeEntry(i, E.column, E.value);
						copy.entries[i].moveNext();
					}
				}
				if(copy.entries[i].length() == 0 && this.entries[i].length() > 0){
					this.entries[i].moveFront();
					for(int l = 0; l < this.entries[i].length(); l++){
						E = (Entry) this.entries[i].get();
						A.changeEntry(i, E.column, E.value);
						copy.entries[i].moveNext();
					}
				}
				if(copy.entries[i].length() > 0 && this.entries[i].length() > 0){
					copy.entries[i].moveFront();
					this.entries[i].moveFront();
					for(int h = 0; h < this.getSize(); h++){
						if(copy.entries[i].index() < 0 && this.entries[i].index() >= 0){
							E = (Entry) this.entries[i].get();
							A.changeEntry(i, E.column, E.value);
							this.entries[i].moveNext();
						}
						else if(copy.entries[i].index() >= 0 && this.entries[i].index() < 0){
							E = (Entry) copy.entries[i].get();
							A.changeEntry(i, E.column, E.value);
							copy.entries[i].moveNext();
						}
						else if(copy.entries[i].index() >= 0 && this.entries[i].index() >= 0){

							E = (Entry) copy.entries[i].get();
							F = (Entry) this.entries[i].get();
							if(E.column < F.column){
								A.changeEntry(i, E.column, E.value);
								copy.entries[i].moveNext();
							}
							else if(E.column > F.column){
								A.changeEntry(i, F.column, F.value);
								this.entries[i].moveNext();
							}
							else if(E.column == F.column){
								A.changeEntry(i, E.column, E.value + F.value);
								this.entries[i].moveNext();
								copy.entries[i].moveNext();
							}
						}
					}
				}
			}
		return A;
	}

	// sub(): returns a new Matrix that is the difference of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix sub(Matrix M){
		if(M.getSize() != this.getSize()){
			throw new RuntimeException("error: calling add() with incompatible matrices");
		}
		Matrix A = new Matrix(this.getSize());
		Matrix copy = M.copy();
		Entry E, F;
		for(int i = 1; i < this.entries.length; i++){
				if(this.entries[i].length() == 0 && copy.entries[i].length() > 0){
					copy.entries[i].moveFront();
					for(int k = 0; k < copy.entries[i].length(); k++){
						E = (Entry) copy.entries[i].get();
						A.changeEntry(i, E.column, E.value * -1);
						copy.entries[i].moveNext();
					}
				}
				if(copy.entries[i].length() == 0 && this.entries[i].length() > 0){
					this.entries[i].moveFront();
					for(int l = 0; l < this.entries[i].length(); l++){
						E = (Entry) this.entries[i].get();
						A.changeEntry(i, E.column, E.value);
						copy.entries[i].moveNext();
					}
				}
				if(copy.entries[i].length() > 0 && this.entries[i].length() > 0){
					copy.entries[i].moveFront();
					this.entries[i].moveFront();
					for(int h = 0; h < this.getSize(); h++){
						if(copy.entries[i].index() < 0 && this.entries[i].index() >= 0){
							E = (Entry) this.entries[i].get();
							A.changeEntry(i, E.column, E.value);
							this.entries[i].moveNext();
						}
						else if(copy.entries[i].index() >= 0 && this.entries[i].index() < 0){
							E = (Entry) copy.entries[i].get();
							A.changeEntry(i, E.column, E.value * -1);
							copy.entries[i].moveNext();
						}
						else if(copy.entries[i].index() >= 0 && this.entries[i].index() >= 0){

							E = (Entry) copy.entries[i].get();
							F = (Entry) this.entries[i].get();
							if(E.column < F.column){
								A.changeEntry(i, E.column, -1 * E.value);
								copy.entries[i].moveNext();
							}
							else if(E.column > F.column){
								A.changeEntry(i, F.column, F.value);
								this.entries[i].moveNext();
							}
							else if(E.column == F.column){
								A.changeEntry(i, E.column, F.value - E.value);
								this.entries[i].moveNext();
								copy.entries[i].moveNext();
							}
						}
					}
				}
			}
		return A;

		}

	// transpose(): returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){
		Matrix A = new Matrix(this.getSize());
		Entry E;
		for(int i = 1; i < this.getSize() + 1; i++){
			if(this.entries[i].length() > 0){
				this.entries[i].moveFront();
				for(int j = 1; j < this.getSize() + 1; j++){
					if(this.entries[i].index() < 0){
						break;
					}
					else{
						E = (Entry) this.entries[i].get();
						A.changeEntry(E.column, i, E.value);
						this.entries[i].moveNext();
					}
				}
			}
		}
		return A;
	}

	// private dot(): computes dot product of two lists
	private static double dot(List P, List Q){
		double sum = 0;
		P.moveFront();
		Q.moveFront();
		Entry E, F;
		if(P.length() == 0 || Q.length() == 0){
			return 0;
		}
		else{
			int a;
			if(P.length() > Q.length()){
				a = P.length();
			}
			else{
				a = Q.length();
			}
			//System.out.println("a: "+a);
			//for(int i = 0; i < a; i++){
				while(P.index() >= 0 && Q.index() >= 0){
					E = (Entry) P.get();
					F = (Entry) Q.get();
					// System.out.println("E: "+E);
					// System.out.println("F: "+F);
					// System.out.println("\n");

					if(E.column < F.column){
						P.moveNext();
					}
					else if(E.column > F.column){
						Q.moveNext();
					}
					else if (E.column == F.column){
						sum += (E.value * F.value);
						P.moveNext();
						Q.moveNext();
					}
				}
			//}
		}
		return sum;
	}

	// mult(): returns a new Matrix that is the product of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix mult(Matrix M){
		if(this.getSize() != M.getSize()){
			throw new RuntimeException("error: calling mult() on incompatible matrices");
		}
		Matrix T = M.transpose();
		Matrix A = new Matrix(this.getSize());
		for(int i = 1; i < this.getSize() + 1; i++){
			for(int j = 1; j < T.getSize() + 1; j++){
					A.changeEntry(i, j, dot(this.entries[i], T.entries[j]));
			}
		}
		return A;
	}
}