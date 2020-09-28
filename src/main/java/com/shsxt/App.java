package com.shsxt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(value = {"com.shsxt.dao"})
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
