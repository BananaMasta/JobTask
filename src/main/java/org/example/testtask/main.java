package org.example.testtask;

public class main {
    public static void main(String[] args) {
        String reg = "^[\\w!#$%&’*+/=?`\\{|\\}~^\\-]+(?:\\.[\\w!#$%&’*+/=?`\\{|\\}~^\\-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String mail = "mail@mail.ru";
        if(mail.matches(reg)){
            System.out.println("true");
        }
    }
}
