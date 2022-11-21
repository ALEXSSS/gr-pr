package org.gerimedica;

import org.gerimedica.utils.WebWithPostgresContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Context loads test
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GerimedicaAppTest extends WebWithPostgresContainer {

    @Test
    public void contextLoad() {
    }
}