
public class MouvementCastle extends Moves {
    public static String CoutPossibleR(long CaseOccupe,long BR)
    {
    	
    	String list="";
        long i=BR&~(BR-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementVH(iLocation)&CaseNBlanche;
            //drawEchiquier(possibility);
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            BR&=~i;
            i=BR&~(BR-1);
        }
        return list;
    }
}
