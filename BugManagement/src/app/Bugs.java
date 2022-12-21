package app;

public class Bugs {
    private String id;  
    private String project;
    private String mo_ta;   
    private String status;   
    private String do_nghiem_trong;   
    private String do_uu_tien;   
    private String phan_loai;
    private String dev;
    private String start_date;
    private String due_date;    
    private String reporter;
    //Default constructor
    public Bugs() { 
        this(null,null,null,null,null,null,null,null,null,null, null);
    }
    
    //Constructor with data
    public Bugs(String id, String project, String mo_ta, String status, String do_nghiem_trong, String do_uu_tien, 
            String phan_loai, String dev, String start_date, String due_date, String reporter) {
        this.id = id;
        this.project = project;
        this.mo_ta = mo_ta;
        this.status = status;
        this.do_nghiem_trong = do_nghiem_trong;
        this.do_uu_tien = do_uu_tien;
        this.phan_loai = phan_loai;
        this.dev = dev;
        this.start_date = start_date;
        this.due_date = due_date;
        this.reporter = reporter;
    }
    
    public String getReporter() { return reporter; }
    public void setReporter(String reporter) { this.reporter = reporter; }

    public String getDue_date() {
        return due_date;
    }
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDev() {
        return dev;
    }
    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getPhan_loai() {
        return phan_loai;
    }
    public void setPhan_loai(String phan_loai) {
        this.phan_loai = phan_loai;
    }

    public String getDo_uu_tien() {
        return do_uu_tien;
    }
    public void setDo_uu_tien(String do_uu_tien) {
        this.do_uu_tien = do_uu_tien;
    }

    public String getDo_nghiem_trong() {
        return do_nghiem_trong;
    }
    public void setDo_nghiem_trong(String do_nghiem_trong) {
        this.do_nghiem_trong = do_nghiem_trong;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getProject() {
        return project;
    }
    public void setProject(String project) {
        this.project = project;
    }

    public String getMo_ta() {
        return mo_ta;
    }
    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getId() {
        return id;
    }   
    public void setId(String id) {
        this.id = id;
    }
}