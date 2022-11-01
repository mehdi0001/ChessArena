
public class MouvementBishop extends Moves {
    public static String CoutPossibleB(long CaseOccupe,long BB)
    {
        String list="";
        long i=BB&~(BB-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementDiag(iLocation)&CaseNBlanche;
            //drawEchiquier(possibility);
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            BB&=~i;
            i=BB&~(BB-1);
        }
        return list;
    }
}
