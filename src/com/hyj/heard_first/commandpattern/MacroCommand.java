package com.hyj.heard_first.commandpattern;

//宏命令
public class MacroCommand implements Command {

    Command[] commands;

    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for (int i = 0,j = commands.length; i < j;i++){
            commands[i].execute();
        }
    }

    @Override
    public void undo() {

    }
}
