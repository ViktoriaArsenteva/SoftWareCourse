package ru.geekbrains.lesson3.ocp;

import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new RightTriangle(2, 3));
        shapes.add(new Square(4));
        shapes.add(new Circle(3));
        double sumArea = 0;
        for (Shape shape : shapes){
            if (shape instanceof RightTriangle) {
                RightTriangle triangle = (RightTriangle) shape;
                sumArea += triangle.getKatet1() * triangle.getKatet2() / 2.0;
            }
            if (shape instanceof Square) {
                Square square = (Square) shape;
                sumArea += Math.pow(square.getSide(), 2);
            }

            if (shape instanceof Circle){
                Circle circle = (Circle) shape;
                sumArea += Math.PI * circle.getRadius() * circle.getRadius();
            }
        }

        System.out.printf("Сумма площадей фигур равна %f \n", sumArea);


        List<ShapeV2> shapesV2 = new ArrayList<>();
        shapesV2.add(new RightTriangleV2(2, 3));
        shapesV2.add(new SquareV2(4));
        shapesV2.add(new CircleV2(3));
        double sumAreaV2 = 0;
        for (ShapeV2 shape : shapesV2) {
            sumAreaV2 += shape.getArea();
        }
        System.out.printf("Сумма площадей фигур равна %f \n", sumArea);








    }

}
