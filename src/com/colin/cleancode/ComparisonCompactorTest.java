package com.colin.cleancode;

import junit.framework.TestCase;

public class ComparisonCompactorTest extends TestCase {
	public void testMessage() {
		String failure = new ComparisonCompactor(0, "b", "c").formatCompactedComparison("a");
		assertTrue("a expected:<[b]> but was:<[c]>".equals(failure));
	}

	public void testStartSame() {
		String failure = new ComparisonCompactor(1, "ba", "bc").formatCompactedComparison(null);
		assertEquals("expected:<b[a]> but was:<b[c]>", failure);
	}

	public void testEndSame() {
		String failure = new ComparisonCompactor(1, "ab", "cb").formatCompactedComparison(null);
		assertEquals("expected:<[a]b> but was:<[c]b>", failure);
	}

	public void testSame() {
		String failure = new ComparisonCompactor(1, "ab", "ab").formatCompactedComparison(null);
		assertEquals("expected:<ab> but was:<ab>", failure);
	}

	public void testNoContextStartAndEndSame() {
		String failure = new ComparisonCompactor(0, "abc", "adc").formatCompactedComparison(null);
		assertEquals("expected:<...[b]...> but was:<...[d]...>", failure);
	}
}
