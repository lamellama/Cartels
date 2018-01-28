package yio.tro.antiyoy.gameplay.name_generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import yio.tro.antiyoy.gameplay.Hex;
import yio.tro.antiyoy.stuff.LanguagesManager;

public class CityNameGenerator {


    private static CityNameGenerator instance = null;
    NameGenerator nameGenerator;
    ArrayList<String> masks;
    HashMap<String, String> groups;


    public CityNameGenerator() {
        nameGenerator = new NameGenerator();

        masks = new ArrayList<>();
        groups = new HashMap<>();
    }


    public static CityNameGenerator getInstance() {
        if (instance == null) {
            instance = new CityNameGenerator();
        }

        return instance;
    }


    public void load() {
        loadMasks();
        loadGroups();
    }


    private void loadGroups() {
        String groupsSrcString = LanguagesManager.getInstance().getString("city_name_gen_groups");

        StringTokenizer tokenizer = new StringTokenizer(groupsSrcString, "#");
        groups.clear();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            int indexOfSeparator = token.indexOf(':');
            String key = token.substring(0, indexOfSeparator);
            String value = token.substring(indexOfSeparator + 1);

            groups.put(key, value);
        }
    }


    private void loadMasks() {
        String masksSrcString = LanguagesManager.getInstance().getString("city_name_gen_masks");

        StringTokenizer tokenizer = new StringTokenizer(masksSrcString, " ");

        masks.clear();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            masks.add(token);
        }
    }


    public String generateName() {
        return nameGenerator.generateName();
    }


    public String generateName(Hex capitalHex) {
        Random random = new Random(capitalHex.index1 + 53 * capitalHex.index2);

        nameGenerator.setMasks(masks);
        nameGenerator.setGroups(groups);
        nameGenerator.setCapitalize(true);

        return nameGenerator.generateName(random);
    }
}
