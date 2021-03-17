package it.academy.builder;

import it.academy.builder.utilitys.TypeQuery;

public class FactoryBuilder {
    public static BuilderQuery createBuilder(TypeQuery typeQuery) {
        BuilderQuery builderQuery = null;
        switch(typeQuery){
            case SQL:
                builderQuery = new BuilderQuerySql();
            default:
                builderQuery = new BuilderQuerySql();
        }
        return builderQuery;
    }
}
