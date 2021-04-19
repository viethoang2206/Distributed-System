/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import java.io.Serializable;

/**
 *
 * @author HuyPC
 */
public class Student implements Serializable{
    private String name;
    private String id;
    private int year;
    
    public Student(String name, String id, int year) {
        this.name = name;
        this.id = id;
        this.year = year;
    }
    
    public String getName() {
        return name;
    }
    
    public String getID() {
        return id;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
}
