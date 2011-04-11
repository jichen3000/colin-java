package com.colin.test.easymock;

import junit.framework.TestCase;

import org.easymock.EasyMock;
// 注意被mock的对象，在创建时，一定要使用接口。
public class TsetEasyMock extends TestCase {
	public void testCal(){
		IParameterClass mock = EasyMock.createMock(IParameterClass.class);
		mock.add3(2);
		EasyMock.expectLastCall().andReturn(5);
		EasyMock.replay(mock);
		
		NeedTestClass ntc = new NeedTestClass();
		String result = ntc.cal(mock);
		assertEquals("5",result);
		EasyMock.verify(mock);
		
		
	}
}
