package com.calculadoraestadistica;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Creado por Carlos_Code el 06/07/2022.
 * carlos.japon.code@gmail.com
 */

class Calculadora {
    /**
     * Calcula la suma en los datos de la lista
     *
     * @param array data set
     * @return Sum of data set
     **/
    public double suma(LinkedList<Double> array) {
        double sumatoria = 0.0;
        for (Double i : array) {
            sumatoria += i;
        }
        return sumatoria;
    }

    /**
     * Calcula la media en los datos de la lista.
     *
     * @param array LinkedList with the data.
     * @return arithmetic mean
     */
    public double media(LinkedList<Double> array) {
        double sumatoria = suma(array);
        return sumatoria / array.size();
    }

    /**
     * Calcula la mediana en los datos de la lista.
     *
     * @param array LinkedList with the data.
     * @return arithmetic median
     */
    public double mediana(LinkedList<Double> array) {
        double mediana;
        array.sort(Comparator.naturalOrder());
        if (array.size() > 1) {
            if (array.size() % 2 == 0) { // es par   [0, 3, 6, 8, 16] = 5 - 1 = 4
                int med = array.size() / 2;        // 0  1  2  3, 4
                mediana = (array.get(med - 1) + array.get(med)) / 2;
            } else {
                int med = (array.size() - 1) / 2;
                mediana = array.get(med);
            }
        } else {
            mediana = array.get(0);
        }
        return mediana;
    }

    /**
     * Calcula la desviacion estandar mediante los datos de la lista.
     *
     * @param array LinkedList with the data.
     * @return result of standard deviation
     */
    public double standardDeviation(LinkedList<Double> array) {
        if (array.size() > 1) {
            double sumatoria = 0;
            double media = media(array);
            for (Double i : array) {  //[1, 3, 6, 8, 16]
                sumatoria += Math.pow(i - media, 2);
            }
            return (float) Math.sqrt((sumatoria) / (array.size() - 1));
        } else {
            return 0.00;
        }
    }
}
