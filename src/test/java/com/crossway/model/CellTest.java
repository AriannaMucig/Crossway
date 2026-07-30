package com.crossway.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CellTest {
    @Test
    void testInitialCellIsEmpty() {
        Cell cell = new Cell();

        assertThat(cell.isEmpty()).isTrue();
    }

    @Test
    void testCellSetColor() {
        Cell cell = new Cell();
        cell.setColor(PlayerColor.BLACK);
        assertThat(cell.getColor()).isEqualTo(PlayerColor.BLACK);
        assertThat(cell.isEmpty()).isFalse();

    }
}
