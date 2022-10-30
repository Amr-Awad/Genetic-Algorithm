import java.util.ArrayList;
import java.util.Random;

class Chromosome{
	public int[] genes;
	public int fitness;
	
	public void generate(int numberOfItems) {
		this.genes = new int[numberOfItems];
		for (int i = 0; i < numberOfItems; i++) {
            Random rand = new Random();
            float float_random = rand.nextFloat();

            this.genes[i] = Math.round(float_random);

        }
	}
	public void printGenes() {
		for(int i=0;i<genes.length;i++) {
			System.out.print(genes[i]+" ");
		}
		System.out.println("\n");
	}
}
public class KnapSack {
    static int [] weights = {20,30,10,2,1};
    static int [] values;
    static int NumberOfItems = 5;
    static int knapWeight = 40;
    final static int NumberOfPopulation = 5;

    public static ArrayList<Chromosome> generatePopulation() {
        ArrayList<Chromosome> population = new ArrayList<>();
        for (int i = 0 ; i<NumberOfPopulation ; i++)
        {
            Chromosome chromosome = new Chromosome();
            chromosome.generate(NumberOfItems);
            int totalWeight = 0;
            for(int j = 0 ; j<NumberOfItems ; j++)
            {
                if(chromosome.genes[j]==1)
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

    public static void calculateFitnessValue(ArrayList<Chromosome> population) {
        for(int i=0 ; i<NumberOfPopulation ; i++)
        {
        	Chromosome current = population.get(i);
            current.fitness=0;
            for (int j =0 ; j<NumberOfItems ; j++)
            {
                if(current.genes[j] == 1)
                    current.fitness+=values[j];
            }
        }
    }

    public static Chromosome[] chromosomeSelection(ArrayList<Chromosome> population) {
        int totalFitnessValues = 0;
        for(int i=0 ; i<NumberOfPopulation ; i++)
            totalFitnessValues+=population.get(i).fitness;

        float [] selectionProbability = new float[NumberOfPopulation];

        for(int i =0 ; i<NumberOfPopulation ; i++)
        {
            if(i==0)
                selectionProbability[i] = population.get(i).fitness/totalFitnessValues;

            else
                selectionProbability[i] = selectionProbability[i-1] + (population.get(i).fitness/totalFitnessValues);
        }
        Chromosome[] selectedChromosomes = new Chromosome[2];
        for(int i=0 ; i<2 ; i++)
        {
            Random rand = new Random(); //instance of random class
            float float_random = rand.nextFloat();

            for (int j =0 ; j<NumberOfPopulation ; j++)
            {
                if(float_random <= selectionProbability[j]) {
                    selectedChromosomes[i] = population.get(j);
                    break;
                }
            }
        }

        return selectedChromosomes;
    }



    public static void main(String[] args) {
        ArrayList<Chromosome> pop = generatePopulation();
        for(int i=0 ;i < NumberOfItems ; i++)
        {
            pop.get(i).printGenes();
        }

    }
}
