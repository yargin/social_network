package com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder;

public class SqlQueryBuilder {
    private StringBuilder query = new StringBuilder();
    public static final String SELECT_ALL = "SELECT * FROM ";

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
        query.append(SELECT_ALL).append(table);
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
     * appends 'SELECT <b>column</b> <b>alias</b> FROM <b>table</b>' to query
     *
     * @param table  specified table
     * @param column specified column
     * @param alias  alias for column
     * @return {@link SqlQueryBuilder} having added 'SELECT <b>column</b> <b>alias</b> FROM <b>table</b>' to query
     */
    public SqlQueryBuilder selectColumn(String table, String column, String alias) {
        query.append("SELECT ").append(column).append(' ').append(alias).append(' ').append(" FROM ").append(table);
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
        query.append(SELECT_ALL).append(leftTable).append(" JOIN ").append(rightTable).append(" ON ").
                append(leftColumn).append(" = ").append(rightColumn);
        return this;
    }

    /**
     * appends 'JOIN <b>joinedTable</b> ON <b>leftColumn</b> = <b>joinedColumn</b>' to query
     *
     * @param joinedTable  specified joined table
     * @param leftColumn   specified left column
     * @param joinedColumn specified joined column
     * @return {@link SqlQueryBuilder} having added 'JOIN <b>joinedTable</b> ON <b>leftColumn</b> = <b>joinedColumn</b>'
     * to query
     */
    public SqlQueryBuilder join(String joinedTable, String leftColumn, String joinedColumn) {
        query.append(" JOIN ").append(joinedTable).append(" ON ").
                append(leftColumn).append(" = ").append(joinedColumn);
        return this;
    }

    /**
     * appends 'SELECT * FROM <b>leftTable</b> JOIN (<b>subSelect</b>) s ON <b>leftColumn</b> = <b>alias</b>'
     *
     * @param leftTable  specified left table
     * @param subSelect  specified sub select
     * @param leftColumn specified left column
     * @param alias      sub select column used for join. ALSO NEED TO BE specified in sub select query
     * @return {@link SqlQueryBuilder} having added 'SELECT * FROM <b>leftTable</b> JOIN (<b>subSelect</b>) s ON
     * <b>leftColumn</b> = <b>alias</b>' to query
     */
    public SqlQueryBuilder joinSubSelect(String leftTable, String subSelect, String leftColumn, String alias) {
        query.append(SELECT_ALL).append(leftTable).append(" JOIN (").append(subSelect).append(" ) ").
                append("s ON ").append(leftColumn).append(" = ").append("s.").append(alias);
        return this;
    }

    public SqlQueryBuilder union() {
        query.append(" UNION ");
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
