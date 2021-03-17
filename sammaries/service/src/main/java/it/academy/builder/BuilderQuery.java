package it.academy.builder;


import it.academy.builder.utilitys.SelectValue;
import java.util.Map;

public interface BuilderQuery {
    <T> void from(Class<T> fClass) throws ClassNotFoundException;
    <T> String equally(Class<T> pClass, String pName, String value) throws ClassNotFoundException;

    void where(String value);

    <T> void concat(SelectValue<T>... selectValues) throws ClassNotFoundException;

    <T> void select(SelectValue<T> selectValues) throws ClassNotFoundException;

    void generateQuery();

    String getQuery();

    <T> String include(Class<T> pClass, String pName, String value) throws ClassNotFoundException;

    String and(String ... v);
    String or(String ... v);

    Map<String, Object> getQueryParams();
}
