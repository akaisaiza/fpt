package org.example;

public class Aco {
    Ant[] ants;
    int antCount;
    int[][] distance;
    double[][] pheromone;
    int VerticeCount;
    int[] bestTour;
    String[] vertices;
    int bestLength;
    public void init(int antCount) {
        this.antCount = antCount;
        ants = new Ant[antCount];
        Vertices vertice = new Vertices();
        vertice.initDis();
        distance = vertice.getDistance();
        vertices = vertice.getVerName();
        VerticeCount = vertices.length;
        pheromone = new double[VerticeCount][VerticeCount];
        for (int i = 0; i < VerticeCount; i++) {
            for (int j = 0; j < VerticeCount; j++) {
                pheromone[i][j] = 1;
            }
        }
        bestLength = Integer.MAX_VALUE;
        bestTour = new int[VerticeCount + 1];
        for (int i = 0; i < antCount; i++) {
            ants[i] = new Ant();
            ants[i].init(VerticeCount);
        }
    }
    public void run(int maxgen) {
        for (int gen = 0; gen < maxgen; gen++) {
            //Quá trình di chuyển của từng con kiến
            for (int i = 0; i < antCount; i++) {
                //lựa chọn tuyến đường đô thị cho kiến này
                for (int j = 1; j < VerticeCount; j++) {
                    ants[i].selectNextEdges(j, pheromone, distance);
                }
                //Tính tổng chiều dài của con đường mà con kiến đi qua
                ants[i].calcTourLength(distance);
                //Xác định xem đó có phải là tuyến đường tốt nhất không
                if (ants[i].getLength() < bestLength) {

                    bestLength = ants[i].getLength();
                   System.out.println( gen + " giải pháp mới đã được tìm thấy như：" + bestLength);
                    for (int j = 0; j < VerticeCount + 1; j++) {
                        bestTour[j] = ants[i].getEdges()[j];
                     System.out.print(vertices[bestTour[j]] + " ");
                    }
                   System.out.println();
                }
            }
            //update Pheromone
            updatePheromone();

            for (int i = 0; i < antCount; i++) {
                ants[i].init(VerticeCount);
            }
        }
        System.out.println("end");
    }
    private void updatePheromone() {
        double rou = 0.5;//pheromone evaporation coefficien
        for (int i = 0; i < VerticeCount; i++) {
            for (int j = 0; j < VerticeCount; j++) {
                pheromone[i][j] *= (1 - rou);
            }
        }
        for (int i = 0; i < antCount; i++) {
            for (int j = 0; j < VerticeCount; j++) {
                int curId = ants[i].getEdges()[j];
                int nextId = ants[i].getEdges()[j + 1];
                pheromone[curId][nextId] += 1.0 / ants[i].getLength();
            }
        }
    }
    public void reportResult() {
        System.out.println("The best Length " + bestLength);
        for (int j = 0; j < VerticeCount + 1; j++) {
            System.out.print(vertices[bestTour[j]] + " ");
        }
    }
}
