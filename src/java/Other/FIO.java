/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

/**
 *
 * @author Aiglu
 */
public class FIO {

    private String fam;
    private String name;
    private String patr;
    private String pol;

    public FIO() {
        fam = "";
        name = "";
        patr = "";
    }

    public FIO(String fam, String name, String patr, String pol) {
        this.fam = fam.trim();
        this.name = name.trim();
        this.patr = patr.trim();
        this.pol = pol;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatr() {
        return patr;
    }

    public void setPatr(String patr) {
        this.patr = patr;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getInicFam() {
        return name.substring(0, 1) + "." + patr.substring(0, 1) + ". " + fam;
    }

    public String getFamInic() {
        return fam + " " + name.substring(0, 1) + "." + patr.substring(0, 1) + ".";
    }

    public String getFullFIO() {
        return fam + " " + name + " " + patr;
    }

    public String getFullIOF() {
        return name + " " + patr + " " + fam;
    }

    public FIO changeToDat() {      // дательный падеж
        // учитываем, что фамилия или имя могут быть составными
        String[] fams = fam.split("-");
        String[] names = name.split("-");
        String sex = "";
        // если пол не известен заранее и есть отчество, определям пол программно
        if ((pol == null) && (patr != null)) {
            if (patr.endsWith("ич")) {
                sex = "м";
                this.setPatr(patr + "у");
            } else if (patr.endsWith("на")) {
                sex = "ж";
                this.setPatr(patr.substring(0, patr.length() - 1) + "е");
            } else if ((patr.toUpperCase().endsWith("ОГЛЫ")) || (patr.toUpperCase().endsWith("ОГЛИ"))
                    || (patr.toUpperCase().endsWith("УГЛИ"))) {
                sex = "м";
            } else if ((patr.toUpperCase().endsWith("КЫЗЫ")) || (patr.toUpperCase().endsWith("ГЫЗЫ")) || (patr.toUpperCase().endsWith("КИЗИ"))) {
                sex = "ж";
            }
        } else if (pol != null) {
            sex = pol;
        }

        switch (sex) {
            case "м":
                // фамилия
                for (int i = 0; i < fams.length; i++) {
                    if (fams[i].endsWith("ой") || fams[i].endsWith("ый")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ому";
                    } else if (fams[i].endsWith("ий")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ему";
                    } else if (fams[i].endsWith("я")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "е";
                    } else if (fams[i].endsWith("ь") || fams[i].endsWith("й")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "ю";
                    } else if (!fams[i].endsWith("а") && !fams[i].endsWith("е") && !fams[i].endsWith("ё") && !fams[i].endsWith("и")
                            && !fams[i].endsWith("о") && !fams[i].endsWith("у") && !fams[i].endsWith("э")
                            && !fams[i].endsWith("ю") && !fams[i].endsWith("х") && !fams[i].endsWith("ы")) {
                        fams[i] += "у";
                    }
                }
                // имя
                for (int i = 0; i < names.length; i++) {
                    if (names[i].equals("Лев")) {
                        names[i] = "Льву";
                    } else if (names[i].equals("Павел")) {
                        names[i] = "Павлу";
                    } else if (names[i].equals("Пётр")) {
                        names[i] = "Петру";
                    } else if (names[i].endsWith("й") || names[i].endsWith("ь")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "ю";
                    } else if (names[i].endsWith("а") || names[i].endsWith("я")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "е";
                    } else if (!names[i].endsWith("е") && !names[i].endsWith("ё") && !names[i].endsWith("и")
                            && !names[i].endsWith("о") && !names[i].endsWith("у") && !names[i].endsWith("э")
                            && !names[i].endsWith("ю")) {
                        names[i] += "у";
                    }
                }
                // отчество
                if (patr != null) {
                    if (patr.endsWith("ич")) {
                        this.setPatr(patr + "у");
                    }
                }
                break;
            case "ж":
                // фамилия
                for (int i = 0; i < fams.length; i++) {
                    if (fams[i].endsWith("ва") || fams[i].endsWith("на")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "ой";
                    } else if (fams[i].endsWith("ая")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ой";
                    } else if (fams[i].endsWith("яя")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ей";
                    } else if (fams[i].endsWith("я")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "е";
                    }
                }
                // имя
                for (int i = 0; i < names.length; i++) {
                    if (names[i].endsWith("ь") || names[i].endsWith("ия")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "и";
                    } else if (names[i].endsWith("а") || names[i].endsWith("я")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "е";
                    }
                }
                // отчество
                if (patr != null) {
                    if (patr.endsWith("на")) {
                        this.setPatr(patr.substring(0, patr.length() - 1) + "е");
                    }
                }
                break;
        }

        this.setFam(fams[0]);
        for (int i = 1; i < fams.length; i++) {
            this.setFam(this.getFam() + "-" + fams[i]);
        }
        this.setName(names[0]);
        for (int i = 1; i < names.length; i++) {
            this.setName(this.getName() + "-" + names[i]);
        }
        return this;
    }

    public FIO changeToRod() {  // родительный падеж
        // учитываем, что фамилия или имя могут быть составными
        String[] fams = fam.split("-");
        String[] names = name.split("-");
        String sex = "";
        // если пол не известен заранее и есть отчество, определям пол программно
        if ((pol == null) && (patr != null)) {
            if (patr.endsWith("ич")) {
                sex = "м";
                this.setPatr(patr + "а");
            } else if (patr.endsWith("на")) {
                sex = "ж";
                this.setPatr(patr.substring(0, patr.length() - 1) + "ы");
            } else if ((patr.toUpperCase().endsWith("ОГЛЫ")) || (patr.toUpperCase().endsWith("ОГЛИ"))
                    || (patr.toUpperCase().endsWith("УГЛИ"))) {
                sex = "м";
            } else if ((patr.toUpperCase().endsWith("КЫЗЫ")) || (patr.toUpperCase().endsWith("ГЫЗЫ")) || (patr.toUpperCase().endsWith("КИЗИ"))) {
                sex = "ж";
            }
        } else if (pol != null) {
            sex = pol;
        }
        switch (sex) {
            case "м":
                // фамилия
                for (int i = 0; i < fams.length; i++) {
                    if (fams[i].endsWith("ой") || fams[i].endsWith("ый")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ого";
                    } else if (fams[i].endsWith("ий")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "его";
                    } else if (fams[i].endsWith("я")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "и";
                    } else if (fams[i].endsWith("ь") || fams[i].endsWith("й")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "я";
                    } else if (!fams[i].endsWith("а") && !fams[i].endsWith("е") && !fams[i].endsWith("ё") && !fams[i].endsWith("и")
                            && !fams[i].endsWith("о") && !fams[i].endsWith("у") && !fams[i].endsWith("э")
                            && !fams[i].endsWith("ю") && !fams[i].endsWith("х") && !fams[i].endsWith("ы")) {
                        fams[i] += "а";
                    }
                }
                // имя
                for (int i = 0; i < names.length; i++) {
                    if (names[i].equals("Лев")) {
                        names[i] = "Льва";
                    } else if (names[i].equals("Павел")) {
                        names[i] = "Павла";
                    } else if (names[i].equals("Пётр")) {
                        names[i] = "Петра";
                    } else if (names[i].endsWith("й") || names[i].endsWith("ь")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "я";
                    } else if (names[i].endsWith("а")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "ы";
                    } else if (names[i].endsWith("я")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "и";
                    } else if (!names[i].endsWith("е") && !names[i].endsWith("ё") && !names[i].endsWith("и")
                            && !names[i].endsWith("о") && !names[i].endsWith("у") && !names[i].endsWith("э")
                            && !names[i].endsWith("ю")) {
                        names[i] += "а";
                    }
                }
                // отчество
                if (patr != null) {
                    if (patr.endsWith("ич")) {
                        this.setPatr(patr + "а");
                    }
                }
                break;
            case "ж":
                // фамилия
                for (int i = 0; i < fams.length; i++) {
                    if (fams[i].endsWith("ва") || fams[i].endsWith("на")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "ой";
                    } else if (fams[i].endsWith("ая")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ой";
                    } else if (fams[i].endsWith("яя")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ей";
                    } else if (fams[i].endsWith("я")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "е";
                    }
                }
                // имя
                for (int i = 0; i < names.length; i++) {
                    if (names[i].endsWith("ь") || names[i].endsWith("ия")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "и";
                    } else if (names[i].endsWith("а")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "ы";
                    } else if (names[i].endsWith("я")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "и";
                    }
                }
                // отчество
                if (patr != null) {
                    if (patr.endsWith("на")) {
                        this.setPatr(patr.substring(0, patr.length() - 1) + "ы");
                    }
                }
                break;
        }

        this.setFam(fams[0]);
        for (int i = 1; i < fams.length; i++) {
            this.setFam(this.getFam() + "-" + fams[i]);
        }
        this.setName(names[0]);
        for (int i = 1; i < names.length; i++) {
            this.setName(this.getName() + "-" + names[i]);
        }
        return this;
    }

    public FIO changeToVin() {  // винительный падеж
        // учитываем, что фамилия или имя могут быть составными
        String[] fams = fam.split("-");
        String[] names = name.split("-");
        String sex = "";

        // если пол не известен заранее и есть отчество, определям пол программно
        if ((pol == null) && (patr != null)) {
            if (!patr.equals("")) {
                if (patr.endsWith("ич")) {
                    sex = "м";
                    this.setPatr(patr + "а");
                } else if (patr.endsWith("на")) {
                    sex = "ж";
                    this.setPatr(patr.substring(0, patr.length() - 1) + "ы");
                } else if ((patr.toUpperCase().endsWith("ОГЛЫ")) || (patr.toUpperCase().endsWith("ОГЛИ"))
                        || (patr.toUpperCase().endsWith("УГЛИ"))) {
                    sex = "м";
                } else if ((patr.toUpperCase().endsWith("КЫЗЫ")) || (patr.toUpperCase().endsWith("ГЫЗЫ")) || (patr.toUpperCase().endsWith("КИЗИ"))) {
                    sex = "ж";
                }
            }
        } else if (pol != null) {
            sex = pol;
        }

        switch (sex) {
            case "м":
                // фамилия
                for (int i = 0; i < fams.length; i++) {
                    if (fams[i].endsWith("ой") || fams[i].endsWith("ый")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ого";
                    } else if (fams[i].endsWith("ий")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "его";
                    } else if (fams[i].endsWith("я")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "ю";
                    } else if (fams[i].endsWith("ь") || fams[i].endsWith("й")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "я";
                    } else if (!fams[i].endsWith("а") && !fams[i].endsWith("е") && !fams[i].endsWith("ё") && !fams[i].endsWith("и")
                            && !fams[i].endsWith("о") && !fams[i].endsWith("у") && !fams[i].endsWith("э")
                            && !fams[i].endsWith("ю") && !fams[i].endsWith("х") && !fams[i].endsWith("ы")) {
                        fams[i] += "а";
                    }
                }
                // имя
                for (int i = 0; i < names.length; i++) {
                    if (names[i].equals("Лев")) {
                        names[i] = "Льва";
                    } else if (names[i].equals("Павел")) {
                        names[i] = "Павла";
                    } else if (names[i].equals("Пётр")) {
                        names[i] = "Петра";
                    } else if (names[i].endsWith("й") || names[i].endsWith("ь")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "я";
                    } else if (names[i].endsWith("а")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "у";
                    } else if (names[i].endsWith("я")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "ю";
                    } else if (!names[i].endsWith("е") && !names[i].endsWith("ё") && !names[i].endsWith("и")
                            && !names[i].endsWith("о") && !names[i].endsWith("у") && !names[i].endsWith("э")
                            && !names[i].endsWith("ю")) {
                        names[i] += "а";
                    }
                }
                // отчество
                if (patr != null) {
                    if (patr.endsWith("ич")) {
                        this.setPatr(patr + "а");
                    }
                }
                break;
            case "ж":
                // фамилия
                for (int i = 0; i < fams.length; i++) {
                    if (fams[i].endsWith("ва") || fams[i].endsWith("на")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "у";
                    } else if (fams[i].endsWith("ая")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "ую";
                    } else if (fams[i].endsWith("яя")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 2) + "юю";
                    } else if (fams[i].endsWith("я")) {
                        fams[i] = fams[i].substring(0, fams[i].length() - 1) + "ю";
                    }
                }
                // имя
                for (int i = 0; i < names.length; i++) {
                    if (names[i].endsWith("ь")) {
                    } else if (names[i].endsWith("ия")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "ю";
                    } else if (names[i].endsWith("а")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "у";
                    } else if (names[i].endsWith("я")) {
                        names[i] = names[i].substring(0, names[i].length() - 1) + "ю";
                    }
                }
                // отчество
                if (patr != null) {
                    if (patr.endsWith("на")) {
                        this.setPatr(patr.substring(0, patr.length() - 1) + "ы");
                    }
                }
                break;
        }

        this.setFam(fams[0]);
        for (int i = 1; i < fams.length; i++) {
            this.setFam(this.getFam() + "-" + fams[i]);
        }
        this.setName(names[0]);
        for (int i = 1; i < names.length; i++) {
            this.setName(this.getName() + "-" + names[i]);
        }
        return this;
    }
}
