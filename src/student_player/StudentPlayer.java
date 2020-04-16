package student_player;

import boardgame.Move;
import java.util.ArrayList;
import boardgame.Board;
import boardgame.BoardState;

import java.lang.reflect.Array;
import java.util.*;
import Saboteur.SaboteurPlayer;
import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.*;

/** A player file submitted by a student. */
public class StudentPlayer extends SaboteurPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
	
	ArrayList<int[]> destinations = new ArrayList<int[]>();
	
//	static ArrayList<int[]> remove(ArrayList<int[]> destinations, int[] item) {
//		
//		for (int[] coord: destinations) {
//			if ((coord[0] == item[0]) && (coord[1] == item[1])) {
//				destinations.remove(coord);
//			}
//		}
//		return destinations;
//	}

	
    public StudentPlayer() {
        super("260807659");
        this.destinations.add(new int[] {12, 3});
        this.destinations.add(new int[] {12, 5});
    	this.destinations.add(new int[] {12, 7});
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(SaboteurBoardState boardState) {
    	 long startTime = System.nanoTime();
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        //MyTools.getSomething();
        ArrayList<SaboteurMove> legalMoves = boardState.getAllLegalMoves();
        
        //Is random the best you can do?
        
        
//        String flag=""; //indicate whether there are some specific moves in legalMoves
        ArrayList<SaboteurMove> tileMoves = new ArrayList<SaboteurMove>();
        SaboteurTile[][] board=boardState.getHiddenBoard();
        //String hidden1Idx=board[12][3].getIdx();
    	SaboteurTile hidden1=board[12][3];
    	SaboteurTile hidden2=board[12][5];
    	SaboteurTile hidden3=board[12][7];
    	
    	if (hidden1.getIdx().equals("nugget")){
			this.destinations.clear();
			this.destinations.add(new int[] {12, 3});
			
		} else if (hidden2.getIdx().equals("nugget")){
			this.destinations.clear();
			this.destinations.add(new int[] {12, 5});
			
		} else if (hidden2.getIdx().equals("nugget")){
			this.destinations.clear();
			this.destinations.add(new int[] {12, 7});
		}
    	System.out.println("###################### LEGAL MOVES TILES ##############################");
        for (SaboteurMove move: legalMoves) {
//        	System.out.println(move.getCardPlayed().getName().split(":")[0]);
        	switch (move.getCardPlayed().getName().split(":")[0]) {
        	case "Tile": 
        		// if not a dead end, add to list
        		System.out.println(((SaboteurTile)(move.getCardPlayed())).getIdx() + " , " + move.getPosPlayed()[0] + " , " + move.getPosPlayed()[1] );
        		SaboteurTile tile=(SaboteurTile)move.getCardPlayed();
//        		System.out.println("PATH BROKEN: " + MyTools.isBrokenPath(move, boardState));
        		if ((!MyTools.isDeadEnd(tile))&&(!MyTools.isTrashPosition(move))&&(MyTools.isConnected(move, boardState))) { //&&(MyTools.isConnected(move, boardState))
        			tileMoves.add(move);
        		}
        		break;
            case "Malus": 
            	return move;
            case "Bonus": 
            	return move;
        	case "Map":
        	{
        		if (destinations.size() > 1) {//always use map on the first destination in the destination list
        			if ((move.getPosPlayed()[0] == destinations.get(0)[0]) && (move.getPosPlayed()[1] == destinations.get(0)[1])) {
        				
//        				StudentPlayer.remove(destinations, new int[] {move.getPosPlayed()[0], move.getPosPlayed()[1]});
        				destinations.removeIf(pos -> (pos[0]==move.getPosPlayed()[0]&&pos[1]==move.getPosPlayed()[1]));
        				return move;
        			}
        			break;
        		}
        		break;
        		
        	}
        	}

        }
//        System.out.println("Hand");
        for (SaboteurMove move: legalMoves) {
//        	System.out.println(move.getCardPlayed().getName());
        }
        if (tileMoves.isEmpty()) {
        	ArrayList<SaboteurCard> hand = boardState.getCurrentPlayerCards();
        	int playerID = legalMoves.get(0).getPlayerID();
        	int cardIndex = MyTools.drop(hand);
        	return new SaboteurMove(new SaboteurDrop(), cardIndex, 0, playerID);
        } else {
        	System.out.println("####################### TILEMOVES #####################");
//        	for (SaboteurMove move: tileMoves) {
//        		System.out.println(((SaboteurTile)(move.getCardPlayed())).getIdx() + " , " + move.getPosPlayed()[0] + " , " + move.getPosPlayed()[1] + );
//        	}
        }
        
        Move myMove = tileMoves.get(0);
        double accumulator = MyTools.getHeuristic(tileMoves.get(0), this.destinations);
        double current;
        System.out.println("tileMoves array size: " + tileMoves.size());
        // for every tile in tileMove
        for (SaboteurMove tile: tileMoves) {
        	// current: heuristic of tile
        	current = MyTools.getHeuristic(tile, this.destinations);
        	System.out.println(((SaboteurTile)(tile.getCardPlayed())).getIdx() + " , " + tile.getPosPlayed()[0] + " , " + tile.getPosPlayed()[1] + " , " +current);
        	if (current < 1.0) MyTools.getHeuristicDebug(tile, this.destinations);
        	// if current heuristic is smaller than minimum, update the minimum
        	if (current < accumulator) {
        		accumulator = current;
        		myMove = tile;
        	}
        }
        
//        myMove = Collections.min(tileMoves, new Comparator<SaboteurMove>() {
//        	@Override
//        	public int compare(SaboteurMove first, SaboteurMove second) {
//        		if ((MyTools.getHeuristic(first)) > (MyTools.getHeuristic(second))) {
//        			return 1;
//        		} else if ((MyTools.getHeuristic(second)) > (MyTools.getHeuristic(first))) {
//        			return -1;
//        		};
//        		return 0;
//        	}
//        })
        
        
        if (((SaboteurMove)myMove).getCardPlayed().getName().split(":")[0].equals("Tile")) {
        	int[][] path = ((SaboteurTile)(((SaboteurMove)myMove).getCardPlayed())).getPath();
        	
        	//switching on x
        	switch (((SaboteurMove)myMove).getPosPlayed()[0]) {
	        	case 11: 
	        		//switching on y
	        		switch (((SaboteurMove)myMove).getPosPlayed()[1]) {
		        		case 3: if (path[1][0] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==3)); 
		        			break;
		        		case 5: if (path[1][0] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==5)); 
		        			break;
		        		case 7: if (path[1][0] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==7)); 
		        			break;
	        		}
	        		break;
	        	case 12:
	        		switch (((SaboteurMove)myMove).getPosPlayed()[1]) {
		        		case 2: if (path[2][1] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==3)); 
		        			break;
		        		case 4:{
		        			
		        			if (path[0][1] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==3));
		        			if (path[2][1] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==5));
		        			break;
		        		}
		        		case 6:{
		        			if (path[0][1] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==5));
		        			if (path[2][1] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==7)); 
		        			break;
		        		}
		        		case 8: if (path[0][1] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==7)); 
		        			break;
	        		}
	        		break;
	        	case 13:
	        		switch (((SaboteurMove)myMove).getPosPlayed()[1]) {
		        		case 3: if (path[1][2] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==3)); 
		        			break;
		        		case 5: if (path[1][2] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==5)); 
		        			break;
		        		case 7: if (path[1][2] == 1) destinations.removeIf(pos -> (pos[0]==12&&pos[1]==7)); 
		        			break;
	        		}
	        		break;
        	}
        }
        System.out.println("################### DESTINATIONS ##################");
        for (int[] destination: destinations) {
        	System.out.println(destination[0] + "," +destination[1]);
        	
        }
        // Return your move to be processed by the server.
//        System.out.println("isTrashPosition: " + MyTools.isTrashPosition((SaboteurMove)myMove));
        long elapsedTime = System.nanoTime() - startTime;
        
        System.out.println("+++++++++++++++++++++++Total execution time in nano seconds: "
                + elapsedTime/1000);
        return myMove;
          
    }
}