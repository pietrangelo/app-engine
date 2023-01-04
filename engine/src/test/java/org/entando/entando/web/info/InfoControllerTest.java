package org.entando.entando.web.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.entando.entando.aps.system.services.health.HealthService;
import org.entando.entando.web.health.HealthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class InfoControllerTest {

    private InfoController infoController;

    @BeforeEach
    public void setup() {
        infoController = new InfoController();
    }

    @Test
    void isHealthyWithWorkingSystemShouldReturnStatus200() {
        assertEquals(HttpStatus.OK.value(), infoController.info().getStatusCodeValue());
    }

}
