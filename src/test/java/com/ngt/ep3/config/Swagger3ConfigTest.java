package com.ngt.ep3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Swagger3ConfigTest {

    private Swagger3Config swagger3ConfigUnderTest;

    @BeforeEach
    void setUp() {
        swagger3ConfigUnderTest = new Swagger3Config();
    }

    @Test
    void testCustomizeOpenAPI() {
        // Arrange
        OpenAPI expectedResult = new OpenAPI(SpecVersion.V30);

        // Act
        OpenAPI result = swagger3ConfigUnderTest.customizeOpenAPI();

        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }
}
