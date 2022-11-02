import java.util.Arrays;

public class MouvementPawn extends Moves {
	public static String CoutPossibleBP(String ancien_mvt,long BP,long NP) {
		String list="";
	    //x1,y1,x2,y2
	    //x1:ligne depart ,x2:ligne arrivee, y1: colonne depart, y2: colonne arrivee
	    
		capture_rightB(list, BP);
		capture_leftB(list, BP);
	    move_1_forwardB(list, BP,CaseLibre,ligne_8);
	    move_2_forwardB(list, BP,CaseLibre,ligne_4);
	    //y1,y2,Promotion Type,"P"
	    PromotionCaptureRB(list, BP);
	    PromotionCaptureLB(list, BP);
	    Promotion1forwardB(list, BP);
	    //y1,y2,Space,"E
	    if (ancien_mvt.length()>=4)//1636
	    {
	        if ((ancien_mvt.charAt(ancien_mvt.length()-1)==ancien_mvt.charAt(ancien_mvt.length()-3)) && Math.abs(ancien_mvt.charAt(ancien_mvt.length()-2)-ancien_mvt.charAt(ancien_mvt.length()-4))==2)
	        {
	            int eFile=ancien_mvt.charAt(ancien_mvt.length()-1)-'0';
	            //en passant right
	            long possibility = (BP << 1)&NP&ligne_5&~colonne_A&FileMasks8[eFile];//shows piece to remove, not the destination
	            if (possibility != 0)
	            {
	                int index=Long.numberOfTrailingZeros(possibility);
	                list+=""+(index%8-1)+(index%8)+" E";
	            }
	            //en passant left
	            possibility = (BP >> 1)&NP&ligne_5&~colonne_H&FileMasks8[eFile];//shows piece to remove, not the destination
	            if (possibility != 0)
	            {
	                int index=Long.numberOfTrailingZeros(possibility);
	                list+=""+(index%8+1)+(index%8)+" E";
	            }
	        }
	    }
	   return list;
}
	
	public static String CoutPossibleNP(String ancien_mvt,long NP,long BP) 
	{
		String list="";
        //x1,y1,x2,y2 
		capture_rightN(list, NP);
		capture_leftN(list, NP);
		move_1_forwardN(list, NP, CaseLibre, ligne_1);
		move_2_forwardN(list, NP, CaseLibre, ligne_5);
        //y1,y2,Promotion Type,"P"
		PromotionCaptureRN(list, NP);
		Promotion1forwardB(list, BP);
		Promotion1forwardN(list, NP);
        //y1,y2,"bE"
        if (ancien_mvt.length()>=4)
        {
            if ((ancien_mvt.charAt(ancien_mvt.length()-1)==ancien_mvt.charAt(ancien_mvt.length()-3)) && Math.abs(ancien_mvt.charAt(ancien_mvt.length()-2)-ancien_mvt.charAt(ancien_mvt.length()-4))==2)
            {
                int eFile=ancien_mvt.charAt(ancien_mvt.length()-1)-'0';
                //en passant right
                long possibility = (NP >> 1)&BP&ligne_4&~colonne_H&FileMasks8[eFile];//shows piece to remove, not the destination
                if (possibility != 0)
                {
                    int index=Long.numberOfTrailingZeros(possibility);
                    list+=""+(index%8+1)+(index%8)+"bE";
                }
                //en passant left
                possibility = (NP << 1)&BP&ligne_4&~colonne_A&FileMasks8[eFile];//shows piece to remove, not the destination
                if (possibility != 0)
                {
                    int index=Long.numberOfTrailingZeros(possibility);
                    list+=""+(index%8-1)+(index%8)+"bE";
                }
            }
        }
       return list;
	}
	
	public static void capture_rightB(String list, long BP) {
	    long mvt_pion=(BP>>7)&mien&~ligne_8&~colonne_A;//capture right
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
	public static void capture_rightN(String list, long NP) {
		long mvt_pion=(NP<<7)&mien&~ligne_1&~colonne_H;//capture right
		//drawEchiquier(mvt_pion);
		long possibility=mvt_pion&~(mvt_pion-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+=""+(index/8-1)+(index%8+1)+(index/8)+(index%8);
            mvt_pion&=~possibility;
            possibility=mvt_pion&~(mvt_pion-1);
        }
	}
	public static void capture_leftB(String list, long BP) {
		long mvt_pion=(BP>>9)&mien&~ligne_8&~colonne_H;//capture left
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
	public static void capture_leftN(String list, long NP) {
		long mvt_pion=(NP<<9)&mien&~ligne_1&~colonne_A;//capture left
		//drawEchiquier(mvt_pion);
		long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index/8-1)+(index%8-1)+(index/8)+(index%8);
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	public static void move_2_forwardB(String list, long BP,long CaseLibre,long ligne_4){  //move 2 forward
		long mvt_pion=(BP>>16)&CaseLibre&(CaseLibre>>8)&ligne_4;
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
	public static void move_2_forwardN(String list, long NP,long CaseLibre,long ligne_5){
		long mvt_pion=(NP<<16)&CaseLibre&(CaseLibre<<8)&ligne_5;//move 2 forward
        long possibility=mvt_pion&~(mvt_pion-1);
        //drawEchiquier(mvt_pion);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+=""+(index/8-2)+(index%8)+(index/8)+(index%8);
            mvt_pion&=~possibility;
            possibility=mvt_pion&~(mvt_pion-1);
        }
	}
	public static void move_1_forwardB(String list, long BP,long CaseLibre,long ligne_8){   //move 1 forward
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
	
	public static void move_1_forwardN(String list, long NP,long CaseLibre,long ligne_1){   //move 1 forward
		long mvt_pion=(NP<<8)&CaseLibre&~ligne_1;
		//drawEchiquier(mvt_pion);
		long possibility=mvt_pion&~(mvt_pion-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+=""+(index/8-1)+(index%8)+(index/8)+(index%8);
            mvt_pion&=~possibility;
            possibility=mvt_pion&~(mvt_pion-1);
        }
	}
	
	public static void PromotionCaptureRB(String list, long BP) {    //pawn promotion by capture right
	    long mvt_pion=(BP>>7)&mien&ligne_8&~colonne_A;
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
	public static void PromotionCaptureRN(String list, long NP) {    //pawn promotion by capture right
		long mvt_pion=(NP<<7)&mien&ligne_1&~colonne_H;
		//drawEchiquier(mvt_pion);
		long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index%8+1)+(index%8)+"qP"+(index%8+1)+(index%8)+"rP"+(index%8+1)+(index%8)+"bP"+(index%8+1)+(index%8)+"nP";
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	public static void PromotionCaptureLB(String list, long BP) {   //pawn promotion by capture left
	    long mvt_pion=(BP>>9)&mien&ligne_8&~colonne_H;
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
	public static void PromotionCaptureLN(String list, long NP) {   //pawn promotion by capture left
		long mvt_pion=(NP<<9)&mien&ligne_1&~colonne_A;
		//drawEchiquier(mvt_pion);
		long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index%8-1)+(index%8)+"qP"+(index%8-1)+(index%8)+"rP"+(index%8-1)+(index%8)+"bP"+(index%8-1)+(index%8)+"nP";
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
	public static void Promotion1forwardB(String list, long BP) {   //pawn promotion by move 1 forward
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
	public static void Promotion1forwardN(String list, long NP) {   //pawn promotion by move 1 forward
		long mvt_pion=(NP<<8)&CaseLibre&ligne_1;
		//drawEchiquier(mvt_pion);
		long possibility=mvt_pion&~(mvt_pion-1);
	    while (possibility != 0)
	    {
	        int index=Long.numberOfTrailingZeros(possibility);
	        list+=""+(index%8)+(index%8)+"qP"+(index%8)+(index%8)+"rP"+(index%8)+(index%8)+"bP"+(index%8)+(index%8)+"nP";
	        mvt_pion&=~possibility;
	        possibility=mvt_pion&~(mvt_pion-1);
	    }
	}
}
