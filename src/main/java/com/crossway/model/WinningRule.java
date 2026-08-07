package com.crossway.model;

import java.util.Optional;

public interface WinningRule {
    Optional<PlayerColor> getWinner(Board board);
}