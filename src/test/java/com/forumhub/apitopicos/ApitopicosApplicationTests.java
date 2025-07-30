package com.forumhub.apitopicos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // Importe esta anotação

@SpringBootTest
@ActiveProfiles("test") // ADICIONE ESTA LINHA
class ApitopicosApplicationTests {

	@Test
	void contextLoads() {
	}

}