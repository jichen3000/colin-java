package com.colin.cleancode;

class Assert {
	public static String format(String message, String expected, String actual) {
		String showMessage = "";
		if (message != null)
			showMessage = message + " ";
		showMessage = showMessage + "expected:<" + expected + "> but was:<"
		    + actual + ">";
		return showMessage;
	}
}

public class ComparisonCompactor {
	private static final String ELLIPSIS = "...";
	private static final String DELTA_END = "]";
	private static final String DELTA_START = "[";
	private int contextLength;
	private String expected;
	private String actual;
	private int prefixIndex;
	private int suffixLength;
	private String compactExpected;
	private String compactActual;

	public ComparisonCompactor(int contextLength, String expected, String actual) {
		this.contextLength = contextLength;
		this.expected = expected;
		this.actual = actual;
	}

	public String formatCompactedComparison(String message) {
		if (canBeCompacted()) {
			compactExpectedAndActual();
			return Assert.format(message, compactExpected, compactActual);
		} else {
			return Assert.format(message, expected, actual);
		}
	}

	private void compactExpectedAndActual() {
		findCommonPrefixAndSuffix();
		compactExpected = compactString(this.expected);
		compactActual = compactString(this.actual);
	}

	private boolean canBeCompacted() {
		return expected != null && actual != null && !areStringsEqual();
	}

	private String compactString(String source) {
		String result = DELTA_START
		    + source.substring(prefixIndex, source.length() - suffixLength + 1)
		    + DELTA_END;
		if (prefixIndex > 0)
			result = computeCommonPrefix() + result;
		if (suffixLength > 0)
			result = result + computeCommonSuffix();
		return result;
	}

	private void findCommonPrefix() {
		prefixIndex = 0;
		int end = Math.min(expected.length(), actual.length());
		for (; prefixIndex < end; prefixIndex++) {
			if (expected.charAt(prefixIndex) != actual.charAt(prefixIndex))
				break;
		}
	}

	private void findCommonPrefixAndSuffix() {
		findCommonPrefix();
		suffixLength = 1;
		for (; !suffixOverlapsPrefix(); suffixLength++) {
			if (charFromEnd(expected,suffixLength) != charFromEnd(actual,suffixLength))
				break;
		}
	}

	private char charFromEnd(String s, int i) {
		return s.charAt(s.length() - i);
	}

	private boolean suffixOverlapsPrefix() {
		return (expected.length() - suffixLength) <= prefixIndex
		    || (actual.length() - suffixLength) <= prefixIndex;
	}

	private String computeCommonPrefix() {
		return (prefixIndex > contextLength ? ELLIPSIS : "")
		    + expected.substring(Math.max(0, prefixIndex - contextLength),
		        prefixIndex);
	}

	private String computeCommonSuffix() {
		int end = Math.min(expected.length() - suffixLength + 1 + contextLength,
		    expected.length());
		return expected.substring(expected.length() - suffixLength + 1, end)
		    + (expected.length() - suffixLength + 1 < expected.length()
		        - contextLength ? ELLIPSIS : "");
	}

	private boolean areStringsEqual() {
		return expected.equals(actual);
	}
}