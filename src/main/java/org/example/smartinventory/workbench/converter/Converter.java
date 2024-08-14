package org.example.smartinventory.workbench.converter;

public interface Converter<D, M>
{
    M convert(D d);
}
