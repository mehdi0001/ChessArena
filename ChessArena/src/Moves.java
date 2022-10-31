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
 

    static long FileMasks8[] =
    {
        0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
        0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
    };    
    
    public static String MouvementCandidat(String ancien_mvt,long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK) {
    	pionNoir=NP|NN|NB|NR|NQ;  //tous les pions noires sauf le king NK 
    	CaseLibre=~(BP|BN|BB|BR|BQ|BK|NP|NN|NB|NR|NQ|NK);  // Toutes les cases libres 
        //timeExperiment(WP);
        String list=MouvementPawn.CoutPossibleP(ancien_mvt,BP,NP);
        return list;
    }
}
