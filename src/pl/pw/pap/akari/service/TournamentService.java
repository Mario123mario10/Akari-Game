package pl.pw.pap.akari.service;

import pl.pw.pap.akari.model.game.tournament.PlayerInfo;
import pl.pw.pap.akari.model.game.tournament.TournamentInfo;

import java.util.List;

public class TournamentService {
    private int currentPlayerIndex;
    private TournamentInfo info;
    
    public TournamentService(List<String> nameList)
    {
    	currentPlayerIndex = 0;
    	info = new TournamentInfo(nameList);
    }
    
    public void incrementIndex(int time)
    {
    	info.setPlayerTime(currentPlayerIndex, time);
    	++currentPlayerIndex;
    }


    public List<PlayerInfo> getPlayers(){

        return info.getPlayers();
    }

    public boolean isLastPlayer()
    {
    	return currentPlayerIndex == (getPlayers().size() - 1);
		
    }

    public PlayerInfo getCurrentPlayerName(){
        List<PlayerInfo> playerInfos = info.getPlayers();
        PlayerInfo result = null;
        if (currentPlayerIndex < playerInfos.size()){
             result = playerInfos.get(currentPlayerIndex);
            
        }
        return result;
    }
    

    public TournamentInfo getTournamentInfo()
    {
    	return info;
    }
}
