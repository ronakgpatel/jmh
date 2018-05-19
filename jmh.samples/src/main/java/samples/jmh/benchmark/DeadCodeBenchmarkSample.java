package samples.jmh.benchmark;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class DeadCodeBenchmarkSample {

	@State(Scope.Thread)
	public static class Data {
		BigInteger bigIntA = new BigInteger(String.valueOf(Integer.MAX_VALUE));
		BigInteger bigIntB = new BigInteger(String.valueOf(Long.MAX_VALUE));
	}
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testAddBigInteger(Data data, Blackhole blackhole) {
		BigInteger bigIntC = data.bigIntA.add(data.bigIntB);
		blackhole.consume(bigIntC);
	}
	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder().include(".*").warmupIterations(2).measurementIterations(10).forks(2)
				.resultFormat(ResultFormatType.TEXT).build();

		new Runner(opts).run();
	}

}
