package pl.pw.pap.akari.service;

import pl.pw.pap.akari.model.game.tournament.PlayerInfo;
import pl.pw.pap.akari.model.game.tournament.TournamentInfo;

import java.util.List;

public class TournamentService {
    int currentPlayerIndex;
    TournamentInfo info;


    public List<PlayerInfo> getPlayers(){

        return info.getPlayers();
    }

    public boolean isLastPlayer(){

    }

    public PlayerInfo getCurrentPlayerName(){
        List<PlayerInfo> playerInfos = info.getPlayers();
        PlayerInfo result = null;
        if (currentPlayerIndex > playerInfos.size()){
             result = playerInfos.get(currentPlayerIndex);
            currentPlayerIndex ++;
        }
        return result;
    }

}
