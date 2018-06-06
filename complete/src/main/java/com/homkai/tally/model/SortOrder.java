package com.homkai.tally.model;

import org.springframework.data.domain.Sort.Direction;

public class SortOrder {

    private Direction direction;

    private String property;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction.toUpperCase().equals("ASC") ? Direction.ASC : Direction.DESC;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}