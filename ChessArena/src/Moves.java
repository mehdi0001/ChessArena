import java.util.Arrays;

public class Moves {
    static long colonne_A=72340172838076673L;
    static long colonne_H=-9187201950435737472L;
    static long ligne_1=-72057594037927936L;
    static long ligne_4=1095216660480L;
    static long ligne_5=4278190080L;
    static long ligne_8=255L;
    static long pionNoir;
    static long CaseLibre;
    static long CaseOccupe;
    static long CaseNBlanche;

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
    
    public static String MouvementCandidat(String ancien_mvt,long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK) {
    	pionNoir=NP|NN|NB|NR|NQ;  //tous les pions noires sauf le king NK 
    	CaseLibre=~(BP|BN|BB|BR|BQ|BK|NP|NN|NB|NR|NQ|NK);  // Toutes les cases libres 
    	CaseOccupe = BP|BN|BB|BR|BQ|BK|NP|NN|NB|NR|NQ|NK;
    	CaseNBlanche = ~(BP|BN|BB|BR|BQ|BK|NK);
    	//MouvementVH(36);
    	//MouvementDiag(36);
        //timeExperiment(WP);
        String list=MouvementPawn.CoutPossibleP(ancien_mvt,BP,NP)+
        		MouvementBishop.CoutPossibleB(CaseOccupe, BB)+
        		MouvementCastle.CoutPossibleR(CaseOccupe,BR)+
        		MouvementQueen.CoutPossibleQ(CaseOccupe, BQ);
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
