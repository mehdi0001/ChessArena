
public class MouvementCastle extends Moves {
    public static String CoutPossibleR(long CaseOccupe,long R)
    {
    	
    	String list="";
        long i=R&~(R-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=MouvementVH(iLocation)&Nmien;
            //drawEchiquier(possibility);
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            R&=~i;
            i=R&~(R-1);
        }
        return list;
    }
    
    public static long makeMoveCastle(long EchiquierR, long EchiquierK, String move, char type) {
        int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
        if ((((EchiquierK>>>start)&1)==1)&&(("0402".equals(move))||("0406".equals(move))||("7472".equals(move))||("7476".equals(move)))) {//'regular' move
            if (type=='R') {
                switch (move) {
                    case "7472": EchiquierR&=~(1L<<PositionR[1]); EchiquierR|=(1L<<(PositionR[1]+3));
                        break;
                    case "7476": EchiquierR&=~(1L<<PositionR[0]); EchiquierR|=(1L<<(PositionR[0]-2));
                        break;
                }
            } else {
                switch (move) {
                    case "0402": EchiquierR&=~(1L<<PositionR[3]); EchiquierR|=(1L<<(PositionR[3]+3));
                        break;
                    case "0406": EchiquierR&=~(1L<<PositionR[2]); EchiquierR|=(1L<<(PositionR[2]-2));
                        break;
                }
            }
        }
        return EchiquierR;
    }
}
