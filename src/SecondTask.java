import java.util.*;

public class SecondTask {

    /*
        You are given a list of cities.
        Each direct connection between two cities has its transportation cost (an integer bigger than 0).
        The goal is to find the paths of minimum cost between pairs of cities.
        Assume that the cost of each path (which is the sum of costs of all direct connections belonging to this path) is at most 200000.
        The name of a city is a string containing characters a,...,z and is at most 10 characters long.
    */

    public static void main(String[] args) {
        // Call function for enter and validate the number of test
        int s = enterAndValidateTestCount();

        // Loop for show many tests. (Every cycle will show one testing)
        for (int t = 0; t < s; t++) {
            // Separate each test with empty line
            if (t > 0) System.out.println();

            // Call function for enter and validate the number of cities (it will be the vertices of the graph)
            int n = enterAndValidateCitiesCount();

            // Make a map for storage names of cities and his index
            Map<String, Integer> cityIndex = new HashMap<>();

            /*
            * The list that will describe the graph and its edges.
            * List<...> -> it's like a vertices of graph (our cities)
            * List<Edges> -> it's a description all relations with neighbors and cost edge
            */
            List<List<Edge>> graph = new ArrayList<>();

            // Loop for asking the names of cities, place it to created map, and initialize their neighbors
            for (int i = 0; i < n; i++) {
                // Call function for enter and validate name of city
                String cityName = enterAndValidateCityName();

                // Put city name and his index to map
                cityIndex.put(cityName, i);

                // Call function for enter and validate count of neighbors for entered city (neighbors for vertices of the graph)
                int neighbors = enterAndValidateNeighborCount(n - 1);

                /*
                * Put an empty list for current city.
                * In this list will be added Edge objects that describe relations in the graph
                */
                graph.add(new ArrayList<>());

                System.out.print("Next step enter the edge info.\n" +
                        "You should to enter two numbers separated by a space.\n" +
                        "The first number is the index of the neighbor's city, the second is the cost\n" +
                        "Example - '2 1'\n");

                // Loop for initialize all neighbors for entered city
                for (int j = 0; j < neighbors; j++) {
                    // Call function for enter and validate info about edges for neighbors for entered city
                    String[] edgeInfo = enterAndValidateEdgeInfo(n - 1, i);

                    // Initialize a neighbor (id of neighbor vertex)
                    int neighbor = Integer.parseInt(edgeInfo[0]) - 1;
                    // Initialize a cost of this edge
                    int cost = Integer.parseInt(edgeInfo[1]);


                    // Now we add the created edge to the list of all edges that our vertex (our city) has
                    graph.get(i).add(new Edge(neighbor, cost));
                }
            }

            // Call function to ask how many paths we want to explore
            int r = enterAndValidateQueryCount();

            System.out.print("The next step is to enter a path for exploring.\n" +
                    "You must enter two city names (start and end of the route)\n" +
                    "Example - 'Kyiv Kharkiv'\n");

            // Loop for display the results of explore of each path
            for (int i = 0; i < r; i++) {
                //Call function for ask a pair of cities (ask a path which should be explored)
                String[] way = enterAndValidateWay(cityIndex);

                // Extract start and finish cities from users input
                String startCity = way[0];
                String endCity = way[1];

                // Find index of entered cities
                int startIndex = cityIndex.get(startCity);
                int endIndex = cityIndex.get(endCity);

                // Use Dijkstra's algorithm to find the minimum path
                int result = dijkstra(graph, startIndex, endIndex);

                // Output of results
                if (result == -1)
                    System.out.println("The path does not exist.");
                else
                    System.out.println("Minimum cost for this way = " + result);
            }
        }
    }

    /*
         dijkstra() -> Dijkstra's algorithm which calculates the least cost path

         The input takes the graph itself, which is analyzed, the index of the city that
         marks the start and the index of the city that marks the finish
    */
    private static int dijkstra(List<List<Edge>> graph, int start, int target) {
        // Get the number of vertices in the graph
        int n = graph.size();

        // Initialize an array of distances, where all distances are initially equal to "infinity"
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Set the distance to the start vertex as 0
        dist[start] = 0;

        // Priority queue for processing vertices in order of increasing value
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));

        // Add the starting vertex to the queue with a value of 0
        pq.offer(new Edge(start, 0));

        // Investigate queue until the queue is empty
        while (!pq.isEmpty()) {
            // Extract the vertex with the smallest current value
            Edge current = pq.poll();
            int currentNeighbor = current.neighbor;
            int currentCost = current.cost;

            // If we've reached the target vertex, return its minimum value
            if (currentNeighbor == target) return currentCost;

            // Loop for looping the neighboring vertices of the current vertex
            for (Edge edge : graph.get(currentNeighbor)) {
                // Our neighbor
                int nextNeighbor = edge.neighbor;
                // New cost
                int newCost = currentCost + edge.cost;

                // If the new value is less than the current distance to the neighbor
                // Update the distance to the neighbor
                if (newCost < dist[nextNeighbor]) {
                    dist[nextNeighbor] = newCost;

                    // Add a neighbor to the queue with a new value
                    pq.offer(new Edge(nextNeighbor, newCost));
                }
            }
        }

        // If the path to the target vertex is not found, return -1
        return -1;
    }

    // Helper class to represent an edge
    static class Edge {
        int neighbor;
        int cost;

        Edge(int neighbor, int cost) {
            this.neighbor = neighbor;
            this.cost = cost;
        }
    }

    /*
        enterAndValidateTestCount() -> function that ask a number of tests and validate it.
        Here we use scanner for input the value from console.
        Also, we use the validation flag for the while loop,
        in order to be able to re-execute the cycle in case of entering the number of tests incorrectly.
        Use the 'try-catch' block to intercepts and output information about the Error and the cycle restart
        In case of success, we change the validate flag on 'true' and return the value entered

        According to the condition of the tests may not be more than 10 as well as less zero
    */
    public static int enterAndValidateTestCount() {
        Scanner reader = new Scanner(System.in);
        int testCount = 0;
        boolean isValidTestCounts = false;

        while (!isValidTestCounts) {
            try{
                System.out.print("Enter the num of tests: ");
                testCount = Integer.parseInt(reader.nextLine());

                if(testCount > 0 && testCount <= 10)
                    isValidTestCounts = true;
                else
                    System.out.println("Number of tests must be greater than 0 and less or equals 10");

            } catch(Exception e){
                System.out.println("Enter a valid number");
            }
        }

        return testCount;
    }

    /*
        enterAndValidateCitiesCount() -> function that ask a number of cities and validate it.
        Here we use scanner for input the value from console.
        Also, we use the validation flag for the while loop,
        in order to be able to re-execute the cycle in case of entering the number of tests incorrectly.
        Use the 'try-catch' block to intercepts and output information about the Error and the cycle restart
        In case of success, we change the validate flag on 'true' and return the value entered

        According to the condition, the number of cities cannot be more than 10_000 as well as less zero
    */
    public static int enterAndValidateCitiesCount() {
        Scanner reader = new Scanner(System.in);
        int citiesCount = 0;
        boolean isValidCitiesCount = false;

        while (!isValidCitiesCount) {
            try{
                System.out.print("Enter the cities count: ");
                citiesCount = Integer.parseInt(reader.nextLine());

                if (citiesCount > 0 && citiesCount <= 10_000)
                    isValidCitiesCount = true;
                else
                    System.out.println("Number of city count be greater than 0 and less or equals 10000");

            } catch (Exception e){
                System.out.println("Enter a valid number");
            }
        }

        return citiesCount;
    }

    /*
        enterAndValidateCityName() -> function that ask a city name and validate it.
        Here we use scanner for input the value from console.
        Also, we use the validation flag for the while loop,
        in order to be able to re-execute the cycle in case of entering the number of tests incorrectly.
        Use the 'try-catch' block to intercepts and output information about the Error and the cycle restart
        In case of success, we change the validate flag on 'true' and return the value entered

        According to the condition, the length of name cannot be more than 10 as well as less zero
    */
    public static String enterAndValidateCityName() {
        Scanner reader = new Scanner(System.in);
        String cityName = null;
        boolean isValidCityName = false;

        while (!isValidCityName) {
            try {
                System.out.print("Enter the city name: ");
                cityName = reader.nextLine();

                if (!cityName.trim().isEmpty() && cityName.trim().length() <= 10)
                    isValidCityName = true;
                else
                    System.out.println("City name characters must be between 0 and 10");

            } catch (Exception e){
                System.out.println("Enter a valid city name");
            }
        }

        return cityName;
    }

    /*
        enterAndValidateNeighborCount() -> function that ask a number of neighbors for city and validate it.
        Here we use scanner for input the value from console.
        Also, we use the validation flag for the while loop,
        in order to be able to re-execute the cycle in case of entering the number of tests incorrectly.
        Use the 'try-catch' block to intercepts and output information about the Error and the cycle restart
        In case of success, we change the validate flag on 'true' and return the value entered

        According to the condition, the number of neighbors in each city cannot be less than zero,
        as well as exceed the number of cities that will be present in the graph
    */
    public static int enterAndValidateNeighborCount(int maxValue) {
        Scanner reader = new Scanner(System.in);
        int neighbors = 0;
        boolean isValidNeighborsCount = false;

        while (!isValidNeighborsCount) {
            try{
                System.out.print("Enter the neighbors count: ");
                neighbors = Integer.parseInt(reader.nextLine());

                if (neighbors > 0 && neighbors <= maxValue)
                    isValidNeighborsCount = true;
                else
                    System.out.printf("Number of neighbors count be less than 0 and also no more available cities. Available %d neighbors\n", maxValue - 1);

            } catch (Exception e){
                System.out.println("Enter a valid number");
            }
        }

        return neighbors;
    }

    /*
        enterAndValidateEdgeInfo() -> function that ask info about edge and validate it.
        Here we use scanner for input the value from console.
        Also, we use the validation flag for the while loop,
        in order to be able to re-execute the cycle in case of entering the number of tests incorrectly.
        Use the 'try-catch' block to intercepts and output information about the Error and the cycle restart
        In case of success, we change the validate flag on 'true' and return the value entered

        User should enter index neighbor city and cost of this edge. The result will be returned in String[] where:
         [1] - index
         [2] - cost

        According to the condition, a city cannot be a neighbor to itself, and the cost of an edge cannot be negative
    */
    public static String[] enterAndValidateEdgeInfo(int maxValue, int currentCityIndex) {
        Scanner reader = new Scanner(System.in);
        String[] edgeInfo = null;
        int neighbor = 0;
        int cost = 0;
        boolean isValidEdgeInfo = false;

        while (!isValidEdgeInfo) {
            try {
                System.out.print("Enter the edge info: ");
                edgeInfo = reader.nextLine().split(" ");

                neighbor = Integer.parseInt(edgeInfo[0]) - 1;
                cost = Integer.parseInt(edgeInfo[1]);

                if (neighbor >= 0 && neighbor <= maxValue) {
                    if (neighbor == currentCityIndex)
                        System.out.printf("The same city cannot be its neighbor. Current city index %d. Choose an index other than %d\n", currentCityIndex + 1, currentCityIndex + 1);
                    else if (cost > 0)
                        isValidEdgeInfo = true;
                    else
                        System.out.println("The cost of path can't be less than 0");
                }
                else
                    System.out.printf("The index of the neighboring city cannot be less than 1 and greater than the total number of cities. You have %d cities\n", maxValue);

            } catch (Exception e){
                System.out.println("Enter a valid pair of numbers");
            }
        }

        return edgeInfo;
    }

    /*
        enterAndValidateQueryCount() -> function that ask a number of path which you want to explore and validate it.
        Here we use scanner for input the value from console.
        Also, we use the validation flag for the while loop,
        in order to be able to re-execute the cycle in case of entering the number of tests incorrectly.
        Use the 'try-catch' block to intercepts and output information about the Error and the cycle restart
        In case of success, we change the validate flag on 'true' and return the value entered

        According to the condition, they cannot be more than 100 and less than zero
    */
    public static int enterAndValidateQueryCount() {
        Scanner reader = new Scanner(System.in);
        int queryCount = 0;
        boolean isValidWaysCount = false;

        while (!isValidWaysCount) {
            try{
                System.out.print("Enter the number of paths you want to explore: ");
                queryCount = Integer.parseInt(reader.nextLine());

                if (queryCount > 0 && queryCount <= 100)
                    isValidWaysCount = true;
                else
                    System.out.println("Number of paths be greater than 0 and less or equals 100");

            } catch (Exception e){
                System.out.println("Enter a valid number");
            }
        }

        return queryCount;
    }

    /*
        enterAndValidateWay() -> function that ask a pair of city names (start and finish) and validate it.
        Here we use scanner for input the value from console.
        Also, we use the validation flag for the while loop,
        in order to be able to re-execute the cycle in case of entering the number of tests incorrectly.
        Use the 'try-catch' block to intercepts and output information about the Error and the cycle restart
        In case of success, we change the validate flag on 'true' and return the value entered

        Checks that the user has entered exactly two cities separated by a space and if these cities is present in graph
    */
    public static String[] enterAndValidateWay(Map<String, Integer> cityIndex) {
        Scanner reader = new Scanner(System.in);
        String[] way = null;
        boolean isValidQuery = false;

        while (!isValidQuery) {
            try{
                System.out.print("Enter the way: ");
                way = reader.nextLine().split(" ");

                if (way.length == 2) {
                    String startCity = way[0];
                    String endCity = way[1];

                    cityIndex.get(startCity);
                    cityIndex.get(endCity);

                    isValidQuery = true;
                } else
                    System.out.println("Enter a valid pair of cities");


            } catch (Exception e){
                System.out.println("Your graph does not contain this city names");
            }
        }

        return way;
    }
}
