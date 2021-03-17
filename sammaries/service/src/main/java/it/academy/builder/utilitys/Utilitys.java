package it.academy.builder.utilitys;

import com.sun.xml.bind.v2.model.core.ID;
import it.academy.builder.utilitys.tree.ParentEntityLink;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilitys {

    public static <T> String getTableName(Class<T>  pClass){
        String nameTable = (pClass.getAnnotation(Table.class)).name();
        if(nameTable.isEmpty()){
            nameTable = pClass.getSimpleName();
        }
        else
        {
            nameTable = getNameElement(nameTable);
        }

        return nameTable;
    }

    public static <T>  Map<String, String> getTableColumns(Class<T>  pClass){
        Map<String, String > mapColumns = new HashMap<>();
        for (Field field :pClass.getDeclaredFields()){
            for (Column column : field.getDeclaredAnnotationsByType(Column.class)){
                String nameColumn = column.name();
                if(nameColumn.isEmpty()){
                    nameColumn = field.getName();
                }
                else
                {
                    nameColumn = getNameElement(nameColumn);
                }
               mapColumns.put(field.getName(), nameColumn);
            }
        }

        return mapColumns;
    }

    public static <T>  Map<String, ParentEntityLink> getTableJoin(Class<T>  pClass){
        Map<String, ParentEntityLink > mapJoinTable= new HashMap<>();
        String idName = null;
        for (Field field :pClass.getDeclaredFields()){
            for (Id id : field.getDeclaredAnnotationsByType(Id.class)) {
                    idName = getNameElement(field.getDeclaredAnnotationsByType(Column.class)[0].name());
                }
            ParentEntityLink parentEntityLink = new ParentEntityLink();
            for (JoinColumn column : field.getDeclaredAnnotationsByType(JoinColumn.class)){
                String joinClass = (field.getType()).getName();
                String nameColumn = column.name();
                if(nameColumn.isEmpty()){
                    nameColumn = field.getName();
                }
                else
                {
                    nameColumn = getNameElement(nameColumn);
                }
                parentEntityLink.setJoinCollum(nameColumn);
                mapJoinTable.put(joinClass, parentEntityLink);
            }
            for (JoinTable annotation : field.getDeclaredAnnotationsByType(JoinTable.class)){
                String nameJoinEntity = field.getGenericType().toString();
                Pattern pattern = Pattern.compile("<{1}\\S+>{1}");
                Matcher matcher = pattern.matcher(nameJoinEntity);
                if (matcher.find()) {
                    nameJoinEntity = nameJoinEntity.substring(matcher.start()+1, nameJoinEntity.length()-1);
                }
                parentEntityLink.setNameJoinTable(getNameElement(annotation.name()));

                for(JoinColumn joinColumn : annotation.joinColumns()){
                    parentEntityLink.setJoinParentCollum(getNameElement(joinColumn.name()));
                }

                for(JoinColumn joinColumn : annotation.inverseJoinColumns()){
                    parentEntityLink.setJoinCollum(getNameElement(joinColumn.name()));
                }
                mapJoinTable.put(nameJoinEntity, parentEntityLink);
            }

            for (OneToMany oneToMany : field.getDeclaredAnnotationsByType(OneToMany.class)) {
                String nameJoinEntity = field.getGenericType().toString();
                Pattern pattern = Pattern.compile("<{1}\\S+>{1}");
                Matcher matcher = pattern.matcher(nameJoinEntity);
                if (matcher.find()) {
                    nameJoinEntity = nameJoinEntity.substring(matcher.start()+1, nameJoinEntity.length()-1);
                }
                parentEntityLink.setJoinCollum(idName);
                mapJoinTable.put(nameJoinEntity, parentEntityLink);
            }
        }

        return mapJoinTable;
    }

    private static String getNameElement(String pValue){
        Pattern pattern = Pattern.compile("`{1}\\w+`{1}");
        Matcher matcher = pattern.matcher(pValue);
        if (matcher.find()) {
            return pValue.substring(1, pValue.length()-1);
        }
        else return pValue;
    }

}
