package com.hyj.annotation;

public class PWUtil {

    @UseCase(id=47)
    public boolean validatePassword(String pw){
        return (pw.matches("\\w*\\d\\W*"));
    }

    @UseCase(id = 48)
    public String encryptPassword(String password){
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49,description = "test annotation")
    public void test(){

    }

}
