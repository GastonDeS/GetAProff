package ar.edu.itba.paw.webapp.Forms;


public class SubjectsForm {

    private final int MAX_SUBJECTS = 15;

    private String[] names = new String[MAX_SUBJECTS];
    private Integer[] prices = new Integer[MAX_SUBJECTS];
    private Integer[] levels = new Integer[MAX_SUBJECTS];

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public Integer[] getPrices() {
        return prices;
    }

    public void setPrices(Integer[] prices) {
        this.prices = prices;
    }

    public Integer[] getLevels() {
        return levels;
    }

    public void setLevels(Integer[] levels) {
        this.levels = levels;
    }
}
