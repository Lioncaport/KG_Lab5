package workspace;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.*;

public class Main extends JDialog
{
    private JButton OKButton;
    private JPanel externalPanel;
    private JPanel window;
    private JTextField bx1TextField;
    private JSlider hSlider;
    private JTextField hTextField;
    private JSlider vSlider;
    private JTextField vTextField;
    private JTextField by1TextField;
    private JTextField bz1TextField;
    private JTextField bx2TextField;
    private JTextField by2TextField;
    private JTextField bz2TextField;
    private JTextField by4TextField;
    private JTextField bx4TextField;
    private JTextField bz3TextField;
    private JTextField by3TextField;
    private JTextField bx3TextField;
    private JTextField gx1TextField;
    private JTextField gy1TextField;
    private JTextField gz1TextField;
    private JTextField gx2TextField;
    private JTextField gy2TextField;
    private JTextField gz2TextField;
    private JTextField gx3TextField;
    private JTextField gx4TextField;
    private JTextField gy3TextField;
    private JTextField gy4TextField;
    private JTextField gz3TextField;

    public void count_of_horizont()
    {
        hTextField.setText(Integer.toString(hSlider.getValue()));
    }

    public void count_of_vertical()
    {
        vTextField.setText(Integer.toString(vSlider.getValue()));
    }

    public Main()
    {
        place myPlace = new place();
        window.add(myPlace);
        setContentPane(externalPanel);
        count_of_horizont();
        count_of_vertical();
        myPlace.Polygon = new double[][][]{
                {{50,28,25},{50,228,25},{180,228,25},{180,28,0}},
                {{20,48,80},{20,208,80},{230,100,-10},{0,0,0}}};
        myPlace.Len = new int[]{4,3};

        bx1TextField.setText(Integer.toString((int) myPlace.Polygon[0][0][0]));
        by1TextField.setText(Integer.toString((int) myPlace.Polygon[0][0][1]));
        bz1TextField.setText(Integer.toString((int) myPlace.Polygon[0][0][2]));
        bx2TextField.setText(Integer.toString((int) myPlace.Polygon[0][1][0]));
        by2TextField.setText(Integer.toString((int) myPlace.Polygon[0][1][1]));
        bz2TextField.setText(Integer.toString((int) myPlace.Polygon[0][1][2]));
        bx3TextField.setText(Integer.toString((int) myPlace.Polygon[0][2][0]));
        by3TextField.setText(Integer.toString((int) myPlace.Polygon[0][2][1]));
        bz3TextField.setText(Integer.toString((int) myPlace.Polygon[0][2][2]));
        bx4TextField.setText(Integer.toString((int) myPlace.Polygon[0][3][0]));
        by4TextField.setText(Integer.toString((int) myPlace.Polygon[0][3][1]));

        gx1TextField.setText(Integer.toString((int) myPlace.Polygon[1][0][0]));
        gy1TextField.setText(Integer.toString((int) myPlace.Polygon[1][0][1]));
        gz1TextField.setText(Integer.toString((int) myPlace.Polygon[1][0][2]));
        gx2TextField.setText(Integer.toString((int) myPlace.Polygon[1][1][0]));
        gy2TextField.setText(Integer.toString((int) myPlace.Polygon[1][1][1]));
        gz2TextField.setText(Integer.toString((int) myPlace.Polygon[1][1][2]));
        gx3TextField.setText(Integer.toString((int) myPlace.Polygon[1][2][0]));
        gy3TextField.setText(Integer.toString((int) myPlace.Polygon[1][2][1]));
        gz3TextField.setText(Integer.toString((int) myPlace.Polygon[1][2][2]));

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });

        hSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                count_of_horizont();
                myPlace.hslid = hSlider.getValue();
                myPlace.repaint();
            }
        });

        vSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                count_of_vertical();
                myPlace.vslid = vSlider.getValue();
                myPlace.repaint();
            }
        });

        OKButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String bx1 = bx1TextField.getText();
                String by1 = by1TextField.getText();
                String bz1 = bz1TextField.getText();
                String bx2 = bx2TextField.getText();
                String by2 = by2TextField.getText();
                String bz2 = bz2TextField.getText();
                String bx3 = bx3TextField.getText();
                String by3 = by3TextField.getText();
                String bz3 = bz3TextField.getText();
                String bx4 = bx4TextField.getText();
                String by4 = by4TextField.getText();

                String gx1 = gx1TextField.getText();
                String gy1 = gy1TextField.getText();
                String gz1 = gz1TextField.getText();
                String gx2 = gx2TextField.getText();
                String gy2 = gy2TextField.getText();
                String gz2 = gz2TextField.getText();
                String gx3 = gx3TextField.getText();
                String gy3 = gy3TextField.getText();
                String gz3 = gz3TextField.getText();
                String gx4 = gx4TextField.getText();
                String gy4 = gy4TextField.getText();

                int[][] p = new int[4][3];
                if (bx1.isEmpty() || by1.isEmpty() || bz1.isEmpty() || bx2.isEmpty() || by2.isEmpty() || bz2.isEmpty() || bx3.isEmpty() || by3.isEmpty() || bz3.isEmpty())
                {
                    JOptionPane.showMessageDialog(externalPanel, "BLUE - Enter all field ", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    p[0][0] = Integer.parseInt(bx1);
                    p[0][1] = Integer.parseInt(by1);
                    p[0][2] = Integer.parseInt(bz1);
                    p[1][0] = Integer.parseInt(bx2);
                    p[1][1] = Integer.parseInt(by2);
                    p[1][2] = Integer.parseInt(bz2);
                    p[2][0] = Integer.parseInt(bx3);
                    p[2][1] = Integer.parseInt(by3);
                    p[2][2] = Integer.parseInt(bz3);

                    double[][] n = new double[myPlace.Len[1]][3];
                    for (int i = 0; i < myPlace.Len[1]; i++)
                    {
                        for (int j = 0; j < 3; j++)
                        {
                            n[i][j] = myPlace.Polygon[1][i][j];
                        }
                    }

                    if (!bx4.isEmpty() || !by4.isEmpty())
                    {
                        if (!bx4.isEmpty() && !by4.isEmpty())
                        {
                            p[3][0] = Integer.parseInt(bx4);
                            p[3][1] = Integer.parseInt(by4);
                            myPlace.Polygon = new double[][][]{
                                    {{p[0][0],p[0][1],p[0][2]},{p[1][0],p[1][1],p[1][2]},{p[2][0],p[2][1],p[2][2]},{p[3][0],p[3][1],p[3][0]}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0}}};
                            myPlace.Len[0] = 4;
                            for (int i = 0; i < myPlace.Len[1]; i++)
                            {
                                for (int j = 0; j < 3; j++)
                                {
                                    myPlace.Polygon[1][i][j] = n[i][j];
                                }
                            }
                            myPlace.repaint();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(externalPanel, "BLUE - P4 - enter all field ", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        myPlace.Polygon = new double[][][]{
                                {{p[0][0],p[0][1],p[0][2]},{p[1][0],p[1][1],p[1][2]},{p[2][0],p[2][1],p[2][2]},{0,0,0}},
                                {{0,0,0},{0,0,0},{0,0,0},{0,0,0}}};
                        myPlace.Len[0] = 3;
                        for (int i = 0; i < myPlace.Len[1]; i++)
                        {
                            for (int j = 0; j < 3; j++)
                            {
                                myPlace.Polygon[1][i][j] = n[i][j];
                            }
                        }
                        myPlace.repaint();
                    }
                }

                if (gx1.isEmpty() || gy1.isEmpty() || gz1.isEmpty() || gx2.isEmpty() || gy2.isEmpty() || gz2.isEmpty() || gx3.isEmpty() || gy3.isEmpty() || gz3.isEmpty())
                {
                    JOptionPane.showMessageDialog(externalPanel, "GREEN - Enter all field ", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    p[0][0] = Integer.parseInt(gx1);
                    p[0][1] = Integer.parseInt(gy1);
                    p[0][2] = Integer.parseInt(gz1);
                    p[1][0] = Integer.parseInt(gx2);
                    p[1][1] = Integer.parseInt(gy2);
                    p[1][2] = Integer.parseInt(gz2);
                    p[2][0] = Integer.parseInt(gx3);
                    p[2][1] = Integer.parseInt(gy3);
                    p[2][2] = Integer.parseInt(gz3);

                    double[][] n = new double[myPlace.Len[0]][3];
                    for (int i = 0; i < myPlace.Len[0]; i++)
                    {
                        for (int j = 0; j < 3; j++)
                        {
                            n[i][j] = myPlace.Polygon[0][i][j];
                        }
                    }

                    if (!gx4.isEmpty() || !gy4.isEmpty())
                    {
                        if (!gx4.isEmpty() && !gy4.isEmpty())
                        {
                            p[3][0] = Integer.parseInt(gx4);
                            p[3][1] = Integer.parseInt(gy4);
                            myPlace.Polygon = new double[][][]{
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{p[0][0],p[0][1],p[0][2]},{p[1][0],p[1][1],p[1][2]},{p[2][0],p[2][1],p[2][2]},{p[3][0],p[3][1],p[3][0]}}};
                            myPlace.Len[1] = 4;
                            for (int i = 0; i < myPlace.Len[0]; i++)
                            {
                                for (int j = 0; j < 3; j++)
                                {
                                    myPlace.Polygon[0][i][j] = n[i][j];
                                }
                            }
                            myPlace.repaint();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(externalPanel, "GREEN - P4 - enter all field ", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        myPlace.Polygon = new double[][][]{
                                {{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                {{p[0][0],p[0][1],p[0][2]},{p[1][0],p[1][1],p[1][2]},{p[2][0],p[2][1],p[2][2]},{0,0,0}}};
                        myPlace.Len[1] = 3;
                        for (int i = 0; i < myPlace.Len[0]; i++)
                        {
                            for (int j = 0; j < 3; j++)
                            {
                                myPlace.Polygon[0][i][j] = n[i][j];
                            }
                        }
                        myPlace.repaint();
                    }
                }
            }
        });
    }

    public static void main(String[] args)
    {
        Main dialog = new Main();
        dialog.setTitle("8307 -- Tkachev Igor, Guseinov Akshin -- Lab 5, Var 6 -- 256x256 (red)");
        dialog.setMinimumSize(new Dimension(820,583));
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
}
