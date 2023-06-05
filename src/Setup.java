import java.util.Date;

import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


public class Setup {
	static Pairing pairing = PairingFactory.getPairing("d224.properties");

	
	public static Element[][] setBstar (Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element[][] B, Element det) {
		Element[][] result = new Element[B.length][B.length];

		result = MatrixOps.inverse(B);
		
		for (int i = 0; i < B.length; i++) {
			for(int j = 0; j < B.length; j++) {
				result[i][j].mulZn(det);
			}
		}
		return result;
	}
}
