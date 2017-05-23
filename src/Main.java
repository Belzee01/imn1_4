import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import helpers.*;
import service.MatrixSpace;
import service.WariantA;
import service.WariantAAlt;
import service.WariantB;

import java.util.Arrays;

import static service.WariantA.TYPE.OBSTACLE;

public class Main {

    public static void zestawA() {
        MatrixSpace matrixSpace = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(301, 91),
                MatrixBuilder.buildDoubleMatrix(301, 91),
                0.01
        );

        WariantAAlt wariantA = new WariantAAlt(matrixSpace);

        wariantA.executeTaskOne();

        PotentialPoint[][] potentialPoints = wariantA.getMatrixSpace().getDoubleMatrix().getMatrix();

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warA_pot.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warA_wir.dat", 0)
        );

        CustomFileWriter.writeToFile(
                wariantA.generateVelocities().toString(), "warA_vel.dat"
        );

        CustomFileWriter.writeToFile(
                wariantA.evaluatePotencjalAndWirowoscAt(50).toString(), "warA_pot_i_50.dat"
        );

        CustomFileWriter.writeToFile(
                wariantA.evaluatePotencjalAndWirowoscAt(250).toString(), "warA_pot_i_250.dat"
        );


        /// Zadanie 2

        MatrixSpace matrixSpace2 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(301, 91),
                MatrixBuilder.buildDoubleMatrix(301, 91),
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

        matrixSpace2.addObstacle(obstacle);

        ///// Q = -1.0
        WariantAAlt wariantA2 = new WariantAAlt(matrixSpace2, WariantAAlt.TYPE.OBSTACLE, -1.0);

        wariantA2.executeTaskOne();

        PotentialPoint[][] potentialPoints2 = wariantA2.getMatrixSpace().getDoubleMatrix().getMatrix();

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints2, 0.01, "warA_pot2_Q1.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantA2.generateUVelocityMatrix(), 0.01, "warA_pot2_Q1_U.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantA2.generateVVelocityMatrix(), 0.01, "warA_pot2_Q1_V.dat")
        );


        MatrixSpace matrixSpace3 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(301, 91),
                MatrixBuilder.buildDoubleMatrix(301, 91),
                0.01
        );
        final Obstacle obstacle2 = new Obstacle()
                .addNewObstaclePoint(85.0, 90.0)
                .addNewObstaclePoint(85.0, 70.0) //A
                .addNewObstaclePoint(101.0, 70.0) //B
                .addNewObstaclePoint(101.0, 50.0) //C
                .addNewObstaclePoint(116.0, 50.0) //D
                .addNewObstaclePoint(116.0, 90.0)
                .addNewObstaclePoint(85.0, 90.0);

        matrixSpace3.addObstacle(obstacle2);
        ///// Q = -150.0
        WariantAAlt wariantA3 = new WariantAAlt(matrixSpace3, WariantAAlt.TYPE.OBSTACLE, -150.0);

        wariantA3.executeTaskOne();

        PotentialPoint[][] potentialPoints3 = wariantA3.getMatrixSpace().getDoubleMatrix().getMatrix();

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints3, 0.01, "warA_pot2_Q150.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantA3.generateUVelocityMatrix(), 0.01, "warA_pot2_Q150_U.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantA3.generateVVelocityMatrix(), 0.01, "warA_pot2_Q150_V.dat")
        );





        MatrixSpace matrixSpace4 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(301, 91),
                MatrixBuilder.buildDoubleMatrix(301, 91),
                0.01
        );
        final Obstacle obstacle3 = new Obstacle()
                .addNewObstaclePoint(85.0, 90.0)
                .addNewObstaclePoint(85.0, 70.0) //A
                .addNewObstaclePoint(101.0, 70.0) //B
                .addNewObstaclePoint(101.0, 50.0) //C
                .addNewObstaclePoint(116.0, 50.0) //D
                .addNewObstaclePoint(116.0, 90.0)
                .addNewObstaclePoint(85.0, 90.0);

        matrixSpace4.addObstacle(obstacle3);
        ///// Q = -400.0
        WariantAAlt wariantA4 = new WariantAAlt(matrixSpace4, WariantAAlt.TYPE.OBSTACLE, -400.0);

        wariantA4.executeTaskOne();

        PotentialPoint[][] potentialPoints4 = wariantA4.getMatrixSpace().getDoubleMatrix().getMatrix();

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints4, 0.01, "warA_pot2_Q400.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantA4.generateUVelocityMatrix(), 0.01, "warA_pot2_Q400_U.dat")
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantA4.generateVVelocityMatrix(), 0.01, "warA_pot2_Q400_V.dat")
        );
    }

    public static void zestawB() {

    }

    public static void main(String[] args) {


        zestawA();

        System.out.println("Hello World!");
    }
}
