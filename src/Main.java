import java.util.Date;
import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.util.Random;

public class Main {
	public static void main(String[] args)  {

        // Setup phase---------------------------------------------------------------------------------
        Pairing pairing = PairingFactory.getPairing("d224.properties");
		Field<Element> Zr = pairing.getZr();
		Field<Element> G1 = pairing.getG1();
		Field<Element> G2 = pairing.getG2();
		Field<Element> GT = pairing.getGT();
		Element g1 = G1.newRandomElement();
		Element g2 = G2.newRandomElement();
        int dimension = 10;
        //int S = (int) Math.pow(10, dimension);
        int S = (int) 100 * dimension;
        
       
		Element[][] B = new Element[dimension][dimension];
		
		for (int i = 0; i < B.length; i++) {
			for (int j = 0; j < B.length; j++) {
				B[i][j] = Zr.newRandomElement();
			}
		}

		Element det = MatrixOps.matrixDeterminant(B);

		Element[][] BStar = new Element[dimension][dimension];
		BStar = Setup.setBstar(Zr, G1, G2, GT, g1, g2, B, det);

		// ZKey generation phase-----------------------------------------------------------------------

		Element tsk = ZKeyGen.setTsk(Zr, G1, G2, GT, g1, g2);
		Element tpk = ZKeyGen.setTpk(Zr, G1, G2, GT, g1, g2, tsk);

		// Encrypt phase-------------------------------------------------------------------------------

		Element[] plaintext = new Element[dimension];
		for (int i = 0; i < dimension; i++) {
			plaintext[i] = Zr.newElement((int)(Math.random()*10));
		}
		//MatrixOps.printArray(plaintext);
		Encrypt enc = new Encrypt();
		
		Element C1 = G2.newRandomElement();
		C1 = enc.setC1(Zr, G1, G2, GT, g1, g2);
		Element[] C2 = new Element[dimension];
		C2 = enc.setC2(Zr, G1, G2, GT, g1, g2, plaintext, BStar);	
  
		// Functional decryption key phase-------------------------------------------------------------

		Element[] query = new Element[dimension];
		for (int i = 0; i < dimension; i++) {
			query[i] = Zr.newElement((int)(Math.random()*10));
		}
		//MatrixOps.printArray(query);
		KeyGen trapdoor = new KeyGen();
		
		Element K1 = G1.newRandomElement();
		
		K1 = trapdoor.setK1(Zr, G1, G2, GT, g1, g2, det);
		Element[] K2 = new Element[dimension];
		K2 = trapdoor.setK2(Zr, G1, G2, GT, g1, g2, query, B, tpk);	

		// Partial Decrypt phase-----------------------------------------------------------------------

		Element D1 = Transformation.setD1(Zr, G1, G2, GT, g1, g2, C1, K1);
		Element D2 = Transformation.setD2(Zr, G1, G2, GT, g1, g2, C2, K2);

		// Final Decrypt phase-------------------------------------------------------------------------

		System.out.println("Test result: " + Test.decrypt(Zr, G1, G2, GT, g1, g2, D1, D2, tsk, S));
		//Test.decrypt(Zr, G1, G2, GT, g1, g2, D1, D2, tsk, S);
		System.out.println("Inner product: " + MatrixOps.multiplyArrays(plaintext, query));
	

	}
}
