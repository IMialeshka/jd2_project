package it.academy.builder.utilitys;

public  class SelectValue<T> {
    private final Class<T> selectEntity;
    private final String selectColumn;

    public  SelectValue(Class<T> selectEntity, String selectColumn) {
        this.selectEntity = selectEntity;
        this.selectColumn = selectColumn;
    }

    public Class<T> getSelectEntity() {
        return selectEntity;
    }

    public String getSelectColumn() {
        return selectColumn;
    }
}
