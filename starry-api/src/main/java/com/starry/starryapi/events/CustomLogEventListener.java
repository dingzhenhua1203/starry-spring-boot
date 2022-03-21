package com.starry.starryapi.events;

import org.springframework.context.event.EventListener;

public class CustomLogEventListener {

    @EventListener(CustomLogEvent.class)
    public void saveLog(CustomLogEvent event) {
        Object source = event.getSource();
        // save(source)


    }
}
