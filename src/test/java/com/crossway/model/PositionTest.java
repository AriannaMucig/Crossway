package com.crossway.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {
    @Test
    void testPositionCoordinates(){

        int expectedX = 1;
        int expectedY = 1;

        Position pos = new Position(expectedX, expectedY);

        assertThat(pos.x()).isEqualTo(expectedX);
        assertThat(pos.y()).isEqualTo(expectedY);
    }
}
