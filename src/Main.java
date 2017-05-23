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
        final BoundingBox boundingBox = new BoundingBox(new MyPair(0.0, 3.0), new MyPair(0.0, 0.9));

        MatrixSpace matrixSpace = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(301, 91),
                MatrixBuilder.buildDoubleMatrix(301, 91),
                0.01
        );

        WariantAAlt wariantA = new WariantAAlt(matrixSpace);
        PotentialPoint[][] potentialPoints = wariantA.getMatrixSpace().getDoubleMatrix().getMatrix();

        for (int i = 0; i < potentialPoints.length; i++) {
            for (int j = 0; j < potentialPoints[0].length; j++) {
                System.out.print(potentialPoints[i][j].getValue() + "\t");
            }
            System.out.println();
        }

//        wariantA.executeTaskOne();
//
//        PotentialPoint[][] potentialPoints = wariantA.getMatrixSpace().getDoubleMatrix().getMatrix();
//
//        CustomFileWriter.writeToFile(
//                new AdvancedOutputFile(potentialPoints, 0.01, "warA_pot.dat")
//        );
//
//        CustomFileWriter.writeToFile(
//                new AdvancedOutputFile(potentialPoints, 0.01, "warA_wir.dat", 0)
//        );
//
//        CustomFileWriter.writeToFile(
//                wariantA.generateVelocities().toString(), "warA_vel.dat"
//        );
    }

    public static void zestawB() {

    }

    public static void main(String[] args) {


        zestawA();

        System.out.println("Hello World!");
    }
}
