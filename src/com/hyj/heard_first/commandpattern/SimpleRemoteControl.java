package com.hyj.heard_first.commandpattern;

public class SimpleRemoteControl {
    Command command;
    public void setCommand(Command command){
        this.command = command;
    }

    public void buttonWasPressed(){
        command.execute();
    }



}
