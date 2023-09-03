package ru.geekbrains.lesson1.store3D.models;

import javax.swing.text.AttributeSet.ColorAttribute;

public class Flash {

    private Point3D location;
    private String color;
    private Float power;

    public void Move(Point3D location){
        
    }



    /**
     * @return String return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return Float return the power
     */
    public Float getPower() {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(Float power) {
        this.power = power;
    }

}
