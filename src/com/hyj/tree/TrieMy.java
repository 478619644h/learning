package com.hyj.tree;

import java.util.concurrent.TimeUnit;

public class TrieMy {

    private final static int SIZE = 26;

    private TrieNode root;

    TrieMy(){
        root = new TrieNode();
    }


    private class TrieNode{

        //有多少单词通过这个节点,即由根至该节点组成的字符串模式出现的次数
        private int num;
        //所有的儿子节点
        private TrieNode[] son;
        //节点值
        private char val;
        //是不是最后一个节点
        private boolean isEnd;

        private boolean haveSon;

        TrieNode(){
            num = 1;
            son = new TrieNode[SIZE];
            isEnd = false;
            haveSon = false;

        }


    }

    public void insert(String str){

        if(str == null || str.length() == 0){
            return;
        }
        TrieNode node = root;
        char[] chars = str.toCharArray();
        for(int i = 0,len = chars.length;i < len;i++){
            int pos = chars[i] - 'a';
            if(node.son[pos] == null){
                node.haveSon = true;
                node.son[pos] = new TrieNode();
                node.son[pos].val = chars[i];
            } else {
                node.son[pos].num += 1;
            }
            node = node.son[pos];
        }
        node.isEnd = true;
    }

    public int countPrefix(String prefix){
        if (prefix == null || prefix.length() == 0) {
            return -1;
        }

        TrieNode node = root;
        char[] chars = prefix.toCharArray();
        for (int i = 0,len = chars.length;i < len; i++){
            int pos = chars[i] - 'a';
            if(node.son[pos] == null){
                return 0;
            }
            node = node.son[pos];
        }
        return node.num;
    }

    public static void main(String[] args) {
        String[] dict = {"abc","abe"};
        TrieMy trieMy = new TrieMy();
        for (String s : dict) {
            trieMy.insert(s);
        }
        System.out.println(trieMy.countPrefix("ac"));
    }

}
