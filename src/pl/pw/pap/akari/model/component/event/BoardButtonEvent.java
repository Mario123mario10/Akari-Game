package pl.pw.pap.akari.model.component.event;

public class BoardButtonEvent {
    private int x, y;
    private EVENT_TYPE eventType;

    public BoardButtonEvent(int x, int y, EVENT_TYPE eventType) {
        this.x = x;
        this.y = y;
        this.eventType = eventType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EVENT_TYPE getEventType() {
        return eventType;
    }
}
