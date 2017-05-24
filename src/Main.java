import fileproc.AdvancedOutputFile;
import fileproc.CustomFileWriter;
import helpers.MatrixBuilder;
import helpers.Obstacle;
import helpers.PotentialPoint;
import service.MatrixSpace;
import service.WariantBAlt;

public class Main {

    public static void zestawB() {

        ///Zadanie 1
        MatrixSpace matrixSpace1 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(401, 201),
                MatrixBuilder.buildDoubleMatrix(401, 201),
                0.01
        );

        int i1 = 125;
        int i2 = 126;

        final Obstacle obstacle1 = new Obstacle()
                .addNewObstaclePoint(0.0, 50.0)
                .addNewObstaclePoint(i1, 50.0) //A
                .addNewObstaclePoint(i1, 200.0) //B
                .addNewObstaclePoint(0.0, 200.0)
                .addNewObstaclePoint(0.0, 50.0);

        final Obstacle obstacle2 = new Obstacle()
                .addNewObstaclePoint(i2, 50.0)
                .addNewObstaclePoint(400.0, 50.0) //A
                .addNewObstaclePoint(400.0, 200.0) //B
                .addNewObstaclePoint(i2, 200.0)
                .addNewObstaclePoint(i2, 50.0);

        matrixSpace1.addObstacle(obstacle1);
        matrixSpace1.addObstacle(obstacle2);

        WariantBAlt wariantBAlt1 = new WariantBAlt(matrixSpace1, i1, i2, -500.0);

        PotentialPoint[][] temp = wariantBAlt1.getMatrixSpace().getDoubleMatrix().getMatrix();

        for (int i = 0; i < 401; i++) {
            for (int j = 0; j < 201; j++) {
                System.out.print(temp[i][j].getWir() + " ");
            }
            System.out.println();
        }

        wariantBAlt1.calculateIntegral();

        PotentialPoint[][] potentialPoints = wariantBAlt1.getMatrixSpace().getDoubleMatrix().getMatrix();
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warB_pot1.dat")
        );

        CustomFileWriter.writeToFile(
                wariantBAlt1.generateVelocities().toString(), "warB_vel_x_2.dat"
        );

        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantBAlt1.generatePotentialAtPoint(0), 0.01, "warB_potnX0.dat")
        );
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantBAlt1.generatePotentialAtPoint(0), 0.01, "warB_WIRX0.dat", 0)
        );
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantBAlt1.generatePotentialAtPoint(2), 0.01, "warB_potnX2.dat")
        );
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(wariantBAlt1.generatePotentialAtPoint(2), 0.01, "warB_WIRX2.dat", 0)
        );


        //Zadanie 2

        i1 = 125;
        i2 = 275;

        final Obstacle obstacle3 = new Obstacle()
                .addNewObstaclePoint(0.0, 50.0)
                .addNewObstaclePoint(i1, 50.0) //A
                .addNewObstaclePoint(i1, 200.0) //B
                .addNewObstaclePoint(0.0, 200.0)
                .addNewObstaclePoint(0.0, 50.0);

        final Obstacle obstacle4 = new Obstacle()
                .addNewObstaclePoint(i2, 50.0)
                .addNewObstaclePoint(400.0, 50.0) //A
                .addNewObstaclePoint(400.0, 200.0) //B
                .addNewObstaclePoint(i2, 200.0)
                .addNewObstaclePoint(i2, 50.0);

        MatrixSpace matrixSpace2 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(401, 201),
                MatrixBuilder.buildDoubleMatrix(401, 201),
                0.01
        );

        matrixSpace2.addObstacle(obstacle3);
        matrixSpace2.addObstacle(obstacle4);

        WariantBAlt wariantBAlt2 = new WariantBAlt(matrixSpace2, i1, i2, -10.0);
        wariantBAlt2.calculateIntegral();

        potentialPoints = wariantBAlt2.getMatrixSpace().getDoubleMatrix().getMatrix();
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warB_potQ10.dat")
        );

        /// Q 100
        MatrixSpace matrixSpace3 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(401, 201),
                MatrixBuilder.buildDoubleMatrix(401, 201),
                0.01
        );

        matrixSpace3.addObstacle(obstacle3);
        matrixSpace3.addObstacle(obstacle4);

        WariantBAlt wariantBAlt3 = new WariantBAlt(matrixSpace3, i1, i2, -100.0);
        wariantBAlt3.calculateIntegral();

        potentialPoints = wariantBAlt3.getMatrixSpace().getDoubleMatrix().getMatrix();
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warB_potQ100.dat")
        );


        /// Q500
        MatrixSpace matrixSpace4 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(401, 201),
                MatrixBuilder.buildDoubleMatrix(401, 201),
                0.01
        );

        matrixSpace4.addObstacle(obstacle3);
        matrixSpace4.addObstacle(obstacle4);

        WariantBAlt wariantBAlt4 = new WariantBAlt(matrixSpace4, i1, i2, -500.0);
        wariantBAlt4.calculateIntegral();

        potentialPoints = wariantBAlt4.getMatrixSpace().getDoubleMatrix().getMatrix();
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warB_potQ500.dat")
        );


        //// Q1000
        MatrixSpace matrixSpace5 = new MatrixSpace(
                //                                  rows          columns
                MatrixBuilder.buildIntegerMatrix(401, 201),
                MatrixBuilder.buildDoubleMatrix(401, 201),
                0.01
        );

        matrixSpace5.addObstacle(obstacle3);
        matrixSpace5.addObstacle(obstacle4);

        WariantBAlt wariantBAlt5 = new WariantBAlt(matrixSpace5, i1, i2, -1000.0);
        wariantBAlt5.calculateIntegral();

        potentialPoints = wariantBAlt5.getMatrixSpace().getDoubleMatrix().getMatrix();
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warB_potQ1000.dat")
        );
        CustomFileWriter.writeToFile(
                new AdvancedOutputFile(potentialPoints, 0.01, "warB_wirQ1000.dat", 0)
        );
    }

    public static void main(String[] args) {

        zestawB();
        System.out.println("Hello World!");
    }
}
