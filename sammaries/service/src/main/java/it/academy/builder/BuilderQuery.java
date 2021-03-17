package it.academy.builder;


import it.academy.builder.utilitys.SelectValue;
import it.academy.builder.utilitys.tree.TreeEntities;

import java.util.HashMap;
import java.util.Map;

public interface BuilderQuery {
    TreeEntities treeEntities = new TreeEntities();
    Map<String, Object> queryParams = new HashMap<>();

    <T> void from(Class<T> fClass) throws ClassNotFoundException;
    <T> String equally(Class<T> pClass, String pName, String value) throws ClassNotFoundException;

    void where(String value);

    <T> void concat(SelectValue<T>... selectValues) throws ClassNotFoundException;

    <T> void select(SelectValue<T> selectValues) throws ClassNotFoundException;

    void generateQuery();

    String getQuery();
    String add(String ... v);
    String or(String ... v);

    Map<String, Object> getQueryParams();
}
