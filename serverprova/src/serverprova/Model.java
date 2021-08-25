package serverprova;


import java.io.Serializable;

public class Model implements Serializable{

 /**
  * 
  */
 private static final long serialVersionUID = 1L;
 private String name;
 private int id;
 private long lastTimsStamp;
 public Model(String name, int id,long lastTimsStamp) {
  super();
  this.name = name;
  this.id = id;
  this.lastTimsStamp = lastTimsStamp;
 }
 public String getName() {
  return name;
 }
 public int getId() {
  return id;
 }
 public long getLastTimsStamp() {
  return lastTimsStamp;
 }
 
 
}
