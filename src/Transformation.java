import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class Transformation {
	public static Element setD1 (Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element C1, Element K1) {
		Element d1 = GT.newRandomElement();
		
		d1.set( Setup.pairing.pairing(K1, C1) );

		
		return d1;
		
	}
	
	public static Element setD2 (Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element[] C2, Element[] K2) {
		Element d2 = GT.newOneElement();
		
		for (int i = 0; i < C2.length; i++) {
			d2.mul(Setup.pairing.pairing(K2[i], C2[i]));	
		}
		
		return d2;
		
	}
}
