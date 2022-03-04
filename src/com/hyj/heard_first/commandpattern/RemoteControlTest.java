package com.hyj.heard_first.commandpattern;

public class RemoteControlTest {
    public static void main(String[] args) {
       /* SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();

        LightOnCommand lightOnCommand = new LightOnCommand(new Light());
        GarageDoorOpenCommand garageDoorOpenCommand = new GarageDoorOpenCommand(new GarageDoor());
        simpleRemoteControl.setCommand(lightOnCommand);
        simpleRemoteControl.buttonWasPressed();
        simpleRemoteControl.setCommand(garageDoorOpenCommand);
        simpleRemoteControl.buttonWasPressed();*/

        //控制器
        RemoteControl remoteControl = new RemoteControl();
        //设备灯
        Light light = new Light();

        //包装命令
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        LightOffCommand lightOffCommand = new LightOffCommand(light);

        //添加命令
        remoteControl.setCommands(1, lightOnCommand, lightOffCommand);

        //执行
        remoteControl.onButtonWasPushed(1);
        remoteControl.undoCommand();
        remoteControl.offButtonWasPushed(1);
        remoteControl.undoCommand();

        //设备风扇
        CeilingFan ceilingFan = new CeilingFan("living room");
        //风扇命令包装
        CeilingFanHighCommand ceilingFanHighCommand = new CeilingFanHighCommand(ceilingFan);
        CeilingFanMediumCommand ceilingFanMediumCommand = new CeilingFanMediumCommand(ceilingFan);
        CeilingFanLowCommand ceilingFanLowCommand = new CeilingFanLowCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOffCommand = new CeilingFanOffCommand(ceilingFan);

        //添加到控制器
        remoteControl.setCommands(0, ceilingFanHighCommand, ceilingFanOffCommand);
        remoteControl.setCommands(2, ceilingFanMediumCommand, ceilingFanOffCommand);
        remoteControl.setCommands(3, ceilingFanLowCommand, ceilingFanOffCommand);

        //打印命令信息
        System.out.println(remoteControl);

        //风扇 1挡
        remoteControl.onButtonWasPushed(0);
        //风扇 关
        remoteControl.offButtonWasPushed(0);
        //回撤命令
        remoteControl.undoCommand();


        //风扇2挡
        remoteControl.onButtonWasPushed(2);
        remoteControl.undoCommand();


        Command[] partyOn = {lightOnCommand, ceilingFanHighCommand};
        Command[] partOff = {lightOffCommand, ceilingFanMediumCommand, ceilingFanOffCommand};
        //宏命令
        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partOff);
        remoteControl.setCommands(4, partyOnMacro, partyOffMacro);
        remoteControl.onButtonWasPushed(4);
        remoteControl.offButtonWasPushed(4);


    }
}
