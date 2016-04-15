package EmployeesListEditor.employees;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    private String surname;
    private String name;
    private String middleName;
    private int stage;
    private int hiringYear;
    private int yearOfBirth;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getHiringYear() {
        return hiringYear;
    }

    public void setHiringYear(int hiringYear) {
        this.hiringYear = hiringYear;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getProfession() {
        return getClass().getSimpleName();
    }

    public String getDescription() {
        String name = getName();
        name = (name == null)?"":name;

        String surname = getSurname();
        surname = (surname == null)?"":surname;

        String profession = getProfession();
        String result = String.join(" ", name, surname);
        if (result.equals(" ")){
            return profession;
        }
        return result;
    }
}
