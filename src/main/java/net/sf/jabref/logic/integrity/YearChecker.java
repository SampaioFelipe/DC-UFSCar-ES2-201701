package net.sf.jabref.logic.integrity;

import java.util.Calendar;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import net.sf.jabref.logic.l10n.Localization;

public class YearChecker implements ValueChecker {

    private static final Predicate<String> CONTAINS_FOUR_DIGIT = Pattern.compile("([^0-9]|^)[0-9]{4}([^0-9]|$)")
            .asPredicate();
    private static final Predicate<String> ENDS_WITH_FOUR_DIGIT = Pattern.compile("[0-9]{4}$").asPredicate();
    private static final String PUNCTUATION_MARKS = "[(){},.;!?<>%&$]";

    /**
     * Checks, if the number String contains a four digit year and ends with it.
     * Official bibtex spec:
     * Generally it should consist of four numerals, such as 1984, although the standard styles
     * can handle any year whose last four nonpunctuation characters are numerals, such as ‘(about 1984)’.
     * Source: http://ftp.fernuni-hagen.de/ftp-dir/pub/mirrors/www.ctan.org/biblio/bibtex/base/btxdoc.pdf
     */
    @Override
    public Optional<String> checkValue(String value) {
        if (!CONTAINS_FOUR_DIGIT.test(value.trim())) {
            return Optional.of(Localization.lang("should contain a four digit number"));
        }

        if (!ENDS_WITH_FOUR_DIGIT.test(value.replaceAll(PUNCTUATION_MARKS, ""))) {return Optional.of(Localization.lang("last four nonpunctuation characters should be numerals"));
        }

        /*If the first parse fails, an error is returned
        * If the year value is negative or higher than the current year, it also returns an error*/
        try
        {
            int entry_year = Integer.parseInt(value);
            int currentyear = Calendar.getInstance().get(Calendar.YEAR);

            if (entry_year < 0 || entry_year > currentyear)
                return Optional.of(Localization.lang("the number must be from 0 to this year"));

        }catch(Exception e)
        {return Optional.of(Localization.lang("must be a number from 0 to this year"));}

        return Optional.empty();
    }
}
