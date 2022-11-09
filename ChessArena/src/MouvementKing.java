
public class MouvementKing extends Moves {
	public static String CoutPossibleK(long CaseOccupe,long K) 
	{
        String list="";
        long possibility;
        int iLocation=Long.numberOfTrailingZeros(K);
        if (iLocation>9)
        {
            possibility=espaceK<<(iLocation-9);
        }
        else {
            possibility=espaceK>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~colonne_GH&Nmien;
        }
        else {
            possibility &=~colonne_AB&Nmien;
        }
      //drawEchiquier(possibility);
        long j=possibility&~(possibility-1);
        while (j != 0)
        {
            int index=Long.numberOfTrailingZeros(j);
            list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
            possibility&=~j;
            j=possibility&~(possibility-1);
        }
        return list;
	}
	
    public static String CoutPossibleCB(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,boolean CWK,boolean CWQ)
    {
        String list="";
        long danger=danger_N(BP, BN, BB, BR, BQ, BK, NP, NN, NB, NR, NQ, NK);
        if ((danger&BK)==0) {
            if (CWK&&(((1L<<PositionR[0])&BR)!=0))
            {
                if (((CaseOccupe|danger)&((1L<<61)|(1L<<62)))==0) {
                    list+="7476";
                }
            }
            if (CWQ&&(((1L<<PositionR[1])&BR)!=0))
            {
                if (((CaseOccupe|(danger&~(1L<<57)))&((1L<<57)|(1L<<58)|(1L<<59)))==0) {
                    list+="7472";
                }
            }
        }
        return list;
    }
    public static String CoutPossibleCN(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,boolean CBK,boolean CBQ)
    {
        String list="";
        long danger=danger_B(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK);
        if ((danger&BK)==0) {
            if (CBK&&(((1L<<PositionR[2])&NR)!=0))
            {
                if (((CaseOccupe|danger)&((1L<<5)|(1L<<6)))==0) {
                    list+="0406";
                }
            }
            if (CBQ&&(((1L<<PositionR[3])&NR)!=0))
            {
                if (((CaseOccupe|(danger&~(1L<<1)))&((1L<<1)|(1L<<2)|(1L<<3)))==0) {
                    list+="0402";
                }
            }
        }
        return list;
    }
	
    public static long danger_N(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK)
    {
        long danger;
        CaseOccupe=BP|BN|BB|BR|BQ|BK|NP|NN|NB|NR|NQ|NK;
        //pawn
        danger=((BP>>>7)&~colonne_A);//pawn capture right
        danger|=((BP>>>9)&~colonne_H);//pawn capture left
        long possibility;
        //knight
        long i=BN&~(BN-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=espaceN<<(iLocation-18);
            }
            else {
                possibility=espaceN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~colonne_GH;
            }
            else {
                possibility &=~colonne_AB;
            }
            danger |= possibility;
            BN&=~i;
            i=BN&~(BN-1);
        }
        //bishop/queen
        long QB=BQ|BB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementDiag(iLocation);
            danger |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        long QR=BQ|BR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementVH(iLocation);
            danger |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(BK);
        if (iLocation>9)
        {
            possibility=espaceK<<(iLocation-9);
        }
        else {
            possibility=espaceK>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~colonne_GH;
        }
        else {
            possibility &=~colonne_AB;
        }
        danger |= possibility;
        //drawEchiquier(danger);
        return danger;
    }
    
    public static long danger_B(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK)
    {
        long danger;
        CaseOccupe=BP|BN|BB|BR|BQ|BK|NP|NN|NB|NR|NQ|NK;
        //pawn
        danger=((NP<<7)&~colonne_H);//pawn capture right
        danger|=((NP<<9)&~colonne_A);//pawn capture left
        long possibility;
        //knight
        long i=NN&~(NN-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=espaceN<<(iLocation-18);
            }
            else {
                possibility=espaceN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~colonne_GH;
            }
            else {
                possibility &=~colonne_AB;
            }
            danger |= possibility;
            NN&=~i;
            i=NN&~(NN-1);
        }
        //bishop/queen
        long QB=NQ|NB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementDiag(iLocation);
            danger |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        long QR=NQ|NR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementVH(iLocation);
            danger |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(NK);
        if (iLocation>9)
        {
            possibility=espaceK<<(iLocation-9);
        }
        else {
            possibility=espaceK>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~colonne_GH;
        }
        else {
            possibility &=~colonne_AB;
        }
        danger |= possibility;
        //drawEchiquier(danger);
        return danger;
    }
}
