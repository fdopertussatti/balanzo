package br.com.balanzo.domain.financeiro.entity;

public enum VisibilityScope {
    private_,   // DB: 'private'
    shared_read,
    shared_edit,
    shared_manage,
    analytical_only;

    public String toDbValue() {
        return this == private_ ? "private" : name();
    }

    public static VisibilityScope fromDbValue(String v) {
        return "private".equals(v) ? private_ : valueOf(v);
    }
}
