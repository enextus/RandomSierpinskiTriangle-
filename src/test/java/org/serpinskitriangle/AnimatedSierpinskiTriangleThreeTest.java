package org.serpinskitriangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.Timer;

import static org.junit.jupiter.api.Assertions.*;

class AnimatedSierpinskiTriangleThreeTest {

    private AnimatedSierpinskiTriangleThree triangle;

    @BeforeEach
    void setUp() {
        triangle = new AnimatedSierpinskiTriangleThree();
    }

    @Test
    void getInitialTriangle() {
        Point[] initialTriangle = triangle.getInitialTriangle(600, 600);
        assertNotNull(initialTriangle);
        assertEquals(3, initialTriangle.length);
        assertEquals(new Point(300, 0), initialTriangle[0]);
        assertEquals(new Point(0, 600), initialTriangle[1]);
        assertEquals(new Point(600, 600), initialTriangle[2]);
    }

    @Test
    void midpoint() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(10, 10);
        Point mid = triangle.midpoint(p1, p2);
        assertNotNull(mid);
        assertEquals(new Point(5, 5), mid);
    }

    @Test
    void testMidpoint() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(4, 4);
        Point midpoint = AnimatedSierpinskiTriangleThree.midpoint(p1, p2);
        assertEquals(new Point(2, 2), midpoint);
    }

    @Test
    void testGetInitialTriangle() {
        Point[] initialTriangle = AnimatedSierpinskiTriangleThree.getInitialTriangle(1000, 1000);
        assertEquals(new Point(500, 0), initialTriangle[0]);
        assertEquals(new Point(0, 1000), initialTriangle[1]);
        assertEquals(new Point(1000, 1000), initialTriangle[2]);
    }

    @Test
    void testDivideTriangleDepthZero() {
        Point[] points = {new Point(0, 0), new Point(4, 0), new Point(2, 4)};
        Timer dummyTimer = new Timer(1000, e -> {}); // Создаем фиктивный таймер
        triangle.triangles.clear(); // Очистка списка треугольников
        triangle.triangles.add(points); // Добавление исходного треугольника
        triangle.divideTriangle(points, 0, dummyTimer);
        assertEquals(4, triangle.triangles.size());
    }

    @Test
    void testDivideTriangleMaxDepth() {
        Point[] points = {new Point(0, 0), new Point(4, 0), new Point(2, 4)};
        Timer dummyTimer = new Timer(1000, e -> {}); // Создаем фиктивный таймер
        triangle.triangles.clear(); // Очистка списка треугольников
        triangle.triangles.add(points); // Добавление исходного треугольника
        triangle.divideTriangle(points, 6, dummyTimer);
        assertEquals(1, triangle.triangles.size());
    }

    @Test
    void testDivideTriangleExceedMaxTriangles() {
        triangle.triangleCount = 499999;
        Point[] points = {new Point(0, 0), new Point(4, 0), new Point(2, 4)};
        Timer dummyTimer = new Timer(1000, e -> {}); // Создаем фиктивный таймер
        triangle.triangles.clear(); // Очистка списка треугольников
        triangle.triangles.add(points); // Добавление исходного треугольника
        triangle.divideTriangle(points, 0, dummyTimer);
        assertEquals(1, triangle.triangles.size());
    }

    @Test
    void testGenerateTriangles() {
        triangle.generateTriangles();
        assertEquals(1, triangle.triangles.size());
    }

    @Test
    void testPaintComponent() {
        JFrame frame = new JFrame();
        frame.add(triangle);
        frame.setSize(200, 200);
        frame.setVisible(true);

        triangle.generateTriangles();
        triangle.repaint(); // Добавляем вызов repaint()
        triangle.paintComponent(frame.getGraphics());
        assertEquals(1, triangle.triangles.size());
    }

    @Test
    void testInitialTrianglePoints() {
        Point[] points = AnimatedSierpinskiTriangleThree.getInitialTriangle(800, 800);
        assertEquals(new Point(400, 0), points[0]);
        assertEquals(new Point(0, 800), points[1]);
        assertEquals(new Point(800, 800), points[2]);
    }

    @Test
    void testMidpointCalculation() {
        Point p1 = new Point(100, 200);
        Point p2 = new Point(300, 400);
        Point mid = AnimatedSierpinskiTriangleThree.midpoint(p1, p2);
        assertEquals(new Point(200, 300), mid);
    }

    @Test
    void testTriangleColorMapping() {
        assertEquals(Color.BLUE, AnimatedSierpinskiTriangleThree.COLOR_MAP[0]);
        assertEquals(Color.RED, AnimatedSierpinskiTriangleThree.COLOR_MAP[1]);
    }

    @Test
    void testTriangleAddition() {
        Point[] points = {new Point(0, 0), new Point(4, 0), new Point(2, 4)};
        triangle.triangles.add(points);
        assertEquals(1, triangle.triangles.size());
    }

    @Test
    void testTriangleCountLabel() {
        assertEquals("Triangles: 1", triangle.countLabel.getText());
        triangle.triangleCount = 10;
        triangle.countLabel.setText("Triangles: " + triangle.triangleCount);
        assertEquals("Triangles: 10", triangle.countLabel.getText());
    }

    @Test
    void testTriangleCountIncrement() {
        triangle.triangleCount = 10;
        triangle.triangleCount += 3;
        assertEquals(13, triangle.triangleCount);
    }

    @Test
    void testMaxDepthConstant() {
        assertEquals(6, AnimatedSierpinskiTriangleThree.MAX_DEPTH);
    }

    @Test
    void testMaxTrianglesConstant() {
        assertEquals(4645, AnimatedSierpinskiTriangleThree.MAX_TRIANGLES);
    }
}
