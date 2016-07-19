
class Rku {
    private double h, t = 0;
    private double[] parameters = new double[9];

    Rku(double x0, double y0, double m, double l, double c, double alpha, double nu, double theta, double step) {
        parameters[0] = x0;
        parameters[1] = y0;
        parameters[2] = 9.8;
        parameters[3] = m;
        parameters[4] = l;
        parameters[5] = c;
        parameters[6] = alpha;
        parameters[7] = nu;
        parameters[8] = theta;
        h = step;
    }

    private double func1(double t, double x, double y) {
        return y;
    }

    private double func2(double t, double x, double y) {
//        return -2*parameters[5]/parameters[3]*parameters[1]-(parameters[2]/parameters[4] + (-parameters[6]*Math.pow(parameters[7], 2)*Math.sin(parameters[7]*t))/parameters[4])*Math.sin(parameters[0]);
        return -(2 * parameters[5] / parameters[3] * y + (parameters[2] / parameters[4] + (-parameters[6] * Math.pow(parameters[7], 2) * Math.cos(parameters[8]) * Math.sin(parameters[7] * t)) / parameters[4]) * Math.sin(x) +
                (-parameters[6] * Math.pow(parameters[7], 2) * Math.sin(parameters[8]) * Math.sin(parameters[7] * t)) / parameters[4] * Math.cos(x));
    }

    public double suspensionX() {
        return parameters[6] * Math.sin(parameters[7] * t) * Math.sin(parameters[8]);
    }

    public double suspensionY() {
        return parameters[6] * Math.sin(parameters[7] * t) * Math.cos(parameters[8]);
    }

    public void toStep() {
        double[][] k = new double[4][2];
        k[0][0] = h * func1(t, parameters[0], parameters[1]);
        k[0][1] = h * func2(t, parameters[0], parameters[1]);
        k[1][0] = h * func1(t + h / 2, parameters[0] + k[0][0] / 2, parameters[1] + k[0][1] / 2);
        k[1][1] = h * func2(t + h / 2, parameters[0] + k[0][0] / 2, parameters[1] + k[0][1] / 2);
        k[2][0] = h * func1(t + h / 2, parameters[0] + k[1][0] / 2, parameters[1] + k[1][1] / 2);
        k[2][1] = h * func2(t + h / 2, parameters[0] + k[1][0] / 2, parameters[1] + k[1][1] / 2);
        k[3][0] = h * func1(t + h, parameters[0] + k[2][0], parameters[1] + k[2][1]);
        k[3][1] = h * func2(t + h, parameters[0] + k[2][0], parameters[1] + k[2][1]);
        parameters[0] += 1.0 / 6 * (k[0][0] + 2 * k[1][0] + 2 * k[2][0] + k[3][0]);
        parameters[1] += 1.0 / 6 * (k[0][1] + 2 * k[1][1] + 2 * k[2][1] + k[3][1]);
        t += h;
    }

    public double getParameters(int index) {
        return parameters[index];
    }

    public void setParameters(double parameter, int index) {
        parameters[index] = parameter;
    }
}

