/**
 * This file was generated by the Jeddict
 */
package educationproject.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Jakob
 */
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    public Teacher(String name) {
        this.name = name;
    }

    public Teacher() {
    }
    
    

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        getCourses().add(course);
        course.setTeacher(this);
    }

    public void removeCourse(Course course) {
        getCourses().remove(course);
        course.setTeacher(null);
    }

    public String toString(){
        return String.format("[%3d] %s", id, name);
    }
    
}