
import java.text.MessageFormat;
import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class MatrixOps{
	static Pairing pairing = PairingFactory.getPairing("d224.properties");
	/**
	 * Method that multiplies two matrices and returns the result
	 *
	 * @param x first matrix
	 * @param y second matrix
	 *
	 * @return result after multiplication
	 */
	//static Pairing pairing = PairingFactory.getPairing("d224.properties");
	//static Field pairing.getZr() = pairing.getpairing.getZr()();
	public static Element multiplyArrays (Element[] x, Element[] y) {
		int xRows;

		xRows = x.length;
		Element result = pairing.getZr().newZeroElement();
		
		
		for (int i = 0; i < xRows; i++)
			result.add( pairing.getZr().newOneElement().mulZn(x[i]).mulZn(y[i]) );


		return (result);
	}
	
	public static Element[] multiplyArrayMatrix (Element[] x, Element[][] y) {
		int xColumns, xRows, yColumns, yRows;

		xRows = x.length;
		yRows = y.length;
		yColumns = y[0].length;
		Element[] result = new Element[xRows];
		
		for (int i = 0; i < xRows; i++) 
			result[i] = pairing.getZr().newZeroElement();
			

		for (int i = 0; i < xRows; i++) 
			for (int j = 0; j < yColumns; j++) 
				result[i].add( pairing.getZr().newOneElement().mulZn(x[j]).mulZn(y[j][i]) );


		return (result);
	}
	
	public static Element[] multiplyMatrixArray (Element[][] y, Element[] x) {
		int xColumns, xRows, yColumns, yRows;

		xRows = x.length;
		yRows = y.length;
		yColumns = y[0].length;
		Element[] result = new Element[xRows];
		
		for (int i = 0; i < xRows; i++) 
			result[i] = pairing.getZr().newZeroElement();
			

		for (int i = 0; i < xRows; i++) 
			for (int j = 0; j < yColumns; j++) 
				result[i].add( pairing.getZr().newOneElement().mulZn(x[j]).mulZn(y[i][j]) );


		return (result);
	}

	
	public static Element[][] multiplyMatrices (Element[][] x, Element[][] y) {
		int xColumns, xRows, yColumns, yRows;

		xRows = x.length;
		xColumns = x[0].length;
		yRows = y.length;
		yColumns = y[0].length;
		Element[][] result = new Element[xRows][yColumns];
		
		for (int i = 0; i < xRows; i++) 
			for (int j = 0; j < yColumns; j++) 
				result[i][j] = pairing.getZr().newZeroElement();
			

		if (xColumns != yRows) {
			throw new IllegalArgumentException (
					MessageFormat.format ("Matrices don't match: {0} != {1}.", xColumns, yRows));
		}


		for (int i = 0; i < xRows; i++) 
			for (int j = 0; j < yColumns; j++) 
				for (int k = 0; k < xColumns; k++) 
					result[i][j].add(pairing.getZr().newZeroElement().add(x[i][k]).mulZn(y[k][j]));
				
			
		

		return (result);
	}

	/**
	 * Method that calculates determinant of given matrix
	 *
	 * @param matrix matrix of which we need to know determinant
	 *
	 * @return determinant of given matrix
	 */
	public static Element matrixDeterminant (Element[][] matrix) {
		Element temporary[][] = new Element[matrix.length - 1][matrix[0].length - 1];
		for (int i = 0; i < matrix.length-1; i++)
			for (int j = 0; j < matrix[0].length-1; j++)
				temporary[i][j] = pairing.getZr().newZeroElement();
		
		Element result = pairing.getZr().newOneElement();

		if (matrix.length != matrix[0].length)
			throw new IllegalStateException("invalid dimensions");

		if (matrix.length == 2) {
			result.mulZn(matrix[0][0]);
			result.mulZn(matrix[1][1]);
			result.sub(pairing.getZr().newOneElement().mulZn(matrix[0][1]).mulZn(matrix[1][0]));
			return (result);
		}
		
		result = pairing.getZr().newZeroElement();
				
		for (int i = 0; i < matrix[0].length; i++) {
			result.add( pairing.getZr().newOneElement().mulZn(matrix[0][i]).mulZn((pairing.getZr().newOneElement().negate()).powZn(pairing.getZr().newElement().set(i))).mulZn(matrixDeterminant(minor(matrix, 0, i))) );
			
		}
		return (result);		
	}
	

	public static Element[][] inverse(Element[][] matrix) {
		Element[][] inverse = new Element[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				inverse[i][j] = pairing.getZr().newZeroElement();
		// minors and cofactors
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				inverse[i][j].set( ( (pairing.getZr().newOneElement().negate()).powZn(pairing.getZr().newElement(i).add(pairing.getZr().newElement(j)))).mulZn(matrixDeterminant(minor(matrix, i, j))) );

		// adjugate and determinant
		Element det = pairing.getZr().newOneElement();
		det.set(matrixDeterminant(matrix).invert());
		
		for (int i = 0; i < inverse.length; i++) {
			for (int j = 0; j <= i; j++) {
				Element temp = pairing.getZr().newElement();
				temp = inverse[i][j].duplicate();
				inverse[i][j].set(pairing.getZr().newOneElement().mulZn(inverse[j][i]).mulZn(det));
				inverse[j][i].set(pairing.getZr().newOneElement().mulZn(temp).mulZn(det));
			}
		}

		return inverse;
	}

	public static Element[][] minor(Element[][] matrix, int row, int column) {
		Element[][] minor = new Element[matrix.length - 1][matrix.length - 1];
		for (int i = 0; i < matrix.length - 1; i++)
			for (int j = 0; j < matrix.length - 1; j++)
				minor[i][j] = pairing.getZr().newZeroElement();

		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; i != row && j < matrix[i].length; j++)
				if (j != column)
					minor[i < row ? i : i - 1][j < column ? j : j - 1].set(matrix[i][j]);
		return minor;
	}

	/**
	 * Method that prints matrix
	 *
	 * @param matrix matrix to print
	 * @param id     what does the matrix contain?
	 */

	/**
	 * Method that prints matrix
	 *
	 * @param matrix matrix to print
	 * @param id     what does the matrix contain?
	 */
	public static void printMatrix (Element[][] matrix) {
		int cols, rows;

		rows = matrix.length;
		cols = matrix[0].length;


		for (int i = 0; i < matrix.length; i++) {
			System.out.print ("[");

			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print (matrix[i][j]);
				if ((j + 1) != matrix[i].length) {
					System.out.print (", ");
				}
			}

			if ((i + 1) != matrix.length) {
				System.out.println ("]");
			} else {
				System.out.println ("].");
			}
		}

		System.out.println ();
	}
	
	public static void printArray (Element[] matrix) {
		int cols;

		cols = matrix.length;

		System.out.print ("[");
		for (int i = 0; i < matrix.length; i++) {
			System.out.print (matrix[i]);
			if ((i + 1) != matrix.length)
				System.out.print (", ");
		}
		System.out.println ("].");
		System.out.println ();
	}
}