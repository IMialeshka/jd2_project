package it.academy.builder;

import it.academy.builder.utilitys.tree.ParentEntityLink;
import it.academy.builder.utilitys.SelectValue;
import it.academy.builder.utilitys.TypeJoin;
import it.academy.builder.utilitys.Utilitys;
import it.academy.builder.utilitys.tree.TreeNode;

import java.util.Map;


public class BuilderQuerySql implements BuilderQuery{
    private int counterTable;
    private String shemaName = "summaries_shema";
    private String fromPart = "";
    private String groupByPart = "";
    String wherePart = "";
    String joinTablePart = "";
    String selectPart = "";

    @Override
    public <T>  void from(Class<T>  fClass) throws ClassNotFoundException {
        crateEntityThree(fClass, null, null);
    }

    @Override
    public <T> String equally(Class<T> pClass, String pName, String value) throws ClassNotFoundException {
        TreeNode treeNode = treeEntities.getNode(pClass.getName());

        String paramQueryName = "p"+pName + queryParams.size()+1;
        String result = treeNode.getNameAlias() + "." + pName + "=:"+paramQueryName;
        queryParams.put(paramQueryName, value);
        treeEntities.includeInQuery(pClass.getName());
        while(treeNode.getNameParent() != null){
            Class<?> aClass = Class.forName(treeNode.getNameParent());
            treeEntities.includeInQuery(aClass.getName());
            treeNode = treeEntities.getNode(aClass.getName());
        }
        return result;
    }

    @Override
    public String add(String ... v){
        StringBuilder result = new StringBuilder();
        String separator = " and ";
        if(v.length == 1){
            result.append(v[0]);
        }
        else
        {
            result.append("(");
            for (int i = 0; i < v.length; i++) {
                if(i == 0)  result.append(v[i]);
                else result.append(separator).append(v[i]);
            }
            result.append(")");
        }
        return result.toString();
    }

    @Override
    public String or(String ... v){
        StringBuilder result = new StringBuilder();
        String separator = " or ";
        if(v.length == 1){
            result.append(v[0]);
        }
        else
        {
            result.append("(");
            for (int i = 0; i < v.length; i++) {
                if(i == 0)  result.append(v[i]);
                else result.append(separator).append(v[i]);
            }
            result.append(")");
        }
        return result.toString();
    }

    @Override
    public void where(String value){
        this.wherePart = value;
    }

    @Override
    public <T> void concat(SelectValue<T>... selectValues) throws ClassNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        String separator = ",";
        if(this.selectPart.isEmpty())  separator = "";

        stringBuilder.append(separator).append(" ").append("string_agg(distinct concat(");
        String separatorConcat = "";
        StringBuilder nameNewColumn = new StringBuilder();
        for (SelectValue<T> selectValue : selectValues) {
            Class<T> pClass = selectValue.getSelectEntity();
            String name = selectValue.getSelectColumn();
            TreeNode treeNode = treeEntities.getNode(pClass.getName());

            stringBuilder.append(separatorConcat).append(treeNode.getNameAlias()).append(".\"").append(name).append("\"");
            separatorConcat = " ";
            nameNewColumn.append("_").append(treeNode.getNameAlias()).append("_").append(name);


            treeEntities.includeInQuery(pClass.getName());
            while(treeNode.getNameParent() != null){
                Class<?> aClass = Class.forName(treeNode.getNameParent());
                treeEntities.includeInQuery(aClass.getName());
                treeNode = treeEntities.getNode(aClass.getName());
            }
        }

        stringBuilder.append("), ', ')").append(" as ").append(nameNewColumn);

        this.selectPart = this.selectPart + stringBuilder.toString();
    }

    @Override
    public <T> void select(SelectValue<T> selectValues) throws ClassNotFoundException {

            Class<T> pClass = selectValues.getSelectEntity();
            String name = selectValues.getSelectColumn();

            TreeNode treeNode = treeEntities.getNode(pClass.getName());
            String separator = ",";
            if(this.selectPart.isEmpty())
                separator = "";

            StringBuilder stringBuilder = new StringBuilder();

            if(treeNode.getTypeJoin() == TypeJoin.FROM){
                stringBuilder.append(separator).append(" ").append(treeNode.getNameAlias()).append(".\"")
                        .append(name).append("\"").append(" as " ).append(treeNode.getNameAlias()).append("_")
                .append(name);
            }
            else{
                stringBuilder.append(separator).append(" ").append("string_agg(distinct concat(")
                        .append(treeNode.getNameAlias()).append(".\"").append(name).append("\", '')")
                        .append(", ', ')").append(" as " ).append(treeNode.getNameAlias()).append("_")
                        .append(name);
            }
        this.selectPart = this.selectPart + stringBuilder.toString();

            treeEntities.includeInQuery(pClass.getName());
         while(treeNode.getNameParent() != null){
             Class<?> aClass = Class.forName(treeNode.getNameParent());
             treeEntities.includeInQuery(aClass.getName());
             treeNode = treeEntities.getNode(aClass.getName());
            }

    }

    @Override
    public void generateQuery(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, TreeNode> next : treeEntities.getTreeEntities().entrySet()) {
            TreeNode treeNode = next.getValue();
            if(treeNode.getTypeJoin() == TypeJoin.FROM)
            {
                stringBuilder.append(" From ").append(shemaName).append(".\"").append(treeNode.getNameTable())
                        .append("\" as ").append(treeNode.getNameAlias());
                this.fromPart = stringBuilder.toString();
                stringBuilder.setLength(0);

                stringBuilder.append("group by ");
                String separator = "";
                for (String value : treeNode.getListNameColumns().values()) {
                    stringBuilder.append(separator).append(treeNode.getNameAlias()).append(".\"")
                            .append(value).append("\"");
                    separator = ",";
                }
               this.groupByPart = stringBuilder.toString();
            }
            else{
                if(treeNode.isIncludeInQuery()){
                    TreeNode pTreeNode = treeEntities.getNode(treeNode.getNameParent());
                    if(treeNode.getParentEntityLink().getNameJoinTable() == null){
                        stringBuilder.append("\n ").append(treeNode.getTypeJoin()).append(" ").append(shemaName).append(".\"")
                                .append(treeNode.getNameTable()).append("\" as ").append(treeNode.getNameAlias())
                                .append(" ON ").append(treeNode.getNameAlias()).append(".\"")
                                .append(treeNode.getParentEntityLink().getJoinCollum()).append("\" = ")
                                .append(pTreeNode.getNameAlias()).append(".\"")
                                .append(treeNode.getParentEntityLink().getJoinCollum())
                                .append("\"");
                        this.joinTablePart = this.joinTablePart + stringBuilder.toString();
                        stringBuilder.setLength(0);
                    }
                    else{
                        String aliasJoinTable = treeNode.getNameAlias() + "_" + treeNode.getParentEntityLink().getNameJoinTable();
                        stringBuilder.append("\n").append(" ").append(TypeJoin.JOIN).append(" ").append(shemaName).append(".\"")
                            .append(treeNode.getParentEntityLink().getNameJoinTable()).append("\" as")
                                .append(aliasJoinTable).append(" ON ").append(aliasJoinTable).append(". \"")
                                .append(treeNode.getParentEntityLink().getJoinParentCollum()).append("\" = ")
                                .append(pTreeNode.getNameAlias()).append(". \"").append(treeNode.getParentEntityLink().getJoinParentCollum())
                                .append("\"").append(" ").append("\n")
                                .append(treeNode.getTypeJoin()).append(" ").append(shemaName).append(".\"")
                                .append(treeNode.getNameTable()).append("\" as ").append(treeNode.getNameAlias())
                                .append(" ON ").append(treeNode.getNameAlias()).append(".\"")
                                .append(treeNode.getParentEntityLink().getJoinCollum()).append("\" = ")
                                .append(aliasJoinTable).append(".\"")
                                .append(treeNode.getParentEntityLink().getJoinCollum())
                                .append("\"");
                        this.joinTablePart = this.joinTablePart + stringBuilder.toString();
                        stringBuilder.setLength(0);
                        }
                }
            }
        }
    }


    @Override
    public String getQuery(){
        return "select " + selectPart + "\n" + fromPart + "\n" + joinTablePart + "\n" + "where " + wherePart + "\n" + groupByPart;
    }

    @Override
    public Map<String, Object> getQueryParams()
    {
        return this.queryParams;
    }

    private <T> void crateEntityThree(Class<T>  pClass, String nameParent, ParentEntityLink parentEntityLink) throws ClassNotFoundException {
        TreeNode treeNode = new TreeNode();
        Map<String, ParentEntityLink> joinTable;
        if(nameParent == null)
        {
            treeNode.setTypeJoin(TypeJoin.FROM);
            treeNode.setIncludeInQuery(true);
        }
        else{
            treeNode.setNameParent(nameParent);
            treeNode.setParentEntityLink(parentEntityLink);
            treeNode.setTypeJoin(TypeJoin.JOIN);
            treeNode.setIncludeInQuery(false);
        }
        treeNode.setNameAlias("a" + this.counterTable);
        this.counterTable = this.counterTable + 1;
        treeNode.setNameTable(Utilitys.getTableName(pClass));
        treeNode.setListNameColumns(Utilitys.getTableColumns(pClass));
        treeEntities.addElement(pClass.getName(), treeNode);
        joinTable = Utilitys.getTableJoin(pClass);

        if(!joinTable.isEmpty()){
            for (Map.Entry<String, ParentEntityLink> next : joinTable.entrySet()) {
                Class<?> aClass = Class.forName(next.getKey());
                if(!treeEntities.containsEntity(next.getKey())) crateEntityThree(aClass, pClass.getName(), next.getValue());
            }
        }
    }

}
