
public class UI {
	static long BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L,NP=0L,NN=0L,NB=0L,NR=0L,NQ=0L,NK=0L;
	public static void main(String[] args) {
		Monjeu();
	}
    public static void Monjeu() {
    	pion.EchiquierInitiale();
    	Moves.MouvementCandidat("",BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK);
    	
    }

}
