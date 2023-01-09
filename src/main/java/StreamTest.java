import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.text.html.Option;

public class StreamTest {

    public static void main(String[] args) {
        List<String> nameList = Arrays.asList("SSS", "bbb", "ttt", "abc", "efg");
        // filter
        Long count = nameList.stream().filter(name -> name.equals("bbb")).count();
        // collect
        List<String> filteredList = nameList.stream().filter(name -> name.equals("bbb")).collect(Collectors.toList());
        // map
        List<String> replacedList = nameList.stream().map(name -> name.replace("S", "TT")).collect(Collectors.toList());
        // reduce (모든 스트림 요소를 처리해 값을 도출, 두개의 인자를 가짐) // 원소들을 하나씩 소모해가며 누적 계산을 수행하고 결과값을 리턴
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Optional<Integer> sum = numbers.reduce((x, y) -> x + y);
        sum.ifPresent(s -> System.out.println("sum : " + s));
    }
}
