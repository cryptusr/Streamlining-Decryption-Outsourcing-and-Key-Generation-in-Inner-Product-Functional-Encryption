import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class Encrypt {
	static Pairing pairing = PairingFactory.getPairing("d224.properties");
	static Element beta = pairing.getZr().newRandomElement();
	
	public Element setC1 (Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2) {
		Element C1 = G2.newElement();
		C1.set(g2);
		C1.powZn(beta);
		return C1;
	}
	
	public Element[] setC2(Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element[] y, Element[][] BStar) {
		Element[] C2 = new Element[y.length];
		Element[] tmp = new Element[y.length];
		for (int i = 0; i < y.length; i++) {
			C2[i] = G2.newElement();
			C2[i].set(g2);
		}
		
		tmp = MatrixOps.multiplyMatrixArray(BStar, y);
		
		for (int i = 0; i < y.length; i++) {
			tmp[i].mulZn(beta);
			C2[i].powZn(tmp[i]);
		}
			
		return C2;
	}
}
