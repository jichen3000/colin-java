package com.colin.test.jsqlparser;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

public class TablesAndColumnsNamesFinderTest extends TestCase {
	CCJSqlParserManager pm = new CCJSqlParserManager();
	private List<String> getFindColumns(String sql){
    try {
  		Statement statement = pm.parse(new StringReader(sql));
			Select selectStatement = (Select) statement;
			TablesAndColumnsNamesFinder tablesNamesFinder = new TablesAndColumnsNamesFinder();
			List<String> columnList = tablesNamesFinder.find(selectStatement);
			return columnList;
    } catch (JSQLParserException e) {
	    e.printStackTrace();
	    return null;
    }
	}
	private String getFindTablesStr(String sql){
		try {
			Statement statement = pm.parse(new StringReader(sql));
			Select selectStatement = (Select) statement;
			TablesAndColumnsNamesFinder tablesNamesFinder = new TablesAndColumnsNamesFinder();
			tablesNamesFinder.find(selectStatement);
			Map<Integer,List<Table>> tableMap = tablesNamesFinder.getTableMap();
//			System.out.println(tableMap);
			return tableMap.toString();
		} catch (JSQLParserException e) {
			e.printStackTrace();
			return null;
		}
	}
//	public void testAllColumns() {
//		String sql = "SELECT * FROM MY_TABLE1 t1, MY_TABLE2";
//		List<String> columnList = getFindColumns(sql);
//		assertEquals("[MY_TABLE1.*, MY_TABLE2.*]",columnList.toString());
//	}
//	public void testColumn() {
//		String sql = "SELECT id FROM MY_TABLE1";
//		List<String> columnList = getFindColumns(sql);
//		assertEquals("[MY_TABLE1.ID]",columnList.toString());
//	}
//	public void testColumnWithAllTables() {
//		String sql = "SELECT id FROM MY_TABLE1,my_table2";
//		List<String> columnList = getFindColumns(sql);
//		assertEquals("[MY_TABLE1.ID, MY_TABLE2.ID]",columnList.toString());
//	}
//	public void testColumnWithAlias() {
//		String sql = "SELECT t2.id aa FROM MY_TABLE1 t1 ,MY_TABLE2 t2";
//		List<String> columnList = getFindColumns(sql);
//		assertEquals("[MY_TABLE2.ID]",columnList.toString());
//	}
//	public void testGetTables(){
//		String sql = "SELECT t2.id, t3.mm aa FROM MY_TABLE1 t1 ,(select * from MY_TABLE3) t3,MY_TABLE2 t2";
//		String tables = getFindTablesStr(sql);
//		assertEquals("{1=[MY_TABLE1 AS t1, MY_TABLE2 AS t2], 2=[MY_TABLE3]}",tables);
//	}
//	public void testColumnWithAliasAndSubSelect() {
//		String sql = "SELECT t2.id, t3.mm aa FROM MY_TABLE1 t1, " +
//				"(select * from MY_TABLE3) t3, MY_TABLE2 t2";
//		List<String> columnList = getFindColumns(sql);
//		assertEquals("[MY_TABLE3.*, MY_TABLE2.ID]",columnList.toString());
//	}
	public void testColumnWithAliasAndMultiSubSelect() {
		String sql = "SELECT t2.id, t3.mm aa FROM MY_TABLE1 t1, " +
		"(select * from MY_TABLE3) t3, MY_TABLE2 t2, (select * from MY_TABLE4) t4";
		List<String> columnList = getFindColumns(sql);
		assertEquals("[MY_TABLE3.*, MY_TABLE4.*, MY_TABLE2.ID]",columnList.toString());
	}
	
}
