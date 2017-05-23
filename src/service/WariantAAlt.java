package service;

import helpers.MatrixBuilder;
import helpers.PotentialAndWirContainer;
import helpers.PotentialPoint;
import helpers.VelocitiesByIteration;

public class WariantAAlt {

    public enum TYPE {
        NONE,
        OBSTACLE
    }

    private MatrixSpace matrixSpace;
    private TYPE type;

    private double Q = -1.0;
    private double ni = 1.0;
    private double jump = 0.01;

    public WariantAAlt(MatrixSpace matrixSpace) {
        this.matrixSpace = matrixSpace;
        this.jump = matrixSpace.getJump();
        this.type = TYPE.NONE;

        evaluateInitialEdges();
    }

    public WariantAAlt(MatrixSpace matrixSpace, TYPE type, double Q) {
        this.matrixSpace = matrixSpace;
        this.jump = matrixSpace.getJump();
        this.Q = Q;

        this.type = type;
        evaluateInitialEdgesWithObstacle();
    }

    public void evaluateInitialEdgesWithObstacle() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        ///// Potencjal
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setValue(
                    calculateInitialPotential(0.0)
            );

            if (i <= 85 || i >= 116)    //A and G
                potentialPoints[i][90].setValue(
                        calculateInitialPotential(0.9)
                );
            if (i >= 85 && i <= 101)    //C
                potentialPoints[i][70].setValue(
                        calculateInitialPotential(0.9)
                );
            if (i >= 101 && i <= 116)//E
                potentialPoints[i][50].setValue(
                        calculateInitialPotential(0.9)
                );
        }

        for (int j = 0; j <= 90; j++) {
            potentialPoints[0][j].setValue(
                    calculateInitialPotential(j * jump)
            );
            potentialPoints[300][j].setValue(
                    calculateInitialPotential(j * jump)
            );

            if (j >= 70 && j <= 90) //B
                potentialPoints[85][j].setValue(
                        calculateInitialPotential(0.9)
                );
            if (j >= 50 && j <= 70) //D
                potentialPoints[101][j].setValue(
                        calculateInitialPotential(0.9)
                );
            if (j >= 50 && j <= 90) //F
                potentialPoints[116][j].setValue(
                        calculateInitialPotential(0.9)
                );
        }

        for (int i = 0; i <= 300; i++) {
            for (int j = 0; j <= 90; j++) {
                if (checkBounds(i, j))
                    potentialPoints[i][j].setValue(
                            calculateInitialPotential(j * jump)
                    );
            }
        }


        /////Wirowosc
        for (int j = 0; j <= 90; j++) {
            potentialPoints[0][j].setWir(
                    calculateInitialWir(j * jump)
            );

            potentialPoints[300][j].setWir(
                    calculateInitialWir(j * jump)
            );

        }

        for (int i = 0; i <= 300; i++) {
            for (int j = 0; j <= 90; j++) {
                if (checkBounds(i, j))
                    potentialPoints[i][j].setWir(
                            calculateInitialWir(j * jump)
                    );
            }
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);

        renewWirEdges();
    }

    private Boolean checkBounds(int i, int j) {
        Boolean toReturn = true;
        if (j == 0)
            toReturn = false;
        if (j == 90)
            toReturn = false;
        if (i == 0)
            toReturn = false;
        if (i == 300)
            toReturn = false;

        if ((i >= 85 && i <= 101) && j >= 70)    //C
            toReturn = false;
        if ((i >= 101 && i <= 116) && j >= 50)//E
            toReturn = false;

        return toReturn;
    }

    private void renewWirEdges() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setWir(
                    2.0 * (potentialPoints[i][1].getValue() - potentialPoints[i][0].getValue()) / (jump * jump)
            );    //I

            if (i <= 85 || i >= 116)    //A and G
                potentialPoints[i][90].setWir(
                        2.0 * (potentialPoints[i][89].getValue() - potentialPoints[i][90].getValue()) / (jump * jump)
                );
            if (i >= 85 && i <= 101)    //C
                potentialPoints[i][70].setWir(
                        2.0 * (potentialPoints[i][69].getValue() - potentialPoints[i][70].getValue()) / (jump * jump)
                );
            if (i >= 101 && i <= 116)//E
                potentialPoints[i][50].setWir(
                        2.0 * (potentialPoints[i][49].getValue() - potentialPoints[i][50].getValue()) / (jump * jump)
                );
        }

        for (int j = 0; j <= 90; j++) {
            if (j >= 70 && j <= 90) //B
                potentialPoints[85][j].setWir(
                        2.0 * (potentialPoints[84][j].getValue() - potentialPoints[85][j].getValue()) / (jump * jump)
                );
            if (j >= 50 && j <= 70) //D
                potentialPoints[101][j].setWir(
                        2.0 * (potentialPoints[100][j].getValue() - potentialPoints[101][j].getValue()) / (jump * jump)
                );
            if (j >= 50 && j <= 90) //F
                potentialPoints[116][j].setWir(
                        2.0 * (potentialPoints[117][j].getValue() - potentialPoints[116][j].getValue()) / (jump * jump)
                );
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    private double calculateInitialPotential(double y) {
        return (this.Q / (2.0 * this.ni)) * (Math.pow(y, 3) / 3.0 - (Math.pow(y, 2.0) / 2.0) * (0.0 + 0.9) + (0.0) * (0.9) * y);
    }

    private double calculateInitialWir(double y) {
        return (this.Q / (2.0 * this.ni)) * (2.0 * y - 0.0 - 0.9);
    }

    public void evaluateInitialEdges() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        // Initialize potential
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setValue(
                    calculateInitialPotential(potentialPoints[i][0].getY())
            );
            potentialPoints[i][90].setValue(
                    calculateInitialPotential(potentialPoints[i][90].getY())
            );
        }

        for (int i = 0; i <= 90; i++) {
            potentialPoints[0][i].setValue(
                    calculateInitialPotential(potentialPoints[0][i].getY())
            );
            potentialPoints[300][i].setValue(
                    calculateInitialPotential(potentialPoints[300][i].getY())
            );
        }


        // Initialize wirowosc
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setWir(
                    calculateInitialWir(potentialPoints[i][0].getY())
            );
            potentialPoints[i][90].setWir(
                    calculateInitialWir(potentialPoints[i][90].getY())
            );
        }

        for (int i = 0; i <= 90; i++) {
            potentialPoints[0][i].setWir(
                    calculateInitialWir(potentialPoints[0][i].getY())
            );
            potentialPoints[300][i].setWir(
                    calculateInitialWir(potentialPoints[300][i].getY())
            );
        }
        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);

    }

    public void calculatePotential() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();
        for (int i = 1; i < 300; i++) {
            for (int j = 1; j < 90; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
//                if (checkBounds(i, j)) {
                    Double value = (((potentialPoints[i + 1][j].getValue() +
                            potentialPoints[i - 1][j].getValue() +
                            potentialPoints[i][j - 1].getValue() +
                            potentialPoints[i][j + 1].getValue() -
                            (potentialPoints[i][j].getWir() * jump * jump))) / 4.0);

                    potentialPoints[i][j].setValue(value);
                }
            }
        }
        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    public void calculateWirowosc() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        for (int i = 1; i < 300; i++) {
            for (int j = 1; j < 90; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
//                if (checkBounds(i, j)) {
                    Double value = ((potentialPoints[i + 1][j].getWir() +
                            potentialPoints[i - 1][j].getWir() +
                            potentialPoints[i][j - 1].getWir() +
                            potentialPoints[i][j + 1].getWir()) / 4.0) -
                            ((potentialPoints[i][j + 1].getValue() - potentialPoints[i][j - 1].getValue()) *
                                    (potentialPoints[i + 1][j].getWir() - potentialPoints[i - 1][j].getWir()) -
                                    (potentialPoints[i + 1][j].getValue() - potentialPoints[i - 1][j].getValue()) *
                                            (potentialPoints[i][j + 1].getWir() - potentialPoints[i][j - 1].getWir())
                            ) / 16.0;
                    potentialPoints[i][j].setWir(value);

                    Double poten = (((potentialPoints[i + 1][j].getValue() +
                            potentialPoints[i - 1][j].getValue() +
                            potentialPoints[i][j - 1].getValue() +
                            potentialPoints[i][j + 1].getValue() -
                            (potentialPoints[i][j].getWir() * jump * jump))) / 4.0);

                    potentialPoints[i][j].setValue(poten);
                }
            }
        }
        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);

    }

    private Double evaluateVelocity(Double y) {
        return (Q / (2 * ni)) * (y - 0.0) * (y - 0.9);
    }

    public void executeTaskOne() {
        int x = 145;
        int y = 45;
        Double diffStr = 0.0;
        Double diffWir = 0.0;
        int k = 0;

        PotentialPoint[][] potentialPoints;

        PotentialPoint potentialPoint = new PotentialPoint(matrixSpace.getDoubleMatrix().getMatrix()[x][y]);
        do {

            potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

            if (type == TYPE.OBSTACLE) {
                renewWirEdges();
            }
            //policz
            calculateWirowosc();
//            calculatePotential();

            PotentialPoint newPoint = new PotentialPoint(matrixSpace.getDoubleMatrix().getMatrix()[x][y]);

            diffStr = Math.abs((potentialPoint.getValue() - newPoint.getValue()));
            diffWir = Math.abs((potentialPoint.getWir() - newPoint.getWir()));

            potentialPoint = new PotentialPoint(newPoint);
            System.out.println("Iteration:" + k++ + " diffStr: " + diffStr + " diffWir: " + diffWir);
        }
        while (diffStr > 10e-7 || diffWir > 10e-7 || k < 100);
    }

    public VelocitiesByIteration generateVelocities() {

        int x = 50;
        VelocitiesByIteration velocitiesByIteration = new VelocitiesByIteration();
        PotentialPoint[] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix()[x];

        for (int i = 1; i < 90; i++) {

            Double velocity = (potentialPoints[i + 1].getValue() - potentialPoints[i - 1].getValue()) / (2 * jump);
            Double velocityAnal = evaluateVelocity(potentialPoints[i].getY());
            velocitiesByIteration.addVelocities(velocity, velocityAnal);

        }
        return velocitiesByIteration;
    }

    public PotentialPoint[][] generateUVelocityMatrix() {
        PotentialPoint[][] velocity = new PotentialPoint[301][91];
        for (int i = 0; i < 301; i++) {
            for (int j = 0; j < 91; j++) {
                velocity[i][j] = new PotentialPoint();
            }
        }
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();
        for (int i = 0; i <= 300; i++) {
            for (int j = 1; j < 90; j++) {
                if (!potentialPoints[i][j].getObstacle() && !potentialPoints[i][j + 1].getObstacle()) {
                    velocity[i][j].setValue(
                            (potentialPoints[i][j + 1].getValue() - potentialPoints[i][j-1].getValue()) / (2* jump)
                    );
                }
            }
        }
        return velocity;
    }

    public PotentialPoint[][] generateVVelocityMatrix() {
        PotentialPoint[][] velocity = new PotentialPoint[301][91];
        for (int i = 0; i < 301; i++) {
            for (int j = 0; j < 91; j++) {
                velocity[i][j] = new PotentialPoint();
            }
        }
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();
        for (int i = 1; i < 300; i++) {
            for (int j = 0; j <= 90; j++) {
                if (!potentialPoints[i][j].getObstacle() && !potentialPoints[i+1][j].getObstacle()) {
                    velocity[i][j].setValue(
                            -(potentialPoints[i+1][j].getValue() - potentialPoints[i-1][j].getValue()) / (2* jump)
                    );
                }
            }
        }
        return velocity;
    }

    public PotentialAndWirContainer evaluatePotencjalAndWirowoscAt(int x) {

        PotentialAndWirContainer potentialAndWirContainer = new PotentialAndWirContainer();
        PotentialPoint[] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix()[x];

        for (int i = 0; i <= 90; i++) {

            Double wirAnal = (Q / (2.0 * ni)) * (2.0 * potentialPoints[i].getY() - 0.0 - 0.9);
            Double potenAnal = (Q / (2.0 * ni)) * (Math.pow(potentialPoints[i].getY(), 3.0) / 3.0 - (Math.pow(potentialPoints[i].getY(), 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * potentialPoints[i].getY()));

            potentialAndWirContainer.addPoint(i + 1, potentialPoints[i].getValue(), potenAnal, potentialPoints[i].getWir(), wirAnal);
        }

        return potentialAndWirContainer;
    }

    public MatrixSpace getMatrixSpace() {
        return matrixSpace;
    }
}
