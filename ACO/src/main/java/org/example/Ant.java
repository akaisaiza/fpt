package org.example;

import java.util.Random;

public class Ant {
    //G=(V,E)
    // Khởi tạo mảng các đỉnh
    private int []vertices;
    //Khởi tạo mảng các đường đi
    private int []edges;
    private int length;
    private  int count;// number vertices
    private double alpha = 1.0;

    private double beta = 2.0;

    public int[] getVertices() {
        return vertices;
    }

    public int[] getEdges() {
        return edges;
    }

    public int getLength() {
        return length;
    }
    //khoi tao duong dan bat dau cua ant
    // count vertices
    public void init(int count) {
        this.count = count;
        vertices = new int[count];
        edges = new int[count + 1];
        for (int i = 0; i < count; i++) {
            vertices[i] = 0;
        }
        int random = new Random(System.currentTimeMillis()).nextInt(count);
        vertices[random] = 1;
        edges[0] = random;
    }

    public void selectNextEdges(int index, double[][] pheromone, int[][] distance) {
        int current = edges[index - 1];
        double[] p = new double[count];
        double sum = 0.0;//Tổng xác suất pheromone
        //Phần mẫu số của công thức
        for (int i = 0; i < count; i++) {
            if (vertices[i] == 0) {
                sum += Math.pow(pheromone[current][i], this.alpha)
                        * (Math.pow(1.0 / distance[current][i], this.beta));
            }
        }

        for (int i = 0; i < count; i++) {
            if (vertices[i] == 1) {
                p[i] = 0.0;
            } else {
                p[i] = Math.pow(pheromone[current][i], this.alpha)
                        * (Math.pow(1.0 / distance[current][i], this.beta)) / sum;
            }
        }
        int select = getRandom(p);
        edges[index] = select;
        vertices[select] = 1;
    }
    private int getRandom(double[] p) {
        double selectP = new Random(System.currentTimeMillis()).nextDouble();
        double sumSel = 0.0;
        for (int i = 0; i < count; i++) {
            sumSel += p[i];
            if (sumSel > selectP)
                return i;
        }
        return -1;
    }

    public void calcTourLength(int[][] distance) {
        length = 0;
        edges[count] = edges[0];
        for (int i = 0; i < count; i++) {
            length += distance[edges[i]][edges[i + 1]];
        }
    }
}
