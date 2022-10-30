import java.util.ArrayList;
import java.util.Random;

class Chromosome{
	public int[] genes;
	public int fitness;
	
	public void generateEmpty(int numberOfItems) {
		this.genes = new int[numberOfItems];
	}
	
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
		System.out.println("fitness= "+this.fitness +"\n");
	}
}
public class KnapSack {
    static int [] weights = {20,30,10,2,1};
    static int [] values = {10,5,2,7,1};
    static int NumberOfItems = 5;
    static int knapWeight = 40;
    static double crossoverProbability = 0.6;
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
    
    public static void calculateSingleFitness(Chromosome chrom) {
    	chrom.fitness=0;
    	for (int j =0 ; j<NumberOfItems ; j++)
        {
            if(chrom.genes[j] == 1)
                chrom.fitness+=values[j];
        }
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
        double totalFitnessValues = 0.0;
        for(int i=0 ; i<NumberOfPopulation ; i++) {
            totalFitnessValues+=population.get(i).fitness;
        }
        	

        double [] selectionProbability = new double[NumberOfPopulation];

        for(int i =0 ; i<NumberOfPopulation ; i++)
        {
            if(i==0)
                selectionProbability[i] = (population.get(i).fitness)/totalFitnessValues;
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
    public static Chromosome crossover(Chromosome[] selected) {
    	Chromosome offspring = new Chromosome();
    	offspring.generateEmpty(NumberOfItems);
    	float prob = new Random().nextFloat();
    	System.out.println("Probability of crossover for current parents= " + prob + "\n");
    	if(prob < crossoverProbability) {
	    	int crossoverPoint = new Random().nextInt(1,NumberOfItems-1);
	    	System.out.println("Crossover point= " + crossoverPoint + "\n");
	    	int size = selected[0].genes.length;
	    	for(int i=0;i<size;i++) {
	    		if(i < crossoverPoint) {
	    			offspring.genes[i] = selected[0].genes[i];
	    		}
	    		else {
	    			offspring.genes[i] = selected[1].genes[i];
	    		}
	    	}
	    	calculateSingleFitness(offspring);
	    	return offspring;
    	}
    	return null;
    }
    public static void runAlgorithm() {
    	ArrayList<Chromosome> chromosomes = new ArrayList<>();
    	chromosomes = generatePopulation();
    	calculateFitnessValue(chromosomes);
    	Chromosome[] parents = new Chromosome[2];
    	parents = chromosomeSelection(chromosomes);
    	System.out.println("Population:");
    	for(int i=0 ;i < NumberOfPopulation ; i++)
        {
            chromosomes.get(i).printGenes();
        }
    	System.out.println("Parents:");
    	for(int i=0 ;i < 2 ; i++)
        {
            parents[i].printGenes();
        }
    	Chromosome offspring = new Chromosome();
    	offspring = crossover(parents);
    	System.out.println("Offspring:");
    	offspring.printGenes();
    }



    public static void main(String[] args) {
    	runAlgorithm();

    }
}
