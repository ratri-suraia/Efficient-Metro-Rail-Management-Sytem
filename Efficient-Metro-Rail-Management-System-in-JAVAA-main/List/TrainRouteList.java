package List;

import Entity.TrainRoute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TrainRouteList {
    private TrainRoute[] routes;
    private int numRoutes;
    private String filename;

    public TrainRouteList(int capacity, String filename) {
        routes = new TrainRoute[capacity];
        numRoutes = 0;
        this.filename = filename;
    }

    public TrainRouteList() {
    }

    public void addRoute(TrainRoute route) {
        if (numRoutes < routes.length) {
            routes[numRoutes] = route;
            numRoutes++;
        } else {
            System.out.println("Route list is full.");
        }
    }

    public void removeRoute(TrainRoute route) {
        for (int i = 0; i < numRoutes; i++) {
            if (routes[i] == route) {
                routes[i] = null;
                for (int j = i; j < numRoutes - 1; j++) {
                    routes[j] = routes[j + 1];
                }
                routes[numRoutes - 1] = null;
                numRoutes--;
                break;
            }
        }
    }

    public TrainRoute getRouteByID(int id) {
        for (int i = 0; i < numRoutes; i++) {
            if (routes[i].getID() == id) {
                return routes[i];
            }
        }
        return null;
    }

    public TrainRoute[] getAllRoutes() {
        TrainRoute[] allRoutes = new TrainRoute[numRoutes];
        for (int i = 0; i < numRoutes; i++) {
            allRoutes[i] = routes[i];
        }
        return allRoutes;
    }

    public void updateRoute(TrainRoute route) {
        for (int i = 0; i < numRoutes; i++) {
            if (routes[i].getID() == route.getID()) {
                routes[i] = route;
                saveToFile();
                break;
            }
        }
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (int i = 0; i < numRoutes; i++) {
                TrainRoute route = routes[i];
                writer.println(route.getID() + "," + route.getSource() + "," + route.getDestination()
                        + "," + route.getDistance() + "," + route.getDuration());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String source = parts[1];
                String destination = parts[2];
                int distance = Integer.parseInt(parts[3]);
                int duration = Integer.parseInt(parts[4]);
                TrainRoute route = new TrainRoute(id, source, destination, distance, duration);
                addRoute(route);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
