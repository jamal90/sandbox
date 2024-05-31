
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExMatcher {

	public static void main(String[] args) {
		final String regex = "[^\\[]+\\[(\\d+)\\]";
		String string = "abc[1][2][3]";

		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
			System.out.println("Full match: " + matcher.group(0));
			int i = 1;
			System.out.println("Group " + i + ": " + matcher.group(i));
			string = string.replace(matcher.group(0), "");
			System.out.println("Remaining: " + string);
		}

	}

}
