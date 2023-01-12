package pl.pw.pap.akari;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.view.component.manager.FramesManager;

public class Main {

    public static void main(String[] args) {
        
    	
    	EventHandler eventHandler = new EventHandler();

        FramesManager framesManager = new FramesManager(eventHandler);
        eventHandler.setFramesManager(framesManager);
    }
}
