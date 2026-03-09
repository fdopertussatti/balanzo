package br.com.balanzo.domain.financeiro.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class VisibilityScopeConverter implements AttributeConverter<VisibilityScope, String> {

    @Override
    public String convertToDatabaseColumn(VisibilityScope attr) {
        return attr == null ? null : attr.toDbValue();
    }

    @Override
    public VisibilityScope convertToEntityAttribute(String dbData) {
        return dbData == null ? null : VisibilityScope.fromDbValue(dbData);
    }
}
