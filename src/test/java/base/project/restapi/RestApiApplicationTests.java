package base.project.restapi;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.test.context.ActiveProfiles;

import java.util.Properties;

@SpringBootTest
@ActiveProfiles("test")
class RestApiApplicationTests {
	@Before
	public void setUp() {
		Properties properties = new Properties();

		properties.setProperty("port", "6379");

		RedisServer redisServer = RedisServer.newServerFrom(properties);

		
	}

	@Test
	void exampleTest() {
		Assertions.assertSame("TEST", "TEST");
	}
}
