package workspace;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class place extends JPanel
{
    public double[][] hMatrix;
    public double[][] vMatrix;
    public int hslid;
    public int vslid;
    public int size = 512;
    public double[][] kor;

    public double[][][] Polygon;
    public int[] Len;
    public double[][] plane;

    public place()
    {
        hslid = 0;
        vslid = 0;
        kor = new double[][]{
                {size/2-1,0,0},{0,size/2-1,0},{0,0,size/2-1}};
        plane = new double[][]{
                {0,0,0,0},{0,0,0,0}};
        setPreferredSize(new Dimension(514,514));
    }

    // получение уравнения плоскости
    public double[] PlaneEquation(double[][] p)
    {
        double[][] A = new double[3][3];
        A[0][0] = -p[0][0];
        A[0][1] = -p[0][1];
        A[0][2] = -p[0][2];
        A[1][0] = p[1][0]-p[0][0];
        A[1][1] = p[1][1]-p[0][1];
        A[1][2] = p[1][2]-p[0][2];
        A[2][0] = p[2][0]-p[0][0];
        A[2][1] = p[2][1]-p[0][1];
        A[2][2] = p[2][2]-p[0][2];

        double[] R = new double[4];
        R[0] = A[1][1] * A[2][2] - A[1][2] * A[2][1];
        R[1] = A[1][2] * A[2][0] - A[1][0] * A[2][2];
        R[2] = A[1][0] * A[2][1] - A[1][1] * A[2][0];
        R[3] = A[0][0] * A[1][1] * A[2][2] + A[0][1] * A[1][2] * A[2][0] + A[0][2] * A[1][0] * A[2][1] - A[0][0] * A[1][2] * A[2][1] - A[0][1] * A[1][0] * A[2][2] - A[0][2] * A[1][1] * A[2][0];

        return  R;
    }

    // получаем координату z в точке на плоскости
    double CheckZ(double[] point, double[] Plane)
    {
        double z = -(Plane[0]*point[0]+Plane[1]*point[1]+Plane[3])/Plane[2];
//        System.out.print(z + " ");
        return z;
    }

    // перемножение матриц
    public double[][] MultiMatrix(double[][] a, double[][] b)
    {
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < b[0].length; j++)
            {
                for (int k = 0; k < b[0].length; k++)
                {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    // пересечение отрезков
    boolean LinesCross(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4 ,double dot[])
    {
        if ((x3 == x4) && (y3 == y4))
        {
            if ((x2 - x1) == 0)
            {
                double Ub1 = (y3 - y1) / (y2 - y1);
                if ((Ub1 >= 0) && (Ub1 <= 1))
                {
                    if (x1 == x3)
                    {
                        dot[0] = x1;
                        dot[1] = y1 + (y2 - y1) * Ub1;
                        return true;
                    }
                    else
                    {
                        return  false;
                    }
                }
                else
                {
                    return  false;
                }
            }
            else if ((y2 - y1) == 0)
            {
                double Ua1 = (x3 - x1) / (x2 - x1);
                if ((Ua1 >= 0) && (Ua1 <= 1))
                {
                    if (y1 == y3)
                    {
                        dot[0] = x1 + (x2 - x1) * Ua1;
                        dot[1] = y1;
                        return  true;
                    }
                    else
                    {
                        return  false;
                    }
                }
                else
                {
                    return  false;
                }
            }
            else
            {
                double Ua1 = (x3 - x1) / (x2 - x1);
                double Ub1 = (y3 - y1) / (y2 - y1);
                if ((x1 == x3) && (y1 == y3))
                {
                    dot[0] = x1;
                    dot[1] = y1;
                    return true;
                }
                else if ((x2 == x3) && (y2 == y3))
                {
                    dot[0] = x2;
                    dot[1] = y2;
                    return  true;
                }
                else
                {
                    if (((Ua1 > 0) && (Ua1 < 1)) && (Ua1 == Ub1))
                    {
                        dot[0] = x1 + (x2 - x1) * Ua1;
                        dot[1] = y1 + (y2 - y1) * Ua1;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }


        double aNumerator = (x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3);
        double bNumerator = (x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3);
        double Denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

        if (Denominator == 0)
        {
            if (aNumerator == 0 || bNumerator == 0)
            {
                double Ua1 = (x3 - x1) / ((x2 - x1) == 0 ? 1 : (x2 - x1));
                double Ua2 = (x4 - x1) / ((x2 - x1) == 0 ? 1 : (x2 - x1));
                double Ua3 = (x1 - x3) / ((x4 - x3) == 0 ? 1 : (x4 - x3));
                double Ua4 = (x2 - x3) / ((x4 - x3) == 0 ? 1 : (x4 - x3));
                double Ub1 = (y3 - y1) / ((y2 - y1) == 0 ? 1 : (y2 - y1));
                double Ub2 = (y4 - y1) / ((y2 - y1) == 0 ? 1 : (y2 - y1));
                double Ub3 = (y1 - y3) / ((y4 - y3) == 0 ? 1 : (y4 - y3));
                double Ub4 = (y2 - y3) / ((y4 - y3) == 0 ? 1 : (y4 - y3));
                if ((Ua1 == 0) && (Ua2 == 0))
                {
                    if ((Ub1 >= 0) && (Ub1 <= 1))
                    {
                        dot[0] = x1 + (x2 - x1) * Ub1;
                        dot[1] = y1 + (y2 - y1) * Ub1;
                        return true;
                    }
                    else if ((Ub2 >= 0) && (Ub2 <= 1))
                    {
                        dot[0] = x1 + (x2 - x1) * Ub2;
                        dot[1] = y1 + (y2 - y1) * Ub2;
                        return true;
                    }
                    else if ((Ub3 >= 0) && (Ub3 <= 1))
                    {
                        dot[0] = x3 + (x4 - x3) * Ub3;
                        dot[1] = y3 + (y4 - y3) * Ub3;
                        return true;
                    }
                    else if ((Ub4 >= 0) && (Ub4 <= 1))
                    {
                        dot[0] = x3 + (x4 - x3) * Ub4;
                        dot[1] = y3 + (y4 - y3) * Ub4;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    if ((Ua1 >= 0) && (Ua1 <= 1))
                    {
                        dot[0] = x1 + (x2 - x1) * Ua1;
                        dot[1] = y1 + (y2 - y1) * Ua1;
                        return true;
                    }
                    else if ((Ua2 >= 0) && (Ua2 <= 1))
                    {
                        dot[0] = x1 + (x2 - x1) * Ua2;
                        dot[1] = y1 + (y2 - y1) * Ua2;
                        return true;
                    }
                    else if ((Ua3 >= 0) && (Ua3 <= 1))
                    {
                        dot[0] = x3 + (x4 - x3) * Ua3;
                        dot[1] = y3 + (y4 - y3) * Ua3;
                        return true;
                    }
                    else if ((Ua4 >= 0) && (Ua4 <= 1))
                    {
                        dot[0] = x3 + (x4 - x3) * Ua4;
                        dot[1] = y3 + (y4 - y3) * Ua4;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            double Ua = aNumerator/Denominator;
            double Ub = bNumerator/Denominator;

            if (((Ua >= 0) && (Ua <= 1)) && ((Ub >= 0) && (Ub <= 1)))
            {
                dot[0] = x1 + (x2 - x1) * Ua;
                dot[1] = y1 + (y2 - y1) * Ua;
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    // транспонирование матрицы
    int[][] Tmatrix(int[][] matrix)
    {
        int[][] result = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            result[j][i] = matrix[i][j];
        }
        return result;
    }

    // изменение координат под активную рамку
    int[][] RedactKor(int[][] oldKor)
    {
        int[][] newKor = new int[oldKor.length][oldKor[0].length];
        for (int i = 0; i < newKor.length; i++)
        {
            newKor[i][0] = (size+2)/2 + oldKor[i][0];
            newKor[i][1] = (size+2)/2 - 1 - oldKor[i][1];
        }
        return newKor;
    }

    // проецирование на экран
    int[][] remake2d(double[][] shape3d)
    {
        double[][] result = new double[shape3d.length][shape3d[0].length];
        for (int i = 0; i < shape3d.length; i++)
            for (int j = 0; j < shape3d[0].length; j++)
            {
                result[i][j] = shape3d[i][j];
            }
        result = MultiMatrix(result, hMatrix);
        result = MultiMatrix(result, vMatrix);

        int[][] shape2d = new int[result.length][2];
        for (int i = 0; i < shape2d.length; i++)
        {
            shape2d[i][0] = (int) (result[i][0]);
            shape2d[i][1] = (int) (result[i][1]);
        }
        int[][] newKor = RedactKor(shape2d);
        return newKor;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // матрицы поворота изображения
        hMatrix = new double[][]{
                {Math.cos(-Math.toRadians(hslid)), 0, -Math.sin(-Math.toRadians(hslid))},
                {0, 1, 0},
                {Math.sin(-Math.toRadians(hslid)), 0, Math.cos(-Math.toRadians(hslid))}};
        vMatrix = new double[][]{
                {1, 0, 0}, {0, Math.cos(Math.toRadians(vslid)), Math.sin(Math.toRadians(vslid))},
                {0, -Math.sin(Math.toRadians(vslid)), Math.cos(Math.toRadians(vslid))}};

        // отрисовка панели для рисования
        g.setColor(Color.black);
        g.drawLine(0,0,size+1,0);
        g.drawLine(0,0,0,size+1);
        g.drawLine(0,size+1,size+1,size+1);
        g.drawLine(size+1,0,size+1,size+1);

        // отрисовка координатных осей
        int[][] shape2d = remake2d(kor);
        g.setColor(Color.red);
        for (int i = 0; i < kor.length; i++)
        {
            g.drawLine((size+2)/2,(size+2)/2-1,shape2d[i][0],shape2d[i][1]);
        }

        for (int i = 0; i < 2; i++)
        {
            plane[i] = PlaneEquation(Polygon[i]);
            if (Len[i] == 4)
            {
                Polygon[i][3][2] = CheckZ(Polygon[i][3],plane[i]);
            }
        }

        // отрисовка многоугольников
        int[][] Poly2d;
        int[][] poluXY;
        for (int i = 0; i < 2; i++)
        {
            Poly2d = remake2d(Polygon[i]);
            poluXY = Tmatrix(Poly2d);
            if (i == 0) g.setColor(Color.blue);
            else g.setColor(Color.green);
            g.drawPolygon(poluXY[0],poluXY[1],Len[i]);
        }

        if ((hslid == 0) && (vslid == 0))
        {
            // начало алгоритма
            Stack<int[]> Windows = new Stack<>();
            // заносим в стек первое окно
            Windows.push(new int[]{0, 0, size / 2});
            for (int j = 1; j > 0; )
            {
                // берём окно из стека и рассматриваем его
                int[] win = Windows.pop();
                int[][] w = new int[][]{
                        {win[0], size / 2 - win[1] - 1}, {win[0] + win[2] - 1, size / 2 - win[1] - 1}, {win[0] + win[2] - 1, size / 2 - (win[1] + win[2])}, {win[0], size / 2 - (win[1] + win[2])}};
                int[] event = new int[]{0,0,0,0,0};
                j = j - 1;

                for (int i = 0; i < 2; i++)
                {
                    // составление 4-х битового кода для вершин
                    boolean[][][] position = new boolean[2][4][4];
                    for (int k = 0; k < Len[i]; k++)
                    {
                        position[i][k][0] = false;
                        position[i][k][1] = false;
                        position[i][k][2] = false;
                        position[i][k][3] = false;
                        if (Polygon[i][k][0] < win[0]) position[i][k][0] = true;
                        if (Polygon[i][k][0] > win[0] + win[2] - 1) position[i][k][1] = true;
                        if (Polygon[i][k][1] < size / 2 - (win[1] + win[2])) position[i][k][2] = true;
                        if (Polygon[i][k][1] > size / 2 - win[1] - 1) position[i][k][3] = true;
                    }

                    // расположение относительно окна
                    int[] count = new int[]{0,0};
                    for (int k = 0; k < Len[i]; k++)
                    {
                        if ((position[i][k][0] || position[i][k][1] || position[i][k][2] || position[i][k][3]) == false)
                        {
                            count[i] += 1;
                        }
                    }

                    if (count[i] == Len[i])
                    {
                        // 2 случай
                        event[1] += i + 1;
                    }
                    else if (count[i] == 1)
                    {
                        // 1 случай
                        event[0] += i + 1;
                    }
                    else
                    {
                        // определяем количество пересечений многоугольника с окном
                        int cnt = 0;
                        double[] dot = new double[2];
                        for (int k = 0; k < Len[i] - 1; k++)
                        {
                            if (LinesCross(Polygon[i][k][0], Polygon[i][k][1], Polygon[i][k + 1][0], Polygon[i][k + 1][1], (double) w[0][0], (double) w[0][1], (double) w[1][0], (double) w[1][1], dot))
                                cnt += 1;
                            if (LinesCross(Polygon[i][k][0], Polygon[i][k][1], Polygon[i][k + 1][0], Polygon[i][k + 1][1], (double) w[1][0], (double) w[1][1], (double) w[2][0], (double) w[2][1], dot))
                                cnt += 1;
                            if (LinesCross(Polygon[i][k][0], Polygon[i][k][1], Polygon[i][k + 1][0], Polygon[i][k + 1][1], (double) w[2][0], (double) w[2][1], (double) w[3][0], (double) w[3][1], dot))
                                cnt += 1;
                            if (LinesCross(Polygon[i][k][0], Polygon[i][k][1], Polygon[i][k + 1][0], Polygon[i][k + 1][1], (double) w[0][0], (double) w[0][1], (double) w[3][0], (double) w[3][1], dot))
                                cnt += 1;
                        }
                        if (LinesCross(Polygon[i][0][0], Polygon[i][0][1], Polygon[i][Len[i] - 1][0], Polygon[i][Len[i] - 1][1], (double) w[0][0], (double) w[0][1], (double) w[1][0], (double) w[1][1], dot))
                            cnt += 1;
                        if (LinesCross(Polygon[i][0][0], Polygon[i][0][1], Polygon[i][Len[i] - 1][0], Polygon[i][Len[i] - 1][1], (double) w[1][0], (double) w[1][1], (double) w[2][0], (double) w[2][1], dot))
                            cnt += 1;
                        if (LinesCross(Polygon[i][0][0], Polygon[i][0][1], Polygon[i][Len[i] - 1][0], Polygon[i][Len[i] - 1][1], (double) w[2][0], (double) w[2][1], (double) w[3][0], (double) w[3][1], dot))
                            cnt += 1;
                        if (LinesCross(Polygon[i][0][0], Polygon[i][0][1], Polygon[i][Len[i] - 1][0], Polygon[i][Len[i] - 1][1], (double) w[0][0], (double) w[0][1], (double) w[3][0], (double) w[3][1], dot))
                            cnt += 1;

                        if (cnt != 0)
                        {
                            // 3 случай
                            event[2] += i + 1;
                        }
                        else
                        {
                            // Для определения (5) или (4) случая используется лучевой тест
                            int RayCount = 0;
                            double[][] tRay = new double[Len[i]][2];
                            double[] RayDot = new double[2];
                            for (int k = 0; k < Len[i] - 1; k++)
                            {
                                if (LinesCross(Polygon[i][k][0], Polygon[i][k][1], Polygon[i][k + 1][0], Polygon[i][k + 1][1],w[1][0], w[1][1], size, w[1][1], RayDot))
                                {
                                    tRay[RayCount][0] = RayDot[0];
                                    tRay[RayCount][1] = RayDot[1];
                                    RayCount += 1;
                                }
                            }
                            if (LinesCross(Polygon[i][0][0], Polygon[i][0][1], Polygon[i][Len[i] - 1][0], Polygon[i][Len[i] - 1][1], w[1][0], w[1][1], size, w[1][1], RayDot))
                            {
                                tRay[RayCount][0] = RayDot[0];
                                tRay[RayCount][1] = RayDot[1];
                                RayCount += 1;
                            }

                            // исключаем совпадающие точки пересечения
                            int resCount = RayCount;
                            for (int k = 0; k < RayCount - 1; k++)
                            {
                                for (int l = k; l < RayCount -1; l++)
                                {
                                    if ((tRay[k][0] == tRay[l + 1][0]) && (tRay[k][1] == tRay[l + 1][1]))
                                    {
                                        int nT = 0;
                                        for (int n = 0; n < Len[i]; n++)
                                        {
                                            if ((Polygon[i][n][0] == tRay[k][0]) && (Polygon[i][n][1] == tRay[k][1]))
                                            {
                                                nT = n;
                                                n = Len[i];
                                            }
                                        }
                                        if (nT == Len[i] - 1)
                                        {
                                            if (((Polygon[i][0][1] - Polygon[i][nT][1]) * (Polygon[i][nT][1] - Polygon[i][nT - 1][1])) >= 0)
                                            {
                                                resCount -= 1;
                                            }
                                        }
                                        else if (nT == 0)
                                        {
                                            if (((Polygon[i][1][1] - Polygon[i][nT][1]) * (Polygon[i][nT][1] - Polygon[i][Len[i] - 1][1])) >= 0)
                                            {
                                                resCount -= 1;
                                            }
                                        }
                                        else
                                        {
                                            if (((Polygon[i][nT - 1][1] - Polygon[i][nT][1]) * (Polygon[i][nT][1] - Polygon[i][nT + 1][1])) >= 0)
                                            {
                                                resCount -= 1;
                                            }
                                        }
                                        l = RayCount;
                                    }
                                }
                            }

                            if (resCount % 2 == 1)
                            {
                                // 4 случай
                                event[3] += i + 1;
                            }
                            else
                            {
                                // 5 случай
                                event[4] += i + 1;
                            }
                        }
                    }
                }

                if (event[1] > 0)
                {
                    if (event[4] > 0)
                    {
                        // находим z многоугольника в вершинах окна
                        double zMin = Polygon[event[1] - 1][0][2];
                        for (int k = 1; k < Len[event[1] - 1]; k++)
                        {
                            if (Polygon[event[1] - 1][k][2] < zMin) zMin = Polygon[event[1] - 1][k][2];
                        }
                        if (zMin >= 0)
                        {
                            int[][] poly2d = remake2d(Polygon[event[1] - 1]);
                            int[][] polyXY = Tmatrix(poly2d);
                            if (event[1] - 1 == 0) g.setColor(Color.blue);
                            else g.setColor(Color.green);
                            g.fillPolygon(polyXY[0], polyXY[1], Len[event[1] - 1]);
                        }
                        else
                        {
                            j = j + 4;
                            Windows.push(new int[]{win[0], win[1], win[2] / 2});
                            Windows.push(new int[]{win[0] + win[2] / 2, win[1], win[2] / 2});
                            Windows.push(new int[]{win[0], win[1] + win[2] / 2, win[2] / 2});
                            Windows.push(new int[]{win[0] + win[2] / 2, win[1] + win[2] / 2, win[2] / 2});
                        }
                    }
                    else
                    {
                        j = j + 4;
                        Windows.push(new int[]{win[0], win[1], win[2] / 2});
                        Windows.push(new int[]{win[0] + win[2] / 2, win[1], win[2] / 2});
                        Windows.push(new int[]{win[0], win[1] + win[2] / 2, win[2] / 2});
                        Windows.push(new int[]{win[0] + win[2] / 2, win[1] + win[2] / 2, win[2] / 2});
                    }
                }
                else if (event[0] > 0)
                {
                    // определяем z вершины многоугольника в окне размером в 1 пиксель
                    if (win[2] == 1)
                    {
                        if (event[4] > 0)
                        {
                            double z = 0;
                            z = CheckZ(new double[]{w[0][0], w[0][1]}, plane[event[0] - 1]);
                            if (z >= 0)
                            {
                                if (event[0] - 1 == 0) g.setColor(Color.blue);
                                else g.setColor(Color.green);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                        }
                        else
                        {
                            double[] z = new double[]{0,0};
                            for (int i = 0; i < 2; i++)
                            {
                                z[i] = CheckZ(new double[]{w[0][0], w[0][1]}, plane[i]);
                            }
                            if ((z[0] >= 0) && (z[0] > z[1]))
                            {
                                g.setColor(Color.blue);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                            else if ((z[0] >= 0) && (z[0] == z[1]))
                            {
                                g.setColor(Color.magenta);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                            else if ((z[1] >= 0) && (z[1] > z[0]))
                            {
                                g.setColor(Color.green);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                        }
                    }
                    else
                    {
                        j = j + 4;
                        Windows.push(new int[]{win[0], win[1], win[2] / 2});
                        Windows.push(new int[]{win[0] + win[2] / 2, win[1], win[2] / 2});
                        Windows.push(new int[]{win[0], win[1] + win[2] / 2, win[2] / 2});
                        Windows.push(new int[]{win[0] + win[2] / 2, win[1] + win[2] / 2, win[2] / 2});
                    }
                }
                else if (event[2] > 0)
                {
                    // определяем z многоугольника в окне размером в 1 пиксель
                    if (win[2] == 1)
                    {
                        if (event[4] > 0)
                        {
                            double z = 0;
                            z = CheckZ(new double[]{w[0][0], w[0][1]}, plane[event[2] - 1]);
                            if (z >= 0)
                            {
                                if (event[2] - 1 == 0) g.setColor(Color.blue);
                                else g.setColor(Color.green);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                        }
                        else
                        {
                            double[] z = new double[]{0,0};
                            for (int i = 0; i < 2; i++)
                            {
                                z[i] = CheckZ(new double[]{w[0][0], w[0][1]}, plane[i]);
                            }
                            if ((z[0] >= 0) && (z[0] > z[1]))
                            {
                                g.setColor(Color.blue);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                            else if ((z[0] >= 0) && (z[0] == z[1]))
                            {
                                g.setColor(Color.magenta);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                            else if ((z[1] >= 0) && (z[1] > z[0]))
                            {
                                g.setColor(Color.green);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                        }
                    }
                    else
                    {
                        j = j + 4;
                        Windows.push(new int[]{win[0], win[1], win[2] / 2});
                        Windows.push(new int[]{win[0] + win[2] / 2, win[1], win[2] / 2});
                        Windows.push(new int[]{win[0], win[1] + win[2] / 2, win[2] / 2});
                        Windows.push(new int[]{win[0] + win[2] / 2, win[1] + win[2] / 2, win[2] / 2});
                    }
                }
                else if (event[3] > 0)
                {
                    if (event[4] > 0)
                    {
                        double[] z = new double[4];
                        for (int k = 0; k < 4; k++)
                        {
                            z[k] = CheckZ(new double[]{w[k][0], w[k][1]}, plane[event[3] - 1]);
                        }
                        if ((z[0] >= 0) && (z[1] >= 0) && (z[2] >= 0) && (z[3] >= 0))
                        {
                            int[][] nw = RedactKor(w);
                            if (event[3] - 1 == 0) g.setColor(Color.blue);
                            else g.setColor(Color.green);
                            g.fillRect(nw[0][0], nw[0][1], win[2], win[2]);
                        }
                        else if ((z[0] >= 0) || (z[1] >= 0) || (z[2] >= 0) || (z[3] >= 0))
                        {
                            j = j + 4;
                            Windows.push(new int[]{win[0], win[1], win[2] / 2});
                            Windows.push(new int[]{win[0] + win[2] / 2, win[1], win[2] / 2});
                            Windows.push(new int[]{win[0], win[1] + win[2] / 2, win[2] / 2});
                            Windows.push(new int[]{win[0] + win[2] / 2, win[1] + win[2] / 2, win[2] / 2});
                        }
                    }
                    else
                    {
                        if (win[2] == 1)
                        {
                            double[] z = new double[]{0,0};
                            for (int i = 0; i < 2; i++)
                            {
                                z[i] = CheckZ(new double[]{w[0][0], w[0][1]}, plane[i]);
                            }
                            if ((z[0] >= 0) && (z[0] > z[1]))
                            {
                                g.setColor(Color.blue);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                            else if ((z[0] >= 0) && (z[0] == z[1]))
                            {
                                g.setColor(Color.magenta);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                            else if ((z[1] >= 0) && (z[1] > z[0]))
                            {
                                g.setColor(Color.green);
                                g.fillRect(win[0] + (size + 2) / 2, win[1] + 1, win[2], win[2]);
                            }
                        }
                        else
                        {
                            j = j + 4;
                            Windows.push(new int[]{win[0], win[1], win[2] / 2});
                            Windows.push(new int[]{win[0] + win[2] / 2, win[1], win[2] / 2});
                            Windows.push(new int[]{win[0], win[1] + win[2] / 2, win[2] / 2});
                            Windows.push(new int[]{win[0] + win[2] / 2, win[1] + win[2] / 2, win[2] / 2});
                        }
                    }
                }
            }
        }
    }
}