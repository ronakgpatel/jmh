package samples.jmh.benchmark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class ListAddBenchmark {
	public static final int _10_00_000 = 10_00_000;

	@State(Scope.Thread)
	public static class MyDataToRemove {
		@Setup(Level.Trial)
		public void trialSetup() {
			System.out.println("***Trial Setup ");
		}

		@Setup(Level.Iteration)
		public void iterationSetup() {
			System.out.println("***Iteration seutp");
		}

		@Setup(Level.Invocation)
		public void invocationSetup() {
			System.out.println("***Invocation seutp");
		}

		@TearDown(Level.Invocation)
		public void invocationTearDown() {
			System.out.println("***Invocation teardown");
		}
		
		@TearDown(Level.Iteration)
		public void iterationTearDown() {
			System.out.println("***Iteration teardown");
		}
		@TearDown(Level.Trial)
		public void trialTearDown() {
			System.out.println("***Trial teardown");
		}

		List<Integer> arrayList = IntStream.range(0, 1_000).map(i -> ThreadLocalRandom.current().nextInt())
				.collect(ArrayList::new, List::add, List::addAll);;
	}

	@State(Scope.Thread)
	public static class MyListData {
		List<Integer> arrayList = IntStream.range(0, _10_00_000).collect(ArrayList::new, List::add, List::addAll);
		List<Integer> linkedList = IntStream.range(0, 10_00_000).collect(LinkedList::new, List::add, List::addAll);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testArrayListRemove(MyListData listData, MyDataToRemove data) {
		data.arrayList.forEach(listData.arrayList::remove);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testLinkedListRemove(MyListData listData, MyDataToRemove data) {
		listData.linkedList.forEach(listData.arrayList::remove);
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder().include(".*").warmupIterations(2).measurementIterations(10).forks(2)
				.resultFormat(ResultFormatType.TEXT).build();

		new Runner(opts).run();
	}
}
