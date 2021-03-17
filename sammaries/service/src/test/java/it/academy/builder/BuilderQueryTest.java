package it.academy.builder;

import it.academy.*;
import it.academy.builder.utilitys.SelectValue;
import it.academy.builder.utilitys.TypeQuery;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

import java.util.List;
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
        builderQuery.select(new SelectValue<>(Applicants.class, "firstName"));
        builderQuery.select(new SelectValue<>(Applicants.class, "lastName"));
        builderQuery.select(new SelectValue<>(Applicants.class, "patronymic"));
        builderQuery.select(new SelectValue<>(Applicants.class, "dateBirth"));
        builderQuery.select(new SelectValue<>(GenderType.class, "name"));
        builderQuery.concat(new SelectValue(ContactsType.class, "name"), new SelectValue<>(Contacts.class, "contact"));
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

        List<Object[]> res = sqlQuery.list();
        for (Object[] re : res) {
            System.out.print(re[0] + " ");
            System.out.print(re[1]+ " ");
            System.out.print(re[2]+ " ");
            System.out.print(re[3]+ " ");
            System.out.print(re[4]+ " ");
            System.out.print(re[5]+ " ");
            System.out.print(re[6]+ " ");
            System.out.print(re[7]+ " ");
            System.out.println();
        }
        assertEquals(1, res.size()
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
        builderQuery.select(new SelectValue<>(Applicants.class, "firstName"));
        builderQuery.select(new SelectValue<>(Applicants.class, "lastName"));
        builderQuery.select(new SelectValue<>(Applicants.class, "patronymic"));
        builderQuery.select(new SelectValue<>(Applicants.class, "dateBirth"));
        builderQuery.select(new SelectValue<>(GenderType.class, "name"));
        builderQuery.concat(new SelectValue(ContactsType.class, "name"), new SelectValue<>(Contacts.class, "contact"));
        builderQuery.select(new SelectValue<>(CompetencesType.class, "name"));
        builderQuery.where(builderQuery.or(builderQuery.include(Applicants.class, "lastName", "*ов"),
                builderQuery.equally(GenderType.class, "id", "2")));
        builderQuery.generateQuery();
        String textQuery = builderQuery.getQuery();
        NativeQuery sqlQuery = session.createSQLQuery(textQuery);
        for (Map.Entry<String, Object> stringObjectEntry : builderQuery.getQueryParams().entrySet()) {
            sqlQuery.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }

        List<Object[]> res = sqlQuery.list();
        for (Object[] re : res) {
            System.out.print(re[0] + " ");
            System.out.print(re[1]+ " ");
            System.out.print(re[2]+ " ");
            System.out.print(re[3]+ " ");
            System.out.print(re[4]+ " ");
            System.out.print(re[5]+ " ");
            System.out.print(re[6]+ " ");
            System.out.print(re[7]+ " ");
            System.out.println();
        }


        assertEquals(2, res.size());
        session.close();
    }
}