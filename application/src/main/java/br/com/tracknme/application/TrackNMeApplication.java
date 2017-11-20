package br.com.tracknme.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.*;

@ComponentScan("br.com.tracknme.*")
@SpringBootApplication
@EnableAutoConfiguration(exclude={RedisAutoConfiguration.class,DataSourceAutoConfiguration.class})
/**
 * Created by Cleberson on 18/11/2017.
 */
public class TrackNMeApplication   {

    public static void main(String[] args) {
        SpringApplication.run(
                TrackNMeApplication.class, args);
    }
}