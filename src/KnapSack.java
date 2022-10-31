import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    static int [] weights;
    static int [] values;
    static int NumberOfItems;
    static int knapWeight;
    static double crossoverProbability = 0.6;

    final static int NumberOfPopulation = 5;

    public static boolean checkWeight(Chromosome chromosome) {
        int totalWeight = 0;
        for (int j = 0; j < NumberOfItems; j++) {
            if (chromosome.genes[j] == 1) {
                totalWeight += weights[j];
            }
        }
        if (totalWeight > knapWeight)
            return false;
        return true;
    }

    public static ArrayList<Chromosome> generatePopulation() {
        ArrayList<Chromosome> population = new ArrayList<>();
        for (int i = 0 ; i<NumberOfPopulation ; i++)
        {
            Chromosome chromosome = new Chromosome();
            chromosome.generate(NumberOfItems);
            if(checkWeight(chromosome))
                population.add(chromosome);
            else
                i--;
        }
        calculateFitnessValue(population);
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

    public static ArrayList<Chromosome> rankSelection(ArrayList<Chromosome> population) {
        for (int i = 0; i < population.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < population.size(); j++){
                if (population.get(j).fitness < population.get(index).fitness){
                    index = j;//searching for lowest index
                }
            }
            Chromosome smallerNumber = population.get(index);
            population.set(index,population.get(i));
            population.set(i,smallerNumber);
        }
        return population;
    }

    public static Chromosome[] chromosomeSelection(ArrayList<Chromosome> population) {
        population = rankSelection(population);
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
                    if(i==1)
                    {
                        if(population.get(j)==selectedChromosomes[i-1])
                        {
                            i--;
                            break;
                        }
                    }
                    selectedChromosomes[i] = population.get(j);
                    break;
                }
            }
        }

        return selectedChromosomes;
    }

    public static Chromosome[] crossover(Chromosome[] selected) {
        Chromosome[] offspring = new Chromosome[2];
        offspring[0] = new Chromosome();
        offspring[1] = new Chromosome();
        offspring[0].generateEmpty(NumberOfItems);
        offspring[1].generateEmpty(NumberOfItems);
        float prob = new Random().nextFloat();
        System.out.println("Probability of crossover for current parents= " + prob + "\n");
        if(prob < crossoverProbability) {
            int crossoverPoint = new Random().nextInt(1,NumberOfItems-1);
            System.out.println("Crossover point= " + crossoverPoint + "\n");
            int size = selected[0].genes.length;
            for(int i=0;i<size;i++) {
                if(i < crossoverPoint) {
                    offspring[0].genes[i] = selected[0].genes[i];
                }
                else {
                    offspring[0].genes[i] = selected[1].genes[i];
                }
            }
            calculateSingleFitness(offspring[0]);
            for(int i=0;i<size;i++) {
                if(i < crossoverPoint) {
                    offspring[1].genes[i] = selected[1].genes[i];
                }
                else {
                    offspring[1].genes[i] = selected[0].genes[i];
                }
            }
            calculateSingleFitness(offspring[1]);
            for(int i =0 ; i< 2;i++) {
                if(!checkWeight(offspring[i]))
                    return selected;
            }
            return offspring;
        }
        return selected;
    }

    public static Chromosome[] mutation(Chromosome[] selectedChromosomes) {
        float Pm = 0.1f;
        Chromosome[] originalChromosomes = selectedChromosomes;
        for (int  i = 0 ; i < 2 ; i++) {
            for (int  j = 0 ; j < selectedChromosomes[i].genes.length ; j++) {
                Random rand2 = new Random();
                float float_random = rand2.nextFloat();
                System.out.println(float_random);
                if(float_random <= Pm)
                {
                    if(selectedChromosomes[i].genes[j] == 1)
                        selectedChromosomes[i].genes[j] = 0;
                    else
                        selectedChromosomes[i].genes[j] = 1;
                }
            }
        }
        calculateSingleFitness(selectedChromosomes[0]);
        calculateSingleFitness(selectedChromosomes[1]);
        for(int i =0 ; i< 2;i++) {
            if(!checkWeight(selectedChromosomes[i]))
                return originalChromosomes;
        }
        return selectedChromosomes;
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
        Chromosome[] offspring;
        offspring = crossover(parents);
        System.out.println("Offspring:");
        offspring[0].printGenes();
        offspring[1].printGenes();

        Chromosome[] mutatedOffsprings;
        mutatedOffsprings = mutation(offspring);
        System.out.println("Offspring with mutation:");
        mutatedOffsprings[0].printGenes();
        mutatedOffsprings[1].printGenes();
    }

    public static void readInput() throws Exception {
		File file = new File("input.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		line = br.readLine();
		int cases = Integer.parseInt(line);
		for(int i=0;i<cases;i++) {
			br.readLine();
			br.readLine();
			line = br.readLine();
			knapWeight = Integer.parseInt(line);
			line = br.readLine();
			NumberOfItems = Integer.parseInt(line);
			weights = new int[NumberOfItems];
			values = new int[NumberOfItems];
			String[] content;
			for(int j=0;j<NumberOfItems;j++) {
				line = br.readLine();
				content = line.split(" ");
				weights[j] = Integer.parseInt(content[0]);
				values[j] = Integer.parseInt(content[1]);
			}
			runAlgorithm();
		}
		br.close();
	}

	public static void main(String[] args) throws Exception {
		readInput();
	}
}
