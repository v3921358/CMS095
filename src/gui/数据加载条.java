/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.ImageIcon;

/**
 *
 * @author pungin
 */
public class 数据加载条 extends javax.swing.JFrame {

    public static final 数据加载条 instance = new 数据加载条();
    private int steps = 1;
    private int step = 1;
    private String title = "";
    private int value = 0;
    private int maximum = 100;
    private int minimum = 0;
    private String text = "";
    private ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("Image/Icon.png"));

    /**
     * Creates new form Progressbar
     */
    public 数据加载条() {
        steps = 1;
        step = 1;
        title = "";
        value = 0;
        maximum = 100;
        minimum = 0;
        text = "";
        initComponents();
        数据加载条1.setMinimum(0);
        数据加载条1.setMaximum(100);
        // 设置当前进度值
        数据加载条1.setValue(0);
         //绘制百分比文本（进度条中间显示的百分数）
        数据加载条1.setStringPainted(true);
        setIconImage(icon.getImage());
    
    }

    public static void visible(boolean bln) {
        instance.setVisible(bln);
    }
    
    public void setVisible(boolean bln) {
        if (bln) {
            setLocationRelativeTo(null);
        }
        super.setVisible(bln);
        if (!bln) {
            init();
        }
    }


    private void init() {
        setTitle("", 1);
        setStep(1);
        setText("");
        setMaximum(100);
        setMinimum(0);
        setValue(0);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String str) {
        title = str;
        if (steps > 1) {
            str += "(" + step + "/" + steps + ")";
        }
        super.setTitle(str);
    }



    public static void updateTitle(String str) {
        instance.setTitle(str);
    }

    public static void setTitle(String str, int steps) {
        setSteps(steps);
        instance.setTitle(str);
    }


    public static void setSteps(int steps) {
        instance.steps = steps;
        instance.setTitle(instance.title);
    }

    public static void nextStep() {
        setStep(++instance.step);
    }

    public static void setStep(int step) {
        instance.step = step;
        instance.setTitle(instance.title);
    }

    public static void updateText() {
        setText(instance.text);
    }

    public static void setText(String str) {
        instance.text = str;
        instance.状态信息.setText(instance.text + (getPercent() > -1 ? ("(" + getPercent() + "%)") : ""));
    }

    public static int getPercent() {
        if (instance.maximum <= 0) {
            return -1;
        }
        return (instance.value - instance.minimum) * 100 / instance.maximum;
    }

    public static void addValue() {
        setValue(++instance.value);
    }

    public static void setValue(int i) {
        instance.value = Math.max(Math.min(i, instance.maximum), instance.minimum);
        instance.数据加载条1.setValue(instance.value);
        updateText();
    }

    public static int getValue() {
        return instance.value;
    }

    public static void setMaximum(int i) {
        instance.maximum = i;
        instance.数据加载条1.setMaximum(instance.maximum);
    }

    public static void setMinimum(int i) {
        instance.minimum = i;
        instance.数据加载条1.setMinimum(instance.minimum);
    }

    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AppIcon = new javax.swing.JLabel();
        数据加载条1 = new javax.swing.JProgressBar();
        状态信息 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        数据加载条1.setBorderPainted(false);

        状态信息.setForeground(new java.awt.Color(0, 204, 255));
        状态信息.setText("状态");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(数据加载条1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(状态信息)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(状态信息)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(数据加载条1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AppIcon;
    private javax.swing.JProgressBar 数据加载条1;
    public static javax.swing.JLabel 状态信息;
    // End of variables declaration//GEN-END:variables


}
