import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import helpers.*;
import service.MatrixSpace;
import service.WariantA;
import service.WariantB;

import java.nio.file.Watchable;
import java.util.Arrays;

import static service.WariantA.TYPE.OBSTACLE;

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

        WariantA wariantA = new WariantA(matrixSpace);

        wariantA.generateStrumienAndWirowosc();
        PotentialPoint[][] temp = wariantA.getMatrixSpace().getDoubleMatrix().getMatrix();

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(temp, boundingBox, 0.01, "warA_pot1.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(temp, boundingBox, 0.01, "warA_wir1.dat", 0)
        );

        CustomFileWriter.writeToFile(
                wariantA.evaluatePotencjalAndWirowoscAt(50).toString(), "warA_i50.dat"
        );

        CustomFileWriter.writeToFile(
                wariantA.evaluatePotencjalAndWirowoscAt(250).toString(), "warA_i250.dat"
        );

        CustomFileWriter.writeToFile(
                wariantA.generateVelocities().toString(), "warA_vel1.dat"
        );


        ///Zadanie 2
        matrixSpace = new MatrixSpace(
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


        ///// Q = -1.0
        WariantA wariantA1 = new WariantA(matrixSpace, -1.0, OBSTACLE);

        wariantA1.generateStrumienAndWirowosc();

        temp = wariantA1.getMatrixSpace().getDoubleMatrix().getMatrix();

        for (PotentialPoint[] p : temp) {
            System.out.println(Arrays.toString(p));
        }

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(temp, boundingBox, 0.01, "warA_pot2_Q1.dat")
        );

        ///// Q = -150.0
        wariantA1 = new WariantA(matrixSpace, -150.0, OBSTACLE);

        wariantA1.generateStrumienAndWirowosc();

        temp = wariantA1.getMatrixSpace().getDoubleMatrix().getMatrix();

        for (PotentialPoint[] p : temp) {
            System.out.println(Arrays.toString(p));
        }

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(temp, boundingBox, 0.01, "warA_pot2_Q2.dat")
        );

        ///// Q = -400.0
        wariantA1 = new WariantA(matrixSpace, -400.0, OBSTACLE);

        wariantA1.generateStrumienAndWirowosc();
        temp = wariantA1.getMatrixSpace().getDoubleMatrix().getMatrix();

        for (PotentialPoint[] p : temp) {
            System.out.println(Arrays.toString(p));
        }

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(temp, boundingBox, 0.01, "warA_pot2_Q3.dat")
        );

    }

    public static void zestawB() {
        final BoundingBox boundingBox = new BoundingBox(new MyPair(0.0, 4.0), new MyPair(0.0, 2.0));

        MatrixSpace matrixSpace = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(201, 401),
                MatrixBuilder.buildDoubleMatrix(201, 401),
                boundingBox,
                0.01
        );


        final Obstacle obstacle1 = new Obstacle()
                .addNewObstaclePoint(275.0, 50.0)
                .addNewObstaclePoint(400.0, 50.0) //A
                .addNewObstaclePoint(400.0, 200.0) //B
                .addNewObstaclePoint(275.0, 200.0) //C
                .addNewObstaclePoint(275.0, 50.0);

        final Obstacle obstacle2 = new Obstacle()
                .addNewObstaclePoint(0.0, 50.0)
                .addNewObstaclePoint(125.0, 50.0) //A
                .addNewObstaclePoint(125.0, 200.0) //B
                .addNewObstaclePoint(0.0, 200.0) //C
                .addNewObstaclePoint(0.0, 50.0);


        WariantB wariantB = new WariantB(matrixSpace);

        PotentialPoint[][] temp = wariantB.getMatrixSpace().getDoubleMatrix().getMatrix();

        for (PotentialPoint[] p : temp) {
            System.out.println(Arrays.toString(p));
        }

    }

    public static void main(String[] args) {


        zestawB();

        System.out.println("Hello World!");
    }
}
