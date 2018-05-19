package samples.jmh.helloworld;

/**
 * Hello world!
 *
 */
public class HelloWorld {
	public String greetWithSF(String name, int number, 
			short anotherNumber, float floatNumber) {
		return String.format("Hello World %s(%d - %d - %f)", name, number, 
				anotherNumber, floatNumber);
	}

	public String greetWithSB(String name, int number, 
			short anotherNumber, float floatNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append("Hello World");
		sb.append(name);
		sb.append("(");
		sb.append(number);
		sb.append("-").append(anotherNumber);
		sb.append("-").append(floatNumber);
		sb.append(")");
		return sb.toString();
	}
}
