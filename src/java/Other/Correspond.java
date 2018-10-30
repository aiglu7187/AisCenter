/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aiglu класс для структурирования отображения кореспонденции
 */
public class Correspond {

    private Integer id;         // ИД письма в таблице (входящий или исходящий)
    private Date date;          // дата отправки/получения
    private String nomer;       // номер (наш)
    private String sender;      // отправитель
    private String recipient;   // получатель
    private String theme;       // тема письма
    private Date ishDate;       // дата исходящего
    private String ishNomer;    // номер исходящего

    public Correspond() {

    }

    // конструктор входящего письма
    public Correspond(Integer id, Date date, String nomer, String sender, String theme, Date ishDate, String ishNomer) {
        this.id = id;
        this.date = date;
        this.nomer = nomer;
        this.sender = sender;
        this.theme = theme;
        this.ishDate = ishDate;
        this.ishNomer = ishNomer;
    }

    // конструктор исдящего письма
    public Correspond(Integer id, Date date, String nomer, String recipient, String theme) {
        this.id = id;
        this.date = date;
        this.nomer = nomer;
        this.recipient = recipient;
        this.theme = theme;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getNomer() {
        return nomer;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getTheme() {
        return theme;
    }

    public Date getIshDate() {
        return ishDate;
    }

    public String getIshNomer() {
        return ishNomer;
    }
    
    public String getFormatDate() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        String strDate = "";
        try {
            strDate = format.format(this.date);
        } catch (Exception ex) {
        }
        return strDate;
    }
    
    public String getFormat2Date() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strDate = "";
        try {
            strDate = format.format(this.date);
        } catch (Exception ex) {
        }
        return strDate;
    }
    
    public String getFormatIshDate() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        String strDate = "";
        try {
            strDate = format.format(this.ishDate);
        } catch (Exception ex) {
        }
        return strDate;
    }
    
    public String getFormat2IshDate() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strDate = "";
        try {
            strDate = format.format(this.ishDate);
        } catch (Exception ex) {
        }
        return strDate;
    }
}
