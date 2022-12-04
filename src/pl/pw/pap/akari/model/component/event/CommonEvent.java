package pl.pw.pap.akari.model.component.event;

public class CommonEvent {
    private EVENT_TYPE eventType;

    public CommonEvent(EVENT_TYPE eventType) {
        this.eventType = eventType;
    }

    public EVENT_TYPE getEventType() {
        return eventType;
    }
}
