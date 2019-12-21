package pl.com.app.service.utils;

import pl.com.app.model.IntegerModel;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumbersCollector implements Collector<IntegerModel, Map<String, List<Integer>>, Map<String, List<Integer>>> {

    @Override
    public Supplier<Map<String, List<Integer>>> supplier() {
        return () -> Stream.of("MIN", "MID", "MAX")
                .collect(Collectors.toMap(
                        name -> name,
                        name -> new ArrayList<Integer>())
                );
    }

    @Override
    public BiConsumer<Map<String, List<Integer>>, IntegerModel> accumulator() {
        return (accumulator, integerModel) -> {
            List<Integer> sortedList = IntStream
                    .of(integerModel.getN1(), integerModel.getN2(), integerModel.getN3())
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());

            accumulator.get("MIN").add(sortedList.get(0));
            accumulator.get("MID").add(sortedList.get(1));
            accumulator.get("MAX").add(sortedList.get(2));
        };
    }

    @Override
    public BinaryOperator<Map<String, List<Integer>>> combiner() {
        return (res1, res2) -> Stream.of(res1, res2)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        })
                );
    }

    @Override
    public Function<Map<String, List<Integer>>, Map<String, List<Integer>>> finisher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return new HashSet<>(Arrays.asList(Characteristics.IDENTITY_FINISH));
    }
}
