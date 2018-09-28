/**
 * Author's name: Tejaswini Jagtap
 */
package student;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MapMakerImpl implements MapMaker {

    /**
     * this method reads the file and stores in a map
     * @param in The {@link InputStream} used to read the {@link RailroadMap
     * map} data.
     * @return map with station list and route list obtained by reading
     * @throws RailroadBaronsException
     * @throws IOException
     */
    @Override
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException {
        try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        /**
         * list of stations
         */
        ArrayList<Station> stationList = new ArrayList<>();

        while(!(line = reader.readLine()).equals("##ROUTES##")) {
            String[] stationline = line.split(" ");
            String name = "";
            for (int i = 3; i < stationline.length; i++){
                name = name + " "+ stationline[i];
            }

            StationImpl station = new StationImpl(Integer.parseInt(stationline[0]), Integer.parseInt(stationline[1]), Integer.parseInt(stationline[2]), name);
            stationList.add(station);
        }

        /**
         * list of routes
         */
        ArrayList<Route> routeList = new ArrayList<>();

        while((line = reader.readLine())!=null){
            String[] routeline = line.split(" ");

            if (routeline[2].equals("UNCLAIMED")) {
                RouteImpl route = new RouteImpl(stationList.get(Integer.parseInt(routeline[0])), stationList.get(Integer.parseInt(routeline[1])), Baron.UNCLAIMED);
                routeList.add(route);
            }

            else if(routeline[2].equals("RED")){
                RouteImpl route = new RouteImpl(stationList.get(Integer.parseInt(routeline[0])), stationList.get(Integer.parseInt(routeline[1])), Baron.RED);
                routeList.add(route);
            }
             // git check
            else if(routeline[2].equals("GREEN")){
                RouteImpl route = new RouteImpl(stationList.get(Integer.parseInt(routeline[0])), stationList.get(Integer.parseInt(routeline[1])), Baron.GREEN);
                routeList.add(route);
            }

            else if(routeline[2].equals("BLUE")){
                RouteImpl route = new RouteImpl(stationList.get(Integer.parseInt(routeline[0])), stationList.get(Integer.parseInt(routeline[1])), Baron.BLUE);
                routeList.add(route);
            }

            else if(routeline[2].equals("YELLOW")){
                RouteImpl route = new RouteImpl(stationList.get(Integer.parseInt(routeline[0])), stationList.get(Integer.parseInt(routeline[1])), Baron.YELLOW);
                routeList.add(route);
            }
        }
            /**
             * storing in a map
             */
            RailroadBaronsMap map = new RailroadBaronsMap(stationList, routeList);
            return map;
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * writes the data from the map
     * @param map The {@link RailroadMap map} to write out to the
     * {@link OutputStream}.
     * @param out The {@link OutputStream} to which the
     * {@link RailroadMap map} data should be written.
     */
    @Override
    public void writeMap(RailroadMap map, OutputStream out) throws RailroadBaronsException{

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        Collection<Route> routeList = map.getRoutes();
        ArrayList<Station> stationList = new ArrayList<>();
        for (Route route: routeList){
            stationList.add(route.getOrigin());
            stationList.add(route.getDestination());
        }
        /**
         * writes stations
         */
        try {
        int i = 0;
            HashMap<Station, Integer> nameid= new HashMap<>();
        for (Station station: stationList){
            if (!nameid.containsKey(station)) {
                nameid.put(station, i);
                writer.write(i + " " + station.getRow() + " " + station.getCol() + " " + station.getName() + "\n");
                i++;
            }
        }
        writer.write("##ROUTES##\n");
            /**
             * writes routes
             */
        for (Route route: routeList){
            writer.write(nameid.get(route.getOrigin()) + " " + nameid.get(route.getDestination()) + " " + route.getBaron() + "\n");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
