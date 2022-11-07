
public class testPerformance {
    public static String moveToAlgebra(String move)
    {
        String moveString="";
        moveString+=""+(char)(move.charAt(1)+49);
        moveString+=""+('8'-move.charAt(0));
        moveString+=""+(char)(move.charAt(3)+49);
        moveString+=""+('8'-move.charAt(2));
        return moveString;
    }
    //static int perftTotalMoveCounter=0;
    static int cptMove=0;
    static int cptProfondeur=1;
    public static void perft(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean MouveB,int depth)
    {
        if (depth<cptProfondeur) {
            String moves;
            if (MouveB) {
                moves=Moves.MouvementCandidatB(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
            } else {
                moves=Moves.MouvementCandidatN(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
            }
            for (int i=0;i<moves.length();i+=4) {
                long BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'P'), BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'N'),
                        BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'B'), BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'R'),
                        BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'Q'), BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'K'),
                        NPt=Moves.makeMove(NP, moves.substring(i,i+4), 'p'), NNt=Moves.makeMove(NN, moves.substring(i,i+4), 'n'),
                        NBt=Moves.makeMove(NB, moves.substring(i,i+4), 'b'), NRt=Moves.makeMove(NR, moves.substring(i,i+4), 'r'),
                        NQt=Moves.makeMove(NQ, moves.substring(i,i+4), 'q'), NKt=Moves.makeMove(NK, moves.substring(i,i+4), 'k'),
                        EPt=Moves.makeMoveEP(BP|NP,moves.substring(i,i+4));
                boolean CWKt=CWK,CWQt=CWQ,CBKt=CBK,CBQt=CBQ;
                if (Character.isDigit(moves.charAt(3))) {//'regular' move
                    int start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                    if (((1L<<start)&BK)!=0) {CWKt=false; CWQt=false;}
                    if (((1L<<start)&NK)!=0) {CBKt=false; CBQt=false;}
                    if (((1L<<start)&BR&(1L<<63))!=0) {CWKt=false;}
                    if (((1L<<start)&BR&(1L<<56))!=0) {CWQt=false;}
                    if (((1L<<start)&NR&(1L<<7))!=0) {CBKt=false;}
                    if (((1L<<start)&NR&1L)!=0) {CBQt=false;}
                }
                if (((BKt&MouvementKing.danger_B(BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt))==0 && MouveB) ||
                        ((NKt&MouvementKing.danger_N(BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt))==0 && !MouveB)) {
                    if (depth+1==cptProfondeur) {cptMove++;}
                    perft(BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt,EPt,CWKt,CWQt,CBKt,CBQt,!MouveB,depth+1);
                }
            }
        }
    }
}
