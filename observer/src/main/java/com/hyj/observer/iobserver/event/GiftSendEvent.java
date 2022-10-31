package com.hyj.observer.iobserver.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftSendEvent extends BaseEvent {

    private String gifIt;

}
