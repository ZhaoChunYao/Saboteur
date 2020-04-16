package student_player;

import Saboteur.*;
import Saboteur.cardClasses.*;
import java.util.*;
import java.util.ArrayList;


public class MyTools {
    public static double getSomething() {
        return Math.random();
    }
    
    static int drop(ArrayList<SaboteurCard> hand) {
    	Random rand = new Random();
    	for(int i=0;i<hand.size();i++) {
    		switch (hand.get(i).getName().split(":")[0]) {
    		case "Tile":
    			if (MyTools.isDeadEnd((SaboteurTile)hand.get(i))) {
        			return i;
        		};
    		case "Malus":
    			return i;
    		
    		case "Destroy":
    			return i;
    		}
    	};
    	return rand.nextInt(hand.size());
    }
    
//    static boolean checkMap(SaboteurMove move,SaboteurBoardState boardState) {
//    	int[][] intBoard=boardState.getHiddenIntBoard();
//    	int[] mapPosition=move.getPosPlayed();
////    	System.out.println("Checking " + mapPosition[0] + "," + mapPosition[1]);
//    	if(intBoard[mapPosition[0]*3][mapPosition[1]*3]!=-1) {//check if the objective had already been revealed
////    		System.out.println(true);
//    		return true;
//    	}else {
////    		System.out.println(false);
//    		return false;
//    	}
//    }
//    
    
    static boolean isDeadEnd(SaboteurTile tile) {
    	String tileIdx=tile.getIdx();
    	//System.out.println("I AM HERE" + tileIdx);
    	if(tileIdx.equals("0")||tileIdx.equals("5")||tileIdx.equals("5_flip")||tileIdx.equals("6")
    			||tileIdx.equals("6_flip")||tileIdx.equals("7")||tileIdx.equals("7_flip")
    			||tileIdx.equals("8")||tileIdx.equals("9")||tileIdx.equals("9_flip")||tileIdx.equals("10")){
    		return false;
    	}
    	return true;
    }
    
    public static boolean isConnected(SaboteurMove placeTile,SaboteurBoardState boardState) {
    	// true: node connected to start
    	// false: node NOT connected to start
    	int[][] intBoard=boardState.getHiddenIntBoard();
    	boolean[][] visited = new boolean[42][42];
    	int x = placeTile.getPosPlayed()[0]*3+1;
    	int y = placeTile.getPosPlayed()[1]*3+1;
    	
    	boolean up = ( x-2>=0 && intBoard[x-2][y]==1)? visit(intBoard, new int[] {x-2, y}, visited) : false;
    	boolean down = ( x+2<42 && intBoard[x+2][y]==1)? visit(intBoard, new int[] {x+2, y}, visited) : false;
    	boolean left = ( y-2>=0 && intBoard[x][y-2]==1)? visit(intBoard, new int[] {x, y-2}, visited) : false;
    	boolean right = ( y+2<42 && intBoard[x][y+2]==1)? visit(intBoard, new int[] {x, y+2}, visited) : false;
    	
    	boolean result=up||down||left||right;
    	System.out.println("Is Connected: "+result);
    	return result;
    }
    
    public static boolean visit(int[][] intBoard, int[] node, boolean[][] visited) {
    	// true: node connected to start
    	// false: node NOT connected to start
//    	System.out.println(Arrays.deepToString(visited));
//    	System.out.println("=====Currently visiting: ");
//    	System.out.println(Arrays.toString(node));
    	int x = node[0];
    	int y = node[1];
    	
    	// base case if node is the start 
    	if (x==16 && y==16) {
    		return true;
    	};
    	
    	// set current node as visited
    	visited[x][y] = true;
//    	System.out.println("Verifying visited: ");
//    	System.out.println(visited[x][y]);
    	
//    	for (int i = 0; i<42; i++) {
//    		for (int j = 0; j<42; j++) {
//        		if (visited[i][j]) {
//        			
//        			System.out.println(i +", " + j +" :true");
//        		}
//        	} 
//    	}
    	
    	ArrayList<int[]> neighbors = neighborhood(intBoard, node);
    	
//    	System.out.println("neighbors");
//    	for (int[] neighbor: neighbors) {
//    		System.out.println(Arrays.toString(neighbor));
//    	}
    	
    	// for every neighbor
    	for (int[] neighbor: neighbors) {
    		
    		// if neighbor is not visited
    		if (!visited[neighbor[0]][neighbor[1]]) {
    			
    			
    			// if a neighbor is connected, return true
    			if (visit(intBoard, neighbor, visited)) return true;
    			// if current is not connected, visit next neighbor

    		} else {
//    			System.out.println("visited");
    		}
    	}
    	// if no child is connected, return false and backtrack
//    	System.out.println(Arrays.deepToString(visited));
    	return false;
    }
    
    public static ArrayList<int[]> neighborhood(int[][] intBoard, int[] node) {

    	ArrayList<int[]> res = new ArrayList<int[]>();
    	int i = node[0];
    	int j = node[1];
    	
    	try {
	    	// left
	    	if (intBoard[i-1][j]==1) {
	    		res.add(new int[] {i-1, j});
	    	
	    	}
    	} catch (Exception e) {System.out.println("At board edge");}
    	
    	try {
    		if (intBoard[i+1][j]==1) {
	    		res.add(new int[] {i+1, j});
    		};
    	} catch (Exception e) {System.out.println("At board edge");}
    	
    	try {
    		if (intBoard[i][j-1]==1) {
	    		res.add(new int[] {i, j-1});
    		};
    	} catch (Exception e) {System.out.println("At board edge");}
    	try {
    		if (intBoard[i][j+1]==1) {
	    		res.add(new int[] {i, j+1});
    		};
    	} catch (Exception e) {System.out.println("At board edge");}
	    
//    	}
//    	switch (1) {
//    		case intBoard[i-1][j]: 
//    			res.add(new int[] {i-1, j});
//    			break;
//    		case intBoard[i+1][j]: 
//    			res.add(new int[] {i+1, j});
//    			break;
//    		case intBoard[i][j-1]: 
//    			res.add(new int[] {i, j-1});
//    			break;
//    		case intBoard[i][j+1]: 
//    			res.add(new int[] {i, j+1});
//    			break;
//    	}
    	
//    	for (int i=node[0]-1; i <= node[0]+1; i++) {
//    		for (int j=node[1]-1; j <= node[1]+1; j++) {
//    			
//        		if (intBoard[i][j]==1) res.add(new int[] {i, j})
//        	}
//    	}
    	return res;
    }
    
    public static double getHeuristic(SaboteurMove placeTile,ArrayList<int[]> destinations) {
    	SaboteurTile tile=(SaboteurTile)placeTile.getCardPlayed();
    	String tileIdx=tile.getIdx();
    	int[] pos=placeTile.getPosPlayed();
    	double heuristic=0;
    	switch(tileIdx) {//find the distance between the tips of each path in the tile and the destination, then choose the min
    	case "0":
    	{
    		;
    		int row=3*pos[0]+2;
    		int col=3*pos[1]+1;
    		heuristic=getDistance(row,col,destinations);
    		break;
    	}
    	case "5":
    	{
    		;
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(heuristicRight, heuristicBottom);
    		break;
    	}
    	case "5_flip":
    	{
    		;
    		int row=3*pos[0]+1;
    		int col=3*pos[1]+0;
    		heuristic=getDistance(row,col,destinations);
    		break;
    	}
    	case "6":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(heuristicLeft, heuristicBottom);
    		break;
    	}
    	case "6_flip":
    	{
    		;
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(heuristicRight, heuristicBottom);
    		break;
    	}
    	
    	case "7":
    	{
    		;
    		int row=3*pos[0]+1;
    		int col=3*pos[1]+2;
    		heuristic=getDistance(row,col,destinations);
    		break;
    	}
    	case "7_flip":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(heuristicLeft, heuristicBottom);
    		break;
    	}
    	case "8":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(Math.min(heuristicRight, heuristicLeft), heuristicBottom);
    		break;
    	}
    	case "9":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(Math.min(heuristicRight, heuristicLeft), heuristicBottom);
    		break;
    	}
    	case "9_flip":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		heuristic=Math.min(heuristicRight, heuristicLeft);
    		break;
    	}
    	case "10":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		heuristic=Math.min(heuristicRight, heuristicLeft);
    		break;
    	}
    	}
    	return heuristic;
    }
    
    public static double getHeuristicDebug(SaboteurMove placeTile,ArrayList<int[]> destinations) {
    	SaboteurTile tile=(SaboteurTile)placeTile.getCardPlayed();
    	String tileIdx=tile.getIdx();
    	int[] pos=placeTile.getPosPlayed();
    	double heuristic=0;
    	switch(tileIdx) {//find the distance between the tips of each path in the tile and the destination, then choose the min
    	case "0":
    	{
    		;
    		int row=3*pos[0]+2;
    		int col=3*pos[1]+1;
    		heuristic=getDistance(row,col,destinations);
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+row+"col: "+col);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristic: "+heuristic);
    		break;
    	}
    	case "5":
    	{
    		;
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowRight+"col: "+colRight);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicRight: "+heuristicRight);
    		
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowBottom+"col: "+colBottom);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicBottom: "+heuristicBottom);
    		
    		heuristic=Math.min(heuristicRight, heuristicBottom);
    		break;
    	}
    	case "5_flip":
    	{
    		;
    		int row=3*pos[0]+1;
    		int col=3*pos[1]+0;
    		heuristic=getDistance(row,col,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+row+"col: "+col);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristic: "+heuristic);
    		
    		break;
    	}
    	case "6":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowLeft+"col: "+colLeft);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicLeft: "+heuristicLeft);
    		
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(heuristicLeft, heuristicBottom);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowBottom+"col: "+colBottom);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicBottom: "+heuristicBottom);
    		break;
    	}
    	case "6_flip":
    	{
    		;
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowRight+"col: "+colRight);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicRight: "+heuristicRight);
    		
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(heuristicRight, heuristicBottom);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowBottom+"col: "+colBottom);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicBottom: "+heuristicBottom);
    		break;
    	}
    	
    	case "7":
    	{
    		;
    		int row=3*pos[0]+1;
    		int col=3*pos[1]+2;
    		heuristic=getDistance(row,col,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+row+"col: "+col);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		break;
    	}
    	case "7_flip":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowLeft+"col: "+colLeft);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicLeft: "+heuristicLeft);
    		
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(heuristicLeft, heuristicBottom);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowBottom+"col: "+colBottom);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicBottom: "+heuristicBottom);
    		break;
    	}
    	case "8":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowLeft+"col: "+colLeft);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicLeft: "+heuristicLeft);
    		
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowRight+"col: "+colRight);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicRight: "+heuristicRight);
    		
    		
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(Math.min(heuristicRight, heuristicLeft), heuristicBottom);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowBottom+"col: "+colBottom);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicBottom: "+heuristicBottom);
    		break;
    	}
    	case "9":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowLeft+"col: "+colLeft);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicLeft: "+heuristicLeft);
    		
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowRight+"col: "+colRight);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicRight: "+heuristicRight);
    		
    		int rowBottom=3*pos[0]+2;
    		int colBottom=3*pos[1]+1;
    		double heuristicBottom=getDistance(rowBottom,colBottom,destinations);
    		heuristic=Math.min(Math.min(heuristicRight, heuristicLeft), heuristicBottom);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowBottom+"col: "+colBottom);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicBottom: "+heuristicBottom);
    		break;
    	}
    	case "9_flip":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowLeft+"col: "+colLeft);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicLeft: "+heuristicLeft);
    		
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowRight+"col: "+colRight);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicRight: "+heuristicRight);
    		
    		heuristic=Math.min(heuristicRight, heuristicLeft);
    		break;
    	}
    	case "10":
    	{
    		;
    		int rowLeft=3*pos[0]+1;
    		int colLeft=3*pos[1]+0;
    		double heuristicLeft=getDistance(rowLeft,colLeft,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowLeft+"col: "+colLeft);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicLeft: "+heuristicLeft);
    		
    		int rowRight=3*pos[0]+1;
    		int colRight=3*pos[1]+2;
    		double heuristicRight=getDistance(rowRight,colRight,destinations);
    		
    		System.out.println("Tile shape: "+tileIdx+" "+"row: "+rowRight+"col: "+colRight);
    		System.out.println("Destination:");
    		for(int[] destination:destinations) {
    			System.out.print(destination[0]+","+destination[1]+"; ");
    		}
    		System.out.println("heuristicRight: "+heuristicRight);
    		
    		heuristic=Math.min(heuristicRight, heuristicLeft);
    		break;
    	}
    	}
    	return heuristic;
    }
    
    private static double getDistance(int rowMove,int colMove,ArrayList<int[]> destinations )
    {
    	double shortest=Integer.MAX_VALUE;
    	for(int[] destination:destinations) {
    		double distance=Math.sqrt(Math.pow(3*destination[0]-rowMove,2)+Math.pow(3*destination[1]-colMove,2));
    		if(distance<shortest) {
    			shortest=distance;
    		}
    	}
    	return shortest;
//    	//find the distance between the current position and each of the 3 destinations
//    	double distance1=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*3-colMove,2));
//    	double distance2=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*5-colMove,2));
//    	double distance3=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*7-colMove,2));
//    	//and choose the min
//    	return Math.min(distance1,Math.min(distance2, distance3));
    	
    	
    	/*int[][] intBoard=boardState.getHiddenIntBoard();
    	int hidden1=intBoard[3*12][3*3];
    	int hidden2=intBoard[3*12][3*5];
    	int hidden3=intBoard[3*12][3*7];
    	
    	SaboteurTile[][] board=boardState.getHiddenBoard();
    	String hidden1Idx=board[12][3].getIdx();
    	String hidden2Idx=board[12][5].getIdx();
    	String hidden3Idx=board[12][7].getIdx();*/
    	
//    	boardState.toString();
//    	System.out.println("IntBoard is: ");
//    	for (int i=0;i<42;i++) {
//    		for (int j=0;j<42;j++)
//    		{
//    			System.out.print(intBoard[i][j]);
//    		}
//    		System.out.println();
//    	}
    	
    	
    	
//    	System.out.println("hidden1");
//		
//		for (int k = 0; k < 3; k++) {
//            for (int h = 0; h < 3; h++) {
//            	System.out.print(intBoard[12 * 3 + k][3 * 3 + h]);
//            }
//            System.out.println();
//        };
//        System.out.println("hidden2");
//        for (int k = 0; k < 3; k++) {
//            for (int h = 0; h < 3; h++) {
//            	System.out.print(intBoard[12 * 3 + k][5 * 3 + h]);
//            }
//            System.out.println();
//        };
//        System.out.println("hidden3");
//        for (int k = 0; k < 3; k++) {
//            for (int h = 0; h < 3; h++) {
//            	System.out.print(intBoard[12 * 3 + k][7 * 3 + h]);
//            }
//            System.out.println();
//        }
        
    	// Discovered nugget
    	/*if(hidden1!=-1&&hidden1Idx.equals("nugget")) {
    		double distance=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*3-colMove,2));
//    		System.out.println("nugget1");
    		return distance;
    	}else if(hidden2!=-1&&hidden2Idx.equals("nugget")) {
    		double distance=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*5-colMove,2));
//    		System.out.println("nugget2");
    		return distance;
    	}else if(hidden3!=-1&&hidden3Idx.equals("nugget")) {
    		double distance=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*7-colMove,2));
//    		System.out.println("nugget3");
    		return distance;
    	
    	// Something is revealed
    	}else if(hidden1!=-1&&hidden2!=-1&&hidden3!=-1) {
//    		System.out.println("An error has occurred: all 3 objectives are known but none of them has gold nugget");
    		
    		return 0;
    		
    	}else if(hidden1!=-1&&hidden2!=-1){
    		double distance=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*7-colMove,2));
    		System.out.println("hidden1 and hidden2 revealed");
    		return distance;
    	}else if(hidden1!=-1&&hidden3!=-1) { //here
    		double distance=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*5-colMove,2));
    		System.out.println("hidden1 and hidden3 revealed");
    		return distance;
    	}else if(hidden2!=-1&&hidden3!=-1) { //here
    		double distance=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*3-colMove,2));
    		System.out.println("hidden2 and hidden2 revealed");
    		return distance;
    		
    	}else if(hidden1!=-1) {
    		double distance2=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*5-colMove,2));
	    	double distance3=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*7-colMove,2));
	    	System.out.println("hidden1 revealed");
	    	return Math.min(distance2,distance3);
    	}else if(hidden2!=-1) {
    		double distance1=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*3-colMove,2));
	    	double distance3=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*7-colMove,2));
	    	System.out.println("hidden2 revealed");
	    	return Math.min(distance1,distance3);
    	}else if(hidden3!=-1) {
    		double distance1=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*3-colMove,2));
	    	double distance2=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*5-colMove,2));
	    	System.out.println("hidden3 revealed");
	    	return Math.min(distance1,distance2);
	    
	    //Everything is still hidden
    	}else {
	    	//find the distance between the current position and each of the 3 destinations
	    	double distance1=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*3-colMove,2));
	    	double distance2=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*5-colMove,2));
	    	double distance3=Math.sqrt(Math.pow(3*12-rowMove,2)+Math.pow(3*7-colMove,2));
	    	System.out.println("Nothing revealed");
	    	//and choose the min
	    	return Math.min(distance1,Math.min(distance2, distance3));
    	}*/
    	
    }
    
    
    static boolean isTrashPosition(SaboteurMove move) {
    	int[] pos = move.getPosPlayed();
    	SaboteurTile tile=(SaboteurTile)move.getCardPlayed();
    	int[][] path = tile.getPath();
    	if(pos[0]<5)return true;
    	switch(pos[0]) {
    	case 11:
    		;
    		switch(pos[1]) {
    		case 3:
    		case 5:
    		case 7:
    			;
    			if(path[1][0] != 1) {return true;}
    			break;
    		}
    		break;
    	case 12:
    		;
    		switch(pos[1]) {
    		case 2:
    			;
    			if(path[2][1]!=1) {return true;}
    			break;
    		case 4:
    		case 6:
    			;
    			if(path[0][1]!=1 && path[2][1]!=1) {return true;}
    			break;
    		case 8:
    			if(path[0][1]!=1) {return true;}
    			break;
    		}
    		break;
    	case 13:
    		;
    		switch(pos[1]) {
    		case 3:
    		case 5:
    		case 7:
    			;
    			if(path[1][2] != 1) {return true;}
    			break;
    		}
    		break;
    	}
    	return false;
    }

}