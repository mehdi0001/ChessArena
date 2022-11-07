import java.util.Arrays;

public class Moves {
    static long colonne_A=72340172838076673L;
    static long colonne_H=-9187201950435737472L;
    static long ligne_1=-72057594037927936L;
    static long ligne_4=1095216660480L;
    static long ligne_5=4278190080L;
    static long ligne_8=255L;
    static long mien;
    static long CaseLibre;
    static long CaseOccupe;
    static long Nmien;
    static long espaceN=43234889994L;
    static long espaceK=460039L;
    static long colonne_AB=217020518514230019L;
    static long colonne_GH=-4557430888798830400L;
    static long PositionR[]={63,56,7,0};

    static long RankMasks8[] =/*ligne1 à ligne8*/
        {
            0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
        };
    static long FileMasks8[] =
    {
        0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
        0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
    };    
    
    static long DiagonalMasks8[] =/*masque de la diagonale*/
    {
	0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
	0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
	0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
    };
    static long AntiDiagonalMasks8[] =/*masque de l'anti diagonale*/
    {
	0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
	0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
	0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
    };
    
    static long MouvementVH(int s)
    {
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (CaseOccupe - 2 * binaryS) ^ Long.reverse(Long.reverse(CaseOccupe) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((CaseOccupe&FileMasks8[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(CaseOccupe&FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
        //drawEchiquier((possibilitiesHorizontal&RankMasks8[s / 8]) | (possibilitiesVertical&FileMasks8[s % 8]));
        return (possibilitiesHorizontal&RankMasks8[s / 8]) | (possibilitiesVertical&FileMasks8[s % 8]);
    }
    static long MouvementDiag(int s)
    {
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((CaseOccupe&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(CaseOccupe&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((CaseOccupe&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(CaseOccupe&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        //drawEchiquier((possibilitiesDiagonal&DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]));
        return (possibilitiesDiagonal&DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
    } 
 
    public static void makeMoveWrong(String move,long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ) {
        //can not opperate on a single board since moves are not backwards compatable
        EP=0;
        if (Character.isDigit(move.charAt(3))) {
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            int end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
            if ((BK&(1L<<start))!=0) {//white castle move
                BK^=(1L<<start);
                BK^=(1L<<end);
                if (end>start) {//kingside
                    BR^=(1L<<63);
                    BR^=(1L<<61);
                    CWK=false;
                } else {//queenside
                    BR^=(1L<<56);
                    BR^=(1L<<59);
                    CWQ=false;
                }
            } else if ((NK&(1L<<start))!=0) {//black castle move
                BK^=(1L<<start);
                BK^=(1L<<end);
                if (end>start) {//kingside
                    BR^=(1L<<7);
                    BR^=(1L<<5);
                    CBK=false;
                } else {//queenside
                    BR^=1L;
                    BR^=(1L<<3);
                    CBQ=false;
                }
            } else {//'regular' move
                //clear destination:
                BP&=~(1L<<end);
                BN&=~(1L<<end);
                BB&=~(1L<<end);
                BR&=~(1L<<end);
                BQ&=~(1L<<end);
                BK&=~(1L<<end);
                //move piece:
                if ((BP&(1L<<start))!=0)
                {
                    BP^=(1L<<start);
                    BP^=(1L<<end);
                    if ((end-start)==16) {//pawn double push
                        EP=FileMasks8['0'-move.charAt(1)];
                    }
                }
                else if ((NP&(1L<<start))!=0)
                {
                    NP^=(1L<<start);
                    NP^=(1L<<end);
                    if ((start-end)==16) {//pawn double push
                        EP=FileMasks8['0'-move.charAt(1)];
                    }
                }
                else if ((BN&(1L<<start))!=0)
                {
                    BN^=(1L<<start);
                    BN^=(1L<<end);
                }
                else if ((NN&(1L<<start))!=0)
                {
                    NN^=(1L<<start);
                    NN^=(1L<<end);
                }
                else if ((BB&(1L<<start))!=0)
                {
                    BB^=(1L<<start);
                    BB^=(1L<<end);
                }
                else if ((NB&(1L<<start))!=0)
                {
                    NB^=(1L<<start);
                    NB^=(1L<<end);
                }
                else if ((BR&(1L<<start))!=0)
                {
                    BR^=(1L<<start);
                    BR^=(1L<<end);
                }
                else if ((NR&(1L<<start))!=0)
                {
                    NR^=(1L<<start);
                    NR^=(1L<<end);
                }
                else if ((BQ&(1L<<start))!=0)
                {
                    BQ^=(1L<<start);
                    BQ^=(1L<<end);
                }
                else if ((NQ&(1L<<start))!=0)
                {
                    NQ^=(1L<<start);
                    NQ^=(1L<<end);
                }
                else if ((BK&(1L<<start))!=0)
                {
                    BK^=(1L<<start);
                    BK^=(1L<<end);
                    CWK=false;
                    CWQ=false;
                }
                else if ((NK&(1L<<start))!=0)
                {
                    NK^=(1L<<start);
                    NK^=(1L<<end);
                    CBK=false;
                    CBQ=false;
                }
                else
                {
                    System.out.print("error: can not move empty piece");
                }
            }
        } else if (move.charAt(3)=='P') {//pawn promotion
            //y1,y2,Promotion Type,"P"
            if (Character.isUpperCase(move.charAt(2)))//white piece promotion
            {
                BP^=(RankMasks8[6]&FileMasks8[move.charAt(0)-'0']);
                switch (move.charAt(2)) {
                case 'N': BN^=(RankMasks8[7]&FileMasks8[move.charAt(1)-'0']);
                    break;
                case 'B': BB^=(RankMasks8[7]&FileMasks8[move.charAt(1)-'0']);
                    break;
                case 'R': BR^=(RankMasks8[7]&FileMasks8[move.charAt(1)-'0']);
                    break;
                case 'Q': BQ^=(RankMasks8[7]&FileMasks8[move.charAt(1)-'0']);
                    break;
                }
            } else {//black piece promotion
                BP^=(RankMasks8[1]&FileMasks8[move.charAt(0)-'0']);
                switch (move.charAt(2)) {
                case 'n': NN^=(RankMasks8[0]&FileMasks8[move.charAt(1)-'0']);
                    break;
                case 'b': NB^=(RankMasks8[0]&FileMasks8[move.charAt(1)-'0']);
                    break;
                case 'r': NR^=(RankMasks8[0]&FileMasks8[move.charAt(1)-'0']);
                    break;
                case 'q': NQ^=(RankMasks8[0]&FileMasks8[move.charAt(1)-'0']);
                    break;
                }
            }
        } else if (move.charAt(3)=='E') {//en passant move
            if (move.charAt(2)=='w') {//white move    
                //y1,y2,"BE"
                BP^=(RankMasks8[4]&FileMasks8['0'-move.charAt(0)]);//remove White pawn
                BP^=(RankMasks8[5]&FileMasks8['0'-move.charAt(1)]);//add white pawn
                NP^=(RankMasks8[4]&FileMasks8['0'-move.charAt(1)]);//remove black pawn)
            } else {//black move
                NP^=(RankMasks8[3]&FileMasks8['0'-move.charAt(0)]);//remove black pawn
                NP^=(RankMasks8[2]&FileMasks8['0'-move.charAt(1)]);//add black pawn
                BP^=(RankMasks8[3]&FileMasks8['0'-move.charAt(1)]);//remove white pawn)
            }
        } else {
            System.out.print("error: not a valid move type");
        }
    }
    
    /*
    long WPt=Moves.makeMove(WP, moves.substring(i,i+4), 'P'), WNt=Moves.makeMove(WN, moves.substring(i,i+4), 'N'),
    WBt=Moves.makeMove(WB, moves.substring(i,i+4), 'B'), WRt=Moves.makeMove(WR, moves.substring(i,i+4), 'R'),
    WQt=Moves.makeMove(WQ, moves.substring(i,i+4), 'Q'), WKt=Moves.makeMove(WK, moves.substring(i,i+4), 'K'),
    BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'p'), BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'n'),
    BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'b'), BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'r'),
    BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'q'), BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'k'),
    EPt=Moves.makeMoveEP(moves.substring(i,i+4));
    */
    
    public static long makeMove(long board, String move, char type) {
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            int end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
            if (((board>>>start)&1)==1) {board&=~(1L<<start); board|=(1L<<end);} else {board&=~(1L<<end);}
        } else if (move.charAt(3)=='P') {//pawn promotion
            int start, end;
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[6]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[7]);
            } else {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[1]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[0]);
            }
            if (type==move.charAt(2)) {board&=~(1L<<start); board|=(1L<<end);} else {board&=~(1L<<end);}
        } else if (move.charAt(3)=='E') {//en passant
            int start, end;
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[4]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[5]);
                board&=~(1L<<(FileMasks8[move.charAt(1)-'0']&RankMasks8[4]));
            } else {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[3]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[2]);
                board&=~(1L<<(FileMasks8[move.charAt(1)-'0']&RankMasks8[3]));
            }
            if (((board>>>start)&1)==1) {board&=~(1L<<start); board|=(1L<<end);}
        } else {
            System.out.print("ERROR: Invalid move type");
        }
        return board;
    }
    public static long makeMoveEP(long board,String move) {
        if (Character.isDigit(move.charAt(3))) {
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            if ((Math.abs(move.charAt(0)-move.charAt(2))==2)&&(((board>>>start)&1)==1)) {//pawn double push
                return FileMasks8[move.charAt(1)-'0'];
            }
        }
        return 0;
    }
  
    
    
    
    public static String MouvementCandidatB(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ) {
    	mien=BP|BN|BB|BR|BQ;  //tous les pions sauf le king NK 
    	CaseOccupe = BP|BN|BB|BR|BQ|BK|NP|NN|NB|NR|NQ|NK; 
    	CaseLibre=~CaseOccupe;  // Toutes les cases libres
    	Nmien = ~(BP|BN|BB|BR|BQ|BK|NK);
    	//MouvementVH(36);
    	//MouvementDiag(36);
        //timeExperiment(WP);
        String list=MouvementPawn.CoutPossibleBP(BP,NP,EP)+
        		MouvementBishop.CoutPossibleB(CaseOccupe, BB)+
        		MouvementCastle.CoutPossibleR(CaseOccupe,BR)+
        		MouvementQueen.CoutPossibleQ(CaseOccupe, BQ)+
        		MouvementHorse.CoutPossibleN(CaseOccupe, BN)+
        		MouvementKing.CoutPossibleK(CaseOccupe, BK)+
        		MouvementKing.CoutPossibleCB(BR, CWK, CWQ);
        return list;
    }
    public static String MouvementCandidatN(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ) {
    	mien=NP|NN|NB|NR|NQ;  //tous les pions noires sauf le king NK 
    	CaseOccupe = BP|BN|BB|BR|BQ|BK|NP|NN|NB|NR|NQ|NK;
    	CaseLibre=~CaseOccupe;  // Toutes les cases libres 
    	Nmien = ~(NP|NN|NB|NR|NQ|NK|BK);
    	//MouvementVH(36);
    	//MouvementDiag(36);
        //timeExperiment(WP);
        String list=MouvementPawn.CoutPossibleNP(NP,BP,EP)+
        		MouvementBishop.CoutPossibleB(CaseOccupe, NB)+
        		MouvementCastle.CoutPossibleR(CaseOccupe,NR)+
        		MouvementQueen.CoutPossibleQ(CaseOccupe, NQ)+
        		MouvementHorse.CoutPossibleN(CaseOccupe, NN)+
        		MouvementKing.CoutPossibleK(CaseOccupe, NK)+
        		MouvementKing.CoutPossibleCN(NR, CBK, CBQ);
        return list;
    }
    
    
    
    public static void drawEchiquier(long bit) {
        String echiquier[][]=new String[8][8];
        for (int i=0;i<64;i++) {
        	echiquier[i/8][i%8]="";
        }
        for (int i=0;i<64;i++) {
            if (((bit>>>i)&1)==1) {echiquier[i/8][i%8]="P";}
            if ("".equals(echiquier[i/8][i%8])) {echiquier[i/8][i%8]=" ";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(echiquier[i]));
        }
    }
}
