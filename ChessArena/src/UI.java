
public class UI {
	static long BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L,NP=0L,NN=0L,NB=0L,NR=0L,NQ=0L,NK=0L,EP=0L;
	static boolean CWK=true,CWQ=true,CBK=true,CBQ=true,MouveB=true;//true=castle is possible
	public static void main(String[] args) {
		Monjeu();
	}
    public static void Monjeu() {
    	pion.EchiquierInitiale();
    	//System.out.println("\n\n");
    	//Moves.MouvementCandidatB(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
    	//MouvementKing.danger_N(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK);
    	//Moves.MouvementCandidatN(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
    	//MouvementKing.danger_B(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK);
    	testPerformance.perft(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK, EP, CWK, CWQ, CBK, CBQ, MouveB, 0);
    	System.out.print(testPerformance.cptMove);
    }

}
