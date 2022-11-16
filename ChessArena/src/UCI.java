import java.util.*;

public class UCI {
    static String ENGINENAME="UI v1";
    public static void uciCommunication() {
    	Scanner input = new Scanner(System.in);
    	while (true)
        {
            
            String inputString=input.nextLine();
            if ("uci".equals(inputString))
            {
                inputUCI();
            }
            else if (inputString.startsWith("setoption"))
            {
                inputSetOption(inputString);
            }
            else if ("isready".equals(inputString))
            {
                inputIsReady();
            }
            else if ("ucinewgame".equals(inputString))
            {
                inputUCINewGame();
            }
            else if (inputString.startsWith("position"))
            {
                inputPosition(inputString);
            }
            else if (inputString.startsWith("go"))
            {
                inputGo();
            }
            else if (inputString.equals("quit"))
            {
                inputQuit();
            }
            else if ("print".equals(inputString))
            {
                inputPrint();
            }
        }
    }
    public static void inputUCI() {
        System.out.println("id name "+ENGINENAME);
        System.out.println("id author Mehdi");
        //options go here
        System.out.println("uciok");
    }
    public static void inputSetOption(String inputString) {
        //set options
    }
    public static void inputIsReady() {
         System.out.println("readyok");
    }
    public static void inputUCINewGame() {
        //add code here
    }
    public static void inputPosition(String input) {
        input=input.substring(9).concat(" ");
        if (input.contains("startpos ")) {
            input=input.substring(9);
            pion.stringFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        }
        else if (input.contains("fen")) {
            input=input.substring(4);
            pion.stringFen(input);
        }
        if (input.contains("moves")) {
            input=input.substring(input.indexOf("moves")+6);
            //make each of the moves
            while (input.length()>0)
            {
                String moves;
                if (UI.MouveB) {
                    moves=Moves.MouvementCandidatB(UI.BP,UI.BN,UI.BB,UI.BR,UI.BQ,UI.BK,UI.NP,UI.NN,UI.NB,UI.NR,UI.NQ,UI.NK,UI.EP,UI.CBK,UI.CBQ,UI.CBK,UI.CBQ);
                } else {
                    moves=Moves.MouvementCandidatN(UI.BP,UI.BN,UI.BB,UI.BR,UI.BQ,UI.BK,UI.NP,UI.NN,UI.NB,UI.NR,UI.NQ,UI.NK,UI.EP,UI.CBK,UI.CBQ,UI.CBK,UI.CBQ);
                }
                algebraToMove(input,moves);
                input=input.substring(input.indexOf(' ')+1);
            }
        }
    }
    public static void inputGo() {
        //search for best move
        String move;
        if (UI.MouveB) {
            move=Moves.MouvementCandidatB(UI.BP,UI.BN,UI.BB,UI.BR,UI.BQ,UI.BK,UI.NP,UI.NN,UI.NB,UI.NR,UI.NQ,UI.NK,UI.EP,UI.CBK,UI.CBQ,UI.CBK,UI.CBQ);
        } else {
            move=Moves.MouvementCandidatN(UI.BP,UI.BN,UI.BB,UI.BR,UI.BQ,UI.BK,UI.NP,UI.NN,UI.NB,UI.NR,UI.NQ,UI.NK,UI.EP,UI.CBK,UI.CBQ,UI.CBK,UI.CBQ);
        }
        int index=(int)(Math.floor(Math.random()*(move.length()/4))*4);
        System.out.println("bestmove "+moveToAlgebra(move.substring(index,index+4)));
    }
    public static String moveToAlgebra(String move) {
        String append="";
        int start=0,end=0;
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
        } else if (move.charAt(3)=='P') {//pawn promotion
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[1]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[0]);
            } else {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[6]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[7]);
            }
            append=""+Character.toLowerCase(move.charAt(2));
        } else if (move.charAt(3)=='E') {//en passant
            if (move.charAt(2)=='W') {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[3]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[2]);
            } else {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[4]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[5]);
            }
        }
        String returnMove="";
        returnMove+=(char)('a'+(start%8));
        returnMove+=(char)('8'-(start/8));
        returnMove+=(char)('a'+(end%8));
        returnMove+=(char)('8'-(end/8));
        returnMove+=append;
        return returnMove;
    }
    public static void algebraToMove(String input,String moves) {
        int start=0,end=0;
        int from=(input.charAt(0)-'a')+(8*('8'-input.charAt(1)));
        int to=(input.charAt(2)-'a')+(8*('8'-input.charAt(3)));
        for (int i=0;i<moves.length();i+=4) {
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                start=(Character.getNumericValue(moves.charAt(i+0))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                end=(Character.getNumericValue(moves.charAt(i+2))*8)+(Character.getNumericValue(moves.charAt(i+3)));
            } else if (moves.charAt(i+3)=='P') {//paBN promotion
                if (Character.isUpperCase(moves.charAt(i+2))) {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[1]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[0]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[6]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[7]);
                }
            } else if (moves.charAt(i+3)=='E') {//en passant
                if (moves.charAt(i+2)=='W') {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[3]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[2]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[4]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[5]);
                }
            }
            if ((start==from) && (end==to)) {
                if ((input.charAt(4)==' ') || (Character.toUpperCase(input.charAt(4))==Character.toUpperCase(moves.charAt(i+2)))) {
                    if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                        start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                        if (((1L<<start)&UI.BK)!=0) {UI.CBK=false; UI.CBQ=false;}
                        else if (((1L<<start)&UI.NK)!=0) {UI.CBK=false; UI.CBQ=false;}
                        else if (((1L<<start)&UI.BR&(1L<<63))!=0) {UI.CBK=false;}
                        else if (((1L<<start)&UI.BR&(1L<<56))!=0) {UI.CBQ=false;}
                        else if (((1L<<start)&UI.NR&(1L<<7))!=0) {UI.CBK=false;}
                        else if (((1L<<start)&UI.NR&1L)!=0) {UI.CBQ=false;}
                    }
                    UI.EP=Moves.makeMoveEP(UI.BP|UI.NP,moves.substring(i,i+4));
                    UI.BR=MouvementCastle.makeMoveCastle(UI.BR, UI.BK|UI.NK, moves.substring(i,i+4), 'R');
                    UI.NR=MouvementCastle.makeMoveCastle(UI.NR, UI.BK|UI.NK, moves.substring(i,i+4), 'r');
                    UI.BP=Moves.makeMove(UI.BP, moves.substring(i,i+4), 'P');
                    UI.BN=Moves.makeMove(UI.BN, moves.substring(i,i+4), 'N');
                    UI.BB=Moves.makeMove(UI.BB, moves.substring(i,i+4), 'B');
                    UI.BR=Moves.makeMove(UI.BR, moves.substring(i,i+4), 'R');
                    UI.BQ=Moves.makeMove(UI.BQ, moves.substring(i,i+4), 'Q');
                    UI.BK=Moves.makeMove(UI.BK, moves.substring(i,i+4), 'K');
                    UI.NP=Moves.makeMove(UI.NP, moves.substring(i,i+4), 'p');
                    UI.NN=Moves.makeMove(UI.NN, moves.substring(i,i+4), 'n');
                    UI.NB=Moves.makeMove(UI.NB, moves.substring(i,i+4), 'b');
                    UI.NR=Moves.makeMove(UI.NR, moves.substring(i,i+4), 'r');
                    UI.NQ=Moves.makeMove(UI.NQ, moves.substring(i,i+4), 'q');
                    UI.NK=Moves.makeMove(UI.NK, moves.substring(i,i+4), 'k');
                    UI.MouveB=!UI.MouveB;
                    break;
                }
            }
        }
    }
    
    public static void inputQuit() {
        System.exit(0);
    }
    
    public static void inputPrint() {
        pion.drawArray(UI.BP,UI.BN,UI.BB,UI.BR,UI.BQ,UI.BK,UI.NP,UI.NN,UI.NB,UI.NR,UI.NQ,UI.NK);
        System.out.print("Zobrist Hash: ");
        System.out.println(Zobrist.getZobristHash(UI.BP,UI.BN,UI.BB,UI.BR,UI.BQ,UI.BK,UI.NP,UI.NN,UI.NB,UI.NR,UI.NQ,UI.NK,UI.EP,UI.CWK,UI.CWQ,UI.CBK,UI.CBQ,UI.MouveB));
    }
	
}
