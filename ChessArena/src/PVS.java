
public class PVS {

    public static int zWSearch(int beta,long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean MouveB,int depth) {//fail-hard zero window search, returns either beta-1 or beta
        int score = Integer.MIN_VALUE;
        //alpha == beta - 1
        //this is either a cut- or all-node
        if (depth == UI.profondeur)
        {
            score = Evaluation.evaluer();
            return score;
        }
        String moves;
        if (MouveB) {
            moves=Moves.MouvementCandidatB(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,NP,CWK,CWQ,CBK,CBQ);
        } else {
            moves=Moves.MouvementCandidatN(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
        }
        //sortMoves();
        for (int i=0;i<moves.length();i+=4) {
            long BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'P'), BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'N'),
                    BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'B'), BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'R'),
                    BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'Q'), BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'K'),
                    NPt=Moves.makeMove(NP, moves.substring(i,i+4), 'p'), NNt=Moves.makeMove(NN, moves.substring(i,i+4), 'n'),
                    NBt=Moves.makeMove(NB, moves.substring(i,i+4), 'b'), NRt=Moves.makeMove(NR, moves.substring(i,i+4), 'r'),
                    NQt=Moves.makeMove(NQ, moves.substring(i,i+4), 'q'), NKt=Moves.makeMove(NK, moves.substring(i,i+4), 'k'),
                    EPt=Moves.makeMoveEP(BP|NP,moves.substring(i,i+4));
            BRt=MouvementCastle.makeMoveCastle(BRt, BK|NK, moves.substring(i,i+4), 'R');
            NRt=MouvementCastle.makeMoveCastle(NRt, BK|NK, moves.substring(i,i+4), 'r');
            boolean CWKt=CWK,CWQt=CWQ,CBKt=CBK,CBQt=CBQ;
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                int start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                if (((1L<<start)&BK)!=0) {CWKt=false; CWQt=false;}
                else if (((1L<<start)&NK)!=0) {CBKt=false; CBQt=false;}
                else if (((1L<<start)&BR&(1L<<63))!=0) {CWKt=false;}
                else if (((1L<<start)&BR&(1L<<56))!=0) {CWQt=false;}
                else if (((1L<<start)&NR&(1L<<7))!=0) {CBKt=false;}
                else if (((1L<<start)&NR&1L)!=0) {CBQt=false;}
            }
            if (((BKt&MouvementKing.danger_B(BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt))==0 && MouveB) ||
                    ((NKt&MouvementKing.danger_N(BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt))==0 && !MouveB)) {
                score = -zWSearch(1 - beta,BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt,EPt,CWKt,CWQt,CBKt,CBQt,!MouveB,depth+1);
            }
            if (score >= beta)
            {
                return score;//fail-hard beta-cutoff
            }
        }
        return beta - 1;//fail-hard, return alpha
    }
    public static int getFirstLegalMove(String moves,long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean MouveB) {
        for (int i=0;i<moves.length();i+=4) {
            long BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'P'), BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'N'),
                    BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'B'), BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'R'),
                    BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'Q'), BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'K'),
                    NPt=Moves.makeMove(NP, moves.substring(i,i+4), 'p'), NNt=Moves.makeMove(NN, moves.substring(i,i+4), 'n'),
                    NBt=Moves.makeMove(NB, moves.substring(i,i+4), 'b'), NRt=Moves.makeMove(NR, moves.substring(i,i+4), 'r'),
                    NQt=Moves.makeMove(NQ, moves.substring(i,i+4), 'q'), NKt=Moves.makeMove(NK, moves.substring(i,i+4), 'k');
            BRt=MouvementCastle.makeMoveCastle(BRt, BK|NK, moves.substring(i,i+4), 'R');
            NRt=MouvementCastle.makeMoveCastle(NRt, BK|NK, moves.substring(i,i+4), 'r');
            if (((BKt&MouvementKing.danger_B(BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt))==0 && MouveB) ||
                    ((NKt&MouvementKing.danger_N(BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt))==0 && !MouveB)) {
                return i;
            }
        }
        return -1;
    }
    public static int pvSearch(int alpha,int beta,long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean MouveB,int depth) {//using fail soft with negamax
        int bestScore;
        int bestMoveIndex = -1;
        if (depth == UI.profondeur)
        {
            bestScore = Evaluation.evaluer();
            return bestScore;
        }
        String moves;
        if (MouveB) {
            moves=Moves.MouvementCandidatB(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
        } else {
            moves=Moves.MouvementCandidatN(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ);
        }
        //sortMoves();
        int firstLegalMove = getFirstLegalMove(moves,BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ,MouveB);
        if (firstLegalMove == -1)
        {
            return MouveB ? UI.MATE_SCORE : -UI.MATE_SCORE;
        }
        long BPt=Moves.makeMove(BP, moves.substring(firstLegalMove,firstLegalMove+4), 'P'), BNt=Moves.makeMove(BN, moves.substring(firstLegalMove,firstLegalMove+4), 'N'),
                BBt=Moves.makeMove(BB, moves.substring(firstLegalMove,firstLegalMove+4), 'B'), BRt=Moves.makeMove(BR, moves.substring(firstLegalMove,firstLegalMove+4), 'R'),
                BQt=Moves.makeMove(BQ, moves.substring(firstLegalMove,firstLegalMove+4), 'Q'), BKt=Moves.makeMove(BK, moves.substring(firstLegalMove,firstLegalMove+4), 'K'),
                NPt=Moves.makeMove(NP, moves.substring(firstLegalMove,firstLegalMove+4), 'p'), NNt=Moves.makeMove(NN, moves.substring(firstLegalMove,firstLegalMove+4), 'n'),
                NBt=Moves.makeMove(NB, moves.substring(firstLegalMove,firstLegalMove+4), 'b'), NRt=Moves.makeMove(NR, moves.substring(firstLegalMove,firstLegalMove+4), 'r'),
                NQt=Moves.makeMove(NQ, moves.substring(firstLegalMove,firstLegalMove+4), 'q'), NKt=Moves.makeMove(NK, moves.substring(firstLegalMove,firstLegalMove+4), 'k'),
                EPt=Moves.makeMoveEP(BP|NP,moves.substring(firstLegalMove,firstLegalMove+4));
        BRt=MouvementCastle.makeMoveCastle(BRt, BK|NK, moves.substring(firstLegalMove,firstLegalMove+4), 'R');
        NRt=MouvementCastle.makeMoveCastle(NRt, BK|NK, moves.substring(firstLegalMove,firstLegalMove+4), 'r');
        boolean CWKt=CWK,CWQt=CWQ,CBKt=CBK,CBQt=CBQ;
        if (Character.isDigit(moves.charAt(firstLegalMove+3))) {//'regular' move
            int start=(Character.getNumericValue(moves.charAt(firstLegalMove))*8)+(Character.getNumericValue(moves.charAt(firstLegalMove+1)));
            if (((1L<<start)&BK)!=0) {CWKt=false; CWQt=false;}
            else if (((1L<<start)&NK)!=0) {CBKt=false; CBQt=false;}
            else if (((1L<<start)&BR&(1L<<63))!=0) {CWKt=false;}
            else if (((1L<<start)&BR&(1L<<56))!=0) {CWQt=false;}
            else if (((1L<<start)&NR&(1L<<7))!=0) {CBKt=false;}
            else if (((1L<<start)&NR&1L)!=0) {CBQt=false;}
        }
        bestScore = -pvSearch(-beta,-alpha,BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt,EPt,CWKt,CWQt,CBKt,CBQt,!MouveB,depth+1);
        UI.Mouvecpt++;
        if (Math.abs(bestScore) == UI.MATE_SCORE)
        {
            return bestScore;
        }
        if (bestScore > alpha)
        {
            if (bestScore >= beta)
            {
                //This is a refutation move
                //It is not a PV move
                //However, it will usually cause a cutoff so it can
                //be considered a best move if no other move is found
                return bestScore;
            }
            alpha = bestScore;
        }
        bestMoveIndex = firstLegalMove;
        for (int i=firstLegalMove;i<moves.length();i+=4) {
            int score;
            UI.Mouvecpt++;
            //legal, non-castle move
            BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'P');
            BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'N');
            BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'B');
            BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'R');
            BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'Q');
            BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'K');
            NPt=Moves.makeMove(NP, moves.substring(i,i+4), 'p');
            NNt=Moves.makeMove(NN, moves.substring(i,i+4), 'n');
            NBt=Moves.makeMove(NB, moves.substring(i,i+4), 'b');
            NRt=Moves.makeMove(NR, moves.substring(i,i+4), 'r');
            NQt=Moves.makeMove(NQ, moves.substring(i,i+4), 'q');
            NKt=Moves.makeMove(NK, moves.substring(i,i+4), 'k');
            EPt=Moves.makeMoveEP(BP|NP,moves.substring(i,i+4));
            BRt=MouvementCastle.makeMoveCastle(BRt, BK|NK, moves.substring(i,i+4), 'R');
            NRt=MouvementCastle.makeMoveCastle(NRt, BK|NK, moves.substring(i,i+4), 'r');
            CWKt=CWK;
            CWQt=CWQ;
            CBKt=CBK;
            CBQt=CBQ;
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                int start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                if (((1L<<start)&BK)!=0) {CWKt=false; CWQt=false;}
                else if (((1L<<start)&NK)!=0) {CBKt=false; CBQt=false;}
                else if (((1L<<start)&BR&(1L<<63))!=0) {CWKt=false;}
                else if (((1L<<start)&BR&(1L<<56))!=0) {CWQt=false;}
                else if (((1L<<start)&NR&(1L<<7))!=0) {CBKt=false;}
                else if (((1L<<start)&NR&1L)!=0) {CBQt=false;}
            }
            score = -zWSearch(-alpha,BPt,BNt,BBt,BRt,BQt,BKt,NPt,NNt,NBt,NRt,NQt,NKt,EPt,CWKt,CWQt,CBKt,CBQt,!MouveB,depth+1);
            if ((score > alpha) && (score < beta))
            {
                //research with window [alpha;beta]
                bestScore = -pvSearch(-beta,-alpha,BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK,EP,CWK,CWQ,CBK,CBQ,!MouveB,depth+1);
                if (score>alpha)
                {
                    bestMoveIndex = i;
                    alpha = score;
                }
            }
            if ((score != UI.NULL_INT) && (score > bestScore))
            {
                if (score >= beta)
                {
                    return score;
                }
                bestScore = score;
                if (Math.abs(bestScore) == UI.MATE_SCORE)
                {
                    return bestScore;
                }
            }
        }
        return bestScore;
    }
}
