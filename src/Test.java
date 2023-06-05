import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class Test {
	public static Element decrypt (Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element D1, Element D2, Element tsk, int S) {
		Element d2 = D2.duplicate().powZn(tsk.duplicate().invert());
		for (int i = 0; i < S; i++) {
			if (d2.isEqual(D1.duplicate().powZn(Zr.newElement(i)))) {
				return Zr.newElement(i);
			}
		}
		return Zr.newElement(0);
		
	}
}
