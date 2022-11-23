package eu.europa.ec.digit.search.improveperformance;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NumberService {

    private static final int SAMPLE_SIZE = 100_000;
    private Random random = new Random();

    public Integer findSmallestDuplicate(List<Integer> data) {

        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {

            for (int j = i + 1; j < data.size(); j++) {

                if (data.get(i).equals(data.get(j))) {

                    log.info("found duplicate {}", data.get(j));
                    duplicates.add(data.get(j));
                }
            }
        }

        return duplicates.stream().sorted().findFirst().orElse(null);

    }

    public Integer findSmallestDuplicateImproved(List<Integer> data) {
        
        Integer smallestDuplicatedValue = Integer.MAX_VALUE;
    	log.info("-- findSmallestDuplicateImproved --");
        for (int i = 0; i < data.size()/2; i++) {           
            	
            	int pos = data.get(i) % data.size();
            	int stamp = data.get(pos) + data.size();
            	if (stamp > data.size()*2) {
            		if (pos<smallestDuplicatedValue.intValue()) { smallestDuplicatedValue = pos; }
            	}
            	data.set(pos, stamp);
            	
            	pos = data.get(data.size()-1-i) % data.size();
            	stamp = data.get(pos) + data.size();
            	if (stamp > data.size()*2) {
            		if (pos<smallestDuplicatedValue.intValue()) { smallestDuplicatedValue = pos; }
            	}
            	data.set(pos, stamp);
            	
        }
        log.info("smallestDuplicatedValue retuned value: {}",smallestDuplicatedValue);
        if (smallestDuplicatedValue>SAMPLE_SIZE) { smallestDuplicatedValue = null;}
        
        return smallestDuplicatedValue;

    }

    public List<Integer> generateData() {

        List<Integer> data = IntStream.range(0, SAMPLE_SIZE).boxed().collect(toList());
        
        data.add(data.get(random.nextInt(data.size())));
        log.info("first duplicate number is: {}", data.get(data.size() - 1));
        data.add(data.get(random.nextInt(data.size())));
        log.info("second duplicate number is: {}", data.get(data.size() - 1));
        Collections.shuffle(data);

        return data;
    }
}
