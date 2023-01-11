package pl.pw.pap.akari.model.game.tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TournamentInfo {
    private List<PlayerInfo> players;

    public TournamentInfo(List<String> nameList)
    {
    	players = new ArrayList<PlayerInfo> (nameList.size());
    	
    	for (String name : nameList)
    	{
    		players.add(new PlayerInfo (name));
    	}
    }
    public List<PlayerInfo> getPlayers() {

        return players;
    }
    
    public String[] getScores()
    {
    	this.sort();
    	
    	String [] scores = new String[players.size() + 1];
    	scores[0] = "ranking    player's names   consumed time";
    	
    	for (int i = 0; i< players.size(); i++)
    	{
    		scores[i+1] = Integer.toString(i+1) + "         " + players.get(i).scoreText();
    	}
    	return scores;
    }
    
    public void setPlayerTime(int index, int time)
    {
    	players.get(index).setTime(time);
    }
    
    public void sort()
    {
    	Collections.sort(players, new Comparator<PlayerInfo>() 
    	{
            public int compare(PlayerInfo p1, PlayerInfo p2) 
            {
                return p1.getTime() - p2.getTime();
            }
    	});
   
    }
    
    
}
