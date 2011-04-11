package com.colin.test.accessive;

import junit.framework.TestCase;

import com.j2speed.accessor.FieldAccessor;
import com.j2speed.accessor.MethodAccessor;

public class TestColinFunction extends TestCase {
	public void testField(){
		ColinFunction cf = new ColinFunction();
		FieldAccessor<ColinFunction, String> name = new FieldAccessor<ColinFunction,String>("name", ColinFunction.class);
		FieldAccessor<ColinFunction, Long> id = new FieldAccessor<ColinFunction,Long>("id", ColinFunction.class);
		
		assertEquals("mm",name.get(cf));
		assertEquals(0,id.get(cf).longValue());
	}
	public void testMethod(){
		ColinFunction cf = new ColinFunction();
		MethodAccessor<String> getName = new MethodAccessor<String>("getName", cf, String.class);
		
		assertEquals("premm",getName.invoke("pre"));
//		assertEquals(0,id.get(cf).longValue());
	}
	
	
}
