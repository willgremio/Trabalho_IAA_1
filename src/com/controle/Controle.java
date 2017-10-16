package com.controle;

import ADReNA_API.Data.DataSet;
import ADReNA_API.Data.DataSetObject;
import ADReNA_API.NeuralNetwork.Backpropagation;
import ADReNA_API.Util.Export;
import com.ui.FrmUI;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Classe abstrata do FrmUI.java
 *
 * @author Master
 */
public class Controle extends FrmUI {

    private final double TAXA_ERRO = 0.05;
    private final double TAXA_APRENDIZADO = 0.1;
    private final int INTERACOES = Integer.MAX_VALUE;
    private final int[] CAMADA_INTERMEDIARIA = {11};

    private final Backpropagation bp = new Backpropagation(12, 3, CAMADA_INTERMEDIARIA);

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == execute) { // se clicado no botão Buscar
            reconhecimento();
        }
    }

    public Controle() throws Exception {
        super();

        bp.SetLearningRate(TAXA_APRENDIZADO);
        bp.SetMaxIterationNumber(INTERACOES);
        bp.SetErrorRate(TAXA_ERRO);

        treinamento();
    }

    private void treinamento() throws Exception {

        DataSet trainingSet = new DataSet(12, 3);

        /**
         * Goleiro
         */
        trainingSet.Add(new DataSetObject(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new double[]{0, 0, 0}));

        /**
         * Zagueiro
         */
        trainingSet.Add(new DataSetObject(new double[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0}, new double[]{0, 1, 0}));

        /**
         * Lateral
         */
        trainingSet.Add(new DataSetObject(new double[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, new double[]{1, 0, 0}));

        /**
         * Meia
         */
        trainingSet.Add(new DataSetObject(new double[]{1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0}, new double[]{1, 1, 0}));

        /**
         * Atacante
         */
        trainingSet.Add(new DataSetObject(new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, new double[]{1, 1, 1}));

        bp.Learn(trainingSet);
        //Export.DataSet(trainingSet, "C:/Users/Willian/Desktop/ADReNA/treinamentoJson/trei.json");
    }

    private void reconhecimento() {

        StringBuilder builder = new StringBuilder();

        switch (carac_fisica.getSelectedIndex()) {
            case 0:
                builder.append("0,0,0");
                break;
            case 1:
                builder.append("0,1,0");
                break;
            case 2:
                builder.append("1,0,0");
                break;
            case 3:
                builder.append("1,1,0");
                break;
            default:
                builder.append("1,1,1");
                break;
        }

        switch (carac_tec.getSelectedIndex()) {
            case 0:
                builder.append(",0,0,0");
                break;
            case 1:
                builder.append(",0,1,0");
                break;
            case 2:
                builder.append(",1,0,0");
                break;
            case 3:
                builder.append(",1,1,0");
                break;
            default:
                builder.append(",1,1,1");
                break;
        }

        switch (carac_tat.getSelectedIndex()) {
            case 0:
                builder.append(",0,0,0");
                break;
            case 1:
                builder.append(",0,1,0");
                break;
            case 2:
                builder.append(",1,0,0");
                break;
            case 3:
                builder.append(",1,1,0");
                break;
            default:
                builder.append(",1,1,1");
                break;
        }

        switch (carac_psic.getSelectedIndex()) {
            case 0:
                builder.append(",0,0,0");
                break;
            case 1:
                builder.append(",0,1,0");
                break;
            case 2:
                builder.append(",1,0,0");
                break;
            case 3:
                builder.append(",1,1,0");
                break;
            default:
                builder.append(",1,1,1");
                break;
        }

        String[] temp = builder.toString().split(",");
        double[] sequence = new double[temp.length];
        for (int z = 0; z < temp.length; z++) {
            sequence[z] = Double.parseDouble(temp[z]);
        }

        /*System.out.println(Arrays.toString(temp));
        System.out.println(Arrays.toString(sequence));*/
        double[] result = null;
        try {
            result = bp.Recognize(sequence);
            //System.out.println(Arrays.toString(result));
        } catch (Exception e) {
        }

        showResult(result);
    }

    private void showResult(double[] result) {

        String r = "";
        String resultExplain = "";

        for (int i = 0; i < result.length; i++) {
            resultExplain += "Saída " + i + ": " + result[i] + "\n";
            if (result[i] > 0.55) {
                r += "1";
            } else {
                r += "0";
            }
        }

        switch (r) {
            case "000":
                explain.setText("Posição: Goleiro.");
                break;
                
            case "010":
                explain.setText("Posição: Zagueiro.");
                break;
                
            case "100":
                explain.setText("Posição: Lateral.");
                break;
                
            case "110":
                explain.setText("Posição: Meia.");
                break;
                
            case "111":
                explain.setText("Posição: Atacante.");
                break;
                
            default:
                explain.setText("Nenhum padrão reconhecido!");
                break;
        }
        
        explainResult.setText(resultExplain);

    }

}
