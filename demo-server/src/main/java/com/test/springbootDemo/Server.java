package com.test.springbootDemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Hello world!
 *
 */
@SpringBootApplication
public class Server
{
    public static void main(String[] args){
        com.alibaba.dubbo.container.Main.main(args);
    }
}
