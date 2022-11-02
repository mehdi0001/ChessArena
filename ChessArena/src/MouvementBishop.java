
public class MouvementBishop extends Moves {
    public static String CoutPossibleB(long CaseOccupe,long B)
    {
        String list="";
        long i=B&~(B-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementDiag(iLocation)&Nmien;
            //drawEchiquier(possibility);
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            B&=~i;
            i=B&~(B-1);
        }
        return list;
    }
}
