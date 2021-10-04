package ar.edu.itba.paw.webapp.forms;

public class FavForm {
    private int teacherId;
    private int studentId;

    public int getStudentId() {
        return studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
