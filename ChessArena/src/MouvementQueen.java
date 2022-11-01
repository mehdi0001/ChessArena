
public class MouvementQueen extends Moves {
    public static String CoutPossibleQ(long OCCUPIED,long BQ)
    {
        String list="";
        long i=BQ&~(BQ-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=(MouvementVH(iLocation)|MouvementDiag(iLocation))&CaseNBlanche;
            long j=possibility&~(possibility-1);
            //drawEchiquier(possibility);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            BQ&=~i;
            i=BQ&~(BQ-1);
        }
        return list;
    }
}
