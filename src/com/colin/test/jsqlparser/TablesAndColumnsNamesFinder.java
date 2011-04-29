package com.colin.test.jsqlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;

public class TablesAndColumnsNamesFinder implements SelectVisitor,
    FromItemVisitor, ExpressionVisitor, ItemsListVisitor, SelectItemVisitor {
	private List<String> columnNames;
	private Map<Integer,List<Table>> tableMap;
	private int levelIndex=0;
	private int subIndex=0;

	// public List getTableList(Select select) {
	// tables = new ArrayList();
	// select.getSelectBody().accept(this);
	// return tables;
	// }

	public List<String> find(Select select) {
		columnNames = new ArrayList<String>();
		tableMap = new HashMap<Integer,List<Table>>();
		select.getSelectBody().accept(this);
		// tables.addAll(columns);
		return columnNames;
	}
	
	public Map<Integer,List<Table>> getTableMap(){
		return tableMap;
	}

	@SuppressWarnings("unchecked")
  public void visit(PlainSelect plainSelect) {
		intoNextLevel();
		plainSelect.getFromItem().accept(this);
		if (plainSelect.getJoins() != null) {
			for (Iterator joinsIt = plainSelect.getJoins().iterator(); joinsIt
			    .hasNext();) {
				Join join = (Join)joinsIt.next();
				join.getRightItem().accept(this);
			}
		}
		for (Iterator selectItemsIt = plainSelect.getSelectItems().iterator(); selectItemsIt.hasNext();) {
	    SelectItem type = (SelectItem) selectItemsIt.next();
	    type.accept(this);
    }
		if (plainSelect.getWhere() != null)
			plainSelect.getWhere().accept(this);
		archiveCurrentTables();
		backLastLevel();
	}
	private static final int LEVEL_STEP=1000;
	private void intoNextLevel(){
		levelIndex += LEVEL_STEP;
	}
	private void backLastLevel(){
		levelIndex -= LEVEL_STEP;
	}
	private int getArchiveSeq(){
		return levelIndex+subIndex;
	}
	private void archiveCurrentTables(){
		subIndex += 1;
		List<Table> curLevelTableList = getCurLevelTableList();
		tableMap.put(getArchiveSeq(), curLevelTableList);
		tableMap.put(levelIndex, null);
	}

	@SuppressWarnings("unchecked")
  public void visit(Union union) {
		for (Iterator iter = union.getPlainSelects().iterator(); iter.hasNext();) {
			PlainSelect plainSelect = (PlainSelect) iter.next();
			visit(plainSelect);
		}
	}

	public void visit(Table table) {
		List<Table> tableList = getCurLevelTableList();
		tableList.add(table);
		tableMap.put(levelIndex, tableList);
	}
	
	public void visit(SubSelect subSelect) {
		subSelect.getSelectBody().accept(this);
	}
  private List<Table> getCurLevelTableList(){
		List<Table> tableList = (ArrayList<Table>)tableMap.get(levelIndex);
		if (tableList==null){
			tableList = new ArrayList<Table>();
		}
  	return tableList;
  }
  
  public void visit(AllColumns paramAllColumns) {
  	for (Iterator<Table> iterator = getCurLevelTableList().iterator(); iterator.hasNext();) {
  		Table table = iterator.next();
	    columnNames.add(table.getWholeTableName()+"."+paramAllColumns);
    }
  }

  public void visit(AllTableColumns paramAllTableColumns) {
  	System.out.println("paramAllTableColumns");
		System.out.println(paramAllTableColumns.getTable());
//		paramAllTableColumns.accept(this);
	  
  }
  private String getTableNameFromAlias(String alias){
  	List<Table> tables = getCurLevelTableList();
  	for (Iterator<Table> iterator = tables.iterator(); iterator.hasNext();) {
	    Table table = iterator.next();
	    if (checkAliasEquals(table.getAlias(),alias))
	    	return table.getWholeTableName();
    }
  	return "";
  }

	private boolean checkAliasExist(String alias) {
		return alias!=null && !alias.isEmpty();
	}
  private boolean checkAliasEquals(String alias, String otherAlias){
  	return (checkAliasExist(alias) && checkAliasExist(otherAlias) && otherAlias.equals(alias));
  }
  private String getColumnNameWithTable(String tableName,Column column){
  	return tableName+"."+column.getColumnName().toUpperCase();
  }
  private String getColumnNameWithTable(Table table,Column column){
  	return table.getWholeTableName().toUpperCase()+"."+column.getColumnName().toUpperCase();
  }
  private boolean checkTableNameExist(String tableName){
  	return tableName!=null && !tableName.isEmpty();
  }
  private List<String> getColumnNameListWithAllTables(Column column){
  	List<String> newColumnNameList = new ArrayList<String>();
  	for (Iterator<Table> iterator = getCurLevelTableList().iterator(); iterator.hasNext();) {
	    Table table = iterator.next();
	    newColumnNameList.add(getColumnNameWithTable(table,column));
    }
  	return newColumnNameList;
  }
  public void visit(SelectExpressionItem selectExpressionItem) {
  	System.out.println("paramSelectExpressionItem");  	
  	System.out.println(selectExpressionItem.getExpression());  	
  	if (selectExpressionItem.getExpression() instanceof Column){
  		Column column = (Column)selectExpressionItem.getExpression();
  		columnNames.addAll(getColumnNameListWithTable(column));
  	}
  }

	private List<String> getColumnNameListWithTable(Column column) {
	  if (checkAliasExist(column.getTable().getName())){
	  	String tableName = getTableNameFromAlias(column.getTable().getName());
	  	List<String> newColumnList = new ArrayList<String>(); 
	  	if(checkTableNameExist(tableName)){
	  		newColumnList.add(getColumnNameWithTable(tableName,column));
	  	}else{
	  		//notNeedAdd();
	  	}
	  	return newColumnList;
	  }else{
	  	return getColumnNameListWithAllTables(column);
	  }
  }
	
	public void visit(Addition addition) {
		visitBinaryExpression(addition);
	}

	public void visit(AndExpression andExpression) {
		visitBinaryExpression(andExpression);
	}

	public void visit(Between between) {
		between.getLeftExpression().accept(this);
		between.getBetweenExpressionStart().accept(this);
		between.getBetweenExpressionEnd().accept(this);
	}

	public void visit(Column column) {
		columnNames.addAll(getColumnNameListWithTable(column));
	}

	public void visit(Division division) {
		visitBinaryExpression(division);
	}

	public void visit(DoubleValue doubleValue) {
	}

	public void visit(EqualsTo equalsTo) {
		visitBinaryExpression(equalsTo);
	}

	public void visit(Function function) {
	}

	public void visit(GreaterThan greaterThan) {
		visitBinaryExpression(greaterThan);
	}

	public void visit(GreaterThanEquals greaterThanEquals) {
		visitBinaryExpression(greaterThanEquals);
	}

	public void visit(InExpression inExpression) {
		inExpression.getLeftExpression().accept(this);
		inExpression.getItemsList().accept(this);
	}

	public void visit(InverseExpression inverseExpression) {
		inverseExpression.getExpression().accept(this);
	}

	public void visit(IsNullExpression isNullExpression) {
	}

	public void visit(JdbcParameter jdbcParameter) {
	}

	public void visit(LikeExpression likeExpression) {
		visitBinaryExpression(likeExpression);
	}

	public void visit(ExistsExpression existsExpression) {
		existsExpression.getRightExpression().accept(this);
	}

	public void visit(LongValue longValue) {
	}

	public void visit(MinorThan minorThan) {
		visitBinaryExpression(minorThan);
	}

	public void visit(MinorThanEquals minorThanEquals) {
		visitBinaryExpression(minorThanEquals);
	}

	public void visit(Multiplication multiplication) {
		visitBinaryExpression(multiplication);
	}

	public void visit(NotEqualsTo notEqualsTo) {
		visitBinaryExpression(notEqualsTo);
	}

	public void visit(NullValue nullValue) {
	}

	public void visit(OrExpression orExpression) {
		visitBinaryExpression(orExpression);
	}

	public void visit(Parenthesis parenthesis) {
		parenthesis.getExpression().accept(this);
	}

	public void visit(StringValue stringValue) {
	}

	public void visit(Subtraction subtraction) {
		visitBinaryExpression(subtraction);
	}

	public void visitBinaryExpression(BinaryExpression binaryExpression) {
		binaryExpression.getLeftExpression().accept(this);
		binaryExpression.getRightExpression().accept(this);
	}

	@SuppressWarnings("unchecked")
  public void visit(ExpressionList expressionList) {
		for (Iterator iter = expressionList.getExpressions().iterator(); iter
		    .hasNext();) {
			Expression expression = (Expression) iter.next();
			expression.accept(this);
		}

	}

	public void visit(DateValue dateValue) {
	}

	public void visit(TimestampValue timestampValue) {
	}

	public void visit(TimeValue timeValue) {
	}

	public void visit(CaseExpression caseExpression) {

	}

	public void visit(WhenClause whenClause) {

	}

	public void visit(AllComparisonExpression allComparisonExpression) {
		allComparisonExpression.GetSubSelect().getSelectBody().accept(this);
	}

	public void visit(AnyComparisonExpression anyComparisonExpression) {
		anyComparisonExpression.GetSubSelect().getSelectBody().accept(this);
	}

	public void visit(SubJoin subjoin) {
		subjoin.getLeft().accept(this);
		subjoin.getJoin().getRightItem().accept(this);
	}

	public void visit(Concat concat) {
		visitBinaryExpression(concat);
	}

	public void visit(Matches matches) {
		visitBinaryExpression(matches);
	}

	public void visit(BitwiseAnd bitwiseAnd) {
		visitBinaryExpression(bitwiseAnd);
	}

	public void visit(BitwiseOr bitwiseOr) {
		visitBinaryExpression(bitwiseOr);
	}

	public void visit(BitwiseXor bitwiseXor) {
		visitBinaryExpression(bitwiseXor);
	}

//	public static void main(String[] args) throws JSQLParserException {
//		CCJSqlParserManager pm = new CCJSqlParserManager();
//		String sql = "SELECT * FROM MY_TABLE1, MY_TABLE2";
////		String sql = "SELECT * FROM MY_TABLE1, MY_TABLE2, (SELECT * FROM MY_TABLE3) LEFT OUTER JOIN MY_TABLE4 "
////		    + " WHERE ID = (SELECT MAX(t5.ID) FROM MY_TABLE5 t5) AND ID2 IN (SELECT * FROM MY_TABLE6)";
//		Statement statement = pm.parse(new StringReader(sql));
//		/*
//		 * now you should use a class that implements StatementVisitor to decide
//		 * what to do based on the kind of the statement, that is SELECT or INSERT
//		 * etc. but here we are only interested in SELECTS
//		 */
//		if (statement instanceof Select) {
//			Select selectStatement = (Select) statement;
//			TablesAndColumnsNamesFinder tablesNamesFinder = new TablesAndColumnsNamesFinder();
//			List tableList = tablesNamesFinder.find(selectStatement);
//			for (Iterator iter = tableList.iterator(); iter.hasNext();) {
//				String tableName = (String) iter.next();
//				System.out.println(tableName);
//			}
//		}
//		System.out.println("ok");
//	}

}
