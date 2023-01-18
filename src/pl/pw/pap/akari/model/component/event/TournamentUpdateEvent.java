package pl.pw.pap.akari.model.component.event;

import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;

public class TournamentUpdateEvent {
    private final String [] nameList;
    private final boolean generateNewBoard;

    public TournamentUpdateEvent( String [] nameList, boolean generateNewBoard) {
        this.nameList = nameList;
        this.generateNewBoard = generateNewBoard;
    }
    
    public String [] getList()
    {
    	return nameList;
    }
    
    public boolean getGenerateNewBoard()
    {
    	return generateNewBoard;
    }
}