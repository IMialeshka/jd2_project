package it.academy.builder.utilitys.tree;

public class ParentEntityLink {
    private String joinCollum;
    private String nameJoinTable;
    private String joinParentCollum;

    public String getJoinCollum() {
        return joinCollum;
    }

    public void setJoinCollum(String joinCollum) {
        this.joinCollum = joinCollum;
    }

    public String getNameJoinTable() {
        return nameJoinTable;
    }

    public void setNameJoinTable(String nameJoinTable) {
        this.nameJoinTable = nameJoinTable;
    }

    public String getJoinParentCollum() {
        return joinParentCollum;
    }

    public void setJoinParentCollum(String joinParentCollum) {
        this.joinParentCollum = joinParentCollum;
    }
}
