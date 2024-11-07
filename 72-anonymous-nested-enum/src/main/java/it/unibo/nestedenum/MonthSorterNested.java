package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private static final int MONTH_WITH_TWENTY_EIGHT_DAYS = 28;
    private static final int MONTH_WITH_THIRTY_DAYS = 30;
    private static final int MONTH_WITH_THIRTY_ONE_DAYS = 31;

    private enum Month {
        JANUARY(MONTH_WITH_THIRTY_ONE_DAYS),
        FEBRUARY(MONTH_WITH_TWENTY_EIGHT_DAYS),
        MARCH(MONTH_WITH_THIRTY_ONE_DAYS),
        APRIL(MONTH_WITH_THIRTY_DAYS),
        MAY(MONTH_WITH_THIRTY_ONE_DAYS),
        JUNE(MONTH_WITH_THIRTY_DAYS),
        JULY(MONTH_WITH_THIRTY_ONE_DAYS),
        AUGUST(MONTH_WITH_THIRTY_ONE_DAYS),
        SEPTEMBER(MONTH_WITH_THIRTY_DAYS),
        OCTOBER(MONTH_WITH_THIRTY_ONE_DAYS),
        NOVEMBER(MONTH_WITH_THIRTY_DAYS),
        DECEMBER(MONTH_WITH_THIRTY_ONE_DAYS);

        private final int days;

        Month(final Integer days) {
            this.days = days;
        }

        int getDays() {
            return this.days;
        }

        static Month fromString(final String value) {
            final String normalizedValue = value.toLowerCase(Locale.UK);
            int matchCounter = 0;
            Month monthFound = null;

            for (final Month month : values()) {
                if (month.name().toLowerCase(Locale.UK).startsWith(normalizedValue)) {
                    monthFound = month;
                    matchCounter++;
                }
            }

            if (matchCounter == 0) {
                throw new IllegalArgumentException(
                        "Month not found with input " + value);
            }

            if (matchCounter > 1) {
                throw new IllegalArgumentException(
                        "Too many months fround with specific input " + value);
            }

            return monthFound;
        }

    }

    @Override
    public Comparator<String> sortByDays() {
        return new Comparator<>() {
            @Override
            public int compare(final String o1, final String o2) {
                final Month month1 = Month.fromString(o1);
                final Month month2 = Month.fromString(o2);

                return Integer.compare(month1.getDays(), month2.getDays());
            }
        };
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new Comparator<>() {
            @Override
            public int compare(final String o1, final String o2) {
                final Month month1 = Month.fromString(o1);
                final Month month2 = Month.fromString(o2);

                return Integer.compare(month1.ordinal(), month2.ordinal());
            }
        };
    }
}
