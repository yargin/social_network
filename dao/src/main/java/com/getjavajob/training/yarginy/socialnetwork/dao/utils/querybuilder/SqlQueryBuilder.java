package com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder;

public class SqlQueryBuilder {
    private StringBuilder query = new StringBuilder();

    /**
     * creates new {@link SqlQueryBuilder} for further query creation
     *
     * @return {@link SqlQueryBuilder} with empty query
     */
    public static SqlQueryBuilder buildQuery() {
        return new SqlQueryBuilder();
    }

    /**
     * appends 'SELECT * FROM <b>table</b>' to query
     *
     * @param table specified table
     * @return {@link SqlQueryBuilder} having added 'SELECT * FROM <b>table</b>' to query
     */
    public SqlQueryBuilder select(String table) {
        query.append("SELECT * FROM ").append(table);
        return this;
    }

    /**
     * appends 'SELECT <b>column</b> FROM <b>table</b>' to query
     *
     * @param table  specified table
     * @param column specified column
     * @return {@link SqlQueryBuilder} having added 'SELECT <b>column</b> FROM <b>table</b>' to query
     */
    public SqlQueryBuilder selectColumn(String table, String column) {
        query.append("SELECT ").append(column).append(" FROM ").append(table);
        return this;
    }

    /**
     * appends 'SELECT * FROM <b>leftTable</b> JOIN <b>rightTable</b> ON <b>leftColumn</b> = <b>rightColumn</b>' to query
     *
     * @param leftTable   specified left table
     * @param rightTable  specified right table
     * @param leftColumn  specified left column
     * @param rightColumn specified right column
     * @return {@link SqlQueryBuilder} having added 'SELECT * FROM <b>leftTable</b> JOIN <b>rightTable</b> ON
     * <b>leftColumn</b> = <b>rightColumn</b>' to query
     */
    public SqlQueryBuilder selectJoin(String leftTable, String rightTable, String leftColumn, String rightColumn) {
        query.append("SELECT * FROM ").append(leftTable).append(" JOIN ").append(rightTable).append(" ON ").
                append(leftColumn).append(" = ").append(rightColumn);
        return this;
    }

    /**
     * appends ' WHERE <b>column</b> = ? ' to query
     *
     * @param column specified column
     * @return {@link SqlQueryBuilder} having added ' WHERE <b>column</b> = ? ' to query
     */
    public SqlQueryBuilder where(String column) {
        query.append(" WHERE ").append(column).append(" = ?");
        return this;
    }

    /**
     * appends ' AND <b>column</b> = ? ' to query
     *
     * @param column specified column
     * @return {@link SqlQueryBuilder} having added ' AND <b>column</b> = ? ' to query
     */
    public SqlQueryBuilder and(String column) {
        query.append(" AND ").append(column).append(" = ?");
        return this;
    }

    /**
     * gives resulting query & deletes internal query that was built
     *
     * @return {@link String} resulting query
     */
    public String build() {
        String result = query.toString();
        query = null;
        return result;
    }
}
