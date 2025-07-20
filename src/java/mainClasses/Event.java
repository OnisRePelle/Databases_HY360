/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainClasses;

/**
 *
 * @author dimos
 */
public class Event {
    int EventID;
    String Name;
    String Date;
    String Time;
    String Type;
    int Capacity;
    int VipN;
    int NormalN;
    int VipP;
    int NormalP;

    public int getVipP() {
        return VipP;
    }

    public void setVipP(int VipP) {
        this.VipP = VipP;
    }

    public int getNormalP() {
        return NormalP;
    }

    public void setNormalP(int NormalP) {
        this.NormalP = NormalP;
    }


    public int getVipN() {
        return VipN;
    }

    public void setVipN(int VipN) {
        this.VipN = VipN;
    }

    public int getNormalN() {
        return NormalN;
    }

    public void setNormalN(int NormalN) {
        this.NormalN = NormalN;
    }


    public int getEventID() {
        return EventID;
    }

    public void setEventID(int EventID) {
        this.EventID = EventID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }

}
