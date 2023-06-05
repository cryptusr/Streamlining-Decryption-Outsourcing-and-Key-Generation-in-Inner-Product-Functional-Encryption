import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class KeyGen {
	static Pairing pairing = PairingFactory.getPairing("d224.properties");
	static Element alpha = pairing.getZr().newRandomElement();
	
	public Element setK1 (Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element det) {
		Element K1 = G1.newElement();
		K1.set(g1);
		K1.powZn(Zr.newOneElement().mulZn(alpha).mulZn(det));
		return K1;
	}
	
	public Element[] setK2(Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element[] m,  Element[][] B, Element tpk) {
		Element[] K2 = new Element[m.length];
		Element[] tmp = new Element[m.length];
		Element[] tmp1 = new Element[m.length];
		
		
		for (int i = 0; i < m.length; i++) {
			tmp[i] = Zr.newOneElement();
			tmp[i].mulZn(alpha);
			tmp[i].mul(m[i]);
		}
		
		tmp1 = MatrixOps.multiplyArrayMatrix(tmp, B);
		
		for (int i = 0; i < m.length; i++) {
			K2[i] = G1.newElement();
			K2[i].set(tpk);
			K2[i].powZn(tmp1[i]);
		}
		return K2;
	}
}
