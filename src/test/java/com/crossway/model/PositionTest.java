package com.crossway.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {
    @Test
    void testPositionCoordinates() {

        int expectedX = 1;
        int expectedY = 1;

        Position pos = new Position(expectedX, expectedY);

        assertThat(pos.x()).isEqualTo(expectedX);
        assertThat(pos.y()).isEqualTo(expectedY);
    }

    @Test
    void testPositionEquality() {
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(1, 1);
        Position pos3 = new Position(1, 2);

        assertThat(pos1).isEqualTo(pos2);
        assertThat(pos1).isNotEqualTo(pos3);
    }
}
