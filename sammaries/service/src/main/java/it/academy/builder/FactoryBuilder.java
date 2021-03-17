package it.academy.builder;

import it.academy.builder.utilitys.TypeQuery;
import org.hibernate.Session;

public class FactoryBuilder {
    public static BuilderQuery createBuilder(TypeQuery typeQuery, Session session) {
        BuilderQuery builderQuery = null;
        switch(typeQuery){
            case SQL:
                builderQuery = new BuilderQuerySql(session);
            default:
                builderQuery = new BuilderQuerySql(session);
        }
        return builderQuery;
    }
}
