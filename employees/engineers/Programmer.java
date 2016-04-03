package employees.engineers;

import employees.Engineer;

public class Programmer extends Engineer {
    public enum ProgrammingLanguage {
        JAVA,
        C,
        PYTHON,
        RUBY,
        PHP,
        DELPHI
    }

    private ProgrammingLanguage programmingLanguage;
    private String currentProject;

    public String getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(String currentProject) {
        this.currentProject = currentProject;
    }


    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
}
