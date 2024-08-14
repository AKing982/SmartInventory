package org.example.smartinventory.workbench.converter;

public interface ModelToEntityConverter<M, E>
{
    E convert(M source);
}
