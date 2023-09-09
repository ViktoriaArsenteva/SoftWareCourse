package ru.geekbrains.lesson3;

public class WipingSt implements Wiping{

    @Override
    public void wipMirrors() {
        System.out.println("Зеркала помыты");
    }

    @Override
    public void wipWindshield() {
        System.out.println("Cтекло помыто");    }

    @Override
    public void wipHeadlights() {
        System.out.println("Фары помыты");
    }
    
}
