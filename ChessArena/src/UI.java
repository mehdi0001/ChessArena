import java.util.Scanner;

public class UI {
	static long BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L,NP=0L,NN=0L,NB=0L,NR=0L,NQ=0L,NK=0L,EP=0L;
	static boolean CWK=true,CWQ=true,CBK=true,CBQ=true,MouveB=true;//true=castle is possible
	public static void main(String[] args) {
		Monjeu();
	}
    public static void Monjeu() {
    	//pion.EchiquierInitiale();
    	//System.out.println("\n\n");
    	//Moves.MouvementCandidatB(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
    	//MouvementKing.danger_N(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK);
    	//Moves.MouvementCandidatN(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
    	//MouvementKing.danger_B(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK);
        //pion.stringFen("r3k2r/p1pNqpb1/bn2pnp1/3P4/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 1");
    	//pion.stringFen("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
    	/*pion.stringFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    	pion.drawArray(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK);
    	testPerformance.performanceOpt (BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK, EP, CWK, CWQ, CBK, CBQ, MouveB, 0);
        if (testPerformance.cptPerformanceMove==0) {
            if (MouveB) {
            	testPerformance.cptPerformanceMove=Moves.MouvementCandidatB(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK, EP, CWK, CWQ, CBK, CBQ).length()/4;
            } else {
            	testPerformance.cptPerformanceMove=Moves.MouvementCandidatN(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK, EP, CWK, CWQ, CBK, CBQ).length()/4;
            }
        }
        System.out.print("Total: "+testPerformance.cptPerformanceMove);
    	//System.out.print(testPerformance.cptMove);*/
        UCI.uciCommunication();
        pion.stringFen("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        pion.drawArray(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK);
        long startTime=System.currentTimeMillis();
        testPerformance.performanceOpt (BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK, EP, CWK, CWQ, CBK, CBQ, MouveB, 0);
        long endTime=System.currentTimeMillis();
        System.out.println("Nodes: "+testPerformance.cptPerformanceMove);
        System.out.println("That took "+(endTime-startTime)+" milliseconds");
        System.out.println("Nodes Per Second: "+(int)(testPerformance.cptPerformanceMove/((endTime-startTime)/1000.0)));
    }

}
