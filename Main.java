import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {
    
    public static void main(String[] args){

        List<Attribute> trainingList;
        List<Attribute> testList;
        HashMap<String,Integer> map = new HashMap<>();

        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel();
        jFrame.add(jPanel);

        String parameter = JOptionPane.showInputDialog(jFrame, "Podaj stałą uczenia");
        int a = Integer.parseInt(parameter);
        String trainSetFile = JOptionPane.showInputDialog(jFrame, "Podaj nazwę pliku, zawierającego zbiór treningowy");
        String testSetFile = JOptionPane.showInputDialog(jFrame, "Podaj nazwę pliku, zawierającego zbiór testowy");

        Object [] options = {
                "Yes", "No"
        };

        int n = JOptionPane.showOptionDialog(jFrame, "Czy chcesz podać wektor do klasyfikacji?","Question" ,JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);

        int correct1 = 0; // liczba poprawnie przypisanych kwiatków jednego rodzaju
        int correct2 = 0; // liczba poprawnie przypisanych kwiatków drugiego rodzaju
        int counter1 = 0; // liczba wystąpień kwiatków jednego rodzaju
        int counter2 = 0; // liczba wystąpień kwiatków drugiego rodzaju

        int counter = 0;

        List<Attribute> nodeSet = new ArrayList<>();
        String line;

        try {
            FileReader fileReader = new FileReader(trainSetFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine())!= null && (!line.equals(""))){

                String [] splited = line.split(";");
                List<Double> attributesColumn = new ArrayList<>();

                for (int i = 0; i < splited.length-1 ; i++) {
                    attributesColumn.add(Double.parseDouble(splited[i]));
                }

                Attribute attribute = new Attribute(attributesColumn,splited[splited.length-1]);
                nodeSet.add(attribute);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono takiego pliku");
        } catch (IOException e) {
            System.err.println("Problem wejścia/wyjścia");
        }

        trainingList = nodeSet;

        for (Attribute attribute : trainingList) {
            if (!map.containsKey(attribute.getIrisClass())) {
                map.put(attribute.getIrisClass(), counter++);
            }
        }

        Collections.shuffle(trainingList);
        int listSize = trainingList.get(0).getDataLines().size();
        Perceptron perceptron = new Perceptron(listSize,a);

        for (Attribute value : trainingList) {
            perceptron.learn(value, map.get(value.getIrisClass()));
        }

        BufferedReader systemInBuffer = new BufferedReader(new InputStreamReader(System.in));

        try {

        if (n == 0) {
            while (true) {

                System.out.println("Podaj wektor w postaci liczba\";\"liczba\";\" itd");
                String line1 = systemInBuffer.readLine();
                line1 += ";empty";
                List<Attribute> nodeSet1 = new ArrayList<>();
                String[] splited = line1.split(";");
                List<Double> attributesColumn = new ArrayList<>();

                for (int i = 0; i < splited.length - 1; i++)
                    attributesColumn.add(Double.parseDouble(splited[i]));

                nodeSet1.add(new Attribute(attributesColumn, splited[splited.length - 1]));
                testList = nodeSet1;

                for (Attribute node : testList) {
                    int y = perceptron.evaluate(node);
                    for (String key :map.keySet()) {
                        if (map.get(key) == y) {
                            System.out.println("Answer: " + key + "\n");
                        }
                    }
                }
            }
        }
        else{
            nodeSet = new ArrayList<>();

            try {
                FileReader fileReader = new FileReader(testSetFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while ((line = bufferedReader.readLine())!= null && (!line.equals(""))){

                    String [] splited = line.split(";");
                    List<Double> attributesColumn = new ArrayList<>();

                    for (int i = 0; i < splited.length-1 ; i++) {
                        attributesColumn.add(Double.parseDouble(splited[i]));
                    }

                    Attribute attribute = new Attribute(attributesColumn,splited[splited.length-1]);
                    nodeSet.add(attribute);
                }

            } catch (FileNotFoundException e) {
                System.err.println("Nie znaleziono takiego pliku");
            } catch (IOException e) {
                System.err.println("Problem wejścia/wyjścia");
            }

            testList = nodeSet;
            counter = 0;
            String[] keys = new String[2];

            for (String key: map.keySet()) {
                keys[counter++] = key;
            }

            for (Attribute attribute : testList) {
                int y = perceptron.evaluate(attribute);

                if (map.get(attribute.getIrisClass()) == 0) {
                    counter1++;
                }

                if (map.get(attribute.getIrisClass()) == 1) {
                    counter2++;
                }

                if (y == map.get(keys[0]) && y == map.get(attribute.getIrisClass())) {
                    correct1++;
                }

                if (y == map.get(keys[1]) && y == map.get(attribute.getIrisClass())) {
                    correct2++;
                }
            }

            double accFirstClass = ((double) correct1/counter1)*100;
            double accSecondClass = ((double) correct2/counter2*100);
            double accuracy = ((double) (correct1+correct2)/testList.size()*100);

            System.out.println("Wektor: "+perceptron.getVectorW()+ "\nt: "+ perceptron.getT());
            System.out.println("Dokładność całkowita: "+ accuracy + "%");
            System.out.println("Dokładność "+ keys[0]+ ": "+ accFirstClass+"%");
            System.out.println("Dokładność "+ keys[1]+ ": "+ accSecondClass+"%");
        }
        } catch (IOException e) {
            System.err.println("Problem wejścia/wyjścia");
        }
    }
}
