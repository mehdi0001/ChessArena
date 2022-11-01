import java.util.Arrays;

public class MouvementPawn extends Moves {
	public static String CoutPossibleP(String ancien_mvt,long BP,long NP) {
		String list="";
	    //x1,y1,x2,y2
	    //x1:ligne depart ,x2:ligne arrivee, y1: colonne depart, y2: colonne arrivee
	    
		capture_right(list, BP);
		capture_left(list, BP);
	    move_1_forward(list, BP,CaseLibre,ligne_8);
	    move_2_forward(list, BP,CaseLibre,ligne_4);
	    //y1,y2,Promotion Type,"P"
	    PromotionCaptureR(list, BP);
	    PromotionCaptureL(list, BP);
	    Promotion1forward(list, BP);
	    //y1,y2,Space,"E
	    if (ancien_mvt.length()>=4)//1636
	    {
	        if ((ancien_mvt.charAt(ancien_mvt.length()-1)==ancien_mvt.charAt(ancien_mvt.length()-3)) && Math.abs(ancien_mvt.charAt(ancien_mvt.length()-2)-ancien_mvt.charAt(ancien_mvt.length()-4))==2)
	        {
	            int eFile=ancien_mvt.charAt(ancien_mvt.length()-1)-'0';
	            //en passant right
	            long possibility = (BP << 1)&BP&ligne_5&~colonne_A&FileMasks8[eFile];//shows piece to remove, not the destination
	            if (possibility != 0)
	            {
	                int index=Long.numberOfTrailingZeros(possibility);
	                list+=""+(index%8-1)+(index%8)+" E";
	            }
	            //en passant left
	            possibility = (BP >> 1)&BP&ligne_5&~colonne_H&FileMasks8[eFile];//shows piece to remove, not the destination
	            if (possibility != 0)
	            {
	                int index=Long.numberOfTrailingZeros(possibility);
	                list+=""+(index%8+1)+(index%8)+" E";
	            }
	        }
	    }
	   return list;
}
	public static void capture_right(String list, long BP) {
	    long mvt_pion=(BP>>7)&pionNoir&~ligne_8&~colonne_A;//capture right
	    //drawEchiquier(mvt_pion);
	    long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index/8+1)+(index%8-1)+(index/8)+(index%8);
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	public static void capture_left(String list, long BP) {
		long mvt_pion=(BP>>9)&pionNoir&~ligne_8&~colonne_H;//capture left
		//drawEchiquier(mvt_pion);
	    long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index/8+1)+(index%8+1)+(index/8)+(index%8);
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
} 
	public static void move_2_forward(String list, long BP,long CaseLibre,long ligne_4){
		long mvt_pion=(BP>>16)&CaseLibre&(CaseLibre>>8)&ligne_4;//move 2 forward
	    long possibility=mvt_pion&~(mvt_pion-1);
	    //drawEchiquier(mvt_pion);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index/8+2)+(index%8)+(index/8)+(index%8);
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	public static void move_1_forward(String list, long BP,long CaseLibre,long ligne_8){   //move 1 forward
	    long mvt_pion=(BP>>8)&CaseLibre&~ligne_8;
	    //drawEchiquier(mvt_pion);
	    long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index/8+1)+(index%8)+(index/8)+(index%8);
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	
	public static void PromotionCaptureR(String list, long BP) {    //pawn promotion by capture right
	    long mvt_pion=(BP>>7)&pionNoir&ligne_8&~colonne_A;
	    //drawEchiquier(mvt_pion);
	    long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index%8-1)+(index%8)+"QP"+(index%8-1)+(index%8)+"RP"+(index%8-1)+(index%8)+"BP"+(index%8-1)+(index%8)+"NP";
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	public static void PromotionCaptureL(String list, long BP) {   //pawn promotion by capture left
	    long mvt_pion=(BP>>9)&pionNoir&ligne_8&~colonne_H;
	    //drawEchiquier(mvt_pion);
	    long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index%8+1)+(index%8)+"QP"+(index%8+1)+(index%8)+"RP"+(index%8+1)+(index%8)+"BP"+(index%8+1)+(index%8)+"NP";
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	public static void Promotion1forward(String list, long BP) {   //pawn promotion by move 1 forward
	    long mvt_pion=(BP>>8)&CaseLibre&ligne_8;
	    //drawEchiquier(mvt_pion);
	    long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index%8)+(index%8)+"QP"+(index%8)+(index%8)+"RP"+(index%8)+(index%8)+"BP"+(index%8)+(index%8)+"NP";
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
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
