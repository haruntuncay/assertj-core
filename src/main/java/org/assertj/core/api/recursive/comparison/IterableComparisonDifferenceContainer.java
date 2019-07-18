package org.assertj.core.api.recursive.comparison;

import java.util.List;

public class IterableComparisonDifferenceContainer {

  public Object actual;
  public Object other;
  public int index;
  public List<ComparisonDifference> differences;

  public IterableComparisonDifferenceContainer(Object actual, Object other, int index, List<ComparisonDifference> differences) {
    this.actual = actual;
    this.other = other;
    this.index = index;
    this.differences = differences;
  }

  public Object getActual() {
    return actual;
  }

  public Object getOther() {
    return other;
  }

  public int getIndex() {
    return index;
  }

  public List<ComparisonDifference> getDifferences() {
    return differences;
  }

}
