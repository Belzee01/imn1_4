import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import helpers.*;
import service.MatrixSpace;
import service.WariantA;
import service.WariantB;

import java.util.Arrays;

import static service.WariantB.TYPE.NEUMANN;

public class Main {

    public static void zestawA() {
        final BoundingBox boundingBox = new BoundingBox(new MyPair(0.0, 3.0), new MyPair(0.0, 0.9));

        MatrixSpace matrixSpace = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(91, 301),
                MatrixBuilder.buildDoubleMatrix(91, 301),
                boundingBox,
                0.01
        );

        final Obstacle obstacle = new Obstacle()
                .addNewObstaclePoint(85.0, 90.0)
                .addNewObstaclePoint(85.0, 70.0) //A
                .addNewObstaclePoint(101.0, 70.0) //B
                .addNewObstaclePoint(101.0, 50.0) //C
                .addNewObstaclePoint(116.0, 50.0) //D
                .addNewObstaclePoint(116.0, 90.0)
                .addNewObstaclePoint(85.0, 90.0);

        matrixSpace.addObstacle(obstacle);

        PotentialPoint[][] temp = matrixSpace.getDoubleMatrix().getMatrix();

        for (PotentialPoint[] p : temp) {
            for (int i = 0; i < p.length; i++) {
                System.out.print(p[i].getObstacle() + "\t\t");
            }
            System.out.println();
        }

        WariantA wariantA = new WariantA(matrixSpace);
//
//        System.out.println(wariantA.calculateIntegral());
//
//        PotentialPoint[][] temp = wariantA.getMatrixSpace().getDoubleMatrix().getMatrix();
//
//        for (PotentialPoint[] p : temp) {
//            System.out.println(Arrays.toString(p));
//        }
//
//        CustomFileWriter.writeToFile(
//                new AdvancedOutputFile(temp, boundingBox, 1.0, "warA_pot.dat")
//        );
//
//        CustomFileWriter.writeToFile(wariantA.getIterationIntegralContainer(), "warA_integral.dat");


        ///Zadanie 2
//        matrixSpace = new MatrixSpace(
//                //                                  rows          columns
//                MatrixBuilder.buildIntegerMatrix(100, 200),
//                MatrixBuilder.buildDoubleMatrix(100, 200),
//                boundingBox,
//                1.0
//        );
//
//        matrixSpace.addObstacle(obstacle);
//
//        Obstacle obstaclePoints = new Obstacle()
//                .addNewObstaclePoint(85.0, 85.0) //A
//                .addNewObstaclePoint(100.0, 85.0) //B
//                .addNewObstaclePoint(100.0, 70.0) //C
//                .addNewObstaclePoint(115.0, 70.0); //D
//
//        WariantA wariantA1 = new WariantA(matrixSpace, obstaclePoints);
//
//        System.out.println(wariantA1.calculateIntegral());
//
//        temp = wariantA1.getMatrixSpace().getDoubleMatrix().getMatrix();
//
//        for (PotentialPoint[] p : temp) {
//            for (int i = 0; i < p.length; i++) {
//                System.out.print(p[i] + "\t\t");
//            }
//            System.out.println();
//        }
//
//        CustomFileWriter.writeToFile(
//                new AdvancedOutputFile(temp, boundingBox, 1.0, "warA_pot2.dat")
//        );
//
//        CustomFileWriter.writeToFile(wariantA1.getIterationIntegralContainer(), "warA_integral2.dat");
    }

    public static void zestawB() {
        final BoundingBox boundingBox = new BoundingBox(new MyPair(0.0, 4.0), new MyPair(0.0, 2.0));

        final Double jump = 0.01;

        MatrixSpace matrixSpace = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(201, (int) 401),
                MatrixBuilder.buildDoubleMatrix(201, 401),
                boundingBox,
                jump
        );

        final Obstacle obstacle1 = new Obstacle()
                .addNewObstaclePoint(0.0, 0.5)
                .addNewObstaclePoint(1.0, 0.5)
                .addNewObstaclePoint(1.0, 2.0)
                .addNewObstaclePoint(0.0, 2.0)
                .addNewObstaclePoint(0.0, 0.5);

        final Obstacle obstacle2 = new Obstacle()
                .addNewObstaclePoint(3.0, 0.0)
                .addNewObstaclePoint(4.0, 0.0)
                .addNewObstaclePoint(4.0, 1.5)
                .addNewObstaclePoint(3.0, 1.5)
                .addNewObstaclePoint(3.0, 0.0);

        matrixSpace.addObstacle(obstacle1).addObstacle(obstacle2);

        WariantB wariantB = new WariantB(matrixSpace, 1.9);

        wariantB.calculateIntegral();

        PotentialPoint[][] temp = wariantB.getMatrixSpace().getDoubleMatrix().getMatrix();

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(temp, boundingBox, jump, "warB_pot.dat")
        );

        CustomFileWriter.writeToFile(wariantB.getIterationIntegralContainer(), "warB_integral.dat");

        WariantB wariantB1 = new WariantB(matrixSpace, 1.9, NEUMANN);
        wariantB1.calculateIntegral();

        temp = wariantB1.getMatrixSpace().getDoubleMatrix().getMatrix();

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(temp, boundingBox, jump, "warB_pot2.dat")
        );

        CustomFileWriter.writeToFile(wariantB1.getIterationIntegralContainer(), "warB_integral2.dat");
    }

    public static void main(String[] args) {


        zestawA();

        System.out.println("Hello World!");
    }
}
