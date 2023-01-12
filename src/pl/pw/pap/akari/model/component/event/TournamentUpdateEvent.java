package pl.pw.pap.akari.model.component.event;

import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;

public class TournamentUpdateEvent {
    private final String [] nameList;

    public TournamentUpdateEvent( String [] nameList) {
        this.nameList = nameList;
    }
    
    public String [] getList()
    {
    	return nameList;
    }
}