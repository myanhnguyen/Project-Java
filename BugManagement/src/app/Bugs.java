package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bugs {
    private final StringProperty id;
    private final StringProperty project;
    private final StringProperty mo_ta;
    private final StringProperty status;
    private final StringProperty do_nghiem_trong;
    private final StringProperty do_uu_tien;
    private final StringProperty phan_loai;
    private final StringProperty dev;
    private final StringProperty start_date;
    private final StringProperty due_date;
    private final StringProperty person;
    
    //Default constructor
    public Bugs() { 
        this(null,null,null,null,null,null,null,null,null,null,null);
    }

    //Constructor with data
    public Bugs (String id, String project, String mo_ta, String status, String do_nghiem_trong, String do_uu_tien, String phan_loai, 
            String dev, String start_date, String due_date, String person) {
        this.id = new SimpleStringProperty(id);
        this.project = new SimpleStringProperty(project);     
        this.mo_ta = new SimpleStringProperty(mo_ta);
        this.status = new SimpleStringProperty(status);
        this.do_nghiem_trong = new SimpleStringProperty(do_nghiem_trong);
        this.do_uu_tien = new SimpleStringProperty(do_uu_tien);
        this.phan_loai = new SimpleStringProperty(phan_loai);
        this.dev = new SimpleStringProperty(dev);
        this.start_date = new SimpleStringProperty(start_date);
        this.due_date = new SimpleStringProperty(due_date);
        this.person = new SimpleStringProperty(person);
    }
    
    public String getId() { return id.get(); }    
    public void setId(String id) { this.id.set(id); }
    public StringProperty idProperty() { return id; }

    public String getProject() { return project.get(); }
    public void setProject(String project) { this.project.set(project); }
    public StringProperty projectProperty() { return project; }

    public String getMo_ta() { return mo_ta.get(); }
    public void setMo_ta(String mo_ta) { this.mo_ta.set(mo_ta); }
    public StringProperty mo_taProperty() { return mo_ta; }
    
    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public StringProperty statusProperty() { return status; }

    public String getDo_nghiem_trong() { return do_nghiem_trong.get(); }
    public void setDo_nghiem_trong(String do_nghiem_trong) { this.do_nghiem_trong.set(do_nghiem_trong); }
    public StringProperty do_nghiem_trongProperty() { return do_nghiem_trong; }

    public String getDo_uu_tien() { return do_uu_tien.get(); }
    public void setDo_uu_tien(String do_uu_tien) { this.do_uu_tien.set(do_uu_tien); }
    public StringProperty do_uu_tienProperty() { return do_uu_tien; }

    public String getPhan_loai() { return phan_loai.get(); }
    public void setPhan_loai(String phan_loai) { this.phan_loai.set(phan_loai); }
    public StringProperty phan_loaiProperty() { return phan_loai; }

    public String getDev() { return dev.get(); }
    public void setDev(String dev) { this.dev.set(dev); }
    public StringProperty devProperty() { return dev; }
    
    public String getStart_date() { return start_date.get(); }
    public void setStart_date(String start_date) { this.start_date.set(start_date); }
    public StringProperty start_dateProperty() { return start_date; }
    
    public String getDue_date() { return due_date.get(); }
    public void setDue_date(String due_date) { this.due_date.set(due_date); }
    public StringProperty due_dateProperty() { return due_date; }
    
    public String getPerson() { return person.get(); }
    public void setPerson(String person) { this.person.set(person); }
    public StringProperty personProperty() { return person; }
}