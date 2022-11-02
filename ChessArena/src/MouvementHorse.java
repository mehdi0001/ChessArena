
public class MouvementHorse extends Moves {
	public static String CoutPossibleN(long CaseOccupe,long H)
    {
        String list="";
        long i=H&~(H-1);
        long possibility;
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
                possibility &=~colonne_GH&Nmien;
            }
            else {
                possibility &=~colonne_AB&Nmien;
            }
            long j=possibility&~(possibility-1);
            //drawEchiquier(possibility);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            H&=~i;
            i=H&~(H-1);
        }
        return list;
    }
}
