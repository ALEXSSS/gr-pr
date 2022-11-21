package org.gerimedica.code;

import org.gerimedica.utils.WebWithPostgresContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodeControllerTest extends WebWithPostgresContainer {

    @Test
    void testController() {
        // test post get
        // didn't have time for it
    }
}