package it.academy.builder;

import it.academy.*;
import it.academy.builder.utilitys.SelectValue;
import it.academy.builder.utilitys.TypeQuery;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class BuilderQueryTest extends BaseTest {

    @SneakyThrows
    @Test
    public void test1(){
        Session session = factory.openSession();
        BuilderQuery builderQuery = FactoryBuilder.createBuilder(TypeQuery.SQL, session);
        builderQuery.from(Summaries.class);
        builderQuery.select(new SelectValue<>(Summaries.class, "id"));
        builderQuery.select(new SelectValue<>(CompetencesType.class, "name"));
        builderQuery.where(builderQuery.and(builderQuery.equally(Applicants.class, "lastName", "Морская"),
                builderQuery.include(Applicants.class, "firstName", "Марина"),
                builderQuery.include(Applicants.class, "patronymic", "Васильевна")));
        builderQuery.generateQuery();
        String textQuery = builderQuery.getQuery();
        NativeQuery sqlQuery = session.createSQLQuery(textQuery);
        for (Map.Entry<String, Object> stringObjectEntry : builderQuery.getQueryParams().entrySet()) {
            sqlQuery.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }


        assertEquals(1, sqlQuery.list().size()
        );
        session.close();
    }

    @SneakyThrows
    @Test
    public void test2(){
        Session session = factory.openSession();
        BuilderQuery builderQuery = FactoryBuilder.createBuilder(TypeQuery.SQL, session);
        builderQuery.from(Summaries.class);
        builderQuery.select(new SelectValue<>(Summaries.class, "id"));
        builderQuery.select(new SelectValue<>(CompetencesType.class, "name"));
        builderQuery.where(builderQuery.or(builderQuery.include(Applicants.class, "lastName", "*ов"),
                builderQuery.equally(GenderType.class, "name", "Женский")));
        builderQuery.generateQuery();
        String textQuery = builderQuery.getQuery();
        NativeQuery sqlQuery = session.createSQLQuery(textQuery);
        for (Map.Entry<String, Object> stringObjectEntry : builderQuery.getQueryParams().entrySet()) {
            sqlQuery.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }


        assertEquals(2, sqlQuery.list().size());
        session.close();
    }
}