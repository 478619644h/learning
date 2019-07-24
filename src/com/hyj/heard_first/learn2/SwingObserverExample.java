package com.hyj.heard_first.learn2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingObserverExample {

    JFrame jFrame;

    public static void main(String[] args) throws InterruptedException {
        SwingObserverExample swingObserverExample = new SwingObserverExample();
        swingObserverExample.go();
        Thread.sleep(1222222222222l);
    }

    public void go(){
        jFrame = new JFrame();

        JButton button = new JButton("Should I do it?");
        button.addActionListener(new AngelListener());
        button.addActionListener(new DevilListener());
        jFrame.getContentPane().add(BorderLayout.CENTER,button);

    }


    class AngelListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println("Don`t do it,you might regret it!");
        }
    }

    class DevilListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("come on,do it!");
        }
    }
}
