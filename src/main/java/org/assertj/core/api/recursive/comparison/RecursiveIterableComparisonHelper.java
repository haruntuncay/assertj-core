package org.assertj.core.api.recursive.comparison;

import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Failures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.error.IterablesShouldBeEqualComparingFieldByFieldRecursively.iterablesShouldBeEqualComparingFieldByFieldRecursively;

public class RecursiveIterableComparisonHelper {

  private RecursiveComparisonConfiguration recursiveComparisonConfiguration;
  private RecursiveComparisonDifferenceCalculator differenceCalculator;
  private Iterable<?> actual;
  private WritableAssertionInfo info;
  private Failures failures;

  public RecursiveIterableComparisonHelper(Iterable<?> actual, RecursiveComparisonConfiguration recursiveComparisonConfiguration, WritableAssertionInfo info, Failures failures) {
    this.actual = actual;
    this.recursiveComparisonConfiguration = recursiveComparisonConfiguration;
    differenceCalculator = new RecursiveComparisonDifferenceCalculator();
    this.info = info;
    this.failures = failures;
  }

  public void isEqualTo(Iterable<?> expected) {
    Iterator<?> expectedIterator = expected.iterator();
    List<IterableComparisonDifferenceContainer> comparisonDifferenceContainersList = new LinkedList<>();

    int index = 0;
    for(Object actualItem : actual) {
      Object otherItem = expectedIterator.next();
      List<ComparisonDifference> differences = differenceCalculator.determineDifferences(actualItem, otherItem, recursiveComparisonConfiguration);
      if(!differences.isEmpty()) {
        comparisonDifferenceContainersList.add(new IterableComparisonDifferenceContainer(actualItem, otherItem, index, differences));
      }
      index++;
    }

    if(!comparisonDifferenceContainersList.isEmpty()) {
      throw failures.failure(info, iterablesShouldBeEqualComparingFieldByFieldRecursively(actual, expected, comparisonDifferenceContainersList, recursiveComparisonConfiguration, info.representation()));
    }
  }
}
