import java.security.*;
public class Zobrist {
    static long zobArray[][][] = new long[2][6][64];
    static long zobEnPassant[] = new long[8];
    static long zobCastle[] = new long[4];
    static long zobBlackMove;
    public static long random64() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }
    public static long random64Bad() {
        return (long)(Math.random()*1000000000000000000L);
    }
    public static void zobristFillArray() {
        for (int color = 0; color < 2; color++)
        {
            for (int pieceType = 0; pieceType < 6; pieceType++)
            {
                for (int square = 0; square < 64; square++)
                {
                    zobArray[color][pieceType][square] = random64();
                }
            }
        }
        for (int column = 0; column < 8; column++)
        {
            zobEnPassant[column] = random64();
        }
        for (int i = 0; i < 4; i++)
        {
            zobCastle[i] = random64();
        }
        zobBlackMove = random64();
    }
    public static long getZobristHash(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean MouveB) {
        long returnZKey = 0;
        for (int square = 0; square < 64; square++)
        {
            if (((BP >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[0][0][square];
            }
            else if (((NP >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[1][0][square];
            }
            else if (((BN >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[0][1][square];
            }
            else if (((NN >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[1][1][square];
            }
            else if (((BB >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[0][2][square];
            }
            
            else if (((NB >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[1][2][square];
            }
            else if (((BR >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[0][3][square];
            }
            else if (((NR >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[1][3][square];
            }
            else if (((BQ >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[0][4][square];
            }
            else if (((NQ >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[1][4][square];
            }
            else if (((BK >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[0][5][square];
            }
            else if (((NK >> square) & 1) == 1)
            {
                returnZKey ^= zobArray[1][5][square];
            }
        }
        for (int column = 0; column < 8; column++)
        {
            if (EP == Moves.FileMasks8[column])
            {
                returnZKey ^= zobEnPassant[column];
            }
        }
        if (CWK)
            returnZKey ^= zobCastle[0];
        if (CWQ)
            returnZKey ^= zobCastle[1];
        if (CBK)
            returnZKey ^= zobCastle[2];
        if (CBQ)
            returnZKey ^= zobCastle[3];
        if (!MouveB)
            returnZKey ^= zobBlackMove;
        return returnZKey;
    }
    public static void testDistribution() {
        int sampleSize = 2000;
        int sampleSeconds = 10;
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (sampleSeconds * 1000);
        int[] distArray;
        distArray = new int[sampleSize];
        while (System.currentTimeMillis() < endTime)
        {
            for (int i = 0; i < 10000; i++)
            {
                distArray[(int)(random64()% (sampleSize / 2)) + (sampleSize / 2)]++;
            }
        }
        for (int i = 0; i < sampleSize; i++)
        {
            System.out.println(distArray[i]);
        }
    }
}