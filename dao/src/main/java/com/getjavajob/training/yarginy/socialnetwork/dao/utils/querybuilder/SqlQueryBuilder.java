package com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder;

public class SqlQueryBuilder {
    private StringBuilder query = new StringBuilder();
    public static final String SELECT_ALL = "SELECT * FROM ";
    public static final String SELECT = "SELECT ";
    public static final String EQUAL = " = ";

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
    public SqlQueryBuilder selectAll(String table) {
        query.append(SELECT_ALL).append(table);
        return this;
    }

    /**
     * appends 'SELECT table.field1, table.field2 ... FROM <b>table</b>' to query
     *
     * @param table specified table
     * @return {@link SqlQueryBuilder} having added 'SELECT table.field1, table.field2 ... FROM <b>table</b>' to query
     */
    public SqlQueryBuilder selectView(String viewFields, String table) {
        query.append(SELECT).append(viewFields).append(" FROM ").append(table);
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
        query.append(SELECT).append(column).append(" FROM ").append(table);
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
        query.append(SELECT).append(column).append(' ').append(alias).append(' ').append(" FROM ").append(table);
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
                append(leftColumn).append(EQUAL).append(rightColumn);
        return this;
    }

    /**
     * appends 'SELECT rightTable.field1, rightTable.field2 ..., leftTable.* FROM <b>leftTable</b> JOIN <b>rightTable</b>
     * ON <b>leftColumn</b> = <b>rightColumn</b>' to query
     *
     * @param leftTable   specified left table
     * @param rightTable  specified right table
     * @param leftColumn  specified left column
     * @param rightColumn specified right column
     * @return {@link SqlQueryBuilder} having added 'SELECT * FROM <b>leftTable</b> JOIN <b>rightTable</b> ON
     * <b>leftColumn</b> = <b>rightColumn</b>' to query
     */
    public SqlQueryBuilder selectLeftFullRightView(String leftTable, String rightTable, String rightFields,
                                                   String leftColumn, String rightColumn) {
        query.append(SELECT).append(rightFields).append(", ").append(leftTable).append(".* FROM ").append(leftTable).
                append(" JOIN ").append(rightTable).append(" ON ").append(leftColumn).append(EQUAL).append(rightColumn);
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
                append(leftColumn).append(EQUAL).append(joinedColumn);
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
                append("s ON ").append(leftColumn).append(EQUAL).append("s.").append(alias);
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
     * appends ' WHERE <b>column</b> IN (' to query
     *
     * @param column specified column
     * @return {@link SqlQueryBuilder} having added ' WHERE <b>column</b> IN (' to query
     */
    public SqlQueryBuilder whereIn(String[] column, String[] params) {
        int columnNumber = column.length;
        if (columnNumber != params.length) {
            throw new IllegalArgumentException("wrong arrays sizes");
        }
        query.append(" WHERE ").append(column[0]).append(" IN (").append(params[0]);
        if (columnNumber > 1) {
            for (int i = 1; i < params.length; i++) {
                query.append(") OR (").append(column[i]).append(" IN (").append(params[i]);
            }
        }
        query.append(')');
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
     * appends 'INSERT INTO <b>table</b> VALUES(?, ?, ... ,?)' to query
     *
     * @param table         table to insert
     * @param columnsNumber number of columns
     * @return {@link SqlQueryBuilder} having added 'INSERT INTO <b>table</b> VALUES(?, ?, ... ,?)'
     */
    public SqlQueryBuilder insert(String table, int columnsNumber) {
        query.append("INSERT INTO ").append(table).append(" VALUES(");
        for (int i = 1; i <= columnsNumber; i++) {
            query.append("?, ");
        }
        query.append(')');
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

    /**
     * appends 'ORDER BY <b>column</b>' to query
     *
     * @return {@link String} resulting query
     */
    public SqlQueryBuilder orderBy(String column) {
        query.append(" ORDER BY ").append(column);
        return this;
    }

    /**
     * appends 'ORDER BY <b>column</b> DESC' to query
     *
     * @return {@link String} resulting query
     */
    public SqlQueryBuilder orderByDesc(String column) {
        query.append(" ORDER BY ").append(column).append(" DESC ");
        return this;
    }
}
