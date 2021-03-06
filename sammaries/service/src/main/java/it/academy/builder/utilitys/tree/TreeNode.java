package it.academy.builder.utilitys.tree;

import it.academy.builder.utilitys.TypeJoin;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {
        private String nameParent;
        private String nameTable;
        private String nameAlias;
        private Map<String, String> listNameColumns = new HashMap<>();
        private TypeJoin typeJoin;
        private ParentEntityLink parentEntityLink;
        int isIncludeInQuery;

    @Override
    public String toString() {
        return "TreeNode{" +
                "nameParent='" + nameParent + '\'' +
                ", nameTable='" + nameTable + '\'' +
                ", nameAlias='" + nameAlias + '\'' +
                ", listNameColumns=" + listNameColumns +
                ", typeJoin=" + typeJoin +
                ", parentEntityLink=" + parentEntityLink +
                ", isIncludeInQuery=" + isIncludeInQuery +
                '}';
    }

    public String getNameParent() {
            return nameParent;
        }

        public void setNameParent(String nameParent) {
            this.nameParent = nameParent;
        }

        public String getNameTable() {
            return nameTable;
        }

        public void setNameTable(String nameTable) {
            this.nameTable = nameTable;
        }


        public String getNameAlias() {
            return nameAlias;
        }

        public void setNameAlias(String nameAlias) {
            this.nameAlias = nameAlias;
        }

        public Map<String, String> getListNameColumns() {
            return listNameColumns;
        }

        public void setListNameColumns(Map<String, String> listNameColumns) {
            this.listNameColumns = listNameColumns;
        }

        public TypeJoin getTypeJoin() {
            return typeJoin;
        }

        public void setTypeJoin(TypeJoin typeJoin) {
            this.typeJoin = typeJoin;
        }

        public boolean isIncludeInQuery() {
            return isIncludeInQuery == 1;
        }

        public boolean wasIncludeInQuery() {
        return isIncludeInQuery == 0;
    }

        public void setIncludeInQuery(int includeInQuery) {
            isIncludeInQuery = includeInQuery;
        }

    public ParentEntityLink getParentEntityLink() {
        return parentEntityLink;
    }

    public void setParentEntityLink(ParentEntityLink parentEntityLink) {
        this.parentEntityLink = parentEntityLink;
    }
}
