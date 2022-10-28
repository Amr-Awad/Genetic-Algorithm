import java.util.ArrayList;
import java.util.Random;

class Chromosome{

}
public class KnapSack {
    static int [] weights;
    static int [] values;
    static int NumberOfItems = 5;
    static int knapWeight;
    final static int NumberOfPopulation = 5;

    public static int [] generateChromosome() {
        int[] chromosome = new int[NumberOfItems];
        for (int i = 0; i < NumberOfItems; i++) {
            Random rand = new Random(); //instance of random class
            float float_random = rand.nextFloat();

            chromosome[i] = Math.round(float_random);

        }
        return chromosome;
    }

    public static ArrayList<int []> generatePopulation() {
        ArrayList<int []> population = new ArrayList<>();
        for (int i = 0 ; i<NumberOfPopulation ; i++)
        {
            int [] chromosome = generateChromosome();
            int totalWeight = 0;
            for(int j = 0 ; j<NumberOfItems ; j++)
            {
                if(chromosome[j]==1)
                {
                    totalWeight += weights[j];
                }
            }
            if(totalWeight<=knapWeight)
                population.add(chromosome);
            else
                i--;
       }
        return population;
    }

    public static int [] calculateFitnessValue(ArrayList<int []> population) {
        int [] fitnessValues = new int[NumberOfItems];
        for(int i=0 ; i<NumberOfPopulation ; i++)
        {
            int fitnessValue = 0;
            for (int j =0 ; j<NumberOfItems ; j++)
            {
                if(population.get(i)[j] == 1)
                    fitnessValue+=values[j];
            }
            fitnessValues[i] = fitnessValue;
        }
        return fitnessValues;
    }

    public st0atic int [] chromosomeSelection(int [] fitnessValues) {
        int totalFitnessValues = 0;
        for(int i=0 ; i<NumberOfPopulation ; i++)
            totalFitnessValues+=fitnessValues[i];

        float [] selectionProbability = new float[NumberOfPopulation];

        for(int i =0 ; i<NumberOfPopulation ; i++)
        {
            if(i==0)
                selectionProbability[i] = fitnessValues[i]/totalFitnessValues;

            else
                selectionProbability[i] = selectionProbability[i-1] + (fitnessValues[i]/totalFitnessValues);
        }
        int [] selectedChromosomes = new int[2];
        for(int i=0 ; i<2 ; i++)
        {
            Random rand = new Random(); //instance of random class
            float float_random = rand.nextFloat();

            for (int j =0 ; j<NumberOfPopulation ; j++)
            {
                if(float_random <= selectionProbability[j]) {
                    selectedChromosomes[i] = j;
                    break;
                }
            }
        }

        return selectedChromosomes;
    }



    public static void main(String[] args) {
        int [] chrom = generateChromosome();
        for(int i=0 ;i < NumberOfItems ; i++)
        {
            System.out.println(chrom[i]);
        }

    }
}
