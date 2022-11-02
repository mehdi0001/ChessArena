
public class MouvementQueen extends Moves {
    public static String CoutPossibleQ(long CaseOccupe,long Q)
    {
        String list="";
        long i=Q&~(Q-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=(MouvementVH(iLocation)|MouvementDiag(iLocation))&Nmien;
            long j=possibility&~(possibility-1);
            //drawEchiquier(possibility);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            Q&=~i;
            i=Q&~(Q-1);
        }
        return list;
    }
}
