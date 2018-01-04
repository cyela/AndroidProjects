package com.example.chandrakanth.notekeeper;

/**
 * Created by Chandrakanth on 2/27/2017.
 */

public class Note {
    int id;
    String note, priority,status;

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    String update_time;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Note( String note, String priority, String status,String update_time ) {

        this.note = note;
        this.priority = priority;
        this.status = status;
        this.update_time=update_time;

    }

    public Note(){

    }


}
