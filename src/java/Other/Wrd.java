/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Count.BigOtch;
import Count.CountDate;
import Count.CountKatBig;
import Count.CountProblemKod;
import Count.CountStatus;
import Count.StandartCount;
import Entities.Children;
import Entities.Ipra;
import Entities.Ipra18;
import Entities.Ipra18Prikaz;
import Entities.IpraEduCondition;
import Entities.SotrudDolgn;
import Entities.SprOmsu;
import Entities.SprOtherPmpk;
import Entities.Users;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTMarkupRange;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.w3c.dom.Node;

/**
 *
 * @author Aiglu
 */
public class Wrd {

    public static void bigOtchet(ServletOutputStream os, Date d1, Date d2, SotrudDolgn sotrudDolgn, List<BigOtch> otch,
            Map<String, BigOtch> konsMap, BigOtch indObsled, List<BigOtch> obsledReg, List<BigOtch> obsledRegAll,
            List<StandartCount> countPmpk, BigOtch indKrz, BigOtch grKrz, String centerName, String centerShortName)
            throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("dd.MM.yyyy");
        String date1 = dateFormat.format(d1);
        String date2 = dateFormat.format(d2);

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        r1.setFontFamily("Times New Roman");
        r1.setFontSize(14);
        r1.setBold(true);
        r1.setText(sotrudDolgn.getSotrudId().getSotrudFIO());
        r1.addBreak();
        r1.setText(sotrudDolgn.getSprdolgnId().getSprdolgnName());
        r1.addBreak();
        r1.addCarriageReturn();
        r1.setText("Отчёт за период с " + date1 + " по " + date2);

        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r2 = p2.createRun();
        r2.setFontFamily("Times New Roman");
        r2.setFontSize(14);
        r2.setBold(true);
        r2.setText("Профессиональная деятельность по направлениям");

        int k = 0;
        for (BigOtch o : otch) {
            XWPFParagraph p = doc.createParagraph();
            p.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r = p.createRun();
            r.setFontFamily("Times New Roman");
            r.setFontSize(12);
            r.setBold(true);
            k++;
            String c = "";
            if (o.getCent() == 1) {
                c = " в " + centerName;
            } else if (o.getCent() == 0) {
                c = " на территориях, закрепленных за Учреждением";
            }
            r.setText(Integer.toString(k) + ". " + o.getUsl() + " " + c);

            XWPFTable t = doc.createTable();
            XWPFTableRow tr = t.getRow(0);
            XWPFTableCell c1 = tr.getCell(0);
            XWPFParagraph pc1 = c1.getParagraphArray(0);
            pc1.setAlignment(ParagraphAlignment.CENTER);
            pc1.setVerticalAlignment(TextAlignment.CENTER);
            XWPFRun rc1 = pc1.createRun();
            rc1.setFontFamily("Times New Roman");
            rc1.setFontSize(12);
            rc1.setBold(true);
            rc1.setText("Всего обращений");

            XWPFTableCell c2 = tr.addNewTableCell();
            XWPFParagraph pc2 = c2.getParagraphArray(0);
            pc2.setAlignment(ParagraphAlignment.CENTER);
            pc2.setVerticalAlignment(TextAlignment.CENTER);
            XWPFRun rc2 = pc2.createRun();
            rc2.setFontFamily("Times New Roman");
            rc2.setFontSize(12);
            rc2.setBold(true);
            rc2.setText("Количество родителей (законных представителей), получивших услуги");

            XWPFTableCell c3 = tr.addNewTableCell();
            XWPFParagraph pc3 = c3.getParagraphArray(0);
            pc3.setAlignment(ParagraphAlignment.CENTER);
            pc3.setVerticalAlignment(TextAlignment.CENTER);
            XWPFRun rc3 = pc3.createRun();
            rc3.setFontFamily("Times New Roman");
            rc3.setFontSize(12);
            rc3.setBold(true);
            rc3.setText("Количество детей, получивших услуги");

            XWPFTableCell c4 = tr.addNewTableCell();
            XWPFParagraph pc4 = c4.getParagraphArray(0);
            pc4.setAlignment(ParagraphAlignment.CENTER);
            pc4.setVerticalAlignment(TextAlignment.CENTER);
            XWPFRun rc4 = pc4.createRun();
            rc4.setFontFamily("Times New Roman");
            rc4.setFontSize(12);
            rc4.setBold(true);
            rc4.setText("Количество педагогических работников, получивших услуги ");

            XWPFTableRow tr2 = t.createRow();

            List<CountKatBig> countList = o.getCountKatBigList();
            HashSet<String> regions = new HashSet<>();
            for (CountKatBig count : countList) {
                regions.add(count.getReg());
            }
            List<String> regList = new ArrayList<>();
            for (String rg : regions) {
                regList.add(rg);
            }
            Collections.sort(regList);

            Integer sumParents = 0;
            Integer sumChildren = 0;
            Integer sumChildren14 = 0;
            Integer sumPed = 0;
            Integer sum = 0;
            for (String reg : regList) {
                XWPFTableRow row = t.createRow();
                XWPFTableCell cell0 = row.getCell(0);
                XWPFParagraph cellp0 = cell0.getParagraphArray(0);
                cellp0.createRun().setText(reg);
                Integer countParents = 0;
                Integer countChildren = 0;
                Integer countPed = 0;
                for (CountKatBig count : countList) {
                    if (count.getReg().equals(reg)) {
                        if (count.getKat().equals("parents")) {
                            countParents += count.getCount();
                            sumParents += count.getCount();
                            sum += count.getCount();
                        } else if (count.getKat().equals("children")) {
                            countChildren += count.getCount();
                            sumChildren += count.getCount();
                            sum += count.getCount();
                        } else if (count.getKat().equals("children14")) {
                            sumChildren14 += count.getCount();
                        } else if (count.getKat().equals("ped")) {
                            countPed += count.getCount();
                            sumPed += count.getCount();
                            sum += count.getCount();
                        }

                    }
                }
                XWPFTableCell cell1 = row.getCell(1);
                XWPFParagraph cellp1 = cell1.getParagraphArray(0);
                cellp1.createRun().setText(countParents.toString());
                XWPFTableCell cell2 = row.getCell(2);
                XWPFParagraph cellp2 = cell2.getParagraphArray(0);
                cellp2.createRun().setText(countChildren.toString());
                XWPFTableCell cell3 = row.getCell(3);
                XWPFParagraph cellp3 = cell3.getParagraphArray(0);
                cellp3.createRun().setText(countPed.toString());

            }
            t.getRow(1).getCell(0).getParagraphArray(0).createRun().setText(sum.toString());
            t.getRow(1).getCell(1).getParagraphArray(0).createRun().setText(sumParents.toString());
            t.getRow(1).getCell(2).getParagraphArray(0).createRun().setText(sumChildren.toString()
                    + " из них старше 14 лет: " + sumChildren14.toString());
            t.getRow(1).getCell(3).getParagraphArray(0).createRun().setText(sumPed.toString());

            List<XWPFTableRow> rows = t.getRows();
            int rowN = 0;
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                    for (XWPFRun run : runs) {
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(10);
                        if (rowN == 0) {
                            run.setBold(true);
                            cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setIndentFromLeft(10);
                    cell.getParagraphArray(0).setSpacingAfter(1);
                    cell.getParagraphArray(0).setSpacingBefore(1);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    t.setCellMargins(20, 20, 20, 20);

                }
                rowN++;
            }

            // статусы
            if (!countList.isEmpty()) {
                XWPFParagraph p3 = doc.createParagraph();
                p3.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r3 = p3.createRun();
                r3.setFontFamily("Times New Roman");
                r3.setFontSize(12);
                r3.setBold(true);
                r3.setText("Характеристика статуса ребенка, обратившегося за психолого-педагогическим консультированием (кол-во характеристик)");

                XWPFTable t2 = doc.createTable();
                t2.createRow();

                List<CountStatus> countStatusList = o.getCountStatusList();
                int i = 0;
                for (CountStatus stat : countStatusList) {
                    if (i != 0) {
                        t2.getRow(0).createCell();
                        t2.getRow(1).createCell();
                    }
                    t2.getRow(0).getCell(i).getParagraphArray(0).createRun().setText(stat.getStatus());
                    t2.getRow(1).getCell(i).getParagraphArray(0).createRun().setText(stat.getCount().toString());
                    i++;
                }

                List<XWPFTableRow> rows2 = t2.getRows();
                rowN = 0;
                for (XWPFTableRow row : rows2) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                        for (XWPFRun run : runs) {
                            run.setFontFamily("Times New Roman");
                            run.setFontSize(10);
                            if (rowN == 0) {
                                run.setBold(true);
                                cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                            }
                            cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setIndentFromLeft(10);
                        cell.getParagraphArray(0).setSpacingAfter(1);
                        cell.getParagraphArray(0).setSpacingBefore(1);
                        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                        t2.setCellMargins(20, 20, 20, 20);
                    }
                    rowN++;
                }

                XWPFParagraph p4 = doc.createParagraph();
                p4.setSpacingBefore(1);
                p4.createRun().setText("Имеют 2 или больше характеристики: " + o.getManyStatus().toString());
                XWPFRun r4 = p4.createRun();
                r4.setFontFamily("Times New Roman");
                r4.setFontSize(12);

                XWPFTable t3 = doc.createTable();
                t3.createRow();
                List<StandartCount> ageList = o.getCountAgeList();
                i = 0;
                for (StandartCount age : ageList) {
                    if (i != 0) {
                        t3.getRow(0).createCell();
                        t3.getRow(1).createCell();
                    }
                    t3.getRow(0).getCell(i).getParagraphArray(0).createRun().setText(age.getTitle());
                    t3.getRow(1).getCell(i).getParagraphArray(0).createRun().setText(age.getCount().toString());
                    i++;
                }

                List<XWPFTableRow> rows3 = t3.getRows();
                rowN = 0;
                for (XWPFTableRow row : rows3) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                        for (XWPFRun run : runs) {
                            run.setFontFamily("Times New Roman");
                            run.setFontSize(10);
                            if (rowN == 0) {
                                run.setBold(true);
                                cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                            }
                            cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setIndentFromLeft(10);
                        cell.getParagraphArray(0).setSpacingAfter(1);
                        cell.getParagraphArray(0).setSpacingBefore(1);
                        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                        t3.setCellMargins(20, 20, 20, 20);
                    }
                    rowN++;
                }

                // мониторинг
                XWPFParagraph p5 = doc.createParagraph();
                p5.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r5 = p5.createRun();
                r5.setFontFamily("Times New Roman");
                r5.setFontSize(12);
                r5.setBold(true);
                r5.setText("Мониторинг удовлетворенности услуг");
                XWPFTable t4 = doc.createTable();
                t4.createRow();
                List<StandartCount> monitList = o.getCountMonitList();
                i = 0;
                for (StandartCount monit : monitList) {
                    if (i != 0) {
                        t4.getRow(0).createCell();
                        t4.getRow(1).createCell();
                    }
                    t4.getRow(0).getCell(i).getParagraphArray(0).createRun().setText(monit.getTitle());
                    t4.getRow(1).getCell(i).getParagraphArray(0).createRun().setText(monit.getCount().toString());
                    i++;
                }

                List<XWPFTableRow> rows4 = t4.getRows();
                rowN = 0;
                for (XWPFTableRow row : rows4) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                        for (XWPFRun run : runs) {
                            run.setFontFamily("Times New Roman");
                            run.setFontSize(10);
                            if (rowN == 0) {
                                run.setBold(true);
                                cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                            }
                            cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setIndentFromLeft(10);
                        cell.getParagraphArray(0).setSpacingAfter(1);
                        cell.getParagraphArray(0).setSpacingBefore(1);
                        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                        t4.setCellMargins(20, 20, 20, 20);
                    }
                    rowN++;
                }

                // проблематика
                XWPFParagraph p6 = doc.createParagraph();
                p6.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r6 = p6.createRun();
                r6.setFontFamily("Times New Roman");
                r6.setFontSize(12);
                r6.setBold(true);
                r6.setText("Характеристика проблематики обращений на консультации");

                XWPFTable t5 = doc.createTable();
                t5.getRow(0).getCell(0).getParagraphArray(0).createRun().setText("№ по кодификатору обращений");
                t5.getRow(0).createCell().getParagraphArray(0).createRun().setText("Проблемы обращений");
                t5.getRow(0).createCell().getParagraphArray(0).createRun().setText("Кол-во обращений");

                List<CountProblemKod> countProblemList = o.getCountProblemList();
                i = 1;
                for (CountProblemKod pr : countProblemList) {
                    t5.createRow().getCell(0).getParagraphArray(0).createRun().setText(pr.getKod().toString());
                    t5.getRow(i).getCell(1).getParagraphArray(0).createRun().setText(pr.getProblem());
                    t5.getRow(i).getCell(2).getParagraphArray(0).createRun().setText(pr.getCount().toString());
                    i++;
                }

                List<XWPFTableRow> rows5 = t5.getRows();
                rowN = 0;
                for (XWPFTableRow row : rows5) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                        for (XWPFRun run : runs) {
                            run.setFontFamily("Times New Roman");
                            run.setFontSize(10);
                            if (rowN == 0) {
                                run.setBold(true);
                                cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                            }
                            cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setIndentFromLeft(10);
                        cell.getParagraphArray(0).setSpacingAfter(1);
                        cell.getParagraphArray(0).setSpacingBefore(1);
                        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                        t5.setCellMargins(20, 20, 20, 20);
                    }
                    rowN++;
                }
            }
        }

        for (Map.Entry<String, BigOtch> entry : konsMap.entrySet()) {
            k++;
            String key = entry.getKey();
            BigOtch value = entry.getValue();
            String title = "";
            String theme = "";
            String kateg = "";
            switch (key) {
                case "ped":
                    title = "Групповое психолого-педагогическое консультирование "
                            + "педагогических работников организаций, осуществляющих образовательную "
                            + "деятельность, охваченных методической помощью (консультированием) по "
                            + "обучению детей, испытывающих трудности в освоении основных общеобразовательных "
                            + "программ, развитии и социальной адаптации";
                    theme = "Тематика консультирования "
                            + "(с указанием формы проведения: семинары, тренинги, совещания, занятия по теме и др. "
                            + "с указанием категории педагогических работников)";
                    kateg = "Кол-во педагогических работников (чел.)";
                    break;
                case "parents":
                    title = "Групповое психолого-педагогическое консультирование родителей (законных представителей)";
                    theme = "Тематика консультирования (с указанием формы проведения: родительские собрания, лектории, "
                            + "занятия по теме и др.)";
                    kateg = "Количество родителей (чел.)";
                    break;
                case "children":
                    title = "Групповое психолого-педагогическое консультирование детей";
                    theme = "Тематика консультирования (с указанием формы проведения: родительские собрания, лектории, "
                            + "занятия по теме и др.)";
                    kateg = "Количество детей (чел.)";
                    break;
                default:
                    break;
            }

            XWPFParagraph p5 = doc.createParagraph();
            XWPFRun r5 = p5.createRun();
            r5.setText(k + ". " + title);
            r5.setBold(true);
            r5.setFontFamily("Times New Roman");
            r5.setFontSize(12);

            XWPFTable t4 = doc.createTable();
            t4.getRow(0).getCell(0).getParagraphArray(0).createRun().setText("Районы");
            t4.getRow(0).createCell().getParagraphArray(0).createRun().setText("Наименование организации, "
                    + "осуществляющей образовательную деятельность");
            t4.getRow(0).createCell().getParagraphArray(0).createRun().setText(theme);
            t4.getRow(0).createCell().getParagraphArray(0).createRun().setText("Дата");
            t4.getRow(0).createCell().getParagraphArray(0).createRun().setText(kateg);
            List<CountDate> countDateList = value.getCountDateList();
            for (CountDate c : countDateList) {
                XWPFTableRow row = t4.createRow();
                row.getCell(0).getParagraphArray(0).createRun().setText(c.getReg());
                row.getCell(3).getParagraphArray(0).createRun().setText(dateFormat.format(c.getDate()));
                row.getCell(4).getParagraphArray(0).createRun().setText(c.getCount().toString());
            }

            List<XWPFTableRow> rows4 = t4.getRows();
            int rowN = 0;
            for (XWPFTableRow row : rows4) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                    for (XWPFRun run : runs) {
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(10);
                        if (rowN == 0) {
                            run.setBold(true);
                            cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setIndentFromLeft(10);
                    cell.getParagraphArray(0).setSpacingAfter(1);
                    cell.getParagraphArray(0).setSpacingBefore(1);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    t4.setCellMargins(20, 20, 20, 20);
                }
                rowN++;
            }
        }

        k++;
        List<CountKatBig> countIndObsled = indObsled.getCountKatBigList();
        XWPFParagraph p6 = doc.createParagraph();
        XWPFRun r6 = p6.createRun();
        r6.setText(k + ". Индивидуальное психолого-медико-педагогическое обследование детей в условиях "
                + centerName);
        r6.setBold(true);
        r6.setFontFamily("Times New Roman");
        r6.setFontSize(12);

        XWPFTable t5 = doc.createTable();
        t5.getRow(0).createCell().getParagraphArray(0).createRun().setText("Количество детей (количество обращений)");
        t5.getRow(0).createCell().getParagraphArray(0).createRun().setText("Из них старше 14");
        t5.createRow().getCell(0).getParagraphArray(0).createRun().setText("Всего обращений");
        Integer sum = 0;
        Integer sum14 = 0;
        HashSet<String> regions = new HashSet<>();
        for (CountKatBig count : countIndObsled) {
            regions.add(count.getReg());
        }
        List<String> regList = new ArrayList<>();
        for (String rg : regions) {
            regList.add(rg);
        }
        Collections.sort(regList);
        int i = 1;
        for (String r : regList) {
            t5.createRow().getCell(0).getParagraphArray(0).createRun().setText(r);
            i++;
            for (CountKatBig c : countIndObsled) {
                if (c.getReg().equals(r)) {
                    if (c.getKat().equals("children")) {
                        t5.getRow(i).getCell(1).getParagraphArray(0).createRun().setText(c.getCount().toString());
                        sum += c.getCount();
                    } else if (c.getKat().equals("children14")) {
                        t5.getRow(i).getCell(2).getParagraphArray(0).createRun().setText(c.getCount().toString());
                        sum14 += c.getCount();
                    }
                }
            }
        }
        t5.getRow(1).getCell(1).getParagraphArray(0).createRun().setText(sum.toString());
        t5.getRow(1).getCell(2).getParagraphArray(0).createRun().setText(sum14.toString());

        List<XWPFTableRow> rows5 = t5.getRows();
        int rowN = 0;
        for (XWPFTableRow row : rows5) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                for (XWPFRun run : runs) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(10);
                    if (rowN == 0) {
                        run.setBold(true);
                        cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                }
                cell.getParagraphArray(0).setIndentFromLeft(10);
                cell.getParagraphArray(0).setSpacingAfter(1);
                cell.getParagraphArray(0).setSpacingBefore(1);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                t5.setCellMargins(20, 20, 20, 20);
            }
            rowN++;
        }

        // статусы
        if (!countIndObsled.isEmpty()) {
            XWPFParagraph p3 = doc.createParagraph();
            p3.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r3 = p3.createRun();
            r3.setFontFamily("Times New Roman");
            r3.setFontSize(12);
            r3.setBold(true);
            r3.setText("Характеристика статуса ребенка");

            XWPFTable t2 = doc.createTable();
            t2.createRow();

            List<CountStatus> countStatusList = indObsled.getCountStatusList();
            i = 0;
            for (CountStatus stat : countStatusList) {
                if (i != 0) {
                    t2.getRow(0).createCell();
                    t2.getRow(1).createCell();
                }
                t2.getRow(0).getCell(i).getParagraphArray(0).createRun().setText(stat.getStatus());
                t2.getRow(1).getCell(i).getParagraphArray(0).createRun().setText(stat.getCount().toString());
                i++;
            }

            List<XWPFTableRow> rows2 = t2.getRows();
            rowN = 0;
            for (XWPFTableRow row : rows2) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                    for (XWPFRun run : runs) {
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(10);
                        if (rowN == 0) {
                            run.setBold(true);
                            cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setIndentFromLeft(10);
                    cell.getParagraphArray(0).setSpacingAfter(1);
                    cell.getParagraphArray(0).setSpacingBefore(1);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    t2.setCellMargins(20, 20, 20, 20);
                }
                rowN++;
            }

            XWPFParagraph p4 = doc.createParagraph();
            p4.setSpacingBefore(1);
            p4.createRun().setText("Имеют 2 или больше характеристики: " + indObsled.getManyStatus().toString());
            XWPFRun r4 = p4.createRun();
            r4.setFontFamily("Times New Roman");
            r4.setFontSize(12);

            XWPFTable t3 = doc.createTable();
            t3.createRow();
            List<StandartCount> ageList = indObsled.getCountAgeList();
            i = 0;
            for (StandartCount age : ageList) {
                if (i != 0) {
                    t3.getRow(0).createCell();
                    t3.getRow(1).createCell();
                }
                t3.getRow(0).getCell(i).getParagraphArray(0).createRun().setText(age.getTitle());
                t3.getRow(1).getCell(i).getParagraphArray(0).createRun().setText(age.getCount().toString());
                i++;
            }

            List<XWPFTableRow> rows3 = t3.getRows();
            rowN = 0;
            for (XWPFTableRow row : rows3) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                    for (XWPFRun run : runs) {
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(10);
                        if (rowN == 0) {
                            run.setBold(true);
                            cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setIndentFromLeft(10);
                    cell.getParagraphArray(0).setSpacingAfter(1);
                    cell.getParagraphArray(0).setSpacingBefore(1);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    t3.setCellMargins(20, 20, 20, 20);
                }
                rowN++;
            }

            // проблематика
            XWPFParagraph p16 = doc.createParagraph();
            p16.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r16 = p16.createRun();
            r16.setFontFamily("Times New Roman");
            r16.setFontSize(12);
            r16.setBold(true);
            r16.setText("Характеристика проблематики обращений на консультации");

            XWPFTable t15 = doc.createTable();
            t15.getRow(0).getCell(0).getParagraphArray(0).createRun().setText("№ по кодификатору обращений");
            t15.getRow(0).createCell().getParagraphArray(0).createRun().setText("Проблемы обращений");
            t15.getRow(0).createCell().getParagraphArray(0).createRun().setText("Кол-во обращений");

            List<CountProblemKod> countProblemList = indObsled.getCountProblemList();
            i = 1;
            for (CountProblemKod pr : countProblemList) {
                t15.createRow().getCell(0).getParagraphArray(0).createRun().setText(pr.getKod().toString());
                t15.getRow(i).getCell(1).getParagraphArray(0).createRun().setText(pr.getProblem());
                t15.getRow(i).getCell(2).getParagraphArray(0).createRun().setText(pr.getCount().toString());
                i++;
            }

            List<XWPFTableRow> rows15 = t15.getRows();
            rowN = 0;
            for (XWPFTableRow row : rows15) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                    for (XWPFRun run : runs) {
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(10);
                        if (rowN == 0) {
                            run.setBold(true);
                            cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                        }
                        cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setIndentFromLeft(10);
                    cell.getParagraphArray(0).setSpacingAfter(1);
                    cell.getParagraphArray(0).setSpacingBefore(1);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    t15.setCellMargins(20, 20, 20, 20);
                }
                rowN++;
            }
        }

        k++;
        XWPFParagraph p7 = doc.createParagraph();
        XWPFRun r7 = p7.createRun();
        r7.setBold(true);
        r7.setFontFamily("Times New Roman");
        r7.setText(k + ". Участие в психолого-медико-педагогической комиссии (количество заседаний)");
        XWPFTable t7 = doc.createTable();
        t7.getRow(0).getCell(0).getParagraphArray(0).createRun().setText("Всего (количество заседаний)");
        t7.getRow(0).createCell();
        t7.createRow().getCell(0).getParagraphArray(0).createRun().setText("Всего в условиях Центра");
        t7.createRow().getCell(0).getParagraphArray(0).createRun().setText("Всего в районах");
        i = 2;
        Integer sumCenter = 0;
        Integer sumReg = 0;
        Integer sumA = 0;
        for (StandartCount s : countPmpk) {
            if (s.getTitle().equals(centerShortName)) {
                sumCenter += s.getCount();
            } else {
                sumReg += s.getCount();
                i++;
                t7.createRow().getCell(0).getParagraphArray(0).createRun().setText(s.getTitle());
                t7.getRow(i).getCell(1).getParagraphArray(0).createRun().setText(s.getCount().toString());
            }
            sumA += s.getCount();
        }
        t7.getRow(0).getCell(1).getParagraphArray(0).createRun().setText(sumA.toString());
        t7.getRow(1).getCell(1).getParagraphArray(0).createRun().setText(sumCenter.toString());
        t7.getRow(2).getCell(1).getParagraphArray(0).createRun().setText(sumReg.toString());

        List<XWPFTableRow> rows7 = t7.getRows();
        rowN = 0;
        for (XWPFTableRow row : rows7) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                for (XWPFRun run : runs) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(10);
                    if (rowN == 0) {
                        run.setBold(true);
                        cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                }
                cell.getParagraphArray(0).setIndentFromLeft(10);
                cell.getParagraphArray(0).setSpacingAfter(1);
                cell.getParagraphArray(0).setSpacingBefore(1);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                t7.setCellMargins(20, 20, 20, 20);
            }
            rowN++;
        }

        k++;
        XWPFParagraph p8 = doc.createParagraph();
        XWPFRun r8 = p8.createRun();
        r8.setBold(true);
        r8.setFontFamily("Times New Roman");
        r8.setText(k + ". Индивидуальное и групповое  психолого-медико-педагогическое обследование детей на "
                + "территориях, закрепленных за Учреждением");
        XWPFTable t8 = doc.createTable();
        t8.getRow(0).getCell(0).getParagraphArray(0).createRun().setText("Район");
        t8.getRow(0).createCell().getParagraphArray(0).createRun().setText("Наименование организации, "
                + "осуществляющей образовательную деятельность");
        t8.getRow(0).createCell().getParagraphArray(0).createRun().setText("Направленность");
        t8.getRow(0).createCell().getParagraphArray(0).createRun().setText("Форма (инд., групп.)");
        t8.getRow(0).createCell().getParagraphArray(0).createRun().setText("Дата");
        t8.getRow(0).createCell().getParagraphArray(0).createRun().setText("Кол-во чел.");
        i = 0;
        for (BigOtch o : obsledReg) {
            List<CountDate> count = o.getCountDateList();
            String usl = o.getUsl();
            for (CountDate c : count) {
                i++;
                t8.createRow().getCell(0).getParagraphArray(0).createRun().setText(c.getReg());
                t8.getRow(i).getCell(3).getParagraphArray(0).createRun().setText(usl);
                t8.getRow(i).getCell(4).getParagraphArray(0).createRun().setText(dateFormat.format(c.getDate()));
                t8.getRow(i).getCell(5).getParagraphArray(0).createRun().setText(c.getCount().toString());
            }
        }
        i++;
        t8.createRow().getCell(0).getParagraphArray(0).createRun().setText("Итого");
        Integer sumAll = 0;
        Integer sumAll14 = 0;
        for (BigOtch o : obsledRegAll) {
            List<StandartCount> countAgeList = o.getCountAgeList();
            for (StandartCount c : countAgeList) {
                if (c.getTitle().equals("юношеский возраст (14-18 лет)")) {
                    sumAll14 += c.getCount();
                }
                sumAll += c.getCount();
            }
        }
        t8.getRow(i).getCell(1).getParagraphArray(0).createRun().setText("Детей всего "
                + sumAll + " из них старше 14 лет " + sumAll14);

        List<XWPFTableRow> rows8 = t8.getRows();
        rowN = 0;
        for (XWPFTableRow row : rows8) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                for (XWPFRun run : runs) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(10);
                    if (rowN == 0) {
                        run.setBold(true);
                        cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                }
                cell.getParagraphArray(0).setIndentFromLeft(10);
                cell.getParagraphArray(0).setSpacingAfter(1);
                cell.getParagraphArray(0).setSpacingBefore(1);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                t8.setCellMargins(20, 20, 20, 20);
            }
            rowN++;
        }

        k++;
        XWPFParagraph p9 = doc.createParagraph();
        XWPFRun r9 = p9.createRun();
        r9.setBold(true);
        r9.setFontFamily("Times New Roman");
        r9.setText(k + ". Индивидуальные и групповые коррекционно-развивающие занятия с детьми");
        XWPFTable t9 = doc.createTable();
        t9.getRow(0).getCell(0).getParagraphArray(0).createRun().setText("Услуга");
        t9.getRow(0).createCell().getParagraphArray(0).createRun().setText("Кол-во детей");
        t9.createRow().getCell(0).getParagraphArray(0).createRun().setText(indKrz.getUsl());
        List<CountKatBig> inds = indKrz.getCountKatDistList();
        Integer indK = 0;
        for (CountKatBig ind : inds) {
            if (ind.getKat().equals("children")) {
                indK += ind.getCount();
            }
        }
        t9.getRow(1).getCell(1).getParagraphArray(0).createRun().setText(indK.toString());
        t9.createRow().getCell(0).getParagraphArray(0).createRun().setText(grKrz.getUsl());
        List<CountKatBig> grs = grKrz.getCountKatDistList();
        Integer grK = 0;
        for (CountKatBig gr : grs) {
            if (gr.getKat().equals("children")) {
                grK += gr.getCount();
            }
        }
        t9.getRow(2).getCell(1).getParagraphArray(0).createRun().setText(grK.toString());

        List<XWPFTableRow> rows9 = t9.getRows();
        rowN = 0;
        for (XWPFTableRow row : rows9) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                for (XWPFRun run : runs) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(10);
                    if (rowN == 0) {
                        run.setBold(true);
                        cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                }
                cell.getParagraphArray(0).setIndentFromLeft(10);
                cell.getParagraphArray(0).setSpacingAfter(1);
                cell.getParagraphArray(0).setSpacingBefore(1);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                t9.setCellMargins(20, 20, 20, 20);
            }
            rowN++;
        }

        // статусы индКРЗ
        XWPFParagraph p10 = doc.createParagraph();
        p10.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r10 = p10.createRun();
        r10.setFontFamily("Times New Roman");
        r10.setFontSize(12);
        r10.setBold(true);
        r10.setText("Характеристика статуса ребенка, получившего услугу индивидуального коррекционно-развивающего занятия");

        XWPFTable t10 = doc.createTable();
        t10.createRow();

        List<CountStatus> countStatusList = indKrz.getCountStatusList();
        i = 0;
        for (CountStatus stat : countStatusList) {
            if (i != 0) {
                t10.getRow(0).createCell();
                t10.getRow(1).createCell();
            }
            t10.getRow(0).getCell(i).getParagraphArray(0).createRun().setText(stat.getStatus());
            t10.getRow(1).getCell(i).getParagraphArray(0).createRun().setText(stat.getCount().toString());
            i++;
        }

        List<XWPFTableRow> rows10 = t10.getRows();
        rowN = 0;
        for (XWPFTableRow row : rows10) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                for (XWPFRun run : runs) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(10);
                    if (rowN == 0) {
                        run.setBold(true);
                        cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                }
                cell.getParagraphArray(0).setIndentFromLeft(10);
                cell.getParagraphArray(0).setSpacingAfter(1);
                cell.getParagraphArray(0).setSpacingBefore(1);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                t10.setCellMargins(20, 20, 20, 20);
            }
            rowN++;
        }
        XWPFParagraph p11 = doc.createParagraph();
        p11.setSpacingBefore(1);
        p11.createRun().setText("Имеют 2 или больше характеристики: " + indKrz.getManyStatus().toString());
        XWPFRun r11 = p11.createRun();
        r11.setFontFamily("Times New Roman");
        r11.setFontSize(12);

        // статусы грКРЗ
        XWPFParagraph p12 = doc.createParagraph();
        p12.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r12 = p12.createRun();
        r12.setFontFamily("Times New Roman");
        r12.setFontSize(12);
        r12.setBold(true);
        r12.setText("Характеристика статуса ребенка, получившего услугу группового коррекционно-развивающего занятия");

        XWPFTable t12 = doc.createTable();
        t12.createRow();

        List<CountStatus> countStatusList1 = grKrz.getCountStatusList();
        i = 0;
        for (CountStatus stat : countStatusList1) {
            if (i != 0) {
                t12.getRow(0).createCell();
                t12.getRow(1).createCell();
            }
            t12.getRow(0).getCell(i).getParagraphArray(0).createRun().setText(stat.getStatus());
            t12.getRow(1).getCell(i).getParagraphArray(0).createRun().setText(stat.getCount().toString());
            i++;
        }

        List<XWPFTableRow> rows12 = t12.getRows();
        rowN = 0;
        for (XWPFTableRow row : rows12) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFRun> runs = cell.getParagraphArray(0).getRuns();
                for (XWPFRun run : runs) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(10);
                    if (rowN == 0) {
                        run.setBold(true);
                        cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
                    }
                    cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                }
                cell.getParagraphArray(0).setIndentFromLeft(10);
                cell.getParagraphArray(0).setSpacingAfter(1);
                cell.getParagraphArray(0).setSpacingBefore(1);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                t12.setCellMargins(20, 20, 20, 20);
            }
            rowN++;
        }
        XWPFParagraph p13 = doc.createParagraph();
        p13.setSpacingBefore(1);
        p13.createRun().setText("Имеют 2 или больше характеристики: " + grKrz.getManyStatus().toString());
        XWPFRun r13 = p13.createRun();
        r13.setFontFamily("Times New Roman");
        r13.setFontSize(12);

        doc.write(os);
        doc.close();
    }

    public static void ipraPrikaz(ServletOutputStream os, Ipra18 ipra, Ipra18Prikaz prikaz)
            throws FileNotFoundException, IOException {
        Children child = ipra.getChildId();
        FIO fioDat = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String inFioDat = fioDat.changeToDat().getInicFam(); // инициалы + фамилия в дательном падеже
        String mseRod = ipra.getSprmseId().getSprmseNameRod(); // МСЭ в родительном падеже
        String mseDateFormat2 = ipra.getFormat2Date(ipra.getIpra18IshmseD());    // дата письма МСЭ
        String[] mseDateV = mseDateFormat2.split("\\.");
        String mseDate = mseDateV[0] + " " + monthNomToString(mseDateV[1]) + " " + mseDateV[2];
        String mseNom = ipra.getIpra18IshmseN(); // номер письма МСЭ
        String ipraNom = ipra.getIpra18N(); // номер ИПРА
        String expNom = ipra.getIpra18Nexp(); // номер протокола экспертизы
        String ipraDateFormat2 = ipra.getFormat2Date(ipra.getIpra18Dateexp());
        String[] ipraDateV = ipraDateFormat2.split("\\.");
        String ipraDate = ipraDateV[0] + " " + monthNomToString(ipraDateV[1]) + " " + ipraDateV[2]; // дата экспертизы
        String mseTv = ipra.getSprmseId().getSprmseNameTv(); // МСЭ в творительном падеже
        String fioDatFull = fioDat.getFullIOF();  // имя, отчество, фамилия полностью в дательном падеже
        String omsuIm = prikaz.getSpromsuId().getSpromsuName(); // ОМСУ в именительном падеже
        String otchCenterFormat2 = ipra.getFormat2Date(prikaz.getIpra18prikazOtchcenter());
        String[] otchCenterV = otchCenterFormat2.split("\\.");
        String otchCenter = otchCenterV[0] + " " + monthNomToString(otchCenterV[1]) + " " + otchCenterV[2]; // дата отчёта ОЦППМСП
        String omsuDat = prikaz.getSpromsuId().getSpromsuNameDat(); // ОМСУ в дательном падеже
        FIO chiefFio = new FIO(prikaz.getSpromsuId().getSpromsuChiefFam(), prikaz.getSpromsuId().getSpromsuChiefName(),
                prikaz.getSpromsuId().getSpromsuChiefPatr(), null);
        String omsuChiefIm = chiefFio.getInicFam(); // начальник ОМСУ в именительном падеже
        String otchOmsuFormat2 = ipra.getFormat2Date(prikaz.getIpra18prikazOtchomsu());
        String[] otchOmsuV = otchOmsuFormat2.split("\\.");
        String otchOmsu = otchOmsuV[0] + " " + monthNomToString(otchOmsuV[1]) + " " + otchOmsuV[2]; // дата отчёта ОМСУ
        FIO fioRod = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String fioRodFull = fioRod.changeToRod().getFullIOF();

        String path = "templates//Приказ.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            // Simply open the file and store a reference into the 'document' 
            // local variable. 
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();

        String font = "Times New Roman";
        insertAtBookmark(paraList, "in_fio_dat", inFioDat, 14, font, "");
        insertAtBookmark(paraList, "mse_rod", mseRod, 14, font, "");
        insertAtBookmark(paraList, "mse_date", mseDate, 14, font, "");
        insertAtBookmark(paraList, "mse_nom", mseNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_nom", ipraNom, 14, font, "");
        insertAtBookmark(paraList, "exp_nom", expNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_date", ipraDate, 14, font, "");
        insertAtBookmark(paraList, "mse_tv", mseTv, 14, font, "");
        insertAtBookmark(paraList, "fio_dat", fioDatFull, 14, font, "");
        insertAtBookmark(paraList, "omsu_im", omsuIm, 14, font, "");
        insertAtBookmark(paraList, "otch_center", otchCenter, 14, font, "");
        insertAtBookmark(paraList, "omsu_dat", omsuDat, 14, font, "");
        insertAtBookmark(paraList, "omsu_chief_im", omsuChiefIm, 14, font, "");
        insertAtBookmark(paraList, "otch_omsu", otchOmsu, 14, font, "");
        insertAtBookmark(paraList, "fio_rod", fioRodFull, 14, font, "");

        doc.write(os);
        doc.close();
    }

    public static void ipra18OtchetCenter(ServletOutputStream os, Ipra18 ipra, Ipra18Prikaz prikaz)
            throws FileNotFoundException, IOException {
        String ipraNom = "";
        try {
            ipraNom = ipra.getIpra18N();   // номер ИПРА
        } catch (Exception ex) {
        }
        String expNom = "";
        try {
            expNom = ipra.getIpra18Nexp();    // номер протокола экспертизы
        } catch (Exception ex) {
        }
        String ipraDate = "";
        try {
            ipraDate = ipra.getFormat2Date(ipra.getIpra18Dateexp()); // дата экспертизы
        } catch (Exception ex) {
        }
        Children child = ipra.getChildId();
        String fioIm = child.getFIO(); // ФИО в им.падеже полностью
        String drFormat2 = ipra.getFormat2Date(child.getChildDr());    // дата рождения
        String[] drV = drFormat2.split("\\.");
        String day = drV[0];   // день рождения
        String month = monthNomToImString(drV[1]); // месяц рождения
        String year = drV[2];   // год рождения
        Date date = prikaz.getIpra18prikazOtchcenter();   // дата отчёта
        String age = child.getAgeOnDate(date).toString();   // возраст
        String region = ""; // район
        String town = "";   // город
        if (child.getSprregId().getSprregIstown() == 0) {
            region = child.getSprregId().getSprregPrintname();
        } else if (child.getSprregId().getSprregIstown() == 1) {
            town = child.getSprregId().getSprregPrintname();
        }
        String dateFormat2 = ipra.getFormat2Date(date);
        String[] dateV = dateFormat2.split("\\.");
        String y = dateV[2].substring(2);   // год (2 цифры)

        String path = "templates//Отчет ОЦППМСП.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            // Simply open the file and store a reference into the 'document' 
            // local variable. 
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();
        List<XWPFTable> tableList = null;
        Iterator<XWPFTable> tableIter = null;
        List<XWPFTableRow> rowList = null;
        Iterator<XWPFTableRow> rowIter = null;
        List<XWPFTableCell> cellList = null;
        Iterator<XWPFTableCell> cellIter = null;
        XWPFTable table = null;
        XWPFTableRow row = null;
        XWPFTableCell cell = null;

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "ipra_nom", ipraNom, 14, font, "");
        insertAtBookmark(paraList, "exp_nom", expNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_date", ipraDate, 14, font, "");
        insertAtBookmark(paraList, "fio_im", fioIm, 14, font, "");
        insertAtBookmark(paraList, "day", day, 14, font, "");
        insertAtBookmark(paraList, "month", month, 14, font, "");
        insertAtBookmark(paraList, "year", year, 14, font, "");
        insertAtBookmark(paraList, "age", age, 14, font, "");
        insertAtBookmark(paraList, "region", region, 14, font, "");
        insertAtBookmark(paraList, "town", town, 14, font, "");
        insertAtBookmark(paraList, "y", y, 14, font, "");

        // вставляем в закладки в таблицах
        tableList = doc.getTables();
        tableIter = tableList.iterator();
        while (tableIter.hasNext()) {
            table = tableIter.next();
            rowList = table.getRows();
            rowIter = rowList.iterator();
            while (rowIter.hasNext()) {
                row = rowIter.next();
                cellList = row.getTableCells();
                cellIter = cellList.iterator();
                while (cellIter.hasNext()) {
                    cell = cellIter.next();
                    insertAtBookmark(cell.getParagraphs(), "ipra_nom", ipraNom, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "exp_nom", expNom, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "ipra_date", ipraDate, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "fio_im", fioIm, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "day", day, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "month", month, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "year", year, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "age", age, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "region", region, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "town", town, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "y", y, 14, font, "");
                }
            }
        }

        doc.write(os);
        doc.close();
    }

    public static void ipraOtchetCenter(ServletOutputStream os, Ipra ipra)
            throws FileNotFoundException, IOException {
        String ipraNom = "";
        try {
            ipraNom = ipra.getIpraN();   // номер ИПРА
        } catch (Exception ex) {
        }
        String expNom = "";
        try {
            expNom = ipra.getIpraNexp();    // номер протокола экспертизы
        } catch (Exception ex) {
        }
        String ipraDate = "";
        try {
            ipraDate = ipra.getFormat2Date(ipra.getIpraDateexp()); // дата экспертизы
        } catch (Exception ex) {
        }
        Children child = ipra.getChildId();
        String fioIm = child.getFIO(); // ФИО в им.падеже полностью
        String drFormat2 = ipra.getFormat2Date(child.getChildDr());    // дата рождения
        String[] drV = drFormat2.split("\\.");
        String day = drV[0];   // день рождения
        String month = monthNomToImString(drV[1]); // месяц рождения
        String year = drV[2];   // год рождения
        Date date = ipra.getIpraOtchcenter();   // дата отчёта
        String age = child.getAgeOnDate(date).toString();   // возраст
        String region = ""; // район
        String town = "";   // город
        if (child.getSprregId().getSprregIstown() == 0) {
            region = child.getSprregId().getSprregPrintname();
        } else if (child.getSprregId().getSprregIstown() == 1) {
            town = child.getSprregId().getSprregPrintname();
        }
        String dateFormat2 = ipra.getFormat2Date(date);
        String[] dateV = dateFormat2.split("\\.");
        String d = dateV[0];   // день
        String m = monthNomToString(dateV[1]); // месяц
        String y = dateV[2].substring(2);   // год (2 цифры)

        String path = "templates//Отчет ОЦППМСП.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            // Simply open the file and store a reference into the 'document' 
            // local variable. 
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();
        List<XWPFTable> tableList = null;
        Iterator<XWPFTable> tableIter = null;
        List<XWPFTableRow> rowList = null;
        Iterator<XWPFTableRow> rowIter = null;
        List<XWPFTableCell> cellList = null;
        Iterator<XWPFTableCell> cellIter = null;
        XWPFTable table = null;
        XWPFTableRow row = null;
        XWPFTableCell cell = null;

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "ipra_nom", ipraNom, 14, font, "");
        insertAtBookmark(paraList, "exp_nom", expNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_date", ipraDate, 14, font, "");
        insertAtBookmark(paraList, "fio_im", fioIm, 14, font, "");
        insertAtBookmark(paraList, "day", day, 14, font, "");
        insertAtBookmark(paraList, "month", month, 14, font, "");
        insertAtBookmark(paraList, "year", year, 14, font, "");
        insertAtBookmark(paraList, "age", age, 14, font, "");
        insertAtBookmark(paraList, "region", region, 14, font, "");
        insertAtBookmark(paraList, "town", town, 14, font, "");
        insertAtBookmark(paraList, "d", d, 14, font, "");
        insertAtBookmark(paraList, "m", m, 14, font, "");
        insertAtBookmark(paraList, "y", y, 14, font, "");

        // вставляем в закладки в таблицах
        tableList = doc.getTables();
        tableIter = tableList.iterator();
        while (tableIter.hasNext()) {
            table = tableIter.next();
            rowList = table.getRows();
            rowIter = rowList.iterator();
            while (rowIter.hasNext()) {
                row = rowIter.next();
                cellList = row.getTableCells();
                cellIter = cellList.iterator();
                while (cellIter.hasNext()) {
                    cell = cellIter.next();
                    insertAtBookmark(cell.getParagraphs(), "ipra_nom", ipraNom, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "exp_nom", expNom, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "ipra_date", ipraDate, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "fio_im", fioIm, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "day", day, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "month", month, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "year", year, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "age", age, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "region", region, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "town", town, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "d", d, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "m", m, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "y", y, 14, font, "");
                }
            }
        }

        doc.write(os);
        doc.close();
    }

    public static void ipra18LetterToDO(ServletOutputStream os, Ipra18 ipra, Ipra18Prikaz prikaz, Users user)
            throws FileNotFoundException, IOException {
        Children child = ipra.getChildId();
        FIO fioRod = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String inFioRod = fioRod.changeToRod().getFamInic(); // фамилия + инициалы в родильном падеже        
        String prDateFormat2 = ipra.getFormat2Date(prikaz.getIpra18prikazDoD());    // дата приказа ДО
        String[] prDateV = prDateFormat2.split("\\.");
        String prDate = prDateV[0] + " " + monthNomToString(prDateV[1]) + " " + prDateV[2];
        String prNom = "";
        try {
            prNom = prikaz.getIpra18prikazDoN();
        } catch (Exception ex) {
        }
        String fioRodS = fioRod.getFullFIO(); //фамилия, имя, отчество полностью в родительном падеже
        FIO sotr = new FIO(user.getSotrudId().getSotrudFam(), user.getSotrudId().getSotrudName(),
                user.getSotrudId().getSotrudPatr(), null);
        
        String pD = ""; // дата письма
        try {
            pD = ipra.getFormat2Date(prikaz.getIpra18prikazOtchcenter());
        } catch (Exception ex) {
        }
        String pN = "";   // номер письма
        try {
            pN = prikaz.getIpra18prikazOtchcenterN();
        } catch (Exception ex) {
        }

        String path = "templates//Сопроводительное в ДО.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "in_fio_rod", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "pr_date", prDate, 14, font, "");
        insertAtBookmark(paraList, "pr_nom", prNom, 14, font, "");
        insertAtBookmark(paraList, "in_fio_rod2", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "fio_rod", fioRodS, 14, font, "");
        insertAtBookmark(paraList, "sort_iof_in", sotr.getInicFam(), 12, font, "");
        
        // вставляем в закладки в TextBox (надпись)
        insertAtBookmarkAtTextbox(paraList, "pnomdate", pD + " № " + pN, 8, "Arial", "underline");

        doc.write(os);
        doc.close();
    }

    public static void ipraLetterToDo(ServletOutputStream os, Ipra ipra, Users user)
            throws FileNotFoundException, IOException {
        Children child = ipra.getChildId();
        FIO fioRod = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String inFioRod = fioRod.changeToRod().getFamInic(); // фамилия + инициалы в родильном падеже        
        String prDateFormat2 = ipra.getFormat2Date(ipra.getIpraPrikazD());    // дата приказа ДО
        String[] prDateV = prDateFormat2.split("\\.");
        String prDate = prDateV[0] + " " + monthNomToString(prDateV[1]) + " " + prDateV[2];
        String prNom = "";
        try {
            prNom = ipra.getIpraPrikazN();
        } catch (Exception ex) {
        }
        String fioRodS = fioRod.getFullFIO(); //фамилия, имя, отчество полностью в родильном падеже
        FIO sotr = new FIO(user.getSotrudId().getSotrudFam(), user.getSotrudId().getSotrudName(),
                user.getSotrudId().getSotrudPatr(), null);
        
        String pD = ""; // дата письма
        try {
            pD = ipra.getFormat2Date(ipra.getIpraOtchcenter());
        } catch (Exception ex) {
        }
        String pN = "";   // номер письма
        try {
            pN = ipra.getIpraOtchcenterN();
        } catch (Exception ex) {
        }

        String path = "templates//Сопроводительное в ДО.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "in_fio_rod", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "pr_date", prDate, 14, font, "");
        insertAtBookmark(paraList, "pr_nom", prNom, 14, font, "");
        insertAtBookmark(paraList, "in_fio_rod2", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "fio_rod", fioRodS, 14, font, "");
        insertAtBookmark(paraList, "sort_iof_in", sotr.getInicFam(), 12, font, "");
        
        // вставляем в закладки в TextBox (надпись)
        insertAtBookmarkAtTextbox(paraList, "pnomdate", pD + " № " + pN, 8, "Arial", "underline");

        doc.write(os);
        doc.close();
    }

    public static void ipra18OtchetDO(ServletOutputStream os, Ipra18 ipra, Ipra18Prikaz prikaz)
            throws FileNotFoundException, IOException {
        String ipraNom = "";
        try {
            ipraNom = ipra.getIpra18N();   // номер ИПРА
        } catch (Exception ex) {
        }
        String expNom = "";
        try {
            expNom = ipra.getIpra18Nexp();    // номер протокола экспертизы
        } catch (Exception ex) {
        }
        String ipraDate = "";
        String iD = "";
        String iM = "";
        String iY = "";
        try {
            ipraDate = ipra.getFormat2Date(ipra.getIpra18Dateexp()); // дата экспертизы
            String[] dateV = ipraDate.split("\\.");
            iD = dateV[0];   // день
            iM = monthNomToString(dateV[1]); // месяц
            iY = dateV[2].substring(2);   // год (2 цифры)
        } catch (Exception ex) {
        }
        Children child = ipra.getChildId();
        String fioIm = child.getFIO(); // ФИО в им.падеже полностью
        String drFormat2 = ipra.getFormat2Date(child.getChildDr());    // дата рождения
        String[] drV = drFormat2.split("\\.");
        String drD = drV[0];   // день рождения
        String drM = monthNomToImString(drV[1]); // месяц рождения
        String drY = drV[2];   // год рождения
        Date date = ipra.getIpra18Otchdo();   // дата отчёта
        String age = child.getAgeOnDate(date).toString();   // возраст
        String region = ""; // район
        String town = "";   // город
        if (child.getSprregId().getSprregIstown() == 0) {
            region = child.getSprregId().getSprregPrintname();
        } else if (child.getSprregId().getSprregIstown() == 1) {
            town = child.getSprregId().getSprregPrintname();
        }
        String datek = ipra.getFormat2Date(ipra.getIpra18Dateok());  // дата исполнения мероприятий
        String dateFormat2 = ipra.getFormat2Date(date);
        String[] dateV = dateFormat2.split("\\.");
        String y = dateV[2].substring(2);   // год (2 цифры)

        String path = "templates//Отчет ДО.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();
        List<XWPFTable> tableList = null;
        Iterator<XWPFTable> tableIter = null;
        List<XWPFTableRow> rowList = null;
        Iterator<XWPFTableRow> rowIter = null;
        List<XWPFTableCell> cellList = null;
        Iterator<XWPFTableCell> cellIter = null;
        XWPFTable table = null;
        XWPFTableRow row = null;
        XWPFTableCell cell = null;

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "ipra_nom", ipraNom, 11, font, "");
        insertAtBookmark(paraList, "exp_nom", expNom, 11, font, "");
        insertAtBookmark(paraList, "i_d", iD, 11, font, "");
        insertAtBookmark(paraList, "i_m", iM, 11, font, "");
        insertAtBookmark(paraList, "i_y", iY, 11, font, "");
        insertAtBookmark(paraList, "fio_im", fioIm, 10, font, "");
        insertAtBookmark(paraList, "dr_d", drD, 10, font, "");
        insertAtBookmark(paraList, "dr_m", drM, 10, font, "");
        insertAtBookmark(paraList, "dr_y", drY, 10, font, "");
        insertAtBookmark(paraList, "age", age, 10, font, "");
        insertAtBookmark(paraList, "region", region, 10, font, "");
        insertAtBookmark(paraList, "town", town, 10, font, "");
        insertAtBookmark(paraList, "ipra_datek", datek, 11, font, "");
        insertAtBookmark(paraList, "ipra_datek2", datek, 11, font, "");
        insertAtBookmark(paraList, "y", y, 12, font, "");

        // вставляем в закладки в таблицах
        tableList = doc.getTables();
        tableIter = tableList.iterator();
        while (tableIter.hasNext()) {
            table = tableIter.next();
            rowList = table.getRows();
            rowIter = rowList.iterator();
            while (rowIter.hasNext()) {
                row = rowIter.next();
                cellList = row.getTableCells();
                cellIter = cellList.iterator();
                while (cellIter.hasNext()) {
                    cell = cellIter.next();
                    insertAtBookmark(cell.getParagraphs(), "ipra_nom", ipraNom, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "exp_nom", expNom, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "i_d", iD, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "i_m", iM, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "i_y", iY, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "fio_im", fioIm, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "dr_d", drD, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "dr_m", drM, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "dr_y", drY, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "age", age, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "region", region, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "town", town, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "ipra_datek", datek, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "ipra_datek2", datek, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "y", y, 12, font, "");
                }
            }
        }

        doc.write(os);
        doc.close();
    }

    public static void ipraOtchetDO(ServletOutputStream os, Ipra ipra)
            throws FileNotFoundException, IOException {
        String ipraNom = "";
        try {
            ipraNom = ipra.getIpraN();   // номер ИПРА
        } catch (Exception ex) {
        }
        String expNom = "";
        try {
            expNom = ipra.getIpraNexp();    // номер протокола экспертизы
        } catch (Exception ex) {
        }
        String ipraDate = "";
        String iD = "";
        String iM = "";
        String iY = "";
        try {
            ipraDate = ipra.getFormat2Date(ipra.getIpraDateexp()); // дата экспертизы
            String[] dateV = ipraDate.split("\\.");
            iD = dateV[0];   // день
            iM = monthNomToString(dateV[1]); // месяц
            iY = dateV[2].substring(2);   // год (2 цифры)
        } catch (Exception ex) {
        }
        Children child = ipra.getChildId();
        String fioIm = child.getFIO(); // ФИО в им.падеже полностью
        String drFormat2 = ipra.getFormat2Date(child.getChildDr());    // дата рождения
        String[] drV = drFormat2.split("\\.");
        String drD = drV[0];   // день рождения
        String drM = monthNomToImString(drV[1]); // месяц рождения
        String drY = drV[2];   // год рождения
        Date date = ipra.getIpraOtchdo();   // дата отчёта
        String age = child.getAgeOnDate(date).toString();   // возраст
        String region = ""; // район
        String town = "";   // город
        if (child.getSprregId().getSprregIstown() == 0) {
            region = child.getSprregId().getSprregPrintname();
        } else if (child.getSprregId().getSprregIstown() == 1) {
            town = child.getSprregId().getSprregPrintname();
        }
        String datek = ipra.getFormat2Date(ipra.getIpraDateok());  // дата исполнения мероприятий
        String dateFormat2 = ipra.getFormat2Date(date);
        String[] dateV = dateFormat2.split("\\.");
        String y = dateV[2].substring(2);   // год (2 цифры)

        String path = "templates//Отчет ДО.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();
        List<XWPFTable> tableList = null;
        Iterator<XWPFTable> tableIter = null;
        List<XWPFTableRow> rowList = null;
        Iterator<XWPFTableRow> rowIter = null;
        List<XWPFTableCell> cellList = null;
        Iterator<XWPFTableCell> cellIter = null;
        XWPFTable table = null;
        XWPFTableRow row = null;
        XWPFTableCell cell = null;

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "ipra_nom", ipraNom, 11, font, "");
        insertAtBookmark(paraList, "exp_nom", expNom, 11, font, "");
        insertAtBookmark(paraList, "i_d", iD, 11, font, "");
        insertAtBookmark(paraList, "i_m", iM, 11, font, "");
        insertAtBookmark(paraList, "i_y", iY, 11, font, "");
        insertAtBookmark(paraList, "fio_im", fioIm, 10, font, "");
        insertAtBookmark(paraList, "dr_d", drD, 10, font, "");
        insertAtBookmark(paraList, "dr_m", drM, 10, font, "");
        insertAtBookmark(paraList, "dr_y", drY, 10, font, "");
        insertAtBookmark(paraList, "age", age, 10, font, "");
        insertAtBookmark(paraList, "region", region, 10, font, "");
        insertAtBookmark(paraList, "town", town, 10, font, "");
        insertAtBookmark(paraList, "ipra_datek", datek, 11, font, "");
        insertAtBookmark(paraList, "ipra_datek2", datek, 11, font, "");
        insertAtBookmark(paraList, "y", y, 12, font, "");

        // вставляем в закладки в таблицах
        tableList = doc.getTables();
        tableIter = tableList.iterator();
        while (tableIter.hasNext()) {
            table = tableIter.next();
            rowList = table.getRows();
            rowIter = rowList.iterator();
            while (rowIter.hasNext()) {
                row = rowIter.next();
                cellList = row.getTableCells();
                cellIter = cellList.iterator();
                while (cellIter.hasNext()) {
                    cell = cellIter.next();
                    insertAtBookmark(cell.getParagraphs(), "ipra_nom", ipraNom, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "exp_nom", expNom, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "i_d", iD, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "i_m", iM, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "i_y", iY, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "fio_im", fioIm, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "dr_d", drD, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "dr_m", drM, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "dr_y", drY, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "age", age, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "region", region, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "town", town, 10, font, "");
                    insertAtBookmark(cell.getParagraphs(), "ipra_datek", datek, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "ipra_datek2", datek, 11, font, "");
                    insertAtBookmark(cell.getParagraphs(), "y", y, 12, font, "");
                }
            }
        }

        doc.write(os);
        doc.close();
    }

    public static void ipra18LetterToMSE(ServletOutputStream os, Ipra18 ipra, Ipra18Prikaz prikaz)
            throws FileNotFoundException, IOException {
        Children child = ipra.getChildId();
        FIO fioRod = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String inFioRod = fioRod.changeToRod().getFamInic(); // фамилия + инициалы в родильном падеже
        String mseRod = ipra.getSprmseId().getSprmseNameRod();  // МСЭ в родительном падеже
        String mseDateFormat2 = ipra.getFormat2Date(ipra.getIpra18IshmseD());
        String[] mseDateV = mseDateFormat2.split("\\.");
        String mseDate = mseDateV[0] + " " + monthNomToString(mseDateV[1]) + " " + mseDateV[2]; // дата письма МСЭ
        String mseNom = ""; // номер письма МСЭ
        try {
            mseNom = ipra.getIpra18IshmseN();
        } catch (Exception ex) {
        }
        String ipraNom = ipra.getIpra18N(); // номер ИПРА
        String expNom = ipra.getIpra18Nexp(); // номер протокола экспертизы
        String ipraDateFormat2 = ipra.getFormat2Date(ipra.getIpra18Dateexp());
        String[] ipraDateV = ipraDateFormat2.split("\\.");
        String ipraDate = ipraDateV[0] + " " + monthNomToString(ipraDateV[1]) + " " + ipraDateV[2]; // дата экспертизы
        String mseTv = ipra.getSprmseId().getSprmseNameTv(); // МСЭ в творительном падеже
        String fioRodS = fioRod.getFullFIO(); //фамилия, имя, отчество полностью в родильном падеже
        String drFormat2 = ipra.getFormat2Date(child.getChildDr());
        String[] drV = drFormat2.split("\\.");
        String dr = drV[0] + " " + monthNomToString(drV[1]) + " " + drV[2]; // дата рождения

        String prDateFormat2 = ipra.getFormat2Date(prikaz.getIpra18prikazDoD());
        String[] prDateV = prDateFormat2.split("\\.");
        String prDate = prDateV[0] + " " + monthNomToString(prDateV[1]) + " " + prDateV[2]; // дата приказа ДО
        String prNom = "";  // номер приказа ДО
        try {
            prNom = prikaz.getIpra18prikazDoN();
        } catch (Exception ex) {
        }

        String path = "templates//Сопроводительное в МСЭ.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "in_fio_rod1", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "mse_rod", mseRod, 14, font, "");
        insertAtBookmark(paraList, "mse_date", mseDate, 14, font, "");
        insertAtBookmark(paraList, "mse_nom", mseNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_nom", ipraNom, 14, font, "");
        insertAtBookmark(paraList, "exp_nom", expNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_date", ipraDate, 14, font, "");
        insertAtBookmark(paraList, "mse_tv", mseTv, 14, font, "");
        insertAtBookmark(paraList, "fio_rod", fioRodS, 14, font, "");
        insertAtBookmark(paraList, "dr", dr, 14, font, "");
        insertAtBookmark(paraList, "pr_date", prDate, 14, font, "");
        insertAtBookmark(paraList, "pr_nom", prNom, 14, font, "");
        insertAtBookmark(paraList, "in_fio_rod2", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "in_fio_rod3", inFioRod, 14, font, "");

        doc.write(os);
        doc.close();
    }

    public static void ipraLetterToMSE(ServletOutputStream os, Ipra ipra)
            throws FileNotFoundException, IOException {
        Children child = ipra.getChildId();
        FIO fioRod = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String inFioRod = fioRod.changeToRod().getFamInic(); // фамилия + инициалы в родильном падеже
        String mseRod = "бюро №  – филиал ФКУ «ГБ МСЭ по Вологодской  области»";  // МСЭ в родительном падеже
        String mseDate = ""; // дата письма МСЭ
        try {
            String mseDateFormat2 = ipra.getFormat2Date(ipra.getIpraIshmseD());
            String[] mseDateV = mseDateFormat2.split("\\.");
            mseDate = mseDateV[0] + " " + monthNomToString(mseDateV[1]) + " " + mseDateV[2];
        } catch (Exception ex) {
        }
        String mseNom = ""; // номер письма МСЭ
        try {
            mseNom = ipra.getIpraIshmseN();
        } catch (Exception ex) {
        }
        String ipraNom = ""; // номер ИПРА
        try {
            ipraNom = ipra.getIpraN();
        } catch (Exception ex) {
        }
        String expNom = ""; // номер протокола экспертизы
        try {
            expNom = ipra.getIpraNexp();
        } catch (Exception ex) {
        }
        String ipraDate = ""; // дата экспертизы
        try {
            String ipraDateFormat2 = ipra.getFormat2Date(ipra.getIpraDateexp());
            String[] ipraDateV = ipraDateFormat2.split("\\.");
            ipraDate = ipraDateV[0] + " " + monthNomToString(ipraDateV[1]) + " " + ipraDateV[2];
        } catch (Exception ex) {
        }
        String mseTv = "бюро №  – филиал ФКУ «ГБ МСЭ по Вологодской  области»"; // МСЭ в творительном падеже
        String fioRodS = fioRod.getFullFIO(); //фамилия, имя, отчество полностью в родильном падеже
        String drFormat2 = ipra.getFormat2Date(child.getChildDr());
        String[] drV = drFormat2.split("\\.");
        String dr = drV[0] + " " + monthNomToString(drV[1]) + " " + drV[2]; // дата рождения

        String prDateFormat2 = ipra.getFormat2Date(ipra.getIpraPrikazD());
        String[] prDateV = prDateFormat2.split("\\.");
        String prDate = prDateV[0] + " " + monthNomToString(prDateV[1]) + " " + prDateV[2]; // дата приказа ДО
        String prNom = "";  // номер приказа ДО
        try {
            prNom = ipra.getIpraPrikazN();
        } catch (Exception ex) {
        }

        String path = "templates//Сопроводительное в МСЭ.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "in_fio_rod1", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "mse_rod", mseRod, 14, font, "");
        insertAtBookmark(paraList, "mse_date", mseDate, 14, font, "");
        insertAtBookmark(paraList, "mse_nom", mseNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_nom", ipraNom, 14, font, "");
        insertAtBookmark(paraList, "exp_nom", expNom, 14, font, "");
        insertAtBookmark(paraList, "ipra_date", ipraDate, 14, font, "");
        insertAtBookmark(paraList, "mse_tv", mseTv, 14, font, "");
        insertAtBookmark(paraList, "fio_rod", fioRodS, 14, font, "");
        insertAtBookmark(paraList, "dr", dr, 14, font, "");
        insertAtBookmark(paraList, "pr_date", prDate, 14, font, "");
        insertAtBookmark(paraList, "pr_nom", prNom, 14, font, "");
        insertAtBookmark(paraList, "in_fio_rod2", inFioRod, 14, font, "");
        insertAtBookmark(paraList, "in_fio_rod3", inFioRod, 14, font, "");

        doc.write(os);
        doc.close();
    }

    public static void printIpra18ReqTpmpk(ServletOutputStream os, List<Children> children, 
            List<SprOmsu> omsuList, SprOtherPmpk tpmpk, Date date, String nomer)
            throws FileNotFoundException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String reqDate = dateFormat.format(date);
        FIO chief_fio = new FIO(tpmpk.getSprotherpmpkChiefFam(), tpmpk.getSprotherpmpkChiefName(), 
                tpmpk.getSprotherpmpkChiefPatr(), null);
        String chief_dat = chief_fio.changeToDat().getInicFam();
        List<String> childrenList = new ArrayList();
        Children lastChild = children.get(children.size() - 1);
        for (Children child : children) {
            FIO fio = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
            fio.changeToRod();
            String reg = child.getSprregId().getSprregName();
            if (child.getSprregId().getSprregIstown() == 0) {
                reg += " р-н";
            }
            String str = "- " + fio.getFullFIO() + " (" + child.getFormat2Dr() + "г.р., " + reg + ")";
            if (child == lastChild) {
                str += ".";
            } else {
                str += ";";
            }
            childrenList.add(str);
        }

        String omsu = "";
        for (SprOmsu sprOmsu : omsuList) {
            if (omsu.equals("")) {
                omsu += sprOmsu.getSpromsuNameRod();
            } else {
                omsu += ", " + sprOmsu.getSpromsuNameRod();
            }
        }
        
        String path = "templates//Запрос в ТПМПК.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "tpmpk_name_rod", tpmpk.getSprotherpmpkNameRod(), 14, font, "");
        insertAtBookmark(paraList, "tpmpk_shname", tpmpk.getSprotherpmpkShname(), 14, font, "");
        insertAtBookmark(paraList, "chief_dat", chief_dat, 14, font, "");
        insertAtBookmark(paraList, "chief_io", tpmpk.getSprotherpmpkChiefName() + " " + tpmpk.getSprotherpmpkChiefPatr(), 14, font, "");
        insertAtBookmark(paraList, "omsu", omsu, 14, font, "");
        insertAtBookmark(paraList, "tpmpk_shname2", tpmpk.getSprotherpmpkShname(), 14, font, "");
        multiInsertAtBookmark(paraList, "spisok", childrenList, 14);
        
        // вставляем в закладки в TextBox (надпись)
        insertAtBookmarkAtTextbox(paraList, "pnomdate", reqDate + " № " + nomer, 8, "Arial", "underline");

        doc.write(os);
        doc.close();
    }

    public static void ipraPerechen(ServletOutputStream os, Ipra18 ipra, Ipra18Prikaz prikaz, List<IpraEduCondition> conditions,
            Users user)
            throws FileNotFoundException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String chiefDolgnDat = prikaz.getSpromsuId().getSpromsuChiefDolgnDat(); // должность в дательном падеже
        String omsuRod = prikaz.getSpromsuId().getSpromsuNameRod(); // ОМСУ в родительном падеже
        FIO chiefFio = new FIO(prikaz.getSpromsuId().getSpromsuChiefFam(), prikaz.getSpromsuId().getSpromsuChiefName(),
                prikaz.getSpromsuId().getSpromsuChiefPatr(), null);
        String omsuChiefIofIn = chiefFio.changeToDat().getInicFam();  // ФИО начальника ОМСУ дат.падеж
        String reqD = ""; // дата запроса от ОМСУ
        try {
            reqD = ipra.getFormat2Date(prikaz.getIpra18prikazReqD());
        } catch (Exception ex) {
        }
        String reqN = "";   // номер запроса от ОМСУ
        try {
            reqN = prikaz.getIpra18prikazReqN();
        } catch (Exception ex) {
        }
        String pD = ""; // дата перечня
        try {
            pD = ipra.getFormat2Date(prikaz.getIpra18prikazPerechD());
        } catch (Exception ex) {
        }
        String pN = "";   // номер перечня
        try {
            pN = prikaz.getIpra18prikazPerechN();
        } catch (Exception ex) {
        }
        Children child = ipra.getChildId();
        FIO fioRod = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String iofInRod = fioRod.changeToRod().getInicFam();    // ФИО инициалы ребенка в род.падеже        
        String omsuChiefIo = prikaz.getSpromsuId().getSpromsuChiefName() + " "
                + prikaz.getSpromsuId().getSpromsuChiefPatr();  // ИО начальника ОМСУ
        String fioRodFull = fioRod.getFullFIO();    // полное ФИО ребёнка в родительном падеже
        FIO fioVin = new FIO(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildPol());
        String iofInVin = fioVin.changeToVin().getInicFam();    // ФИО инициалы ребёнка в винительном падеже
        FIO sotr = new FIO(user.getSotrudId().getSotrudFam(), user.getSotrudId().getSotrudName(),
                user.getSotrudId().getSotrudPatr(), null);
        String sotrIofIn = sotr.getInicFam();   // ФИО исполнителя
        String dr = ""; // дата рождения
        try {
            String drFormat2 = child.getFormat2Dr();
            String[] drV = drFormat2.split("\\.");
            dr = drV[0] + " " + monthNomToString(drV[1]) + " " + drV[2];
        } catch (Exception ex) {
        }

        String ipraD = ""; // дата ИПРА
        try {
            String ipraDFormat2 = ipra.getFormat2Date(ipra.getIpra18Dateexp());
            String[] ipraDV = ipraDFormat2.split("\\.");
            ipraD = ipraDV[0] + " " + monthNomToString(ipraDV[1]) + " " + ipraDV[2];
        } catch (Exception ex) {
        }
        String ipraNom = ipra.getIpra18N(); // номер ИПРА
        String expNom = ipra.getIpra18Nexp();   // номер протокола экспертизы       

        Map<String[], List<String>> conditionsMap = new HashMap<>();  // условия обучения
        String isp = "Образовательная организация";
        Set<String[]> keySet = new HashSet<>();
        for (IpraEduCondition c : conditions) {
            String[] keys = {c.getIpraeducondtypeId().getIpraeducondtypeName(), isp, "До " + c.getFormat2Date()};
            keySet.add(keys);
        }

        for (String[] key : keySet) {
            conditionsMap.put(key, new ArrayList<>());
        }
        for (Map.Entry<String[], List<String>> entry : conditionsMap.entrySet()) {
            String[] key = entry.getKey();
            List<String> value = entry.getValue();
            if (!key[0].contains("Образовательная программа")) {
                value.add(key[0] + ":");
            }
            for (IpraEduCondition c : conditions) {
                String[] condKey = {c.getIpraeducondtypeId().getIpraeducondtypeName(), isp, "До " + c.getFormat2Date()};
                if (Arrays.equals(key, condKey)) {
                    String[] contextV = c.getIpraeducondContext().split("\\n");
                    for (String con : contextV) {
                        value.add(con);
                    }
                }
            }
        }

        String path = "templates//Перечень мероприятий.docx";
        File file = null;
        FileInputStream fis = null;
        XWPFDocument doc = null;
        try {
            file = new File(path);
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException ioEx) {
            }
        }

        List<XWPFParagraph> paraList = doc.getParagraphs();
        List<XWPFTable> tableList = null;
        Iterator<XWPFTable> tableIter = null;
        List<XWPFTableRow> rowList = null;
        Iterator<XWPFTableRow> rowIter = null;
        List<XWPFTableCell> cellList = null;
        Iterator<XWPFTableCell> cellIter = null;
        XWPFTable table = null;
        XWPFTableRow row = null;
        XWPFTableCell cell = null;

        String font = "Times New Roman";
        // вставляем в закладки в обычных параграфах
        insertAtBookmark(paraList, "chief_dolgn_dat", chiefDolgnDat, 14, font, "");
        insertAtBookmark(paraList, "omsu_rod", omsuRod, 14, font, "");
        insertAtBookmark(paraList, "omsu_chief_iof_in", omsuChiefIofIn, 14, font, "");
        insertAtBookmark(paraList, "iof_in_rod", iofInRod, 14, font, "");
        insertAtBookmark(paraList, "omsu_chief_io", omsuChiefIo, 14, font, "");
        insertAtBookmark(paraList, "fio_rod", fioRodFull, 14, font, "");
        insertAtBookmark(paraList, "iof_in_vin", iofInVin, 14, font, "");
        insertAtBookmark(paraList, "sotr_iof_in", sotrIofIn, 12, font, "");
        insertAtBookmark(paraList, "fio_rod2", fioRodFull, 14, font, "bold"); // жирный
        insertAtBookmark(paraList, "dr", dr, 14, font, "bold");   // жирный

        // вставляем в закладки в таблицах
        tableList = doc.getTables();
        tableIter = tableList.iterator();
        while (tableIter.hasNext()) {
            table = tableIter.next();

            rowList = table.getRows();
            rowIter = rowList.iterator();
            while (rowIter.hasNext()) {
                row = rowIter.next();
                cellList = row.getTableCells();
                cellIter = cellList.iterator();
                while (cellIter.hasNext()) {
                    cell = cellIter.next();
                    insertAtBookmark(cell.getParagraphs(), "ipra_nom", ipraNom, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "exp_nom", expNom, 14, font, "");
                    insertAtBookmark(cell.getParagraphs(), "ipra_date", ipraD, 14, font, "");
                    rowIter = insertTableAtBookmark(cell.getParagraphs(), table, rowIter, "conditions", conditionsMap, 14, font, "");
                }
            }

        }

        // вставляем в закладки в TextBox (надпись)
        insertAtBookmarkAtTextbox(paraList, "reqnomdate", "На № " + reqN + " от " + reqD, 8, "Arial", "underline");
        insertAtBookmarkAtTextbox(paraList, "pnomdate", pD + " № " + pN, 8, "Arial", "underline");

        doc.write(os);
        doc.close();
    }

    private static String monthNomToString(String monthNom) {
        String month = "";
        switch (monthNom) {
            case "01":
                month = "января";
                break;
            case "02":
                month = "февраля";
                break;
            case "03":
                month = "марта";
                break;
            case "04":
                month = "апреля";
                break;
            case "05":
                month = "мая";
                break;
            case "06":
                month = "июня";
                break;
            case "07":
                month = "июля";
                break;
            case "08":
                month = "августа";
                break;
            case "09":
                month = "сентября";
                break;
            case "10":
                month = "октября";
                break;
            case "11":
                month = "ноября";
                break;
            case "12":
                month = "декабря";
                break;
        }
        return month;
    }

    private static String monthNomToImString(String monthNom) {
        String month = "";
        switch (monthNom) {
            case "01":
                month = "январь";
                break;
            case "02":
                month = "февраль";
                break;
            case "03":
                month = "март";
                break;
            case "04":
                month = "апрель";
                break;
            case "05":
                month = "май";
                break;
            case "06":
                month = "июнь";
                break;
            case "07":
                month = "июль";
                break;
            case "08":
                month = "август";
                break;
            case "09":
                month = "сентябрь";
                break;
            case "10":
                month = "октябрь";
                break;
            case "11":
                month = "ноябрь";
                break;
            case "12":
                month = "декабрь";
                break;
        }
        return month;
    }

    private static void insertAtBookmark(List<XWPFParagraph> paraList,
            String bookmarkName, String bookmarkValue, int fontSize, String fontFamily, String style) {
        Iterator<XWPFParagraph> paraIter = null;
        XWPFParagraph para = null;
        List<CTBookmark> bookmarkList = null;
        Iterator<CTBookmark> bookmarkIter = null;
        CTBookmark bookmark = null;
        XWPFRun run = null;
        Node nextNode = null;

        // Get an Iterator to step through the contents of the paragraph list. 
        paraIter = paraList.iterator();
        while (paraIter.hasNext()) {
            // Get the paragraph, a llist of CTBookmark objects and an Iterator 
            // to step through the list of CTBookmarks. 
            para = paraIter.next();
            bookmarkList = para.getCTP().getBookmarkStartList();
            bookmarkIter = bookmarkList.iterator();

            while (bookmarkIter.hasNext()) {
                // Get a Bookmark and check it's name. If the name of the 
                // bookmark matches the name the user has specified... 
                bookmark = bookmarkIter.next();
                if (bookmark.getName().equals(bookmarkName)) {
                    // ...create the text run to insert and set it's text 
                    // content and then insert that text into the document. 
                    run = para.createRun();
                    run.setText(bookmarkValue);
                    run.setFontFamily(fontFamily);
                    run.setFontSize(fontSize);
                    if (style.equals("bold")) {
                        run.setBold(true);
                    }
                    if (style.equals("italic")) {
                        run.setItalic(true);
                    }
                    if (style.equals("underline")) {
                        run.setUnderline(UnderlinePatterns.SINGLE);
                    }
                    // The new Run should be inserted between the bookmarkStart 
                    // and bookmarkEnd nodes, so find the bookmarkEnd node. 
                    // Note that we are looking for the next sibling of the 
                    // bookmarkStart node as it does not contain any child nodes 
                    // as far as I am aware. 
                    nextNode = bookmark.getDomNode().getNextSibling();
                    // If the next node is not the bookmarkEnd node, then step 
                    // along the sibling nodes, until the bookmarkEnd node 
                    // is found. As the code is here, it will remove anything 
                    // it finds between the start and end nodes. This, of course 
                    // comepltely sidesteps the issues surrounding boorkamrks 
                    // that contain other bookmarks which I understand can happen. 
                    while (!(nextNode.getNodeName().contains("bookmarkEnd"))) {
                        para.getCTP().getDomNode().removeChild(nextNode);
                        nextNode = bookmark.getDomNode().getNextSibling();
                    }

                    // Finally, insert the new Run node into the document 
                    // between the bookmarkStrat and the bookmarkEnd nodes. 
                    para.getCTP().getDomNode().insertBefore(
                            run.getCTR().getDomNode(),
                            nextNode);
                }
            }
        }
    }

    private static void insertAtBookmarkAtTextbox(List<XWPFParagraph> paraList,
            String bookmarkName, String bookmarkValue, int fontSize, String fontFamily, String style) {
        Iterator<XWPFParagraph> paraIter = null;
        XWPFParagraph para = null;

        // Get an Iterator to step through the contents of the paragraph list. 
        paraIter = paraList.iterator();
        while (paraIter.hasNext()) {
            para = paraIter.next();
            XmlCursor cursor = para.getCTP().newCursor();
            cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' "
                    + ".//*/w:txbxContent/w:p/w:r");

            List<XmlObject> ctrsintxtbx = new ArrayList<XmlObject>();

            while (cursor.hasNextSelection()) {
                cursor.toNextSelection();
                XmlObject obj = cursor.getObject();
                ctrsintxtbx.add(obj);
            }
            for (XmlObject obj : ctrsintxtbx) {
                CTR ctr = null;
                try {
                    ctr = CTR.Factory.parse(obj.toString());
                } catch (XmlException ex) {
                    Logger.getLogger(Wrd.class.getName()).log(Level.SEVERE, null, ex);
                }
                XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) para);
                String text = bufferrun.getText(0);
                if (text != null && text.contains(bookmarkName)) {
                    text = text.replace(bookmarkName, bookmarkValue);
                    bufferrun.setText(text, 0);
                    bufferrun.setFontFamily(fontFamily);
                    bufferrun.setFontSize(fontSize);
                    if (style.equals("bold")) {
                        bufferrun.setBold(true);
                    }
                    if (style.equals("italic")) {
                        bufferrun.setItalic(true);
                    }
                    if (style.equals("underline")) {
                        bufferrun.setUnderline(UnderlinePatterns.SINGLE);
                    }
                }
                obj.set(bufferrun.getCTR());
            }
        }

    }

    private static void multiInsertAtBookmark(List<XWPFParagraph> paraList,
            String bookmarkName, List<String> bookmarkValue, int fontSize) {
        Iterator<XWPFParagraph> paraIter = null;
        XWPFParagraph para = null;
        List<CTBookmark> bookmarkList = null;
        Iterator<CTBookmark> bookmarkIter = null;
        CTBookmark bookmark = null;
        XWPFRun run = null;
        Node nextNode = null;

        // Get an Iterator to step through the contents of the paragraph list. 
        paraIter = paraList.iterator();
        while (paraIter.hasNext()) {
            // Get the paragraph, a llist of CTBookmark objects and an Iterator 
            // to step through the list of CTBookmarks. 
            para = paraIter.next();
            bookmarkList = para.getCTP().getBookmarkStartList();
            bookmarkIter = bookmarkList.iterator();

            while (bookmarkIter.hasNext()) {
                // Get a Bookmark and check it's name. If the name of the 
                // bookmark matches the name the user has specified... 
                bookmark = bookmarkIter.next();
                if (bookmark.getName().equals(bookmarkName)) {
                    // ...create the text run to insert and set it's text 
                    // content and then insert that text into the document. 
                    run = para.createRun();
                    for (String val : bookmarkValue) {
                        run.setText(val);
                        run.addBreak();
                    }
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(fontSize);
                    // The new Run should be inserted between the bookmarkStart 
                    // and bookmarkEnd nodes, so find the bookmarkEnd node. 
                    // Note that we are looking for the next sibling of the 
                    // bookmarkStart node as it does not contain any child nodes 
                    // as far as I am aware. 
                    nextNode = bookmark.getDomNode().getNextSibling();
                    // If the next node is not the bookmarkEnd node, then step 
                    // along the sibling nodes, until the bookmarkEnd node 
                    // is found. As the code is here, it will remove anything 
                    // it finds between the start and end nodes. This, of course 
                    // comepltely sidesteps the issues surrounding boorkamrks 
                    // that contain other bookmarks which I understand can happen. 
                    while (!(nextNode.getNodeName().contains("bookmarkEnd"))) {
                        para.getCTP().getDomNode().removeChild(nextNode);
                        nextNode = bookmark.getDomNode().getNextSibling();
                    }

                    // Finally, insert the new Run node into the document 
                    // between the bookmarkStrat and the bookmarkEnd nodes. 
                    para.getCTP().getDomNode().insertBefore(
                            run.getCTR().getDomNode(),
                            nextNode);
                }
            }
        }
    }

    private static Iterator<XWPFTableRow> insertTableAtBookmark(List<XWPFParagraph> paraList, XWPFTable table, Iterator<XWPFTableRow> rowIter,
            String bookmarkName, Map<String[], List<String>> bookmarkValue, int fontSize, String fontFamily, String style) {
        Iterator<XWPFParagraph> paraIter = null;
        XWPFParagraph para = null;
        List<CTBookmark> bookmarkList = null;
        Iterator<CTBookmark> bookmarkIter = null;
        CTBookmark bookmark = null;
        XWPFRun run = null;
        Node nextNode = null;

        Iterator<XWPFTableRow> newRowIter = rowIter;

        // Get an Iterator to step through the contents of the paragraph list. 
        paraIter = paraList.iterator();
        while (paraIter.hasNext()) {
            // Get the paragraph, a llist of CTBookmark objects and an Iterator 
            // to step through the list of CTBookmarks. 
            para = paraIter.next();
            bookmarkList = para.getCTP().getBookmarkStartList();
            bookmarkIter = bookmarkList.iterator();

            while (bookmarkIter.hasNext()) {
                // Get a Bookmark and check it's name. If the name of the 
                // bookmark matches the name the user has specified... 
                bookmark = bookmarkIter.next();
                if (bookmark.getName().equals(bookmarkName)) {
                    for (Map.Entry<String[], List<String>> entry : bookmarkValue.entrySet()) {
                        String[] key = entry.getKey();
                        List<String> value = entry.getValue();
                        XWPFTableRow row = table.createRow();

                        XWPFParagraph cellPara = row.getCell(0).getParagraphArray(0);
                        XWPFRun cellRun = cellPara.createRun();
                        for (String val : value) {
                            cellRun.setText(val);
                            cellRun.addBreak();
                        }
                        cellRun.setFontFamily(fontFamily);
                        cellRun.setFontSize(fontSize);
                        if (style.equals("bold")) {
                            cellRun.setBold(true);
                        }
                        if (style.equals("italic")) {
                            cellRun.setItalic(true);
                        }
                        if (style.equals("underline")) {
                            cellRun.setUnderline(UnderlinePatterns.SINGLE);
                        }
                        cellPara.getCTP().getDomNode().appendChild(cellRun.getCTR().getDomNode());

                        // исполнитель
                        cellPara = row.getCell(1).getParagraphArray(0);
                        cellRun = cellPara.createRun();
                        cellRun.setText(key[1]);
                        cellRun.setFontFamily(fontFamily);
                        cellRun.setFontSize(fontSize);
                        if (style.equals("bold")) {
                            cellRun.setBold(true);
                        }
                        if (style.equals("italic")) {
                            cellRun.setItalic(true);
                        }
                        if (style.equals("underline")) {
                            cellRun.setUnderline(UnderlinePatterns.SINGLE);
                        }
                        cellPara.getCTP().getDomNode().appendChild(cellRun.getCTR().getDomNode());

                        // дата
                        cellPara = row.getCell(2).getParagraphArray(0);
                        cellRun = cellPara.createRun();
                        cellRun.setText(key[2]);
                        cellRun.setFontFamily(fontFamily);
                        cellRun.setFontSize(fontSize);
                        if (style.equals("bold")) {
                            cellRun.setBold(true);
                        }
                        if (style.equals("italic")) {
                            cellRun.setItalic(true);
                        }
                        if (style.equals("underline")) {
                            cellRun.setUnderline(UnderlinePatterns.SINGLE);
                        }
                        cellPara.getCTP().getDomNode().appendChild(cellRun.getCTR().getDomNode());

                        CTTblPr tblpro = table.getCTTbl().getTblPr();
                        CTTblBorders borders = tblpro.addNewTblBorders();
                        borders.addNewBottom().setVal(STBorder.SINGLE);
                        borders.addNewLeft().setVal(STBorder.SINGLE);
                        borders.addNewRight().setVal(STBorder.SINGLE);
                        borders.addNewTop().setVal(STBorder.SINGLE);
                        borders.addNewInsideH().setVal(STBorder.SINGLE);
                        borders.addNewInsideV().setVal(STBorder.SINGLE);
                    }

                    /*table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 1, "000000");
                    table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 1, "000000");*/
                    //нужно задать новый итератор, т.к. в таблицу добавили строки
                    List<XWPFTableRow> rowList = table.getRows();
                    newRowIter = rowList.iterator();
                    while (newRowIter.hasNext()) {
                        newRowIter.next();
                    };
                }
            }
        }
        return newRowIter;
    }

}
