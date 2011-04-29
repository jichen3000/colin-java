package com.colin.test.jsqlparser;


import java.io.BufferedReader;
import java.io.StringReader;

import junit.framework.TestCase;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class CCJSqlParserManagerTest extends TestCase {

	public CCJSqlParserManagerTest(String arg0) {
		super(arg0);
	}

//	public static void main(String[] args) {
//		junit.swingui.TestRunner.run(CCJSqlParserManagerTest.class);
//	}

	/**
	 * @throws Exception
	 */
	public void testParse() throws Exception {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		String sql = "select  *  from  (  select  persistence_dynamic_ot.pdl_id  ,"+ 
			"acs_objects.default_domain_class  as  attribute0  , "+
			"acs_objects.object_type  as  attribute1  ,  acs_objects.display_name "+
			"as  attribute2  ,  persistence_dynamic_ot.dynamic_object_type  as "+
			"attribute3  ,  persistence_dynamic_ot.pdl_file  as  attribute4  from "+ 
			"persistence_dynamic_ot  ,  acs_objects  where "+
			"persistence_dynamic_ot.pdl_id  =  acs_objects.object_id  )";
		Statement statement = parserManager.parse(new StringReader(sql));
		System.out.println(sql);
		System.out.println(statement);
		if (statement instanceof Select) {
			Select selectStatement = (Select) statement;
			System.out.println(((PlainSelect)selectStatement.getSelectBody()).getFromItem());
			System.out.println(((PlainSelect)selectStatement.getSelectBody()).getJoins());
			System.out.println(((PlainSelect)selectStatement.getSelectBody()).getSelectItems());
		}
//		BufferedReader in = new BufferedReader(new FileReader("testfiles" + File.separator + "simple_parsing.txt"));
//		String statement = "";
//		while (true) {
//			try {
//				statement = CCJSqlParserManagerTest.getStatement(in);
//				if (statement == null)
//					break;
//					
//				Statement parsedStm = parserManager.parse(new StringReader(statement));
//				System.out.println(statement);
//			} catch (JSQLParserException e) {
//				throw new Exception("impossible to parse statement: " + statement, e);
//			}
//		}
	}

	public static String getStatement(BufferedReader in) throws Exception {
		StringBuffer buf = new StringBuffer();
		String line = null;
		while ((line = CCJSqlParserManagerTest.getLine(in)) != null) {

			if (line.length() == 0)
				break;

			buf.append(line);
			buf.append("\n");

		}

		if (buf.length() > 0) {
			return buf.toString();
		} else {
			return null;
		}

	}

	public static String getLine(BufferedReader in) throws Exception {
		String line = null;
		while (true) {
			line = in.readLine();
			if (line != null) {
				line.trim();
//				if ((line.length() != 0) && ((line.length() < 2) ||  (line.length() >= 2) && !(line.charAt(0) == '/' && line.charAt(1) == '/')))
				if (((line.length() < 2) || (line.length() >= 2) && !(line.charAt(0) == '/' && line.charAt(1) == '/')))
					break;
			} else {
				break;
			}

		}

		return line;
	}

}

