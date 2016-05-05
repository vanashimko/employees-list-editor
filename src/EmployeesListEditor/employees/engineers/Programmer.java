package EmployeesListEditor.employees.engineers;

import EmployeesListEditor.employees.Engineer;
import EmployeesListEditor.gui.LocalizedName;

@LocalizedName("Программист")
public class Programmer extends Engineer {
    public enum ProgrammingLanguage {
        @LocalizedName("Java")
        JAVA,
        @LocalizedName("C")
        C,
        @LocalizedName("Python")
        PYTHON,
        @LocalizedName("Ruby")
        RUBY,
        @LocalizedName("PHP")
        PHP,
        @LocalizedName("Delphi")
        DELPHI
    }

    private ProgrammingLanguage programmingLanguage;
    private String currentProject;

    @LocalizedName("Текущий проект")
    public String getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(String currentProject) {
        this.currentProject = currentProject;
    }

    @LocalizedName("Язык программирования")
    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
}
