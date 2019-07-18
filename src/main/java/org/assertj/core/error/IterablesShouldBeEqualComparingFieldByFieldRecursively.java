package org.assertj.core.error;

import org.assertj.core.api.recursive.comparison.ComparisonDifference;
import org.assertj.core.api.recursive.comparison.IterableComparisonDifferenceContainer;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.assertj.core.presentation.Representation;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.util.Strings.escapePercent;
import static org.assertj.core.util.Strings.join;

public class IterablesShouldBeEqualComparingFieldByFieldRecursively extends BasicErrorMessageFactory {

  public static ErrorMessageFactory iterablesShouldBeEqualComparingFieldByFieldRecursively(Iterable<?> actual, Iterable<?> other,
                                                                                          List<IterableComparisonDifferenceContainer> differenceContainers,
                                                                                          RecursiveComparisonConfiguration recursiveComparisonConfiguration,
                                                                                          Representation representation) {

    String differencesDescription = join(differenceContainers.stream()
      .map(differenceContainer -> {
        StringBuilder sb = new StringBuilder("elements at index " + differenceContainer.getIndex() + " differ:\n");
        sb.append("- actual was: ")
          .append(differenceContainer.getActual())
          .append("\n")
          .append("- expected was: ")
          .append(differenceContainer.getOther())
          .append("\nDifferences found:\n");

        differenceContainer.getDifferences().forEach(difference -> sb.append(difference.multiLineDescription()).append("\n"));

        return sb.toString();
      })
      .collect(toList())).with(format("%n%n"));

    String recursiveComparisonConfigurationDescription = recursiveComparisonConfiguration.multiLineDescription(representation);
    String differencesCount = differenceContainers.size() == 1 ? "difference:%n" : "%s differences:%n";

    return new IterablesShouldBeEqualComparingFieldByFieldRecursively("%n" +
      "Expecting iterable:%n" +
      "  <%s>%n" +
      "to be equal to:%n" +
      "  <%s>%n" +
      "when recursively comparing iterables field by field, but found the following " + differencesCount +
      "%n" +
      escapePercent(differencesDescription) + "%n" +
      "%n"+
      "The recursive comparison was performed with this configuration:%n" +
      recursiveComparisonConfigurationDescription, // don't use %s to avoid AssertJ formatting String with ""
      actual, other, differenceContainers.size());
  }

  public IterablesShouldBeEqualComparingFieldByFieldRecursively(String format, Object... arguments) {
    super(format, arguments);
  }

}
