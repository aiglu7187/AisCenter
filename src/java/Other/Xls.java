/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Count.CountProblem;
import Count.CountProblemUsl;
import Count.CountStatPmpk;
import Count.CountStatus;
import Count.CountStatusDolgn;
import Count.CountStatusReg;
import Count.CountStatusUsl;
import Count.Gz;
import Count.OtchetStatPmpk;
import Count.StandartCount;
import Count.Statistic;
import Entities.Ipra;
import Entities.Ipra18;
import Entities.Ipra18Prikaz;
import Entities.SotrudDolgn;
import Entities.SprRegion;
import Entities.SprUsl;
import Reestr.PMPKR;
import Reestr.PMPKStatus;
import Reestr.PMPKTer;
import Reestr.Reestr;
import Reestr.ReestrMonitPMPK;
import Reestr.ReestrPMPK;
import Reestr.ReestrPMPKFull;
import Reestr.ReestrUsl;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletOutputStream;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *
 * @author Aiglu
 */
public class Xls {

    private static WritableWorkbook workbook; // переменная рабочей книги
    public static WritableSheet sheet;
    public static WritableCellFormat arial12BoldFormat;

    public static Label label;

    // метод создает книгу с одной раб страницей
    public static void exelSet() throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            //имя и путь файла
            workbook = Workbook.createWorkbook(new File("d:/My-tmp.xls"), ws);
            //название листа
            sheet = workbook.createSheet("Отрицательные остатки", 0);

            /*
			* здесь необходимо перечислить методы, которые будут
			* выполняться для заполнения листа
             */
            test();

        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printOtchetSotrud(ServletOutputStream os,
            SotrudDolgn sotrudDolgn, Date date1, Date date2, List<Statistic> statistic, Integer childKol,
            Integer parentsKol, Integer pedKol, Integer childKolP, Integer parentsKolP, Integer pedKolP,
            String centerShortName)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            String fio = sotrudDolgn.getSotrudId().getSotrudFam() + " " + sotrudDolgn.getSotrudId().getSotrudName() + " " + sotrudDolgn.getSotrudId().getSotrudPatr();

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            try {
                sheet.mergeCells(0, 0, 7, 0);
                sheet.addCell(new Label(0, 0, "Отчет по сотруднику за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 7, 1);
                sheet.addCell(new Label(0, 1, fio, tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "№", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);

                sheet.addCell(new Label(1, 3, "Услуга", tnr12ptFormat));
                sheet.mergeCells(2, 3, 4, 3);
                sheet.addCell(new Label(2, 3, "Кол-во клиентов", tnr12ptFormat));
                sheet.addCell(new Label(2, 4, "в " + centerShortName, tnr12ptFormat));
                sheet.addCell(new Label(3, 4, "в ОО", tnr12ptFormat));
                sheet.addCell(new Label(4, 4, "итого", tnr12ptFormat));
                sheet.mergeCells(5, 3, 7, 3);
                sheet.addCell(new Label(5, 3, "Кол-во оказанных услуг", tnr12ptFormat));
                sheet.addCell(new Label(5, 4, "в " + centerShortName, tnr12ptFormat));
                sheet.addCell(new Label(6, 4, "в ОО", tnr12ptFormat));
                sheet.addCell(new Label(7, 4, "итого", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 4;
            for (Statistic st : statistic) {
                if ((st.getClientAll() != 0) && (st.getPriyomAll() != 0)) {
                    i++;
                    j++;
                    try {
                        sheet.addCell(new Number(0, j, i, tnr12ptFormatLeft));
                        sheet.addCell(new Label(1, j, st.getUsl(), tnr12ptFormatLeft));
                        sheet.addCell(new Number(2, j, st.getClientCenter(), tnr12ptFormat));
                        sheet.addCell(new Number(3, j, st.getClientNotCenter(), tnr12ptFormat));
                        sheet.addCell(new Number(4, j, st.getClientAll(), tnr12ptFormat));
                        sheet.addCell(new Number(5, j, st.getPriyomCenter(), tnr12ptFormat));
                        sheet.addCell(new Number(6, j, st.getPriyomNotCenter(), tnr12ptFormat));
                        sheet.addCell(new Number(7, j, st.getPriyomAll(), tnr12ptFormat));
                    } catch (RowsExceededException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                }
            }
            j++;
            try {
                sheet.mergeCells(0, ++j, 1, j);
                sheet.addCell(new Label(0, j, "По категориям:", tnr12ptBoldFormatLeft));
                sheet.addCell(new Label(2, j, "количество", tnr12ptFormatNoBorder));
                sheet.addCell(new Label(3, j, "услуги", tnr12ptFormatNoBorder));
                sheet.mergeCells(0, ++j, 1, j);
                sheet.addCell(new Label(0, j, "дети", tnr12ptFormatLeft));
                sheet.addCell(new Number(2, j, childKol, tnr12ptFormat));
                sheet.addCell(new Number(3, j, childKolP, tnr12ptFormat));
                sheet.mergeCells(0, ++j, 1, j);
                sheet.addCell(new Label(0, j, "законные представители", tnr12ptFormatLeft));
                sheet.addCell(new Number(2, j, parentsKol, tnr12ptFormat));
                sheet.addCell(new Number(3, j, parentsKolP, tnr12ptFormat));
                sheet.mergeCells(0, ++j, 1, j);
                sheet.addCell(new Label(0, j, "педагоги", tnr12ptFormatLeft));
                sheet.addCell(new Number(2, j, pedKol, tnr12ptFormat));
                sheet.addCell(new Number(3, j, pedKolP, tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(22 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(319 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(101 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(51 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(45 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(101 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(43 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(45 * 37);
            sheet.setColumnView(7, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printOtchetGz(ServletOutputStream os, Date date1, Date date2, List<Gz> gzs) throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 5, 0);
                sheet.addCell(new Label(0, 0, "Отчет по исполнению государственного задания", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 5, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "Услуга", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Условия оказания услуги", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Направления", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Кол-во человек", tnr12ptFormat));
                sheet.addCell(new Label(4, 3, "Кол-во услуг", tnr12ptFormat));
                sheet.addCell(new Label(5, 3, "Кол-во обращений", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Set<String> osnUslList = new HashSet<String>();
            for (Gz gz : gzs) {
                if ((gz.getClient() != 0) && (gz.getPriyom() != 0)) {
                    osnUslList.add(gz.getOsnUsl());
                }
            }

            Integer i;
            Integer j = 3;
            for (String osnUsl : osnUslList) {
                j++;
                i = j;
                sheet.addCell(new Label(0, j, osnUsl, tnr12ptFormatLeftCenter));
                for (Gz gz : gzs) {
                    if ((gz.getOsnUsl().equals(osnUsl)) && (gz.getCenter())) {
                        if ((gz.getClient() != 0) && (gz.getPriyom() != 0)) {
                            if (gz.getUsl().equals("итого")) {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatRight));
                            } else {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatLeft));
                            }
                            sheet.addCell(new Number(3, i, gz.getClient(), tnr12ptFormat));
                            sheet.addCell(new Number(4, i, gz.getPriyom(), tnr12ptFormat));
                            sheet.addCell(new Number(5, i, gz.getPrcl(), tnr12ptFormat));
                            i++;
                        }
                    }
                }
                if (j < i) {
                    sheet.mergeCells(1, j, 1, i - 1);
                    sheet.addCell(new Label(1, j, "в ЦППМСП", tnr12ptFormatLeftCenter));
                }
                Integer k = i;
                for (Gz gz : gzs) {
                    if ((gz.getOsnUsl().equals(osnUsl)) && (!gz.getCenter())) {
                        if ((gz.getClient() != 0) && (gz.getPriyom() != 0)) {
                            if (gz.getUsl().equals("итого")) {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatRight));
                            } else {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatLeft));
                            }
                            sheet.addCell(new Number(3, i, gz.getClient(), tnr12ptFormat));
                            sheet.addCell(new Number(4, i, gz.getPriyom(), tnr12ptFormat));
                            sheet.addCell(new Number(5, i, gz.getPrcl(), tnr12ptFormat));
                            i++;
                        }
                    }
                }
                if (k < i) {
                    sheet.mergeCells(1, k, 1, i - 1);
                    sheet.addCell(new Label(1, k, "в ОО", tnr12ptFormatLeftCenter));
                }
                sheet.mergeCells(0, j, 0, i - 1);
                j = i - 1;
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(334 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(181 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(319 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(114 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(189 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(189 * 37);
            sheet.setColumnView(5, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    public static void printReestrPMPK(ServletOutputStream os, Date date1, Date date2, List<ReestrPMPK> reestr, String pmpkShname) throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldFormat.setWrap(true);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 8, 0);
                sheet.addCell(new Label(0, 0, "Реестр детей с ограниченными возможностями здоровья и (или) девиантным (общественно опасным) поведением, прошедших обследование на " + pmpkShname, tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 8, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Мун.район/ городской округ", tnr12ptFormat));
                sheet.addCell(new Label(4, 3, "Ребенок с ОВЗ", tnr12ptFormat));
                sheet.addCell(new Label(5, 3, "Ребенок с девиантным (общественно опасным) поведением", tnr12ptFormat));
                sheet.addCell(new Label(6, 3, "Дата ПМПК", tnr12ptFormat));
                sheet.addCell(new Label(7, 3, "Заключение ПМПК", tnr12ptFormat));
                sheet.addCell(new Label(8, 3, "Срок действия заключения", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 3;
            for (ReestrPMPK child : reestr) {
                i++;
                j++;
                sheet.addCell(new Number(0, j, i, tnr12ptFormatRight));
                sheet.addCell(new Label(1, j, child.getFio(), tnr12ptFormatLeft));
                sheet.addCell(new Label(2, j, dateFormat.format(child.getDr()), tnr12ptFormatLeft));
                sheet.addCell(new Label(3, j, child.getReg(), tnr12ptFormatLeft));
                if (!child.getOvz().equals("")) {
                    sheet.addCell(new Label(4, j, child.getOvz(), tnr12ptFormat));
                } else if (child.getOvz().equals("")) {
                    sheet.addCell(new Label(4, j, "-", tnr12ptFormat));
                }
                if (child.getOp()) {
                    sheet.addCell(new Label(5, j, "+", tnr12ptFormat));
                } else if (!child.getOp()) {
                    sheet.addCell(new Label(5, j, "-", tnr12ptFormat));
                }
                sheet.addCell(new Label(6, j, dateFormat.format(child.getDatep()), tnr12ptFormatLeft));
                sheet.addCell(new Label(7, j, child.getObr(), tnr12ptFormatLeft));
                String datek = "";
                if (child.getDatek() != null) {
                    datek = dateFormat.format(child.getDatek());
                }
                sheet.addCell(new Label(8, j, datek, tnr12ptFormatLeft));
            }

            CellView cvv = sheet.getRowView(0);
            cvv.setSize(21 * 37);
            sheet.setRowView(0, cvv);

            CellView cv = sheet.getColumnView(0);
            cv.setSize(40 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(300 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(90 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(120 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(105 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(105 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(90 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(725 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(110 * 37);
            sheet.setColumnView(8, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    public static void printStatPMPK(ServletOutputStream os, Date date1, Date date2, List<SprRegion> regions,
            List<OtchetStatPmpk> statPmpk, List<OtchetStatPmpk> statPmpkRek, List<OtchetStatPmpk> statPmpkIpr,
            List<OtchetStatPmpk> statPmpkUd, List<OtchetStatPmpk> statPmpkT, String pmpkNameRod,
            RolesRight rolesRight)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;
            WritableCellFormat tnr12ptVertFormat;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldFormat.setWrap(true);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру вертикально
            tnr12ptVertFormat = new WritableCellFormat(tnr12pt);
            tnr12ptVertFormat.setAlignment(Alignment.CENTRE);
            tnr12ptVertFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptVertFormat.setOrientation(Orientation.PLUS_90);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeft.setWrap(true);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatRight.setWrap(true);

            // обычный влево по центру по вертикали по центру               
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            Integer rn = regions.size() + 1;

            try {
                sheet.mergeCells(0, 0, rn + 1, 0);
                sheet.addCell(new Label(0, 0, "Статистические данные о работе " + pmpkNameRod, tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, rn + 1, 1);
                sheet.addCell(new Label(0, 1, "по основным направлениям деятельности за период", tnr12ptBoldFormat));
                sheet.mergeCells(0, 2, rn + 1, 2);
                sheet.addCell(new Label(0, 2, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 4, 0, 5);
                sheet.addCell(new Label(0, 4, "№ п/п", tnr12ptFormat));
                sheet.mergeCells(1, 4, 1, 5);
                sheet.addCell(new Label(1, 4, "Показатель", tnr12ptFormat));
                sheet.mergeCells(2, 4, rn + 1, 4);
                sheet.addCell(new Label(2, 4, "Статистические данные по муниципальным районам и городским округам Вологодской области", tnr12ptFormat));
                int i = 2;
                for (SprRegion reg : regions) {
                    sheet.addCell(new Label(i, 5, reg.getSprregName(), tnr12ptVertFormat));
                    i++;
                }
                sheet.addCell(new Label(i, 5, "Всего", tnr12ptVertFormat));

                int j = 5;

                for (OtchetStatPmpk stat : statPmpk) {
                    j++;
                    sheet.addCell(new Label(0, j, stat.getN(), tnr12ptFormatRight));
                    sheet.addCell(new Label(1, j, stat.getPar(), tnr12ptFormatLeft));
                    List<CountStatPmpk> count = stat.getStat();
                    for (int k = 2; k <= i; k++) {
                        for (CountStatPmpk c : count) {
                            if (sheet.getCell(k, 5).getContents().equals(c.getReg())) {
                                sheet.addCell(new Number(k, j, c.getCount(), tnr12ptFormat));
                            } else if (sheet.getCell(k, j).getContents().equals("")) {
                                sheet.addCell(new Number(k, j, 0, tnr12ptFormat));
                            }
                        }
                    }
                }

                j++;
                sheet.mergeCells(0, j, rn + 1, j);
                sheet.addCell(new Label(0, j, "Рекомендации ПМПК", tnr12ptFormat));
                for (OtchetStatPmpk stat : statPmpkRek) {
                    j++;
                    sheet.addCell(new Label(0, j, stat.getN(), tnr12ptFormatRight));
                    sheet.addCell(new Label(1, j, stat.getPar(), tnr12ptFormatLeft));
                    List<CountStatPmpk> count = stat.getStat();
                    for (int k = 2; k <= i; k++) {
                        for (CountStatPmpk c : count) {
                            if (sheet.getCell(k, 5).getContents().equals(c.getReg())) {
                                sheet.addCell(new Number(k, j, c.getCount(), tnr12ptFormat));
                            } else if (sheet.getCell(k, j).getContents().equals("")) {
                                sheet.addCell(new Number(k, j, 0, tnr12ptFormat));
                            }
                        }
                    }
                }

                if (rolesRight.isVologda()) {
                    j++;
                    sheet.mergeCells(0, j, rn + 1, j);
                    sheet.addCell(new Label(0, j, "Взаимодействие с МСЭ", tnr12ptFormat));
                    for (OtchetStatPmpk stat : statPmpkIpr) {
                        j++;
                        sheet.addCell(new Label(0, j, stat.getN(), tnr12ptFormatRight));
                        sheet.addCell(new Label(1, j, stat.getPar(), tnr12ptFormatLeft));
                        List<CountStatPmpk> count = stat.getStat();
                        for (int k = 2; k <= i; k++) {
                            for (CountStatPmpk c : count) {
                                if (sheet.getCell(k, 5).getContents().equals(c.getReg())) {
                                    sheet.addCell(new Number(k, j, c.getCount(), tnr12ptFormat));
                                } else if (sheet.getCell(k, j).getContents().equals("")) {
                                    sheet.addCell(new Number(k, j, 0, tnr12ptFormat));
                                }
                            }
                        }
                    }
                }
                j++;
                sheet.mergeCells(0, j, rn + 1, j);
                sheet.addCell(new Label(0, j, "Удовлетворенность родителей (законных представителей) деятельностью ПМПК", tnr12ptFormat));
                for (OtchetStatPmpk stat : statPmpkUd) {
                    j++;
                    sheet.addCell(new Label(0, j, stat.getN(), tnr12ptFormatRight));
                    sheet.addCell(new Label(1, j, stat.getPar(), tnr12ptFormatLeft));
                    List<CountStatPmpk> count = stat.getStat();
                    for (int k = 2; k <= i; k++) {
                        for (CountStatPmpk c : count) {
                            if (sheet.getCell(k, 5).getContents().equals(c.getReg())) {
                                sheet.addCell(new Number(k, j, c.getCount(), tnr12ptFormat));
                            } else if (sheet.getCell(k, j).getContents().equals("")) {
                                sheet.addCell(new Number(k, j, 0, tnr12ptFormat));
                            }
                        }
                    }
                }

                if (rolesRight.isVologda()) {
                    j++;
                    sheet.mergeCells(0, j, rn + 1, j);
                    sheet.addCell(new Label(0, j, "Взаимодействие с ТПМПК", tnr12ptFormat));
                    for (OtchetStatPmpk stat : statPmpkT) {
                        j++;
                        sheet.addCell(new Label(0, j, stat.getN(), tnr12ptFormatRight));
                        sheet.addCell(new Label(1, j, stat.getPar(), tnr12ptFormatLeft));
                        List<CountStatPmpk> count = stat.getStat();
                        for (int k = 2; k <= i; k++) {
                            for (CountStatPmpk c : count) {
                                if (sheet.getCell(k, 5).getContents().equals(c.getReg())) {
                                    sheet.addCell(new Number(k, j, c.getCount(), tnr12ptFormat));
                                } else if (sheet.getCell(k, j).getContents().equals("")) {
                                    sheet.addCell(new Number(k, j, 0, tnr12ptFormat));
                                }
                            }
                        }
                    }
                }
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            CellView cvv = sheet.getRowView(0);
            cvv.setSize(21 * 37);
            sheet.setRowView(0, cvv);

            cvv = sheet.getRowView(4);
            cvv.setSize(21 * 37);
            sheet.setRowView(4, cvv);

            CellView cv = sheet.getColumnView(0);
            cv.setSize(48 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(250 * 37);
            sheet.setColumnView(1, cv);

            for (int i = 2; i <= rn + 2; i++) {
                cv = sheet.getColumnView(i);
                cv.setSize(40 * 37);
                sheet.setColumnView(i, cv);
            }

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    public static void test() throws WriteException {
        //установка шрифта
        WritableFont arial12ptBold
                = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        arial12BoldFormat = new WritableCellFormat(arial12ptBold);
        //выравнивание по центру
        arial12BoldFormat.setAlignment(Alignment.CENTRE);
        //перенос по словам если не помещается
        arial12BoldFormat.setWrap(true);
        //установить цвет
        arial12BoldFormat.setBackground(Colour.GRAY_50);
        //рисуем рамку
        arial12BoldFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        //поворот текста

        arial12BoldFormat.setOrientation(Orientation.PLUS_90);

        //пример добавления в ячейки
        int i;
        int q;
        q = 10;

        for (i = 9; i < 22; i++) {
            Label label = new Label(1, i, "С " + i + " по " + q, arial12BoldFormat);
            sheet.addCell(label);
            q++;
        }

        Label label1 = new Label(2, 2, "значение", arial12BoldFormat);
        try {
            //добавление данных в лист sheet с обработкой исключений
            sheet.addCell(label1);
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }

    public static void printStatus(ServletOutputStream os, Date date1, Date date2,
            List<CountStatus> status, List<CountStatusUsl> stUsl, List<CountStatusUsl> stOsnUsl, List<CountStatusDolgn> stDolgn)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Статусы", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;
            WritableCellFormat tnr12ptVertFormat;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldFormat.setWrap(true);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру вертикально
            tnr12ptVertFormat = new WritableCellFormat(tnr12pt);
            tnr12ptVertFormat.setAlignment(Alignment.CENTRE);
            tnr12ptVertFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptVertFormat.setOrientation(Orientation.PLUS_90);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали по центру               
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 2, 0);
                sheet.addCell(new Label(0, 0, "Отчет по статусам детей (общее количество) за период", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 2, 1);
                sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Статус", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Кол-во детей", tnr12ptFormat));

                int j = 3;
                for (CountStatus c : status) {
                    j++;
                    sheet.addCell(new Number(0, j, j - 3, tnr12ptFormat));
                    sheet.addCell(new Label(1, j, c.getStatus(), tnr12ptFormat));
                    sheet.addCell(new Number(2, j, c.getCount(), tnr12ptFormat));
                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(48 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(150 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(250 * 37);
            sheet.setColumnView(2, cv);

            // 2 лист
            sheet = workbook.createSheet("Статусы по услугам", 1);
            try {
                sheet.mergeCells(0, 0, 3, 0);
                sheet.addCell(new Label(0, 0, "Отчет по статусам детей по услугам за период", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 3, 1);
                sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Услуга", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Статус", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Кол-во детей", tnr12ptFormat));

                int j = 3;
                for (CountStatusUsl c : stUsl) {
                    j++;
                    sheet.addCell(new Number(0, j, j - 3, tnr12ptFormat));
                    sheet.addCell(new Label(1, j, c.getUsl(), tnr12ptFormat));
                    sheet.addCell(new Label(2, j, c.getStatus(), tnr12ptFormat));
                    sheet.addCell(new Number(3, j, c.getCount(), tnr12ptFormat));
                }
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            CellView cv2 = sheet.getColumnView(0);
            cv2.setSize(48 * 37);
            sheet.setColumnView(0, cv2);

            cv2 = sheet.getColumnView(1);
            cv2.setSize(250 * 37);
            sheet.setColumnView(1, cv2);

            cv2 = sheet.getColumnView(2);
            cv2.setSize(150 * 37);
            sheet.setColumnView(2, cv2);

            cv2 = sheet.getColumnView(3);
            cv2.setSize(250 * 37);
            sheet.setColumnView(3, cv2);

            // 3 лист
            sheet = workbook.createSheet("Статусы по обследованию", 2);
            try {
                sheet.mergeCells(0, 0, 3, 0);
                sheet.addCell(new Label(0, 0, "Отчет по статусам детей по специалистам (обследование) за период", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 3, 1);
                sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Специалист", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Статус", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Кол-во детей", tnr12ptFormat));

                int j = 3;
                for (CountStatusDolgn c : stDolgn) {
                    j++;
                    sheet.addCell(new Number(0, j, j - 3, tnr12ptFormat));
                    sheet.addCell(new Label(1, j, c.getDolgn(), tnr12ptFormat));
                    sheet.addCell(new Label(2, j, c.getStatus(), tnr12ptFormat));
                    sheet.addCell(new Number(3, j, c.getCount(), tnr12ptFormat));
                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            CellView cv3 = sheet.getColumnView(0);
            cv3.setSize(48 * 37);
            sheet.setColumnView(0, cv3);

            cv3 = sheet.getColumnView(1);
            cv3.setSize(250 * 37);
            sheet.setColumnView(1, cv3);

            cv3 = sheet.getColumnView(2);
            cv3.setSize(150 * 37);
            sheet.setColumnView(2, cv3);

            cv3 = sheet.getColumnView(3);
            cv3.setSize(250 * 37);
            sheet.setColumnView(3, cv3);

            // 4 лист
            sheet = workbook.createSheet("Статусы по основным услугам", 3);
            try {
                sheet.mergeCells(0, 0, 3, 0);
                sheet.addCell(new Label(0, 0, "Отчет по статусам детей по основным услугам за период", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 3, 1);
                sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Услуга", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Статус", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Кол-во детей", tnr12ptFormat));

                int j = 3;
                for (CountStatusUsl c : stOsnUsl) {
                    j++;
                    sheet.addCell(new Number(0, j, j - 3, tnr12ptFormat));
                    sheet.addCell(new Label(1, j, c.getUsl(), tnr12ptFormat));
                    sheet.addCell(new Label(2, j, c.getStatus(), tnr12ptFormat));
                    sheet.addCell(new Number(3, j, c.getCount(), tnr12ptFormat));
                }
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            CellView cv4 = sheet.getColumnView(0);
            cv4.setSize(48 * 37);
            sheet.setColumnView(0, cv4);

            cv4 = sheet.getColumnView(1);
            cv4.setSize(250 * 37);
            sheet.setColumnView(1, cv4);

            cv4 = sheet.getColumnView(2);
            cv4.setSize(150 * 37);
            sheet.setColumnView(2, cv4);

            cv4 = sheet.getColumnView(3);
            cv4.setSize(250 * 37);
            sheet.setColumnView(3, cv4);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    public static void printProblem(ServletOutputStream os, List<CountProblem> problemAll, List<CountProblemUsl> problemUsl, Date date1, Date date2)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Проблемы", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 2, 0);
                sheet.addCell(new Label(0, 0, "Отчет по выявленным проблемам за период ", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 2, 1);
                sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "Тип проблемы", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Название проблемы", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Кол-во", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Set<String> typeListHash = new HashSet<String>();
            for (CountProblem cp : problemAll) {
                typeListHash.add(cp.getProblemType());
            }

            List<String> typeList = new ArrayList<String>();
            for (String tlh : typeListHash) {
                typeList.add(tlh);
            }

            Collections.sort(typeList);

            int i;
            int j = 3;

            for (String type : typeList) {
                j++;
                i = j;
                sheet.addCell(new Label(0, j, type, tnr12ptFormatLeftCenter));
                for (CountProblem cp : problemAll) {
                    if (cp.getProblemType().equals(type)) {
                        sheet.addCell(new Label(1, i, cp.getProblem(), tnr12ptFormatLeft));
                        sheet.addCell(new Number(2, i, cp.getCount(), tnr12ptFormatRight));
                        i++;
                    }
                }
                sheet.mergeCells(0, j, 0, i - 1);
                j = i - 1;
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(300 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(300 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(50 * 37);
            sheet.setColumnView(2, cv);

            sheet = workbook.createSheet("По услугам", 1);

            try {
                sheet.mergeCells(0, 0, 2, 0);
                sheet.addCell(new Label(0, 0, "Отчет по выявленным проблемам (разбивка по услугам) за период ", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 2, 1);
                sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Set<String> uslList = new HashSet<String>();
            for (CountProblemUsl cpu : problemUsl) {
                uslList.add(cpu.getUsl());
            }

            typeListHash = new HashSet<String>();
            for (CountProblem cp : problemAll) {
                typeListHash.add(cp.getProblemType());
            }

            typeList = new ArrayList<String>();
            for (String tlh : typeListHash) {
                typeList.add(tlh);
            }

            Collections.sort(typeList);
            j = 3;
            int k = j;
            for (String usl : uslList) {
                sheet.addCell(new Label(0, k, usl, tnr12ptBoldFormatLeft));
                k++;
                sheet.addCell(new Label(0, k, "Тип проблемы", tnr12ptFormat));
                sheet.addCell(new Label(1, k, "Название проблемы", tnr12ptFormat));
                sheet.addCell(new Label(2, k, "Кол-во", tnr12ptFormat));
                for (String type : typeList) {
                    j++;
                    i = j;
                    sheet.addCell(new Label(0, j, type, tnr12ptFormatLeftCenter));
                    for (CountProblemUsl cpu : problemUsl) {
                        if (cpu.getUsl().equals(usl)) {
                            if (cpu.getProblemType().equals(type)) {
                                sheet.addCell(new Label(1, i, cpu.getProblem(), tnr12ptFormatLeft));
                                sheet.addCell(new Number(2, i, cpu.getCount(), tnr12ptFormatRight));
                                i++;
                            }
                        }
                    }
                    if (i - j > 1) {
                        sheet.mergeCells(0, j, 0, i - 1);
                    }
                    j = i - 1;
                }
                j = j + 2;
                k = j++;
            }

            cv = sheet.getColumnView(0);
            cv.setSize(300 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(300 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(50 * 37);
            sheet.setColumnView(2, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printAge(ServletOutputStream os, List<StandartCount> uslAges, Date date1, Date date2, Integer ageN, Integer ageK)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Услуги по возрасту", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 2, 0);
                sheet.addCell(new Label(0, 0, "Отчет по услугам за период ", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 2, 1);
                sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 2, 2, 2);
                sheet.addCell(new Label(0, 2, "Возраст детей с " + ageN + " до " + ageK, tnr12ptBoldFormat));

                sheet.addCell(new Label(0, 4, "Услуга", tnr12ptFormat));
                sheet.addCell(new Label(1, 4, "Район", tnr12ptFormat));
                sheet.addCell(new Label(2, 4, "Кол-во", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            int i = 4;
            for (StandartCount uslAge : uslAges) {
                i++;
                sheet.addCell(new Label(0, i, uslAge.getTitle(), tnr12ptFormatLeft));
                sheet.addCell(new Label(1, i, uslAge.getPar(), tnr12ptFormatLeft));
                sheet.addCell(new Number(2, i, uslAge.getCount(), tnr12ptFormatRight));
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(350 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(150 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(50 * 37);
            sheet.setColumnView(2, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printPMPKStatus(ServletOutputStream os, Date date1, Date date2, List<PMPKStatus> reestr)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (reestr.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 4, 0);
                    sheet.addCell(new Label(0, 0, "Реестр детей, прошедших ПМПК за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 4, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Статус", tnr12ptFormat));
                    sheet.addCell(new Label(4, 3, "Район", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (PMPKStatus r : reestr) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, r.getFio(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, dateFormat.format(r.getDr()), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getStatus(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(4, i, r.getReg(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(300 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(100 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(50 * 37);
                sheet.setColumnView(3, cv);

                cv = sheet.getColumnView(4);
                cv.setSize(150 * 37);
                sheet.setColumnView(4, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printPMPKGia(ServletOutputStream os, Date date1, Date date2, List<PMPKR> reestr)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (reestr.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 3, 0);
                    sheet.addCell(new Label(0, 0, "Реестр детей, прошедших ПМПК для ГИА за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 3, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (PMPKR r : reestr) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, r.getFio(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, dateFormat.format(r.getDr()), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getReg(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(300 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(100 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(150 * 37);
                sheet.setColumnView(3, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printPMPKFirstOvz(ServletOutputStream os, Date date1, Date date2, List<PMPKR> reestr)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (reestr.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 3, 0);
                    sheet.addCell(new Label(0, 0, "Реестр детей, прошедших ПМПК, у которых впервые выявлены ОВЗ за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 3, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (PMPKR r : reestr) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, r.getFio(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, dateFormat.format(r.getDr()), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getReg(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(300 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(100 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(150 * 37);
                sheet.setColumnView(3, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printPMPKIpr(ServletOutputStream os, Date date1, Date date2, List<PMPKR> reestr)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (reestr.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 3, 0);
                    sheet.addCell(new Label(0, 0, "Реестр детей, прошедших ПМПК для оказания содействия в разработке ИПР ребенка-инвалида за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 3, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (PMPKR r : reestr) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, r.getFio(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, dateFormat.format(r.getDr()), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getReg(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(300 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(100 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(150 * 37);
                sheet.setColumnView(3, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printPMPKTer(ServletOutputStream os, Date date1, Date date2, List<PMPKTer> reestr)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (reestr.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 4, 0);
                    sheet.addCell(new Label(0, 0, "Реестр детей, прошедших ПМПК по направлению ТПМПК за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 4, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                    sheet.addCell(new Label(4, 3, "Направление", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (PMPKTer r : reestr) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, r.getFio(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, dateFormat.format(r.getDr()), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getReg(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(4, i, r.getTer(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(300 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(100 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(150 * 37);
                sheet.setColumnView(3, cv);

                cv = sheet.getColumnView(4);
                cv.setSize(150 * 37);
                sheet.setColumnView(4, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printPMPKRek(ServletOutputStream os, Date date1, Date date2, List<PMPKTer> reestr)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (reestr.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 4, 0);
                    sheet.addCell(new Label(0, 0, "Реестр детей, прошедших ПМПК за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 4, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                    sheet.addCell(new Label(4, 3, "Рекомендация", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (PMPKTer r : reestr) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, r.getFio(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, dateFormat.format(r.getDr()), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getReg(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(4, i, r.getTer(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(300 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(100 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(150 * 37);
                sheet.setColumnView(3, cv);

                cv = sheet.getColumnView(4);
                cv.setSize(400 * 37);
                sheet.setColumnView(4, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printStatPmpkStatus(ServletOutputStream os, Date date1, Date date2, List<CountStatusReg> count)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (count.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 3, 0);
                    sheet.addCell(new Label(0, 0, "Отчет ПМПК по статусам за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 3, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "Статус", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Район", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Кол-во", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (CountStatusReg c : count) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, c.getStatus(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, c.getReg(), tnr12ptFormatLeft));
                    sheet.addCell(new Number(3, i, c.getCount(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(100 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(150 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(50 * 37);
                sheet.setColumnView(3, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printStatPmpkRek(ServletOutputStream os, Date date1, Date date2, List<CountStatusReg> count)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (count.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 3, 0);
                    sheet.addCell(new Label(0, 0, "Отчет ПМПК по рекомендациям за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 3, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "Рекомендация", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Район", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Кол-во", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (CountStatusReg c : count) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, c.getStatus(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, c.getReg(), tnr12ptFormatLeft));
                    sheet.addCell(new Number(3, i, c.getCount(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(300 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(150 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(50 * 37);
                sheet.setColumnView(3, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printStatPmpkPar(ServletOutputStream os, Date date1, Date date2, List<CountStatusReg> count)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            if (count.size() != 0) {
                try {
                    sheet.mergeCells(0, 0, 3, 0);
                    sheet.addCell(new Label(0, 0, "Отчет ПМПК по параметрам (ИПР, ТПМПК, ГИА) за период ", tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 3, 1);
                    sheet.addCell(new Label(0, 1, "с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));

                    sheet.addCell(new Label(0, 3, "№п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "Параметр", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Район", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Кол-во", tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                int i = 3;
                for (CountStatusReg c : count) {
                    i++;
                    sheet.addCell(new Number(0, i, i - 3, tnr12ptFormatRight));
                    sheet.addCell(new Label(1, i, c.getStatus(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, c.getReg(), tnr12ptFormatLeft));
                    sheet.addCell(new Number(3, i, c.getCount(), tnr12ptFormatLeft));
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(50 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(140 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(150 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(50 * 37);
                sheet.setColumnView(3, cv);
            }
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static File printMonitReestrPMPK(Date date1, Date date2, List<ReestrMonitPMPK> reestr,
            String xlsName, String pmpkShname) throws WriteException, IOException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));
        File f = new File("tmp//" + xlsName);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(f, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;
            WritableCellFormat tnr12ptvcenterFormat;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldFormat.setWrap(true);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру по вертикали по центру
            WritableFont tnr12ptvcenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptvcenterFormat = new WritableCellFormat(tnr12ptvcenter);
            tnr12ptvcenterFormat.setAlignment(Alignment.CENTRE);
            tnr12ptvcenterFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptvcenterFormat.setWrap(true);
            tnr12ptvcenterFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            String region = reestr.get(0).getReg();

            try {
                sheet.mergeCells(0, 0, 13, 0);
                sheet.addCell(new Label(0, 0, "Реестр детей с ограниченными возможностями здоровья, прошедших обследование на " + pmpkShname, tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 13, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 2, 13, 2);
                sheet.addCell(new Label(0, 2, "проживающих: " + region, tnr12ptBoldFormat));
                sheet.mergeCells(0, 4, 0, 5);
                sheet.addCell(new Label(0, 4, "№ п/п", tnr12ptvcenterFormat));
                sheet.addCell(new Label(0, 6, "", tnr12ptvcenterFormat));
                sheet.mergeCells(1, 4, 1, 5);
                sheet.addCell(new Label(1, 4, "Номер в базе данных", tnr12ptvcenterFormat));
                sheet.addCell(new Label(1, 6, "", tnr12ptvcenterFormat));
                sheet.mergeCells(2, 4, 2, 5);
                sheet.addCell(new Label(2, 4, "ФИО ребенка", tnr12ptvcenterFormat));
                sheet.addCell(new Label(2, 6, "1", tnr12ptvcenterFormat));
                sheet.mergeCells(3, 4, 3, 5);
                sheet.addCell(new Label(3, 4, "Дата рождения", tnr12ptvcenterFormat));
                sheet.addCell(new Label(3, 6, "2", tnr12ptvcenterFormat));
                sheet.mergeCells(4, 4, 4, 5);
                sheet.addCell(new Label(4, 4, "Дата ПМПК", tnr12ptvcenterFormat));
                sheet.addCell(new Label(4, 6, "3", tnr12ptvcenterFormat));
                sheet.mergeCells(5, 4, 5, 5);
                sheet.addCell(new Label(5, 4, "№ протокола", tnr12ptvcenterFormat));
                sheet.addCell(new Label(5, 6, "4", tnr12ptvcenterFormat));
                sheet.mergeCells(6, 4, 6, 5);
                sheet.addCell(new Label(6, 4, "Предъявлено (не предъявлено) заключение ПМПК в образовательную организацию", tnr12ptvcenterFormat));
                sheet.addCell(new Label(6, 6, "5", tnr12ptvcenterFormat));
                sheet.mergeCells(7, 4, 7, 5);
                sheet.addCell(new Label(7, 4, "Образовательная организация, класс (группа), где обучается ребенок", tnr12ptvcenterFormat));
                sheet.addCell(new Label(7, 6, "6", tnr12ptvcenterFormat));
                sheet.mergeCells(8, 4, 12, 4);
                sheet.addCell(new Label(8, 4, "Создание специальных условий для получения образования ребенком с ОВЗ", tnr12ptvcenterFormat));
                sheet.addCell(new Label(8, 5, "Форма получения образования", tnr12ptvcenterFormat));
                sheet.addCell(new Label(8, 6, "7", tnr12ptvcenterFormat));
                sheet.addCell(new Label(9, 5, "Программа обучения", tnr12ptvcenterFormat));
                sheet.addCell(new Label(9, 6, "8", tnr12ptvcenterFormat));
                sheet.addCell(new Label(10, 5, "Занятия со специалистами", tnr12ptvcenterFormat));
                sheet.addCell(new Label(10, 6, "9", tnr12ptvcenterFormat));
                sheet.addCell(new Label(11, 5, "Услуги тьютора, ассистента (помощника)", tnr12ptvcenterFormat));
                sheet.addCell(new Label(11, 6, "10", tnr12ptvcenterFormat));
                sheet.addCell(new Label(12, 5, "Использование специального оборудования (перечислить из указанных в заключении ПМПК)", tnr12ptvcenterFormat));
                sheet.addCell(new Label(12, 6, "11", tnr12ptvcenterFormat));
                sheet.mergeCells(13, 4, 13, 5);
                sheet.addCell(new Label(13, 4, "Причины невыполнения рекомендаций ПМПК", tnr12ptvcenterFormat));
                sheet.addCell(new Label(13, 6, "12", tnr12ptvcenterFormat));

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 6;
            for (ReestrMonitPMPK child : reestr) {
                i++;
                j++;
                sheet.addCell(new Number(0, j, i, tnr12ptFormatRight));
                sheet.addCell(new Number(1, j, child.getNom(), tnr12ptFormatRight));
                sheet.addCell(new Label(2, j, child.getFio(), tnr12ptFormatLeft));
                sheet.addCell(new Label(3, j, dateFormat.format(child.getDr()), tnr12ptFormatLeft));
                sheet.addCell(new Label(4, j, dateFormat.format(child.getDatep()), tnr12ptFormatLeft));
                sheet.addCell(new Label(5, j, child.getNp(), tnr12ptFormatRight));
                sheet.addCell(new Label(6, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(7, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(8, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(9, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(10, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(11, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(12, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(13, j, "", tnr12ptFormatLeft));
            }

            CellView cvv = sheet.getRowView(0);
            cvv.setSize(21 * 37);
            sheet.setRowView(0, cvv);

            CellView cv = sheet.getColumnView(0);
            cv.setSize(40 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(60 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(300 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(90 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(90 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(105 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(105 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(250 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(100 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(200 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(200 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(100 * 37);
            sheet.setColumnView(11, cv);

            cv = sheet.getColumnView(12);
            cv.setSize(200 * 37);
            sheet.setColumnView(12, cv);

            cv = sheet.getColumnView(13);
            cv.setSize(200 * 37);
            sheet.setColumnView(13, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
        return f;
    }

    public static void printIpraRed(ServletOutputStream os, List<Ipra> ipraList)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            try {
                sheet.mergeCells(0, 0, 11, 0);
                sheet.addCell(new Label(0, 0, "Реестр ИПРА с ближайшими сроками на " + dateFormat.format(curDate), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                sheet.addCell(new Label(4, 3, "№ ИПРА", tnr12ptFormat));
                sheet.addCell(new Label(5, 3, "№ протокола", tnr12ptFormat));
                sheet.addCell(new Label(6, 3, "Дата протокола", tnr12ptFormat));
                sheet.addCell(new Label(7, 3, "№ приказа", tnr12ptFormat));
                sheet.addCell(new Label(8, 3, "Дата приказа", tnr12ptFormat));
                sheet.addCell(new Label(9, 3, "Дата отчета ОМСУ", tnr12ptFormat));
                sheet.addCell(new Label(10, 3, "Дата отчета ОЦППМСП", tnr12ptFormat));
                sheet.addCell(new Label(11, 3, "Дата отчета ДО", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 3;
            for (Ipra ipra : ipraList) {
                i++;
                j++;
                try {
                    sheet.addCell(new Number(0, j, i, tnr12ptFormatLeft));
                    sheet.addCell(new Label(1, j, ipra.getChildId().getFIO(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, j, ipra.getChildId().getFormat2Dr(), tnr12ptFormat));
                    sheet.addCell(new Label(3, j, ipra.getChildId().getSprregId().getSprregName(), tnr12ptFormat));
                    sheet.addCell(new Label(4, j, ipra.getIpraN(), tnr12ptFormat));
                    sheet.addCell(new Label(5, j, ipra.getIpraNexp(), tnr12ptFormat));
                    sheet.addCell(new Label(6, j, ipra.getFormat2Date(ipra.getIpraDateexp()), tnr12ptFormat));
                    sheet.addCell(new Label(7, j, ipra.getIpraPrikazN(), tnr12ptFormat));
                    sheet.addCell(new Label(8, j, ipra.getFormat2Date(ipra.getIpraPrikazD()), tnr12ptFormat));
                    sheet.addCell(new Label(9, j, ipra.getFormat2Date(ipra.getIpraOtchomsu()), tnr12ptFormat));
                    sheet.addCell(new Label(10, j, ipra.getFormat2Date(ipra.getIpraOtchcenter()), tnr12ptFormat));
                    sheet.addCell(new Label(11, j, ipra.getFormat2Date(ipra.getIpraOtchdo()), tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(22 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(320 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(85 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(100 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(130 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(130 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(85 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(70 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(85 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(85 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(85 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(85 * 37);
            sheet.setColumnView(11, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printIpra18Red(ServletOutputStream os, List<Ipra18> ipraList, List<Ipra18Prikaz> ipraPrikazList)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            try {
                sheet.mergeCells(0, 0, 11, 0);
                sheet.addCell(new Label(0, 0, "Реестр ИПРА с ближайшими сроками на " + dateFormat.format(curDate), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                sheet.addCell(new Label(4, 3, "№ ИПРА", tnr12ptFormat));
                sheet.addCell(new Label(5, 3, "№ протокола", tnr12ptFormat));
                sheet.addCell(new Label(6, 3, "Дата протокола", tnr12ptFormat));
                sheet.addCell(new Label(7, 3, "№ приказа", tnr12ptFormat));
                sheet.addCell(new Label(8, 3, "Дата приказа", tnr12ptFormat));
                sheet.addCell(new Label(9, 3, "Дата отчета ОМСУ", tnr12ptFormat));
                sheet.addCell(new Label(10, 3, "Дата отчета ОЦППМСП", tnr12ptFormat));
                sheet.addCell(new Label(11, 3, "Дата отчета ДО", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 3;
            for (Ipra18 ipra : ipraList) {
                Ipra18Prikaz prikaz = null;
                for (Ipra18Prikaz ip : ipraPrikazList) {
                    if (ip.getIpra18Id().equals(ipra)) {
                        prikaz = ip;
                    }
                }
                i++;
                j++;
                try {
                    sheet.addCell(new Number(0, j, i, tnr12ptFormatLeft));
                    sheet.addCell(new Label(1, j, ipra.getChildId().getFIO(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, j, ipra.getChildId().getFormat2Dr(), tnr12ptFormat));
                    sheet.addCell(new Label(3, j, ipra.getChildId().getSprregId().getSprregName(), tnr12ptFormat));
                    sheet.addCell(new Label(4, j, ipra.getIpra18N(), tnr12ptFormat));
                    sheet.addCell(new Label(5, j, ipra.getIpra18Nexp(), tnr12ptFormat));
                    sheet.addCell(new Label(6, j, ipra.getFormat2Date(ipra.getIpra18Dateexp()), tnr12ptFormat));
                    sheet.addCell(new Label(7, j, prikaz.getIpra18prikazDoN(), tnr12ptFormat));
                    sheet.addCell(new Label(8, j, ipra.getFormat2Date(prikaz.getIpra18prikazDoD()), tnr12ptFormat));
                    sheet.addCell(new Label(9, j, ipra.getFormat2Date(prikaz.getIpra18prikazOtchomsu()), tnr12ptFormat));
                    sheet.addCell(new Label(10, j, ipra.getFormat2Date(prikaz.getIpra18prikazOtchcenter()), tnr12ptFormat));
                    sheet.addCell(new Label(11, j, ipra.getFormat2Date(ipra.getIpra18Otchdo()), tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(22 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(320 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(85 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(100 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(130 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(130 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(85 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(70 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(85 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(85 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(85 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(85 * 37);
            sheet.setColumnView(11, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printIpra18Svod(ServletOutputStream os, List<Ipra18> ipraList, List<Ipra18Prikaz> ipraPrikazList)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            try {
                sheet.mergeCells(0, 0, 19, 0);
                sheet.addCell(new Label(0, 0, "Сводный реестр ИПРА", tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "Фамилия", tnr12ptFormat));
                sheet.mergeCells(2, 3, 2, 4);
                sheet.addCell(new Label(2, 3, "Имя", tnr12ptFormat));
                sheet.mergeCells(3, 3, 3, 4);
                sheet.addCell(new Label(3, 3, "Отчество", tnr12ptFormat));
                sheet.mergeCells(4, 3, 4, 4);
                sheet.addCell(new Label(4, 3, "Дата рождения", tnr12ptFormat));
                sheet.mergeCells(5, 3, 5, 4);
                sheet.addCell(new Label(5, 3, "Район", tnr12ptFormat));
                sheet.mergeCells(6, 3, 7, 3);
                sheet.addCell(new Label(6, 3, "Исходящее письмо из МСЭ", tnr12ptFormat));
                sheet.addCell(new Label(6, 4, "№", tnr12ptFormat));
                sheet.addCell(new Label(7, 4, "дата", tnr12ptFormat));
                sheet.mergeCells(8, 3, 8, 4);
                sheet.addCell(new Label(8, 3, "№ ИПРА", tnr12ptFormat));
                sheet.mergeCells(9, 3, 10, 3);
                sheet.addCell(new Label(9, 3, "Протокол экспертизы", tnr12ptFormat));
                sheet.addCell(new Label(9, 4, "№", tnr12ptFormat));
                sheet.addCell(new Label(10, 4, "дата", tnr12ptFormat));
                sheet.mergeCells(11, 3, 11, 4);
                sheet.addCell(new Label(11, 3, "Письмо в ОМСУ", tnr12ptFormat));
                sheet.mergeCells(12, 3, 13, 3);
                sheet.addCell(new Label(12, 3, "Запрос от ОМСУ", tnr12ptFormat));
                sheet.addCell(new Label(12, 4, "№", tnr12ptFormat));
                sheet.addCell(new Label(13, 4, "дата", tnr12ptFormat));
                sheet.mergeCells(14, 3, 15, 3);
                sheet.addCell(new Label(14, 3, "Перечень мероприятий", tnr12ptFormat));
                sheet.addCell(new Label(14, 4, "№", tnr12ptFormat));
                sheet.addCell(new Label(15, 4, "дата", tnr12ptFormat));
                sheet.mergeCells(16, 3, 17, 3);
                sheet.addCell(new Label(16, 3, "Входящее письмо в ДО из ОМСУ", tnr12ptFormat));
                sheet.addCell(new Label(16, 4, "№", tnr12ptFormat));
                sheet.addCell(new Label(17, 4, "дата", tnr12ptFormat));
                sheet.mergeCells(18, 3, 19, 3);
                sheet.addCell(new Label(18, 3, "Приказ", tnr12ptFormat));
                sheet.addCell(new Label(18, 4, "№", tnr12ptFormat));
                sheet.addCell(new Label(19, 4, "дата", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 4;
            for (Ipra18 ipra : ipraList) {
                Ipra18Prikaz prikaz = null;
                for (Ipra18Prikaz ip : ipraPrikazList) {
                    if (ip.getIpra18Id().equals(ipra)) {
                        prikaz = ip;
                    }
                }
                i++;
                j++;
                try {
                    sheet.addCell(new Number(0, j, i, tnr12ptFormatLeft));
                    sheet.addCell(new Label(1, j, ipra.getChildId().getChildFam(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, j, ipra.getChildId().getChildName(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, j, ipra.getChildId().getChildPatr(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(4, j, ipra.getChildId().getFormat2Dr(), tnr12ptFormat));
                    sheet.addCell(new Label(5, j, ipra.getChildId().getSprregId().getSprregName(), tnr12ptFormat));
                    sheet.addCell(new Label(6, j, ipra.getIpra18IshmseN(), tnr12ptFormat));
                    sheet.addCell(new Label(7, j, ipra.getFormat2Date(ipra.getIpra18IshmseD()), tnr12ptFormat));
                    sheet.addCell(new Label(8, j, ipra.getIpra18N(), tnr12ptFormat));
                    sheet.addCell(new Label(9, j, ipra.getIpra18Nexp(), tnr12ptFormat));
                    sheet.addCell(new Label(10, j, ipra.getFormat2Date(ipra.getIpra18Dateexp()), tnr12ptFormat));
                    sheet.addCell(new Label(11, j, "+", tnr12ptFormat));
                    sheet.addCell(new Label(12, j, prikaz.getIpra18prikazReqN(), tnr12ptFormat));
                    sheet.addCell(new Label(13, j, ipra.getFormat2Date(prikaz.getIpra18prikazReqD()), tnr12ptFormat));
                    sheet.addCell(new Label(14, j, prikaz.getIpra18prikazPerechN(), tnr12ptFormat));
                    sheet.addCell(new Label(15, j, ipra.getFormat2Date(prikaz.getIpra18prikazPerechD()), tnr12ptFormat));
                    sheet.addCell(new Label(16, j, ipra.getIpra18VhdoN(), tnr12ptFormat));
                    sheet.addCell(new Label(17, j, ipra.getFormat2Date(ipra.getIpra18VhdoD()), tnr12ptFormat));
                    sheet.addCell(new Label(18, j, prikaz.getIpra18prikazDoN(), tnr12ptFormat));
                    sheet.addCell(new Label(19, j, ipra.getFormat2Date(prikaz.getIpra18prikazDoD()), tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(48 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(130 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(130 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(130 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(112 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(130 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(60 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(90 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(140 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(140 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(90 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(130 * 37);
            sheet.setColumnView(11, cv);

            cv = sheet.getColumnView(12);
            cv.setSize(130 * 37);
            sheet.setColumnView(12, cv);

            cv = sheet.getColumnView(13);
            cv.setSize(90 * 37);
            sheet.setColumnView(13, cv);

            cv = sheet.getColumnView(14);
            cv.setSize(90 * 37);
            sheet.setColumnView(14, cv);

            cv = sheet.getColumnView(15);
            cv.setSize(90 * 37);
            sheet.setColumnView(15, cv);

            cv = sheet.getColumnView(16);
            cv.setSize(80 * 37);
            sheet.setColumnView(16, cv);

            cv = sheet.getColumnView(17);
            cv.setSize(90 * 37);
            sheet.setColumnView(17, cv);

            cv = sheet.getColumnView(18);
            cv.setSize(60 * 37);
            sheet.setColumnView(18, cv);

            cv = sheet.getColumnView(19);
            cv.setSize(90 * 37);
            sheet.setColumnView(19, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printOvzMonitResult(ServletOutputStream os, List<OvzMonit> resList)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            /*    try {
                sheet.mergeCells(0, 0, 8, 0);
                sheet.addCell(new Label(0, 0, "Реестр ИПРА с ближайшими сроками на " + dateFormat.format(curDate), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Район", tnr12ptFormat));
                sheet.addCell(new Label(4, 3, "№ приказа", tnr12ptFormat));
                sheet.addCell(new Label(5, 3, "Дата приказа", tnr12ptFormat));
                sheet.addCell(new Label(6, 3, "Дата отчета ОМСУ", tnr12ptFormat));
                sheet.addCell(new Label(7, 3, "Дата отчета ОЦППМСП", tnr12ptFormat));
                sheet.addCell(new Label(8, 3, "Дата отчета ДО", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
             */
            Integer i = 0;
            Integer j = 3;
            for (OvzMonit om : resList) {
                i++;
                j++;
                try {
                    sheet.addCell(new Number(0, j, i, tnr12ptFormatLeft));
                    String fio = "";
                    try {
                        fio = om.getFio();

                    } catch (Exception ex) {
                    }
                    String dr = "";
                    try {
                        dr = om.getDr();

                    } catch (Exception ex) {
                    }
                    String reg = "";
                    try {
                        reg = om.getReg();

                    } catch (Exception ex) {
                    }
                    String op = "";
                    try {
                        op = om.getOp();

                    } catch (Exception ex) {
                    }
                    String info = "";
                    try {
                        info = om.getInfo();

                    } catch (Exception ex) {
                    }
                    String dpmpk = "";
                    try {
                        dpmpk = om.getDatePmpk();

                    } catch (Exception ex) {
                    }
                    String obr = "";
                    try {
                        obr = om.getSprObr().getSprobrShname();

                    } catch (Exception ex) {
                    }
                    String var = "";
                    try {
                        var = om.getSprObrVar().getSprobrvarName();

                    } catch (Exception ex) {
                    }
                    String reg2 = "";
                    try {
                        reg2 = om.getSprRegion().getSprregName();

                    } catch (Exception ex) {
                    }

                    sheet.addCell(new Label(1, j, fio, tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, j, dr, tnr12ptFormat));
                    sheet.addCell(new Label(3, j, reg, tnr12ptFormat));
                    sheet.addCell(new Label(4, j, op, tnr12ptFormat));
                    sheet.addCell(new Label(5, j, dpmpk, tnr12ptFormat));
                    sheet.addCell(new Label(6, j, obr, tnr12ptFormat));
                    sheet.addCell(new Label(7, j, var, tnr12ptFormat));
                    sheet.addCell(new Label(8, j, reg2, tnr12ptFormat));
                    sheet.addCell(new Label(9, j, info, tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(50 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(320 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(85 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(100 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(320 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(85 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(200 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(100 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(85 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(100 * 37);
            sheet.setColumnView(9, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printOvzFgos(ServletOutputStream os, List<OvzFgos> ovzFgosList)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);

            WritableCellFormat tnr16ptBoldFormat;
            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr11ptFormat;
            WritableCellFormat tnr12ptBoldBorderFormat;

            // жирный 16 по центру
            WritableFont tnr16ptBold = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD);
            tnr16ptBoldFormat = new WritableCellFormat(tnr16ptBold);
            tnr16ptBoldFormat.setAlignment(Alignment.CENTRE);

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // жирный по центру c границами
            WritableFont tnr12ptBoldBorder = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldBorderFormat = new WritableCellFormat(tnr12ptBoldBorder);
            tnr12ptBoldBorderFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldBorderFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptBoldBorderFormat.setWrap(true);

            // 11 по центру c границей сверху
            WritableFont tnr11pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr11ptFormat = new WritableCellFormat(tnr11pt);
            tnr11ptFormat.setAlignment(Alignment.CENTRE);
            tnr11ptFormat.setBorder(Border.TOP, BorderLineStyle.THIN);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeft.setWrap(true);

            Set<String> reg = new HashSet<>();
            for (OvzFgos ovzFgos : ovzFgosList) {
                reg.add(ovzFgos.getReg());
            }
            List<String> regList = new ArrayList<>();
            regList.addAll(reg);
            Collections.sort(regList, Collections.reverseOrder());

            for (String r : regList) {
                sheet = workbook.createSheet(r, 0);
                try {
                    sheet.mergeCells(0, 1, 16, 1);
                    sheet.addCell(new Label(0, 1, "Информация о введении ФГОС ОВЗ в 2017 - 2018 учебном году на " + dateFormat.format(curDate), tnr16ptBoldFormat));
                    sheet.mergeCells(0, 2, 16, 2);
                    sheet.addCell(new Label(0, 2, r, tnr16ptBoldFormat));
                    sheet.mergeCells(0, 3, 16, 3);
                    sheet.addCell(new Label(0, 3, "наименование муниципального образования", tnr11ptFormat));
                    sheet.mergeCells(0, 5, 0, 8);
                    sheet.addCell(new Label(0, 5, "Наименование общеобразовательной организации", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(1, 5, 1, 8);
                    sheet.addCell(new Label(1, 5, "ФИО ребенка", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(2, 5, 2, 8);
                    sheet.addCell(new Label(2, 5, "Дата рождения", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(3, 5, 3, 8);
                    sheet.addCell(new Label(3, 5, "Дата начала обучения", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(4, 5, 4, 8);
                    sheet.addCell(new Label(4, 5, "Класс (1, 2 / обычный - \"Об\"; специальный - \"Сп\")", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(5, 5, 5, 8);
                    sheet.addCell(new Label(5, 5, "Наименование ПМПК", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(6, 5, 6, 8);
                    sheet.addCell(new Label(6, 5, "Дата ПМПК", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(7, 5, 7, 8);
                    sheet.addCell(new Label(7, 5, "№ протокола", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(8, 5, 16, 5);
                    sheet.addCell(new Label(8, 5, "Создание специальных условий для получения образования ребенком с ОВЗ", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(8, 6, 8, 8);
                    sheet.addCell(new Label(8, 6, "Форма получения образования и обучения", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(9, 6, 9, 8);
                    sheet.addCell(new Label(9, 6, "Программа обучения", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(10, 6, 14, 6);
                    sheet.addCell(new Label(10, 6, "Занятия со специалистами", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(10, 7, 10, 8);
                    sheet.addCell(new Label(10, 7, "Учитель-логопед", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(11, 7, 11, 8);
                    sheet.addCell(new Label(11, 7, "Учитель-дефектолог (олигофренопедагог)", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(12, 7, 12, 8);
                    sheet.addCell(new Label(12, 7, "Учитель-дефектолог (тифлопедагог)", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(13, 7, 13, 8);
                    sheet.addCell(new Label(13, 7, "Учитель-дефектолог (сурдопедагог)", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(14, 7, 14, 8);
                    sheet.addCell(new Label(14, 7, "Педагог-психолог", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(15, 6, 15, 8);
                    sheet.addCell(new Label(15, 6, "Услуги тьютора, ассистента (помощника)", tnr12ptBoldBorderFormat));
                    sheet.mergeCells(16, 6, 16, 8);
                    sheet.addCell(new Label(16, 6, "Использование специального оборудования (перечислить из указанных в заключении ПМПК)", tnr12ptBoldBorderFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

                try {
                    sheet.addCell(new Number(0, 9, 1, tnr12ptFormat));
                    sheet.addCell(new Number(1, 9, 2, tnr12ptFormat));
                    sheet.addCell(new Number(2, 9, 3, tnr12ptFormat));
                    sheet.addCell(new Number(3, 9, 4, tnr12ptFormat));
                    sheet.addCell(new Number(4, 9, 5, tnr12ptFormat));
                    sheet.addCell(new Number(5, 9, 6, tnr12ptFormat));
                    sheet.addCell(new Number(6, 9, 7, tnr12ptFormat));
                    sheet.addCell(new Number(7, 9, 8, tnr12ptFormat));
                    sheet.addCell(new Number(8, 9, 9, tnr12ptFormat));
                    sheet.addCell(new Number(9, 9, 10, tnr12ptFormat));
                    sheet.addCell(new Number(10, 9, 11, tnr12ptFormat));
                    sheet.addCell(new Number(11, 9, 12, tnr12ptFormat));
                    sheet.addCell(new Number(12, 9, 13, tnr12ptFormat));
                    sheet.addCell(new Number(13, 9, 14, tnr12ptFormat));
                    sheet.addCell(new Number(14, 9, 15, tnr12ptFormat));
                    sheet.addCell(new Number(15, 9, 16, tnr12ptFormat));
                    sheet.addCell(new Number(16, 9, 17, tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

                Integer j = 10;
                Set<String> oo = new TreeSet<>();
                for (OvzFgos of : ovzFgosList) {
                    if (of.getReg().equals(r)) {
                        oo.add(of.getOo());
                    }
                }
                List<String> ooList = new ArrayList<>();
                ooList.addAll(oo);
                Collections.sort(ooList);

                for (String o : ooList) {
                    List<OvzFgos> ofOo = new ArrayList<>();
                    for (OvzFgos of : ovzFgosList) {
                        if (of.getReg().equals(r) && of.getOo().equals(o)) {
                            ofOo.add(of);
                        }
                    }
                    Collections.sort(ofOo, new OvzFgosComparator());
                    sheet.mergeCells(0, j, 0, j + ofOo.size() - 1);
                    sheet.addCell(new Label(0, j, o, tnr12ptFormatLeft));
                    for (OvzFgos of : ofOo) {
                        sheet.addCell(new Label(1, j, of.getFio(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(2, j, of.getRegularFormatDate(of.getDr()), tnr12ptFormatLeft));
                        String dat = of.getRegularFormatDate(of.getBeginEdu());
                        if (dat.equals("01.01.2000")) {
                            dat = "";
                        }
                        sheet.addCell(new Label(3, j, dat, tnr12ptFormatLeft));
                        sheet.addCell(new Label(4, j, "", tnr12ptFormatLeft));
                        sheet.addCell(new Label(5, j, of.getPmpkName(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(6, j, of.getRegularFormatDate(of.getPmpkDate()), tnr12ptFormatLeft));
                        sheet.addCell(new Label(7, j, of.getNp(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(8, j, "", tnr12ptFormatLeft));
                        sheet.addCell(new Label(9, j, of.getObr(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(10, j, of.getRekLogo(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(11, j, of.getRekDefOl(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(12, j, of.getRekDefTiflo(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(13, j, of.getRekDefSurdo(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(14, j, of.getRekPsy(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(15, j, of.getRekAssist(), tnr12ptFormatLeft));
                        sheet.addCell(new Label(16, j, of.getRekEquip(), tnr12ptFormatLeft));
                        j++;
                    }
                }
                CellView cv = sheet.getColumnView(0);
                cv.setSize(280 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(280 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(85 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(85 * 37);
                sheet.setColumnView(3, cv);

                cv = sheet.getColumnView(4);
                cv.setSize(85 * 37);
                sheet.setColumnView(4, cv);

                cv = sheet.getColumnView(5);
                cv.setSize(100 * 37);
                sheet.setColumnView(5, cv);

                cv = sheet.getColumnView(6);
                cv.setSize(85 * 37);
                sheet.setColumnView(6, cv);

                cv = sheet.getColumnView(7);
                cv.setSize(85 * 37);
                sheet.setColumnView(7, cv);

                cv = sheet.getColumnView(8);
                cv.setSize(100 * 37);
                sheet.setColumnView(8, cv);

                cv = sheet.getColumnView(9);
                cv.setSize(260 * 37);
                sheet.setColumnView(9, cv);

                cv = sheet.getColumnView(10);
                cv.setSize(100 * 37);
                sheet.setColumnView(10, cv);

                cv = sheet.getColumnView(11);
                cv.setSize(100 * 37);
                sheet.setColumnView(11, cv);

                cv = sheet.getColumnView(12);
                cv.setSize(100 * 37);
                sheet.setColumnView(12, cv);

                cv = sheet.getColumnView(13);
                cv.setSize(100 * 37);
                sheet.setColumnView(13, cv);

                cv = sheet.getColumnView(14);
                cv.setSize(100 * 37);
                sheet.setColumnView(14, cv);

                cv = sheet.getColumnView(15);
                cv.setSize(100 * 37);
                sheet.setColumnView(15, cv);

                cv = sheet.getColumnView(16);
                cv.setSize(100 * 37);
                sheet.setColumnView(16, cv);
            }

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static File printOvz(List<OvzFgos> ovzList, String xlsName)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));
        File f = new File("tmp//" + xlsName);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(f, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr16ptBoldFormat;
            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr11ptFormat;
            WritableCellFormat tnr12ptBoldBorderFormat;

            // жирный 16 по центру
            WritableFont tnr16ptBold = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD);
            tnr16ptBoldFormat = new WritableCellFormat(tnr16ptBold);
            tnr16ptBoldFormat.setAlignment(Alignment.CENTRE);

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // жирный по центру c границами
            WritableFont tnr12ptBoldBorder = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldBorderFormat = new WritableCellFormat(tnr12ptBoldBorder);
            tnr12ptBoldBorderFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldBorderFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptBoldBorderFormat.setWrap(true);

            // 11 по центру c границей сверху
            WritableFont tnr11pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr11ptFormat = new WritableCellFormat(tnr11pt);
            tnr11ptFormat.setAlignment(Alignment.CENTRE);
            tnr11ptFormat.setBorder(Border.TOP, BorderLineStyle.THIN);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeft.setWrap(true);
            String r = ovzList.get(0).getReg();
            try {
                sheet.mergeCells(0, 1, 17, 1);
                sheet.addCell(new Label(0, 1, "Реестр детей с ОВЗ, проживающих на территории муниципального образования", tnr16ptBoldFormat));
                sheet.mergeCells(0, 2, 17, 2);
                sheet.addCell(new Label(0, 2, r, tnr16ptBoldFormat));
                sheet.mergeCells(0, 3, 17, 3);
                sheet.addCell(new Label(0, 3, "наименование муниципального образования", tnr11ptFormat));
                sheet.mergeCells(0, 5, 0, 8);
                sheet.addCell(new Label(0, 5, "№ п/п", tnr12ptBoldBorderFormat));
                sheet.mergeCells(1, 5, 1, 8);
                sheet.addCell(new Label(1, 5, "ФИО ребенка", tnr12ptBoldBorderFormat));
                sheet.mergeCells(2, 5, 2, 8);
                sheet.addCell(new Label(2, 5, "Дата рождения", tnr12ptBoldBorderFormat));
                sheet.mergeCells(3, 5, 3, 8);
                sheet.addCell(new Label(3, 5, "Наименование общеобразовательной организации", tnr12ptBoldBorderFormat));
                sheet.mergeCells(4, 5, 4, 8);
                sheet.addCell(new Label(4, 5, "Дата начала обучения", tnr12ptBoldBorderFormat));
                sheet.mergeCells(5, 5, 5, 8);
                sheet.addCell(new Label(5, 5, "Класс / группа", tnr12ptBoldBorderFormat));
                sheet.mergeCells(6, 5, 6, 8);
                sheet.addCell(new Label(6, 5, "Наименование ПМПК", tnr12ptBoldBorderFormat));
                sheet.mergeCells(7, 5, 7, 8);
                sheet.addCell(new Label(7, 5, "Дата ПМПК", tnr12ptBoldBorderFormat));
                sheet.mergeCells(8, 5, 8, 8);
                sheet.addCell(new Label(8, 5, "№ протокола", tnr12ptBoldBorderFormat));
                sheet.mergeCells(9, 5, 17, 5);
                sheet.addCell(new Label(9, 5, "Создание специальных условий для получения образования ребенком с ОВЗ", tnr12ptBoldBorderFormat));
                sheet.mergeCells(9, 6, 9, 8);
                sheet.addCell(new Label(9, 6, "Форма получения образования и обучения", tnr12ptBoldBorderFormat));
                sheet.mergeCells(10, 6, 10, 8);
                sheet.addCell(new Label(10, 6, "Программа обучения", tnr12ptBoldBorderFormat));
                sheet.mergeCells(11, 6, 15, 6);
                sheet.addCell(new Label(11, 6, "Занятия со специалистами", tnr12ptBoldBorderFormat));
                sheet.mergeCells(11, 7, 11, 8);
                sheet.addCell(new Label(11, 7, "Учитель-логопед", tnr12ptBoldBorderFormat));
                sheet.mergeCells(12, 7, 12, 8);
                sheet.addCell(new Label(12, 7, "Учитель-дефектолог (олигофренопедагог)", tnr12ptBoldBorderFormat));
                sheet.mergeCells(13, 7, 13, 8);
                sheet.addCell(new Label(13, 7, "Учитель-дефектолог (тифлопедагог)", tnr12ptBoldBorderFormat));
                sheet.mergeCells(14, 7, 14, 8);
                sheet.addCell(new Label(14, 7, "Учитель-дефектолог (сурдопедагог)", tnr12ptBoldBorderFormat));
                sheet.mergeCells(15, 7, 15, 8);
                sheet.addCell(new Label(15, 7, "Педагог-психолог", tnr12ptBoldBorderFormat));
                sheet.mergeCells(16, 6, 16, 8);
                sheet.addCell(new Label(16, 6, "Услуги тьютора, ассистента (помощника)", tnr12ptBoldBorderFormat));
                sheet.mergeCells(17, 6, 17, 8);
                sheet.addCell(new Label(17, 6, "Использование специального оборудования", tnr12ptBoldBorderFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            try {
                sheet.addCell(new Number(0, 9, 1, tnr12ptFormat));
                sheet.addCell(new Number(1, 9, 2, tnr12ptFormat));
                sheet.addCell(new Number(2, 9, 3, tnr12ptFormat));
                sheet.addCell(new Number(3, 9, 4, tnr12ptFormat));
                sheet.addCell(new Number(4, 9, 5, tnr12ptFormat));
                sheet.addCell(new Number(5, 9, 6, tnr12ptFormat));
                sheet.addCell(new Number(6, 9, 7, tnr12ptFormat));
                sheet.addCell(new Number(7, 9, 8, tnr12ptFormat));
                sheet.addCell(new Number(8, 9, 9, tnr12ptFormat));
                sheet.addCell(new Number(9, 9, 10, tnr12ptFormat));
                sheet.addCell(new Number(10, 9, 11, tnr12ptFormat));
                sheet.addCell(new Number(11, 9, 12, tnr12ptFormat));
                sheet.addCell(new Number(12, 9, 13, tnr12ptFormat));
                sheet.addCell(new Number(13, 9, 14, tnr12ptFormat));
                sheet.addCell(new Number(14, 9, 15, tnr12ptFormat));
                sheet.addCell(new Number(15, 9, 16, tnr12ptFormat));
                sheet.addCell(new Number(16, 9, 17, tnr12ptFormat));
                sheet.addCell(new Number(17, 9, 18, tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer j = 10;

            for (OvzFgos of : ovzList) {
                sheet.addCell(new Number(0, j, j - 9, tnr12ptFormatLeft));
                sheet.addCell(new Label(1, j, of.getFio(), tnr12ptFormatLeft));
                sheet.addCell(new Label(2, j, of.getRegularFormatDate(of.getDr()), tnr12ptFormatLeft));
                sheet.addCell(new Label(3, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(4, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(5, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(6, j, of.getPmpkName(), tnr12ptFormatLeft));
                sheet.addCell(new Label(7, j, of.getRegularFormatDate(of.getPmpkDate()), tnr12ptFormatLeft));
                sheet.addCell(new Label(8, j, of.getNp(), tnr12ptFormatLeft));
                sheet.addCell(new Label(9, j, "", tnr12ptFormatLeft));
                sheet.addCell(new Label(10, j, of.getObr(), tnr12ptFormatLeft));
                sheet.addCell(new Label(11, j, of.getRekLogo(), tnr12ptFormatLeft));
                sheet.addCell(new Label(12, j, of.getRekDefOl(), tnr12ptFormatLeft));
                sheet.addCell(new Label(13, j, of.getRekDefTiflo(), tnr12ptFormatLeft));
                sheet.addCell(new Label(14, j, of.getRekDefSurdo(), tnr12ptFormatLeft));
                sheet.addCell(new Label(15, j, of.getRekPsy(), tnr12ptFormatLeft));
                sheet.addCell(new Label(16, j, of.getRekAssist(), tnr12ptFormatLeft));
                sheet.addCell(new Label(17, j, of.getRekEquip(), tnr12ptFormatLeft));
                j++;
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(30 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(280 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(85 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(280 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(85 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(85 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(100 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(85 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(85 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(100 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(260 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(100 * 37);
            sheet.setColumnView(11, cv);

            cv = sheet.getColumnView(12);
            cv.setSize(100 * 37);
            sheet.setColumnView(12, cv);

            cv = sheet.getColumnView(13);
            cv.setSize(120 * 37);
            sheet.setColumnView(13, cv);

            cv = sheet.getColumnView(14);
            cv.setSize(100 * 37);
            sheet.setColumnView(14, cv);

            cv = sheet.getColumnView(15);
            cv.setSize(100 * 37);
            sheet.setColumnView(15, cv);

            cv = sheet.getColumnView(16);
            cv.setSize(100 * 37);
            sheet.setColumnView(16, cv);

            cv = sheet.getColumnView(17);
            cv.setSize(120 * 37);
            sheet.setColumnView(17, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
        return f;

    }

    public static void printReestrUsl(ServletOutputStream os, Date date1, Date date2,
            List<ReestrUsl> reestrUslList, SprUsl usl)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            try {
                sheet.mergeCells(0, 0, 3, 0);
                sheet.addCell(new Label(0, 0, "Реестр клиентов по услуге: ", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 3, 1);
                sheet.addCell(new Label(0, 1, usl.getSpruslName(), tnr12ptBoldFormat));
                sheet.mergeCells(0, 2, 3, 2);
                sheet.addCell(new Label(0, 2, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "Дата", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "ФИО клиента", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "ФИО специалистов (должность)", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 3;
            for (ReestrUsl ru : reestrUslList) {
                i++;
                try {
                    sheet.addCell(new Label(0, i, dateFormat.format(ru.getDate()), tnr12ptFormatLeft));
                    sheet.addCell(new Label(1, i, ru.getFio(), tnr12ptFormatLeft));
                    Date dr = ru.getDr();
                    String drS = "";
                    if (dr != null) {
                        drS = dateFormat.format(dr);
                    }
                    sheet.addCell(new Label(2, i, drS, tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, ru.getSpec(), tnr12ptFormatLeft));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(80 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(320 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(112 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(320 * 37);
            sheet.setColumnView(3, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printReestrPMPKFull(ServletOutputStream os, Date date1, Date date2, List<ReestrPMPKFull> reestr, String pmpkShname) throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldFormat.setWrap(true);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 18, 0);
                sheet.addCell(new Label(0, 0, "Реестр детей с ограниченными возможностями здоровья и (или) девиантным (общественно опасным) поведением, прошедших обследование на " + pmpkShname, tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 18, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                sheet.mergeCells(2, 3, 2, 4);
                sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                sheet.mergeCells(3, 3, 3, 4);
                sheet.addCell(new Label(3, 3, "Возраст", tnr12ptFormat));
                sheet.mergeCells(4, 3, 4, 4);
                sheet.addCell(new Label(4, 3, "Пол", tnr12ptFormat));
                sheet.mergeCells(5, 3, 5, 4);
                sheet.addCell(new Label(5, 3, "Мун.район/ городской округ", tnr12ptFormat));
                sheet.mergeCells(6, 3, 6, 4);
                sheet.addCell(new Label(6, 3, "Ребенок с ОВЗ", tnr12ptFormat));
                sheet.mergeCells(7, 3, 7, 4);
                sheet.addCell(new Label(7, 3, "Ребенок с девиантным (общественно опасным) поведением", tnr12ptFormat));
                sheet.mergeCells(8, 3, 8, 4);
                sheet.addCell(new Label(8, 3, "Ребенок-инвалид", tnr12ptFormat));
                sheet.mergeCells(9, 3, 9, 4);
                sheet.addCell(new Label(9, 3, "Сирота", tnr12ptFormat));
                sheet.mergeCells(10, 3, 10, 4);
                sheet.addCell(new Label(10, 3, "Дата ПМПК", tnr12ptFormat));
                sheet.mergeCells(11, 3, 11, 4);
                sheet.addCell(new Label(11, 3, "Заключение ПМПК", tnr12ptFormat));
                sheet.mergeCells(12, 3, 12, 4);
                sheet.addCell(new Label(12, 3, "Срок действия заключения", tnr12ptFormat));
                sheet.mergeCells(13, 3, 18, 3);
                sheet.addCell(new Label(13, 3, "Рекомендации", tnr12ptFormat));
                sheet.addCell(new Label(13, 4, "ассистент (помощник)", tnr12ptFormat));
                sheet.addCell(new Label(14, 4, "тьютор", tnr12ptFormat));
                sheet.addCell(new Label(15, 4, "педагог-психолог", tnr12ptFormat));
                sheet.addCell(new Label(16, 4, "учитель-логопед", tnr12ptFormat));
                sheet.addCell(new Label(17, 4, "учитель-дефектолог (олигофренопедагог)", tnr12ptFormat));
                sheet.addCell(new Label(18, 4, "учитель-дефектолог (тифлопедагога)", tnr12ptFormat));
                sheet.addCell(new Label(19, 4, "учитель-дефектолог (сурдопедагога)", tnr12ptFormat));
                sheet.addCell(new Label(20, 4, "социальный педагог", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 4;
            for (ReestrPMPKFull child : reestr) {
                i++;
                j++;
                sheet.addCell(new Number(0, j, i, tnr12ptFormatRight));
                sheet.addCell(new Label(1, j, child.getFio(), tnr12ptFormatLeft));
                sheet.addCell(new Label(2, j, dateFormat.format(child.getDr()), tnr12ptFormatLeft));
                sheet.addCell(new Label(3, j, child.getAge().toString(), tnr12ptFormatLeft));
                sheet.addCell(new Label(4, j, child.getSex(), tnr12ptFormatLeft));
                sheet.addCell(new Label(5, j, child.getReg(), tnr12ptFormatLeft));
                if (!child.getOvz().equals("")) {
                    sheet.addCell(new Label(6, j, child.getOvz(), tnr12ptFormat));
                } else if (child.getOvz().equals("")) {
                    sheet.addCell(new Label(6, j, "-", tnr12ptFormat));
                }
                if (child.getOp()) {
                    sheet.addCell(new Label(7, j, "+", tnr12ptFormat));
                } else if (!child.getOp()) {
                    sheet.addCell(new Label(7, j, "-", tnr12ptFormat));
                }
                if (child.getDi()) {
                    sheet.addCell(new Label(8, j, "+", tnr12ptFormat));
                } else if (!child.getDi()) {
                    sheet.addCell(new Label(8, j, "-", tnr12ptFormat));
                }
                if (child.getDs()) {
                    sheet.addCell(new Label(9, j, "+", tnr12ptFormat));
                } else if (!child.getDs()) {
                    sheet.addCell(new Label(9, j, "-", tnr12ptFormat));
                }
                sheet.addCell(new Label(10, j, dateFormat.format(child.getDatep()), tnr12ptFormatLeft));
                sheet.addCell(new Label(11, j, child.getObr(), tnr12ptFormatLeft));
                String datek = "";
                if (child.getDatek() != null) {
                    datek = dateFormat.format(child.getDatek());
                }
                sheet.addCell(new Label(12, j, datek, tnr12ptFormatLeft));
                if (child.getAssist()) {
                    sheet.addCell(new Label(13, j, "+", tnr12ptFormat));
                } else if (!child.getAssist()) {
                    sheet.addCell(new Label(13, j, "-", tnr12ptFormat));
                }
                if (child.getTutor()) {
                    sheet.addCell(new Label(14, j, "+", tnr12ptFormat));
                } else if (!child.getTutor()) {
                    sheet.addCell(new Label(14, j, "-", tnr12ptFormat));
                }
                if (child.getPsy()) {
                    sheet.addCell(new Label(15, j, "+", tnr12ptFormat));
                } else if (!child.getPsy()) {
                    sheet.addCell(new Label(15, j, "-", tnr12ptFormat));
                }
                if (child.getLogo()) {
                    sheet.addCell(new Label(16, j, "+", tnr12ptFormat));
                } else if (!child.getLogo()) {
                    sheet.addCell(new Label(16, j, "-", tnr12ptFormat));
                }
                if (child.getDefolig()) {
                    sheet.addCell(new Label(17, j, "+", tnr12ptFormat));
                } else if (!child.getDefolig()) {
                    sheet.addCell(new Label(17, j, "-", tnr12ptFormat));
                }
                if (child.getDeftiflo()) {
                    sheet.addCell(new Label(18, j, "+", tnr12ptFormat));
                } else if (!child.getDeftiflo()) {
                    sheet.addCell(new Label(18, j, "-", tnr12ptFormat));
                }
                if (child.getDefsurdo()) {
                    sheet.addCell(new Label(19, j, "+", tnr12ptFormat));
                } else if (!child.getDefsurdo()) {
                    sheet.addCell(new Label(19, j, "-", tnr12ptFormat));
                }
                if (child.getSoc()) {
                    sheet.addCell(new Label(20, j, "+", tnr12ptFormat));
                } else if (!child.getSoc()) {
                    sheet.addCell(new Label(20, j, "-", tnr12ptFormat));
                }
            }

            CellView cvv = sheet.getRowView(0);
            cvv.setSize(21 * 37);
            sheet.setRowView(0, cvv);

            CellView cv = sheet.getColumnView(0);
            cv.setSize(40 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(300 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(90 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(90 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(90 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(120 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(105 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(105 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(80 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(80 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(110 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(725 * 37);
            sheet.setColumnView(11, cv);

            cv = sheet.getColumnView(12);
            cv.setSize(110 * 37);
            sheet.setColumnView(12, cv);

            cv = sheet.getColumnView(13);
            cv.setSize(80 * 37);
            sheet.setColumnView(13, cv);

            cv = sheet.getColumnView(14);
            cv.setSize(80 * 37);
            sheet.setColumnView(14, cv);

            cv = sheet.getColumnView(15);
            cv.setSize(80 * 37);
            sheet.setColumnView(15, cv);

            cv = sheet.getColumnView(16);
            cv.setSize(80 * 37);
            sheet.setColumnView(16, cv);

            cv = sheet.getColumnView(17);
            cv.setSize(80 * 37);
            sheet.setColumnView(17, cv);

            cv = sheet.getColumnView(18);
            cv.setSize(80 * 37);
            sheet.setColumnView(18, cv);

            cv = sheet.getColumnView(19);
            cv.setSize(80 * 37);
            sheet.setColumnView(19, cv);

            cv = sheet.getColumnView(20);
            cv.setSize(80 * 37);
            sheet.setColumnView(20, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    public static void printIpra18NoInfo(ServletOutputStream os, List<Ipra18> ipra18NoInfoList, Date dateN, Date dateK)
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            try {
                sheet.mergeCells(0, 0, 9, 0);
                sheet.addCell(new Label(0, 0, "Реестр ИПРА, по которым нет запроса/отказа", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 9, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(dateN) + " по " + dateFormat.format(dateK), tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "Фамилия", tnr12ptFormat));
                sheet.mergeCells(2, 3, 2, 4);
                sheet.addCell(new Label(2, 3, "Имя", tnr12ptFormat));
                sheet.mergeCells(3, 3, 3, 4);
                sheet.addCell(new Label(3, 3, "Отчество", tnr12ptFormat));
                sheet.mergeCells(4, 3, 4, 4);
                sheet.addCell(new Label(4, 3, "Дата рождения", tnr12ptFormat));
                sheet.mergeCells(5, 3, 5, 4);
                sheet.addCell(new Label(5, 3, "Район", tnr12ptFormat));
                sheet.mergeCells(6, 3, 7, 3);
                sheet.addCell(new Label(6, 3, "Исходящее письмо из МСЭ", tnr12ptFormat));
                sheet.addCell(new Label(6, 4, "№", tnr12ptFormat));
                sheet.addCell(new Label(7, 4, "дата", tnr12ptFormat));
                sheet.mergeCells(8, 3, 8, 4);
                sheet.addCell(new Label(8, 3, "№ ИПРА", tnr12ptFormat));
                sheet.mergeCells(9, 3, 9, 4);
                sheet.addCell(new Label(9, 3, "Дата протокола", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 4;

            for (Ipra18 ipra : ipra18NoInfoList) {
                i++;
                j++;
                try {
                    sheet.addCell(new Number(0, j, i, tnr12ptFormatLeft));
                    sheet.addCell(new Label(1, j, ipra.getChildId().getChildFam(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, j, ipra.getChildId().getChildName(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, j, ipra.getChildId().getChildPatr(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(4, j, ipra.getChildId().getFormat2Dr(), tnr12ptFormat));
                    sheet.addCell(new Label(5, j, ipra.getChildId().getSprregId().getSprregName(), tnr12ptFormat));
                    sheet.addCell(new Label(6, j, ipra.getIpra18IshmseN(), tnr12ptFormat));
                    sheet.addCell(new Label(7, j, ipra.getFormat2Date(ipra.getIpra18IshmseD()), tnr12ptFormat));
                    sheet.addCell(new Label(8, j, ipra.getIpra18N(), tnr12ptFormat));
                    sheet.addCell(new Label(9, j, ipra.getFormat2Date(ipra.getIpra18Dateexp()), tnr12ptFormat));
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(48 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(130 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(130 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(130 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(112 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(130 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(60 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(90 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(130 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(140 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(90 * 37);
            sheet.setColumnView(9, cv);

        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

    }

    public static void printOtchetUslKat(ServletOutputStream os, Date date1, Date date2, List<Gz> gzs) throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 8, 0);
                sheet.addCell(new Label(0, 0, "Отчет по услугам и категориям", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 8, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "Услуга", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "Условия оказания услуги", tnr12ptFormat));
                sheet.mergeCells(2, 3, 2, 4);
                sheet.addCell(new Label(2, 3, "Направления", tnr12ptFormat));
                sheet.mergeCells(3, 3, 5, 3);
                sheet.addCell(new Label(3, 3, "Кол-во человек", tnr12ptFormat));
                sheet.addCell(new Label(3, 4, "Дети", tnr12ptFormat));
                sheet.addCell(new Label(4, 4, "Законные представители", tnr12ptFormat));
                sheet.addCell(new Label(5, 4, "Педагоги", tnr12ptFormat));
                /*    sheet.mergeCells(6, 3, 8, 3);
                sheet.addCell(new Label(6, 3, "Кол-во услуг", tnr12ptFormat));
                sheet.addCell(new Label(6, 4, "Дети", tnr12ptFormat));
                sheet.addCell(new Label(7, 4, "Законные представители", tnr12ptFormat));
                sheet.addCell(new Label(8, 4, "Педагоги", tnr12ptFormat));*/
                sheet.mergeCells(6, 3, 8, 3);
                sheet.addCell(new Label(6, 3, "Кол-во обращений", tnr12ptFormat));
                sheet.addCell(new Label(6, 4, "Дети", tnr12ptFormat));
                sheet.addCell(new Label(7, 4, "Законные представители", tnr12ptFormat));
                sheet.addCell(new Label(8, 4, "Педагоги", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Set<String> osnUslList = new HashSet<>();
            for (Gz gz : gzs) {
                if ((gz.getClientCh() != 0) || (gz.getClientPar() != 0) || (gz.getClientPed() != 0)) {
                    osnUslList.add(gz.getOsnUsl());
                }
            }

            Integer i;
            Integer j = 4;
            for (String osnUsl : osnUslList) {
                j++;
                i = j;
                sheet.addCell(new Label(0, j, osnUsl, tnr12ptFormatLeftCenter));
                for (Gz gz : gzs) {
                    if ((gz.getOsnUsl().equals(osnUsl)) && (gz.getCenter())) {
                        if ((gz.getClientCh() != 0) || (gz.getClientPar() != 0) || (gz.getClientPed() != 0)) {
                            if (gz.getUsl().equals("итого")) {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatRight));
                            } else {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatLeft));
                            }
                            sheet.addCell(new Number(3, i, gz.getClientCh(), tnr12ptFormat));
                            sheet.addCell(new Number(4, i, gz.getClientPar(), tnr12ptFormat));
                            sheet.addCell(new Number(5, i, gz.getClientPed(), tnr12ptFormat));
                            /*sheet.addCell(new Number(6, i, gz.getPriyomCh(), tnr12ptFormat));
                            sheet.addCell(new Number(7, i, gz.getPriyomPar(), tnr12ptFormat));
                            sheet.addCell(new Number(8, i, gz.getPriyomPed(), tnr12ptFormat));*/
                            sheet.addCell(new Number(6, i, gz.getPrclCh(), tnr12ptFormat));
                            sheet.addCell(new Number(7, i, gz.getPrclPar(), tnr12ptFormat));
                            sheet.addCell(new Number(8, i, gz.getPrclPed(), tnr12ptFormat));
                            i++;
                        }
                    }
                }
                if (j < i) {
                    sheet.mergeCells(1, j, 1, i - 1);
                    sheet.addCell(new Label(1, j, "в ЦППМСП", tnr12ptFormatLeftCenter));
                }
                Integer k = i;
                for (Gz gz : gzs) {
                    if ((gz.getOsnUsl().equals(osnUsl)) && (!gz.getCenter())) {
                        if ((gz.getClientCh() != 0) || (gz.getClientPar() != 0) || (gz.getClientPed() != 0)) {
                            if (gz.getUsl().equals("итого")) {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatRight));
                            } else {
                                sheet.addCell(new Label(2, i, gz.getUsl(), tnr12ptFormatLeft));
                            }
                            sheet.addCell(new Number(3, i, gz.getClientCh(), tnr12ptFormat));
                            sheet.addCell(new Number(4, i, gz.getClientPar(), tnr12ptFormat));
                            sheet.addCell(new Number(5, i, gz.getClientPed(), tnr12ptFormat));
                            /*sheet.addCell(new Number(6, i, gz.getPriyomCh(), tnr12ptFormat));
                            sheet.addCell(new Number(7, i, gz.getPriyomPar(), tnr12ptFormat));
                            sheet.addCell(new Number(8, i, gz.getPriyomPed(), tnr12ptFormat));*/
                            sheet.addCell(new Number(6, i, gz.getPrclCh(), tnr12ptFormat));
                            sheet.addCell(new Number(7, i, gz.getPrclPar(), tnr12ptFormat));
                            sheet.addCell(new Number(8, i, gz.getPrclPed(), tnr12ptFormat));
                            i++;
                        }
                    }
                }
                if (k < i) {
                    sheet.mergeCells(1, k, 1, i - 1);
                    sheet.addCell(new Label(1, k, "в ОО", tnr12ptFormatLeftCenter));
                }
                sheet.mergeCells(0, j, 0, i - 1);
                j = i - 1;
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(334 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(181 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(319 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(180 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(180 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(180 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(180 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(180 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(180 * 37);
            sheet.setColumnView(8, cv);

            /*     cv = sheet.getColumnView(9);
            cv.setSize(180 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(180 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(180 * 37);
            sheet.setColumnView(11, cv);*/
            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    public static void printRanniy(ServletOutputStream os, Date date1, Date date2, Map<List<String>, Integer> countChildren,
            Map<List<String>, Integer> countParents, Map<List<String>, Integer> countChildrenN,
            Map<List<String>, Integer> countParentsN, List<Reestr> pmpkReestr,
            List<Reestr> massajReestr, List<Reestr> uslReestr, List<Reestr> consultReestr,
            Set<String> regSet, Set<String> childrenUslSet, Set<String> parentsUslSet, Set<String> childrenStatusSet) 
            throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;
            WritableCellFormat tnr12ptVertFormat;
            WritableCellFormat tnr12ptFormatBoldLeftBorder;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // жирный влево с рамкой                
            WritableFont tnr12ptBoldLeftBorder = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptFormatBoldLeftBorder = new WritableCellFormat(tnr12ptBoldLeftBorder);
            tnr12ptFormatBoldLeftBorder.setAlignment(Alignment.LEFT);
            tnr12ptFormatBoldLeftBorder.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            // обычный по центру вертикально
            tnr12ptVertFormat = new WritableCellFormat(tnr12pt);
            tnr12ptVertFormat.setAlignment(Alignment.CENTRE);
            tnr12ptVertFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptVertFormat.setOrientation(Orientation.PLUS_90);

            // Лист 1 - статистика
            sheet = workbook.createSheet("Статистика", 0);
            // вычисляем кол-во столбцов
            int n = 2 + regSet.size();
            try {
                sheet.mergeCells(0, 0, n - 1, 0);
                sheet.addCell(new Label(0, 0, "Статистические данные об услугах, оказанных детям раннего возраста и их законным представителям", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, n - 1, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "Категория/Услуга", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "Всего", tnr12ptFormat));
                sheet.mergeCells(2, 3, n - 1, 3);
                sheet.addCell(new Label(2, 3, "Районы", tnr12ptFormat));
                int j = 2;
                for (String reg : regSet) {
                    sheet.addCell(new Label(j++, 4, reg, tnr12ptFormat));
                }

                int i = 5;
                String vsego = "Всего";
                String kol = "количество";
                String obr = "обращения";
                String keyCh = "Количество детей";
                String keyPar = "Количество законных представителей";
                String keyObrCh = "Количество обращений детей";
                String keyObrPar = "Количество обращений законных представителей";
                // общее кол-во детей                
                sheet.addCell(new Label(0, i, keyCh, tnr12ptFormatBoldLeftBorder));
                List<String> key1 = Arrays.asList(keyCh, kol, vsego);
                sheet.addCell(new Number(1, i, countChildren.get(key1), tnr12ptFormat));
                j = 2;
                for (String reg : regSet) {
                    List<String> key = Arrays.asList(keyCh, kol, reg);
                    try {
                        sheet.addCell(new Number(j, i, countChildren.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во детей по услугам
                for (String usl : childrenUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, kol, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countChildren.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String reg : regSet) {
                        List<String> key = Arrays.asList(usl, kol, reg);
                        try {
                            sheet.addCell(new Number(j, i, countChildren.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }
                // кол-во обращений детей                
                sheet.addCell(new Label(0, i, keyObrCh, tnr12ptFormatBoldLeftBorder));
                List<String> key3 = Arrays.asList(keyObrCh, obr, vsego);
                try {
                    sheet.addCell(new Number(1, i, countChildren.get(key3), tnr12ptFormat));
                } catch (Exception ex) {
                    sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                }
                j = 2;
                for (String reg : regSet) {
                    List<String> key = Arrays.asList(keyObrCh, obr, reg);
                    try {
                        sheet.addCell(new Number(j, i, countChildren.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во обращений детей по услугам
                for (String usl : childrenUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, obr, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countChildren.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String reg : regSet) {
                        List<String> key = Arrays.asList(usl, obr, reg);
                        try {
                            sheet.addCell(new Number(j, i, countChildren.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }
                // общее кол-во зак.представителей                
                sheet.addCell(new Label(0, i, keyPar, tnr12ptFormatBoldLeftBorder));
                List<String> key4 = Arrays.asList(keyPar, kol, vsego);
                try {
                    sheet.addCell(new Number(1, i, countParents.get(key4), tnr12ptFormat));
                } catch (Exception ex) {
                    sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                }
                j = 2;
                for (String reg : regSet) {
                    List<String> key = Arrays.asList(keyPar, kol, reg);
                    try {
                        sheet.addCell(new Number(j, i, countParents.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во зак.представителей по услугам
                for (String usl : parentsUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, kol, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countParents.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String reg : regSet) {
                        List<String> key = Arrays.asList(usl, kol, reg);
                        try {
                            sheet.addCell(new Number(j, i, countParents.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }
                // кол-во обращений зак.представителей                
                sheet.addCell(new Label(0, i, keyObrPar, tnr12ptFormatBoldLeftBorder));
                List<String> key6 = Arrays.asList(keyObrPar, obr, vsego);
                try {
                    sheet.addCell(new Number(1, i, countParents.get(key6), tnr12ptFormat));
                } catch (Exception ex) {
                    sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                }
                j = 2;
                for (String reg : regSet) {
                    List<String> key = Arrays.asList(keyObrPar, obr, reg);
                    try {
                        sheet.addCell(new Number(j, i, countParents.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во обращений зак.представителей по услугам
                for (String usl : parentsUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, obr, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countParents.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String reg : regSet) {
                        List<String> key = Arrays.asList(usl, obr, reg);
                        try {
                            sheet.addCell(new Number(j, i, countParents.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(360 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(120 * 37);
                sheet.setColumnView(1, cv);

                for (int k = 2; k < n; k++) {
                    cv = sheet.getColumnView(k);
                    cv.setSize(130 * 37);
                    sheet.setColumnView(k, cv);
                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            // Лист 2 - статистика
            sheet = workbook.createSheet("Статистика по статусам", 1);
            
            // вычисляем кол-во столбцов
            n = 2 + childrenStatusSet.size();
            try {
                sheet.mergeCells(0, 0, n - 1, 0);
                sheet.addCell(new Label(0, 0, "Статистические данные об услугах, оказанных детям раннего возраста и их законным представителям", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, n - 1, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "Категория/Услуга", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "Всего", tnr12ptFormat));
                sheet.mergeCells(2, 3, n - 1, 3);
                sheet.addCell(new Label(2, 3, "Статусы", tnr12ptFormat));
                int j = 2;
                for (String st : childrenStatusSet) {
                    sheet.addCell(new Label(j++, 4, st, tnr12ptFormat));
                }

                int i = 5;
                String vsego = "Всего";
                String kol = "количество";
                String obr = "обращения";
                String keyCh = "Количество детей";
                String keyPar = "Количество законных представителей";
                String keyObrCh = "Количество обращений детей";
                String keyObrPar = "Количество обращений законных представителей";
                // общее кол-во детей                
                sheet.addCell(new Label(0, i, keyCh, tnr12ptFormatBoldLeftBorder));
                List<String> key1 = Arrays.asList(keyCh, kol, vsego);
                sheet.addCell(new Number(1, i, countChildrenN.get(key1), tnr12ptFormat));
                j = 2;
                for (String st : childrenStatusSet) {
                    List<String> key = Arrays.asList(keyCh, kol, st);
                    try {
                        sheet.addCell(new Number(j, i, countChildrenN.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во детей по услугам
                for (String usl : childrenUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, kol, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countChildrenN.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String st : childrenStatusSet) {
                        List<String> key = Arrays.asList(usl, kol, st);
                        try {
                            sheet.addCell(new Number(j, i, countChildrenN.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }
                // кол-во обращений детей                
                sheet.addCell(new Label(0, i, keyObrCh, tnr12ptFormatBoldLeftBorder));
                List<String> key3 = Arrays.asList(keyObrCh, obr, vsego);
                try {
                    sheet.addCell(new Number(1, i, countChildrenN.get(key3), tnr12ptFormat));
                } catch (Exception ex) {
                    sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                }
                j = 2;
                for (String st : childrenStatusSet) {
                    List<String> key = Arrays.asList(keyObrCh, obr, st);
                    try {
                        sheet.addCell(new Number(j, i, countChildrenN.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во обращений детей по услугам
                for (String usl : childrenUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, obr, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countChildrenN.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String st : childrenStatusSet) {
                        List<String> key = Arrays.asList(usl, obr, st);
                        try {
                            sheet.addCell(new Number(j, i, countChildrenN.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }
                // общее кол-во зак.представителей                
                sheet.addCell(new Label(0, i, keyPar, tnr12ptFormatBoldLeftBorder));
                List<String> key4 = Arrays.asList(keyPar, kol, vsego);
                try {
                    sheet.addCell(new Number(1, i, countParentsN.get(key4), tnr12ptFormat));
                } catch (Exception ex) {
                    sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                }
                j = 2;
                for (String st : childrenStatusSet) {
                    List<String> key = Arrays.asList(keyPar, kol, st);
                    try {
                        sheet.addCell(new Number(j, i, countParentsN.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во зак.представителей по услугам
                for (String usl : parentsUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, kol, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countParentsN.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String st : childrenStatusSet) {
                        List<String> key = Arrays.asList(usl, kol, st);
                        try {
                            sheet.addCell(new Number(j, i, countParentsN.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }
                // кол-во обращений зак.представителей                
                sheet.addCell(new Label(0, i, keyObrPar, tnr12ptFormatBoldLeftBorder));
                List<String> key6 = Arrays.asList(keyObrPar, obr, vsego);
                try {
                    sheet.addCell(new Number(1, i, countParentsN.get(key6), tnr12ptFormat));
                } catch (Exception ex) {
                    sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                }
                j = 2;
                for (String st : childrenStatusSet) {
                    List<String> key = Arrays.asList(keyObrPar, obr, st);
                    try {
                        sheet.addCell(new Number(j, i, countParentsN.get(key), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                    }
                    j++;
                }
                i++;
                // кол-во обращений зак.представителей по услугам
                for (String usl : parentsUslSet) {
                    sheet.addCell(new Label(0, i, usl, tnr12ptFormatLeft));
                    List<String> key2 = Arrays.asList(usl, obr, vsego);
                    try {
                        sheet.addCell(new Number(1, i, countParentsN.get(key2), tnr12ptFormat));
                    } catch (Exception ex) {
                        sheet.addCell(new Number(1, i, 0, tnr12ptFormat));
                    }
                    j = 2;
                    for (String st : childrenStatusSet) {
                        List<String> key = Arrays.asList(usl, obr, st);
                        try {
                            sheet.addCell(new Number(j, i, countParentsN.get(key), tnr12ptFormat));
                        } catch (Exception ex) {
                            sheet.addCell(new Number(j, i, 0, tnr12ptFormat));
                        }
                        j++;
                    }
                    i++;
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(360 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(120 * 37);
                sheet.setColumnView(1, cv);

                for (int k = 2; k < n; k++) {
                    cv = sheet.getColumnView(k);
                    cv.setSize(130 * 37);
                    sheet.setColumnView(k, cv);
                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            
            
            // Лист 3 - дети РВ прошедшие ПМПК
            sheet = workbook.createSheet("Дети ПМПК", 2);
            try {
                sheet.mergeCells(0, 0, 6, 0);
                sheet.addCell(new Label(0, 0, "Реестр детей раннего возраста, прошедших ПМПК", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 6, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Фамилия", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Имя", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Отчество", tnr12ptFormat));
                sheet.addCell(new Label(4, 3, "Дата рождения", tnr12ptFormat));
                sheet.addCell(new Label(5, 3, "Район", tnr12ptFormat));
                sheet.addCell(new Label(6, 3, "Дата ПМПК", tnr12ptFormat));
                sheet.addCell(new Label(7, 3, "Место проведения", tnr12ptFormat));

                int i = 4;
                for (Reestr r : pmpkReestr) {
                    sheet.addCell(new Number(0, i, i-3, tnr12ptFormat));
                    sheet.addCell(new Label(1, i, r.getFam(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, r.getName(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getPatr(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(4, i, r.getFormat2Dr(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(5, i, r.getReg(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(6, i, r.getFormat2Date(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(7, i, r.getRegPr(), tnr12ptFormatLeft));
                    i++;
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(48 * 48);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(240 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(240 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(240 * 37);
                sheet.setColumnView(3, cv);

                cv = sheet.getColumnView(4);
                cv.setSize(120 * 37);
                sheet.setColumnView(4, cv);

                cv = sheet.getColumnView(5);
                cv.setSize(180 * 37);
                sheet.setColumnView(5, cv);

                cv = sheet.getColumnView(6);
                cv.setSize(120 * 37);
                sheet.setColumnView(6, cv);

                cv = sheet.getColumnView(7);
                cv.setSize(180 * 37);
                sheet.setColumnView(7, cv);

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            // Лист 4 - дети РВ посещавшие массаж
            sheet = workbook.createSheet("Дети массаж", 3);
            try {
                sheet.mergeCells(0, 0, 6, 0);
                sheet.addCell(new Label(0, 0, "Реестр детей раннего возраста, посещавшие массаж", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 6, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Фамилия", tnr12ptFormat));
                sheet.addCell(new Label(2, 3, "Имя", tnr12ptFormat));
                sheet.addCell(new Label(3, 3, "Отчество", tnr12ptFormat));
                sheet.addCell(new Label(4, 3, "Дата рождения", tnr12ptFormat));
                sheet.addCell(new Label(5, 3, "Район", tnr12ptFormat));
                sheet.addCell(new Label(6, 3, "Дата сеанса", tnr12ptFormat));

                int i = 4;
                for (Reestr r : massajReestr) {
                    sheet.addCell(new Number(0, i, i-3, tnr12ptFormat));
                    sheet.addCell(new Label(1, i, r.getFam(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(2, i, r.getName(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(3, i, r.getPatr(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(4, i, r.getFormat2Dr(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(5, i, r.getReg(), tnr12ptFormatLeft));
                    sheet.addCell(new Label(6, i, r.getFormat2Date(), tnr12ptFormatLeft));
                    i++;
                }

                CellView cv = sheet.getColumnView(0);
                cv.setSize(48 * 37);
                sheet.setColumnView(0, cv);

                cv = sheet.getColumnView(1);
                cv.setSize(240 * 37);
                sheet.setColumnView(1, cv);

                cv = sheet.getColumnView(2);
                cv.setSize(240 * 37);
                sheet.setColumnView(2, cv);

                cv = sheet.getColumnView(3);
                cv.setSize(240 * 37);
                sheet.setColumnView(3, cv);

                cv = sheet.getColumnView(4);
                cv.setSize(120 * 37);
                sheet.setColumnView(4, cv);

                cv = sheet.getColumnView(5);
                cv.setSize(180 * 37);
                sheet.setColumnView(5, cv);

                cv = sheet.getColumnView(6);
                cv.setSize(120 * 37);
                sheet.setColumnView(6, cv);
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            int k = 4;
            // Дети РВ, получившие другие услуги распределяем по листам для каждой услуги
            Set<String> uslSet = new HashSet();
            for (Reestr r : uslReestr) {
                uslSet.add(r.getUsl());
            }
            for (String usl : uslSet) {
                sheet = workbook.createSheet("Дети " + usl, k);
                try {
                    sheet.mergeCells(0, 0, 8, 0);
                    sheet.addCell(new Label(0, 0, "Реестр детей раннего возраста, получивших услугу " + usl, tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 8, 1);
                    sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                    sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                    sheet.addCell(new Label(1, 3, "Фамилия", tnr12ptFormat));
                    sheet.addCell(new Label(2, 3, "Имя", tnr12ptFormat));
                    sheet.addCell(new Label(3, 3, "Отчество", tnr12ptFormat));
                    sheet.addCell(new Label(4, 3, "Дата рождения", tnr12ptFormat));
                    sheet.addCell(new Label(5, 3, "Район", tnr12ptFormat));
                    sheet.addCell(new Label(6, 3, "Дата услуги", tnr12ptFormat));
                    sheet.addCell(new Label(7, 3, "Место проведения услуги", tnr12ptFormat));
                    sheet.addCell(new Label(8, 3, "Специалисты", tnr12ptFormat));

                    int i = 4;
                    for (Reestr r : uslReestr) {
                        if (usl.equals(r.getUsl())) {
                            sheet.addCell(new Number(0, i, i-3, tnr12ptFormat));
                            sheet.addCell(new Label(1, i, r.getFam(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(2, i, r.getName(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(3, i, r.getPatr(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(4, i, r.getFormat2Dr(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(5, i, r.getReg(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(6, i, r.getFormat2Date(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(7, i, r.getRegPr(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(8, i, r.getInfo(), tnr12ptFormatLeft));
                            i++;                            
                        }
                    }

                    k++;

                    CellView cv = sheet.getColumnView(0);
                    cv.setSize(48 * 37);
                    sheet.setColumnView(0, cv);

                    cv = sheet.getColumnView(1);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(1, cv);

                    cv = sheet.getColumnView(2);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(2, cv);

                    cv = sheet.getColumnView(3);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(3, cv);

                    cv = sheet.getColumnView(4);
                    cv.setSize(120 * 37);
                    sheet.setColumnView(4, cv);

                    cv = sheet.getColumnView(5);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(5, cv);

                    cv = sheet.getColumnView(6);
                    cv.setSize(120 * 37);
                    sheet.setColumnView(6, cv);

                    cv = sheet.getColumnView(7);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(7, cv);

                    cv = sheet.getColumnView(8);
                    cv.setSize(300 * 37);
                    sheet.setColumnView(8, cv);
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

            // Зак.представители детей РВ, получившие услуги распределяем по листам для каждой услуги
            Set<String> uslParSet = new HashSet();
            for (Reestr r : consultReestr) {
                uslParSet.add(r.getUsl());
            }
            for (String usl : uslParSet) {
                sheet = workbook.createSheet("Зак.пред. " + usl, k);
                try {
                    sheet.mergeCells(0, 0, 11, 0);
                    sheet.addCell(new Label(0, 0, "Реестр законных представителей детей раннего возраста, получивших услугу " + usl, tnr12ptBoldFormat));
                    sheet.mergeCells(0, 1, 11, 1);
                    sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                    sheet.mergeCells(0, 3, 0, 4);
                    sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                    sheet.mergeCells(1, 3, 3, 3);
                    sheet.addCell(new Label(1, 3, "Законный представитель", tnr12ptFormat));
                    sheet.addCell(new Label(1, 4, "Фамилия", tnr12ptFormat));
                    sheet.addCell(new Label(2, 4, "Имя", tnr12ptFormat));
                    sheet.addCell(new Label(3, 4, "Отчество", tnr12ptFormat));
                    sheet.mergeCells(4, 3, 7, 3);
                    sheet.addCell(new Label(4, 3, "Ребёнок", tnr12ptFormat));
                    sheet.addCell(new Label(4, 4, "Фамилия", tnr12ptFormat));
                    sheet.addCell(new Label(5, 4, "Имя", tnr12ptFormat));
                    sheet.addCell(new Label(6, 4, "Отчество", tnr12ptFormat));
                    sheet.addCell(new Label(7, 4, "Дата рождения", tnr12ptFormat));
                    sheet.mergeCells(8, 3, 8, 4);
                    sheet.addCell(new Label(8, 3, "Район", tnr12ptFormat));
                    sheet.mergeCells(9, 3, 9, 4);
                    sheet.addCell(new Label(9, 3, "Дата услуги", tnr12ptFormat));
                    sheet.mergeCells(10, 3, 10, 4);
                    sheet.addCell(new Label(10, 3, "Место проведения услуги", tnr12ptFormat));
                    sheet.mergeCells(11, 3, 11, 4);
                    sheet.addCell(new Label(11, 3, "Специалисты", tnr12ptFormat));

                    int i = 5;
                    for (Reestr r : consultReestr) {
                        if (usl.equals(r.getUsl())) {
                            sheet.addCell(new Number(0, i, i - 4, tnr12ptFormat));
                            sheet.addCell(new Label(1, i, r.getFamPar(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(2, i, r.getNamePar(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(3, i, r.getPatrPar(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(4, i, r.getFam(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(5, i, r.getName(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(6, i, r.getPatr(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(7, i, r.getFormat2Dr(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(8, i, r.getReg(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(9, i, r.getFormat2Date(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(10, i, r.getRegPr(), tnr12ptFormatLeft));
                            sheet.addCell(new Label(11, i, r.getInfo(), tnr12ptFormatLeft));
                            i++;
                        }
                    }

                    k++;

                    CellView cv = sheet.getColumnView(0);
                    cv.setSize(48 * 37);
                    sheet.setColumnView(0, cv);

                    cv = sheet.getColumnView(1);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(1, cv);

                    cv = sheet.getColumnView(2);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(2, cv);

                    cv = sheet.getColumnView(3);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(3, cv);

                    cv = sheet.getColumnView(4);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(4, cv);

                    cv = sheet.getColumnView(5);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(5, cv);

                    cv = sheet.getColumnView(6);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(6, cv);

                    cv = sheet.getColumnView(7);
                    cv.setSize(120 * 37);
                    sheet.setColumnView(7, cv);

                    cv = sheet.getColumnView(8);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(8, cv);

                    cv = sheet.getColumnView(9);
                    cv.setSize(120 * 37);
                    sheet.setColumnView(9, cv);

                    cv = sheet.getColumnView(10);
                    cv.setSize(180 * 37);
                    sheet.setColumnView(10, cv);

                    cv = sheet.getColumnView(11);
                    cv.setSize(300 * 37);
                    sheet.setColumnView(11, cv);
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    public static void printOtchetConsultPar(ServletOutputStream os, Date date1, Date date2, int clients,
            int clients3, int clients37, int clientsInvOVZ, int clientsOp, int clientsZS) throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeft.setWrap(true);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 1, 0);
                sheet.addCell(new Label(0, 0, "Отчет по консультированию законных представителей (категории)", tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 1, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.addCell(new Label(0, 3, "Категория", tnr12ptFormat));
                sheet.addCell(new Label(1, 3, "Кол-во услуг", tnr12ptFormat));
                sheet.addCell(new Label(0, 4, "Родители (законные представители) всего:", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 4, clients, tnr12ptFormatRight));
                sheet.addCell(new Label(0, 5, "а) родители (законные представители) с детьми в возрасте от 0 до 3 лет", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 5, clients3, tnr12ptFormatRight));
                sheet.addCell(new Label(0, 6, "б) родители (законные представители) с детьми в возрасте от 3 до 7 лет", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 6, clients37, tnr12ptFormatRight));
                sheet.addCell(new Label(0, 7, "в) родители (законные представители), обеспечивающие получение детьми дошкольного образования в форме семейного обучения", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 7, 0, tnr12ptFormatRight));
                sheet.addCell(new Label(0, 8, "г) родители (законные представители), воспитывающие детей с ОВЗ и инвалидностью", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 8, clientsInvOVZ, tnr12ptFormatRight));
                sheet.addCell(new Label(0, 9, "д) родители (законные представители) с детьми, имеющими проблемы в социальной адаптации (с девиантным поведением)", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 9, clientsOp, tnr12ptFormatRight));
                sheet.addCell(new Label(0, 10, "е) родители (законные представители), имеющие приемных детей", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 10, clientsZS, tnr12ptFormatRight));
                sheet.addCell(new Label(0, 11, "ж) в том числе иные категории родителей (законных представителей) ", tnr12ptFormatLeft));
                sheet.addCell(new Number(1, 11, 0, tnr12ptFormatRight));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            CellView cv = sheet.getColumnView(0);
            cv.setSize(450 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(100 * 37);
            sheet.setColumnView(1, cv);

        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }
    
    public static void printReestrPMPKAgeOnDate(ServletOutputStream os, Date date1, Date date2, List<ReestrPMPKFull> reestr, String pmpkShname) throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldFormat.setWrap(true);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 16, 0);
                sheet.addCell(new Label(0, 0, "Реестр детей с ограниченными возможностями здоровья и (или) девиантным (общественно опасным) поведением, прошедших обследование на " + pmpkShname, tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 16, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "ФИО ребенка", tnr12ptFormat));
                sheet.mergeCells(2, 3, 2, 4);
                sheet.addCell(new Label(2, 3, "Дата рождения", tnr12ptFormat));
                sheet.mergeCells(3, 3, 3, 4);
                sheet.addCell(new Label(3, 3, "Возраст", tnr12ptFormat));
                sheet.mergeCells(4, 3, 4, 4);
                sheet.addCell(new Label(4, 3, "Пол", tnr12ptFormat));
                sheet.mergeCells(5, 3, 5, 4);
                sheet.addCell(new Label(5, 3, "Мун.район/ городской округ", tnr12ptFormat));
                sheet.mergeCells(6, 3, 6, 4);
                sheet.addCell(new Label(6, 3, "Дата ПМПК", tnr12ptFormat));
                sheet.mergeCells(7, 3, 7, 4);
                sheet.addCell(new Label(7, 3, "Заключение ПМПК", tnr12ptFormat));
                sheet.mergeCells(8, 3, 8, 4);
                sheet.addCell(new Label(8, 3, "Срок действия заключения", tnr12ptFormat));
                sheet.mergeCells(9, 3, 16, 3);
                sheet.addCell(new Label(9, 3, "Рекомендации", tnr12ptFormat));
                sheet.addCell(new Label(9, 4, "ассистент (помощник)", tnr12ptFormat));
                sheet.addCell(new Label(10, 4, "тьютор", tnr12ptFormat));
                sheet.addCell(new Label(11, 4, "педагог-психолог", tnr12ptFormat));
                sheet.addCell(new Label(12, 4, "учитель-логопед", tnr12ptFormat));
                sheet.addCell(new Label(13, 4, "учитель-дефектолог (олигофренопедагог)", tnr12ptFormat));
                sheet.addCell(new Label(14, 4, "учитель-дефектолог (тифлопедагога)", tnr12ptFormat));
                sheet.addCell(new Label(15, 4, "учитель-дефектолог (сурдопедагога)", tnr12ptFormat));
                sheet.addCell(new Label(16, 4, "социальный педагог", tnr12ptFormat));
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 4;
            for (ReestrPMPKFull child : reestr) {
                i++;
                j++;
                sheet.addCell(new Number(0, j, i, tnr12ptFormatRight));
                sheet.addCell(new Label(1, j, child.getFio(), tnr12ptFormatLeft));
                sheet.addCell(new Label(2, j, dateFormat.format(child.getDr()), tnr12ptFormatLeft));
                sheet.addCell(new Label(3, j, child.getAge().toString(), tnr12ptFormatLeft));
                sheet.addCell(new Label(4, j, child.getSex(), tnr12ptFormatLeft));
                sheet.addCell(new Label(5, j, child.getReg(), tnr12ptFormatLeft));                
                sheet.addCell(new Label(6, j, dateFormat.format(child.getDatep()), tnr12ptFormatLeft));
                sheet.addCell(new Label(7, j, child.getObr(), tnr12ptFormatLeft));
                String datek = "";
                if (child.getDatek() != null) {
                    datek = dateFormat.format(child.getDatek());
                }
                sheet.addCell(new Label(8, j, datek, tnr12ptFormatLeft));
                if (child.getAssist()) {
                    sheet.addCell(new Label(9, j, "+", tnr12ptFormat));
                } else if (!child.getAssist()) {
                    sheet.addCell(new Label(9, j, "-", tnr12ptFormat));
                }
                if (child.getTutor()) {
                    sheet.addCell(new Label(10, j, "+", tnr12ptFormat));
                } else if (!child.getTutor()) {
                    sheet.addCell(new Label(10, j, "-", tnr12ptFormat));
                }
                if (child.getPsy()) {
                    sheet.addCell(new Label(11, j, "+", tnr12ptFormat));
                } else if (!child.getPsy()) {
                    sheet.addCell(new Label(11, j, "-", tnr12ptFormat));
                }
                if (child.getLogo()) {
                    sheet.addCell(new Label(12, j, "+", tnr12ptFormat));
                } else if (!child.getLogo()) {
                    sheet.addCell(new Label(12, j, "-", tnr12ptFormat));
                }
                if (child.getDefolig()) {
                    sheet.addCell(new Label(13, j, "+", tnr12ptFormat));
                } else if (!child.getDefolig()) {
                    sheet.addCell(new Label(13, j, "-", tnr12ptFormat));
                }
                if (child.getDeftiflo()) {
                    sheet.addCell(new Label(14, j, "+", tnr12ptFormat));
                } else if (!child.getDeftiflo()) {
                    sheet.addCell(new Label(14, j, "-", tnr12ptFormat));
                }
                if (child.getDefsurdo()) {
                    sheet.addCell(new Label(15, j, "+", tnr12ptFormat));
                } else if (!child.getDefsurdo()) {
                    sheet.addCell(new Label(15, j, "-", tnr12ptFormat));
                }
                if (child.getSoc()) {
                    sheet.addCell(new Label(16, j, "+", tnr12ptFormat));
                } else if (!child.getSoc()) {
                    sheet.addCell(new Label(16, j, "-", tnr12ptFormat));
                }
            }

            CellView cvv = sheet.getRowView(0);
            cvv.setSize(21 * 37);
            sheet.setRowView(0, cvv);

            CellView cv = sheet.getColumnView(0);
            cv.setSize(40 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(300 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(90 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(90 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(90 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(120 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(110 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(300 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(110 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(80 * 37);
            sheet.setColumnView(9, cv);

            cv = sheet.getColumnView(10);
            cv.setSize(80 * 37);
            sheet.setColumnView(10, cv);

            cv = sheet.getColumnView(11);
            cv.setSize(80 * 37);
            sheet.setColumnView(11, cv);

            cv = sheet.getColumnView(12);
            cv.setSize(80 * 37);
            sheet.setColumnView(12, cv);

            cv = sheet.getColumnView(13);
            cv.setSize(80 * 37);
            sheet.setColumnView(13, cv);

            cv = sheet.getColumnView(14);
            cv.setSize(80 * 37);
            sheet.setColumnView(14, cv);

            cv = sheet.getColumnView(15);
            cv.setSize(80 * 37);
            sheet.setColumnView(15, cv);

            cv = sheet.getColumnView(16);
            cv.setSize(80 * 37);
            sheet.setColumnView(16, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }
    
    public static void printReestrPMPKForConsult(ServletOutputStream os, Date date1, Date date2, List<Reestr> reestr, String pmpkShname) throws WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd.MM.yyyy");

            workbook = Workbook.createWorkbook(os, ws);
            sheet = workbook.createSheet("Лист1", 0);

            WritableCellFormat tnr12ptBoldFormat;
            WritableCellFormat tnr12ptFormat;
            WritableCellFormat tnr12ptBoldFormatLeft;
            WritableCellFormat tnr12ptFormatLeft;
            WritableCellFormat tnr12ptFormatNoBorder;
            WritableCellFormat tnr12ptFormatLeftCenter;
            WritableCellFormat tnr12ptFormatRight;

            // жирный по центру
            WritableFont tnr12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormat = new WritableCellFormat(tnr12ptBold);
            tnr12ptBoldFormat.setAlignment(Alignment.CENTRE);
            tnr12ptBoldFormat.setWrap(true);

            // обычный по центру
            WritableFont tnr12pt = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormat = new WritableCellFormat(tnr12pt);
            tnr12ptFormat.setAlignment(Alignment.CENTRE);
            tnr12ptFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormat.setWrap(true);

            // обычный по центру без границ                
            tnr12ptFormatNoBorder = new WritableCellFormat(tnr12pt);
            tnr12ptFormatNoBorder.setAlignment(Alignment.LEFT);

            // жирный влево
            WritableFont tnr12ptBoldLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
            tnr12ptBoldFormatLeft = new WritableCellFormat(tnr12ptBoldLeft);
            tnr12ptBoldFormatLeft.setAlignment(Alignment.LEFT);

            // обычный влево                
            WritableFont tnr12ptLeft = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeft = new WritableCellFormat(tnr12ptLeft);
            tnr12ptFormatLeft.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный вправо
            WritableFont tnr12ptRight = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatRight = new WritableCellFormat(tnr12ptRight);
            tnr12ptFormatRight.setAlignment(Alignment.RIGHT);
            tnr12ptFormatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

            // обычный влево по центру по вертикали                
            WritableFont tnr12ptLeftCenter = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            tnr12ptFormatLeftCenter = new WritableCellFormat(tnr12ptLeftCenter);
            tnr12ptFormatLeftCenter.setAlignment(Alignment.LEFT);
            tnr12ptFormatLeftCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
            tnr12ptFormatLeftCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
            tnr12ptFormatLeftCenter.setWrap(true);

            try {
                sheet.mergeCells(0, 0, 9, 0);
                sheet.addCell(new Label(0, 0, "Реестр родителей детей, прошедших обследование на " + pmpkShname, tnr12ptBoldFormat));
                sheet.mergeCells(0, 1, 9, 1);
                sheet.addCell(new Label(0, 1, "за период с " + dateFormat.format(date1) + " по " + dateFormat.format(date2), tnr12ptBoldFormat));
                sheet.mergeCells(0, 3, 0, 4);
                sheet.addCell(new Label(0, 3, "№ п/п", tnr12ptFormat));
                sheet.mergeCells(1, 3, 1, 4);
                sheet.addCell(new Label(1, 3, "Дата ПМПК", tnr12ptFormat));
                sheet.mergeCells(2, 3, 2, 4);
                sheet.addCell(new Label(2, 3, "Состав ПМПК", tnr12ptFormat));
                sheet.mergeCells(3, 3, 4, 3);
                sheet.addCell(new Label(3, 3, "Родитель", tnr12ptFormat));
                sheet.addCell(new Label(3, 4, "Фамилия", tnr12ptFormat));
                sheet.addCell(new Label(4, 4, "Имя", tnr12ptFormat));
                sheet.addCell(new Label(5, 4, "Отчество", tnr12ptFormat));
                sheet.mergeCells(6, 3, 9, 3);
                sheet.addCell(new Label(6, 3, "Ребёнок", tnr12ptFormat));
                sheet.addCell(new Label(6, 4, "Фамилия", tnr12ptFormat));
                sheet.addCell(new Label(7, 4, "Имя", tnr12ptFormat));
                sheet.addCell(new Label(8, 4, "Отчество", tnr12ptFormat));
                sheet.addCell(new Label(9, 4, "Дата рождения", tnr12ptFormat));                
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            Integer i = 0;
            Integer j = 4;
            for (Reestr r : reestr) {
                i++;
                j++;
                sheet.addCell(new Number(0, j, i, tnr12ptFormatRight));
                sheet.addCell(new Label(1, j, dateFormat.format(r.getDate()), tnr12ptFormatLeft));
                sheet.addCell(new Label(2, j, r.getInfo(), tnr12ptFormatLeft));
                sheet.addCell(new Label(3, j, r.getFamPar(), tnr12ptFormatLeft));
                sheet.addCell(new Label(4, j, r.getNamePar(), tnr12ptFormatLeft));
                sheet.addCell(new Label(5, j, r.getPatrPar(), tnr12ptFormatLeft));                
                sheet.addCell(new Label(6, j, r.getFam(), tnr12ptFormatLeft));
                sheet.addCell(new Label(7, j, r.getName(), tnr12ptFormatLeft));
                sheet.addCell(new Label(8, j, r.getPatr(), tnr12ptFormatLeft));
                sheet.addCell(new Label(9, j, dateFormat.format(r.getDr()), tnr12ptFormatLeft));                
            }

            CellView cvv = sheet.getRowView(0);
            cvv.setSize(21 * 37);
            sheet.setRowView(0, cvv);

            CellView cv = sheet.getColumnView(0);
            cv.setSize(40 * 37);
            sheet.setColumnView(0, cv);

            cv = sheet.getColumnView(1);
            cv.setSize(100 * 37);
            sheet.setColumnView(1, cv);

            cv = sheet.getColumnView(2);
            cv.setSize(300 * 37);
            sheet.setColumnView(2, cv);

            cv = sheet.getColumnView(3);
            cv.setSize(300 * 37);
            sheet.setColumnView(3, cv);

            cv = sheet.getColumnView(4);
            cv.setSize(300 * 37);
            sheet.setColumnView(4, cv);

            cv = sheet.getColumnView(5);
            cv.setSize(300 * 37);
            sheet.setColumnView(5, cv);

            cv = sheet.getColumnView(6);
            cv.setSize(300 * 37);
            sheet.setColumnView(6, cv);

            cv = sheet.getColumnView(7);
            cv.setSize(300 * 37);
            sheet.setColumnView(7, cv);

            cv = sheet.getColumnView(8);
            cv.setSize(300 * 37);
            sheet.setColumnView(8, cv);

            cv = sheet.getColumnView(9);
            cv.setSize(120 * 37);
            sheet.setColumnView(9, cv);

            //    sheetAutoFitColumns(sheet);
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Автоматически созданный блок catch
            e.printStackTrace();
        }
    }

    /*    private static void sheetAutoFitColumns(WritableSheet sheet) {            
        for (int i = 0; i < sheet.getColumns(); i++) {
            Cell[] cells = sheet.getColumn(i);
            Range[] mergedCells = sheet.getMergedCells();
            int longestStrLen = -1;

            if (cells.length == 0)
                continue;

            /* Find the widest cell in the column. */
 /*   for (int j = 0; j < cells.length; j++) {
                for (int k = 0; k < mergedCells.length; k++){
                    if (mergedCells[k] != cells[j]){
                        if ( cells[j].getContents().length() > longestStrLen ) {                                    
                            String str = cells[j].getContents();
                            if (str == null || str.isEmpty())
                                continue;
                            longestStrLen = str.trim().length();
                        }
                    }
                }
            }

            /* If not found, skip the column. */
 /*  if (longestStrLen == -1) 
                continue;

            /* If wider than the max width, crop width */
 /*  if (longestStrLen > 255)
                longestStrLen = 255;

            CellView cv = sheet.getColumnView(i);
            cv.setSize(longestStrLen * 256 + 100); /* Every character is 256 units wide, so scale it. */
 /*   sheet.setColumnView(i, cv);
            }
        }*/
}
