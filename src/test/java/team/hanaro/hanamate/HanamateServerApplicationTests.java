package team.hanaro.hanamate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class HanamateServerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("hello, world");
        assertThat("hello").isEqualTo("hello");
    }

}
