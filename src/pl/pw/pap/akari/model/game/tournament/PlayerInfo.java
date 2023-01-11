package pl.pw.pap.akari.model.game.tournament;

public class PlayerInfo {
    
	private String nickName;
    private int time; // w milisekundach
    
	public PlayerInfo(String name) 
	{
		nickName = name;
		time = -1;
	}
	
	public String getName()
	{
		return this.nickName;
	}
	
	public int getTime()
	{
		return this.time;
	}
	
	public void setTime(int time)
	{
		this.time = time;
	}
	
	public String scoreText()
	{
		return nickName + "                   " + time;
	}
	
	
    
    
}
