package org.example.tags;

import java.util.Objects;

class TransactionTag {
    private final String name;
    private final String description;
    private final String color;

    public TransactionTag(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionTag that = (TransactionTag) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}

