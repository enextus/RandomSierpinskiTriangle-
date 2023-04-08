package org.serpinskitriangle;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnimatedSierpinskiTriangleThree extends JPanel {

    @NotNull
    private static final int MAX_DEPTH = 5; // should be > 2
    private static final Color[] COLOR_MAP = {Color.BLUE, Color.RED, Color.GREEN, Color.WHITE, Color.YELLOW, Color.MAGENTA, Color.ORANGE};
    private static final Color[] COLOR_MAP_TWO = {Color.BLACK};
    private static final int DELAY = 700;
    private static final int DELAY_SHORT = 0;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int FONT_SIZE = 32;
    private static final int DOT_RADIUS = 3;
    private final ArrayList<Point[]> triangles = new ArrayList<>();
    private int triangleCount = 1;
    private final JLabel countLabel = new JLabel("Triangles: 1");

    public static void main(String[] args) {

        JFrame frame = new JFrame("Animated Sierpinski Triangle Three");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        AnimatedSierpinskiTriangleThree contentPane = new AnimatedSierpinskiTriangleThree();
        frame.setContentPane(contentPane);
        frame.setVisible(true);

        System.out.println("2.contentPane: " + contentPane);

        contentPane.generateTriangles();

    }

    public AnimatedSierpinskiTriangleThree() {
        Font currentFont = countLabel.getFont(); // Get the current font
        Font newFont = currentFont.deriveFont((float) FONT_SIZE); // Создайте новый шрифт на основе текущего шрифта с новым размером
        countLabel.setFont(newFont); // Set the new font for countLabel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        add(countLabel, BorderLayout.NORTH);
    }

    public void generateTriangles() {
        Point[] points = getInitialTriangle(WIDTH, HEIGHT);
/*        System.out.println("points[0: " + points[0].toString());
        System.out.println("X: " + points[0].getX() + ", Y: " + points[0].getY());*/
        triangles.add(points);

//        System.out.println("1.triangles: " + triangles);

        /*
        size(): 1
        getClass(): class java.util.ArrayList
        triangles.get(0): [java.awt.Point[x=500,y=0], java.awt.Point[x=0,y=1000], java.awt.Point[x=1000,y=1000]]
         */
/*        for (Point[] tr : triangles) {
            System.out.println("size(): " + triangles.size());
            System.out.println("getClass(): " + triangles.getClass());
            System.out.println("triangles.get(0): " + Arrays.toString(triangles.get(0)));
        }*/

        divideTriangle(points, 0);
    }

/*    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < triangles.size(); i++) {
            g.setColor(COLOR_MAP[i % COLOR_MAP.length]);
            int[] x = {(int) triangles.get(i)[0].getX(), (int) triangles.get(i)[1].getX(), (int) triangles.get(i)[2].getX()};
            int[] y = {(int) triangles.get(i)[0].getY(), (int) triangles.get(i)[1].getY(), (int) triangles.get(i)[2].getY()};
            g.fillPolygon(x, y, 3);
        }
    }*/

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int dotRadius = DOT_RADIUS; // Radius of the bold dot (you can adjust this value)

        for (int i = 0; i < triangles.size(); i++) {
            g.setColor(COLOR_MAP_TWO[i % COLOR_MAP_TWO.length]);

            for (Point point : triangles.get(i)) {
                int x = (int) point.getX() - dotRadius / 2;
                int y = (int) point.getY() - dotRadius / 2;

                g.fillOval(x, y, dotRadius, dotRadius);
            }

            g.setColor(COLOR_MAP[i % COLOR_MAP.length]);
            int[] x = {(int) triangles.get(i)[0].getX(), (int) triangles.get(i)[1].getX(), (int) triangles.get(i)[2].getX()};
            int[] y = {(int) triangles.get(i)[0].getY(), (int) triangles.get(i)[1].getY(), (int) triangles.get(i)[2].getY()};
            g.fillPolygon(x, y, 3);
        }
    }

    void divideTriangle(Point[] points, int depth) {
        if (depth < MAX_DEPTH) {
            Point p1 = midpoint(points[0], points[1]);
            Point p2 = midpoint(points[1], points[2]);
            Point p3 = midpoint(points[0], points[2]);
            Point[] triangle1 = {points[0], p1, p3};
            Point[] triangle2 = {p1, points[1], p2};
            Point[] triangle3 = {p3, p2, points[2]};

            // add new triangle in the frame
            triangles.add(triangle1);
            takeDelay(DELAY_SHORT);
            triangles.add(triangle2);
            takeDelay(DELAY_SHORT);
            triangles.add(triangle3);

            triangleCount += 3;
            countLabel.setText("Triangles: " + triangleCount);
            repaint();

            takeDelay(DELAY);

            divideTriangle(triangle1, depth + 1);
            divideTriangle(triangle2, depth + 1);
            divideTriangle(triangle3, depth + 1);
        }
    }

    private static void takeDelay(int delayShort) {
        try {
            Thread.sleep(delayShort);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Point[] getInitialTriangle(int width, int height) {
        Point[] points = new Point[3];
        points[0] = new Point(width / 2, 0);
        points[1] = new Point(0, height);
        points[2] = new Point(width, height);
        return points;
    }

    Point midpoint(Point p1, Point p2) {
        int x = (int) ((p1.getX() + p2.getX()) / 2);
        int y = (int) ((p1.getY() + p2.getY()) / 2);
        return new Point(x, y);
    }

}
