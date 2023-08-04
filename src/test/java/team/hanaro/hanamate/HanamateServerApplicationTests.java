package team.hanaro.hanamate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
class HanamateServerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("hello, world");
        assertThat("hello").isEqualTo("hello");
    }

}
