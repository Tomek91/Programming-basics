package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.IntegerModel;
import pl.com.app.service.utils.NumbersCollector;

import java.util.*;
import java.util.stream.Collectors;


class ListsService {
    private final List<Integer> minList;
    private final List<Integer> midList;
    private final List<Integer> maxList;

    public List<Integer> getMinList() {
        return minList;
    }

    public List<Integer> getMidList() {
        return midList;
    }

    public List<Integer> getMaxList() {
        return maxList;
    }

    ListsService(final String fileName) {
        List<IntegerModel> numbersList = new DataLoaderService().loadIntegerModels(fileName);

        Map<String, List<Integer>> lists = numbersList.stream().collect(new NumbersCollector());
        minList = lists.get("MIN");
        midList = lists.get("MID");
        maxList = lists.get("MAX");
    }

    boolean isPerfectFile() {
        Collections.sort(this.getMinList());
        Collections.sort(this.getMidList());
        Collections.sort(this.getMaxList());

        return this.getMinList().get(this.getMinList().size() - 1) < this.getMidList().get(0)
                && this.getMidList().get(this.getMidList().size() - 1) < this.getMaxList().get(0);
    }

    Map<String, List<Integer>> mapOfNumbersToRemoveToBePerfectFile() {
        Map<String, List<Integer>> mapOfNumbersToRemoveToBePerfectFile = new LinkedHashMap<>();
        mapOfNumbersToRemoveToBePerfectFile.put("MIN LIST", getNumbersLessThan(minOfList(this.getMidList()), this.getMinList()));
        mapOfNumbersToRemoveToBePerfectFile.put("AVG LIST", getNumbersLessThan(minOfList(this.getMaxList()), this.getMidList()));
        return mapOfNumbersToRemoveToBePerfectFile;
    }

    private Integer minOfList(List<Integer> list){
        if (list == null){
            throw new MyException("ARGS ARE NULL");
        }
        return list
                .stream()
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new MyException("MIN IS NULL"));
    }

    private Integer maxOfList(List<Integer> list){
        if (list == null){
            throw new MyException("ARGS ARE NULL");
        }
        return list
                .stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new MyException("MIN IS NULL"));
    }

    private List<Integer> getNumbersLessThan(final Integer maxValue, List<Integer> list) {
        if (list == null || maxValue == null){
            throw new MyException("ARGS ARE NULL");
        }
        return list
                .stream()
                .filter(x -> x >= maxValue)
                .collect(Collectors.toList());
    }

    int diffBetweenMaxListOneAndMinListTwo() {
        return Math.abs(maxOfList(this.getMinList()) - minOfList(this.getMidList()));
    }

    int numberOfDividingByDifference() {
        int n = 0;
        int diff = diffBetweenMaxListOneAndMinListTwo();
        if (diff > 0) {
            n = (int) this.getMaxList()
                    .stream()
                    .filter(x -> x % diff == 0)
                    .count();
        }
        return n;
    }

    String longestDecreaseCourse() {
        int decreaseCourse1 = decreaseCourse(this.getMinList());
        int decreaseCourse2 = decreaseCourse(this.getMidList());
        int decreaseCourse3 = decreaseCourse(this.getMaxList());

        if (decreaseCourse1 > decreaseCourse2 && decreaseCourse1 > decreaseCourse3) {
            return "First";
        } else if (decreaseCourse2 > decreaseCourse1 && decreaseCourse2 > decreaseCourse3) {
            return "Second";
        } else if (decreaseCourse3 > decreaseCourse1 && decreaseCourse3 > decreaseCourse2) {
            return "Third";
        } else {
            return "Equals";
        }
    }

    private static int decreaseCourse(List<Integer> list) {
        if (list == null){
            throw new MyException("LIST IS NULL");
        }
        int n = 1;
        int nMax = 1;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) >= list.get(i - 1)) {
                n++;
            } else {
                if (n > nMax) {
                    nMax = n;
                }
                n = 1;
            }
        }
        System.out.println("decreaseCourse Max " + nMax);
        return nMax;
    }

    void minMaxDiffAfterSorting() {
        this.getMinList().sort(Collections.reverseOrder());
        this.getMidList().sort(Collections.reverseOrder());
        this.getMaxList().sort(Collections.reverseOrder());

        int idMinDiffIndex = 0;
        int idMaxDiffIndex = 0;
        for (int i = 1; i < this.getMaxList().size(); i++) {
            int diff = calculateDiff(i);
            if (diff > calculateDiff(idMaxDiffIndex)) {
                idMaxDiffIndex = i;
            } else if (diff < calculateDiff(idMinDiffIndex)) {
                idMinDiffIndex = i;
            }
        }
        System.out.println("MinDiffIndex " + idMinDiffIndex);
        showMinMaxDiff(idMinDiffIndex);
        System.out.println("MaxDiffIndex " + idMaxDiffIndex);
        showMinMaxDiff(idMaxDiffIndex);
    }

    private void showMinMaxDiff(int i) {
        System.out.println(this.getMaxList().get(i));
        System.out.println(this.getMidList().get(i));
        System.out.println(this.getMinList().get(i));
    }

    private int calculateDiff(int i) {
        return this.getMaxList().get(i) - this.getMidList().get(i) - this.getMinList().get(i);
    }
}
