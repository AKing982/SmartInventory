package org.example.smartinventory.workbench.converter;

public interface Converter<M, E>
{
    E convert(M source);
}
