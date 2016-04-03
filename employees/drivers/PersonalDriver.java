package employees.drivers;

import employees.Driver;

public class PersonalDriver extends Driver {
    private String boss;

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }
}
