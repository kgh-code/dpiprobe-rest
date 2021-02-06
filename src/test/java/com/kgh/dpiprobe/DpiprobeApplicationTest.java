package com.kgh.dpiprobe;

import com.kgh.dpiprobe.controllers.StubController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/*

Uses legacy JUnit 'vintage' profiles if run from within an IDE (intelij)
Embeded tomcat in Springboot does not cause a problem
The test profile for the project (in pom) is skipped in the IDE ???

 */
@SpringBootTest
public class DpiprobeApplicationTest {

    @Autowired
    private StubController sc;

    @Test
    public void test_spring_boot_application_starts() throws Exception {
        assertThat(sc).isNotNull();
    }

}