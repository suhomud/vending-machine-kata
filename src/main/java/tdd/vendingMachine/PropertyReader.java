package tdd.vendingMachine;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yevhen Sukhomud
 */
public class PropertyReader {

    private static final String APPLICATION_PROPERTY_PATH = "/application.property";
    private static final String VALID_DENOMINATIONS_PROPERTY = "valid.denominations";
    private static final Integer[] DEFAULT_DENOMINATIONS = {500, 200, 100, 50, 20, 10};
    private static final String DENOMINATION_SEPARATOR = ",";
    private Properties prop = new Properties();
    private Set<Integer> acceptableDenominations = new HashSet<>();

    public PropertyReader() throws IOException {
        setUp();
    }

    public Set<Integer> readAcceptableDenominations() {
        return new HashSet<>(acceptableDenominations);
    }

    public void setAcceptableDenominations(Set<Integer> denominations) {
        acceptableDenominations.addAll(denominations);
    }

    private void setUp() throws IOException {
        prop.load(getClass().getResourceAsStream(APPLICATION_PROPERTY_PATH));
        String validDenominationsProperty = prop.getProperty(VALID_DENOMINATIONS_PROPERTY);
        if (validDenominationsProperty != null) {
            setAcceptableDenominations(splitDenominations());
        } else {
            setDenominationsByDefault();
        }
    }

    private Set<Integer> splitDenominations() {
        return Arrays.stream(prop.getProperty(VALID_DENOMINATIONS_PROPERTY).split(DENOMINATION_SEPARATOR))
            .map(Integer::valueOf)
            .collect(Collectors.toSet());
    }

    private void setDenominationsByDefault() {
        acceptableDenominations.addAll(Arrays.asList(DEFAULT_DENOMINATIONS));
    }

}
