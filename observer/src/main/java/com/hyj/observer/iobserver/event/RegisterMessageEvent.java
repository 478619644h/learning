package com.hyj.observer.iobserver.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterMessageEvent extends BaseEvent {

    private String msgId;

}
