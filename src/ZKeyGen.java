import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class ZKeyGen {
	static Pairing pairing = PairingFactory.getPairing("d224.properties");
	
	public static Element setTsk (Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2) {
		Element zsk = Zr.newRandomElement();
		return zsk;
	}
	
	public static Element setTpk(Field<Element> Zr, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element g1, Element g2, Element tsk) {
		Element zpk = g1.duplicate().powZn(tsk);
		return zpk;
	}
}
