package com.hyj.algorithm;


import java.util.LinkedList;

public class CatDogQ {

    private int count = 0;

    private LinkedList<PetEntery> cat = new LinkedList<>();

    private LinkedList<PetEntery> dog = new LinkedList<>();

    public void add(Pet pet){
        if(pet instanceof Cat){
            cat.add(new PetEntery((Cat)pet,count));
        } else if(pet instanceof Dog){
            dog.add(new PetEntery((Dog)pet,count));
        }
        count++;
    }

    public Pet poll(){
        if(!cat.isEmpty()&&!dog.isEmpty()){
            if(cat.peek().getTime() < dog.peek().getTime()){
                return cat.poll().getData();
            } else {
                return dog.poll().getData();
            }
        } else if(!cat.isEmpty()&&dog.isEmpty()){
            return cat.poll().getData();
        } else if(!dog.isEmpty()&&cat.isEmpty()){
            return dog.poll().getData();
        } else {
            throw new RuntimeException("queue is empty");
        }
    }

    public int size(){
        return cat.size()+dog.size();
    }

    public static int twoSum(int[] nums) {
        int result = 0;
        for(int i = 0; i < nums.length;i++){
            result = result ^ nums[i];
            System.out.println(result);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = {2,2,1};
        System.out.println(twoSum(a));
    }

}
