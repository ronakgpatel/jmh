package samples.jmh.helloworld.benchmark;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import samples.jmh.helloworld.HelloWorld;

public class HelloWorldTestBenchmark {
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public void testGreetWithSF(Blackhole blackhole) {
		blackhole.consume(new HelloWorld()
				.greetWithSF(
						"Anon", 
				ThreadLocalRandom.current().nextInt(),
				(short) ThreadLocalRandom.current().nextInt(), 
				ThreadLocalRandom.current().nextFloat()));
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public void testGreetWithSB(Blackhole blackhole) {
		blackhole.consume(new HelloWorld().greetWithSB(
				"anon",
				ThreadLocalRandom.current().nextInt(),
				(short) ThreadLocalRandom.current().nextInt(), 
				ThreadLocalRandom.current().nextFloat()));
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder().include(".*")
				.warmupIterations(2)
				.measurementIterations(10)
				.forks(2)
				.resultFormat(ResultFormatType.TEXT).build();

		new Runner(opts).run();
	}
}
