package br.com.balanzo.domain.financeiro.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VisibilityScope {
    private_,   // DB: 'private'
    shared_read,
    shared_edit,
    shared_manage,
    analytical_only;

    public String toDbValue() {
        return this == private_ ? "private" : name();
    }

    @JsonValue
    public String toApiValue() {
        return toDbValue();
    }

    public static VisibilityScope fromDbValue(String v) {
        return fromApiValue(v);
    }

    @JsonCreator
    public static VisibilityScope fromApiValue(String v) {
        return "private".equals(v) ? private_ : valueOf(v);
    }
}
