import java.util.*;
/*
* Majuscule==Blanc
* Minuscule==Noire
* pawn=P/p
* horse=N/n
* bishop=B/b
* castle=R/r
* Queen=Q/q
* King=K/k
*/

public class pion {
    public static void EchiquierInitiale () {
        long BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L,NP=0L,NN=0L,NB=0L,NR=0L,NQ=0L,NK=0L;
        String Echiquier[][]={
                {"r","n","b","q","k","b","n","r"},
        		{"p","p","p","p","p","p","p","p"},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {"P","P","P","P","P","P","P","P"},
                {"R","N","B","Q","K","B","N","R"}};
        		
        arrayToBitboards(Echiquier,BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK);
    }
    
    public static void stringFen(String fenString) {
        //not chess960 compatible
    	UI.BP=0; UI.BN=0; UI.BB=0;
        UI.BR=0; UI.BQ=0; UI.BK=0;
        UI.NP=0; UI.NN=0; UI.NB=0;
        UI.NR=0; UI.NQ=0; UI.NK=0;
        UI.CWK=false; UI.CWQ=false;
        UI.CBK=false; UI.CBQ=false;
	int charIndex = 0;
	int boardIndex = 0;
	while (fenString.charAt(charIndex) != ' ')
	{
		switch (fenString.charAt(charIndex++))
		{
		case 'P': UI.BP |= (1L << boardIndex++);
			break;
		case 'p': UI.NP |= (1L << boardIndex++);
			break;
		case 'N': UI.BN |= (1L << boardIndex++);
			break;
		case 'n': UI.NN |= (1L << boardIndex++);
			break;
		case 'B': UI.BB |= (1L << boardIndex++);
			break;
		case 'b': UI.NB |= (1L << boardIndex++);
			break;
		case 'R': UI.BR |= (1L << boardIndex++);
			break;
		case 'r': UI.NR |= (1L << boardIndex++);
			break;
		case 'Q': UI.BQ |= (1L << boardIndex++);
			break;
		case 'q': UI.NQ |= (1L << boardIndex++);
			break;
		case 'K': UI.BK |= (1L << boardIndex++);
			break;
		case 'k': UI.NK |= (1L << boardIndex++);
			break;
		case '/':
			break;
		case '1': boardIndex++;
			break;
		case '2': boardIndex += 2;
			break;
		case '3': boardIndex += 3;
			break;
		case '4': boardIndex += 4;
			break;
		case '5': boardIndex += 5;
			break;
		case '6': boardIndex += 6;
			break;
		case '7': boardIndex += 7;
			break;
		case '8': boardIndex += 8;
			break;
		default:
			break;
		}
	}
	UI.MouveB = (fenString.charAt(++charIndex) == 'w');
	charIndex += 2;
	while (fenString.charAt(charIndex) != ' ')
	{
		switch (fenString.charAt(charIndex++))
		{
		case '-':
			break;
		case 'K': UI.CWK = true;
			break;
		case 'Q': UI.CWQ = true;
			break;
		case 'k': UI.CBK = true;
			break;
		case 'q': UI.CBQ = true;
			break;
		default:
			break;
		}
	}
	if (fenString.charAt(++charIndex) != '-')
	{
		UI.EP = Moves.FileMasks8[fenString.charAt(charIndex++) - 'a'];
	}
	//the rest of the fenString is not yet utilized
    }
    
    public static void arrayToBitboards(String[][] Echiquier,long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK) {
        String Binaire;
        for (int i=0;i<64;i++) {
            Binaire="0000000000000000000000000000000000000000000000000000000000000000";
            Binaire=Binaire.substring(i+1)+"1"+Binaire.substring(0, i);
            switch (Echiquier[i/8][i%8]) {
                case "P": BP+=convertStringToBitboard(Binaire);
                    break;
                case "N": BN+=convertStringToBitboard(Binaire);
                    break;
                case "B": BB+=convertStringToBitboard(Binaire);
                    break;
                case "R": BR+=convertStringToBitboard(Binaire);
                    break;
                case "Q": BQ+=convertStringToBitboard(Binaire);
                    break;
                case "K": BK+=convertStringToBitboard(Binaire);
                    break;
                case "p": NP+=convertStringToBitboard(Binaire);
                    break;
                case "n": NN+=convertStringToBitboard(Binaire);
                    break;
                case "b": NB+=convertStringToBitboard(Binaire);
                    break;
                case "r": NR+=convertStringToBitboard(Binaire);
                    break;
                case "q": NQ+=convertStringToBitboard(Binaire);
                    break;
                case "k": NK+=convertStringToBitboard(Binaire);
                    break;
            }
        }
        drawArray(BP,BN,BB,BR,BQ,BK,NP,NN,NB,NR,NQ,NK);
        UI.BP=BP; UI.BN=BN; UI.BB=BB;
        UI.BR=BR; UI.BQ=BQ; UI.BK=BK;
        UI.NP=NP; UI.NN=NN; UI.NB=NB;
        UI.NR=NR; UI.NQ=NQ; UI.NK=NK;
    }
    public static long convertStringToBitboard(String Binaire) {
        if (Binaire.charAt(0)=='0') {//not going to be a negative number
            return Long.parseLong(Binaire, 2);
        } else {
            return Long.parseLong("1"+Binaire.substring(2), 2)*2;
        }
    }
    public static void drawArray(long BP,long BN,long BB,long BR,long BQ,long BK,long NP,long NN,long NB,long NR,long NQ,long NK) {
        String Echiquier[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            Echiquier[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((BP>>i)&1)==1) {Echiquier[i/8][i%8]="P";}
            if (((BN>>i)&1)==1) {Echiquier[i/8][i%8]="N";}
            if (((BB>>i)&1)==1) {Echiquier[i/8][i%8]="B";}
            if (((BR>>i)&1)==1) {Echiquier[i/8][i%8]="R";}
            if (((BQ>>i)&1)==1) {Echiquier[i/8][i%8]="Q";}
            if (((BK>>i)&1)==1) {Echiquier[i/8][i%8]="K";}
            if (((NP>>i)&1)==1) {Echiquier[i/8][i%8]="p";}
            if (((NN>>i)&1)==1) {Echiquier[i/8][i%8]="n";}
            if (((NB>>i)&1)==1) {Echiquier[i/8][i%8]="b";}
            if (((NR>>i)&1)==1) {Echiquier[i/8][i%8]="r";}
            if (((NQ>>i)&1)==1) {Echiquier[i/8][i%8]="q";}
            if (((NK>>i)&1)==1) {Echiquier[i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(Echiquier[i]));
        }
    }
}
