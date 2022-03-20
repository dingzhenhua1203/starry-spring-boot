package com.starry.starryapi;


import com.starry.starryapi.config.StarryConfig;
import com.starry.starryapi.config.StarryConfig2;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StarryConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class StarryApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
