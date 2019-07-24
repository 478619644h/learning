package com.hyj.heard_first.commandpattern;

import javax.crypto.Cipher;

public class RemoteControlTest {
    public static void main(String[] args) {
       /* SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();

        LightOnCommand lightOnCommand = new LightOnCommand(new Light());
        GarageDoorOpenCommand garageDoorOpenCommand = new GarageDoorOpenCommand(new GarageDoor());
        simpleRemoteControl.setCommand(lightOnCommand);
        simpleRemoteControl.buttonWasPressed();
        simpleRemoteControl.setCommand(garageDoorOpenCommand);
        simpleRemoteControl.buttonWasPressed();*/

       RemoteControl remoteControl = new RemoteControl();
       LightOnCommand lightOnCommand = new LightOnCommand(new Light());
       LightOffCommand lightOffCommand = new LightOffCommand(new Light());

       remoteControl.setCommands(1,lightOnCommand,lightOffCommand);



       remoteControl.onButtonWasPushed(1);
       remoteControl.undoCommand();
       remoteControl.offButtonWasPushed(1);
       remoteControl.undoCommand();

       CeilingFan ceilingFan = new CeilingFan("living room");
       CeilingFanHighCommand ceilingFanHighCommand = new CeilingFanHighCommand(ceilingFan);
       CeilingFanMediumCommand ceilingFanMediumCommand = new CeilingFanMediumCommand(ceilingFan);
       CeilingFanLowCommand ceilingFanLowCommand = new CeilingFanLowCommand(ceilingFan);
       CeilingFanOffCommand ceilingFanOffCommand = new CeilingFanOffCommand(ceilingFan);
       remoteControl.setCommands(0,ceilingFanHighCommand,ceilingFanOffCommand);
       remoteControl.setCommands(2,ceilingFanMediumCommand,ceilingFanOffCommand);
       remoteControl.setCommands(3,ceilingFanLowCommand,ceilingFanOffCommand);
        System.out.println(remoteControl);
       remoteControl.onButtonWasPushed(0);
       remoteControl.offButtonWasPushed(0);
       remoteControl.undoCommand();

       remoteControl.onButtonWasPushed(2);
       remoteControl.undoCommand();


       Command[] partyOn = {lightOnCommand,ceilingFanHighCommand};
       Command[] partOff = {lightOffCommand,ceilingFanMediumCommand,ceilingFanOffCommand};
       MacroCommand partyOnMacro = new MacroCommand(partyOn);
       MacroCommand partyOffMacro = new MacroCommand(partOff);
       remoteControl.setCommands(4,partyOnMacro,partyOffMacro);
       remoteControl.onButtonWasPushed(4);
       remoteControl.offButtonWasPushed(4);


    }
}
